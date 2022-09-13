package com.sql.data.provider.threatmaster;

import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

import org.hibernate.Session;

import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReport;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportCapabilityKillerSuccessProbability;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreat;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreatControl;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreatControlPK;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreatControlPivotedThreat;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreatControlPivotedThreatPK;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreatPK;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class RiskReportProvider extends PersistenceObjectProvider<RiskReport> implements PersistenceObjectProviderService<RiskReport>{

	public RiskReport find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		return super.find(id);
	}
	
	public RiskReportThreat find(RiskReportThreatPK id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		RiskReportThreat obj = session.find(RiskReportThreat.class, id);
		session.close();
		return obj;
	}
	
	public List<RiskReport> getRiskReportsForService(int service_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM risk_report WHERE service_id=:service_id";
		NativeQuery<RiskReport> query = session.createNativeQuery(sql, RiskReport.class);
		query.setParameter("service_id", service_id);
		List<RiskReport> reports = query.list();
		session.close();
		return reports;
	}
	
	public List<RiskReportThreat> getThreatsForRiskReport(int risk_report_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM risk_report_threat WHERE risk_report_id=:risk_report_id";
		NativeQuery<RiskReportThreat> query = session.createNativeQuery(sql, RiskReportThreat.class);
		query.setParameter("risk_report_id", risk_report_id);
		List<RiskReportThreat> threats = query.list();
		session.close();
		return threats;
	}
	
	public List<BusinessRisk> getBusinessRisksForRiskReport(int risk_report_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT business_risk.* FROM business_risk JOIN risk_report_business_risk USING (business_risk_id) WHERE risk_report_id=:risk_report_id";
		NativeQuery<BusinessRisk> query = session.createNativeQuery(sql, BusinessRisk.class);
		query.setParameter("risk_report_id", risk_report_id);
		List<BusinessRisk> risks = query.list();
		session.close();
		return risks;
	}
	
	public List<BusinessRisk> getBusinessRisksNotInReport(int risk_report_id, int service_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT business_risk.* FROM	business_risk JOIN service_business_risk USING (business_risk_id) WHERE service_business_risk.service_id = :service_id AND business_risk_id NOT IN (SELECT business_risk_id FROM risk_report_business_risk WHERE risk_report_id = :risk_report_id)";
		NativeQuery<BusinessRisk> query = session.createNativeQuery(sql, BusinessRisk.class);
		query.setParameter("risk_report_id", risk_report_id);
		query.setParameter("service_id", service_id);
		List<BusinessRisk> risks = query.list();
		session.close();
		return risks;
	}
	
	
	
	public RiskReportThreatControl find(RiskReportThreatControlPK id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		RiskReportThreatControl obj = session.find(RiskReportThreatControl.class, id);
		session.close();
		return obj;
	}
	
	public RiskReportThreatControlPivotedThreat findPivotedThreat(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		RiskReportThreatControlPivotedThreat obj = session.find(RiskReportThreatControlPivotedThreat.class, id);
		session.close();
		return obj;
	}
	
	public List<RiskReportThreatControlPivotedThreat> getPivotedThreatsForRiskReport(int risk_report_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM risk_report_threat_control_pivoted_threat WHERE risk_report_id=:risk_report_id";
		NativeQuery<RiskReportThreatControlPivotedThreat> query = session.createNativeQuery(sql, RiskReportThreatControlPivotedThreat.class);
		query.setParameter("risk_report_id", risk_report_id);
		List<RiskReportThreatControlPivotedThreat> threats = query.list();
		session.close();
		return threats;
	}
	
	public List<RiskReportCapabilityKillerSuccessProbability> getRiskReportCapabilityKillerSuccessProbabilities(int risk_report_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM risk_report_capability_killer_success_probability WHERE risk_report_id=:risk_report_id";
		NativeQuery<RiskReportCapabilityKillerSuccessProbability> query = session.createNativeQuery(sql, RiskReportCapabilityKillerSuccessProbability.class);
		query.setParameter("risk_report_id", risk_report_id);
		List<RiskReportCapabilityKillerSuccessProbability> probs = query.list();
		session.close();
		return probs;
	}
	
	public List<Integer> getControlsForRiskReportThreat(int threat_id, int risk_report_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT control_id FROM risk_report_threat_control WHERE risk_report_id=:risk_report_id AND threat_id=:threat_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("threat_id", threat_id);
		query.setParameter("risk_report_id", risk_report_id);
		List<Integer> control_ids = query.list();
		session.close();
		return control_ids;
	}
	
	@Override
	public Class<RiskReport> getClassName() {
		return RiskReport.class;
	}
	
	public void persist(RiskReport obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.persist(obj);
	}
	
	public void update(RiskReport obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(RiskReportThreat obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(RiskReportThreatControl obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(RiskReportThreatControlPivotedThreat obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(RiskReportCapabilityKillerSuccessProbability obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void persistBusinessRiskAssignment(int risk_report_id, int business_risk_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "INSERT INTO risk_report_business_risk (risk_report_id,business_risk_id) VALUES (:risk_report_id,:business_risk_id)";
		NativeQuery query = session.createNativeQuery(sql);
		query.setParameter("risk_report_id", risk_report_id);
		query.setParameter("business_risk_id", business_risk_id);		
		Transaction tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.close();
	}
	
	public List<Integer> getExcludedThreatsForReport(int risk_report_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT threat_id FROM risk_report_excluded_threat WHERE risk_report_id=:risk_report_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("risk_report_id", risk_report_id);
		List<Integer> excluded_threat_ids = query.list();
		session.close();
		return excluded_threat_ids;
	}
	
	public void persistExcludedThreat(int risk_report_id, int threat_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "INSERT INTO risk_report_excluded_threat (risk_report_id,threat_id) VALUES(:risk_report_id,:threat_id)";
		NativeQuery query = session.createNativeQuery(sql);
		query.setParameter("risk_report_id", risk_report_id);
		query.setParameter("threat_id", threat_id);
		Transaction tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.close();
	}
	
	public void deleteExcludedThreat(int risk_report_id, int threat_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "DELETE FROM risk_report_excluded_threat WHERE risk_report_id = :risk_report_id AND threat_id = :threat_id";
		NativeQuery query = session.createNativeQuery(sql);
		query.setParameter("risk_report_id", risk_report_id);
		query.setParameter("threat_id", threat_id);
		Transaction tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.close();
	}

}
