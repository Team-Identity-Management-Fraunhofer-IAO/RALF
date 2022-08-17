package de.securityallies.data.Loader;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import com.sql.hibernate.HibernateThreatMasterLoaderInstance;

import de.securityallies.taxii2.Taxii2Client.data.stix.Identity;
import de.securityallies.taxii2.Taxii2Client.data.stix.MarkingDefinition;

public class IdentityLoader {
	
	private HibernateThreatMasterLoaderInstance threatMasterLoaderInstance;
	
	public void loadIdentities(List<Identity> identities, String collection_id, Timestamp timestamp) {
		Session session = threatMasterLoaderInstance.getSessionFactory().openSession();
		for (Identity identity : identities) {
			boolean insertHub = false;
			boolean insertSatellite = false;
			org.hibernate.Transaction tx = session.beginTransaction();
			String sql = "SELECT identity_id FROM H_IDENTITY WHERE id=:id AND collection_id=:collection_id";
			NativeQuery<Integer> query = session.createNativeQuery(sql);
			query.setParameter("id", identity.getId());
			query.setParameter("collection_id", collection_id);
			Integer identity_id = null;
			try {
				identity_id = query.getSingleResult();
			}catch (NoResultException ex) {
				insertHub = true;
			}
			if (insertHub) {
				sql = "INSERT INTO H_IDENTITY (id,collection_id,loadTimestamp) VALUES(:id,:collection_id,:loadTimestamp)";
				query = session.createNativeQuery(sql);
				query.setParameter("id", identity.getId());
				query.setParameter("collection_id", collection_id);
				query.setParameter("loadTimestamp", timestamp);
				query.executeUpdate();
				sql = "SELECT LAST_INSERT_ID()";
				NativeQuery<BigInteger> queryLastID = session.createNativeQuery(sql);
				BigInteger last_identity_id = queryLastID.getSingleResult();
				identity_id = last_identity_id.intValueExact();
			}
			sql = "SELECT identity_id FROM S_IDENTITY WHERE identity_id = :identity_id AND created = :created AND modified = :modified AND name = :name AND description = :description AND roles = :roles AND identity_class = :identity_class AND sectors = :sectors AND contact_information = :contact_information";
			query = session.createNativeQuery(sql);
			query.setParameter("identity_id", identity_id);
			query.setParameter("created", identity.getCreated());
			query.setParameter("modified", identity.getModified());
			query.setParameter("name", identity.getName());
			query.setParameter("description", identity.getDescription());
			query.setParameter("roles", identity.getRoles());
			query.setParameter("identity_class", identity.getIdentity_class());
			query.setParameter("sectors", identity.getSectors());
			query.setParameter("contact_information", identity.getContact_information());
			try {
				identity_id = query.getSingleResult();
			}catch (NoResultException ex) {
				insertSatellite = true;
			}
			if (insertSatellite) {
				sql = "INSERT INTO S_IDENTITY (identity_id,loadTimestamp,created,modified,name,description,roles,identity_class,sectors,contact_information) VALUES(:identity_id,:loadTimestamp,:created,:modified,:name,:description,:roles,:identity_class,:sectors,:contact_information)";
				query = session.createNativeQuery(sql);
				query.setParameter("identity_id", identity_id);
				query.setParameter("loadTimestamp", timestamp);
				query.setParameter("created", identity.getCreated());
				query.setParameter("modified", identity.getModified());
				query.setParameter("name", identity.getName());
				query.setParameter("description", identity.getDescription());
				query.setParameter("roles", identity.getRoles());
				query.setParameter("identity_class", identity.getIdentity_class());
				query.setParameter("sectors", identity.getSectors());
				query.setParameter("contact_information", identity.getContact_information());
				query.executeUpdate();
			}
			for (MarkingDefinition markingDefinition : identity.getObject_marking_refs()) {
				BigInteger marking_definition_id = null;				
				sql = "SELECT marking_definition_id FROM H_MARKING_DEFINITION WHERE id=:id AND collection_id=:collection_id";
				query = session.createNativeQuery(sql);
				query.setParameter("id", markingDefinition.getId());
				query.setParameter("collection_id", collection_id);
				try {
					marking_definition_id = BigInteger.valueOf(query.getSingleResult().intValue());
				}catch (NoResultException ex) {
					System.err.println("Referenced Marking Definition does not exist!");
				}
				if (marking_definition_id != null) {
					sql = "SELECT l_identity_marking_definition_id FROM L_IDENTITY_MARKING_DEFINITION WHERE identity_id = :identity_id AND marking_definition_id = :marking_definition_id AND collection_id = :collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("identity_id", identity_id);
					query.setParameter("marking_definition_id", marking_definition_id);
					query.setParameter("collection_id", collection_id);
					try {
						BigInteger l_identity_marking_definition_id = BigInteger.valueOf(query.getSingleResult().intValue());
					}catch (NoResultException ex) {
						sql ="INSERT INTO L_IDENTITY_MARKING_DEFINITION (identity_id,marking_definition_id,loadTimestamp,collection_id) VALUES(:identity_id,:marking_definition_id,:loadTimestamp,:collection_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("identity_id", identity_id);
						query.setParameter("marking_definition_id", marking_definition_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
		}
	}

}
