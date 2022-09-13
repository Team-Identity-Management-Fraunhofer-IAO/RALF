package com.sql.data.provider.threatmaster.datawarehouse;

import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sql.data.objects.helpers.attackpatterns.OrderedKillChainPhase;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternPrerequisite;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class AttackPatternPrerequisiteProvider extends PersistenceObjectProvider<AttackPatternPrerequisite> implements PersistenceObjectProviderService<AttackPatternPrerequisite>{

	@Override
	public Class<AttackPatternPrerequisite> getClassName() {
		return AttackPatternPrerequisite.class;
	}
	
	public void persist(AttackPatternPrerequisite obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public AttackPatternPrerequisite find(int id) {
		super.instantiateHibernateReportingSessionFactory();
		return super.find(id);
	}
	
	public void delete(AttackPatternPrerequisite obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	public List<AttackPatternPrerequisite> getRequiredPatternsForAttackPattern(int attack_pattern_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM C_ATTACK_PATTERN_PREREQUISITE WHERE attack_pattern_id=:attack_pattern_id";
		NativeQuery<AttackPatternPrerequisite> query = session.createNativeQuery(sql, AttackPatternPrerequisite.class);
		query.setParameter("attack_pattern_id", attack_pattern_id);
		List<AttackPatternPrerequisite> prereqs = query.list();
		session.close();
		return prereqs;
	}
	
	public List<OrderedKillChainPhase> getKillChainPhasesForAttackPattern(int attack_pattern_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT kill_chain_name, kill_chain_order, phase_name FROM C_KILL_CHAIN_PHASE_ORDER JOIN	H_KILL_CHAIN_PHASE USING (kill_chain_phase_id) JOIN	l_attack_pattern_kill_chain_phase USING	(kill_chain_phase_id) WHERE attack_pattern_id = :attack_pattern_id";
		NativeQuery<Object[]> query = session.createNativeQuery(sql);
		query.setParameter("attack_pattern_id", attack_pattern_id);
		List<Object[]> kill_chains = query.list();
		List<OrderedKillChainPhase> phases = new ArrayList<OrderedKillChainPhase>();
		for (Object[] kill_chain : kill_chains) {
			OrderedKillChainPhase kill_chain_phase = new OrderedKillChainPhase();
			kill_chain_phase.setKill_chain_name((String) kill_chain[0]);
			kill_chain_phase.setKill_chain_order((Integer) kill_chain[1]);
			kill_chain_phase.setPhase_name((String) kill_chain[2]);
			phases.add(kill_chain_phase);
		}
		return phases;
	}
	
	public List<Integer> getCapabilityKillerIDsForAttackPatternID(int attack_pattern_id){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT capability_killer_id FROM c_l_attack_pattern_capability_killer WHERE attack_pattern_id=:attack_pattern_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("attack_pattern_id", attack_pattern_id);
		List<Integer> killerIDs = query.list();
		session.close();
		return killerIDs;
	}

}
