package com.sql.data.provider;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.sql.data.objects.persistence.metadata.Metadata;
import com.sql.data.objects.persistence.platforms.CPECore;

public class MetadataProvider extends PersistenceObjectProvider<Metadata>
		implements PersistenceObjectProviderService<Metadata> {

	@Override
	public Class<Metadata> getClassName() {
		return Metadata.class;
	}

	public Metadata getLatestMetadata() {
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT metadata.* FROM metadata ORDER BY metadataID DESC LIMIT 1";
		Query<Metadata> query = session.createNativeQuery(sql, Metadata.class);
		Metadata result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {
			session.close();
			return null;
		}
		session.close();
		return result;
	}
	
	@Override
	public void persist(Metadata obj) {
		System.out.println("Persisting "+obj);
		instantiateHibernateSessionFactory();
		Session session = hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(obj);
		tx.commit();
		System.out.println("Persisted "+obj);
		session.close();
	}

}
