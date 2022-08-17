package de.securityallies.data.Loader;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.mysql.cj.QueryBindings;
import com.sql.hibernate.HibernateThreatMasterLoaderInstance;

import de.securityallies.taxii2.Taxii2Client.data.stix.AttackPattern;
import de.securityallies.taxii2.Taxii2Client.data.stix.CourseOfAction;
import de.securityallies.taxii2.Taxii2Client.data.stix.IntrusionSet;
import de.securityallies.taxii2.Taxii2Client.data.stix.Malware;
import de.securityallies.taxii2.Taxii2Client.data.stix.MarkingDefinition;
import de.securityallies.taxii2.Taxii2Client.data.stix.Reference;
import de.securityallies.taxii2.Taxii2Client.data.stix.Relationship;

public class RelationshipLoader {
	
	private HibernateThreatMasterLoaderInstance hibernateThreatMasterLoaderInstance;
	
	public void LoadRelationships(List<Relationship> relationships, String collection_id, Timestamp timestamp) {
		Session session = hibernateThreatMasterLoaderInstance.getSessionFactory().openSession();
		for (Relationship relationship : relationships) {
			Transaction tx = session.beginTransaction();
			String sql = "SELECT relationship_id FROM H_RELATIONSHIP WHERE id=:id AND collection_id=:collection_id";
			NativeQuery<Integer> query = session.createNativeQuery(sql);
			query.setParameter("id", relationship.getId());
			query.setParameter("collection_id", collection_id);
			Integer relationship_id = null;
			try {
				relationship_id = query.getSingleResult();
			}catch (NoResultException ex) {
				sql = "INSERT INTO H_RELATIONSHIP (id,collection_id,loadTimestamp) VALUES(:id,:collection_id,:loadTimestamp)";
				query = session.createNativeQuery(sql);
				query.setParameter("id", relationship.getId());
				query.setParameter("collection_id", collection_id);
				query.setParameter("loadTimestamp", timestamp);
				query.executeUpdate();
				sql = "SELECT LAST_INSERT_ID()";
				NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
				BigInteger last_relationship_id = lastIDQuery.getSingleResult();
				relationship_id = last_relationship_id.intValueExact();
			}
			sql = "SELECT relationship_id FROM S_RELATIONSHIP WHERE relationship_id=:relationship_id AND modified=:modified AND created=:created AND source_ref=:source_ref AND target_ref=:target_ref AND description=:description AND relationship_type=:relationship_type";
			query = session.createNativeQuery(sql);
			query.setParameter("relationship_id",relationship_id);
			query.setParameter("modified", relationship.getModified());
			query.setParameter("created", relationship.getCreated());
			query.setParameter("source_ref",relationship.getSource_ref());
			query.setParameter("target_ref", relationship.getTarget_ref());
			query.setParameter("description", relationship.getDescription());
			query.setParameter("relationship_type", relationship.getType());
			try {
				relationship_id = query.getSingleResult();
			} catch (NoResultException ex) {
				sql = "INSERT INTO S_RELATIONSHIP (relationship_id,loadTimestamp,modified,created,source_ref,target_ref,description,relationship_type) VALUES(:relationship_id,:loadTimestamp,:modified,:created,:source_ref,:target_ref,:description,:relationship_type)";
				query = session.createNativeQuery(sql);
				query.setParameter("relationship_id",relationship_id);
				query.setParameter("loadTimestamp", timestamp);
				query.setParameter("modified", relationship.getModified());
				query.setParameter("created", relationship.getCreated());
				query.setParameter("source_ref",relationship.getSource_ref());
				query.setParameter("target_ref", relationship.getTarget_ref());
				query.setParameter("description", relationship.getDescription());
				query.setParameter("relationship_type", relationship.getType());
				query.executeUpdate();
			}
			tx.commit();
			tx = session.beginTransaction();
			//Target refs
			if (!loadRelationship(relationship.getTarget_ref(), relationship_id, collection_id, session, timestamp)) {
				System.err.println("Could not insert Relationship between "+relationship.getSource_ref()+" and "+relationship.getTarget_ref()+" of type "+relationship.getType());
			}
			tx.commit();
			tx = session.beginTransaction();
			//Source refs
			if (!loadRelationship(relationship.getSource_ref(), relationship_id, collection_id, session, timestamp)) {
				System.err.println("Could not insert Relationship between "+relationship.getSource_ref()+" and "+relationship.getTarget_ref()+" of type "+relationship.getType());
			}
			tx.commit();
			tx = session.beginTransaction();
			//References
			if (relationship.getExternal_references() != null) {
				for (Reference reference : relationship.getExternal_references()) {
					sql = "SELECT reference_id FROM H_REFERENCE WHERE collection_id=:collection_id AND source_name=:source_name AND external_id=:external_id AND url=:url AND description=:description";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("source_name",reference.getSource_name());
					query.setParameter("external_id", reference.getExternal_id());
					query.setParameter("url", reference.getUrl());
					query.setParameter("description", reference.getDescription());
					Integer reference_id = null;
					try {
						reference_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_REFERENCE (loadTimestamp,collection_id,source_name,external_id,url,description) VALUES(:loadTimestamp,:collection_id,:source_name,:external_id,:url,:description)";
						query = session.createNativeQuery(sql);
						query.setParameter("loadTimestamp", timestamp);
						query.setParameter("collection_id", collection_id);
						query.setParameter("source_name",reference.getSource_name());
						query.setParameter("external_id", reference.getExternal_id());
						query.setParameter("url", reference.getUrl());
						query.setParameter("description", reference.getDescription());
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_reference_id = lastIDQuery.getSingleResult();
						reference_id = last_reference_id.intValueExact();
					}
					sql = "SELECT l_relationship_reference_id FROM L_RELATIONSHIP_REFERENCE WHERE reference_id=:reference_id AND relationship_id=:relationship_id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("reference_id", reference_id);
					query.setParameter("relationship_id", relationship_id);
					query.setParameter("collection_id", collection_id);
					try {
						Integer l_relationship_reference_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_RELATIONSHIP_REFERENCE (reference_id,relationship_id,loadTimestamp,collection_id) VALUES(:reference_id,:relationship_id,:loadTimestamp,:collection_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("reference_id", reference_id);
						query.setParameter("relationship_id", relationship_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			//Marking Definitions
			if (relationship.getObject_marking_refs() != null) {
				for (MarkingDefinition markingDefinition : relationship.getObject_marking_refs()) {
					sql = "SELECT marking_definition_id FROM H_MARKING_DEFINITION WHERE id=:id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("id", markingDefinition.getId());
					query.setParameter("collection_id", collection_id);
					Integer marking_definition_id = null;
					try {
						marking_definition_id = query.getSingleResult();
					} catch (NoResultException ex) {
						System.err.println("No Marking Definition found for Relationship "+relationship.getId());
					}
					if (marking_definition_id != null) {
						sql = "SELECT l_relationship_marking_definition_id FROM L_RELATIONSHIP_MARKING_DEFINITION WHERE collection_id=:collection_id AND relationship_id=:relationship_id AND marking_definition_id=:marking_definition_id";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("relationship_id", relationship_id);
						query.setParameter("marking_definition_id", marking_definition_id);
						try {
							Integer l_relationship_marking_definition_id = query.getSingleResult();
						} catch (NoResultException ex) {
							sql = "INSERT INTO L_RELATIONSHIP_MARKING_DEFINITION (loadTimestamp,collection_id,relationship_id,marking_definition_id) VALUES(:loadTimestamp,:collection_id,:relationship_id,:marking_definition_id)";
							query = session.createNativeQuery(sql);
							query.setParameter("collection_id", collection_id);
							query.setParameter("relationship_id", relationship_id);
							query.setParameter("marking_definition_id", marking_definition_id);
							query.setParameter("loadTimestamp", timestamp);
							query.executeUpdate();
						}
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			//Identities
			if (relationship.getCreated_by_ref() != null) {
				sql = "SELECT identity_id FROM H_IDENTITY WHERE id=:id AND collection_id=:collection_id";
				query = session.createNativeQuery(sql);
				query.setParameter("id", relationship.getCreated_by_ref().getId());
				query.setParameter("collection_id", collection_id);
				Integer identity_id = null;
				try {
					identity_id = query.getSingleResult();
				} catch (NoResultException ex) {
					System.err.println("No Identity found for relationship "+relationship.getId());
				}
				if (identity_id != null) {
					sql = "SELECT l_relationship_identity_id FROM L_RELATIONSHIP_IDENTITY WHERE relationship_id=:relationship_id AND identity_id=:identity_id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("relationship_id", relationship_id);
					query.setParameter("identity_id", identity_id);
					query.setParameter("collection_id", collection_id);
					try {
						Integer l_relationship_identity_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_RELATIONSHIP_IDENTITY (loadTimestamp,relationship_id,identity_id,collection_id) VALUES(loadTimestamp,relationship_id,identity_id,collection_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("relationship_id", relationship_id);
						query.setParameter("identity_id", identity_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			
		}
	}
	
	private boolean loadRelationship(String ref_id, Integer relationship_id, String collection_id, Session session, Timestamp timestamp) {
		String sql = "";
		if(ref_id.startsWith(CourseOfAction.type)) {
			sql = "SELECT course_of_action_id FROM H_COURSE_OF_ACTION WHERE collection_id=:collection_id AND id=:id";
		}else if(ref_id.startsWith(AttackPattern.type)) {
			sql = "SELECT attack_pattern_id FROM H_ATTACK_PATTERN WHERE collection_id=:collection_id AND id=:id";
		}else if(ref_id.startsWith(IntrusionSet.type)) {
			sql = "SELECT intrusion_set_id FROM H_INTRUSION_SET WHERE collection_id=:collection_id AND id=:id";
		}else if(ref_id.startsWith(Malware.type)){
			sql = "SELECT malware_id FROM H_MALWARE WHERE collection_id=:collection_id AND id=:id";
		}else {
			return false;
		}
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("collection_id", collection_id);
		query.setParameter("id", ref_id);
		Integer id = null;
		try {
			id = query.getSingleResult();
		} catch (NoResultException ex) {
			session.close();
			return false;
		}
		if (id != null) {
			if(ref_id.startsWith(CourseOfAction.type)) {
				sql = "SELECT l_course_of_action_relationship_id FROM L_COURSE_OF_ACTION_RELATIONSHIP WHERE collection_id=:collection_id AND relationship_id=:relationship_id AND course_of_action_id=:object_id";
			}else if(ref_id.startsWith(AttackPattern.type)) {
				sql = "SELECT l_attack_pattern_relationship_id FROM L_ATTACK_PATTERN_RELATIONSHIP WHERE collection_id=:collection_id AND relationship_id=:relationship_id AND attack_pattern_id=:object_id";
			}else if(ref_id.startsWith(IntrusionSet.type)) {
				sql = "SELECT l_intrusion_set_relationship_id FROM L_INTRUSION_SET_RELATIONSHIP WHERE collection_id=:collection_id AND relationship_id=:relationship_id AND intrusion_set_id=:object_id";
			}else if(ref_id.startsWith(Malware.type)) {
				sql = "SELECT l_malware_relationship_id FROM L_MALWARE_RELATIONSHIP WHERE collection_id=:collection_id AND relationship_id=:relationship_id AND malware_id=:object_id";
			}
			query = session.createNativeQuery(sql);
			query.setParameter("collection_id", collection_id);
			query.setParameter("relationship_id", relationship_id);
			query.setParameter("object_id", id);
			Integer link_id = null;
			try {
				link_id = query.getSingleResult();
			} catch (NoResultException ex) {
				if(ref_id.startsWith(CourseOfAction.type)) {
					sql = "INSERT INTO L_COURSE_OF_ACTION_RELATIONSHIP (loadTimestamp,collection_id,relationship_id,course_of_action_id) VALUES (:loadTimestamp,:collection_id,:relationship_id,:object_id)";
				}else if(ref_id.startsWith(AttackPattern.type)) {
					sql = "INSERT INTO L_ATTACK_PATTERN_RELATIONSHIP (loadTimestamp,collection_id,relationship_id,attack_pattern_id) VALUES (:loadTimestamp,:collection_id,:relationship_id,:object_id)";
				}else if(ref_id.startsWith(IntrusionSet.type)) {
					sql = "INSERT INTO L_INTRUSION_SET_RELATIONSHIP (loadTimestamp,collection_id,relationship_id,intrusion_set_id) VALUES (:loadTimestamp,:collection_id,:relationship_id,:object_id)";
				}else if(ref_id.startsWith(Malware.type)) {
					sql = "INSERT INTO L_MALWARE_RELATIONSHIP (loadTimestamp,collection_id,relationship_id,malware_id) VALUES (:loadTimestamp,:collection_id,:relationship_id,:object_id)";
				}
				query = session.createNativeQuery(sql);
				query.setParameter("collection_id", collection_id);
				query.setParameter("relationship_id", relationship_id);
				query.setParameter("object_id", id);
				query.setParameter("loadTimestamp", timestamp);
				query.executeUpdate();
			}
		}
		return true;
		
	}

}
