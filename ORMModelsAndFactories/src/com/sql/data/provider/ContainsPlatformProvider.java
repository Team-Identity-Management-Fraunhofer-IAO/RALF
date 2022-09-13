package com.sql.data.provider;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.sql.data.objects.persistence.platforms.CPECore;
import com.sql.data.objects.persistence.platforms.ContainsPlatform;
import com.sql.data.objects.persistence.platforms.ContainsPlatformPK;

public class ContainsPlatformProvider extends PersistenceObjectProvider<ContainsPlatform> implements PersistenceObjectProviderService<ContainsPlatform>{

	@Override
	public void delete(ContainsPlatform obj) {
		super.delete(obj);
	}
	
	public ContainsPlatform find(int combinationID, int cpeID) {
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		ContainsPlatform result = session.get(ContainsPlatform.class, new ContainsPlatformPK(combinationID, cpeID));
		session.close();
		return result;
	}
	
	public List<ContainsPlatform> findByCombinationID(int combinationID){
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String hql = "FROM ContainsPlatform WHERE combinationID = :combinationID";
		Query<ContainsPlatform> query = session.createQuery(hql);
		query.setParameter("combinationID", combinationID);
		List<ContainsPlatform> result = query.list();
		session.close();
		return result;
	}
	
	@Override
	public void persist(ContainsPlatform obj) {
		super.persist(obj);
	}
	
	@Override
	public Class<ContainsPlatform> getClassName() {		
		return ContainsPlatform.class;
	}
	
	public List<ContainsPlatform> findByCombinationIDExclusionAndConfId(int combinationId, int confId) {
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		//String hql = "FROM CPECore WHERE vendor = 'microsoft' AND product='windows_server_2016' AND version='1903'";
		//Query<CPECore> query = session.createQuery(hql);
		//List<CPECore> cpeList = query.list();
		String sql = "SELECT "
				+ "contains_platform.combinationID, "
				+ "contains_platform.cpeID, "
				+ "contains_platform.vulnerable, "
				+ "contains_platform.versionStart, "
				+ "contains_platform.versionEnd, "
				+ "contains_platform.startIncluding, "
				+ "contains_platform.endIncluding "
					+ "FROM contains_platform "
					+ "JOIN platform_combination "
				+ "ON (platform_combination.combinationID = contains_platform.combinationID) "
				+ "WHERE contains_platform.combinationID != :combinationId "
				+ " AND "
				+ " platform_combination.confID = :confId";
		Query<ContainsPlatform> query = session.createNativeQuery(sql, ContainsPlatform.class);
		query.setParameter("combinationId", combinationId);
		query.setParameter("confId", confId);
		List<ContainsPlatform> results = query.list();
		session.close();
		return results;
	}
	
	public List<ContainsPlatform> findByConfId(int confId) {
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		//String hql = "FROM CPECore WHERE vendor = 'microsoft' AND product='windows_server_2016' AND version='1903'";
		//Query<CPECore> query = session.createQuery(hql);
		//List<CPECore> cpeList = query.list();
		String sql = "SELECT "
				+ "contains_platform.combinationID, "
				+ "contains_platform.cpeID, "
				+ "contains_platform.vulnerable, "
				+ "contains_platform.versionStart, "
				+ "contains_platform.versionEnd, "
				+ "contains_platform.startIncluding, "
				+ "contains_platform.endIncluding "
					+ "FROM contains_platform "
					+ "JOIN platform_combination "
				+ "ON (platform_combination.combinationID = contains_platform.combinationID) "
				+ "WHERE platform_combination.confID = :confId";
		Query<ContainsPlatform> query = session.createNativeQuery(sql, ContainsPlatform.class);
		query.setParameter("confId", confId);
		List<ContainsPlatform> results = query.list();
		session.close();
		return results;
	}
}
