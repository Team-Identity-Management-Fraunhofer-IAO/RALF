package com.sql.data.provider.threatmaster;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCapabilityKiller;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCapabilityKillerPK;
import com.sql.data.objects.persistence.threatmaster.risks.CapabilityKiller;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class CapabilityKillerProvider extends PersistenceObjectProvider<CapabilityKiller> implements PersistenceObjectProviderService<CapabilityKiller>{

	@Override
	public void persist(CapabilityKiller obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.persist(obj);
	}
	
	public void persistForBusinessRisk(CapabilityKiller obj, int business_risk_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		BusinessRiskCapabilityKiller bRiskCKiller = session.find(BusinessRiskCapabilityKiller.class, new BusinessRiskCapabilityKillerPK(obj.getCapability_killer_id(), business_risk_id));
		if (bRiskCKiller == null) {
			bRiskCKiller = new BusinessRiskCapabilityKiller();
			bRiskCKiller.setBusiness_risk_id(business_risk_id);
			bRiskCKiller.setCapability_killer_id(obj.getCapability_killer_id());
			Transaction tx = session.beginTransaction();
			session.persist(bRiskCKiller);
			tx.commit();
		}
		session.close();
	}
	
	public void persistForBusinessRisk(int capability_killer_id, int business_risk_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		BusinessRiskCapabilityKiller bRiskCKiller = session.find(BusinessRiskCapabilityKiller.class, new BusinessRiskCapabilityKillerPK(capability_killer_id, business_risk_id));
		if (bRiskCKiller == null) {
			bRiskCKiller = new BusinessRiskCapabilityKiller();
			bRiskCKiller.setBusiness_risk_id(business_risk_id);
			bRiskCKiller.setCapability_killer_id(capability_killer_id);
			Transaction tx = session.beginTransaction();
			session.persist(bRiskCKiller);
			tx.commit();
		}
		session.close();
	}

	@Override
	public CapabilityKiller find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		return find(id);
	}

	@Override
	public void delete(CapabilityKiller obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.delete(obj);		
	}
	
	public void deleteForBusinessRisk(CapabilityKiller obj, int business_risk_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		BusinessRiskCapabilityKiller bRiskCKiller = session.find(BusinessRiskCapabilityKiller.class, new BusinessRiskCapabilityKillerPK(obj.getCapability_killer_id(), business_risk_id));
		if (bRiskCKiller != null) {
			session.delete(bRiskCKiller);
		}
		session.close();
	}
	
	public void deleteForBusinessRisk(int capability_killer_id, int business_risk_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		BusinessRiskCapabilityKiller bRiskCKiller = session.find(BusinessRiskCapabilityKiller.class, new BusinessRiskCapabilityKillerPK(capability_killer_id, business_risk_id));
		if (bRiskCKiller != null) {
			session.delete(bRiskCKiller);
		}
		session.close();
	}

	@Override
	public Class<CapabilityKiller> getClassName() {
		return CapabilityKiller.class;
	}
	
	public List<CapabilityKiller> getAllCapabilityKillers(){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM capability_killer";
		NativeQuery<CapabilityKiller> query = session.createNativeQuery(sql, CapabilityKiller.class);
		List<CapabilityKiller> result = query.list();
		session.close();
		return result;
	}
	
	public List<CapabilityKiller> getAllCapabilityKillersForBusinessRisk(int business_risk_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT capability_killer_id, capability_killer_name FROM capability_killer JOIN business_risk_capability_killer USING (capability_killer_id) WHERE business_risk_capability_killer.business_risk_id = :business_risk_id";
		NativeQuery<CapabilityKiller> query = session.createNativeQuery(sql, CapabilityKiller.class);
		query.setParameter("business_risk_id", business_risk_id);
		List<CapabilityKiller> result = query.list();
		session.close();
		return result;
	}

}
