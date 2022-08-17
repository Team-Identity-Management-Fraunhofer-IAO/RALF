package de.ralf.threatmasterkitchen.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sql.data.objects.helpers.attackpatterns.NamedThreat;
import com.sql.data.objects.helpers.attackpatterns.OrderedKillChainPhase;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReport;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControl;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControlExcludedThreats;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactor;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactorQuestion;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestion;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternPlatform;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternPrerequisite;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternSuccessProbability;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.CourseOfActionAttackPattern;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.ThreatCollection;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.VulnerabilityEnablingFactor;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.XMitrePlatform;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.IntrusionSet;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.Malware;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.Reference;
import com.sql.data.objects.persistence.threatmaster.organizations.Organization;
import com.sql.data.objects.persistence.threatmaster.organizations.RiskCategory;
import com.sql.data.objects.persistence.threatmaster.organizations.RiskPhi;
import com.sql.data.objects.persistence.threatmaster.riskassessment.AttackMotivatingQuestionResponse;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReport;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportCapabilityKillerSuccessProbability;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreat;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreatControl;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreatControlPivotedThreat;
import com.sql.data.objects.persistence.threatmaster.riskassessment.VulnerabilityEnablingQuestionResponse;
import com.sql.data.objects.persistence.threatmaster.riskassessment.VulnerabilityEnablingQuestionnaireResponse;
import com.sql.data.objects.persistence.threatmaster.risks.AttackMotivatingQuestionnaireResponse;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCapabilityKiller;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrder;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrderList;
import com.sql.data.objects.persistence.threatmaster.risks.CapabilityKiller;
import com.sql.data.objects.persistence.threatmaster.services.Service;
import com.sql.data.objects.persistence.threatmaster.services.ServiceExistenceProbability;
import com.sql.data.provider.threatmaster.AttackFactorQuestionProvider;
import com.sql.data.provider.threatmaster.AttackFactorQuestionnaireProvider;
import com.sql.data.provider.threatmaster.BusinessRiskOrderProvider;
import com.sql.data.provider.threatmaster.BusinessRiskProvider;
import com.sql.data.provider.threatmaster.CapabilityKillerProvider;
import com.sql.data.provider.threatmaster.ControlReportProvider;
import com.sql.data.provider.threatmaster.OrganizationProvider;
import com.sql.data.provider.threatmaster.RiskCategoryProvider;
import com.sql.data.provider.threatmaster.RiskCategoryProvider.RiskCategoryType;
import com.sql.data.provider.threatmaster.RiskPhiProvider;
import com.sql.data.provider.threatmaster.RiskReportProvider;
import com.sql.data.provider.threatmaster.ServiceProvider;
import com.sql.data.provider.threatmaster.datawarehouse.AttackFactorProvider;
import com.sql.data.provider.threatmaster.datawarehouse.AttackPatternPlatformProvider;
import com.sql.data.provider.threatmaster.datawarehouse.AttackPatternPrerequisiteProvider;
import com.sql.data.provider.threatmaster.datawarehouse.AttackPatternSuccessProbabilityProvider;
import com.sql.data.provider.threatmaster.datawarehouse.CourseOfActionAttackPatternProvider;
import com.sql.data.provider.threatmaster.datawarehouse.ThreatCollectionProvider;
import com.sql.data.provider.threatmaster.datawarehouse.VulnerabilityEnablingFactorProvider;

import de.ralf.threatmasterkitchen.MainWebAppInitializer;
import de.ralf.threatmasterkitchen.controller.datatransfer.DeterminedRisk;
import de.ralf.threatmasterkitchen.controller.presentation.OrderedBusinessRisk;
import de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects.AssessedThreat;
import de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects.AssessmentControl;
import de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects.AssessmentControl.ControlApplication;
import de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects.RiskReportArrow;
import de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects.SuccessProbability;
import de.ralf.threatmasterkitchen.security.utils.SecurityUtils;

/*
 * Implements the frontend workflow
 */
@Controller
public class PageController extends AbstractController {
	
	private final static String adress_dashboard = "Dashboard";
	private final static String adress_application_modeling = "Application_Modeling";
	private final static String adress_assessment_schedules = "Assessment_Schedules";
	private final static String adress_assessment_reports = "Assessment_Reports";
	private final static String adress_settings = "Settings";
	private final static String adress_administration = "Administration";
	private final static String adress_vulnerability_management = "Vulnerability_Management";
	private final static String address_administration_group_management = "Administration/Groups";
	private final static String address_administration_application_assignments = "Administration/Stacks";
	private final static String address_administration_user_management = "Administration/Users";
	private final static String address_administration_data_harvesting = "Administration/DataHarvesting";
	private final static String address_vulnerability_list = "Vulnerability_Management";
	private final static String address_subnet_modelling = "Subnet_Modelling/Subnets";

	private final static String adress_logoff = "perform_logout";

	@GetMapping(value = { "/ServiceDefinition", "/ServiceDefinition/ServiceModelling/{service_id}" })
	public String createServiceModellingView(ModelMap model,
			@PathVariable(name = "service_id", required = false) Integer service_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Service Modelling");
		model.addAttribute("function", "service_description");
		model.addAttribute("application", "service_definition");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		int organization_id = 0;
		if (isAuthorized) {
			OrganizationProvider organizationProvider = new OrganizationProvider();
			List<Organization> organizations = organizationProvider.getAllOrganizations();
			model.addAttribute("organizations", organizations);
			ServiceProvider serviceProvider = new ServiceProvider();
			List<Service> services = serviceProvider.getAllServices();
			model.addAttribute("services", services);
			Map<Integer, List<BusinessRisk>> businessRisksPerService = new HashMap<Integer, List<BusinessRisk>>();
			BusinessRiskProvider businessRiskProvider = new BusinessRiskProvider();
			for (Service service : services) {
				List<BusinessRisk> businessRisks = businessRiskProvider
						.getAssignedBusinessRisksForService(service.getService_id());
				businessRisksPerService.put(service.getService_id(), businessRisks);
			}
			model.addAttribute("risks_per_service", businessRisksPerService);			
			if (service_id == null || service_id == -1) {
				model.addAttribute("function2", "serviceModelling");
				model.addAttribute("service_id", -1);
				List<BusinessRisk> businessRisks = businessRiskProvider.getAllBusinessRisksForService(0);
				Map<Integer, List<CapabilityKiller>> killersPerBusinessRisk = new HashMap<Integer, List<CapabilityKiller>>();
				CapabilityKillerProvider capabilityKillerProvider = new CapabilityKillerProvider();
				for (BusinessRisk businessRisk : businessRisks) {
					List<CapabilityKiller> capabilityKillers = capabilityKillerProvider
							.getAllCapabilityKillersForBusinessRisk(businessRisk.getBusiness_risk_id());
					killersPerBusinessRisk.put(businessRisk.getBusiness_risk_id(), capabilityKillers);
				}
				model.addAttribute("available_business_risks", businessRisks);
				model.addAttribute("capability_killers_per_business_risk", killersPerBusinessRisk);
			} else if (service_id != null && service_id > 0) {
				model.addAttribute("function2", "serviceModelling");
				Service service = new Service();
				service.setService_id(service_id);
				service = services.get(services.indexOf(service));
				organization_id = service.getOrganization_id();
				if (service != null) {
					model.addAttribute("service_id", service.getService_id());
					model.addAttribute("service_name", service.getService_name());
					model.addAttribute("service_description", service.getService_description());
					List<BusinessRisk> businessRisks = businessRiskProvider
							.getAssignedBusinessRisksForService(service.getService_id());
					List<BusinessRisk> availableBusinessRisks = businessRiskProvider
							.getAllBusinessRisksForService(service.getService_id());
					List<BusinessRisk> sanitizedBusinessRisks = new ArrayList<BusinessRisk>();
					Map<Integer, List<CapabilityKiller>> killersPerBusinessRisk = new HashMap<Integer, List<CapabilityKiller>>();
					CapabilityKillerProvider capabilityKillerProvider = new CapabilityKillerProvider();
					for (BusinessRisk businessRisk : availableBusinessRisks) {
						if (!businessRisks.contains(businessRisk)) {
							sanitizedBusinessRisks.add(businessRisk);
						}
						List<CapabilityKiller> capabilityKillers = capabilityKillerProvider
								.getAllCapabilityKillersForBusinessRisk(businessRisk.getBusiness_risk_id());
						killersPerBusinessRisk.put(businessRisk.getBusiness_risk_id(), capabilityKillers);
					}
					model.addAttribute("capability_killers_per_business_risk", killersPerBusinessRisk);
					model.addAttribute("available_business_risks", sanitizedBusinessRisks);
					model.addAttribute("business_risks", businessRisks);
				}
			}
		}
		addPageStandardsToModel(model, isAdmin);
		createPageStandardsForOrganizationAndServiceSubpages(model, organization_id, service_id==null?0:service_id);
		return "subnet_modelling";
	}

	@GetMapping(value = "/ThreatAdministration/PlatformManagement")
	public String createThreatAdministrationView(ModelMap model, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Service Modelling");
		model.addAttribute("function", "risk_identification");
		model.addAttribute("application", "service_definition");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			AttackPatternPlatformProvider attackPatternPlatformProvider = new AttackPatternPlatformProvider();
			List<AttackPatternPlatform> attackPatternPlatformAssignments = attackPatternPlatformProvider
					.getAllAttackPlatternPlatformAssignments();
			Map<Integer, List<AttackPatternPlatform>> assignmentsPerPattern = new LinkedHashMap<Integer, List<AttackPatternPlatform>>();
			for (AttackPatternPlatform attackPatternPlatformAssignment : attackPatternPlatformAssignments) {
				if (!assignmentsPerPattern.containsKey(attackPatternPlatformAssignment.getAttack_pattern_id())) {
					LinkedList<AttackPatternPlatform> platformList = new LinkedList<AttackPatternPlatform>();
					assignmentsPerPattern.put(attackPatternPlatformAssignment.getAttack_pattern_id(), platformList);
				}
				assignmentsPerPattern.get(attackPatternPlatformAssignment.getAttack_pattern_id())
						.add(attackPatternPlatformAssignment);
			}
			System.out.println(assignmentsPerPattern);
			model.addAttribute("assignmentsPerPattern", assignmentsPerPattern);
			model.addAttribute("function2", "platform_management");
			addPageStandardsToModel(model, isAdmin);
		}
		return "threat_admin";
	}

	
	
	@GetMapping(value = {"/ThreatAdministration/SuccessProbabilities/{collection_id}","/ThreatAdministration/SuccessProbabilities"})
	public String createThreatAdministrationSuccessProbabilitiesView(ModelMap model, @PathVariable(name="collection_id", required = false) String collection_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Threat Administration - Success Probabilities");
		model.addAttribute("function", "threat_administration");
		model.addAttribute("application", "success_probabilities");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			/*
			 * 
			 * Map mit Threats und Collection
			 * Ansicht für einen Threat mit den Countermeasures
			 */
			ThreatCollectionProvider collectionProvider = new ThreatCollectionProvider();
			List<ThreatCollection> collections = collectionProvider.getAllThreatCollections();
			model.addAttribute("collections", collections);
			if (collection_id != null) {
				model.addAttribute("function3","collection_selected");
				CourseOfActionAttackPatternProvider courseOfActionAttackPatternProvider = new CourseOfActionAttackPatternProvider();
				List<CourseOfActionAttackPattern> courseOfActionAttackPatterns = courseOfActionAttackPatternProvider.getCoursesOfActionForCollection(collection_id);
				Map<OrderedKillChainPhase, List<CourseOfActionAttackPattern>> patternsPerKillChainPhase = new HashMap<OrderedKillChainPhase, List<CourseOfActionAttackPattern>>();
				Map<OrderedKillChainPhase, List<Integer>> includedAttackPatternsPerKillChainPhase = new HashMap<OrderedKillChainPhase, List<Integer>>();
				Map<Integer, List<Integer>> attackPatternProbabilityIDs = new HashMap<Integer, List<Integer>>();
				Map<Integer, List<AttackPatternSuccessProbability>> patternProbabilities = new HashMap<Integer, List<AttackPatternSuccessProbability>>();
				Map<Integer, List<CourseOfActionAttackPattern>> allPatterns = new HashMap<Integer, List<CourseOfActionAttackPattern>>();
				int minKillChainOrder = 1;
				int maxKillChainOrder = 0;
				AttackPatternPrerequisiteProvider prereqProvider = new AttackPatternPrerequisiteProvider();
				AttackPatternSuccessProbabilityProvider successProbabilityProvider = new AttackPatternSuccessProbabilityProvider();
				AttackFactorProvider attackFactorProvider = new AttackFactorProvider();
				AttackPatternPlatformProvider platformProvider = new AttackPatternPlatformProvider();
				List<XMitrePlatform> platforms = platformProvider.getAllPlatformsInCollection(collection_id);
				Map<Integer, XMitrePlatform> platformsPerID = new HashMap<Integer, XMitrePlatform>();
				for (XMitrePlatform platform : platforms) {
					if (!platformsPerID.containsKey(platform.getX_mitre_platforms_id())) {
						platformsPerID.put(platform.getX_mitre_platforms_id(), platform);
					}
				}
				for (CourseOfActionAttackPattern coAPattern : courseOfActionAttackPatterns) {
					List<OrderedKillChainPhase> patternPhases = prereqProvider.getKillChainPhasesForAttackPattern(coAPattern.getAttack_pattern_id());
					killChainLoop:
					for (OrderedKillChainPhase patternPhase : patternPhases) {
						minKillChainOrder = patternPhase.getKill_chain_order()<minKillChainOrder?patternPhase.getKill_chain_order():minKillChainOrder;
						maxKillChainOrder = patternPhase.getKill_chain_order()>maxKillChainOrder?patternPhase.getKill_chain_order():maxKillChainOrder;
						if (!patternsPerKillChainPhase.containsKey(patternPhase)) {
							patternsPerKillChainPhase.put(patternPhase, new ArrayList<CourseOfActionAttackPattern>());
							includedAttackPatternsPerKillChainPhase.put(patternPhase, new ArrayList<Integer>());
						}
						if (!includedAttackPatternsPerKillChainPhase.get(patternPhase).contains(coAPattern.getAttack_pattern_id())){
							patternsPerKillChainPhase.get(patternPhase).add(coAPattern);
							includedAttackPatternsPerKillChainPhase.get(patternPhase).add(coAPattern.getAttack_pattern_id());
						}						
					}
					if (!attackPatternProbabilityIDs.containsKey(coAPattern.getAttack_pattern_id())) {
						List<AttackPatternSuccessProbability> successProbabilities = successProbabilityProvider.getSuccessProbabilitiesForAttackPattern(coAPattern.getAttack_pattern_id());
						attackPatternProbabilityIDs.put(coAPattern.getAttack_pattern_id(),new ArrayList<Integer>());
						for (AttackPatternSuccessProbability successProbability : successProbabilities) {
							if (!attackPatternProbabilityIDs.get(coAPattern.getAttack_pattern_id()).contains(successProbability.getC_success_probability_id())) {
								attackPatternProbabilityIDs.get(coAPattern.getAttack_pattern_id()).add(successProbability.getC_success_probability_id());
							}
							if (!patternProbabilities.containsKey(successProbability.getC_success_probability_id())) {
								patternProbabilities.put(successProbability.getC_success_probability_id(),new ArrayList<AttackPatternSuccessProbability>());
							}
							boolean addToList = true;
							for (AttackPatternSuccessProbability listPattern : patternProbabilities.get(successProbability.getC_success_probability_id())) {
								if ((listPattern.getC_vulnerability_enabling_factor_id() == successProbability.getC_vulnerability_enabling_factor_id()) && (listPattern.getCourse_of_action_id() == successProbability.getCourse_of_action_id())){
									addToList = false;
									break;
								}
							}
							if (addToList) {
								patternProbabilities.get(successProbability.getC_success_probability_id()).add(successProbability);
							}
						}
					}
					if (!allPatterns.containsKey(coAPattern.getAttack_pattern_id())) {
						allPatterns.put(coAPattern.getAttack_pattern_id(), new ArrayList<CourseOfActionAttackPattern>());
					}
					allPatterns.get(coAPattern.getAttack_pattern_id()).add(coAPattern);
				}
				List<VulnerabilityEnablingFactor> enablingFactors = attackFactorProvider.getAllVulnerabilityEnablingFactors();
				model.addAttribute("patternProbabilities",patternProbabilities);
				model.addAttribute("probabilityIDs",attackPatternProbabilityIDs);
				model.addAttribute("patternsPerKillChainPhase",patternsPerKillChainPhase);
				model.addAttribute("enablingFactors",enablingFactors);
				model.addAttribute("platforms", platformsPerID);
				model.addAttribute("courseOfActionAttackPatterns",courseOfActionAttackPatterns);
				model.addAttribute("allPatterns",allPatterns);
			}
			
			//Output here.. Frontend here..
			
			model.addAttribute("function2", "success_probabilities");
			addPageStandardsToModel(model, isAdmin);
			return "threat_admin";
		}
		return null;
	}

	/*
	 * Function to pivot the threats of a collection. Threats are pivoted according
	 * to their kill chain and their association with an intrusion set
	 * 
	 * @GetMapping(value = "/pivotThreats") public String
	 * createThreatPrivots(ModelMap model, Authentication authentication) { boolean
	 * isAdmin = new SecurityUtils().isAdmin(authentication); UserDetails
	 * userDetails = (UserDetails) authentication.getPrincipal();
	 * model.addAttribute("principal", userDetails.getUsername());
	 * model.addAttribute("pageTitle", "RALF - Service Modelling");
	 * model.addAttribute("function", "risk_identification");
	 * model.addAttribute("application", "service_definition"); boolean isAuthorized
	 * = false; if (isAdmin) { isAuthorized = true; } if (isAuthorized) {
	 * AttackPatternSuccessProbabilityProvider
	 * attackPatternSuccessProbabilityProvider = new
	 * AttackPatternSuccessProbabilityProvider();
	 * attackPatternSuccessProbabilityProvider.createThreatPivotsForCollection(
	 * "95ecc380-afe9-11e4-9b6c-751b66dd541e"); return "login"; } return null; }
	 */
	@GetMapping(value = "/AssessmentPreferences/{organization_id}/{service_id}/riskValueSettings")
	public String createRiskValueSettingsInterface(ModelMap model, @PathVariable(name="organization_id", required = true) Integer organization_id, @PathVariable(name="service_id", required = true) Integer service_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Assessment Preferences - Risk Classes and Risk Component Weighing");
		model.addAttribute("function", "threat_administration");
		model.addAttribute("application", "success_probabilities");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			/*
			 * Risk Classes
			 */
			RiskCategoryProvider riskCategoryProvider = new RiskCategoryProvider();
			List<RiskCategory> riskCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(organization_id, service_id, RiskCategoryProvider.RiskCategoryType.Risk);
			List<RiskCategory> impactCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(organization_id, service_id, RiskCategoryProvider.RiskCategoryType.Impact);
			List<RiskCategory> probabilityCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(organization_id, service_id, RiskCategoryProvider.RiskCategoryType.Probability);
			model.addAttribute("riskCategories",riskCategories);
			model.addAttribute("impactCategories",impactCategories);
			model.addAttribute("probabilityCategories",probabilityCategories);
			OrganizationProvider organizationProvider = new OrganizationProvider();
			List<Organization> organizations = organizationProvider.getAllOrganizations();
			model.addAttribute("organizations", organizations);
			ServiceProvider serviceProvider = new ServiceProvider();
			List<Service> services = serviceProvider.getAllServices();
			model.addAttribute("services", services);
			model.addAttribute("function2", "riskValueSettings");
			addPageStandardsToModel(model, true);
			return "riskValueAdministration";
		}
		return null;
	}
	
	@GetMapping(value = "/AssessmentPreferences/{organization_id}/{service_id}/riskPhiSettings")
	public String createRiskPhiSettingsInterface(ModelMap model, @PathVariable(name="organization_id", required = true) Integer organization_id, @PathVariable(name="service_id", required = true) Integer service_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Assessment Preferences - Risk Classes and Risk Component Weighing");
		model.addAttribute("function", "threat_administration");
		model.addAttribute("application", "success_probabilities");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			RiskPhiProvider riskPhiProvider = new RiskPhiProvider();
			RiskPhi riskPhi = riskPhiProvider.getLatestRiskPhiForOrganizationAndService(organization_id, service_id, RiskPhiProvider.RiskPhiType.OCCURENCE_PROBABILITY);
			RiskPhi riskPhiRisk = riskPhiProvider.getLatestRiskPhiForOrganizationAndService(organization_id, service_id, RiskPhiProvider.RiskPhiType.RISK);
			model.addAttribute("riskPhi",riskPhi);
			model.addAttribute("riskPhiRisk",riskPhiRisk);
			OrganizationProvider organizationProvider = new OrganizationProvider();
			List<Organization> organizations = organizationProvider.getAllOrganizations();
			model.addAttribute("organizations", organizations);
			ServiceProvider serviceProvider = new ServiceProvider();
			List<Service> services = serviceProvider.getAllServices();
			model.addAttribute("services", services);
			model.addAttribute("function2", "riskPhiSettings");
			RiskCategoryProvider riskCategoryProvider = new RiskCategoryProvider();
			List<RiskCategory> probabilityCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(organization_id, service_id, RiskCategoryProvider.RiskCategoryType.Probability);
			List<RiskCategory> impactCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(organization_id, service_id, RiskCategoryProvider.RiskCategoryType.Impact);
			List<RiskCategory> riskCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(organization_id, service_id, RiskCategoryType.Risk);
			if (riskPhi != null && !probabilityCategories.isEmpty()) {
				Map<RiskCategory, Map<RiskCategory, RiskCategory>> categoryComputation = new LinkedHashMap<RiskCategory, Map<RiskCategory, RiskCategory>>();
				for (RiskCategory probabilityCategory : probabilityCategories) {
					for (RiskCategory refProbCategory : probabilityCategories) {
						long maxRiskPhi = Math.round(((1-riskPhi.getRisk_phi().doubleValue())*probabilityCategory.getRisk_value_max()+riskPhi.getRisk_phi().doubleValue()*refProbCategory.getRisk_value_max()));
						RiskCategory resultingCategory = null;
						if (maxRiskPhi >= refProbCategory.getRisk_value_min() && maxRiskPhi <= refProbCategory.getRisk_value_max()) {
							resultingCategory = refProbCategory;
						}else if (maxRiskPhi >= probabilityCategory.getRisk_value_min() && maxRiskPhi <= probabilityCategory.getRisk_value_max()) {
							resultingCategory = probabilityCategory;
						}else {
							for (RiskCategory testCat : probabilityCategories) {
								if (testCat == probabilityCategory || testCat == refProbCategory) {
									continue;
								}
								if (maxRiskPhi >= testCat.getRisk_value_min() && maxRiskPhi <= testCat.getRisk_value_max()) {
									resultingCategory = testCat;
									break;
								}
							}
						}
						
						if (!categoryComputation.containsKey(probabilityCategory)) {
							categoryComputation.put(probabilityCategory, new LinkedHashMap<RiskCategory,RiskCategory>());
						}
						categoryComputation.get(probabilityCategory).put(refProbCategory, resultingCategory);
						
					}
				}
				model.addAttribute("categoryComputation",categoryComputation);
			}
			if (riskPhiRisk != null && !probabilityCategories.isEmpty() && !impactCategories.isEmpty() && !riskCategories.isEmpty()) {
				Map<RiskCategory, Map<RiskCategory, RiskCategory>> categoryRiskComputation = new LinkedHashMap<RiskCategory, Map<RiskCategory, RiskCategory>>();
				for (RiskCategory impactCategory : impactCategories) {
					for (RiskCategory probabilityCategory : probabilityCategories) {
						long maxRiskPhi = Math.round((1-riskPhiRisk.getRisk_phi().doubleValue())*impactCategory.getRisk_value_max()+riskPhiRisk.getRisk_phi().doubleValue()*probabilityCategory.getRisk_value_max());
						RiskCategory resultingCategory = null;
						for (RiskCategory riskCategory : riskCategories) {
							if (maxRiskPhi >= riskCategory.getRisk_value_min() && maxRiskPhi <= riskCategory.getRisk_value_max()) {
								resultingCategory = riskCategory;
								break;
							}
						}
						if (!categoryRiskComputation.containsKey(impactCategory)) {
							categoryRiskComputation.put(impactCategory, new LinkedHashMap<RiskCategory,RiskCategory>());
						}
						if (probabilityCategory.getCategory_name().equals("High")) {
							System.out.println("Impact "+impactCategory.getRisk_value_max()+" Probability: "+probabilityCategory.getRisk_value_max()+" result: "+maxRiskPhi+" Category: "+resultingCategory.getRisk_value_max());
						}
						categoryRiskComputation.get(impactCategory).put(probabilityCategory,resultingCategory);
					}
				}
				model.addAttribute("categoryRiskComputation", categoryRiskComputation);
			}
			model.addAttribute("probabilityCategories",probabilityCategories);
			model.addAttribute("impactCategories",impactCategories);
			model.addAttribute("riskCategories",riskCategories);
			addPageStandardsToModel(model, true);
			return "riskPhiAdministration";
		}
		return null;
	}
	
	@GetMapping(value="/AssessmentPreferences/{organization_id}/adjustFactorWeights/{service_id}")
	public String createFactorWeighingInterface(ModelMap model, @PathVariable(name="organization_id", required=true) Integer organization_id, @PathVariable(name="service_id", required = true) Integer service_id, Authentication authentication) {
		AttackFactorProvider attackFactorProvider = new AttackFactorProvider();
		List<AttackMotivatingFactor> motivatingFactors = attackFactorProvider.getAllAttackMotivatingFactors();
		List<VulnerabilityEnablingFactor> vulnerabilityEnablingFactors = attackFactorProvider.getAllVulnerabilityEnablingFactors();
		model.addAttribute("motivatingFactors",motivatingFactors);
		model.addAttribute("vulnerabilityEnablingFactors",vulnerabilityEnablingFactors);
		model.addAttribute("organization_id",organization_id);
		model.addAttribute("service_id",service_id);
		model.addAttribute("function2", "attackFactorWeighing");
		addPageStandardsToModel(model, true);
		return "factorWeighingInterface";
	}
	
	@GetMapping(value="/RiskAssessment/{organization_id}/{service_id}/attackExistenceQuestionnaire")
	public String createAttackExistenceQuestionnaire(ModelMap model, @PathVariable(name="organization_id", required = true) Integer organization_id, @PathVariable(name="service_id") Integer service_id, Authentication authentication) {
		AttackFactorQuestionnaireProvider questionnaireProvider = new AttackFactorQuestionnaireProvider();
		AttackFactorQuestionProvider questionProvider = new AttackFactorQuestionProvider();
		List<AttackMotivatingFactorQuestion> attackMotivatingQuestions = questionProvider.getAttackMotivatingFactorQuestions();
		List<VulnerabilityEnablingFactorQuestion> vulnerabilityEnablingQuestions = questionProvider.getVulnerabilityEnablingFactorQuestions();
		AttackMotivatingQuestionnaireResponse attackMotivatingQuestionnaireResponse = questionnaireProvider.getLastAttackMotivatingQuestionnaireResponse(organization_id, service_id);
		if (attackMotivatingQuestionnaireResponse != null) {
			Map<Integer, AttackMotivatingQuestionResponse> responsePerFactorId = new HashMap<Integer, AttackMotivatingQuestionResponse>();
			List<AttackMotivatingQuestionResponse> attackMotivatingResponses = questionnaireProvider.getResponsesForAttackMotivatingQuestionnaireResponse(attackMotivatingQuestionnaireResponse.getAttack_motivating_questionnaire_response_id());
			for (AttackMotivatingQuestionResponse response : attackMotivatingResponses) {
				responsePerFactorId.put(response.getFactor_id(), response);
			}
			model.addAttribute("attackMotivatingResponses", responsePerFactorId);
		}
		VulnerabilityEnablingQuestionnaireResponse vulnerabilityEnablingQuestionnaireResponse = questionnaireProvider.getLastVulnerabilityEnablingQuestionnaireResponse(organization_id, service_id);
		if (vulnerabilityEnablingQuestionnaireResponse != null) {
			Map<Integer, VulnerabilityEnablingQuestionResponse> responsePerFactorId = new HashMap<Integer, VulnerabilityEnablingQuestionResponse>();
			List<VulnerabilityEnablingQuestionResponse> vulnerabilityEnablingResponses = questionnaireProvider.getResponsesForVulnerabilityEnablingQuestionnaireResponse(vulnerabilityEnablingQuestionnaireResponse.getVulnerability_enabling_questionnaire_response_id());
			for (VulnerabilityEnablingQuestionResponse response : vulnerabilityEnablingResponses) {
				responsePerFactorId.put(response.getFactor_id(), response);
			}
			model.addAttribute("vulnerabilityEnablingResponses", responsePerFactorId);
		}
		model.addAttribute("attackMotivatingQuestions",attackMotivatingQuestions);
		model.addAttribute("vulnerabilityEnablingQuestions",vulnerabilityEnablingQuestions);
		model.addAttribute("function2", "existenceProbabilityQuestionnaire");
		model.addAttribute("organization_id", organization_id);
		model.addAttribute("service_id",service_id);
		addPageStandardsToModel(model, true);
		return "existenceProbabilityQuestionnaire";
	}
	
	
	@GetMapping(value={"/RiskAssessment/{service_id}/RiskReporting/ViewReport/{report_id}", "/RiskAssessment/{service_id}/RiskReporting/ViewReport/{report_id}/{action}"})
	public String createRiskReportView(ModelMap model, @PathVariable(name="action", required = false) String action, @PathVariable(name="service_id", required = true) Integer service_id, @PathVariable(name="report_id") Integer risk_report_id, Authentication authentication) {
		boolean print = false;
		if (action != null) {
			if (action.equals("print")) {
				print = true;
			}
		}
		OrganizationProvider organizationProvider = new OrganizationProvider();
		List<Organization> organizations = organizationProvider.getAllOrganizations();
		model.addAttribute("organizations", organizations);
		ServiceProvider serviceProvider = new ServiceProvider();
		List<Service> services = serviceProvider.getAllServices();
		model.addAttribute("services", services);
		Service service = serviceProvider.find(service_id);
		RiskCategoryProvider riskCategoryProvider = new RiskCategoryProvider();
		List<RiskCategory> impactCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), service_id, RiskCategoryProvider.RiskCategoryType.Impact);
		if (impactCategories == null) {
			impactCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), -1, RiskCategoryProvider.RiskCategoryType.Impact);
		}
		if (impactCategories == null) {
			System.err.println("No impact categories were found!");
			return null;
		}
		Map<Long, RiskCategory> impactPerMaxValue = new HashMap<Long, RiskCategory>();
		for (RiskCategory impactCategory : impactCategories) {
			impactPerMaxValue.put(new Long(impactCategory.getRisk_value_max()), impactCategory);
		}
		List<RiskCategory> probabilityCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), service_id, RiskCategoryProvider.RiskCategoryType.Probability);
		if (probabilityCategories == null) {
			probabilityCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), -1, RiskCategoryProvider.RiskCategoryType.Probability);
		}
		if (probabilityCategories == null) {
			System.err.println("No probability categories were found!");
			return null;
		}
		Map<Integer, RiskCategory> probabilityPerMaxValue = new HashMap<Integer, RiskCategory>();
		for (RiskCategory probabilityCategory : probabilityCategories) {
			probabilityPerMaxValue.put(probabilityCategory.getRisk_value_max(), probabilityCategory);
		}
		List<RiskCategory> riskCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), service_id, RiskCategoryProvider.RiskCategoryType.Risk);
		if (riskCategories == null) {
			riskCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), -1, RiskCategoryProvider.RiskCategoryType.Risk);
		}
		if (riskCategories == null) {
			System.err.println("No risk categories were found!");
			return null;
		}
		Map<Integer, RiskCategory> riskPerMaxValue = new HashMap<Integer, RiskCategory>();
		for (RiskCategory riskCategory : riskCategories) {
			riskPerMaxValue.put(riskCategory.getRisk_value_max(), riskCategory);
		}
		
		RiskReportProvider riskReportProvider = new RiskReportProvider();		
		RiskReport riskReport = riskReportProvider.find(risk_report_id);
		List<RiskReportThreat> threats = riskReportProvider.getThreatsForRiskReport(risk_report_id);
		List<BusinessRisk> affectedRisks = riskReportProvider.getBusinessRisksForRiskReport(risk_report_id);
		BusinessRiskOrderProvider orderProvider = new BusinessRiskOrderProvider();
		BusinessRiskProvider riskProvider = new BusinessRiskProvider();
		BusinessRiskCustomOrder customOrder = orderProvider.findForService(service_id);
		List<BusinessRiskCustomOrderList> customOrders = orderProvider.getCustomOrderList(customOrder.getOrder_id());
		List<DeterminedRisk> determinedRisks = new ArrayList<DeterminedRisk>();
		for (BusinessRiskCustomOrderList ordering : customOrders) {
			DeterminedRisk determinedRisk = new DeterminedRisk();
			long weight = Math.round(ordering.getWeight()*100);
			for (RiskCategory impactCategory : impactCategories) {
				if (weight >= impactCategory.getRisk_value_min() && weight <= impactCategory.getRisk_value_max()) {
					weight = impactCategory.getRisk_value_max();
				}
			}
			determinedRisk.setWeight(weight);
			determinedRisk.setMonetary_value_min(ordering.getBusiness_risk_impact_min());
			determinedRisk.setMonetary_value_max(ordering.getBusiness_risk_impact_max());
			for (BusinessRisk risk : affectedRisks) {
				if (risk.getBusiness_risk_id() == ordering.getBusiness_risk_id()) {
					determinedRisk.setRisk(risk);
					List<Integer> capabilityKillerIDs = riskProvider.getAssignedCapabilityKillerIDsForBusinessRisk(risk.getBusiness_risk_id());
					determinedRisk.setKiller_ids(capabilityKillerIDs);
					break;
				}
			}
			determinedRisks.add(determinedRisk);
		}
		List<BusinessRisk> unaffectedRisks = riskReportProvider.getBusinessRisksNotInReport(risk_report_id, service_id);
		AttackPatternPrerequisiteProvider prereqProvider = new AttackPatternPrerequisiteProvider();
		Map<OrderedKillChainPhase, List<RiskReportThreat>> threatsPerKillChain = new HashMap<OrderedKillChainPhase, List<RiskReportThreat>>();
		Map<Integer, RiskReportThreat> threatsByID = new HashMap<Integer, RiskReportThreat>();
		Map<Integer, List<OrderedKillChainPhase>> killChainOrder = new HashMap<Integer, List<OrderedKillChainPhase>>();
		Map<Integer, OrderedKillChainPhase> phaseIndices = new HashMap<Integer, OrderedKillChainPhase>();
		Map<Integer, List<Integer>> killerIDsPerThreat = new HashMap<Integer, List<Integer>>();
		List<Integer> threatIDs = new ArrayList<Integer>();
		int minPhase = -1;
		int maxPhase = -1;
		List<RiskReportThreat> threatsInReport = new ArrayList<RiskReportThreat>();
		AttackPatternSuccessProbabilityProvider probabilityProvider = new AttackPatternSuccessProbabilityProvider();
		Map<Integer, Map<Integer, List<AttackPatternSuccessProbability>>> alternativeProbabilities = new HashMap<Integer, Map<Integer, List<AttackPatternSuccessProbability>>>();
		for (RiskReportThreat threat : threats) {
			if (!threatsByID.containsKey(threat.getThreat_id())) {
				threatsByID.put(threat.getThreat_id(), threat);
			}
			List<Integer> controlIDs = riskReportProvider.getControlsForRiskReportThreat(threat.getThreat_id(), riskReport.getRisk_report_id());
			List<AttackPatternSuccessProbability> successProbabilities = probabilityProvider.getSuccessProbabilitiesForAttackPattern(threat.getThreat_id());
			for (AttackPatternSuccessProbability successProbability : successProbabilities) {
				if (successProbability.getSuccess_probability_order() < threat.getSuccess_probability_order()) {
					if (!alternativeProbabilities.containsKey(threat.getThreat_id())) {
						alternativeProbabilities.put(threat.getThreat_id(),
								new HashMap<Integer, List<AttackPatternSuccessProbability>>());
					}
					if (!alternativeProbabilities.get(threat.getThreat_id())
							.containsKey(successProbability.getC_success_probability_id())) {
						alternativeProbabilities.get(threat.getThreat_id()).put(
								successProbability.getC_success_probability_id(),
								new ArrayList<AttackPatternSuccessProbability>());
					}
					if (successProbability.getCourse_of_action_id() != null) {
						String refinedDescription = probabilityProvider.getRefinedDescriptionForCourseOfAction(
								successProbability.getAttack_pattern_id(), successProbability.getCourse_of_action_id());
						if (refinedDescription != null) {
							successProbability.setCourse_of_action_description(refinedDescription);
						}
					}
					for (RiskCategory probabilityCategory : probabilityCategories) {
						if (successProbability.getSuccess_probability_order() >= probabilityCategory.getRisk_value_min() && successProbability.getSuccess_probability_order() <= probabilityCategory.getRisk_value_max()) {
							successProbability.setSuccess_probability_order(probabilityCategory.getRisk_value_max());
						}
					}
					
					alternativeProbabilities.get(threat.getThreat_id())
							.get(successProbability.getC_success_probability_id()).add(successProbability);
				}
			}
			
			threatIDs.add(threat.getThreat_id());
			List<Integer> killerIDs = prereqProvider.getCapabilityKillerIDsForAttackPatternID(threat.getThreat_id());
			if (killerIDs != null) {
				killerIDsPerThreat.put(threat.getThreat_id(), killerIDs);
			}
			/*
			 * Adjust Success Probability with probability classification
			 */
			
			
			threatsInReport.add(threat);
			for (RiskCategory probabilityCategory : probabilityCategories) {
				if (threat.getSuccess_probability_order() >= probabilityCategory.getRisk_value_min() && threat.getSuccess_probability_order() <= probabilityCategory.getRisk_value_max()) {
					threat.setSuccess_probability_order(probabilityCategory.getRisk_value_max());
					threat.setSuccess_probability_name(probabilityCategory.getCategory_name());
				}
			}
			List<OrderedKillChainPhase> phases = prereqProvider.getKillChainPhasesForAttackPattern(threat.getThreat_id());
			for (OrderedKillChainPhase phase : phases) {
				if (minPhase == -1 || minPhase > phase.getKill_chain_order()) {
					minPhase = phase.getKill_chain_order();
				}
				if (maxPhase == -1 || maxPhase < phase.getKill_chain_order()){
					maxPhase = phase.getKill_chain_order();
				}
				if (!(threatsPerKillChain.containsKey(phase))) {
					threatsPerKillChain.put(phase, new ArrayList<RiskReportThreat>());
				}
				threatsPerKillChain.get(phase).add(threat);			
				
				if (!(killChainOrder.containsKey(phase.getKill_chain_order()))) {
					killChainOrder.put(phase.getKill_chain_order(), new ArrayList<OrderedKillChainPhase>());
				
				}else {
					if (!(killChainOrder.get(phase.getKill_chain_order()).contains(phase))) {
						killChainOrder.get(phase.getKill_chain_order()).add(phase);
					}
					
				}
			}
		}
		int rollingIndex = 0;
		Object[] orderedKeys = killChainOrder.keySet().toArray();
		Arrays.sort(orderedKeys);
		Map<String, Integer> killChainIndexPerPhaseName = new HashMap<String, Integer>();
		for (int i = 0; i < orderedKeys.length; i++) {
			for (OrderedKillChainPhase phase : killChainOrder.get(orderedKeys[i])) {
				phaseIndices.put(rollingIndex, phase);
				killChainIndexPerPhaseName.put(phase.getPhase_name(), rollingIndex);
				rollingIndex++;
			}
		}
		List<RiskReportThreatControlPivotedThreat> pivotedThreats = riskReportProvider.getPivotedThreatsForRiskReport(risk_report_id);
		Map<Integer, List<RiskReportThreatControlPivotedThreat>> threatPivots = new HashMap<Integer, List<RiskReportThreatControlPivotedThreat>>();
		List<RiskReportArrow> arrows = new ArrayList<RiskReportArrow>();
		List<Integer> pivoted_threat_ids = new ArrayList<Integer>();
		Map<OrderedKillChainPhase, Map<Integer, RiskCategory>> pivotedSuccessProbabilities = new HashMap<OrderedKillChainPhase, Map<Integer, RiskCategory>>();
		for (RiskReportThreatControlPivotedThreat pivotedThreat : pivotedThreats) {
			
			int threat_id = pivotedThreat.getThreat_id();
			int suc_threat_id = pivotedThreat.getSucceeding_threat_id();
			OrderedKillChainPhase kPhase = new OrderedKillChainPhase();
			kPhase.setKill_chain_name(pivotedThreat.getKill_chain_name());
			kPhase.setKill_chain_order(pivotedThreat.getKill_chain_phase_order());
			kPhase.setPhase_name(pivotedThreat.getKill_chain_phase_name());
			OrderedKillChainPhase sucPhase = new OrderedKillChainPhase();
			sucPhase.setKill_chain_name(pivotedThreat.getSucceeding_kill_chain_name());
			sucPhase.setPhase_name(pivotedThreat.getSucceeding_kill_chain_phase_name());
			sucPhase.setKill_chain_order(pivotedThreat.getSucceeding_kill_chain_phase_order());
			Integer kOrder = null;
			Integer sucOrder = null;
			for (Integer index : phaseIndices.keySet()) {
				if (phaseIndices.get(index).equals(kPhase) && kOrder == null) {
					kOrder = index;
				}else if(phaseIndices.get(index).equals(sucPhase) && sucOrder == null) {
					sucOrder = index;
				}
				if (kOrder != null && sucOrder != null) {
					break;
				}
			}
			int threatOrder = 0;
			int sucThreatOrder = 0;
			if (threatsPerKillChain.get(kPhase) == null || threatsPerKillChain.get(sucPhase) == null) {
				continue;
			}
			RiskCategory probCategory = null;
			for (RiskCategory probability : probabilityCategories) {
				if (pivotedThreat.getSuccess_probability_order() >= probability.getRisk_value_min() && pivotedThreat.getSuccess_probability_order() <= probability.getRisk_value_max()) {
					probCategory = probability;
					break;
				}
			}
			if (!pivotedSuccessProbabilities.containsKey(sucPhase)) {
				pivotedSuccessProbabilities.put(sucPhase, new HashMap<Integer,RiskCategory>());
			}
			if (pivotedSuccessProbabilities.get(sucPhase).containsKey(pivotedThreat.getSucceeding_threat_id())){
				if (pivotedSuccessProbabilities.get(sucPhase).get(pivotedThreat.getSucceeding_threat_id()).getRisk_value_max() < probCategory.getRisk_value_max()) {
					pivotedSuccessProbabilities.get(sucPhase).put(pivotedThreat.getSucceeding_threat_id(), probCategory);
				}
			}else {
				pivotedSuccessProbabilities.get(sucPhase).put(pivotedThreat.getSucceeding_threat_id(), probCategory);
			}
			if (!(threatPivots.containsKey(pivotedThreat.getThreat_id()))) {
				threatPivots.put(pivotedThreat.getThreat_id(), new ArrayList<RiskReportThreatControlPivotedThreat>());
			}
			if (!(pivoted_threat_ids.contains(pivotedThreat.getThreat_id()))) {
				pivoted_threat_ids.add(pivotedThreat.getThreat_id());
			}
			if (!(pivoted_threat_ids.contains(pivotedThreat.getSucceeding_threat_id()))){
				pivoted_threat_ids.add(pivotedThreat.getSucceeding_threat_id());
			}
			for (RiskReportThreat threat : threatsPerKillChain.get(kPhase)) {
				if (threat.getThreat_id() == threat_id) {
					threatOrder = threatsPerKillChain.get(kPhase).indexOf(threat);
					break;
				}
			}
			for (RiskReportThreat threat : threatsPerKillChain.get(sucPhase)) {
				if (threat.getThreat_id() == suc_threat_id) {
					sucThreatOrder = threatsPerKillChain.get(sucPhase).indexOf(threat);
					break;
				}
			}
			/* 
			 * Calculate x0,y0,x1,y1
			 */
			final int stepX1 = 100;
			final int startX1 = 95;
			final int stepX2 = 100;
			final int startX2 = 05;
			final int stepY1 = 80;
			final int startY1 = 50;
			final int stepY2 = 80;
			final int startY2 = 50;
			int x1 = startX1+stepX1*kOrder;
			int x2 = startX2+stepX2*sucOrder;
			int y1 = startY1+stepY1*threatOrder;
			int y2 = startY2+stepY2*sucThreatOrder;
			RiskReportArrow arrow = new RiskReportArrow();
			arrow.setX0(x1);
			arrow.setY0(y1);
			arrow.setX1(x2);
			arrow.setY1(y2);
			arrow.setThreat_id(threat_id);
			arrow.setSuc_threat_id(suc_threat_id);
			arrow.setKill_chain_name(kPhase.getPhase_name());
			arrow.setKill_chain_index(kOrder);
			if (!arrows.contains(arrow)) {
				arrows.add(arrow);
			}
		}
		int maxNoThreats = 0;
		for (OrderedKillChainPhase kPhase : threatsPerKillChain.keySet()) {
			if (threatsPerKillChain.get(kPhase).size() > maxNoThreats) {
				maxNoThreats = threatsPerKillChain.get(kPhase).size();
			}
		}
		double pivotCoverage = ((double) threatPivots.keySet().size()) / threatsInReport.size();
		ServiceExistenceProbability existenceProbability = serviceProvider.findExistenceProbability(riskReport.getService_existence_probability_id());
		
		
		
		RiskPhiProvider riskPhiProvider = new RiskPhiProvider();
		RiskPhi riskPhi = riskPhiProvider.getLatestRiskPhiForOrganizationAndService(service.getOrganization_id(), service_id, RiskPhiProvider.RiskPhiType.OCCURENCE_PROBABILITY);
		if (riskPhi == null) {
			riskPhi = riskPhiProvider.getLatestRiskPhiForOrganizationAndService(service.getOrganization_id(), -1, RiskPhiProvider.RiskPhiType.OCCURENCE_PROBABILITY);
		}
		if (riskPhi == null) {
			System.err.println("Cannot create risk report because no weighing has been done!");
			return null;
		}
		RiskPhi riskPhiRisk = riskPhiProvider.getLatestRiskPhiForOrganizationAndService(service.getOrganization_id(), service_id, RiskPhiProvider.RiskPhiType.RISK);
		if (riskPhiRisk == null) {
			riskPhiRisk = riskPhiProvider.getLatestRiskPhiForOrganizationAndService(service.getOrganization_id(), -1, RiskPhiProvider.RiskPhiType.RISK);
		}
		if (riskPhiRisk == null) {
			System.err.println("Cannot create risk report because no weighing has been done!");
			return null;
		}
		double phi = riskPhi.getRisk_phi().doubleValue();
		double occurenceProbability = (((phi)*riskReport.getAssessed_success_probability_order())+((1-phi)*existenceProbability.getExistence_probability().doubleValue()*100));
		RiskCategory occurenceProbabilityCategory = getRiskCategoryForValue(Math.round(occurenceProbability), probabilityCategories);
		Map<Integer, BigDecimal> occurenceProbabilityPerRisk = new HashMap<Integer, BigDecimal>();
		List<RiskReportCapabilityKillerSuccessProbability> pivoted_probs = riskReportProvider.getRiskReportCapabilityKillerSuccessProbabilities(riskReport.getRisk_report_id());
		Map<Integer,Integer> maxSuccessProbabilityPerRisk = new HashMap<Integer,Integer>();
		for (RiskReportCapabilityKillerSuccessProbability pivoted_prob : pivoted_probs) {
			if (maxSuccessProbabilityPerRisk.containsKey(pivoted_prob.getBusiness_risk_id())) {
				if (maxSuccessProbabilityPerRisk.get(pivoted_prob.getBusiness_risk_id()) > pivoted_prob.getSuccess_probability_order()) {
					continue;
				}
			}
			maxSuccessProbabilityPerRisk.put(pivoted_prob.getBusiness_risk_id(), pivoted_prob.getSuccess_probability_order());
			double pivoted_occurence_prob = (((phi)*pivoted_prob.getSuccess_probability_order())+((1-phi)*existenceProbability.getExistence_probability().doubleValue()*100));
			occurenceProbabilityPerRisk.put(pivoted_prob.getBusiness_risk_id(), new BigDecimal(pivoted_occurence_prob, MathContext.DECIMAL64));
		}
		if (riskPhiRisk != null && !probabilityCategories.isEmpty() && !impactCategories.isEmpty() && !riskCategories.isEmpty()) {
			Map<RiskCategory, Map<RiskCategory, RiskCategory>> categoryRiskComputation = new LinkedHashMap<RiskCategory, Map<RiskCategory, RiskCategory>>();
			for (RiskCategory impactCategory : impactCategories) {
				for (RiskCategory probabilityCategory : probabilityCategories) {
					long maxRiskPhi = Math.round((1-riskPhiRisk.getRisk_phi().doubleValue())*impactCategory.getRisk_value_max()+riskPhiRisk.getRisk_phi().doubleValue()*probabilityCategory.getRisk_value_max());
					RiskCategory resultingCategory = null;
					for (RiskCategory riskCategory : riskCategories) {
						if (maxRiskPhi >= riskCategory.getRisk_value_min() && maxRiskPhi <= riskCategory.getRisk_value_max()) {
							resultingCategory = riskCategory;
							break;
						}
					}
					if (!categoryRiskComputation.containsKey(impactCategory)) {
						categoryRiskComputation.put(impactCategory, new LinkedHashMap<RiskCategory,RiskCategory>());
					}
					categoryRiskComputation.get(impactCategory).put(probabilityCategory,resultingCategory);
				}
			}
			model.addAttribute("categoryRiskComputation", categoryRiskComputation);
		}
		List<Integer> excludedThreats = riskReportProvider.getExcludedThreatsForReport(riskReport.getRisk_report_id());
		
		AttackFactorQuestionnaireProvider factorProvider = new AttackFactorQuestionnaireProvider();
		AttackMotivatingQuestionnaireResponse attackMotivatingQuestionnaire = factorProvider.getLastAttackMotivatingQuestionnaireResponse(service.getOrganization_id(), service.getService_id());
		List<Integer> attack_motivating_factor_ids = factorProvider.getAttackMotivatingFactorIDsForQuestionnaireResponse(attackMotivatingQuestionnaire.getAttack_motivating_questionnaire_response_id());
		VulnerabilityEnablingQuestionnaireResponse vulnerabilityEnablingQuestionnaire = factorProvider.getLastVulnerabilityEnablingQuestionnaireResponse(service.getOrganization_id(), service.getService_id());
		List<Integer> vulnerability_enabling_factor_ids = factorProvider.getVulnerabilityEnablingFactorIDsForQuestionnaireResponse(vulnerabilityEnablingQuestionnaire.getVulnerability_enabling_questionnaire_response_id());
		AttackFactorProvider vulnerabilityEnablingFactorProvider = new AttackFactorProvider();
		Map<Integer, String> vulnerabilityEnablingFactorNames = vulnerabilityEnablingFactorProvider.getVulnerabilityEnablingFactorNamesByID(vulnerability_enabling_factor_ids);
		AttackFactorProvider dwhFactorProvider = new AttackFactorProvider();
		List<IntrusionSet> attack_motivating_intrusion_sets = dwhFactorProvider.getIntrusionSetsForMotivatingFactorIDs(attack_motivating_factor_ids);
		List<IntrusionSet> vulnerability_enabling_intrusion_sets = dwhFactorProvider.getIntrusionSetsForVulnerabilityEnablingFactorIDs(vulnerability_enabling_factor_ids);
		CourseOfActionAttackPatternProvider courseOfActionAttackPatternProvider = new CourseOfActionAttackPatternProvider();
		List<IntrusionSet> attack_pattern_intrusion_sets = courseOfActionAttackPatternProvider.getIntrusionSetForAttackPattternIDs(threatIDs);
		List<Malware> attack_pattern_malware = courseOfActionAttackPatternProvider.getMalwareForAttackPatternIDs(threatIDs);
		System.out.println(attack_pattern_malware.size());
		List<IntrusionSet> motivated_and_enabled_intrusion_sets = new ArrayList<IntrusionSet>();
		List<IntrusionSet> motivated_and_patterned_intrusion_sets = new ArrayList<IntrusionSet>();
		List<IntrusionSet> enabled_and_patterned_intrusion_sets = new ArrayList<IntrusionSet>();
		List<IntrusionSet> motivated_and_enabled_and_patterned_intrusion_sets = new ArrayList<IntrusionSet>();
		List<IntrusionSet> only_patterned_intrusion_sets = new ArrayList<IntrusionSet>();
		List<IntrusionSet> only_motivated_intrusion_sets = new ArrayList<IntrusionSet>(); 
		Map<Integer, List<Reference>> referencesPerIntrusionSet = new HashMap<Integer, List<Reference>>();
		Map<Integer, List<Reference>> referencesPerMalware = new HashMap<Integer, List<Reference>>();
		for (IntrusionSet intrusion_set : attack_pattern_intrusion_sets) {
			List<Reference> references = courseOfActionAttackPatternProvider.getReferencesForIntrusionSetID(intrusion_set.getIntrusion_set_id());
			if (references != null) {
				if (!referencesPerIntrusionSet.containsKey(intrusion_set.getIntrusion_set_id())) {
					referencesPerIntrusionSet.put(intrusion_set.getIntrusion_set_id(), new ArrayList<Reference>());
				}
				referencesPerIntrusionSet.get(intrusion_set.getIntrusion_set_id()).addAll(references);
			}
			if (attack_motivating_intrusion_sets.contains(intrusion_set) && vulnerability_enabling_intrusion_sets.contains(intrusion_set)) {
				attack_motivating_intrusion_sets.remove(intrusion_set);
				vulnerability_enabling_intrusion_sets.remove(intrusion_set);
				motivated_and_enabled_and_patterned_intrusion_sets.add(intrusion_set);
			}else if (attack_motivating_intrusion_sets.contains(intrusion_set)) {
				attack_motivating_intrusion_sets.remove(intrusion_set);
				motivated_and_patterned_intrusion_sets.add(intrusion_set);
			}else if (vulnerability_enabling_intrusion_sets.contains(intrusion_set)) {
				vulnerability_enabling_intrusion_sets.remove(intrusion_set);
				enabled_and_patterned_intrusion_sets.add(intrusion_set);
			}else {
				only_patterned_intrusion_sets.add(intrusion_set);
			}
		}
		for (IntrusionSet intrusion_set : attack_motivating_intrusion_sets) {
			List<Reference> references = courseOfActionAttackPatternProvider.getReferencesForIntrusionSetID(intrusion_set.getIntrusion_set_id());
			if (references != null) {
				if (!referencesPerIntrusionSet.containsKey(intrusion_set.getIntrusion_set_id())) {
					referencesPerIntrusionSet.put(intrusion_set.getIntrusion_set_id(), new ArrayList<Reference>());
				}
				referencesPerIntrusionSet.get(intrusion_set.getIntrusion_set_id()).addAll(references);
			}
			if (vulnerability_enabling_intrusion_sets.contains(intrusion_set)) {
				motivated_and_enabled_intrusion_sets.add(intrusion_set);
				vulnerability_enabling_intrusion_sets.remove(intrusion_set);
			}else {
				only_motivated_intrusion_sets.add(intrusion_set);
			}
		}
		for (IntrusionSet intrusion_set : vulnerability_enabling_intrusion_sets) {
			List<Reference> references = courseOfActionAttackPatternProvider.getReferencesForIntrusionSetID(intrusion_set.getIntrusion_set_id());
			if (references != null) {
				if (!referencesPerIntrusionSet.containsKey(intrusion_set.getIntrusion_set_id())) {
					referencesPerIntrusionSet.put(intrusion_set.getIntrusion_set_id(), new ArrayList<Reference>());
				}
				referencesPerIntrusionSet.get(intrusion_set.getIntrusion_set_id()).addAll(references);
			}
		}
		List<Malware> cleaned_malware = new ArrayList<Malware>();
		MalwareLoop:
		for (Malware malware : attack_pattern_malware) {
			for (Malware cleaned_malw : cleaned_malware) {
				if (malware.getMalware_id() == cleaned_malw.getMalware_id()) {
					continue MalwareLoop;
				}
			}
			cleaned_malware.add(malware);
		}
		for (Malware malware : attack_pattern_malware) {
			List<Reference> references = courseOfActionAttackPatternProvider.getReferencesForMalwareID(malware.getMalware_id());
			if (references != null) {
				if (!referencesPerMalware.containsKey(malware.getMalware_id())) {
					referencesPerMalware.put(malware.getMalware_id(), new ArrayList<Reference>());
				}
				referencesPerMalware.get(malware.getMalware_id()).addAll(references);
			}
		}
		if (print)
			model.addAttribute("threatsById",threatsByID);
		model.addAttribute("service",service);
		model.addAttribute("vulnerabilityEnablingFactorNames",vulnerabilityEnablingFactorNames);
		model.addAttribute("orderedProbabilityCategories",probabilityCategories);
		model.addAttribute("alternativeProbabilities",alternativeProbabilities);
		model.addAttribute("referencesPerAttacker",referencesPerIntrusionSet);
		model.addAttribute("referencesPerMalware",referencesPerMalware);
		model.addAttribute("onlyMotivatedAttackers",only_motivated_intrusion_sets);
		model.addAttribute("onlyEnabledAttackers",vulnerability_enabling_intrusion_sets);
		model.addAttribute("onlyPatternedAttackers",only_patterned_intrusion_sets);
		model.addAttribute("motivatedAndPatternedAttackers",motivated_and_patterned_intrusion_sets);
		model.addAttribute("enabledAndPatternedAttackers",enabled_and_patterned_intrusion_sets);
		model.addAttribute("motivatedAndEnabledAndPatternedAttackers",motivated_and_enabled_and_patterned_intrusion_sets);
		model.addAttribute("motivatedAndEnabledAttackers",motivated_and_enabled_intrusion_sets);
		model.addAttribute("relevantMalware",cleaned_malware);
		model.addAttribute("excludedThreats",excludedThreats);
		model.addAttribute("phaseIndices",killChainIndexPerPhaseName);
		model.addAttribute("killerIDsPerThreat",killerIDsPerThreat);
		model.addAttribute("riskPhi", riskPhi.getRisk_phi().doubleValue());
		model.addAttribute("riskPhiRisk", riskPhiRisk.getRisk_phi().doubleValue());
		model.addAttribute("determinedRisks",determinedRisks);
		model.addAttribute("impactCategories",impactPerMaxValue);
		model.addAttribute("probabilityCategories",probabilityPerMaxValue);
		model.addAttribute("riskCategories",riskPerMaxValue);
		model.addAttribute("pivotProbabilities", pivotedSuccessProbabilities);
		model.addAttribute("existenceProbability", existenceProbability);
		model.addAttribute("pivotedSuccessProbabilities",maxSuccessProbabilityPerRisk);
		model.addAttribute("occurenceProbability",occurenceProbability);
		model.addAttribute("occurenceProbabilityCategory",occurenceProbabilityCategory);
		model.addAttribute("pivoted_occurence_probabilities",occurenceProbabilityPerRisk);
		model.addAttribute("pivotCoverage",pivotCoverage);
		model.addAttribute("pivotBoxWidth",threatsPerKillChain.keySet().size()*100);
		model.addAttribute("pivotBoxHeight",maxNoThreats*90);
		model.addAttribute("arrows",arrows);
		model.addAttribute("killChainOrder",killChainOrder);
		model.addAttribute("threats",threatsPerKillChain);
		model.addAttribute("minPhase",minPhase);
		model.addAttribute("maxPhase",maxPhase);
		model.addAttribute("affectedRisks", affectedRisks);
		model.addAttribute("unaffectedRisks", unaffectedRisks);
		model.addAttribute("risk_report",riskReport);
		model.addAttribute("application", "risk_assessment");
		model.addAttribute("function2", "risk_report");
		addPageStandardsToModel(model, true);
		if (action != null) {
			if (action.equals("print")) {
				return "risk_report_print";
			}
		}
		return "risk_report";
	}
	
	private RiskCategory getRiskCategoryForValue(long risk_value, List<RiskCategory> categories) {
		for (RiskCategory category : categories) {
			if (risk_value >= category.getRisk_value_min() && risk_value <= category.getRisk_value_max()) {
				return category;
			}
		}
		return null;
	}
	
	

	

	@GetMapping(value = {"/RiskAssessment","/RiskAssessment/{service_id}"})
	public String createRiskAssessmentOverview(ModelMap model, @PathVariable(name="service_id", required = false) Integer service_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Risk Assessment");
		model.addAttribute("function", "risk_identification");
		model.addAttribute("application", "risk_assessment");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			if (service_id != null) {
				model.addAttribute("function2","riskAssessmentOverview");
				RiskReportProvider riskReportProvider = new RiskReportProvider();
				List<RiskReport> reports = riskReportProvider.getRiskReportsForService(service_id);
				model.addAttribute("riskReports",reports);
				model.addAttribute("service_id",service_id);
			}else {
				model.addAttribute("function2","riskAssessmentStartPage");
			}
			OrganizationProvider organizationProvider = new OrganizationProvider();
			List<Organization> organizations = organizationProvider.getAllOrganizations();
			model.addAttribute("organizations", organizations);
			ServiceProvider serviceProvider = new ServiceProvider();
			List<Service> services = serviceProvider.getAllServices();
			model.addAttribute("services", services);
			addPageStandardsToModel(model,isAdmin);
			return "riskAssessmentOverview";
		}
		return null;
	}
	
	@GetMapping(value = { "/RiskAssessment/{service_id}/RiskQuestionnaire",
			"/RiskAssessment/{service_id}/RiskQuestionnaire/page1",
			"/RiskAssessment/{service_id}/RiskQuestionnaire/page2",
			"/RiskAssessment/{service_id}/RiskQuestionnaire/page3" })
	public String createRiskQuestionnaireView(ModelMap model,
			@PathVariable(name = "service_id", required = true) Integer service_id,
			@RequestParam(required = false) String checkedCollections,
			@RequestParam(required = false) String checkedPlatforms,
			@RequestParam(required = false) String checkedPlatformIDs,
			@RequestParam(required = false) String checkedControls,
			@RequestParam(required = false) String checkedEnablingFactors, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Service Modelling");
		model.addAttribute("function", "risk_identification");
		model.addAttribute("application", "service_definition");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			OrganizationProvider organizationProvider = new OrganizationProvider();
			List<Organization> organizations = organizationProvider.getAllOrganizations();
			model.addAttribute("organizations", organizations);
			ServiceProvider serviceProvider = new ServiceProvider();
			Service currentService = serviceProvider.find(service_id);
			model.addAttribute("service",currentService);
			
			if (checkedCollections == null && checkedPlatforms == null && checkedPlatformIDs == null
					&& checkedControls == null && checkedEnablingFactors == null) {
				ThreatCollectionProvider threatCollectionProvider = new ThreatCollectionProvider();
				List<ThreatCollection> collections = threatCollectionProvider.getAllThreatCollections();
				model.addAttribute("collections", collections);

			} else if (checkedCollections != null && checkedPlatforms == null && checkedPlatformIDs == null
					&& checkedControls == null && checkedEnablingFactors == null) {
				checkedCollections += ",RALF";
				String[] collectionIDs = checkedCollections.split(",");
				AttackPatternPlatformProvider attackPatternPlatformProvider = new AttackPatternPlatformProvider();
				List<String> platforms = attackPatternPlatformProvider.getDistinctPlatforms(collectionIDs);
				Map<String,List<String>> groupsForPlatforms = attackPatternPlatformProvider.getPlatformsPerGroup(platforms);
				System.out.println(groupsForPlatforms);
				for (String group : groupsForPlatforms.keySet()) {
					List<String> platformList = groupsForPlatforms.get(group);
					for (String platform : platformList) {
						if (platforms.contains(platform)) {
							platforms.remove(platform);
						}
					}
				}
				model.addAttribute("platformPerGroup",groupsForPlatforms);
				model.addAttribute("platformsWithoutGroup", platforms);
				model.addAttribute("checkedCollections", checkedCollections);
				if (service_id != null && service_id > 0) {
					model.addAttribute("service_id", service_id);
				} else {
					return null;
				}
			} else if (checkedCollections != null && checkedPlatforms != null && checkedPlatformIDs == null
					&& checkedControls == null && checkedEnablingFactors == null) {
				String[] platformList = checkedPlatforms.split(",");
				String[] collectionIDs = checkedCollections.split(",");
				VulnerabilityEnablingFactorProvider vulnerabilityEnablingFactorProvider = new VulnerabilityEnablingFactorProvider();
				CourseOfActionAttackPatternProvider courseOfActionAttackPatternProvider = new CourseOfActionAttackPatternProvider();
				List<CourseOfActionAttackPattern> coursesOfActionAttackPattern = courseOfActionAttackPatternProvider
						.getCoursesOfActionForPlatforms(platformList, collectionIDs);
				//List<Integer> platformIDs = courseOfActionAttackPatternProvider.getPlatformIDsForNames(platformList);
				Map<String, Map<Integer, String>> coursesOfAction = new HashMap<String, Map<Integer, String>>();
				Map<Integer, List<CourseOfActionAttackPattern>> attackPatternsPerCourseOfAction = new HashMap<Integer, List<CourseOfActionAttackPattern>>();
				List<Integer> processedCoursesOfActions = new ArrayList<Integer>();
				for (CourseOfActionAttackPattern courseOfActionAttackPattern : coursesOfActionAttackPattern) {
					if (!processedCoursesOfActions.contains(courseOfActionAttackPattern.getCourse_of_action_id())) {
						List<String> aliases = courseOfActionAttackPatternProvider
								.getAliasesForCourseOfActionId(courseOfActionAttackPattern.getCourse_of_action_id());
						if (!aliases.isEmpty()) {
							String alias = String.join(",", aliases);
							if (!coursesOfAction.containsKey(alias)) {
								coursesOfAction.put(alias, new HashMap<Integer, String>());
							}
							if (!coursesOfAction.get(alias)
									.containsKey(courseOfActionAttackPattern.getCourse_of_action_id())) {
								coursesOfAction.get(alias).put(courseOfActionAttackPattern.getCourse_of_action_id(),
										courseOfActionAttackPattern.getCourse_of_action_name());
							}
							processedCoursesOfActions.add(courseOfActionAttackPattern.getCourse_of_action_id());
						}
					}
					if (!attackPatternsPerCourseOfAction
							.containsKey(courseOfActionAttackPattern.getCourse_of_action_id())) {
						List<CourseOfActionAttackPattern> patterns = new ArrayList<CourseOfActionAttackPattern>();
						attackPatternsPerCourseOfAction.put(courseOfActionAttackPattern.getCourse_of_action_id(),
								patterns);
					}
					boolean doNotStore = false;
					for (CourseOfActionAttackPattern storedPattern : attackPatternsPerCourseOfAction.get(courseOfActionAttackPattern.getCourse_of_action_id())) {
						if (storedPattern.getAttack_pattern_id() == courseOfActionAttackPattern.getAttack_pattern_id()) {
							doNotStore = true;
							break;
						}
					}
					if (!doNotStore) {
						attackPatternsPerCourseOfAction.get(courseOfActionAttackPattern.getCourse_of_action_id()).add(courseOfActionAttackPattern);
					}
					
				}
				List<VulnerabilityEnablingFactor> factors = vulnerabilityEnablingFactorProvider
						.getAllVulnerabilityEnablingFactors();
				model.addAttribute("vulnerabilityEnablingFactors", factors);
				model.addAttribute("coursesOfAction", coursesOfAction);
				model.addAttribute("platformIDs", platformList);
				model.addAttribute("checkedCollections", checkedCollections);
				model.addAttribute("coursesOfActionAttackPattern", attackPatternsPerCourseOfAction);
				model.addAttribute("function3", "checkMitigations");
			} else if (checkedCollections != null && checkedPlatforms == null && checkedPlatformIDs != null
					&& checkedControls != null && checkedEnablingFactors != null) {
				/*String[] platformList = checkedPlatformIDs.split(",");
				String[] controlsList = checkedControls.split(",");
				String[] enablingFactorsList = checkedEnablingFactors.split(",");
				List<Integer> platforms = new ArrayList<Integer>();
				for (String platformID : platformList) {
					Integer id = Integer.parseInt(platformID);
					platforms.add(id);
				}
				List<Integer> controls = new ArrayList<Integer>();
				for (String controlID : controlsList) {
					Integer id = Integer.parseInt(controlID);
					controls.add(id);
				}
				List<Integer> factors = new ArrayList<Integer>();
				for (String factorIDs : enablingFactorsList) {
					Integer id = Integer.parseInt(factorIDs);
					factors.add(id);
				}
				AttackPatternSuccessProbabilityProvider attackPatternSuccessProbabilityProvider = new AttackPatternSuccessProbabilityProvider();
				List<AttackPatternSuccessProbability> probs = attackPatternSuccessProbabilityProvider
						.getSuccessProbabilitiesByActionsPlatformsAndFactors(controls, platforms, factors);
				HashMap<Integer, Map<Integer, List<AttackPatternSuccessProbability>>> probabilities = new HashMap<Integer, Map<Integer, List<AttackPatternSuccessProbability>>>();
				for (AttackPatternSuccessProbability prob : probs) {
					if (!probabilities.containsKey(prob.getAttack_pattern_id())) {
						HashMap<Integer, List<AttackPatternSuccessProbability>> platformedPatterns = new HashMap<Integer, List<AttackPatternSuccessProbability>>();
						ArrayList<AttackPatternSuccessProbability> patternList = new ArrayList<AttackPatternSuccessProbability>();
						platformedPatterns.put(prob.getX_mitre_platforms_id(), patternList);
						probabilities.put(prob.getAttack_pattern_id(), platformedPatterns);
					}
					if (!probabilities.get(prob.getAttack_pattern_id()).containsKey(prob.getX_mitre_platforms_id())) {
						List<AttackPatternSuccessProbability> patternList = new ArrayList<AttackPatternSuccessProbability>();
						probabilities.get(prob.getAttack_pattern_id()).put(prob.getX_mitre_platforms_id(), patternList);
					}
					probabilities.get(prob.getAttack_pattern_id()).get(prob.getX_mitre_platforms_id()).add(prob);
				}
				List<AttackPatternSuccessProbability> compiledProbs = new ArrayList<AttackPatternSuccessProbability>();
				for (Integer attack_pattern_id : probabilities.keySet()) {
					for (Integer x_mitre_platforms_id : probabilities.get(attack_pattern_id).keySet()) {
						HashMap<Integer, List<AttackPatternSuccessProbability>> sortedProbMap = new HashMap<Integer, List<AttackPatternSuccessProbability>>();
						List<AttackPatternSuccessProbability> probList = probabilities.get(attack_pattern_id)
								.get(x_mitre_platforms_id);
						for (AttackPatternSuccessProbability prob : probList) {
							if (!sortedProbMap.containsKey(prob.getC_success_probability_id())) {
								ArrayList<AttackPatternSuccessProbability> sortedProbList = new ArrayList<AttackPatternSuccessProbability>();
								sortedProbMap.put(prob.getC_success_probability_id(), sortedProbList);
								// System.out.println("putting "+prob.getC_success_probability_id()+" for
								// platform "+x_mitre_platforms_id);
							}
							sortedProbMap.get(prob.getC_success_probability_id()).add(prob);
						}
						for (Integer success_probability_id : sortedProbMap.keySet()) {
							if (attackPatternSuccessProbabilityProvider.isControlSetComplete(
									sortedProbMap.get(success_probability_id).size(), x_mitre_platforms_id,
									success_probability_id)) {
								compiledProbs.add(sortedProbMap.get(success_probability_id).get(0));
							}
						}
					}
				}
				model.addAttribute("compiledProbabilities", compiledProbs);
				model.addAttribute("function3", "risk_assessment");*/
			}

			model.addAttribute("function2", "risk_questionnaire");
			addPageStandardsToModel(model, isAdmin);
			return "risk_questionnaire";
		}
		return null;
	}

	@GetMapping(value = { "/ServiceDefinition/ServiceModelling/{service_id}/RiskIdentification/newRisk",
			"/ServiceDefinition/ServiceModelling/{service_id}/RiskIdentification/{business_risk_id}" })
	public String createRiskIdentificationView(ModelMap model,
			@PathVariable(name = "service_id", required = true) Integer service_id,
			@PathVariable(name = "business_risk_id", required = false) Integer business_risk_id,
			Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Service Modelling");
		model.addAttribute("function", "risk_identification");
		model.addAttribute("application", "service_definition");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			OrganizationProvider organizationProvider = new OrganizationProvider();
			List<Organization> organizations = organizationProvider.getAllOrganizations();
			model.addAttribute("organizations", organizations);
			ServiceProvider serviceProvider = new ServiceProvider();
			List<Service> services = serviceProvider.getAllServices();
			model.addAttribute("services", services);
			Map<Integer, List<BusinessRisk>> businessRisksPerService = new HashMap<Integer, List<BusinessRisk>>();
			BusinessRiskProvider businessRiskProvider = new BusinessRiskProvider();
			for (Service service : services) {
				List<BusinessRisk> businessRisks = businessRiskProvider
						.getAssignedBusinessRisksForService(service.getService_id());
				businessRisksPerService.put(service.getService_id(), businessRisks);
			}
			model.addAttribute("risks_per_service", businessRisksPerService);
			if (service_id != null && service_id > 0) {
				Service service = serviceProvider.find(service_id);
				if (service != null) {
					RiskCategoryProvider riskCategoryProvider = new RiskCategoryProvider();
					List<RiskCategory> impactCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), service.getService_id(), RiskCategoryProvider.RiskCategoryType.Impact);					
					if (impactCategories == null) {
						impactCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), -1, RiskCategoryProvider.RiskCategoryType.Impact);
					}
					model.addAttribute("impactCategories",impactCategories);
					model.addAttribute("function2", "risk_identification");
					CapabilityKillerProvider capabilityKillerProvider = new CapabilityKillerProvider();
					List<CapabilityKiller> capabilityKillers = capabilityKillerProvider.getAllCapabilityKillers();
					model.addAttribute("capability_killers", capabilityKillers);
					if (business_risk_id != null && business_risk_id > 0) {
						BusinessRisk businessRisk = businessRiskProvider.find(business_risk_id);
						if (businessRisk != null) {
							model.addAttribute("business_risk_id", businessRisk.getBusiness_risk_id());
							model.addAttribute("business_risk_name", businessRisk.getBusiness_risk_name());
							model.addAttribute("business_risk_description",
									businessRisk.getBusiness_risk_description());
							model.addAttribute("business_risk_impact", businessRisk.getBusiness_risk_impact());
							RiskCategory applicableCategory = null;
							if (impactCategories != null) {
								for (RiskCategory impactCategory : impactCategories) {
									if (businessRisk.getBusiness_risk_default_order() >= impactCategory.getRisk_value_min() && businessRisk.getBusiness_risk_default_order() <= impactCategory.getRisk_value_max()) {
										applicableCategory = impactCategory;
										break;
									}
								}
							}
							model.addAttribute("business_risk_default_order",
									businessRisk.getBusiness_risk_default_order());
							model.addAttribute("applicableCategory",applicableCategory);
							List<CapabilityKiller> businessRiskCapabilityKillers = capabilityKillerProvider
									.getAllCapabilityKillersForBusinessRisk(businessRisk.getBusiness_risk_id());
							model.addAttribute("business_risk_capability_killers", businessRiskCapabilityKillers);
						}
					} else {
						model.addAttribute("business_risk_id", -1);
					}
				}

			}

		}
		addPageStandardsToModel(model, isAdmin);
		return "subnet_modelling";
	}

	@GetMapping(value = "/ServiceDefinition/ServiceModelling/{service_id}/ImpactOrdering")
	public String createRiskImpactOrderView(ModelMap model,
			@PathVariable(name = "service_id", required = true) Integer service_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Service Modelling");
		model.addAttribute("function", "risk_identification");
		model.addAttribute("application", "service_definition");
		model.addAttribute("submenu","impactOrdering");
		model.addAttribute("menu","serviceDefinition");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			
			OrganizationProvider organizationProvider = new OrganizationProvider();
			List<Organization> organizations = organizationProvider.getAllOrganizations();
			model.addAttribute("organizations", organizations);
			ServiceProvider serviceProvider = new ServiceProvider();
			List<Service> services = serviceProvider.getAllServices();
			model.addAttribute("services", services);
			Map<Integer, List<BusinessRisk>> businessRisksPerService = new HashMap<Integer, List<BusinessRisk>>();
			BusinessRiskProvider businessRiskProvider = new BusinessRiskProvider();
			for (Service service : services) {
				List<BusinessRisk> businessRisks = businessRiskProvider
						.getAssignedBusinessRisksForService(service.getService_id());
				businessRisksPerService.put(service.getService_id(), businessRisks);
			}
			model.addAttribute("risks_per_service", businessRisksPerService);
			if (service_id != null && service_id > 0) {
				Service service = serviceProvider.find(service_id);
				RiskCategoryProvider riskCategoryProvider = new RiskCategoryProvider();
				List<RiskCategory> impactCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), service_id, RiskCategoryProvider.RiskCategoryType.Impact);
				if (impactCategories == null) {
					impactCategories = riskCategoryProvider.getRiskCategoriesForServiceAndOrganization(service.getOrganization_id(), -1, RiskCategoryProvider.RiskCategoryType.Impact);
				}
				model.addAttribute("impactCategories",impactCategories);
				if (service != null) {
					model.addAttribute("function2", "impact_ordering");
					List<BusinessRisk> businessRisks = businessRiskProvider
							.getAssignedBusinessRisksForService(service_id);
					BusinessRiskOrderProvider businessRiskOrderProvider = new BusinessRiskOrderProvider();
					BusinessRiskCustomOrder businessRiskOrder = businessRiskOrderProvider.findForService(service_id);
					if (businessRiskOrder == null) {
						model.addAttribute("function3", "auto_generated_order");
					} else {
						List<BusinessRiskCustomOrderList> customOrderList = businessRiskOrderProvider
								.getCustomOrderList(businessRiskOrder.getOrder_id());
						HashMap<String, List<OrderedBusinessRisk>> listPerWeightSegment = new HashMap<String, List<OrderedBusinessRisk>>();
						for (int i = 0; i <= 20; i++) {
							listPerWeightSegment.put(String.valueOf(i), new ArrayList<OrderedBusinessRisk>());
						}
						for (BusinessRiskCustomOrderList customOrder : customOrderList) {
							int weight = (int) Math.round(customOrder.getWeight() * 100);
							int segment = weight / 5;
							OrderedBusinessRisk orderedBusinessRisk = new OrderedBusinessRisk();
							orderedBusinessRisk.setWeight(weight);
							for (RiskCategory impactCategory : impactCategories) {
								if (weight >= impactCategory.getRisk_value_min() && weight <= impactCategory.getRisk_value_max()) {
									orderedBusinessRisk.setImpactCategory(impactCategory);
									break;
								}
							}
							orderedBusinessRisk.setBusiness_risk_custom_order_list(customOrder);
							for (BusinessRisk businessRisk : businessRisks) {
								if (businessRisk.getBusiness_risk_id() == customOrder.getBusiness_risk_id()) {
									orderedBusinessRisk.setBusiness_risk(businessRisk);
									break;
								}
							}
							listPerWeightSegment.get(String.valueOf(segment)).add(orderedBusinessRisk);
						}
						model.addAttribute("risksPerSegment", listPerWeightSegment);
					}
				}

			}

		}
		createPageStandardsForOrganizationAndServiceSubpages(model, 0, service_id);
		addPageStandardsToModel(model, isAdmin);
		return "subnet_modelling";
	}

	@GetMapping(value = "/ServiceDefinition/ServiceModelling/{service_id}/ImpactOrdering/ModifyOrder")
	public String generateImpactOrderingView(ModelMap model,
			@PathVariable(name = "service_id", required = true) Integer service_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Service Modelling");
		model.addAttribute("function", "risk_identification");
		model.addAttribute("application", "service_definition");
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		int organization_id = 0;
		if (isAuthorized) {
			OrganizationProvider organizationProvider = new OrganizationProvider();
			List<Organization> organizations = organizationProvider.getAllOrganizations();
			model.addAttribute("organizations", organizations);
			ServiceProvider serviceProvider = new ServiceProvider();
			List<Service> services = serviceProvider.getAllServices();
			model.addAttribute("services", services);
			Map<Integer, List<BusinessRisk>> businessRisksPerService = new HashMap<Integer, List<BusinessRisk>>();
			BusinessRiskProvider businessRiskProvider = new BusinessRiskProvider();
			for (Service service : services) {
				List<BusinessRisk> businessRisks = businessRiskProvider
						.getAssignedBusinessRisksForService(service.getService_id());
				businessRisksPerService.put(service.getService_id(), businessRisks);
			}
			model.addAttribute("risks_per_service", businessRisksPerService);
			
			if (service_id != null && service_id > 0) {
				Service service = serviceProvider.find(service_id);
				organization_id = service.getOrganization_id();
				if (service != null) {
					model.addAttribute("function2", "modify_impact_ordering");
					List<BusinessRisk> businessRisks = businessRiskProvider
							.getAssignedBusinessRisksForService(service_id);
					BusinessRiskOrderProvider businessRiskOrderProvider = new BusinessRiskOrderProvider();
					BusinessRiskCustomOrder businessRiskOrder = businessRiskOrderProvider.findForService(service_id);
					//if (businessRiskOrder == null) {
						HashMap<BusinessRisk, List<BusinessRisk>> questionnaire = new HashMap<BusinessRisk, List<BusinessRisk>>();
						Object[] businessRiskArray = businessRisks.toArray();
						for (int i = 0; i < businessRiskArray.length; i++) {
							List<BusinessRisk> risks = new ArrayList<BusinessRisk>();
							for (int j = i + 1; j < businessRiskArray.length; j++) {
								risks.add((BusinessRisk) businessRiskArray[j]);
							}
							questionnaire.put((BusinessRisk) businessRiskArray[i], risks);
						}
						model.addAttribute("questionnaire_blocks", questionnaire);
					//}
				}
			}
		}
		addPageStandardsToModel(model, isAdmin);
		createPageStandardsForOrganizationAndServiceSubpages(model, organization_id, service_id);
		return "subnet_modelling";
	}

	/*
	 * @GetMapping(value =
	 * "/ServiceDefinition/ServiceModelling/{service_id}/RiskIdentification") public
	 * String createRiskOverview(ModelMap model,
	 * 
	 * @PathVariable(name = "service_id", required = true) Integer service_id,
	 * Authentication authentication) { boolean isAdmin = new
	 * SecurityUtils().isAdmin(authentication); UserDetails userDetails =
	 * (UserDetails) authentication.getPrincipal(); model.addAttribute("principal",
	 * userDetails.getUsername()); model.addAttribute("pageTitle",
	 * "RALF - Service Modelling"); model.addAttribute("function",
	 * "risk_identification"); model.addAttribute("application",
	 * "service_definition"); boolean isAuthorized = false; if (isAdmin) {
	 * isAuthorized = true; } if (isAuthorized) { OrganizationProvider
	 * organizationProvider = new OrganizationProvider(); List<Organization>
	 * organizations = organizationProvider.getAllOrganizations();
	 * model.addAttribute("organizations", organizations); ServiceProvider
	 * serviceProvider = new ServiceProvider(); List<Service> services =
	 * serviceProvider.getAllServices(); model.addAttribute("services", services);
	 * if (service_id != null && service_id > 0) { Service service =
	 * serviceProvider.find(service_id); if (service != null) {
	 * model.addAttribute("function2","risk_overview"); CapabilityKillerProvider
	 * capabilityKillerProvider = new CapabilityKillerProvider();
	 * BusinessRiskProvider businessRiskProvider = new BusinessRiskProvider();
	 * List<BusinessRisk> businessRisks = businessRiskProvider.
	 * 
	 * model.addAttribute("capability_killers",capabilityKillers); if
	 * (business_risk_id != null && business_risk_id > 0) { BusinessRiskProvider
	 * businessRiskProvider = new BusinessRiskProvider(); BusinessRisk businessRisk
	 * = businessRiskProvider.find(business_risk_id); if (businessRisk != null) {
	 * model.addAttribute("business_risk_id",businessRisk.getBusiness_risk_id());
	 * model.addAttribute("business_risk_name",businessRisk.getBusiness_risk_name())
	 * ; model.addAttribute("business_risk_description",
	 * businessRisk.getBusiness_risk_description());
	 * model.addAttribute("business_risk_impact",businessRisk.
	 * getBusiness_risk_impact());
	 * model.addAttribute("business_risk_default_order",businessRisk.
	 * getBusiness_risk_default_order()); List<CapabilityKiller>
	 * businessRiskCapabilityKillers =
	 * capabilityKillerProvider.getAllCapabilityKillersForBusinessRisk(businessRisk.
	 * getBusiness_risk_id());
	 * model.addAttribute("business_risk_capability_killers",
	 * businessRiskCapabilityKillers); } } }
	 * 
	 * }
	 * 
	 * } addPageStandardsToModel(model, isAdmin); return "subnet_modelling"; }
	 */
	@GetMapping(value = "/Settings")
	public String getSettings(ModelMap model, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("principal", userDetails.getUsername());
		model.addAttribute("pageTitle", "RALF - Settings");
		addPageStandardsToModel(model, isAdmin);
		model.addAttribute("application", "settings");
		return "user_settings";
	}

	/*
	 * @GetMapping(value = { "/Administration/{function}",
	 * "/Administration/DataHarvesting/{order_id}", "/Administration" }) public
	 * String createAdminInterface(ModelMap model,
	 * 
	 * @PathVariable(name = "function", required = false) String function,
	 * 
	 * @PathVariable(name = "order_id", required = false) Integer order_id,
	 * Authentication authentication) { boolean isAdmin = new
	 * SecurityUtils().isAdmin(authentication); if (isAdmin) {
	 * UserGroupAuthorityProvider authorityProvider = new
	 * UserGroupAuthorityProvider(); SWStackProvider swStackProvider = new
	 * SWStackProvider(); List<Authority> userAuthorities =
	 * authorityProvider.getAllGroupAuthorities(); if (function != null) {
	 * model.addAttribute("prefix", ".."); } else { model.addAttribute("prefix",
	 * "."); } if ((function == null && order_id == null) || (function != null &&
	 * function.equals("Groups"))) { model.addAttribute("function", "Groups");
	 * model.addAttribute("userAuthorities", userAuthorities); } else if (function
	 * != null && function.equals("Stacks")) { model.addAttribute("function",
	 * "Stacks"); List<SerializedAuthoritiesWithSWStack> stackList = new
	 * ArrayList(); Iterable<SWStack> allStacks = swStackProvider.findAll(); for
	 * (Authority authority : userAuthorities) { Iterable<SWStack> stacks =
	 * swStackProvider.findAllForGroupname(authority.getGroupname());
	 * SerializedAuthoritiesWithSWStack serializedStacks = new
	 * SerializedAuthoritiesWithSWStack(authority, stacks);
	 * stackList.add(serializedStacks); } model.addAttribute("stackList",
	 * stackList); model.addAttribute("allStacks", allStacks); } else if (function
	 * != null && function.equals("Users")) { model.addAttribute("function",
	 * "Users"); model.addAttribute("userAuthorities", userAuthorities);
	 * UserGroupAuthorityProvider userProvider = new UserGroupAuthorityProvider();
	 * List<User> users = userProvider.getAllUsers();
	 * ArrayList<SerializedUserWithGroups> usersWithGroups = new
	 * ArrayList<SerializedUserWithGroups>(); for (User user : users) {
	 * List<UserGroupAssociation> associations = userProvider
	 * .getUserGroupAssociationsForUser(user.getUsername());
	 * SerializedUserWithGroups userWithGroups = new SerializedUserWithGroups();
	 * userWithGroups.setUsername(user.getUsername()); if (associations != null) {
	 * for (UserGroupAssociation association : associations) {
	 * userWithGroups.getGroups().add(association); } }
	 * usersWithGroups.add(userWithGroups); } model.addAttribute("userList",
	 * usersWithGroups); } else if ((function != null &&
	 * function.equals("DataHarvesting")) || order_id != null) {
	 * model.addAttribute("function", "Harvester"); TransformationOrderProvider
	 * transformationOrderProvider = new TransformationOrderProvider(); Map<Integer,
	 * String> idsAndNames = transformationOrderProvider.getAllOrderIDsAndNames();
	 * model.addAttribute("transformationOrderList", idsAndNames); if (order_id !=
	 * null) { model.addAttribute("prefix", "../.."); String name =
	 * transformationOrderProvider.getNameForOrderID(order_id);
	 * TransformationSchedule schedule =
	 * transformationOrderProvider.getTransformationSchedule(order_id);
	 * System.out.println(schedule); model.addAttribute("schedule", schedule);
	 * model.addAttribute("transformation_order_name", name); ExecutionListFactory
	 * executionListFactory = new ExecutionListFactory();
	 * LinkedList<TransformationOperatorContainer> operators = executionListFactory
	 * .getExecutionListForTransformationOrderId(order_id); Map<Integer,
	 * List<RequestHeaderProperty>> headerMap = new HashMap<Integer,
	 * List<RequestHeaderProperty>>(); for (TransformationOperatorContainer operator
	 * : operators) { if (operator.getContainerType() ==
	 * TransformationOperatorContainer.OperatorType.REQUEST) {
	 * List<RequestHeaderProperty> headerList = null; if
	 * (headerMap.containsKey(operator.getRequest().getOperator_id())) { headerList
	 * = headerMap.ge)t(operator.getRequest().getOperator_id()); } else { headerList
	 * = new ArrayList<RequestHeaderProperty>(); } List<RequestHeaderProperty>
	 * headers = transformationOrderProvider
	 * .getRequestHeaderPropertyForOperatorId(operator.getRequest().getOperator_id()
	 * ); System.out.println(headers); if (headers != null) {
	 * headerList.addAll(headers); }
	 * headerMap.put(operator.getRequest().getOperator_id(), headerList); } }
	 * model.addAttribute("headerMap", headerMap); model.addAttribute("operators",
	 * operators); model.addAttribute("transformation_order_id", order_id);
	 * Map<String, String> operatorNames = new HashMap<String, String>(); for
	 * (OperationEnumeration operatorName : OperationEnumeration.values()) {
	 * operatorNames.put(OperationEnumeration.getStringCodeForOperation(operatorName
	 * ), OperationEnumeration.getStringNameForOperation(operatorName)); }
	 * model.addAttribute("operatorNames", operatorNames);
	 * model.addAttribute("operatorMetadata", OperationMetadata.metadata); } }
	 * addPageStandardsToModel(model, (function != null ? "." : ""), isAdmin);
	 * UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	 * model.addAttribute("principal", userDetails.getUsername());
	 * model.addAttribute("pageTitle", "RALF - Administration");
	 * model.addAttribute("application", "administration"); return
	 * "administration_template"; } return null; }
	 */

	public static void createPageStandardsForOrganizationAndServiceSubpages(ModelMap model, int organization_id, int service_id) {
		String context = MainWebAppInitializer.context + "/";
		model.addAttribute("adress_impact_ordering", context+"ServiceDefinition/ServiceModelling/"+service_id+"/ImpactOrdering");
		model.addAttribute("adress_service_description",context+"ServiceDefinition/ServiceModelling/"+service_id);
		model.addAttribute("organization_id",organization_id);
		model.addAttribute("adress_risk_classes",context+"AssessmentPreferences/"+organization_id+"/"+service_id+"/riskValueSettings");
		model.addAttribute("adress_risk_phi", context+"AssessmentPreferences/"+organization_id+"/"+service_id+"/riskPhiSettings");
		model.addAttribute("adress_factor_weighing", context+"AssessmentPreferences/"+organization_id+"/adjustFactorWeights/"+service_id);
		model.addAttribute("adress_existence_probability",context+"RiskAssessment/"+organization_id+"/"+service_id+"/attackExistenceQuestionnaire");
	}
	public static void addPageStandardsToModel(ModelMap model, boolean isAdmin) {
		model.addAttribute("context", MainWebAppInitializer.context);
		String context = MainWebAppInitializer.context + "/";

		model.addAttribute("adress_dashboard", context + PageController.adress_dashboard);
		model.addAttribute("adress_application_modelling", context + PageController.adress_application_modeling);
		model.addAttribute("adress_assessment_schedules", context + PageController.adress_assessment_schedules);
		model.addAttribute("adress_assessment_reports", context + PageController.adress_assessment_reports);
		model.addAttribute("adress_settings", context + PageController.adress_settings);
		model.addAttribute("adress_logoff", context + PageController.adress_logoff);
		model.addAttribute("adress_vulnerability_management", context + PageController.adress_vulnerability_management);
		model.addAttribute("adress_vulnerability_list", context + PageController.address_vulnerability_list);
		model.addAttribute("adress_subnet_modelling", context + PageController.address_subnet_modelling);
		model.addAttribute("adress_risk_assessment", context + "RiskAssessment");
		if (isAdmin) {
			model.addAttribute("adress_administration", context + PageController.adress_administration);
			model.addAttribute("adress_administration_group_management",
					context + PageController.address_administration_group_management);
			model.addAttribute("adress_administration_application_assignments",
					context + PageController.address_administration_application_assignments);
			model.addAttribute("adress_administration_user_management",
					context + PageController.address_administration_user_management);
			model.addAttribute("adress_administration_data_harvesting",
					context + PageController.address_administration_data_harvesting);
		}
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("context", MainWebAppInitializer.context);
	}
/*
	public class SectionHelperClass {
		private String name;
		private List<Component> swComponents;

		public SectionHelperClass() {
			swComponents = new ArrayList();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Component> getSwComponents() {
			return swComponents;
		}

		public void setSwComponents(List<Component> swComponents) {
			this.swComponents = swComponents;
		}

	}

	public class GroupSelectionHelperClass {
		private String groupname;
		private boolean selected;

		public GroupSelectionHelperClass(String groupname) {
			this.groupname = groupname;
		}

		public GroupSelectionHelperClass(String groupname, boolean selected) {
			this.groupname = groupname;
			this.selected = selected;
		}

		public String getGroupname() {
			return groupname;
		}

		public void setGroupname(String groupname) {
			this.groupname = groupname;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

	}
*/
}
