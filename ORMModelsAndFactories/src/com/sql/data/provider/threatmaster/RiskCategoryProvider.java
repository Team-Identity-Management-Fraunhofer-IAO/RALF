package com.sql.data.provider.threatmaster;



import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.organizations.RiskCategory;
import com.sql.data.objects.persistence.threatmaster.organizations.RiskCategoryBundle;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class RiskCategoryProvider extends PersistenceObjectProvider<RiskCategory> implements PersistenceObjectProviderService<RiskCategory>{
	public static enum RiskCategoryType {Risk,Impact,Probability};

	@Override
	public Class<RiskCategory> getClassName() {
		return RiskCategory.class;
	}
	
	@Override
	public void persist(RiskCategory obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(RiskCategoryBundle obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	@Override
	public void delete(RiskCategory obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	@Override
	public RiskCategory find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		RiskCategory riskCategory = session.find(RiskCategory.class, id);
		session.close();
		return riskCategory;
	}
	
	public List<RiskCategory> getRiskCategoriesForServiceAndOrganization(int organization_id, int service_id, RiskCategoryType type){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = null;
		NativeQuery<RiskCategoryBundle> bundleQuery = null;
		if (service_id == -1) {
			sql = "SELECT * FROM risk_category_bundle WHERE organization_id=:organization_id AND service_id=-1 ORDER BY loadTimestamp DESC LIMIT 1";
			bundleQuery = session.createNativeQuery(sql,RiskCategoryBundle.class);
			bundleQuery.setParameter("organization_id", organization_id);
		}else {
			sql = "SELECT * FROM risk_category_bundle WHERE organization_id=:organization_id AND service_id = :service_id ORDER BY loadTimestamp DESC LIMIT 1";
			bundleQuery = session.createNativeQuery(sql,RiskCategoryBundle.class);
			bundleQuery.setParameter("organization_id", organization_id);
			bundleQuery.setParameter("service_id", service_id);
		}
		RiskCategoryBundle bundle = null;
		try {
			bundle = bundleQuery.getSingleResult();
		}catch(NoResultException ex) {
			System.err.println("No Risk Bundle for Organization "+organization_id+" and Service "+service_id+" found!");
			session.close();
			return null;
		}
		String parameterType = null;
		switch (type) {
			case Risk:
				parameterType="risk";
				break;
			case Impact:
				parameterType="impact";
				break;
			case Probability:
				parameterType="probability";
				break;			
		}
		sql = "SELECT * FROM risk_category WHERE risk_category_type = :type AND risk_category_bundle_id = :bundle_id ORDER BY risk_value_min ASC";
		NativeQuery<RiskCategory> query = session.createNativeQuery(sql, RiskCategory.class);
		query.setParameter("bundle_id", bundle.getRisk_category_bundle_id());
		query.setParameter("type", parameterType);
		List<RiskCategory> categories = query.list();
		session.close();
		return categories;
	}
	
	

}
