package com.sql.data.provider.threatmaster.datawarehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactor;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactorQuestion;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestion;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.VulnerabilityEnablingFactor;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.IntrusionSet;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class AttackFactorProvider extends PersistenceObjectProvider<VulnerabilityEnablingFactor> implements PersistenceObjectProviderService<VulnerabilityEnablingFactor>{

	@Override
	public Class<VulnerabilityEnablingFactor> getClassName() {
		return VulnerabilityEnablingFactor.class;
	}
	
	public AttackFactorProvider() {
		super();
		
	}
	
	public void persist(AttackMotivatingFactor obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(AttackMotivatingFactorQuestion obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(VulnerabilityEnablingFactorQuestion obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public List<VulnerabilityEnablingFactor> getAllVulnerabilityEnablingFactors(){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM c_vulnerability_enabling_factor";
		NativeQuery<VulnerabilityEnablingFactor> query = session.createNativeQuery(sql, VulnerabilityEnablingFactor.class);
		List<VulnerabilityEnablingFactor> factors = query.list();
		session.close();
		return factors;
	}
	
	public Map<Integer, String> getVulnerabilityEnablingFactorNamesByID(List<Integer> vulnerabilityEnablingFactorIDs){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM c_vulnerability_enabling_factor WHERE c_vulnerability_enabling_factor_id IN :vulnerabilityEnablingFactorIDs";
		NativeQuery<VulnerabilityEnablingFactor> query = session.createNativeQuery(sql, VulnerabilityEnablingFactor.class);
		query.setParameterList("vulnerabilityEnablingFactorIDs", vulnerabilityEnablingFactorIDs);
		List<VulnerabilityEnablingFactor> factors = query.list();
		Map<Integer, String> factorNamesPerID = new HashMap<Integer, String>();
		for (VulnerabilityEnablingFactor factor : factors) {
			factorNamesPerID.put(factor.getC_vulnerability_enabling_factor_id(), factor.getVulnerability_enabling_factor_name());
		}
		session.close();
		return factorNamesPerID;
	}
	
	public List<AttackMotivatingFactor> getAllAttackMotivatingFactors(){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM c_attack_motivating_factor";
		NativeQuery<AttackMotivatingFactor> query = session.createNativeQuery(sql, AttackMotivatingFactor.class);
		List<AttackMotivatingFactor> factors = query.list();
		session.close();
		return factors;
	}
	
	public List<IntrusionSet> getIntrusionSetsForMotivatingFactorIDs(List<Integer> attack_motivating_factor_ids){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM s_intrusion_set JOIN c_l_attack_motivating_factor_intrusion_set USING (intrusion_set_id) WHERE attack_motivating_factor_id IN (:attack_motivating_factor_ids)";
		NativeQuery<IntrusionSet> query = session.createNativeQuery(sql, IntrusionSet.class);
		query.setParameterList("attack_motivating_factor_ids", attack_motivating_factor_ids);
		List<IntrusionSet> intrusion_sets = query.list();
		session.close();
		return intrusion_sets;
	}
	
	public List<IntrusionSet> getIntrusionSetsForVulnerabilityEnablingFactorIDs(List<Integer> vulnerability_enabling_factor_ids){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM s_intrusion_set JOIN c_l_vulnerability_enabling_factor_intrusion_set USING (intrusion_set_id) WHERE c_vulnerability_enabling_factor_id IN (:vulnerability_enabling_factor_ids)";
		NativeQuery<IntrusionSet> query = session.createNativeQuery(sql, IntrusionSet.class);
		query.setParameterList("vulnerability_enabling_factor_ids",vulnerability_enabling_factor_ids);
		List<IntrusionSet> intrusion_sets = query.list();
		session.close();
		return intrusion_sets;
	}

}
