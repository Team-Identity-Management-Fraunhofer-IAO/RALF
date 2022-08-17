package de.securityallies.data.Loader;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.hibernate.HibernateThreatMasterLoaderInstance;

import de.securityallies.taxii2.Taxii2Client.data.stix.IntrusionSet;
import de.securityallies.taxii2.Taxii2Client.data.stix.MarkingDefinition;
import de.securityallies.taxii2.Taxii2Client.data.stix.Reference;

public class IntrusionSetLoader {
	private HibernateThreatMasterLoaderInstance threatMasterLoaderInstance;
	
	public void loadIntrusionSets(List<IntrusionSet> intrusionSets, String collection_id, Timestamp timestamp) {
		Session session = threatMasterLoaderInstance.getSessionFactory().openSession();
		for (IntrusionSet intrusionSet : intrusionSets) {
			Transaction tx = session.beginTransaction();
			String sql = "SELECT intrusion_set_id FROM H_INTRUSION_SET WHERE id=:id AND collection_id=:collection_id";
			NativeQuery<Integer> query = session.createNativeQuery(sql);
			query.setParameter("id", intrusionSet.getId());
			query.setParameter("collection_id", collection_id);
			Integer intrusion_set_id = null;
			try {
				intrusion_set_id = query.getSingleResult();
			}catch (NoResultException ex) {
				sql = "INSERT INTO H_INTRUSION_SET (id,collection_id,loadTimestamp) VALUES(:id,:collection_id,:loadTimestamp)";
				query = session.createNativeQuery(sql);
				query.setParameter("id", intrusionSet.getId());
				query.setParameter("collection_id", collection_id);
				query.setParameter("loadTimestamp", timestamp);
				query.executeUpdate();
				sql = "SELECT LAST_INSERT_ID()";
				NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
				BigInteger last_intrusion_set_id = lastIDQuery.getSingleResult();
				intrusion_set_id = last_intrusion_set_id.intValueExact();
			}
			sql = "SELECT intrusion_set_id FROM S_INTRUSION_SET WHERE intrusion_set_id=:intrusion_set_id AND name=:name AND created=:created AND modified=:modified AND description=:description AND x_mitre_version=:x_mitre_version";
			query = session.createNativeQuery(sql);
			query.setParameter("intrusion_set_id", intrusion_set_id);
			query.setParameter("name",intrusionSet.getName());
			query.setParameter("created", intrusionSet.getCreated());
			query.setParameter("modified", intrusionSet.getModified());
			query.setParameter("description", intrusionSet.getDescription());
			query.setParameter("x_mitre_version", intrusionSet.getX_mitre_version());
			try {
				intrusion_set_id = query.getSingleResult();
			}catch (NoResultException ex) {
				sql = "INSERT INTO S_INTRUSION_SET (intrusion_set_id,loadTimestamp,name,created,modified,description,x_mitre_version) VALUES(:intrusion_set_id,:loadTimestamp,:name,:created,:modified,:description,:x_mitre_version)";
				query = session.createNativeQuery(sql);
				query.setParameter("intrusion_set_id", intrusion_set_id);
				query.setParameter("loadTimestamp", timestamp);
				query.setParameter("name",intrusionSet.getName());
				query.setParameter("created", intrusionSet.getCreated());
				query.setParameter("modified", intrusionSet.getModified());
				query.setParameter("description", intrusionSet.getDescription());
				query.setParameter("x_mitre_version", intrusionSet.getX_mitre_version());
				query.executeUpdate();
			}
			tx.commit();
			tx = session.beginTransaction();
			//References
			if (intrusionSet.getReferences() != null) {
				for (Reference reference : intrusionSet.getReferences()) {
					sql = "SELECT reference_id FROM H_REFERENCE WHERE collection_id=:collection_id AND source_name=:source_name AND external_id=:external_id AND url=:url AND description=:description";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("source_name", reference.getSource_name());
					query.setParameter("external_id", reference.getExternal_id());
					query.setParameter("url", reference.getUrl());
					query.setParameter("description", reference.getDescription());
					Integer reference_id = null;
					try {
						reference_id = query.getSingleResult();
					}catch (NoResultException ex) {
						sql = "INSERT INTO H_REFERENCE (loadTimestamp,collection_id,source_name,external_id,url,description) VALUES(:loadTimestamp,:collection_id,:source_name,:external_id,:url,:description)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("source_name", reference.getSource_name());
						query.setParameter("external_id", reference.getExternal_id());
						query.setParameter("url", reference.getUrl());
						query.setParameter("description", reference.getDescription());
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_reference_id = lastIDQuery.getSingleResult();
						reference_id = last_reference_id.intValueExact();
					}
					sql = "SELECT l_intrusion_set_reference_id FROM L_INTRUSION_SET_REFERENCE WHERE collection_id=:collection_id AND intrusion_set_id=:intrusion_set_id AND reference_id=:reference_id";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("intrusion_set_id", intrusion_set_id);
					query.setParameter("reference_id", reference_id);
					try {
						Integer l_intrusion_set_reference_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_INTRUSION_SET_REFERENCE (loadTimestamp,collection_id,intrusion_set_id,reference_id) VALUES(:loadTimestamp,:collection_id,:intrusion_set_id,:reference_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("intrusion_set_id", intrusion_set_id);
						query.setParameter("reference_id", reference_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			//identity
			if (intrusionSet.getCreated_by_ref() != null) {
				sql = "SELECT identity_id FROM H_IDENTITY WHERE id=:id AND collection_id=:collection_id";
				query = session.createNativeQuery(sql);
				query.setParameter("id", intrusionSet.getCreated_by_ref().getId());
				query.setParameter("collection_id", collection_id);
				Integer identity_id = null;
				try {
					identity_id = query.getSingleResult();
				}catch (NoResultException ex) {
					System.err.println("Identity for Intrusion Set "+intrusionSet.getId()+" does not exist!");
				}
				if (identity_id != null) {
					sql = "SELECT l_intrusion_set_identity_id FROM L_INTRUSION_SET_IDENTITY WHERE intrusion_set_id=:intrusion_set_id AND identity_id=:identity_id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("intrusion_set_id", intrusion_set_id);
					query.setParameter("identity_id", identity_id);
					query.setParameter("collection_id", collection_id);
					try {
						Integer l_intrusion_set_identity_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_INTRUSION_SET_IDENTITY (intrusion_set_id,identity_id,loadTimestamp,collection_id) VALUES(:intrusion_set_id,:identity_id,:loadTimestamp,:collection_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("intrusion_set_id", intrusion_set_id);
						query.setParameter("identity_id", identity_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			//marking definition
			if (intrusionSet.getObject_marking_refs() != null) {
				for (MarkingDefinition markingDefinition : intrusionSet.getObject_marking_refs()) {
					sql = "SELECT marking_definition_id FROM H_MARKING_DEFINITION WHERE id=:id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("id", markingDefinition.getId());
					query.setParameter("collection_id", collection_id);
					Integer marking_definition_id = null;
					try {
						marking_definition_id = query.getSingleResult();
					} catch (NoResultException ex) {
						System.err.println("Marking Definition for intrusion set "+intrusionSet.getId()+" does not exist!");
					}
					if (marking_definition_id != null) {
						sql = "SELECT l_intrusion_set_marking_definition_id FROM L_INTRUSION_SET_MARKING_DEFINITION WHERE marking_definition_id = :marking_definition_id AND intrustion_set_id=:intrusion_set_id AND collection_id=:collection_id";
						query = session.createNativeQuery(sql);
						query.setParameter("marking_definition_id", marking_definition_id);
						query.setParameter("intrusion_set_id", intrusion_set_id);
						query.setParameter("collection_id", collection_id);
						try {
							Integer l_intrusion_set_marking_definition_id = query.getSingleResult();
						} catch (NoResultException ex) {
							sql = "INSERT INTO L_INTRUSION_SET_MARKING_DEFINITION (marking_definition_id,loadTimestamp,collection_id,intrustion_set_id) VALUES(:marking_definition_id,:loadTimestamp,:collection_id,:intrusion_set_id)";
							query = session.createNativeQuery(sql);
							query.setParameter("marking_definition_id", marking_definition_id);
							query.setParameter("intrusion_set_id", intrusion_set_id);
							query.setParameter("collection_id", collection_id);
							query.setParameter("loadTimestamp",timestamp);
							query.executeUpdate();
						}
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			//aliases
			if (intrusionSet.getAliases() != null) {
				for (String alias : intrusionSet.getAliases()) {
					sql = "SELECT alias_id FROM H_ALIASES WHERE alias=:alias AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("alias", alias);
					query.setParameter("collection_id", collection_id);
					Integer alias_id = null;
					try {
						alias_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_ALIASES (collection_id,loadTimestamp,alias) VALUES(:collection_id,:loadTimestamp,:alias)";
						query = session.createNativeQuery(sql);
						query.setParameter("alias", alias);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_alias_id = lastIDQuery.getSingleResult();
						alias_id = last_alias_id.intValueExact();
					}
					sql = "SELECT l_intrusion_set_aliases_id FROM L_INTRUSION_SET_ALIASES WHERE collection_id=:collection_id AND alias_id=:alias_id AND intrusion_set_id=:intrusion_set_id";
					query = session.createNativeQuery(sql);
					query.setParameter("intrusion_set_id",intrusion_set_id);
					query.setParameter("alias_id", alias_id);
					query.setParameter("collection_id", collection_id);
					try {
						Integer l_intrusion_set_aliases_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_INTRUSION_SET_ALIASES (collection_id,loadTimestamp,alias_id,intrusion_set_id) VALUES(:collection_id,:loadTimestamp,:alias_id,:intrusion_set_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("intrusion_set_id",intrusion_set_id);
						query.setParameter("alias_id", alias_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp",timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			//x_mitre_contributors
			if (intrusionSet.getX_mitre_contributors() != null) {
				for (String contributor : intrusionSet.getX_mitre_contributors()) {
					sql = "SELECT x_mitre_contributors_id FROM H_X_MITRE_CONTRIBUTORS WHERE collection_id=:collection_id AND x_mitre_contributor=:x_mitre_contributor";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("x_mitre_contributor", contributor);
					Integer x_mitre_contributors_id = null;
					try {
						x_mitre_contributors_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_X_MITRE_CONTRIBUTORS (collection_id,loadTimestamp,x_mitre_contributor) VALUES(:collection_id,:loadTimestamp,:x_mitre_contributor)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("x_mitre_contributor", contributor);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_x_mitre_contributors_id = lastIDQuery.getSingleResult();
						x_mitre_contributors_id = last_x_mitre_contributors_id.intValueExact();
					}
					sql = "SELECT l_intrusion_set_x_mitre_contributors_id FROM L_INTRUSION_SET_X_MITRE_CONTRIBUTORS WHERE collection_id=:collection_id AND intrusion_set_id=:intrusion_set_id AND x_mitre_contributors_id=:x_mitre_contributors_id";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("x_mitre_contributors_id", x_mitre_contributors_id);
					query.setParameter("intrusion_set_id", intrusion_set_id);
					try {
						Integer l_intrusion_set_x_mitre_contributors_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_INTRUSION_SET_X_MITRE_CONTRIBUTORS (loadTimestamp,collection_id,intrusion_set_id,x_mitre_contributors_id) VALUES(:loadTimestamp,:collection_id,:intrusion_set_id,:x_mitre_contributors_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("x_mitre_contributors_id", x_mitre_contributors_id);
						query.setParameter("intrusion_set_id", intrusion_set_id);
						query.setParameter("loadTimestamp",timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
		}
	}

}
