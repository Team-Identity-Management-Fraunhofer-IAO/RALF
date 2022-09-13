package com.sql.data.provider.threatmaster;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.services.Service;
import com.sql.data.objects.persistence.threatmaster.services.ServiceExistenceProbability;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class ServiceProvider extends PersistenceObjectProvider<Service> implements PersistenceObjectProviderService<Service>{


	public Class<Service> getClassName(){
		return Service.class;
	}
	
	@Override
	public void persist(Service obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.persist(obj);
	}
	
	public void update(Service obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.update(obj);
		tx.commit();
	}
	
	public void persist(ServiceExistenceProbability obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
	}
	
	public ServiceExistenceProbability getLastExistenceProbabilityForService(int service_id, int organization_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM service_existence_probability WHERE service_id = :service_id AND organization_id = :organization_id ORDER BY loadTimestamp DESC LIMIT 1";
		NativeQuery<ServiceExistenceProbability> query = session.createNativeQuery(sql, ServiceExistenceProbability.class);
		query.setParameter("service_id", service_id);
		query.setParameter("organization_id", organization_id);
		ServiceExistenceProbability prob = null;
		try {
			prob = query.getSingleResult();
		}catch (NoResultException ex) {
			sql = "SELECT * FROM service_existence_probability WHERE service_id = -1 AND organization_id=:organization_id ORDER BY loadTimestamp DESC LIMIT 1";
			query = session.createNativeQuery(sql, ServiceExistenceProbability.class);
			query.setParameter("organization_id", organization_id);
			prob = query.getSingleResult();
		}
		session.close();
		return prob;
	}

	@Override
	public Service find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		return super.find(id);
	}
	
	public ServiceExistenceProbability findExistenceProbability(int service_existence_probability_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		ServiceExistenceProbability prob = session.find(ServiceExistenceProbability.class, service_existence_probability_id);
		session.close();
		return prob;
	}

	@Override
	public void delete(Service obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.delete(obj);
	}
	
	public List<Service> getAllServices(){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM service";
		NativeQuery<Service> query = session.createNativeQuery(sql, Service.class);
		List<Service> result = query.list();
		session.close();
		return result;
	}

}
