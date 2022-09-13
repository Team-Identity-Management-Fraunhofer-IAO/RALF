package com.sql.data.provider.threatmaster;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;
import com.sql.data.objects.persistence.threatmaster.services.ServiceBusinessRisk;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class BusinessRiskProvider extends PersistenceObjectProvider<BusinessRisk> implements PersistenceObjectProviderService<BusinessRisk>{

	@Override
	public Class<BusinessRisk> getClassName() {
		return BusinessRisk.class;
	}

	@Override
	public BusinessRisk find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		return super.find(id);
	}

	@Override
	public void delete(BusinessRisk obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.delete(obj);
	}

	@Override
	public void persist(BusinessRisk obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.persist(obj);
	}
	
	public void update(BusinessRisk obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.update(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(BusinessRisk obj, int service_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM service_business_risk WHERE service_id = :service_id AND business_risk_id = :business_risk_id";
		NativeQuery<ServiceBusinessRisk> query = session.createNativeQuery(sql, ServiceBusinessRisk.class);
		query.setParameter("service_id",service_id);
		query.setParameter("business_risk_id",obj.getBusiness_risk_id());
		try {
			ServiceBusinessRisk service_business_risk = query.getSingleResult();
		} catch (NoResultException ex) {
			ServiceBusinessRisk service_business_risk = new ServiceBusinessRisk();
			service_business_risk.setBusiness_risk_id(obj.getBusiness_risk_id());
			service_business_risk.setService_id(service_id);
			Transaction tx = session.beginTransaction();
			session.persist(service_business_risk);
			tx.commit();
		}
		session.close();
	}
	
	public void deleteAllBusinessRiskAssociationsForService(int service_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM service_business_risk WHERE service_id = :service_id";
		NativeQuery<ServiceBusinessRisk> query = session.createNativeQuery(sql, ServiceBusinessRisk.class);
		query.setParameter("service_id",service_id);
		List<ServiceBusinessRisk> service_business_risks = query.list();
		if (service_business_risks != null) {
			Transaction tx = session.beginTransaction();
			for (ServiceBusinessRisk service_business_risk : service_business_risks) {
				session.delete(service_business_risk);
			}
			tx.commit();
		}
		session.close();
	}
	
	public List<BusinessRisk> getAllBusinessRisks(){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM business_risk";
		NativeQuery<BusinessRisk> query = session.createNativeQuery(sql, BusinessRisk.class);
		List<BusinessRisk> result = query.list();
		session.close();
		return result;
	}
	
	public List<BusinessRisk> getAllBusinessRisksForService(int service_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "(SELECT * FROM business_risk WHERE service_id = 0) UNION (SELECT * FROM business_risk WHERE service_id = :service_id)";
		NativeQuery<BusinessRisk> query = session.createNativeQuery(sql, BusinessRisk.class);
		query.setParameter("service_id", service_id);
		List<BusinessRisk> result = query.list();
		session.close();
		return result;
	}
	
	public List<BusinessRisk> getAssignedBusinessRisksForService(int service_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT b.business_risk_id, b.business_risk_name, b.business_risk_description, b.business_risk_impact, b.business_risk_default_order, b.service_id FROM business_risk AS b JOIN service_business_risk USING (business_risk_id) WHERE service_business_risk.service_id = :service_id";
		NativeQuery<BusinessRisk> query = session.createNativeQuery(sql, BusinessRisk.class);
		query.setParameter("service_id", service_id);
		List<BusinessRisk> result = query.list();
		session.close();
		return result;
	}
	
	public List<BusinessRisk> getBusinessRiskForServiceAndCapabilityKiller(int service_id, List<Integer> capability_killer_ids){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM business_risk WHERE service_id = :service_id AND business_risk_id IN (SELECT business_risk_id FROM business_risk_capability_killer WHERE capability_killer_id IN (:capability_killer_ids))";
		NativeQuery<BusinessRisk> query = session.createNativeQuery(sql, BusinessRisk.class);
		query.setParameter("service_id", service_id);
		query.setParameterList("capability_killer_ids", capability_killer_ids);
		List<BusinessRisk> risks = query.list();
		session.close();
		return risks;
	}
	
	public List<Integer> getAssignedCapabilityKillerIDsForBusinessRisk(int business_risk_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT capability_killer_id FROM business_risk_capability_killer WHERE business_risk_id = :business_risk_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("business_risk_id", business_risk_id);
		List<Integer> killerIDs = query.list();
		session.close();
		return killerIDs;
	}
	
	public List<Integer> getCapabilityKillerIDsForBusinessRisks(int service_id, List<Integer> capability_killer_ids){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT capability_killer_id FROM business_risk JOIN business_risk_capability_killer USING (business_risk_id) WHERE service_id=:service_id AND capability_killer_id IN (:capability_killer_ids)";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("service_id", service_id);
		query.setParameterList("capability_killer_ids", capability_killer_ids);
		List<Integer> killerIDs = query.list();
		session.close();
		return killerIDs;
	}

}
