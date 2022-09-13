package com.sql.data.provider.threatmaster;



import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.organizations.RiskPhi;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class RiskPhiProvider extends PersistenceObjectProvider<RiskPhi> implements PersistenceObjectProviderService<RiskPhi>{
	public enum RiskPhiType {OCCURENCE_PROBABILITY,RISK};

	@Override
	public Class<RiskPhi> getClassName() {
		return RiskPhi.class;
	}
	
	@Override
	public void persist(RiskPhi obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	@Override
	public void delete(RiskPhi obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	@Override
	public RiskPhi find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		RiskPhi phi = session.find(RiskPhi.class, id);
		session.close();
		return phi;
	}
	
	public RiskPhi getLatestRiskPhiForOrganizationAndService(int organization_id, int service_id, RiskPhiType type) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		NativeQuery<RiskPhi> query = null;
		String sql = null;
		String risk_phi_type = null;
		if (type == RiskPhiType.OCCURENCE_PROBABILITY) {
			risk_phi_type = "occurence_probability";
		}else if (type == RiskPhiType.RISK) {
			risk_phi_type = "risk";
		}else {
			return null;
		}
		sql = "SELECT * FROM risk_phi WHERE organization_id=:organization_id AND service_id=:service_id AND risk_phi_type=:risk_phi_type ORDER BY loadTimestamp DESC LIMIT 1";
		query = session.createNativeQuery(sql, RiskPhi.class);
		query.setParameter("organization_id", organization_id);
		query.setParameter("service_id", service_id);
		query.setParameter("risk_phi_type",risk_phi_type);
		RiskPhi phi = null;
		try {
			phi = query.getSingleResult();
		} catch (NoResultException ex) {
			System.err.println("No phi value found for organization "+organization_id+" and service "+service_id);
		}
		session.close();
		return phi;
	}
	

}
