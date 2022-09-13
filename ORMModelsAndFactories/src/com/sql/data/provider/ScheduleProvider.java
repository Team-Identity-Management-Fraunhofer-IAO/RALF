package com.sql.data.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.aggregations.StackAssessmentsMetadata;
import com.sql.data.objects.persistence.report.schedule.AssessmentMetadata;
import com.sql.data.objects.persistence.report.schedule.Schedule;

public class ScheduleProvider extends PersistenceObjectProvider<Schedule>
		implements PersistenceObjectProviderService<Schedule> {
	private final Map<String, Integer> statuses = new HashMap<String, Integer>();
	
	public ScheduleProvider() {
		statuses.put("",-1);
		statuses.put("processing", 0);
		statuses.put("building_report",1);
		statuses.put("cleaning_up",2);
	}
	
	@Override
	public Schedule find(int id) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		Schedule fact = session.find(Schedule.class, id);
		session.close();
		return fact;
	}

	@Override
	public void persist(Schedule obj) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Schedule obj) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}

	public List<Schedule> getAllSchedules() {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM schedule";
		NativeQuery<Schedule> query = session.createNativeQuery(sql, Schedule.class);
		List<Schedule> result = query.list();
		session.close();
		return result;
	}

	public List<Schedule> getSchedulesForSWStackID(long swStackID) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM schedule WHERE swStackID=:swStackID";
		NativeQuery<Schedule> query = session.createNativeQuery(sql, Schedule.class);
		query.setParameter("swStackID", swStackID);
		List<Schedule> result = query.list();
		session.close();
		return result;
	}

	public StackAssessmentsMetadata getAssessmentsMetadataForSWStackID(long swStackID) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM assessment_metadata WHERE swStackID = :swStackID";
		NativeQuery<AssessmentMetadata> metadataQuery = session.createNativeQuery(sql, AssessmentMetadata.class);
		metadataQuery.setParameter("swStackID", swStackID);
		try {
			List<AssessmentMetadata> metadatas = metadataQuery.list();
			String currentStatus = "";
			for (AssessmentMetadata meta : metadatas) {
				if (statuses.get(meta.getStatus()) > statuses.get(currentStatus)) {
					currentStatus = meta.getStatus();
				}
			}
			session.close();
			StackAssessmentsMetadata data = new StackAssessmentsMetadata();
			data.setStatus(currentStatus);
			data.setSwStackID(swStackID);			
			return data;
		} catch (NoResultException ex) {
			session.close();
			StackAssessmentsMetadata data = new StackAssessmentsMetadata();
			data.setStatus("");
			data.setSwStackID(swStackID);
			return data;
		}
	}
	
	public void deleteAssessmentMetadataForScheduleID(int scheduleID) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM assessment_metadata WHERE scheduleID = :scheduleID";
		NativeQuery<AssessmentMetadata> metadataQuery = session.createNativeQuery(sql, AssessmentMetadata.class);
		metadataQuery.setParameter("scheduleID",scheduleID);
		try {
			AssessmentMetadata metadata = metadataQuery.getSingleResult();
			Transaction tx = session.beginTransaction();
			session.delete(metadata);
			tx.commit();
			session.close();
		}catch (NoResultException ex) {
			session.close();
		}
	}
	
	public void updateAssessmentMetadataForScheduleID(int scheduleID, String status, int swStackID) {
		instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM assessment_metadata WHERE scheduleID = :scheduleID";
		NativeQuery<AssessmentMetadata> metadataQuery = session.createNativeQuery(sql, AssessmentMetadata.class);
		metadataQuery.setParameter("scheduleID",scheduleID);
		try {
			AssessmentMetadata metadata = metadataQuery.getSingleResult();
			metadata.setStatus(status);
			Transaction tx = session.beginTransaction();
			session.save(metadata);
			tx.commit();
			session.close();
		}catch (NoResultException ex) {
			AssessmentMetadata metadata = new AssessmentMetadata();
			metadata.setScheduleID(scheduleID);
			metadata.setStatus(status);
			metadata.setSwStackID(swStackID);
			Transaction tx = session.beginTransaction();
			session.save(metadata);
			tx.commit();
			session.close();
		}
	}

	@Override
	public Class<Schedule> getClassName() {
		return Schedule.class;
	}

}
