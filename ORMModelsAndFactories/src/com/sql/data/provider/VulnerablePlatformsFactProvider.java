package com.sql.data.provider;

import com.sql.data.objects.aggregations.AggregatedVulnerability;
import com.sql.data.objects.aggregations.VulnerablePlatformCombination;
import com.sql.data.objects.persistence.facts.VulnerablePlatformsFact;
import com.sql.data.objects.persistence.platforms.CPECore;
import com.sql.data.objects.persistence.platforms.ConfigurationNode;
import com.sql.data.objects.persistence.platforms.ContainsPlatform;
import com.sql.data.objects.persistence.platforms.PlatformCombination;
import com.sql.data.objects.persistence.vulnerabilities.CVECore;
import com.sql.data.objects.persistence.vulnerabilities.Description;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

public class VulnerablePlatformsFactProvider extends PersistenceObjectProvider<VulnerablePlatformsFact>
		implements PersistenceObjectProviderService<VulnerablePlatformsFact> {

	@Override
	public Class<VulnerablePlatformsFact> getClassName() {
		return VulnerablePlatformsFact.class;
	}

	private String checkForWildcards(String value) {
		if (value.equals("-")) {
			return "IN ('*','-')";
		} else if (value.equals("*")) {
			return null;
		} else if (value.contains("*")) {
			return "LIKE " + value.replace("*", "%");
		} else {
			return "=\"" + value + "\"";
		}
	}

	public boolean checkForEquality(CPECore cpe, CPECore cpe2) {
		if (cpe.getVendor().equals(cpe2.getVendor()) && cpe.getProduct().equals(cpe2.getProduct())
				&& cpe.getPart().equals(cpe2.getPart())) {
			int differences = 0;
			if (!cpe.getVersion().equals("*")) {
				if (!cpe.getVersion().equals(cpe2.getVersion())) {
					differences++;
				}
			}
			if (!cpe.getCpe_update().equals("*")) {
				if (!cpe.getCpe_update().equals(cpe2.getCpe_update())) {
					differences++;
				}
			}
			if (!cpe.getEdition().equals("*")) {
				if (!cpe.getEdition().equals(cpe2.getEdition())) {
					differences++;
				}
			}
			if (!cpe.getSw_edition().equals("*")) {
				if (!cpe.getSw_edition().equals(cpe2.getSw_edition())) {
					differences++;
				}
			}
			if (!cpe.getCpe_language().equals("*")) {
				if (!cpe.getCpe_language().equals(cpe2.getCpe_language())) {
					differences++;
				}
			}
			if (!cpe.getTarget_sw().equals("*")) {
				if (!cpe.getTarget_sw().equals(cpe2.getTarget_sw())) {
					differences++;
				}
			}
			if (!cpe.getTarget_hw().equals("*")) {
				if (!cpe.getTarget_hw().equals(cpe2.getTarget_hw())) {
					differences++;
				}
			}
			if (!cpe.getOther().equals("*")) {
				if (!cpe.getOther().equals(cpe2.getOther())) {
					differences++;
				}
			}
			if (differences != 0) {
				return true;
			}
		}
		return false;
	}

	public List<AggregatedVulnerability> getAggregatedVulnerabilitiesByDescriptionText(String vendor,
			List<String> permutations, CPECore cpe, String mode, List<String> identifierStrings,
			List<String> treatedIdentifiers, List<AggregatedVulnerability> retrievedVulnerabilities) {
		instantiateHibernateSessionFactory();
		String productSelector = "descriptionText LIKE '%" + cpe.getVendor() + "%'";
		if (!permutations.isEmpty()) {
			productSelector += "AND (";
		}
		for (int i = 0; i < permutations.size(); i++) {
			String permutation = permutations.get(i);
			productSelector += "descriptionText LIKE '%" + permutation + "%'"
					+ (i == permutations.size() - 1 ? "" : " OR ");
		}
		productSelector += ")";
		String includeCVESelector = "";
		String excludeCVESelector = "";
		if (mode.equals("new_only") || mode.contentEquals("untreated")) {
			if (!identifierStrings.isEmpty()) {
				includeCVESelector = "(cveYear,cveID) IN (";
				for (int i = 0; i < identifierStrings.size(); i++) {
					String identifier = identifierStrings.get(i);
					CVECore cve = new CVECore();
					cve.parseIdentifierString(identifier);
					includeCVESelector += "(" + cve.getCveYear() + "," + cve.getCveID() + ")"
							+ (i == identifierStrings.size() - 1 ? "" : ",");
				}
				includeCVESelector += ")";
			}
			if (mode.contentEquals("untreated")) {
				if (!treatedIdentifiers.isEmpty()) {
					excludeCVESelector = "(cveYear,cveID) NOT IN (";
					for (int i = 0; i < treatedIdentifiers.size(); i++) {
						String identifier = treatedIdentifiers.get(i);
						CVECore cve = new CVECore();
						cve.parseIdentifierString(identifier);
						excludeCVESelector += "(" + cve.getCveYear() + "," + cve.getCveID() + ")"
								+ (i == treatedIdentifiers.size() - 1 ? "" : ",");
					}
					excludeCVESelector += ")";
				}
			}
		}
		String excludeFoundCVESelector = "";
		if (!retrievedVulnerabilities.isEmpty()) {
			excludeFoundCVESelector += "(cveYear,cveID) NOT IN (";
			for (int i = 0; i < retrievedVulnerabilities.size(); i++) {
				AggregatedVulnerability vuln = retrievedVulnerabilities.get(i);
				excludeFoundCVESelector += "(" + vuln.getCveYear() + "," + vuln.getCveID() + ")"
						+ (i == retrievedVulnerabilities.size() - 1 ? "" : ",");
			}
			excludeFoundCVESelector += ")";
		}
		String sql = "SELECT * FROM description WHERE " + productSelector
				+ (includeCVESelector.equals("") ? "" : " AND ") + includeCVESelector
				+ (excludeCVESelector.equals("") ? "" : " AND ") + excludeCVESelector
				+ (excludeFoundCVESelector.equals("") ? "" : " AND ") + excludeFoundCVESelector + ";";
		System.out.println(sql);
		Session session = super.hibernateSessionFactory.openSession();
		NativeQuery<Description> descriptionQuery = session.createNativeQuery(sql, Description.class);
		ArrayList<AggregatedVulnerability> vulnerabilityList = new ArrayList<AggregatedVulnerability>();
		try {
			List<Description> descriptions = descriptionQuery.list();
			System.out.println("Found " + descriptions.size());
			for (Description description : descriptions) {
				sql = "SELECT * FROM cve WHERE cveYear=:cveYear AND cveID=:cveID";
				NativeQuery<CVECore> cveQuery = session.createNativeQuery(sql, CVECore.class);
				cveQuery.setParameter("cveYear", description.getCveYear());
				cveQuery.setParameter("cveID", description.getCveID());
				List<CVECore> cves = cveQuery.list();
				for (CVECore cve : cves) {
					sql = "SELECT * FROM configuration_node WHERE cveYear=:cveYear AND cveID=:cveID";
					NativeQuery<ConfigurationNode> nodeQuery = session.createNativeQuery(sql, ConfigurationNode.class);
					nodeQuery.setParameter("cveYear", cve.getCveYear());
					nodeQuery.setParameter("cveID", cve.getCveID());
					List<ConfigurationNode> nodes = null;
					try {
						nodes = nodeQuery.list();
					} catch (NoResultException ex) {
						nodes = null;
					}
					if (nodes == null || nodes.isEmpty()) {
						System.out.println("Adding " + cve.getCveYear() + "-" + cve.getCveID());
						AggregatedVulnerability vuln = new AggregatedVulnerability();
						vuln.setDescriptionText(description.getDescriptionText());
						vuln.setCveID(cve.getCveID());
						vuln.setCveYear(cve.getCveYear());
						vuln.setIdentifierString(cve.getIdentifierString());
						vuln.setVectorString(cve.getVectorString());
						vuln.setAttackVector(cve.getAttackVector());
						vuln.setAttackComplexity(cve.getAttackComplexity());
						vuln.setPrivilegesRequired(cve.getPrivilegesRequired());
						vuln.setIntegrityImpact(cve.getIntegrityImpact());
						vuln.setAvailabilityImpact(cve.getAvailabilityImpact());
						vuln.setBaseScore(cve.getBaseScore());
						vuln.setBaseSeverity(cve.getBaseSeverity());
						vuln.setExploitabilityScore(cve.getExploitabilityScore());
						vuln.setImpactScore(cve.getImpactScore());
						vuln.setConfID(0);
						vuln.setRelationship_operator(null);
						vuln.setNode_operator(null);
						vuln.setCombinationID(0);
						vuln.setOperator(null);
						vuln.setVulnerable(true);
						vuln.setStandardized(false);
						vuln.setURI(cpe.getURI());
						vuln.setCpeID(cpe.getCpeID());
						vulnerabilityList.add(vuln);
					}
				}
			}
		} catch (NoResultException ex) {
			System.err.println("No results found!");
		}
		return vulnerabilityList;
	}

	public List<AggregatedVulnerability> getAggregatedVulnerabilitiesForCPE(CPECore cpe, String mode,
			List<String> identifierStrings, List<String> treatedIdentifiers) {
		instantiateHibernateSessionFactory();
		boolean explicit = true;
		String selectionSubQuery = explicit ? "" : "vulnerable ";
		String cpe_language = checkForWildcards(cpe.getCpe_language());
		String cpe_update = checkForWildcards(cpe.getCpe_update());
		String edition = checkForWildcards(cpe.getEdition());
		String other = checkForWildcards(cpe.getOther());
		String part = checkForWildcards(cpe.getPart());
		String product = checkForWildcards(cpe.getProduct());
		String sw_edition = checkForWildcards(cpe.getSw_edition());
		String target_hw = checkForWildcards(cpe.getTarget_hw());
		String target_sw = checkForWildcards(cpe.getTarget_sw());
		String vendor = checkForWildcards(cpe.getVendor());
		if (cpe_language != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "cpe_language " + cpe_language;
		}
		if (cpe_update != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "cpe_update " + cpe_update;
		}
		if (edition != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "edition " + edition;
		}
		if (other != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "other " + other;
		}
		if (part != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "part " + part;
		}
		if (product != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "product " + product;
		}
		if (sw_edition != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "sw_edition " + sw_edition;
		}
		if (target_hw != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "target_hw " + target_hw;
		}
		if (target_sw != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "target_sw " + target_sw;
		}
		if (vendor != null) {
			selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "vendor " + vendor;
		}
		if (mode.equals("new_only") || mode.contentEquals("untreated")) {
			if (!identifierStrings.isEmpty()) {
				selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "identifierString IN (";
				String identifierSubQuery = "";
				for (String identifier : identifierStrings) {
					identifierSubQuery += (identifierSubQuery.equals("") ? "" : ",") + "'" + identifier + "'";
				}
				selectionSubQuery += identifierSubQuery + ")";
				if (!treatedIdentifiers.isEmpty()) {
					selectionSubQuery += (selectionSubQuery.equals("") ? "" : " AND ") + "identifierString NOT IN (";
					String exclusionSubQuery = "";
					for (String identifier : treatedIdentifiers) {
						exclusionSubQuery += (exclusionSubQuery.equals("") ? "" : ",") + "'" + identifier + "'";
					}
					selectionSubQuery += exclusionSubQuery + ")";
				}

			} else {
				return new ArrayList<AggregatedVulnerability>();
			}
		}
		String version = cpe.getVersion();
		String versionSubQuery = "";
		if (version.equals("-")) {
			versionSubQuery = " version =\"" + version + "\" ";
		} else if (!version.equals("*")) {
			versionSubQuery = " IF(versionStart IS NULL,TRUE,IF(startIncluding IS TRUE,versionStart<=\"" + (version)
					+ "\",versionStart<\"" + (version)
					+ "\")) AND IF(versionEnd IS NULL,TRUE,IF(endIncluding IS TRUE,versionEnd >= \"" + (version)
					+ "\",versionEnd > \"" + (version)
					+ "\")) AND IF((versionStart IS NULL) AND (versionEnd IS NULL),version = \"" + (version)
					+ "\",TRUE)";
		} else {
			versionSubQuery = "";
		}
		String sql = "SELECT * FROM vulnerableplatforms " + (selectionSubQuery.equals("") ? "" : "WHERE ")
				+ selectionSubQuery
				+ (selectionSubQuery.equals("") ? (versionSubQuery.equals("") ? "" : " WHERE ")
						: (versionSubQuery.equals("") ? "" : " AND "))
				+ versionSubQuery + " ORDER BY cveYear DESC, cveID DESC;";
		System.out.println("query: " + sql);
		Session session = super.hibernateSessionFactory.openSession();
		NativeQuery<VulnerablePlatformsFact> query = session.createNativeQuery(sql, VulnerablePlatformsFact.class);
		List<VulnerablePlatformsFact> result = query.list();
		session.close();
		List<AggregatedVulnerability> vulnList = new ArrayList();
		session = super.hibernateSessionFactory.openSession();
		for (VulnerablePlatformsFact fact : result) {
			AggregatedVulnerability factVuln = null;
			boolean doNotAddToList = false;
			for (AggregatedVulnerability vuln : vulnList) {
				if (vuln.getCveYear() == fact.getCveYear() && vuln.getCveID() == fact.getCveID()) {
					factVuln = vuln;
					doNotAddToList = true;
					break;
				}
			}
			if (doNotAddToList) {
				vulnList.remove(vulnList.indexOf(factVuln));
			}
			if (factVuln == null) {
				factVuln = new AggregatedVulnerability();
			}
			// Get description for VulnerablePlatformsFact

			if (!doNotAddToList) {
				sql = "SELECT * FROM description WHERE cveYear = :cveYear AND cveID = :cveID";
				NativeQuery<Description> descQuery = session.createNativeQuery(sql, Description.class);
				descQuery.setParameter("cveYear", fact.getCveYear());
				descQuery.setParameter("cveID", fact.getCveID());
				List<Description> descriptions = descQuery.list();
				for (Description description : descriptions) {
					factVuln.setDescriptionText(description.getDescriptionText());
					break;
				}
				/*
				 * Now create the Aggregated Vulnerability public AggregatedVulnerability(int
				 * cveYear, int cveID, String identifierString, String vectorString, String
				 * attackVector, String attackComplexity, String privilegesRequired, String
				 * userInteraction, String scope, String confidentialityImpact, String
				 * integrityImpact, String availabilityImpact, String baseScore, String
				 * baseSeverity, String exploitabilityScore, String impactScore, int confID,
				 * String relationship_operator, String node_operator, int combinationID, String
				 * operator, boolean vulnerable, String uRI, boolean standardized, String
				 * descriptionText)
				 */
				factVuln.setCveID(fact.getCveID());
				factVuln.setCveYear(fact.getCveYear());
				factVuln.setIdentifierString(fact.getIdentifierString());
				factVuln.setVectorString(fact.getVectorString());
				factVuln.setAttackVector(fact.getAttackVector());
				factVuln.setAttackComplexity(fact.getAttackComplexity());
				factVuln.setPrivilegesRequired(fact.getPrivilegesRequired());
				factVuln.setIntegrityImpact(fact.getIntegrityImpact());
				factVuln.setAvailabilityImpact(fact.getAvailabilityImpact());
				factVuln.setBaseScore(fact.getBaseScore());
				factVuln.setBaseSeverity(fact.getBaseSeverity());
				factVuln.setExploitabilityScore(fact.getExploitabilityScore());
				factVuln.setImpactScore(fact.getImpactScore());
				factVuln.setConfID(0);
				factVuln.setRelationship_operator(null);
				factVuln.setNode_operator(null);
				factVuln.setCombinationID(0);
				factVuln.setOperator(null);
				factVuln.setVulnerable(fact.isVulnerable());
				factVuln.setStandardized(fact.isStandardized());
				factVuln.setURI(cpe.getURI());
				factVuln.setCpeID(cpe.getCpeID());
			}
			// Redo of Platform Combination Preparation

			// Case 1: Platform_combination Operator is OR
			// Case 1A: Conf Operator is AND -->
			// Case 1AA: Platform_combination' is OR --> Create combinations out of CPE and
			// all CPE' under Platform_combination' DONE
			// Case 1AB: Platform_combination' is AND --> Create one combination with the
			// CPE and all CPE'
			// Case 1B: Conf Operator is OR --> Do Nothing

			// Case 2: Platform_combination Operator is AND
			// Case 2A Conf Operator is AND -->
			// Case 2AA: Platform_combination' is OR --> Create combinations of all CPE
			// under Platform_combination with each CPE' under Platform_combination'
			// Case 2AB: Platform_combination' is AND --> Create one combination with all
			// CPE under Platform_combination with all CPE' under Platform_combination'
			// Case 2B Conf Operator is OR --> Create one combination out of CPE under
			// Platform_combination

			sql = "SELECT platform_combination.* FROM platform_combination WHERE combinationID = :combinationID";
			NativeQuery<PlatformCombination> combinationQuery = session.createNativeQuery(sql,
					PlatformCombination.class);
			combinationQuery.setParameter("combinationID", fact.getCombinationID());
			List<PlatformCombination> platformCombinations = combinationQuery.list();
			List<VulnerablePlatformCombination> vulnerablePlatformCombinations = new ArrayList<VulnerablePlatformCombination>();
			for (PlatformCombination combination : platformCombinations) {
				sql = "SELECT configuration_node.* FROM configuration_node WHERE confID = :confID";
				NativeQuery<ConfigurationNode> configurationQuery = session.createNativeQuery(sql,
						ConfigurationNode.class);
				configurationQuery.setParameter("confID", combination.getConfID());
				List<ConfigurationNode> configurationNodes = configurationQuery.list();
				for (ConfigurationNode confNode : configurationNodes) {
					// Case 1
					if (combination.getOperator() == null) {
						combination.setOperator("OR");
					}
					if (combination.getOperator().equals("OR")) {
						// Case 1A
						if (confNode.getNode_operator() != null && confNode.getNode_operator().equals("AND")) {
							sql = "SELECT platform_combination.* FROM platform_combination WHERE confID = :confID AND NOT combinationID = :combinationID";
							combinationQuery = session.createNativeQuery(sql, PlatformCombination.class);
							combinationQuery.setParameter("confID", confNode.getConfID());
							combinationQuery.setParameter("combinationID", combination.getCombinationID());
							List<PlatformCombination> platformCombinationsDashed = combinationQuery.list(); // Platform_combination'
							for (PlatformCombination combinationDashed : platformCombinationsDashed) {
								sql = "SELECT cpe.* FROM cpe JOIN contains_platform USING (cpeID) WHERE combinationID = :combinationID";
								NativeQuery<CPECore> cpeQuery = session.createNativeQuery(sql, CPECore.class);
								cpeQuery.setParameter("combinationID", combinationDashed.getCombinationID());
								List<CPECore> combinatedCPEs = cpeQuery.list();
								// Case 1AA
								if (combinationDashed.getOperator() == null) {
									combinationDashed.setOperator("OR");
								}
								if (combinationDashed.getOperator().equals("OR")) {
									for (CPECore combinatedCPE : combinatedCPEs) {
										if (!checkForEquality(cpe, combinatedCPE)) {
											VulnerablePlatformCombination vulnerablePlatformCombination = new VulnerablePlatformCombination();
											vulnerablePlatformCombination.setOperator("AND");
											vulnerablePlatformCombination.getPlatforms().add(cpe);
											vulnerablePlatformCombination.getPlatforms().add(combinatedCPE);
											vulnerablePlatformCombinations.add(vulnerablePlatformCombination);
										}
									}
								}
								// Case 1AB
								else if (combinationDashed.getOperator().equals("AND")) {
									VulnerablePlatformCombination vulnerablePlatformCombination = new VulnerablePlatformCombination();
									vulnerablePlatformCombination.setOperator("AND");
									vulnerablePlatformCombination.getPlatforms().add(cpe);
									List<CPECore> nonEqualCPE = new ArrayList<CPECore>();
									for (CPECore cpe2 : combinatedCPEs) {
										if (!checkForEquality(cpe, cpe2)) {
											nonEqualCPE.add(cpe2);
										}
									}
									vulnerablePlatformCombination.getPlatforms().addAll(nonEqualCPE);
									vulnerablePlatformCombinations.add(vulnerablePlatformCombination);
								}

							}
						}
						// Case 1B Do nothing
					}
					// Case 2
					if (combination.getOperator().equals("AND")) {
						// Case 2B
						if (confNode.getNode_operator() == null) {
							confNode.setNode_operator("OR");
						}
						if (confNode.getNode_operator().equals("OR")) {
							sql = "SELECT cpe.* FROM cpe JOIN contains_platform USING (cpeID) WHERE combinationID = :combinationID";
							NativeQuery<CPECore> cpeQuery = session.createNativeQuery(sql, CPECore.class);
							cpeQuery.setParameter("combinationID", combination.getCombinationID());
							List<CPECore> cpes = cpeQuery.list(); // CPEs
							VulnerablePlatformCombination vulnerablePlatformCombination = new VulnerablePlatformCombination();
							vulnerablePlatformCombination.setOperator("AND");
							List<CPECore> nonEqualCPE = new ArrayList<CPECore>();
							for (CPECore cpe2 : cpes) {
								if (!checkForEquality(cpe, cpe2)) {
									nonEqualCPE.add(cpe2);
								}
							}
							vulnerablePlatformCombination.getPlatforms().addAll(nonEqualCPE);
							vulnerablePlatformCombinations.add(vulnerablePlatformCombination);
						} // Case 2A
						else if (confNode.getNode_operator().equals("AND")) {
							sql = "SELECT platform_combination.* FROM platform_combination WHERE confID = :confID AND NOT combinationID = :combinationID";
							combinationQuery = session.createNativeQuery(sql, PlatformCombination.class);
							combinationQuery.setParameter("confID", confNode.getConfID());
							combinationQuery.setParameter("combinationID", combination.getCombinationID());
							List<PlatformCombination> platformCombinationsDashed = combinationQuery.list(); // Platform_combination'
							for (PlatformCombination combinationDashed : platformCombinationsDashed) {
								sql = "SELECT cpe.* FROM cpe JOIN contains_platform USING (cpeID) WHERE combinationID = :combinationID";
								NativeQuery<CPECore> cpeQuery = session.createNativeQuery(sql, CPECore.class);
								cpeQuery.setParameter("combinationID", combination.getCombinationID());
								List<CPECore> cpes = cpeQuery.list(); // CPEs
								List<CPECore> nonEqualCPE = new ArrayList<CPECore>();
								for (CPECore cpe2 : cpes) {
									if (!checkForEquality(cpe, cpe2)) {
										nonEqualCPE.add(cpe2);
									}
								}
								sql = "SELECT cpe.* FROM cpe JOIN contains_platform USING (cpeID) WHERE combinationID = :combinationID";
								cpeQuery = session.createNativeQuery(sql, CPECore.class);
								cpeQuery.setParameter("combinationID", combinationDashed.getCombinationID());
								List<CPECore> combinatedCPEs = cpeQuery.list(); // CPEs'
								// Case 2AA
								if (combinationDashed.getOperator() == null) {
									combinationDashed.setOperator("OR");
								}
								if (combinationDashed.getOperator().equals("OR")) {
									for (CPECore cpeDash : combinatedCPEs) {
										if (!checkForEquality(cpe, cpeDash)) {
											VulnerablePlatformCombination vulnerablePlatformCombination = new VulnerablePlatformCombination();
											vulnerablePlatformCombination.setOperator("AND");
											vulnerablePlatformCombination.getPlatforms().addAll(nonEqualCPE);
											vulnerablePlatformCombination.getPlatforms().add(cpeDash);
											vulnerablePlatformCombinations.add(vulnerablePlatformCombination);
										}
									}
								}
								// Case 2AB
								else if (combinationDashed.getOperator().equals("AND")) {
									VulnerablePlatformCombination vulnerablePlatformCombination = new VulnerablePlatformCombination();
									vulnerablePlatformCombination.setOperator("AND");
									vulnerablePlatformCombination.getPlatforms().addAll(nonEqualCPE);
									List<CPECore> nonEqualDashedCPE = new ArrayList<CPECore>();
									for (CPECore cpe2 : combinatedCPEs) {
										if (!checkForEquality(cpe, cpe2)) {
											nonEqualDashedCPE.add(cpe2);
										}
									}
									vulnerablePlatformCombination.getPlatforms().addAll(nonEqualDashedCPE);
									vulnerablePlatformCombinations.add(vulnerablePlatformCombination);
								}
							}
						}
					}
				}
			}

			// Check if there are any important platform combinations required for this CVE
			// First check if there are any AND correlations between multiple platform
			// groups
			/*
			 * sql =
			 * "SELECT configuration_node.* FROM configuration_node JOIN platform_combination ON (platform_combination.confID = configuration_node.confID) WHERE platform_combination.combinationID = :combinationID AND NOT platform_combination.operator='OR'"
			 * ; NativeQuery<ConfigurationNode> cNQuery = session.createNativeQuery(sql,
			 * ConfigurationNode.class); cNQuery.setParameter("combinationID",
			 * fact.getCombinationID()); List<ConfigurationNode> cNList = cNQuery.list();
			 * boolean confNodeOperatorIsAnd = false; List<VulnerablePlatformCombination>
			 * combinedPlatformList = new ArrayList(); for (ConfigurationNode cNNode :
			 * cNList) { if (cNNode.getNode_operator().equals("AND")) {
			 * confNodeOperatorIsAnd = true; sql =
			 * "SELECT platform_combination.* FROM platform_combination WHERE confId = :confID AND NOT combinationId = :notCombinationID"
			 * ; NativeQuery<PlatformCombination> pcQuery = session.createNativeQuery(sql,
			 * PlatformCombination.class); pcQuery.setParameter("confID",
			 * cNNode.getConfID()); pcQuery.setParameter("notCombinationID",
			 * fact.getCombinationID()); List<PlatformCombination> pcList = pcQuery.list();
			 * List<VulnerablePlatformCombination> vulnPlatformList = new ArrayList(); for
			 * (PlatformCombination pComb : pcList) { VulnerablePlatformCombination
			 * vulnPlatformCombination = new VulnerablePlatformCombination(); if
			 * (pComb.getOperator() == null || pComb.getOperator().equals("AND")) {
			 * vulnPlatformCombination.setOperator("AND"); } else if
			 * (pComb.getOperator().equals("OR")) {
			 * vulnPlatformCombination.setOperator("OR"); } sql =
			 * "SELECT contains_platform.* FROM contains_platform WHERE combinationID = :combinationID"
			 * ; NativeQuery<ContainsPlatform> cnPlatformQuery =
			 * session.createNativeQuery(sql, ContainsPlatform.class);
			 * cnPlatformQuery.setParameter("combinationID", pComb.getCombinationID());
			 * List<ContainsPlatform> cnPlatformList = cnPlatformQuery.list(); for
			 * (ContainsPlatform cnPlatform : cnPlatformList) { if (cnPlatform.getCpeID() ==
			 * fact.getCpeID()) { continue; } else { sql =
			 * "SELECT cpe.* FROM cpe WHERE cpeID = :cpeID"; NativeQuery<CPECore> cpeQuery =
			 * session.createNativeQuery(sql, CPECore.class); cpeQuery.setParameter("cpeID",
			 * cnPlatform.getCpeID()); List<CPECore> cpeList = cpeQuery.list();
			 * vulnPlatformCombination.getPlatforms().addAll(cpeList); } }
			 * vulnPlatformList.add(vulnPlatformCombination); }
			 * 
			 * VulnerablePlatformCombination currentPlatformCombination = new
			 * VulnerablePlatformCombination(); boolean noOrPlatformCombinations = true; for
			 * (VulnerablePlatformCombination vuln : vulnPlatformList) { if
			 * (vuln.getOperator().equals("AND")) {
			 * currentPlatformCombination.getPlatforms().addAll(vuln.getPlatforms()); }else
			 * if (vuln.getOperator().equals("OR")) { noOrPlatformCombinations = false; for
			 * (CPECore currCPE : vuln.getPlatforms()) { VulnerablePlatformCombination comb
			 * = new VulnerablePlatformCombination();
			 * comb.getPlatforms().addAll(currentPlatformCombination.getPlatforms());
			 * comb.getPlatforms().add(currCPE); combinedPlatformList.add(comb); } } }
			 * /*boolean noOrPlatformCombinations = true;
			 * System.out.println("Vulnerable Platform Combination List Size 2: "
			 * +vulnPlatformList.size()); for (VulnerablePlatformCombination vuln :
			 * vulnPlatformList) { if (vuln.getOperator().equals("OR")) {
			 * noOrPlatformCombinations = false; for (CPECore currCPE : vuln.getPlatforms())
			 * { VulnerablePlatformCombination comb = new VulnerablePlatformCombination();
			 * comb.getPlatforms().addAll(currentPlatformCombination.getPlatforms());
			 * comb.getPlatforms().add(currCPE); combinedPlatformList.add(comb); } } } if
			 * (!noOrPlatformCombinations) {
			 * combinedPlatformList.add(currentPlatformCombination); } }else {
			 * factVuln.setSingularApplicable(true); } } if (!confNodeOperatorIsAnd) { sql =
			 * "SELECT platform_combination.* FROM platform_combination WHERE combinationId = :combinationID"
			 * ; NativeQuery<PlatformCombination> pcQuery = session.createNativeQuery(sql,
			 * PlatformCombination.class); pcQuery.setParameter("combinationID",
			 * fact.getCombinationID()); List<PlatformCombination> pcList = pcQuery.list();
			 * for (PlatformCombination pComb : pcList) { if (pComb.getOperator() != null &&
			 * pComb.getOperator().equals("AND")) { sql =
			 * "SELECT cpe.* FROM cpe JOIN contains_platform ON (cpe.cpeID = contains_platform.cpeID) WHERE contains_platform.combinationID = :combinationID;"
			 * ; NativeQuery<CPECore> cpeQuery = session.createNativeQuery(sql,
			 * CPECore.class); cpeQuery.setParameter("combinationID",
			 * fact.getCombinationID()); List<CPECore> cpeList = cpeQuery.list();
			 * VulnerablePlatformCombination platformCombination = new
			 * VulnerablePlatformCombination();
			 * platformCombination.getPlatforms().addAll(cpeList);
			 * combinedPlatformList.add(platformCombination); }else { sql =
			 * "SELECT configurationNode.* FROM configuration_node WHERE confId = :confId";
			 * NativeQuery<ConfigurationNode> confQuery = session.createNativeQuery(sql,
			 * ConfigurationNode.class); confQuery.setParameter("confId",
			 * pComb.getConfID()); List<ConfigurationNode> confNodes = confQuery.list();
			 * 
			 * } } }
			 */
			factVuln.getPlatformCombinations().addAll(vulnerablePlatformCombinations);
			vulnList.add(factVuln);
		}
		session.close();
		return vulnList;
	}

}
