package com.sql.data.provider;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.sql.data.objects.persistence.platforms.CPECore;

public class CPEProvider extends PersistenceObjectProvider<CPECore> implements PersistenceObjectProviderService<CPECore>{

	@Override
	public void delete(CPECore obj) {
		super.delete(obj);
	}
	
	@Override
	public CPECore find(int id) {
		return super.find(id);
	}
	
	@Override
	public void persist(CPECore obj) {
		super.persist(obj);
	}
	
	@Override
	public Class<CPECore> getClassName() {		
		return CPECore.class;
	}
	
	public List<CPECore> getCPEsByContainsPlatformId(int cPlatformID) {
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT cpe.* "
				+ "FROM cpe "
				+ "JOIN contains_platform "
				+ "ON (contains_platform.cpeID = cpe.cpeID) "
				+ "WHERE "
				+ "contains_platform.combinationID = :combinationID";
		Query<CPECore> query = session.createNativeQuery(sql, CPECore.class);
		query.setParameter("combinationID", cPlatformID);
		List<CPECore> result = query.list();
		session.close();
		return result;	
	}
}

