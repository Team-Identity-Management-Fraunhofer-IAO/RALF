package com.sql.data.provider.threatmaster.datawarehouse;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.datawarehouse.ThreatCollection;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class ThreatCollectionProvider extends PersistenceObjectProvider<ThreatCollection> implements PersistenceObjectProviderService<ThreatCollection>{

	@Override
	public Class<ThreatCollection> getClassName() {
		return ThreatCollection.class;
	}
	
	public void persist(ThreatCollection obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		super.persist(obj);
	}
	
	public ThreatCollection find(int id) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		return super.find(id);
	}
	
	public void delete(ThreatCollection obj) {
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		super.delete(obj);
	}
	
	public List<ThreatCollection> getAllThreatCollections(){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM C_COLLECTION";
		NativeQuery<ThreatCollection> query = session.createNativeQuery(sql, ThreatCollection.class);
		List<ThreatCollection> collections = query.list();
		session.close();
		return collections;
	}

}
