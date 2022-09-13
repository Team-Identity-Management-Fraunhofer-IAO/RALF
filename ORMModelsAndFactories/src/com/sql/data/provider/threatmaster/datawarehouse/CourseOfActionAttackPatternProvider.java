package com.sql.data.provider.threatmaster.datawarehouse;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.helpers.attackpatterns.NamedControl;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.CourseOfActionAttackPattern;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.IntrusionSet;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.Malware;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.Reference;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class CourseOfActionAttackPatternProvider extends PersistenceObjectProvider<CourseOfActionAttackPattern> implements PersistenceObjectProviderService<CourseOfActionAttackPattern>{

	@Override
	public void persist(CourseOfActionAttackPattern obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		super.persist(obj);
	}

	@Override
	public CourseOfActionAttackPattern find(int id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		return super.find(id);
	}

	@Override
	public void delete(CourseOfActionAttackPattern obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		super.delete(obj);
		
	}

	@Override
	public Class<CourseOfActionAttackPattern> getClassName() {
		return CourseOfActionAttackPattern.class;
	}
	
	public List<CourseOfActionAttackPattern> getCoursesOfActionForCollection(String collection_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM V_COURSE_OF_ACTION_ATTACK_PATTERN WHERE attack_pattern_collection_id=:collection_id AND NOT attack_pattern_description LIKE '**This technique has been deprecated%'";
		NativeQuery<CourseOfActionAttackPattern> query = session.createNativeQuery(sql, CourseOfActionAttackPattern.class);
		query.setParameter("collection_id", collection_id);
		List<CourseOfActionAttackPattern> courseOfActionAttackPatterns = query.list();
		session.close();
		return courseOfActionAttackPatterns;
	}
	
	public List<CourseOfActionAttackPattern> getCoursesOfActionForPlatforms(String[] platforms, String[] collectionIDs){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT V_COURSE_OF_ACTION_ATTACK_PATTERN.* FROM V_COURSE_OF_ACTION_ATTACK_PATTERN JOIN V_ATTACK_PATTERN_PLATFORM USING (attack_pattern_id) JOIN S_ATTACK_PATTERN USING (attack_pattern_id) WHERE (NOT course_of_action_x_mitre_deprecated) AND (NOT S_ATTACK_PATTERN.description LIKE '**This technique has been deprecated%') AND V_ATTACK_PATTERN_PLATFORM.x_mitre_platforms_x_mitre_platform IN (:platforms) AND V_ATTACK_PATTERN_PLATFORM.x_mitre_platforms_collection_id IN (:collections)";
		NativeQuery<CourseOfActionAttackPattern> query = session.createNativeQuery(sql, CourseOfActionAttackPattern.class);
		query.setParameterList("platforms", platforms);
		query.setParameterList("collections", collectionIDs);
		List<CourseOfActionAttackPattern> courseOfActionAttackPatterns = query.list();
		session.close();
		return courseOfActionAttackPatterns;
	}
	
	public List<Integer> getPlatformIDsForNames(String[] platforms){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT DISTINCT x_mitre_platforms_id FROM V_ATTACK_PATTERN_PLATFORM WHERE x_mitre_platforms_x_mitre_platform IN (:platforms)";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameterList("platforms", platforms);
		List<Integer> result = query.list();
		session.close();
		return result;
	}
	
	public List<String> getAliasesForCourseOfActionId(int course_of_action_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT alias FROM C_L_COURSE_OF_ACTION_COURSE_OF_ACTION_ALIAS JOIN C_COURSE_OF_ACTION_ALIAS USING (course_of_action_alias_id) WHERE course_of_action_id=:course_of_action_id ORDER BY course_of_action_id,course_of_action_alias_id";
		NativeQuery<String> query = session.createNativeQuery(sql);
		query.setParameter("course_of_action_id", course_of_action_id);
		List<String> aliases = new ArrayList<String>();
		try {
			aliases = query.list();
		} catch (NoResultException ex) {
			
		}
		session.close();
		return aliases;
	}
	
	public List<Integer> getAttackPatternsForCourseOfActionId(int course_of_action_id,List<String> platform_names){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT attack_pattern_id FROM V_COURSE_OF_ACTION_ATTACK_PATTERN JOIN V_ATTACK_PATTERN_PLATFORM USING (attack_pattern_id) WHERE x_mitre_platforms_x_mitre_platform IN :platforms AND course_of_action_id=:course_of_action_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("course_of_action_id", course_of_action_id);
		query.setParameterList("platforms", platform_names);
		List<Integer> attack_pattern_ids = new ArrayList<Integer>();
		try {
			attack_pattern_ids = query.list();
		} catch (NoResultException ex) {
			
		}
		session.close();
		return attack_pattern_ids;
	}
	
	public List<Integer> filterAttackPatternsThroughPlatformIds(List<Integer> attack_pattern_ids, List<Integer> platform_ids){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT DISTINCT(attack_pattern_id) FROM V_ATTACK_PATTERN_PLATFORM WHERE attack_pattern_id IN :attack_pattern_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("attack_pattern_id", attack_pattern_ids);
		List<Integer> pattern_ids_in_db = new ArrayList<Integer>();
		pattern_ids_in_db = query.list();
		List<Integer> untouchable_patterns = new ArrayList<Integer>();
		if (pattern_ids_in_db.size() < attack_pattern_ids.size()) {
			for (Integer pattern_id : attack_pattern_ids) {
				if (!pattern_ids_in_db.contains(pattern_id)) {
					untouchable_patterns.add(pattern_id);
				}
			}			
		}		
		sql = "SELECT attack_pattern_id FROM L_ATTACK_PATTERN_X_MITRE_PLATFORMS WHERE attack_pattern_id IN :attack_pattern_id AND x_mitre_platforms_id IN :platform_ids";
		query = session.createNativeQuery(sql);
		query.setParameter("attack_pattern_id", pattern_ids_in_db);
		query.setParameter("platform_ids", platform_ids);
		List<Integer> pattern_ids = new ArrayList<Integer>();
		try {
			pattern_ids = query.list();
		} catch (NoResultException ex) {
			
		}
		session.close();
		pattern_ids.addAll(untouchable_patterns);
		return pattern_ids;
	}
	
	public List<IntrusionSet> getIntrusionSetForAttackPattternIDs(List<Integer> attack_pattern_ids){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM s_intrusion_set JOIN (SELECT DISTINCT(intrusion_set_id) AS intrusion_set_id FROM v_intrusion_set_attack_pattern WHERE attack_pattern_id IN (:attack_pattern_ids)) AS a1 USING (intrusion_set_id)";
		NativeQuery<IntrusionSet> query = session.createNativeQuery(sql, IntrusionSet.class);
		query.setParameterList("attack_pattern_ids", attack_pattern_ids);
		List<IntrusionSet> intrusion_sets = query.list();
		session.close();
		return intrusion_sets;		
	}
	
	public List<Malware> getMalwareForAttackPatternIDs(List<Integer> attack_pattern_ids){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT DISTINCT* FROM s_malware JOIN l_malware_relationship USING (malware_id) JOIN l_attack_pattern_relationship USING (relationship_id) WHERE attack_pattern_id IN (:attack_pattern_ids)";
		NativeQuery<Malware> query = session.createNativeQuery(sql, Malware.class);
		query.setParameterList("attack_pattern_ids", attack_pattern_ids);
		List<Malware> malware = query.list();
		session.close();
		return malware;
	}
	
	public List<Reference> getReferencesForIntrusionSetID(Integer intrusion_set_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM h_reference JOIN l_intrusion_set_reference USING (reference_id) WHERE intrusion_set_id = :intrusion_set_id";
		NativeQuery<Reference> query = session.createNativeQuery(sql, Reference.class);
		query.setParameter("intrusion_set_id", intrusion_set_id);
		List<Reference> references = query.list();
		session.close();
		return references;
	}
	
	public List<Reference> getReferencesForMalwareID(Integer malware_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM h_reference JOIN l_malware_reference USING (reference_id) WHERE malware_id = :malware_id";
		NativeQuery<Reference> query = session.createNativeQuery(sql, Reference.class);
		query.setParameter("malware_id",malware_id);
		List<Reference> references = query.list();
		session.close();
		return references;
	}
	
	public NamedControl getNamedControlForCourseOfActionId(int course_of_action_id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT course_of_action_id, name, description FROM S_COURSE_OF_ACTION WHERE course_of_action_id = :course_of_action_id ORDER BY loadTimestamp LIMIT 1";
		NativeQuery<Object[]> query = session.createNativeQuery(sql);
		query.setParameter("course_of_action_id", course_of_action_id);
		Object[] namedControlObj = query.getSingleResult();
		NamedControl namedControl = new NamedControl();
		namedControl.setControl_id((int) namedControlObj[0]);
		namedControl.setControl_name((String) namedControlObj[1]);
		namedControl.setControl_description((String) namedControlObj[2]);
		session.close();
		return namedControl;
	}

}
