package de.securityallies.data.Loader;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.hibernate.HibernateThreatMasterLoaderInstance;

import de.securityallies.taxii2.Taxii2Client.data.stix.MarkingDefinition;

public class MarkingDefinitionLoader {
	private HibernateThreatMasterLoaderInstance threatMasterLoaderInstance;
	
	public void loadMarkingDefinitionsFromList(List<MarkingDefinition> markingDefinitions, String collection_id, Timestamp timestamp) {
		Session session = threatMasterLoaderInstance.getSessionFactory().openSession();
		for (MarkingDefinition markingDefinition : markingDefinitions) {
			boolean insertHub = false;
			boolean insertSatellite = false;
			String sql = "SELECT marking_definition_id FROM H_MARKING_DEFINITION WHERE id=:id AND collection_id=:collection_id";
			NativeQuery<Integer> query = session.createNativeQuery(sql);
			query.setParameter("id", markingDefinition.getId());
			query.setParameter("collection_id", collection_id);
			Integer marking_definition_id = null;
			Transaction tx = session.beginTransaction();
			try {
				marking_definition_id = query.getSingleResult();
			} catch (NoResultException ex) {
				insertHub = true;
			}
			if (insertHub) {
				sql = "INSERT INTO H_MARKING_DEFINITION (id, collection_id, loadTimestamp) VALUES(:id,:collection_id,:loadTimestamp)";
				query = session.createNativeQuery(sql);
				query.setParameter("id", markingDefinition.getId());
				query.setParameter("collection_id", collection_id);
				query.setParameter("loadTimestamp", timestamp);
							
				query.executeUpdate();
				
				sql = "SELECT LAST_INSERT_ID();";
				NativeQuery<BigInteger> queryLastID = session.createNativeQuery(sql);
				BigInteger last_marking_definition_id = queryLastID.getSingleResult();
				marking_definition_id = last_marking_definition_id.intValueExact();
			}
			sql = "SELECT marking_definition_id FROM S_MARKING_DEFINITION WHERE marking_definition_id = :marking_definition_id AND name = :name AND created = :created AND modified = :modified";
			query = session.createNativeQuery(sql);
			query.setParameter("marking_definition_id", marking_definition_id);
			query.setParameter("name", markingDefinition.getName());
			query.setParameter("created", markingDefinition.getCreated());
			query.setParameter("modified", markingDefinition.getModified());
			try {
				marking_definition_id = query.getSingleResult();
			} catch (NoResultException ex) {
				insertSatellite = true;
			}
			if (insertSatellite) {
				sql = "INSERT INTO S_MARKING_DEFINITION (marking_definition_id,loadTimestamp,name,created,modified) VALUES(:marking_definition_id,:loadTimestamp,:name,:created,:modified)";
				query = session.createNativeQuery(sql);
				query.setParameter("marking_definition_id", marking_definition_id);
				query.setParameter("loadTimestamp", timestamp);
				query.setParameter("name",markingDefinition.getName());
				query.setParameter("created",markingDefinition.getCreated());
				query.setParameter("modified", markingDefinition.getModified());
				query.executeUpdate();
			}
			if (markingDefinition.getDefinition() != null) {
				for (String definition : markingDefinition.getDefinition().keySet()) {
					boolean insertDefinitionHub = false;
					sql = "SELECT definition_id FROM H_DEFINITION WHERE collection_id = :collection_id AND definition_type = :definition_type AND definition = :definition";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("definition_type", definition);
					query.setParameter("definition", markingDefinition.getDefinition().get(definition));
					Integer definition_id = null;
					try {
						definition_id = query.getSingleResult();
					} catch (NoResultException ex) {
						insertDefinitionHub = true;
					}
					if (insertDefinitionHub) {
						sql = "INSERT INTO H_DEFINITION (loadTimestamp,collection_id,definition_type,definition) VALUES(:loadTimestamp,:collection_id,:definition_type,:definition)";
						query = session.createNativeQuery(sql);
						query.setParameter("loadTimestamp", timestamp);
						query.setParameter("collection_id", collection_id);
						query.setParameter("definition_type", definition);
						query.setParameter("definition", markingDefinition.getDefinition().get(definition));
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID();";
						NativeQuery<BigInteger> queryLastID = session.createNativeQuery(sql);
						BigInteger last_definition_id = queryLastID.getSingleResult();
						definition_id = last_definition_id.intValueExact();
					}
					if (insertHub || insertDefinitionHub) {
						sql = "INSERT INTO L_MARKING_DEFINITION_DEFINITION (loadTimestamp,collection_id,definition_id,marking_definition_id) VALUES(:loadTimestamp,:collection_id,:definition_id,:marking_definition_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("loadTimestamp", timestamp);
						query.setParameter("collection_id", collection_id);
						query.setParameter("definition_id", definition_id);
						query.setParameter("marking_definition_id", marking_definition_id);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
		}
	}

}
