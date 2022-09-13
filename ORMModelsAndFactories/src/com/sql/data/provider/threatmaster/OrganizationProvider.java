package com.sql.data.provider.threatmaster;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.organizations.Organization;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class OrganizationProvider extends PersistenceObjectProvider<Organization> implements PersistenceObjectProviderService<Organization>{

	@Override
	public void persist(Organization obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.persist(obj);
		
	}

	@Override
	public Organization find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		return super.find(id);
	}

	@Override
	public void delete(Organization obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.delete(obj);
	}
	
	@Override
	public Class<Organization> getClassName(){
		return Organization.class;
	}
	
	public List<Organization> getAllOrganizations(){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM organization";
		NativeQuery<Organization> query = session.createNativeQuery(sql, Organization.class);
		List<Organization> result = query.list();
		session.close();
		return result;
	}

}
