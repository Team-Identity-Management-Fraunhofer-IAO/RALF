package com.sql.data.provider.threatmaster;

import java.util.List;

import org.hibernate.Transaction;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.assessment.ControlReport;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControl;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControlExcludedThreats;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControlExcludedThreatsPK;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportPlatform;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class ControlReportProvider extends PersistenceObjectProvider<ControlReport> implements PersistenceObjectProviderService<ControlReport>{

	@Override
	public Class<ControlReport> getClassName() {
		return ControlReport.class;
	}

	@Override
	public ControlReport find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		return super.find(id);
	}
	
	public ControlReportControl findControlReportControl(int control_report_control_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		ControlReportControl control = session.find(ControlReportControl.class, control_report_control_id);
		session.close();
		return control;
	}
	
	public ControlReportControlExcludedThreats findControlReportControlExcludedThreat(int control_report_control_id, int threat_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		ControlReportControlExcludedThreatsPK pk = new ControlReportControlExcludedThreatsPK(control_report_control_id,threat_id);
		ControlReportControlExcludedThreats control = session.find(ControlReportControlExcludedThreats.class, pk);
		session.close();
		return control;
	}

	@Override
	public void delete(ControlReport obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.delete(obj);
	}
	
	public void delete(ControlReportControl obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		session.delete(obj);
		session.close();
	}
	
	public void delete(ControlReportControlExcludedThreats obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		session.delete(obj);
		session.close();
	}

	@Override
	public void persist(ControlReport obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.persist(obj);
	}
	
	public void persist(ControlReportControl obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(ControlReportControlExcludedThreats obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(ControlReportPlatform obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public List<ControlReportPlatform> getControlReportPlatformsByReport(int control_report_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM control_report_platforms WHERE control_report_id=:control_report_id";
		NativeQuery<ControlReportPlatform> query = session.createNativeQuery(sql, ControlReportPlatform.class);
		query.setParameter("control_report_id", control_report_id);
		List<ControlReportPlatform> platforms = query.list();
		session.close();
		return platforms;
	}
	
	public List<ControlReportControl> getControlReportControlsByReport(int control_report_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM control_report_control WHERE control_report_id=:control_report_id";
		NativeQuery<ControlReportControl> query = session.createNativeQuery(sql, ControlReportControl.class);
		query.setParameter("control_report_id", control_report_id);
		List<ControlReportControl> controls = query.list();
		session.close();
		return controls;
	}
	
	public List<ControlReportControlExcludedThreats> getExcludedThreatsByControlReportControl(int control_report_control_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM control_report_control_excludedThreats WHERE control_report_control_id=:control_report_control_id";
		NativeQuery<ControlReportControlExcludedThreats> query = session.createNativeQuery(sql, ControlReportControlExcludedThreats.class);
		query.setParameter("control_report_control_id",control_report_control_id);
		List<ControlReportControlExcludedThreats> threats = query.list();
		session.close();
		return threats;
	}

	
}
