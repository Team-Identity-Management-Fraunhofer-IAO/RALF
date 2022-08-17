package de.securityallies.data.Loader;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import de.securityallies.taxii2.Taxii2Client.data.stix.Reference;
import com.sql.hibernate.HibernateThreatMasterLoaderInstance;

import de.securityallies.taxii2.Taxii2Client.data.stix.CourseOfAction;
import de.securityallies.taxii2.Taxii2Client.data.stix.MarkingDefinition;

public class CourseOfActionLoader {
	private HibernateThreatMasterLoaderInstance hibernateThreatMasterLoaderInstance;
	
	public void loadCoursesOfAction(List<CourseOfAction> coursesOfAction, String collection_id, Timestamp timestamp) {
		Session session = hibernateThreatMasterLoaderInstance.getSessionFactory().openSession();
		for (CourseOfAction courseOfAction : coursesOfAction) {
			Transaction tx = session.beginTransaction();
			String sql = "SELECT course_of_action_id FROM H_COURSE_OF_ACTION WHERE collection_id=:collection_id AND id=:id";
			NativeQuery<Integer> query = session.createNativeQuery(sql);
			query.setParameter("collection_id",collection_id);
			query.setParameter("id", courseOfAction.getId());
			Integer course_of_action_id = null;
			try {
				course_of_action_id = query.getSingleResult();
			} catch (NoResultException ex) {
				sql = "INSERT INTO H_COURSE_OF_ACTION (id,loadTimestamp,collection_id) VALUES(:id,:loadTimestamp,:collection_id)";
				query = session.createNativeQuery(sql);
				query.setParameter("collection_id",collection_id);
				query.setParameter("id", courseOfAction.getId());
				query.setParameter("loadTimestamp", timestamp);
				query.executeUpdate();
				sql = "SELECT LAST_INSERT_ID()";
				NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
				BigInteger last_course_of_action_id = lastIDQuery.getSingleResult();
				course_of_action_id = last_course_of_action_id.intValueExact();
			}
			sql = "SELECT course_of_action_id FROM S_COURSE_OF_ACTION WHERE course_of_action_id=:course_of_action_id AND name=:name AND created=:created AND modified=:modified AND description=:description AND x_mitre_version=:x_mitre_version AND x_mitre_deprecated=:x_mitre_deprecated";
			query = session.createNativeQuery(sql);
			query.setParameter("course_of_action_id", course_of_action_id);
			query.setParameter("name",courseOfAction.getName());
			query.setParameter("created", courseOfAction.getCreated());
			query.setParameter("modified", courseOfAction.getModified());
			query.setParameter("description", courseOfAction.getDescription());
			query.setParameter("x_mitre_version", courseOfAction.getX_mitre_version());
			query.setParameter("x_mitre_deprecated", courseOfAction.isX_mitre_deprecated());
			try {
				course_of_action_id = query.getSingleResult();
			}catch (NoResultException ex) {
				sql = "INSERT INTO S_COURSE_OF_ACTION (course_of_action_id,loadTimestamp,name,created,modified,description,x_mitre_version,x_mitre_deprecated) VALUES(:course_of_action_id,:loadTimestamp,:name,:created,:modified,:description,:x_mitre_version,:x_mitre_deprecated)";
				query = session.createNativeQuery(sql);
				query.setParameter("loadTimestamp", timestamp);
				query.setParameter("course_of_action_id", course_of_action_id);
				query.setParameter("name",courseOfAction.getName());
				query.setParameter("created", courseOfAction.getCreated());
				query.setParameter("modified", courseOfAction.getModified());
				query.setParameter("description", courseOfAction.getDescription());
				query.setParameter("x_mitre_version", courseOfAction.getX_mitre_version());
				query.setParameter("x_mitre_deprecated", courseOfAction.isX_mitre_deprecated());
				query.executeUpdate();
			}
			tx.commit();
			tx = session.beginTransaction();
			if (courseOfAction.getCreated_by_ref() != null) {
				//identity
				sql = "SELECT identity_id FROM H_IDENTITY WHERE collection_id=:collection_id AND id=:id";
				query = session.createNativeQuery(sql);
				query.setParameter("collection_id", collection_id);
				query.setParameter("id", courseOfAction.getCreated_by_ref().getId());
				Integer identity_id = null;
				try {
					identity_id = query.getSingleResult();
				} catch (NoResultException ex) {
					System.err.println("Identity for Course of Action "+courseOfAction.getId()+" not found!");
				}
				if (identity_id != null) {
					sql = "SELECT l_course_of_action_identity_id FROM L_COURSE_OF_ACTION_IDENTITY WHERE collection_id=:collection_id AND course_of_action_id=:course_of_action_id AND identity_id=:identity_id";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("course_of_action_id", course_of_action_id);
					query.setParameter("identity_id", identity_id);
					try {
						Integer l_course_of_action_identity_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_COURSE_OF_ACTION_IDENTITY (loadTimestamp,collection_id,course_of_action_id,identity_id) VALUES(:loadTimestamp,:collection_id,:course_of_action_id,:identity_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("course_of_action_id", course_of_action_id);
						query.setParameter("identity_id", identity_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			//marking definition
			if (courseOfAction.getObject_marking_refs() != null) {
				for (MarkingDefinition markingDefinition : courseOfAction.getObject_marking_refs()) {
					sql = "SELECT marking_definition_id FROM H_MARKING_DEFINITION WHERE id=:id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("id",markingDefinition.getId());
					query.setParameter("collection_id", collection_id);
					Integer marking_definition_id = null;
					try {
						marking_definition_id = query.getSingleResult();
					} catch (NoResultException ex) {
						System.err.println("Marking Definition for Course of Action "+courseOfAction.getId()+" not found!");
					}
					if (marking_definition_id != null) {
						sql = "SELECT l_course_of_action_marking_definition FROM L_COURSE_OF_ACTION_MARKING_DEFINITION WHERE collection_id=:collection_id AND marking_definition_id=:marking_definition_id AND course_of_action_id=:course_of_action_id";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("marking_definition_id", marking_definition_id);
						query.setParameter("course_of_action_id", course_of_action_id);
						try {
							Integer l_course_of_action_marking_definition_id = query.getSingleResult();
						} catch (NoResultException ex) {
							sql = "INSERT INTO L_COURSE_OF_ACTION_MARKING_DEFINITION (loadTimestamp,collection_id,marking_definition_id,course_of_action_id) VALUES(:loadTimestamp,:collection_id,:marking_definition_id,:course_of_action_id)";
							query = session.createNativeQuery(sql);
							query.setParameter("collection_id", collection_id);
							query.setParameter("marking_definition_id", marking_definition_id);
							query.setParameter("course_of_action_id", course_of_action_id);
							query.setParameter("loadTimestamp", timestamp);
							query.executeUpdate();
						}
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			//reference
			if (courseOfAction.getExternal_references() != null) {
				for (Reference reference : courseOfAction.getExternal_references()) {
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
					} catch (NoResultException ex) {
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
					sql = "SELECT l_course_of_actions_reference_id FROM L_COURSE_OF_ACTION_REFERENCE WHERE collection_id=:collection_id AND course_of_action_id=:course_of_action_id AND reference_id=:reference_id";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("course_of_action_id", course_of_action_id);
					query.setParameter("reference_id", reference_id);
					try {
						Integer l_course_of_actions_reference_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_COURSE_OF_ACTION_REFERENCE (collection_id,loadTimestamp,course_of_action_id,reference_id) VALUES(:collection_id,:loadTimestamp,:course_of_action_id,:reference_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("course_of_action_id", course_of_action_id);
						query.setParameter("reference_id", reference_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
		}
	}

}
