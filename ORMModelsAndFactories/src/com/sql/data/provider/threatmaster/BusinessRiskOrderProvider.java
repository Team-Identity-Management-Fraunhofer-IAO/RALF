package com.sql.data.provider.threatmaster;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrder;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrderList;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrderListPK;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskPairwiseComparison;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class BusinessRiskOrderProvider extends PersistenceObjectProvider<BusinessRiskCustomOrder>
		implements PersistenceObjectProviderService<BusinessRiskCustomOrder> {

	@Override
	public void persist(BusinessRiskCustomOrder obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.persist(obj);
	}

	public void persist(BusinessRiskCustomOrderList obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}
	
	public void updateBusinessRiskCustomOrderList(BusinessRiskCustomOrderList obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.update(obj);
		tx.commit();
		session.close();
	}

	public void persist(BusinessRiskPairwiseComparison obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	@Override
	public BusinessRiskCustomOrder find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		return super.find(id);
	}

	public BusinessRiskCustomOrder findForService(int service_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM business_risk_custom_order WHERE service_id = :service_id";
		NativeQuery<BusinessRiskCustomOrder> query = session.createNativeQuery(sql, BusinessRiskCustomOrder.class);
		query.setParameter("service_id", service_id);
		BusinessRiskCustomOrder result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {

		}
		return result;
	}

	public List<BusinessRiskCustomOrderList> getCustomOrderList(int order_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM business_risk_custom_order_list WHERE order_id = :order_id ORDER BY weight DESC";
		NativeQuery<BusinessRiskCustomOrderList> query = session.createNativeQuery(sql,
				BusinessRiskCustomOrderList.class);
		query.setParameter("order_id", order_id);
		List<BusinessRiskCustomOrderList> result = query.list();
		session.close();
		return result;
	}
	
	public BusinessRiskCustomOrderList getCustomOrderList(int order_id, int business_risk_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		BusinessRiskCustomOrderList customOrderList = session.find(BusinessRiskCustomOrderList.class, new BusinessRiskCustomOrderListPK(order_id,business_risk_id));
		session.close();
		return customOrderList;
	}

	public List<BusinessRiskPairwiseComparison> getBusinessRiskPairwiseComparisons(int service_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM business_risk_pairwise_comparison WHERE service_id = :service_id";
		NativeQuery<BusinessRiskPairwiseComparison> query = session.createNativeQuery(sql,
				BusinessRiskPairwiseComparison.class);
		query.setParameter("service_id", service_id);
		List<BusinessRiskPairwiseComparison> result = query.list();
		session.close();
		return result;
	}

	@Override
	public void delete(BusinessRiskCustomOrder obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.delete(obj);
	}

	public void clearBusinessRiskPairwiseComparisonList(int service_id) {
		List<BusinessRiskPairwiseComparison> pairwiseComparisonList = this
				.getBusinessRiskPairwiseComparisons(service_id);
		BusinessRiskCustomOrder businessCustomOrder = this.findForService(service_id);
		if (!(businessCustomOrder == null)) {
			List<BusinessRiskCustomOrderList> businessRiskCustomOrderList = this
					.getCustomOrderList(businessCustomOrder.getOrder_id());
			Session session = super.getHibernateSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			for (BusinessRiskPairwiseComparison pairwiseComparison : pairwiseComparisonList) {
				session.delete(pairwiseComparison);
			}
			tx.commit();
			tx = session.beginTransaction();
			for (BusinessRiskCustomOrderList customOrderList : businessRiskCustomOrderList) {
				session.delete(customOrderList);
			}
			session.delete(businessCustomOrder);
			tx.commit();
			session.close();
		}
	}

	public Class<BusinessRiskCustomOrder> getClassName() {
		return BusinessRiskCustomOrder.class;
	}

}
