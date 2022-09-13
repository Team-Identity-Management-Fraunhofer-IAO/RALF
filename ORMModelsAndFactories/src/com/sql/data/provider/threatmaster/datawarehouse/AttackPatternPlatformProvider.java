package com.sql.data.provider.threatmaster.datawarehouse;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import org.hibernate.Transaction;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternPlatform;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.XMitrePlatform;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class AttackPatternPlatformProvider extends PersistenceObjectProvider<AttackPatternPlatform> implements PersistenceObjectProviderService<AttackPatternPlatform>{

	@Override
	public Class<AttackPatternPlatform> getClassName() {
		return AttackPatternPlatform.class;
	}
	
	public void persist(AttackPatternPlatform obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		super.persist(obj);
	}
	
	public AttackPatternPlatform find(int id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		return super.find(id);
	}
	
	public void delete(AttackPatternPlatform obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		super.delete(obj);
	}
	
	public List<AttackPatternPlatform> getAllAttackPlatternPlatformAssignments(){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM V_ATTACK_PATTERN_PLATFORM ORDER BY x_mitre_platforms_collection_id";
		NativeQuery<AttackPatternPlatform> query = session.createNativeQuery(sql, AttackPatternPlatform.class);
		List<AttackPatternPlatform> attackPatternPlatformAssignments = query.list();
		session.close();
		return attackPatternPlatformAssignments;
	}
	
	public List<String> getDistinctPlatforms(String[] collectionIDs){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT DISTINCT(x_mitre_platforms_x_mitre_platform) FROM V_ATTACK_PATTERN_PLATFORM WHERE x_mitre_platforms_collection_id IN (:collections)";
		NativeQuery<String> query = session.createNativeQuery(sql);
		query.setParameterList("collections", collectionIDs);
		List<String> platforms = query.list();
		session.close();
		return platforms;
	}
	
	public void updatePlatformsForAttackPattern(int attack_pattern_id, List<String> platforms) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		for (String platform : platforms) {
			org.hibernate.Transaction tx = session.beginTransaction();
			String sql = "SELECT platforms_id FROM C_PLATFORMS where platform=:platform";
			NativeQuery<Integer> query = session.createNativeQuery(sql);
			query.setParameter("platform", platform);
			Integer platform_id = null;
			try {
				platform_id = query.getSingleResult();
			}catch (NoResultException ex) {
				sql = "INSERT INTO C_PLATFORMS (loadTimestamp,platform) VALUES(:loadTimestamp,:platform)";
				query = session.createNativeQuery(sql);
				query.setParameter("loadTimestamp", timestamp);
				query.setParameter("platform", platform);
				query.executeUpdate();
				sql = "SELECT LAST_INSERT_ID()";
				NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
				BigInteger last_platform_id = lastIDQuery.getSingleResult();
				platform_id = last_platform_id.intValueExact();
			}
			sql = "SELECT c_l_attack_pattern_platforms_id FROM C_L_ATTACK_PATTERN_PLATFORMS WHERE platforms_id=:platforms_id AND attack_pattern_id=:attack_pattern_id";
			query = session.createNativeQuery(sql);
			query.setParameter("platforms_id", platform_id);
			query.setParameter("attack_pattern_id", attack_pattern_id);
			try {
				Integer c_l_attack_pattern_platforms_id = query.getSingleResult();
			} catch (NoResultException ex) {
				sql = "INSERT INTO C_L_ATTACK_PATTERN_PLATFORMS (attack_pattern_id,platforms_id,loadTimestamp) VALUES(:attack_pattern_id,:platforms_id,:loadTimestamp)";
				query = session.createNativeQuery(sql);
				query.setParameter("attack_pattern_id", attack_pattern_id);
				query.setParameter("platforms_id", platform_id);
				query.setParameter("loadTimestamp",timestamp);
				query.executeUpdate();
			}
			tx.commit();
		}
		session.close();
	}
	
	public List<Integer> getCapabilityKillerIdsForAttackPattern(int attack_pattern_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT capability_killer_id FROM C_L_ATTACK_PATTERN_CAPABILITY_KILLER WHERE attack_pattern_id=:attack_pattern_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("attack_pattern_id",attack_pattern_id);
		List<Integer> killerIds = query.list();
		session.close();
		return killerIds;
	}
	
	public List<XMitrePlatform> getAllPlatformsInCollection(String collection_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM H_X_MITRE_PLATFORMS WHERE collection_id=:collection_id";
		NativeQuery<XMitrePlatform> query = session.createNativeQuery(sql, XMitrePlatform.class);
		query.setParameter("collection_id",collection_id);
		List<XMitrePlatform> platforms = query.list();
		session.close();
		return platforms;
	}
	
	public Map<String, List<String>> getPlatformsPerGroup(List<String> platform_names){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT from_platform,relationship_name FROM c_platform_ontology WHERE relationship='group' AND from_platform IN (:platform_names)";
		NativeQuery<Object[]> query = session.createNativeQuery(sql);
		query.setParameterList("platform_names", platform_names);
		List<Object[]> objects = query.list();
		Map<String,List<String>> platformPerGroup = new HashMap<String,List<String>>();
		for (Object[] object : objects) {
			String platform = (String) object[0];
			String group = (String) object[1];
			if (!platformPerGroup.containsKey(group)) {
				platformPerGroup.put(group, new ArrayList<String>());
			}
			platformPerGroup.get(group).add(platform);
		}
		session.close();
		return platformPerGroup;
	}

}
