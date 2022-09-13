package com.sql.data.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import de.securityallies.sql.data.objects.persistence.transformationengine.FileUpload;
import de.securityallies.sql.data.objects.persistence.transformationengine.Operator;
import de.securityallies.sql.data.objects.persistence.transformationengine.Request;
import de.securityallies.sql.data.objects.persistence.transformationengine.RequestHeaderProperty;
import de.securityallies.sql.data.objects.persistence.transformationengine.TransformationOperator;
import de.securityallies.sql.data.objects.persistence.transformationengine.TransformationOrder;
import de.securityallies.sql.data.objects.persistence.transformationengine.TransformationRule;
import de.securityallies.sql.data.objects.persistence.transformationengine.TransformationSchedule;

public class TransformationOrderProvider extends PersistenceObjectProvider<TransformationOrder> implements PersistenceObjectProviderService<TransformationOrder>{

	@Override
	public Class<TransformationOrder> getClassName() {
		return TransformationOrder.class;
	}
	
	public TransformationOrder find(int id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		TransformationOrder result = session.get(TransformationOrder.class, id);
		session.close();
		return result;
	}
	
	public void delete(TransformationOrder obj) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	public void delete(TransformationOperator obj) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	public void delete(Request obj) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	public void delete(Operator obj) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	public void delete(TransformationRule obj) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	public void delete(FileUpload obj) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
		session.close();
	}
	
	public void persist(TransformationOrder obj) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if (obj.getOperator_id() == 0) {
			session.save(obj);
		}else {
			session.saveOrUpdate(obj);
		}
		tx.commit();
		session.close();
	}
	
	public Map<Integer, String> getAllOrderIDsAndNames(){
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		String sql = "SELECT DISTINCT(transformation_order_id), transformation_order_name FROM transformation_order";
		NativeQuery<Object[]> query = session.createNativeQuery(sql);
		List<Object[]> idsAndNames = query.list();
		Map<Integer, String> allOrderIDsAndNames = new HashMap<Integer, String>();
		for (Object[] line : idsAndNames) {
			allOrderIDsAndNames.put((Integer) line[0], (String) line[1]);
		}
		return allOrderIDsAndNames;
	}	
	
	public String getNameForOrderID(int order_id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		String sql = "SELECT DISTINCT(transformation_order_name) FROM transformation_order WHERE transformation_order_id = :order_id";
		NativeQuery<Object> query = session.createNativeQuery(sql);
		query.setParameter("order_id", order_id);
		String name = (String) query.getSingleResult();
		session.close();
		return name;
	}
	
	public Integer getMaxOrderID() {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		String sql = "SELECT MAX(transformation_order_id) FROM transformation_order";
		NativeQuery<Object> query = session.createNativeQuery(sql);
		Integer maxOrderID = (Integer) query.getSingleResult();
		session.close();
		return maxOrderID;
	}
	
	public void reOrderOperator(int order, int operator_id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		TransformationOrder transformationOrder = session.find(TransformationOrder.class, operator_id);
		transformationOrder.setTransformation_sequence(order);
		session.saveOrUpdate(transformationOrder);
		tx.commit();
		session.close();
	}
	
	public TransformationOperator getTransformationOperator(int id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		TransformationOperator metaOperator = session.get(TransformationOperator.class, id);
		session.close();
		return metaOperator;
	}
	
	public void persistRequest(Request req) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if (req.getOperator_id() == 0) {
			session.save(req);
		}else {
			session.saveOrUpdate(req);
		}
		tx.commit(); 
		session.close();
	}
	
	public Request getRequest(int id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Request req = session.get(Request.class, id);
		session.close();
		return req;
	}
	
	public void persistOperator(Operator op) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if (op.getOperator_id() == 0) {
			session.save(op);
		}else {
			session.saveOrUpdate(op);
		}
		tx.commit(); 
		session.close();
	}
	
	public Operator getOperator(int id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Operator op = session.get(Operator.class, id);
		session.close();
		return op;
	}
	
	public void persistRequestHeaderProperty(RequestHeaderProperty prop) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if (prop.getHeader_property_id() == 0) {
			session.save(prop);
		}else {
			session.saveOrUpdate(prop);
		}
		tx.commit(); 
		session.close();
	}
	
	public RequestHeaderProperty getRequestHeaderProperty(int id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		RequestHeaderProperty op = session.get(RequestHeaderProperty.class, id);
		session.close();
		return op;
	}
	
	public List<RequestHeaderProperty> getRequestHeaderPropertyForOperatorId(int operatorId){
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM request_header_property WHERE operator_id = :operator_id";
		NativeQuery<RequestHeaderProperty> propQuery = session.createNativeQuery(sql, RequestHeaderProperty.class);
		propQuery.setParameter("operator_id", operatorId);
		List<RequestHeaderProperty> properties = propQuery.list();
		session.close();
		return properties;
	}
	
	public void persistTransformationRule(TransformationRule rule) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if (rule.getOperator_id() == 0) {
			session.save(rule);
		}else {
			session.saveOrUpdate(rule);
		}
		tx.commit(); 
		session.close();
	}
	
	public TransformationRule getTransformationRule(int id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		TransformationRule op = session.get(TransformationRule.class, id);
		session.close();
		return op;
	}
	
	public void persistTransformationOperator(TransformationOperator op) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(op);
		tx.commit(); 
		session.close();
	}
	
	public FileUpload getFileUpload(int id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		FileUpload op = session.get(FileUpload.class, id);
		session.close();
		return op;
	}
	
	public void persistFileUpload(FileUpload fi) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(fi);
		tx.commit(); 
		session.close();
	}
	
	public TransformationSchedule getTransformationSchedule(int transformation_order_id) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		TransformationSchedule schedule = session.get(TransformationSchedule.class, transformation_order_id);
		session.close();
		return schedule;
	}
	
	public List<TransformationSchedule> getAllTransformationSchedules(){
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM transformation_schedule;";
		NativeQuery<TransformationSchedule> query = session.createNativeQuery(sql, TransformationSchedule.class);
		List<TransformationSchedule> result = query.list();
		session.close();
		return result;
	}
	
	public void persistTransformationSchedule(TransformationSchedule sched, boolean update) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if (!update) {
			session.save(sched);
		}else {
			session.saveOrUpdate(sched);
		}
		tx.commit();
		session.close();
	}
	
	
	public void deleteTransformationSchedule(TransformationSchedule sched) {
		instantiateHibernateTransformationSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(sched);
		tx.commit();
		session.close();
	}
}
