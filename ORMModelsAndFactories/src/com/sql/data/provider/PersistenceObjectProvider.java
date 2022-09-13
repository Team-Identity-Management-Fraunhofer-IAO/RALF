package com.sql.data.provider;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.sql.hibernate.HibernateInstance;
import com.sql.hibernate.HibernateReportInstance;
import com.sql.hibernate.HibernateThreatMasterInstance;
import com.sql.hibernate.HibernateThreatMasterLoaderInstance;
import com.sql.hibernate.HibernateTransformationInstance;

public abstract class PersistenceObjectProvider<T> implements PersistenceObjectProviderService<T> {
	SessionFactory hibernateSessionFactory;

	public void instantiateHibernateSessionFactory() {
		if (hibernateSessionFactory == null) {
			hibernateSessionFactory = HibernateInstance.getSessionFactory();
		}
	}
	
	public void instantiateHibernateReportingSessionFactory() {
		if (hibernateSessionFactory == null) {
			hibernateSessionFactory = HibernateReportInstance.getSessionFactory();
		}
	}
	
	public void instantiateHibernateTransformationSessionFactory() {
		if (hibernateSessionFactory == null) {
			hibernateSessionFactory = HibernateTransformationInstance.getSessionFactory();
		}
	}
	
	public void instantiateHibernateThreatMasterSessionFactory() {
		if (hibernateSessionFactory == null) {
			hibernateSessionFactory = HibernateThreatMasterInstance.getSessionFactory();
		}
	}
	

	
	public void instantiateHibernateThreatMasterLoaderSessionFactory() {
		if (hibernateSessionFactory == null) {
			hibernateSessionFactory = HibernateThreatMasterLoaderInstance.getSessionFactory();
		}
	}
	
	public SessionFactory getHibernateSessionFactory() {
		return this.hibernateSessionFactory;
	}
	
	public T find(int id) {
		instantiateHibernateSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		T result = session.get(getClassName(), id);
		session.close();
		return result;
	}
	
	public void delete(T obj) {
		instantiateHibernateSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		session.delete(obj);
		session.close();
	}
	
	public void persist(T obj) {
		System.out.println("Persisting "+obj);
		instantiateHibernateSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		session.save(obj);
		tx.commit();
		System.out.println("Persisted "+obj);
		session.close();
	}

	public abstract Class<T> getClassName();
}
 