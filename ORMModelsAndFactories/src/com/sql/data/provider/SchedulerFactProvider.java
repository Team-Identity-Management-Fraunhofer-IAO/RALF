package com.sql.data.provider;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.report.schedule.SchedulerFact;

public class SchedulerFactProvider extends PersistenceObjectProvider<SchedulerFact> implements PersistenceObjectProviderService<SchedulerFact>{

	@Override
	public Class<SchedulerFact> getClassName() {
		return SchedulerFact.class;
	}
	
	@Override
	public SchedulerFact find(int id) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		SchedulerFact fact = session.find(SchedulerFact.class, id);
		session.close();
		return fact;		
	}
	
	@Override
	public void persist(SchedulerFact obj) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	@Override
	public void delete(SchedulerFact obj) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	public List<SchedulerFact> getAllSchedulerFacts() {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM scheduler_facts";
		NativeQuery<SchedulerFact> query = session.createNativeQuery(sql, SchedulerFact.class);
		List<SchedulerFact> result = query.list();
		session.close();
		return result;
	} 
	
	public List<SchedulerFact> getSchedulerFactsForStackId(int stackID) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM scheduler_facts WHERE swStackID = :swStackID";
		NativeQuery<SchedulerFact> query = session.createNativeQuery(sql, SchedulerFact.class);
		query.setParameter("swStackID", stackID);
		List<SchedulerFact> result = query.list();
		session.close();
		return result;
	} 

}
