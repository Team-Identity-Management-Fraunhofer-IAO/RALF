package com.sql.data.provider;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.sql.data.objects.persistence.attackpatterns.CAPECCore;
import com.sql.data.objects.persistence.platforms.CPECore;

public class CAPECProvider extends PersistenceObjectProvider<CAPECCore>
		implements PersistenceObjectProviderService<CAPECCore> {

	@Override
	public Class<CAPECCore> getClassName() {
		return CAPECCore.class;
	}

	public List<CAPECCore> getAttackPatternsForCVE(int cveYear, int cveID) {
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT "
				+ "	attackpattern.*"
				+ "	FROM " + "cve,problemtype,cwecore,capeccwerelationship,attackpattern " + "WHERE"
				+ "	(cve.cveID = :cveID) AND (cve.cveYear = :cveYear) AND (cve.cveYear = problemtype.cveYear) AND (cve.cveID = problemtype.cveID) AND (cwecore.weaknessID = problemtype.weaknessID) AND (cwecore.weaknessID = capeccwerelationship.CWEId) AND (capeccwerelationship.CAPECId = attackpattern.id);";
		Query<CAPECCore> query = session.createNativeQuery(sql, CAPECCore.class);
		query.setParameter("cveYear", cveYear);
		query.setParameter("cveID", cveID);
		List<CAPECCore> result = query.list();
		session.close();
		return result;
	}

}
