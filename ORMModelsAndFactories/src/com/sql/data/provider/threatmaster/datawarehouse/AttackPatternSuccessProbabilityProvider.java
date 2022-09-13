package com.sql.data.provider.threatmaster.datawarehouse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.helpers.attackpatterns.NamedThreat;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternPrerequisite;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternSuccessProbability;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.IntrusionSetAttackPattern;
import com.sql.data.objects.persistence.threatmaster.risks.SuccessProbability;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class AttackPatternSuccessProbabilityProvider extends PersistenceObjectProvider<AttackPatternSuccessProbability> implements PersistenceObjectProviderService<AttackPatternSuccessProbability>{

	@Override
	public void persist(AttackPatternSuccessProbability obj) {
		//super.instantiateHibernateThreatMasterLoaderSessionFactory();
		//super.persist(obj);		
	}

	@Override
	public AttackPatternSuccessProbability find(int id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		return super.find(id);
	}

	@Override
	public void delete(AttackPatternSuccessProbability obj) {
		//super.instantiateHibernateThreatMasterLoaderSessionFactory();
		//super.delete(obj);		
	}

	@Override
	public Class<AttackPatternSuccessProbability> getClassName() {
		return AttackPatternSuccessProbability.class;
	}
	
	public String getRefinedDescriptionForCourseOfAction(int attack_pattern_id, int course_of_action_id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT s_relationship.description FROM l_course_of_action_relationship JOIN l_attack_pattern_relationship USING (relationship_id) JOIN s_relationship USING (relationship_id) WHERE course_of_action_id=:course_of_action_id AND attack_pattern_id=:attack_pattern_id";
		NativeQuery<String> query = session.createNativeQuery(sql);
		query.setParameter("attack_pattern_id", attack_pattern_id);
		query.setParameter("course_of_action_id", course_of_action_id);
		String refinedDescription = null;
		try {
			refinedDescription = query.getSingleResult();
		} catch (NoResultException ex) {
			
		}
		session.close();
		return refinedDescription;
	}
	
	public List<AttackPatternSuccessProbability> getSuccessProbabilitiesForAttackPattern(int attack_pattern_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM V_ATTACK_PATTERN_SUCCESS_PROBABILITY WHERE attack_pattern_id = :attack_pattern_id ORDER BY c_success_probability_id";
		NativeQuery<AttackPatternSuccessProbability> query = session.createNativeQuery(sql, AttackPatternSuccessProbability.class);
		query.setParameter("attack_pattern_id", attack_pattern_id);
		List<AttackPatternSuccessProbability> probs = query.list();
		session.close();
		return probs;
	}
	
	public List<AttackPatternSuccessProbability> getAllSuccessProbabilitiesForCourseOfActions(List<Integer> course_of_action_ids){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM V_ATTACK_PATTERN_SUCCESS_PROBABILITY WHERE course_of_action_id IN (:course_of_action_ids)";
		NativeQuery<AttackPatternSuccessProbability> query = session.createNativeQuery(sql, AttackPatternSuccessProbability.class);
		query.setParameterList("course_of_action_ids", course_of_action_ids);
		List<AttackPatternSuccessProbability> probs = query.list();
		session.close();
		return probs;
	}
	
	public List<AttackPatternSuccessProbability> getAllSuccessProbabilities(){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM V_ATTACK_PATTERN_SUCCESS_PROBABILITY";
		NativeQuery<AttackPatternSuccessProbability> query = session.createNativeQuery(sql, AttackPatternSuccessProbability.class);
		List<AttackPatternSuccessProbability> probs = query.list();
		session.close();
		return probs;
	}
	
	public List<AttackPatternSuccessProbability> getSuccessProbabilitiesByIdList(List<Integer> c_success_probability_ids){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM V_ATTACK_PATTERN_SUCCESS_PROBABILITY WHERE c_success_probability_id IN (:c_success_probability_ids)";
		NativeQuery<AttackPatternSuccessProbability> query = session.createNativeQuery(sql, AttackPatternSuccessProbability.class);
		query.setParameterList("c_success_probability_ids", c_success_probability_ids);
		List<AttackPatternSuccessProbability> probs = query.list();
		session.close();
		return probs;
	}
	
	public List<AttackPatternSuccessProbability> getSuccessProbabilitiesByActionsPlatformsAndFactors(List<Integer> course_of_action_ids, List<Integer> x_mitre_platforms_ids, List<Integer> c_vulnerability_enabling_factor_ids){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT v1.* FROM V_ATTACK_PATTERN_SUCCESS_PROBABILITY v1 WHERE (v1.course_of_action_id IN (:course_of_action_ids) OR ISNULL(v1.course_of_action_id)) AND v1.x_mitre_platforms_id IN (:x_mitre_platforms_ids) AND (v1.c_vulnerability_enabling_factor_id IN (:c_vulnerability_enabling_factor_ids) OR ISNULL(v1.c_vulnerability_enabling_factor_id))";
		NativeQuery<AttackPatternSuccessProbability> query = session.createNativeQuery(sql, AttackPatternSuccessProbability.class);
		query.setParameterList("course_of_action_ids", course_of_action_ids);
		query.setParameterList("x_mitre_platforms_ids", x_mitre_platforms_ids);
		query.setParameterList("c_vulnerability_enabling_factor_ids",c_vulnerability_enabling_factor_ids);
		List<AttackPatternSuccessProbability> probs = query.list();
		session.close();
		return probs;		
	}
	
	public boolean isControlSetComplete(int sizeOfControlSet, int x_mitre_platforms_id, int c_success_probability_id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT COUNT(*) FROM V_ATTACK_PATTERN_SUCCESS_PROBABILITY WHERE c_success_probability_id = :c_success_probability_id AND x_mitre_platforms_id=:x_mitre_platforms_id";
		NativeQuery<BigInteger> query = session.createNativeQuery(sql);
		query.setParameter("c_success_probability_id", c_success_probability_id);
		query.setParameter("x_mitre_platforms_id", x_mitre_platforms_id);
		BigInteger controlSize = query.getSingleResult();
		session.close();
		return controlSize.intValueExact()==sizeOfControlSet?true:false;		
	}
	
	public NamedThreat getNamedThreatForAttackPatternId(int attack_pattern_id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT attack_pattern_id, name, description, loadTimestamp FROM S_ATTACK_PATTERN WHERE attack_pattern_id = :attack_pattern_id ORDER BY loadTimestamp DESC LIMIT 1";
		NativeQuery<Object[]> query = session.createNativeQuery(sql);
		query.setParameter("attack_pattern_id",attack_pattern_id);
		Object[] namedThreatObj = query.getSingleResult();
		NamedThreat namedThreat = new NamedThreat();
		namedThreat.setThreat_id((int) namedThreatObj[0]);
		namedThreat.setThreat_name((String) namedThreatObj[1]);
		namedThreat.setThreat_description((String) namedThreatObj[2]);
		session.close();
		return namedThreat;
	}
	
	/*
	 * Helper Functions to create pivoting threats
	 * 
	 */
	public void createThreatPivotsForCollection(String collection_id) {
		List<Integer> intrusionSets = getIntrusionSetIDsForCollection(collection_id);
		for (Integer intrusionSet : intrusionSets) {
			System.out.println("Pivoting Threats for Intrusion Set "+intrusionSet);
			pivotThreatsForIntrusionSetID(intrusionSet, collection_id);
			System.out.println("Done.");
		}
	}

	public List<Integer> getIntrusionSetIDsForCollection(String collection_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT intrusion_set_id FROM H_INTRUSION_SET WHERE collection_id=:collection_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("collection_id", collection_id);
		List<Integer> ids = query.list();
		session.close();
		return ids;
	}
	
	public void persistSuccessProbability(SuccessProbability prob, List<Integer> factorIDs, List<Integer> controlIDs) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(prob);
		for (Integer factorID : factorIDs) {
			String sql = "INSERT INTO L_SUCCESS_PROBABILITY_VULNERABILITY_ENABLING_FACTOR (c_success_probability_id,c_vulnerability_enabling_factor_id) VALUES(:success_probability_id,:vulnerability_enabling_factor_id)";
			NativeQuery query = session.createNativeQuery(sql);
			query.setParameter("success_probability_id",prob.getC_success_probability_id());
			query.setParameter("vulnerability_enabling_factor_id", factorID);
			query.executeUpdate();
		}
		for (Integer controlID : controlIDs) {
			String sql = "INSERT INTO L_SUCCESS_PROBABILITY_COURSE_OF_ACTION_EXISTENCE (c_success_probability_id,course_of_action_id) VALUES(:success_probability_id,:course_of_action_id)";
			NativeQuery query = session.createNativeQuery(sql);
			query.setParameter("success_probability_id",prob.getC_success_probability_id());
			query.setParameter("course_of_action_id",controlID);
			query.executeUpdate();
		}
		tx.commit();
		session.close();
	}
	
	public void deleteSuccessProbability(int c_success_probability_id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String sql = "DELETE FROM C_SUCCESS_PROBABILITY WHERE c_success_probability_id=:c_success_probability_id";
		NativeQuery query = session.createNativeQuery(sql);
		query.setParameter("c_success_probability_id", c_success_probability_id);
		query.executeUpdate();
		sql = "DELETE FROM L_SUCCESS_PROBABILITY_VULNERABILITY_ENABLING_FACTOR WHERE c_success_probability_id=:c_success_probability_id";
		query = session.createNativeQuery(sql);
		query.setParameter("c_success_probability_id", c_success_probability_id);
		query.executeUpdate();
		sql = "DELETE FROM L_SUCCESS_PROBABILITY_COURSE_OF_ACTION_EXISTENCE WHERE c_success_probability_id=:c_success_probability_id";
		query = session.createNativeQuery(sql);
		query.setParameter("c_success_probability_id", c_success_probability_id);
		query.executeUpdate();
		tx.commit();
		session.close();
	}
	
	public void pivotThreatsForIntrusionSetID(int intrusion_set_id, String collection_id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM V_INTRUSION_SET_ATTACK_PATTERN WHERE NOT attack_pattern_description LIKE '****This technique has been deprecated%' AND intrusion_set_id=:intrusion_set_id AND attack_pattern_collection_id=:collection_id";
		NativeQuery<IntrusionSetAttackPattern> query = session.createNativeQuery(sql, IntrusionSetAttackPattern.class);
		query.setParameter("collection_id",collection_id);
		query.setParameter("intrusion_set_id", intrusion_set_id);
		List<IntrusionSetAttackPattern> patterns = query.list();
		Map<Integer, List<IntrusionSetAttackPattern>> patternsByKillChainPhase = new HashMap<Integer,List<IntrusionSetAttackPattern>>();		
		for (IntrusionSetAttackPattern pattern : patterns) {
			sql = "SELECT kill_chain_order FROM L_ATTACK_PATTERN_KILL_CHAIN_PHASE JOIN C_KILL_CHAIN_PHASE_ORDER USING (kill_chain_phase_id) WHERE attack_pattern_id=:attack_pattern_id";
			NativeQuery<Integer> orderQuery = session.createNativeQuery(sql);
			orderQuery.setParameter("attack_pattern_id", pattern.getAttack_pattern_id());
			try {
				List<Integer> kill_chain_phase_orders = orderQuery.list();
				for (Integer phase_order : kill_chain_phase_orders) {
					if (!patternsByKillChainPhase.containsKey(phase_order)) {
						patternsByKillChainPhase.put(phase_order, new ArrayList<IntrusionSetAttackPattern>());						
					}
					patternsByKillChainPhase.get(phase_order).add(pattern);
				}
			}catch (NoResultException ex) {
				
			}
		}
		session.close();
		Set<Integer> orders = patternsByKillChainPhase.keySet();
		AttackPatternPrerequisiteProvider prereqProvider = new AttackPatternPrerequisiteProvider();
		for (Integer phase_order : patternsByKillChainPhase.keySet()) {
			for (Integer compare_order : orders) {
				if (compare_order <= phase_order) {
					continue;
				}else {
					List<IntrusionSetAttackPattern> patternListA = patternsByKillChainPhase.get(phase_order);
					List<IntrusionSetAttackPattern> patternListB = patternsByKillChainPhase.get(compare_order);
					for (IntrusionSetAttackPattern patternA : patternListA) {
						for (IntrusionSetAttackPattern patternB : patternListB) {
							if (patternA.getAttack_pattern_id() == patternB.getAttack_pattern_id()) {
								continue;
							}
							AttackPatternPrerequisite prereqA = new AttackPatternPrerequisite();
							prereqA.setAttack_pattern_id(patternB.getAttack_pattern_id());
							prereqA.setRequired_attack_pattern_id(patternA.getAttack_pattern_id());
							prereqA.setIntrusion_set_id(intrusion_set_id);
							prereqA.setCollection_id(collection_id);
							prereqProvider.persist(prereqA);
						}
					}
				}
				
			}
		}
		
	}
}
