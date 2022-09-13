package com.sql.data.provider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.report.VulnerableComponent;

public class VulnerableComponentProvider extends PersistenceObjectProvider<VulnerableComponent>
implements PersistenceObjectProviderService<VulnerableComponent> {

	@Override
	public Class<VulnerableComponent> getClassName() {
		return VulnerableComponent.class;
	}
	
	public VulnerableComponent find(int id) {
		instantiateHibernateReportingSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		VulnerableComponent result = session.get(getClassName(), id);
		session.close();
		return result;
	}
	
	public void delete(VulnerableComponent obj) {
		instantiateHibernateReportingSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		session.delete(obj);
		session.close();
	}
	
	public void persist(VulnerableComponent obj) {
		instantiateHibernateReportingSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		session.save(obj);
		session.close();
	}
	
	public List<VulnerableComponent> getPersistedVulnerableComponentsForReportID(int reportID){
		super.instantiateHibernateReportingSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM vulnerableComponent WHERE reportID = :reportID";
		NativeQuery<VulnerableComponent> compQuery = session.createNativeQuery(sql, VulnerableComponent.class);
		compQuery.setParameter("reportID", reportID);
		List<VulnerableComponent> list = compQuery.list();
		session.close();
		return list;
	}
	
	public List<VulnerableComponent> getPersistedVulnerableComponentsForReportIDAndComponentID(int reportID, int componentID){
		super.instantiateHibernateReportingSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM vulnerableComponent WHERE reportID = :reportID AND componentID = :componentID";
		NativeQuery<VulnerableComponent> compQuery = session.createNativeQuery(sql, VulnerableComponent.class);
		compQuery.setParameter("reportID", reportID);
		compQuery.setParameter("componentID",componentID);
		List<VulnerableComponent> list = compQuery.list();
		session.close();
		return list;
	}
	
	public List<Long> getApplicationIdentifiersForCVE(String identifier){
		super.instantiateHibernateReportingSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		String sql = "SELECT DISTINCT swStackID FROM vulnerableComponent JOIN componentVulnerability USING (vulnCompID) WHERE identifierString=:identifier";
		NativeQuery<Object> idQuery = session.createNativeQuery(sql);
		try {
			List<Long> result = new ArrayList<Long>();
			idQuery.setParameter("identifier", identifier);
			List<Object> ids = idQuery.list();
			for (Object id : ids) {
				Long idL = ((Integer) id).longValue();
				result.add(idL);
			}
			session.close();
			return result;
		}catch(NoResultException ex) {
			return new ArrayList<Long>();
		}
	}

}
