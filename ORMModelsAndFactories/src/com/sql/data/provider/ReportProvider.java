package com.sql.data.provider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.aggregations.AttackVector;
import com.sql.data.objects.aggregations.RatedAttackVector;
import com.sql.data.objects.aggregations.ReportOverview;
import com.sql.data.objects.persistence.report.AggregatedVulnerabilityFact;
import com.sql.data.objects.persistence.report.AttackPattern;
import com.sql.data.objects.persistence.report.ComponentVulnerability;
import com.sql.data.objects.persistence.report.NonExplicitCPEList;
import com.sql.data.objects.persistence.report.Report;
import com.sql.data.objects.persistence.report.VulnerableComponent;
import com.sql.data.objects.persistence.report.vulnerabilitymanagement.ManagedVulnerability;

public class ReportProvider extends PersistenceObjectProvider<Report>
		implements PersistenceObjectProviderService<Report> {

	@Override
	public void delete(Report obj) {
		super.instantiateHibernateReportingSessionFactory();
		super.delete(obj);
	}

	@Override
	public Report find(int id) {
		super.instantiateHibernateReportingSessionFactory();
		return super.find(id);
	}

	@Override
	public void persist(Report obj) {
		super.instantiateHibernateReportingSessionFactory();
		super.persist(obj);
	}

	@Override
	public Class<Report> getClassName() {
		return Report.class;
	}
	
	public List<String> getVulnerabilityIdentifiers(int reportID){
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT DISTINCT identifierString FROM componentVulnerability JOIN vulnerableComponent USING (vulnCompID) WHERE reportID = :reportID";
		NativeQuery<Object> idQuery = session.createNativeQuery(sql);
		idQuery.setParameter("reportID", reportID);
		try {
			List<Object> identifiers = idQuery.list();
			List<String> result = new ArrayList<String>();
			for (Object identifier : identifiers) {
				result.add((String) identifier);
			}
			session.close();
			return result;
		}catch (NoResultException ex) {
			session.close();
			return new ArrayList<String>();
		}
	}
	
	public List<AggregatedVulnerabilityFact> getAggregatedVulnerabilityFactsForReportID(int reportID){
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM aggregatedVulnerabilityFact WHERE reportID = :reportID" ;
		NativeQuery<AggregatedVulnerabilityFact> factQuery = session.createNativeQuery(sql, AggregatedVulnerabilityFact.class);
		factQuery.setParameter("reportID",reportID);
		List<AggregatedVulnerabilityFact> facts = factQuery.list();
		session.close();
		return facts;
	}

	public int getNonExplicitCPEListCountForReportID(int reportID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT COUNT(*) FROM nonExplicitCPEList WHERE reportID = :reportID AND NOT partOfApplication";
		NativeQuery<BigInteger> nonExplicitQuery = session.createNativeQuery(sql);
		nonExplicitQuery.setParameter("reportID", reportID);
		int numberOfNonExplicitCPELists = nonExplicitQuery.getSingleResult().intValueExact();
		return numberOfNonExplicitCPELists;
	}
	
	public List<NonExplicitCPEList> getNonExplicitCPEListForReportID(int reportID, int startIndex) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM nonExplicitCPEList WHERE reportID = :reportID AND NOT partOfApplication LIMIT :startIndex,25";
		System.out.println(sql+" reportID: "+reportID+" startIndex: "+startIndex);
		NativeQuery<NonExplicitCPEList> nonExplicitQuery = session.createNativeQuery(sql, NonExplicitCPEList.class);
		nonExplicitQuery.setParameter("reportID", reportID);
		nonExplicitQuery.setParameter("startIndex", startIndex);
		List<NonExplicitCPEList> result = nonExplicitQuery.list();
		session.close();
		return result;
	}
	
	public List<NonExplicitCPEList> getNonExplicitCPEListForReportID(int reportID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM nonExplicitCPEList WHERE reportID = :reportID AND NOT partOfApplication";
		NativeQuery<NonExplicitCPEList> nonExplicitQuery = session.createNativeQuery(sql, NonExplicitCPEList.class);
		nonExplicitQuery.setParameter("reportID", reportID);
		List<NonExplicitCPEList> result = nonExplicitQuery.list();
		session.close();
		return result;
	}
	
	public List<NonExplicitCPEList> getNonExplicitCPEListForReportIDAndCPEID(int reportID, int cpeID){
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM nonExplicitCPEList WHERE reportID = :reportID AND cpeID = :cpeID";
		NativeQuery<NonExplicitCPEList> nonExplicitQuery = session.createNativeQuery(sql, NonExplicitCPEList.class);
		nonExplicitQuery.setParameter("reportID", reportID);
		nonExplicitQuery.setParameter("cpeID", cpeID);
		List<NonExplicitCPEList> result = nonExplicitQuery.list();
		session.close();
		return result;
	}

	public boolean isVulnerabilityRemovable(int combinationID, int reportID, int cveYear, int cveID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT COUNT(*)>1 AS exclusiveCVE FROM nonExplicitCPEList WHERE combinationID=:combinationID";
		NativeQuery<BigInteger> exclusiveCVEQuery = session.createNativeQuery(sql);
		exclusiveCVEQuery.setParameter("combinationID", combinationID);
		boolean combinationIsLargerThanVulnerableComponent = exclusiveCVEQuery.getSingleResult().intValue()==1?true:false;
		sql = "SELECT COUNT(*)>1 AS exclusiveCVE FROM nonExplicitCPEList WHERE cveYear=:cveYear AND cveID = :cveID AND reportID=:reportID";
		exclusiveCVEQuery = session.createNativeQuery(sql);
		exclusiveCVEQuery.setParameter("cveYear", cveYear);
		exclusiveCVEQuery.setParameter("cveID", cveID);
		exclusiveCVEQuery.setParameter("reportID", reportID);
		boolean moreCombinationsExistForCVE = exclusiveCVEQuery.getSingleResult().intValue()==1?true:false;
		session.close();
		return (combinationIsLargerThanVulnerableComponent && !moreCombinationsExistForCVE);
	}

	public void removeComponentVulnerability(int combinationID, int reportID, int cveYear, int cveID) {
		if (isVulnerabilityRemovable(combinationID, reportID, cveYear, cveID)) {
			super.instantiateHibernateReportingSessionFactory();
			Session session = super.hibernateSessionFactory.openSession();
			String sql = "SELECT componentVulnerability.* FROM componentVulnerability JOIN vulnerableComponent USING (vulnCompID) WHERE reportID=:reportID AND cveID=:cveID AND cveYear=:cveYear";
			NativeQuery<ComponentVulnerability> componentVulnerabilityQuery = session.createNativeQuery(sql,
					ComponentVulnerability.class);
			componentVulnerabilityQuery.setParameter("reportID", reportID);
			componentVulnerabilityQuery.setParameter("cveYear", cveYear);
			componentVulnerabilityQuery.setParameter("cveID", cveID);
			List<ComponentVulnerability> componentVulnerabilities = componentVulnerabilityQuery.list();
			List<Integer> vulnCompIDs = new ArrayList<Integer>();
			Transaction tx = session.beginTransaction();
			for (ComponentVulnerability componentVulnerability : componentVulnerabilities) {
				vulnCompIDs.add(componentVulnerability.getVulnCompID());
				session.delete(componentVulnerability);
			}
			//See if there are any empty component vulnerabilities
			for (Integer vulnCompID : vulnCompIDs) {
				sql = "SELECT componentVulnerability.* FROM componentVulnerability JOIN vulnerableComponent USING (vulnCompID) WHERE reportID=:reportID AND vulnerableComponent.vulnCompID = :vulnCompID";
				componentVulnerabilityQuery = session.createNativeQuery(sql, ComponentVulnerability.class);
				componentVulnerabilityQuery.setParameter("reportID",reportID);
				componentVulnerabilityQuery.setParameter("vulnCompID", vulnCompID);
				try {
					componentVulnerabilities = componentVulnerabilityQuery.list();
				} catch (NoResultException ex) {
					//Remove vulnerable component if it is empty
					VulnerableComponent comp = session.find(VulnerableComponent.class, vulnCompID);
					session.delete(comp);
				}
			}
			tx.commit();
			session.close();
		}
	}
	
	public void deleteNonExplicitCPEList(NonExplicitCPEList cpeList) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(cpeList);
		tx.commit();
		session.close();
	}
	
	public Report getLastReportForSWStack(Long swStackID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM report WHERE swStackID = :swStackID ORDER BY reportID DESC LIMIT 1";
		NativeQuery<Report> reportQuery = session.createNativeQuery(sql, Report.class);
		reportQuery.setParameter("swStackID", swStackID);
		try {
			Report report = reportQuery.getSingleResult();
			session.close();
			return report;
		}catch (NoResultException ex) {
			ex.printStackTrace();
			session.close();
			return null;
		}
	}

	public List<Integer> getLastVulnerableComponentIDForSWStack(int swStackID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT reportID FROM report WHERE swStackID = :swStackID ORDER BY timeString DESC LIMIT 1";
		int reportID = -1;
		try {
			reportID = (int) session.createNativeQuery(sql).setParameter("swStackID", swStackID).getSingleResult();
		} catch (NoResultException ex) {
			session.close();
			return null;
		}
		sql = "SELECT vulnCompID FROM vulnerableComponent WHERE reportID = :reportID";
		try {
			List<Integer> vulnCompIDs = session.createNativeQuery(sql).setParameter("reportID", reportID).list();
			session.close();
			return vulnCompIDs;
		} catch (NoResultException ex) {
			session.close();
			return null;
		}
	}

	public Map<String, Float> getMaxBaseScorePerVectorAndVulnCompID(List<Integer> vulnCompIDs) {
		if (vulnCompIDs == null) {
			return new HashMap<String, Float>();
		}
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		Map<String, Float> baseScores = new HashMap<String, Float>();
		for (Integer vulnCompID : vulnCompIDs) {
			String sql = "SELECT attackVector, MAX(baseScore) FROM componentVulnerability WHERE vulnCompID = :vulnCompID  AND identifierString NOT IN (SELECT identifier FROM managedvulnerability) GROUP BY attackVector";
			NativeQuery<Object[]> query = session.createNativeQuery(sql);
			query.setParameter("vulnCompID", vulnCompID);
			List<Object[]> result = query.list();
			for (Object[] res : result) {
				String vector = (String) res[0];
				Float currentScore = ((BigDecimal) res[1]).setScale(2).floatValue();
				if (baseScores.containsKey(vector)) {
					Float maxScore = baseScores.get(vector);
					if (maxScore >= currentScore) {
						continue;
					}
				}
				baseScores.put(vector, currentScore);
			}
			sql = "SELECT attackVector, MAX(baseScore) FROM componentVulnerability WHERE vulnCompID = :vulnCompID  AND identifierString IN (SELECT identifier FROM managedvulnerability WHERE NOT treated) GROUP BY attackVector";
			query = session.createNativeQuery(sql);
			query.setParameter("vulnCompID", vulnCompID);
			result = query.list();
			for (Object[] res : result) {
				String vector = (String) res[0]+" (Managed)";
				Float currentScore = ((BigDecimal) res[1]).setScale(2).floatValue();
				if (baseScores.containsKey(vector)) {
					Float maxScore = baseScores.get(vector);
					if (maxScore >= currentScore) {
						continue;
					}
				}
				baseScores.put(vector, currentScore);
			}
			sql = "SELECT attackVector, MAX(baseScore) FROM componentVulnerability WHERE vulnCompID = :vulnCompID  AND identifierString IN (SELECT identifier FROM managedvulnerability WHERE treated) GROUP BY attackVector";
			query = session.createNativeQuery(sql);
			query.setParameter("vulnCompID", vulnCompID);
			result = query.list();
			for (Object[] res : result) {
				String vector = (String) res[0]+" (Treated)";
				Float currentScore = ((BigDecimal) res[1]).setScale(2).floatValue();
				if (baseScores.containsKey(vector)) {
					Float maxScore = baseScores.get(vector);
					if (maxScore >= currentScore) {
						continue;
					}
				}
				baseScores.put(vector, currentScore);
			}
		}
		session.close();
		return baseScores;
	}

	public List<com.sql.data.objects.aggregations.VulnerableComponent> getVulnerableComponentsForReportID(
			int reportID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM vulnerableComponent WHERE reportID = :reportID";
		NativeQuery<VulnerableComponent> query = session.createNativeQuery(sql, VulnerableComponent.class);
		query.setParameter("reportID", reportID);
		List<VulnerableComponent> components = query.list();
		List<com.sql.data.objects.aggregations.VulnerableComponent> result = new ArrayList();
		for (VulnerableComponent component : components) {
			com.sql.data.objects.aggregations.VulnerableComponent transComponent = new com.sql.data.objects.aggregations.VulnerableComponent();
			transComponent.setComp_language(component.getComp_language());
			transComponent.setComp_update(component.getComp_update());
			transComponent.setComponentID(new Long(component.getComponentID()));
			transComponent.setEdition(component.getEdition());
			transComponent.setOther(component.getOther());
			transComponent.setPart(component.getPart());
			transComponent.setProduct(component.getProduct());
			transComponent.setSw_edition(component.getSw_edition());
			transComponent.setTarget_hw(component.getTarget_hw());
			transComponent.setTarget_sw(component.getTarget_sw());
			transComponent.setVendor(component.getVendor());
			transComponent.setVersion(component.getVersion());
			result.add(transComponent);
			sql = "SELECT * FROM componentVulnerability WHERE vulnCompID = :vulnCompID";
			NativeQuery<ComponentVulnerability> vulnQuery = session.createNativeQuery(sql,
					ComponentVulnerability.class);
			vulnQuery.setParameter("vulnCompID", component.getVulnCompID());
			List<ComponentVulnerability> vulns = vulnQuery.list();
			for (ComponentVulnerability vuln : vulns) {
				com.sql.data.objects.aggregations.ComponentVulnerability transVuln = new com.sql.data.objects.aggregations.ComponentVulnerability();
				transVuln.setAttackComplexity(vuln.getAttackComplexity());
				transVuln.setAttackVector(vuln.getAttackVector());
				transVuln.setAvailabilityImpact(vuln.getAvailabilityImpact());
				transVuln.setBaseScore(vuln.getBaseScore());
				transVuln.setBaseSeverity(vuln.getBaseSeverity());
				transVuln.setConfidentialityImpact(vuln.getConfidentialityImpact());
				transVuln.setDescriptionText(vuln.getDescriptionText());
				transVuln.setExploitabilityScore(vuln.getExploitabilityScore());
				transVuln.setIdentifierString(vuln.getIdentifierString());
				transVuln.setImpactScore(vuln.getImpactScore());
				transVuln.setIntegrityImpact(vuln.getIntegrityImpact());
				transVuln.setPrivilegesRequired(vuln.getPrivilegesRequired());
				transVuln.setScope(vuln.getScope());
				transVuln.setUri(vuln.getUri());
				transVuln.setUserInteraction(vuln.getUserInteraction());
				transVuln.setVectorString(vuln.getVectorString());
				transVuln.setYear(vuln.getCveYear());
				transVuln.setId(vuln.getCveID());
				
				ManagedVulnerability manVuln = session.get(ManagedVulnerability.class, transVuln.getIdentifierString());
				if (manVuln != null) {
					transVuln.setManaged(true);
					if (manVuln.isTreated()) {
						transVuln.setTreated(true);
					}
				}
				
				AttackVector vector = new AttackVector();
				vector.setVectorName(vuln.getAttackVector());
				if (transComponent.getVectors().contains(vector)) {
					vector = transComponent.getVectors().get(transComponent.getVectors().indexOf(vector));
				} else {
					transComponent.getVectors().add(vector);
				}
				vector.getComponentVulnerabilities().add(transVuln);
			}
			for (AttackVector vec : transComponent.getVectors()) {
				sql = "SELECT * FROM attackPattern WHERE vulnCompID = :vulnCompID AND attackVector = :attackVector";
				NativeQuery<AttackPattern> patternQuery = session.createNativeQuery(sql, AttackPattern.class);
				patternQuery.setParameter("vulnCompID", component.getVulnCompID());
				patternQuery.setParameter("attackVector", vec.getVectorName());
				List<AttackPattern> patterns = patternQuery.list();
				vec.setAttackPatterns(patterns);
			}
		}
		session.close();
		return result;
	}

	public List<Report> getReports(Long swStackID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM report WHERE swStackID = :swStackID";
		NativeQuery<Report> query = session.createNativeQuery(sql, Report.class);
		query.setParameter("swStackID", swStackID);
		List<Report> result = query.list();
		session.close();
		return result;
	}

	public String getDateForSWStackID(Long swStackID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String maxTime = (String) session.createQuery("SELECT MAX(timeString) FROM Report WHERE swStackID = :swStackID")
				.setParameter("swStackID", swStackID).getSingleResult();
		session.close();
		return maxTime;
	}
	
	public ReportOverview getReportOverview(int reportID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT " + "report.reportID, " + "timeString, " + "attackVector, " + "MIN(baseScore) AS minBase, "
				+ "MAX(baseScore) AS maxBase, " + "MIN(exploitabilityScore) AS minExploitability, "
				+ "MAX(exploitabilityScore) AS maxExploitability, " + "MIN(impactScore) AS minImpact, "
				+ "MAX(impactScore) AS maxImpact " + "FROM report " + "JOIN vulnerableComponent "
				+ "JOIN componentVulnerability " + "ON (report.reportID = vulnerableComponent.reportID) "
				+ "AND (componentVulnerability.vulnCompID = vulnerableComponent.vulnCompID) "
				+ "WHERE report.reportID= :reportID " + "GROUP BY attackVector, reportID "
				+ "ORDER BY reportID ASC, attackVector DESC;";
		NativeQuery<Object[]> query = session.createNativeQuery(sql);
		query.setParameter("reportID", reportID);
		try {
		List<Object[]> result = query.list();
		ReportOverview report = new ReportOverview();
		for (Object[] line : result) {
			int id = (int) line[0];
			String timeString = (String) line[1];
			String attackVector = (String) line[2];
			float minBase = bigDecimalToFloat((BigDecimal) line[3]);
			float maxBase = bigDecimalToFloat((BigDecimal) line[4]);
			float minExploitability = bigDecimalToFloat((BigDecimal) line[5]);
			float maxExploitability = bigDecimalToFloat((BigDecimal) line[6]);
			float minImpact = bigDecimalToFloat((BigDecimal) line[7]);
			float maxImpact = bigDecimalToFloat((BigDecimal) line[8]);			
			report.setReportID(id);
			RatedAttackVector vector = new RatedAttackVector();
			vector.setAttackVector(attackVector);
			if (report.getAttackVectors() != null) {
				if (!report.getAttackVectors().contains(vector)) {
					vector.setMinBase(minBase);
					vector.setMaxBase(maxBase);
					vector.setMinExploitability(minExploitability);
					vector.setMaxExploitability(maxExploitability);
					vector.setMinImpact(minImpact);
					vector.setMaxImpact(maxImpact);
					report.getAttackVectors().add(vector);
				}
			} else {
				report.setAttackVectors(new ArrayList<RatedAttackVector>());
				vector.setMinBase(minBase);
				vector.setMaxBase(maxBase);
				vector.setMinExploitability(minExploitability);
				vector.setMaxExploitability(maxExploitability);
				vector.setMinImpact(minImpact);
				vector.setMaxImpact(maxImpact);
				report.getAttackVectors().add(vector);
			}
		}
		session.close();
		return report;
		}catch (NoResultException ex) {
			session.close();
			return null;
		}
	}

	public List<ReportOverview> getReportOverviews(Long swStackID) {
		super.instantiateHibernateReportingSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		//Query anpassen, so dass nicht die componentVulnerabilities gezogen werden, die in managedVulnerability drin sind
		String sql = "SELECT " + "report.reportID, " + "timeString, " + "attackVector, " + "MIN(baseScore) AS minBase, "
				+ "MAX(baseScore) AS maxBase, " + "MIN(exploitabilityScore) AS minExploitability, "
				+ "MAX(exploitabilityScore) AS maxExploitability, " + "MIN(impactScore) AS minImpact, "
				+ "MAX(impactScore) AS maxImpact " + "FROM report " + "JOIN vulnerableComponent "
				+ "JOIN componentVulnerability " + "ON (report.reportID = vulnerableComponent.reportID) "
				+ "AND (componentVulnerability.vulnCompID = vulnerableComponent.vulnCompID) "
				+ "WHERE report.swStackID= :swStackID " + "AND (componentVulnerability.identifierString NOT IN (SELECT identifier FROM managedvulnerability)) "+"GROUP BY attackVector, reportID "
				+ "ORDER BY reportID ASC, attackVector DESC;";
		NativeQuery<Object[]> query = session.createNativeQuery(sql);
		query.setParameter("swStackID", swStackID);
		List<Object[]> result = query.list();
		List<ReportOverview> reports = new ArrayList<ReportOverview>();
		for (Object[] line : result) {
			int id = (int) line[0];
			String timeString = (String) line[1];
			String attackVector = (String) line[2];
			float minBase = bigDecimalToFloat((BigDecimal) line[3]);
			float maxBase = bigDecimalToFloat((BigDecimal) line[4]);
			float minExploitability = bigDecimalToFloat((BigDecimal) line[5]);
			float maxExploitability = bigDecimalToFloat((BigDecimal) line[6]);
			float minImpact = bigDecimalToFloat((BigDecimal) line[7]);
			float maxImpact = bigDecimalToFloat((BigDecimal) line[8]);
			ReportOverview report = new ReportOverview();
			report.setReportID(id);

			if (reports.contains(report)) {
				report = reports.get(reports.indexOf(report));
			} else {
				report.setTimeString(timeString);
				reports.add(report);
			}
			RatedAttackVector vector = new RatedAttackVector();
			vector.setAttackVector(attackVector);
			if (report.getAttackVectors() != null) {
				if (!report.getAttackVectors().contains(vector)) {
					vector.setMinBase(minBase);
					vector.setMaxBase(maxBase);
					vector.setMinExploitability(minExploitability);
					vector.setMaxExploitability(maxExploitability);
					vector.setMinImpact(minImpact);
					vector.setMaxImpact(maxImpact);
					report.getAttackVectors().add(vector);
				}
			} else {
				report.setAttackVectors(new ArrayList<RatedAttackVector>());
				vector.setMinBase(minBase);
				vector.setMaxBase(maxBase);
				vector.setMinExploitability(minExploitability);
				vector.setMaxExploitability(maxExploitability);
				vector.setMinImpact(minImpact);
				vector.setMaxImpact(maxImpact);
				report.getAttackVectors().add(vector);
			}
		}
		//Das gleiche nochmal für alle managedVulnerabilities die nicht treated sind
		sql = "SELECT " + "report.reportID, " + "timeString, " + "attackVector, " + "MIN(baseScore) AS minBase, "
				+ "MAX(baseScore) AS maxBase, " + "MIN(exploitabilityScore) AS minExploitability, "
				+ "MAX(exploitabilityScore) AS maxExploitability, " + "MIN(impactScore) AS minImpact, "
				+ "MAX(impactScore) AS maxImpact " + "FROM report " + "JOIN vulnerableComponent "
				+ "JOIN componentVulnerability " + "ON (report.reportID = vulnerableComponent.reportID) "
				+ "AND (componentVulnerability.vulnCompID = vulnerableComponent.vulnCompID) "
				+ "WHERE report.swStackID= :swStackID " + "AND (componentVulnerability.identifierString IN (SELECT identifier FROM managedvulnerability WHERE NOT treated)) "+"GROUP BY attackVector, reportID "
				+ "ORDER BY reportID ASC, attackVector DESC;";
		query = session.createNativeQuery(sql);
		query.setParameter("swStackID", swStackID);
		result = query.list();
		for (Object[] line : result) {
			int id = (int) line[0];
			String timeString = (String) line[1];
			String attackVector = (String) line[2]+ " (Managed)";
			float minBase = bigDecimalToFloat((BigDecimal) line[3]);
			float maxBase = bigDecimalToFloat((BigDecimal) line[4]);
			float minExploitability = bigDecimalToFloat((BigDecimal) line[5]);
			float maxExploitability = bigDecimalToFloat((BigDecimal) line[6]);
			float minImpact = bigDecimalToFloat((BigDecimal) line[7]);
			float maxImpact = bigDecimalToFloat((BigDecimal) line[8]);
			ReportOverview report = new ReportOverview();
			report.setReportID(id);

			if (reports.contains(report)) {
				report = reports.get(reports.indexOf(report));
			} else {
				report.setTimeString(timeString);
				reports.add(report);
			}
			RatedAttackVector vector = new RatedAttackVector();
			vector.setAttackVector(attackVector);
			if (report.getAttackVectors() != null) {
				if (!report.getAttackVectors().contains(vector)) {
					vector.setMinBase(minBase);
					vector.setMaxBase(maxBase);
					vector.setMinExploitability(minExploitability);
					vector.setMaxExploitability(maxExploitability);
					vector.setMinImpact(minImpact);
					vector.setMaxImpact(maxImpact);
					report.getAttackVectors().add(vector);
				}
			} else {
				report.setAttackVectors(new ArrayList<RatedAttackVector>());
				vector.setMinBase(minBase);
				vector.setMaxBase(maxBase);
				vector.setMinExploitability(minExploitability);
				vector.setMaxExploitability(maxExploitability);
				vector.setMinImpact(minImpact);
				vector.setMaxImpact(maxImpact);
				report.getAttackVectors().add(vector);
			}
		}
		//Und nochmal für alle managedVulnerabilities die treated sind
		sql = "SELECT " + "report.reportID, " + "timeString, " + "attackVector, " + "MIN(baseScore) AS minBase, "
				+ "MAX(baseScore) AS maxBase, " + "MIN(exploitabilityScore) AS minExploitability, "
				+ "MAX(exploitabilityScore) AS maxExploitability, " + "MIN(impactScore) AS minImpact, "
				+ "MAX(impactScore) AS maxImpact " + "FROM report " + "JOIN vulnerableComponent "
				+ "JOIN componentVulnerability " + "ON (report.reportID = vulnerableComponent.reportID) "
				+ "AND (componentVulnerability.vulnCompID = vulnerableComponent.vulnCompID) "
				+ "WHERE report.swStackID= :swStackID " + "AND (componentVulnerability.identifierString IN (SELECT identifier FROM managedvulnerability WHERE treated)) "+"GROUP BY attackVector, reportID "
				+ "ORDER BY reportID ASC, attackVector DESC;";
		query = session.createNativeQuery(sql);
		query.setParameter("swStackID", swStackID);
		result = query.list();
		for (Object[] line : result) {
			int id = (int) line[0];
			String timeString = (String) line[1];
			String attackVector = (String) line[2]+ " (Treated)";
			float minBase = bigDecimalToFloat((BigDecimal) line[3]);
			float maxBase = bigDecimalToFloat((BigDecimal) line[4]);
			float minExploitability = bigDecimalToFloat((BigDecimal) line[5]);
			float maxExploitability = bigDecimalToFloat((BigDecimal) line[6]);
			float minImpact = bigDecimalToFloat((BigDecimal) line[7]);
			float maxImpact = bigDecimalToFloat((BigDecimal) line[8]);
			ReportOverview report = new ReportOverview();
			report.setReportID(id);

			if (reports.contains(report)) {
				report = reports.get(reports.indexOf(report));
			} else {
				report.setTimeString(timeString);
				reports.add(report);
			}
			RatedAttackVector vector = new RatedAttackVector();
			vector.setAttackVector(attackVector);
			if (report.getAttackVectors() != null) {
				if (!report.getAttackVectors().contains(vector)) {
					vector.setMinBase(minBase);
					vector.setMaxBase(maxBase);
					vector.setMinExploitability(minExploitability);
					vector.setMaxExploitability(maxExploitability);
					vector.setMinImpact(minImpact);
					vector.setMaxImpact(maxImpact);
					report.getAttackVectors().add(vector);
				}
			} else {
				report.setAttackVectors(new ArrayList<RatedAttackVector>());
				vector.setMinBase(minBase);
				vector.setMaxBase(maxBase);
				vector.setMinExploitability(minExploitability);
				vector.setMaxExploitability(maxExploitability);
				vector.setMinImpact(minImpact);
				vector.setMaxImpact(maxImpact);
				report.getAttackVectors().add(vector);
			}
		}
		session.close();
		return reports;
	}

	private float bigDecimalToFloat(BigDecimal decimal) {
		return decimal.setScale(2, RoundingMode.UP).floatValue();
	}
}
