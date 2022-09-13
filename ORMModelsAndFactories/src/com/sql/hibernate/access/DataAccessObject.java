package com.sql.hibernate.access;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sql.data.objects.persistence.PersistentObjectInterface;
import com.sql.hibernate.HibernateInstance;
import com.sql.hibernate.HibernateReportInstance;
import com.sql.hibernate.HibernateTransformationInstance;

public class DataAccessObject {
	private PersistentObjectInterface transientObject;
	SessionFactory hibernateSessionFactory;
	
	public enum Databases{
		DATAWAREHOUSE,
		REPORTING,
		TRANSFORMATION
	};

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
	
	public DataAccessObject(PersistentObjectInterface transientObject, Databases operatingMode) {
		this.transientObject = transientObject;
		switch (operatingMode) {
			case DATAWAREHOUSE : 
				instantiateHibernateSessionFactory();
				break;
			case REPORTING:
				instantiateHibernateReportingSessionFactory();
				break;
			case TRANSFORMATION:
				instantiateHibernateTransformationSessionFactory();
				break;
		}
	}
	
	public void persist() {
		if (transientObject != null) {
			Session session = hibernateSessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			if (transientObject.getIdentifier() == 0) {
				session.save(transientObject);				
			}else {
				PersistentObjectInterface persistentObject = (PersistentObjectInterface) session.find(transientObject.getImplementingClass(), transientObject.getIdentifier());
				persistentObject.overwriteWithTransientObject(transientObject);				
			}
			tx.commit();
			session.close();
		}
	}
	
	

}
