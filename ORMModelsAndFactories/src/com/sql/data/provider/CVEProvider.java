package com.sql.data.provider;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import com.sql.data.objects.persistence.vulnerabilities.CVECore;
import com.sql.data.objects.persistence.vulnerabilities.Description;

public class CVEProvider extends PersistenceObjectProvider<CVECore>
		implements PersistenceObjectProviderService<CVECore> {

	private int year;

	public CVEProvider() {
		super();
		this.year = -1;
	}

	@Override
	public Class<CVECore> getClassName() {
		return CVECore.class;
	}

	@Override
	public CVECore find(int id) {
		if (year == -1) {
			System.err.println("A year for the CVE has not been provided!");
			return null;
		} else {
			super.instantiateHibernateSessionFactory();
			Session session = super.hibernateSessionFactory.openSession();
			String sql = "SELECT cve.* FROM cve WHERE cveYear = :cveYear AND cveID = :cveID";
			Query<CVECore> query = session.createNativeQuery(sql, CVECore.class);
			query.setParameter("cveYear", this.year);
			query.setParameter("cveID", id);
			List<CVECore> results = query.list();
			session.close();
			if (results.size() > 1) {
				System.err.println("Multiple CVE found for the given identifier CVE-"+year+"-"+id);
				return null;
			}else {
				return results.get(0);
			}
		}
	}
	
	public Description getDescriptionForCVE(CVECore cve) {
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM description WHERE cveID = :cveID AND cveYear = :cveYear";
		NativeQuery<Description> descQuery = session.createNativeQuery(sql, Description.class);
		descQuery.setParameter("cveID",cve.getCveID());
		descQuery.setParameter("cveYear", cve.getCveYear());
		try {
			Description result = descQuery.getSingleResult();
			session.close();
			return result;
		}catch (NoResultException ex) {
			session.close();
			return new Description();
		}
	}
	
	public List<CVECore> getAllCVE(){
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();		
		String sql = "SELECT cve.* FROM cve";
		Query<CVECore> query = session.createNativeQuery(sql, CVECore.class);
		List<CVECore> results = query.list();
		return results;
	}
	
	

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
