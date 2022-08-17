package de.ralf.threatmasterkitchen.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sql.data.objects.helpers.attackpatterns.NamedThreat;
import com.sql.data.objects.helpers.attackpatterns.OrderedKillChainPhase;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReport;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControl;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControlExcludedThreats;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportPlatform;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactorWeights;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorWeights;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternPrerequisite;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternSuccessProbability;
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
import com.sql.data.objects.persistence.threatmaster.services.Service;
import com.sql.data.objects.persistence.threatmaster.services.ServiceExistenceProbability;
import com.sql.data.provider.threatmaster.AttackFactorQuestionnaireProvider;
import com.sql.data.provider.threatmaster.BusinessRiskProvider;
import com.sql.data.provider.threatmaster.ControlReportProvider;
import com.sql.data.provider.threatmaster.RiskReportProvider;
import com.sql.data.provider.threatmaster.ServiceProvider;
import com.sql.data.provider.threatmaster.datawarehouse.AttackPatternPlatformProvider;
import com.sql.data.provider.threatmaster.datawarehouse.AttackPatternPrerequisiteProvider;
import com.sql.data.provider.threatmaster.datawarehouse.AttackPatternSuccessProbabilityProvider;
import com.sql.data.provider.threatmaster.datawarehouse.CourseOfActionAttackPatternProvider;

import de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects.AssessedThreat;
import de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects.AssessmentControl;
import de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects.SuccessProbability;
import de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects.AssessmentControl.ControlApplication;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.FactorQuestionnaireResponseDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.FactorResponseDTO;
import de.ralf.threatmasterkitchen.security.utils.SecurityUtils;

@Controller
public class AssessmentController {
	
	@PostMapping("/RiskAssessment/submitExistenceProbabilityQuestionnaire")
	public ResponseEntity submitExistenceProbabilityQuestionnaire(@RequestBody FactorQuestionnaireResponseDTO questionnaireResponse, Authentication authentication) {
		int service_id = questionnaireResponse.getService_id();
		int organization_id = questionnaireResponse.getOrganization_id();
		List<FactorResponseDTO> attackMotivatingResponses = questionnaireResponse.getAttack_motivating_questions();
		List<FactorResponseDTO> vulnerabilityEnablingResponses = questionnaireResponse.getVulnerability_enabling_questions();
		AttackFactorQuestionnaireProvider questionnaireProvider = new AttackFactorQuestionnaireProvider();
		AttackMotivatingQuestionnaireResponse attackMotivatingQuestionnaireResponse = new AttackMotivatingQuestionnaireResponse();
		Timestamp loadTimestamp = new Timestamp(System.currentTimeMillis());
		attackMotivatingQuestionnaireResponse.setLoadTimestamp(loadTimestamp);
		attackMotivatingQuestionnaireResponse.setOrganization_id(organization_id);
		attackMotivatingQuestionnaireResponse.setService_id(service_id);
		questionnaireProvider.persist(attackMotivatingQuestionnaireResponse);
		AttackMotivatingQuestionnaire quest = questionnaireProvider.getLastAttackMotivatingQuestionnaire(organization_id, service_id);
		
		if (quest == null) {
			quest = new AttackMotivatingQuestionnaire();
			quest.setOrganization_id(organization_id);
			quest.setService_id(service_id);
			quest.setLoadTimestamp(loadTimestamp);
			questionnaireProvider.persist(quest);
		}
		BigDecimal maxAttackMotivatingScore = new BigDecimal(0.0, MathContext.DECIMAL64);
		for (FactorResponseDTO attackMotivatingResponse : attackMotivatingResponses) {
			AttackMotivatingQuestionResponse response = new AttackMotivatingQuestionResponse();
			response.setAttack_motivating_questionnaire_response_id(attackMotivatingQuestionnaireResponse.getAttack_motivating_questionnaire_response_id());
			response.setAttack_motivating_factor_question_id(attackMotivatingResponse.getFactor_question_id());
			response.setFactor_id(attackMotivatingResponse.getFactor_id());
			response.setJustification(attackMotivatingResponse.getJustification());
			response.setResponse(attackMotivatingResponse.isResponse());
			questionnaireProvider.persist(response);
			System.out.println(attackMotivatingResponse.getFactor_id()+""+attackMotivatingResponse.getFactor_question_id()+attackMotivatingResponse.getJustification()+attackMotivatingResponse.isResponse());
			if (response.isResponse()) {
				AttackMotivatingFactorWeights weight = questionnaireProvider.getAttackMotivatingFactorWeight(quest.getAttack_motivating_questionnaire_id(), response.getFactor_id());
				BigDecimal decWeight = new BigDecimal(weight==null?1.0:weight.getWeight(), MathContext.DECIMAL64);
				if (decWeight.compareTo(maxAttackMotivatingScore) > 0) {
					maxAttackMotivatingScore = new BigDecimal(weight==null?1.0:weight.getWeight(), MathContext.DECIMAL64);
				}
			}
		}
		VulnerabilityEnablingQuestionnaireResponse vulnerabilityEnablingQuestionnaireResponse = new VulnerabilityEnablingQuestionnaireResponse();
		vulnerabilityEnablingQuestionnaireResponse.setLoadTimestamp(loadTimestamp);
		vulnerabilityEnablingQuestionnaireResponse.setOrganization_id(organization_id);
		vulnerabilityEnablingQuestionnaireResponse.setService_id(service_id);
		questionnaireProvider.persist(vulnerabilityEnablingQuestionnaireResponse);
		VulnerabilityEnablingFactorQuestionnaire vulnQuest = null;
		vulnQuest = questionnaireProvider.getLastVulnerabilityEnablingQuestionnaire(organization_id, service_id);
		if (vulnQuest == null) {
			vulnQuest = new VulnerabilityEnablingFactorQuestionnaire();
			vulnQuest.setLoadTimestamp(loadTimestamp);
			vulnQuest.setOrganization_id(organization_id);
			vulnQuest.setService_id(service_id);
			questionnaireProvider.persist(vulnQuest);
		}
		BigDecimal maxVulnerabilityEnablingScore = new BigDecimal(0.0, MathContext.DECIMAL64);
		for (FactorResponseDTO vulnerabilityEnablingResponse : vulnerabilityEnablingResponses) {
			VulnerabilityEnablingQuestionResponse response = new VulnerabilityEnablingQuestionResponse();
			response.setVulnerability_enabling_questionnaire_response_id(vulnerabilityEnablingQuestionnaireResponse.getVulnerability_enabling_questionnaire_response_id());
			response.setVulnerability_enabling_factor_question_id(vulnerabilityEnablingResponse.getFactor_question_id());
			response.setFactor_id(vulnerabilityEnablingResponse.getFactor_id());
			response.setJustification(vulnerabilityEnablingResponse.getJustification());
			response.setResponse(vulnerabilityEnablingResponse.isResponse());
			questionnaireProvider.persist(response);
			if (response.isResponse()) {
				VulnerabilityEnablingFactorWeights weight = questionnaireProvider.getVulnerabilityEnablingFactorWeight(vulnQuest.getVulnerability_enabling_questionnaire_id(), response.getFactor_id());
				BigDecimal decWeight = new BigDecimal(weight==null?1.0:weight.getWeight(), MathContext.DECIMAL64);
				System.out.println(decWeight.doubleValue());
				if (decWeight.compareTo(maxVulnerabilityEnablingScore) > 0) {
					maxVulnerabilityEnablingScore = new BigDecimal(weight==null?1.0:weight.getWeight(), MathContext.DECIMAL64);
				}
			}
		}
		
		System.out.println("Vulnerability Enabling Score: "+maxVulnerabilityEnablingScore.doubleValue());
		System.out.println("Attack Motivating Score: "+maxAttackMotivatingScore.doubleValue());
		double combinedScore = maxVulnerabilityEnablingScore.doubleValue()*0.5+maxAttackMotivatingScore.doubleValue()*0.5;
		combinedScore = (combinedScore > 1)?1:combinedScore;
		System.out.println("Attack Existence Probability is: "+combinedScore);
		
		ServiceProvider serviceProvider = new ServiceProvider();
		ServiceExistenceProbability existenceProbability = new ServiceExistenceProbability();
		existenceProbability.setAttack_enabling_score(maxVulnerabilityEnablingScore);
		existenceProbability.setAttack_motivation_score(maxAttackMotivatingScore);
		existenceProbability.setExistence_probability(new BigDecimal(combinedScore, MathContext.DECIMAL64));
		existenceProbability.setLoadTimestamp(loadTimestamp);
		existenceProbability.setService_id(service_id);
		existenceProbability.setOrganization_id(organization_id);
		serviceProvider.persist(existenceProbability);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping(value = "/RiskAssessment/{service_id}/RiskReporting/excludeThreat/{risk_report_id}/{threat_id}")
	public ResponseEntity excludeThreatFromReport(@PathVariable(name="service_id", required = true) Integer service_id, @PathVariable(name="risk_report_id", required = true) Integer risk_report_id, @PathVariable(name="threat_id", required=true) Integer threat_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			RiskReportProvider riskReportProvider = new RiskReportProvider();
			riskReportProvider.persistExcludedThreat(risk_report_id, threat_id);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value = "/RiskAssessment/{service_id}/RiskReporting/includeThreat/{risk_report_id}/{threat_id}")
	public ResponseEntity includeThreatInReport(@PathVariable(name="service_id", required = true) Integer service_id, @PathVariable(name="risk_report_id", required = true) Integer risk_report_id, @PathVariable(name="threat_id", required=true) Integer threat_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			RiskReportProvider riskReportProvider = new RiskReportProvider();
			riskReportProvider.deleteExcludedThreat(risk_report_id, threat_id);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value = "/RiskAssessment/{service_id}/RiskReporting/{control_report_id}")
	public ResponseEntity<Integer> createRiskReport(	@PathVariable(name = "service_id", required = true) Integer service_id,
			@PathVariable(name = "control_report_id") Integer control_report_id, Authentication authentication) {
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		boolean isAuthorized = false;
		if (isAdmin) {
			isAuthorized = true;
		}
		if (isAuthorized) {
			ControlReportProvider controlReportProvider = new ControlReportProvider();
			ControlReport controlReport = controlReportProvider.find(control_report_id);
			if (controlReport == null) {
				return null;
			}
			List<ControlReportControl> controls = controlReportProvider
					.getControlReportControlsByReport(control_report_id);
			List<ControlReportPlatform> platforms = controlReportProvider.getControlReportPlatformsByReport(control_report_id);
			if (platforms == null) {
				platforms = new ArrayList<ControlReportPlatform>();
			}
			List<String> platform_names = new ArrayList<String>();
			for (ControlReportPlatform platform : platforms) {
				platform_names.add(platform.getPlatform());
			}
			ServiceProvider serviceProvider = new ServiceProvider();
			Service service = serviceProvider.find(service_id);
			CourseOfActionAttackPatternProvider courseOfActionAttackPatternProvider = new CourseOfActionAttackPatternProvider();
			AttackPatternSuccessProbabilityProvider attackPatternSuccessProbabilityProvider = new AttackPatternSuccessProbabilityProvider();
			Map<Integer, List<AssessmentControl>> controlsPerThreat = new HashMap<Integer, List<AssessmentControl>>();
			Map<Integer, List<SuccessProbability>> successProbabilitiesPerThreat = new HashMap<Integer, List<SuccessProbability>>();
			
			AttackFactorQuestionnaireProvider vulnerabilityEnablingFactorProvider = new AttackFactorQuestionnaireProvider();
			VulnerabilityEnablingQuestionnaireResponse vulnQuestionnaire = vulnerabilityEnablingFactorProvider.getLastVulnerabilityEnablingQuestionnaireResponse(service.getOrganization_id(), service.getService_id());
			List<VulnerabilityEnablingQuestionResponse> vulnerabilityEnablingFactors = vulnerabilityEnablingFactorProvider.getResponsesForVulnerabilityEnablingQuestionnaireResponse(vulnQuestionnaire.getVulnerability_enabling_questionnaire_response_id());
			for (ControlReportControl control : controls) {
				List<Integer> threat_ids = courseOfActionAttackPatternProvider
						.getAttackPatternsForCourseOfActionId(control.getControl_id(), platform_names);
				//List<Integer> threat_ids = courseOfActionAttackPatternProvider.filterAttackPatternsThroughPlatformIds(threat_ids_unfiltered, platform_ids);
				if (control.isAllThreats() && (control.getAppliesTo().equals("fullyAppliedControl")
						|| control.getAppliesTo().equals("partiallyAppliedControl"))) {
					for (Integer threat_id : threat_ids) {
						if (!controlsPerThreat.containsKey(threat_id)) {
							controlsPerThreat.put(threat_id, new ArrayList<AssessmentControl>());
						}
						AssessmentControl asControl = new AssessmentControl();
						if (control.getAppliesTo().equals("fullyAppliedControl")) {
							asControl.setAppliesTo(ControlApplication.appliesFully);
						} else {
							asControl.setAppliesTo(ControlApplication.appliesPartially);
						}
						asControl.setControl_id(control.getControl_id());
						controlsPerThreat.get(threat_id).add(asControl);

						List<AttackPatternSuccessProbability> successProbabilities = attackPatternSuccessProbabilityProvider
								.getSuccessProbabilitiesForAttackPattern(threat_id);
						int c_success_probability_id = -1;
						int x_mitre_platforms_id = -1;
						for (AttackPatternSuccessProbability succProb : successProbabilities) {
							if (!successProbabilitiesPerThreat.containsKey(succProb.getAttack_pattern_id())) {
								successProbabilitiesPerThreat.put(succProb.getAttack_pattern_id(),
										new ArrayList<SuccessProbability>());
								
							}
							SuccessProbability searchProb = new SuccessProbability();
							searchProb.setC_success_probability_id(succProb.getC_success_probability_id());
							if (successProbabilitiesPerThreat.get(threat_id).indexOf(searchProb)==-1) {
								SuccessProbability succProbList = new SuccessProbability();
								succProbList.setC_success_probability_id(succProb.getC_success_probability_id());
								succProbList.setSuccessProbability(succProb.getSuccess_probability_name());
								succProbList.setSuccessProbabilityOrder(succProb.getSuccess_probability_order());
								succProbList.setProbabilities(new ArrayList<AttackPatternSuccessProbability>());
								succProbList.getProbabilities().add(succProb);
								successProbabilitiesPerThreat.get(threat_id).add(succProbList);
							}else {
								List<AttackPatternSuccessProbability> probs = successProbabilitiesPerThreat.get(threat_id).get(successProbabilitiesPerThreat.get(threat_id).indexOf(searchProb)).getProbabilities();
								if (threat_id == 236) {
									System.out.println("236 is being added for "+searchProb.getC_success_probability_id());
									System.out.println(succProb.getId());
									System.out.println(!probs.contains(succProb));
								}
								if (!probs.contains(succProb)) {
									successProbabilitiesPerThreat.get(threat_id).get(successProbabilitiesPerThreat.get(threat_id).indexOf(searchProb)).getProbabilities().add(succProb);
								}
							}
							/*
							if ((c_success_probability_id == -1 || x_mitre_platforms_id == -1) 
									|| (c_success_probability_id != succProb.getC_success_probability_id() || x_mitre_platforms_id != succProb.getX_mitre_platforms_id())) {
								SuccessProbability succProbList = new SuccessProbability();
								succProbList.setC_success_probability_id(succProb.getC_success_probability_id());
								succProbList.setSuccessProbability(succProb.getSuccess_probability_name());
								succProbList.setSuccessProbabilityOrder(succProb.getSuccess_probability_order());
								succProbList.setProbabilities(new ArrayList<AttackPatternSuccessProbability>());
								//succProbList.getProbabilities().add(succProb);
								c_success_probability_id = succProb.getC_success_probability_id();		
								x_mitre_platforms_id = succProb.getX_mitre_platforms_id();
								successProbabilitiesPerThreat.get(threat_id).get(succProb.getX_mitre_platforms_id()).add(succProbList);
							} else {
								SuccessProbability searchProb = new SuccessProbability();
								searchProb.setC_success_probability_id(c_success_probability_id);
								successProbabilitiesPerThreat.get(threat_id).get(succProb.getX_mitre_platforms_id()).get(successProbabilitiesPerThreat.get(threat_id).get(succProb.getX_mitre_platforms_id()).indexOf(searchProb)).getProbabilities().add(succProb);
							}*/
						}
					}
				} else if (!(control.getAppliesTo().equals("fullyAppliedControl")
						|| control.getAppliesTo().equals("partiallyAppliedControl"))) {
					for (Integer threat_id : threat_ids) {
						if (!controlsPerThreat.containsKey(threat_id)) {
							controlsPerThreat.put(threat_id, new ArrayList<AssessmentControl>());
						}

						List<AttackPatternSuccessProbability> successProbabilities = attackPatternSuccessProbabilityProvider
								.getSuccessProbabilitiesForAttackPattern(threat_id);
						int c_success_probability_id = -1;
						int x_mitre_platforms_id = -1;
						for (AttackPatternSuccessProbability succProb : successProbabilities) {
							if (!successProbabilitiesPerThreat.containsKey(succProb.getAttack_pattern_id())) {
								successProbabilitiesPerThreat.put(succProb.getAttack_pattern_id(),
										new ArrayList<SuccessProbability>());
								
							}
							
							SuccessProbability searchProb = new SuccessProbability();
							searchProb.setC_success_probability_id(succProb.getC_success_probability_id());
							if (successProbabilitiesPerThreat.get(threat_id).indexOf(searchProb)==-1) {
								SuccessProbability succProbList = new SuccessProbability();
								succProbList.setC_success_probability_id(succProb.getC_success_probability_id());
								succProbList.setSuccessProbability(succProb.getSuccess_probability_name());
								succProbList.setSuccessProbabilityOrder(succProb.getSuccess_probability_order());
								succProbList.setProbabilities(new ArrayList<AttackPatternSuccessProbability>());
								succProbList.getProbabilities().add(succProb);
								successProbabilitiesPerThreat.get(threat_id).add(succProbList);
							}else{
								List<AttackPatternSuccessProbability> probs = successProbabilitiesPerThreat.get(threat_id).get(successProbabilitiesPerThreat.get(threat_id).indexOf(searchProb)).getProbabilities();
								if (threat_id == 236) {
									System.out.println("236 is being added");
									System.out.println(succProb.getId());
									System.out.println(!probs.contains(succProb));
								}
								if (!probs.contains(succProb)) {
									successProbabilitiesPerThreat.get(threat_id).get(successProbabilitiesPerThreat.get(threat_id).indexOf(searchProb)).getProbabilities().add(succProb);
								}
							}
							/*
							if ((c_success_probability_id == -1 || x_mitre_platforms_id == -1) 
									|| (c_success_probability_id != succProb.getC_success_probability_id() || x_mitre_platforms_id != succProb.getX_mitre_platforms_id())) {
								SuccessProbability succProbList = new SuccessProbability();
								succProbList.setC_success_probability_id(succProb.getC_success_probability_id());
								succProbList.setSuccessProbability(succProb.getSuccess_probability_name());
								succProbList.setSuccessProbabilityOrder(succProb.getSuccess_probability_order());
								succProbList.setProbabilities(new ArrayList<AttackPatternSuccessProbability>());
								//succProbList.getProbabilities().add(succProb);
								c_success_probability_id = succProb.getC_success_probability_id();
								x_mitre_platforms_id = succProb.getX_mitre_platforms_id();
								successProbabilitiesPerThreat.get(threat_id).get(succProb.getX_mitre_platforms_id()).add(succProbList);
							} else {
								SuccessProbability searchProb = new SuccessProbability();
								searchProb.setC_success_probability_id(c_success_probability_id);
								successProbabilitiesPerThreat.get(threat_id).get(succProb.getX_mitre_platforms_id()).get(successProbabilitiesPerThreat.get(threat_id).get(succProb.getX_mitre_platforms_id()).indexOf(searchProb)).getProbabilities().add(succProb);
							}*/
						}
					}
				} else if (!control.isAllThreats() && (control.getAppliesTo().equals("fullyAppliedControl")
						|| control.getAppliesTo().equals("partiallyAppliedControl"))) {
					List<ControlReportControlExcludedThreats> excluded_threats = controlReportProvider
							.getExcludedThreatsByControlReportControl(control.getControl_report_control_id());
					List<Integer> excluded_threat_ids = new ArrayList<Integer>();
					for (ControlReportControlExcludedThreats excluded_threat : excluded_threats) {
						excluded_threat_ids.add(excluded_threat.getThreat_id());
					}
					for (Integer threat_id : threat_ids) {
						if (excluded_threat_ids.contains(threat_id)) {
							if (!controlsPerThreat.containsKey(threat_id)) {
								controlsPerThreat.put(threat_id, new ArrayList<AssessmentControl>());
							}
							continue;
						}
						if (!controlsPerThreat.containsKey(threat_id)) {
							controlsPerThreat.put(threat_id, new ArrayList<AssessmentControl>());
						}
						AssessmentControl asControl = new AssessmentControl();
						if (control.getAppliesTo().equals("fullyAppliedControl")) {
							asControl.setAppliesTo(ControlApplication.appliesFully);
						} else {
							asControl.setAppliesTo(ControlApplication.appliesPartially);
						}
						asControl.setControl_id(control.getControl_id());
						controlsPerThreat.get(threat_id).add(asControl);

						List<AttackPatternSuccessProbability> successProbabilities = attackPatternSuccessProbabilityProvider
								.getSuccessProbabilitiesForAttackPattern(threat_id);
						int c_success_probability_id = -1;
						int x_mitre_platforms_id = -1;
						for (AttackPatternSuccessProbability succProb : successProbabilities) {
							if (!successProbabilitiesPerThreat.containsKey(succProb.getAttack_pattern_id())) {
								successProbabilitiesPerThreat.put(succProb.getAttack_pattern_id(),
										new ArrayList<SuccessProbability>());
								
							}							
							SuccessProbability searchProb = new SuccessProbability();
							searchProb.setC_success_probability_id(succProb.getC_success_probability_id());
							if (successProbabilitiesPerThreat.get(threat_id).indexOf(searchProb)==-1) {
								SuccessProbability succProbList = new SuccessProbability();
								succProbList.setC_success_probability_id(succProb.getC_success_probability_id());
								succProbList.setSuccessProbability(succProb.getSuccess_probability_name());
								succProbList.setSuccessProbabilityOrder(succProb.getSuccess_probability_order());
								succProbList.setProbabilities(new ArrayList<AttackPatternSuccessProbability>());
								succProbList.getProbabilities().add(succProb);
								successProbabilitiesPerThreat.get(threat_id).add(succProbList);
							}else {
								List<AttackPatternSuccessProbability> probs = successProbabilitiesPerThreat.get(threat_id).get(successProbabilitiesPerThreat.get(threat_id).indexOf(searchProb)).getProbabilities();
								if (threat_id == 236) {
									System.out.println("236 is being added");
									System.out.println(succProb.getId());
									System.out.println(!probs.contains(succProb));
								}
								if (!probs.contains(succProb)) {
									successProbabilitiesPerThreat.get(threat_id).get(successProbabilitiesPerThreat.get(threat_id).indexOf(searchProb)).getProbabilities().add(succProb);
								}
							}
							/*
							if ((c_success_probability_id == -1 || x_mitre_platforms_id == -1) 
									|| (c_success_probability_id != succProb.getC_success_probability_id() || x_mitre_platforms_id != succProb.getX_mitre_platforms_id())) {
								SuccessProbability succProbList = new SuccessProbability();
								succProbList.setC_success_probability_id(succProb.getC_success_probability_id());
								succProbList.setSuccessProbability(succProb.getSuccess_probability_name());
								succProbList.setSuccessProbabilityOrder(succProb.getSuccess_probability_order());
								succProbList.setProbabilities(new ArrayList<AttackPatternSuccessProbability>());
								//succProbList.getProbabilities().add(succProb);
								c_success_probability_id = succProb.getC_success_probability_id();
								x_mitre_platforms_id = succProb.getX_mitre_platforms_id();
								successProbabilitiesPerThreat.get(threat_id).get(succProb.getX_mitre_platforms_id()).add(succProbList);
							} else {
								SuccessProbability searchProb = new SuccessProbability();
								searchProb.setC_success_probability_id(c_success_probability_id);
								successProbabilitiesPerThreat.get(threat_id).get(succProb.getX_mitre_platforms_id()).get(successProbabilitiesPerThreat.get(threat_id).get(succProb.getX_mitre_platforms_id()).indexOf(searchProb)).getProbabilities().add(succProb);
							}*/
						}
					}

				}
			}
			Map<Integer, List<SuccessProbability>> applicableSuccessProbabilities = new HashMap<Integer, List<SuccessProbability>>();
			Map<Integer, List<SuccessProbability>> notApplicableSuccessProbabilities = new HashMap<Integer, List<SuccessProbability>>();
			
			/* Compare Controls Per Threat and Success Probability Per Threat */
			for (Integer threat_id : controlsPerThreat.keySet()) {
				if (successProbabilitiesPerThreat.containsKey(threat_id)) {
						List<SuccessProbability> probs = successProbabilitiesPerThreat.get(threat_id);
						if (probs == null) {
							continue;
						}
						
						if (threat_id == 236) {
							System.out.println("Threat 236!");
						}
						List<SuccessProbability> emptyProbs = new ArrayList<SuccessProbability>();
						boolean noProbabilitiesAdded = true;
						SuccessProbabilityLoop: for (SuccessProbability prob : probs) {
							if (prob.getProbabilities().size()==1) {
								if (prob.getProbabilities().get(0).getCourse_of_action_id()==null) {
									emptyProbs.add(prob);
								}
							}
							if (threat_id == 236)
								System.out.println(prob.getProbabilities().size()+" > "+controlsPerThreat.get(threat_id).size()+" ?");
							if (!(prob.getProbabilities().size() == 1 && controlsPerThreat.get(threat_id).size()==0 && prob.getProbabilities().get(0).getCourse_of_action_id()==null)) {
								if (prob.getProbabilities().size() > controlsPerThreat.get(threat_id).size()) {
									if (threat_id == 236)
									System.out.println("threat 236 is being omitted, because probability list is larger than control list");
									continue;
								} 
							}			
							if (threat_id == 236)
								System.out.println("threat 236 is being checked");
							List<AttackPatternSuccessProbability> probProbabilities = new ArrayList<AttackPatternSuccessProbability>();
							probProbabilities.addAll(prob.getProbabilities());
							//Check if Success Probability Control is empty and control list is empty!
							//TODO
							controlLoop: 
							for (AssessmentControl control : controlsPerThreat.get(threat_id)) {
								if (control.getControl_id()==18 && threat_id==61)
									System.out.println("Encrypt everything control");
								if (control.getControl_id()==18 && threat_id==61)
									System.out.println(prob.getProbabilities());
								for (AttackPatternSuccessProbability succProb : prob.getProbabilities()) {
									if (succProb.getCourse_of_action_id()!=null && succProb.getCourse_of_action_id() == control.getControl_id()) {
										probProbabilities.remove(succProb);
									}
									if (probProbabilities.isEmpty()) {
										break controlLoop;
									}
									if (control.getControl_id()==18 && threat_id==61)
										System.out.println("Remainder: "+prob.getProbabilities());
								}
							}
							if (!probProbabilities.isEmpty()) {
								if (!notApplicableSuccessProbabilities.containsKey(threat_id)) {
									notApplicableSuccessProbabilities.put(threat_id,
											new ArrayList<SuccessProbability>());
								}
								notApplicableSuccessProbabilities.get(threat_id).add(prob);								
								continue SuccessProbabilityLoop;
							}
							/*
							for (AssessmentControl control : controlsPerThreat.get(threat_id)) {
								boolean probDoesNotApply = true;								
								for (AttackPatternSuccessProbability succProb : prob.getProbabilities()) {
									System.out.println(succProb.getCourse_of_action_id());
									if (succProb.getCourse_of_action_id() != null
											&& succProb.getCourse_of_action_id() == control.getControl_id()) {
										probDoesNotApply = false;
										break;
									}
								}
								if (probDoesNotApply) {
									if (!notApplicableSuccessProbabilities.containsKey(threat_id)) {
										notApplicableSuccessProbabilities.put(threat_id,
												new ArrayList<SuccessProbability>());
									}
									notApplicableSuccessProbabilities.get(threat_id).add(prob);
									continue SuccessProbabilityLoop;
								}
							}*/
							/* Success Probability does apply */
							// Do not add success probability if it is equal to 1 == avoided
							if (!(prob.getSuccessProbabilityOrder() == 1)) {
								if (!applicableSuccessProbabilities.containsKey(threat_id)) {
									applicableSuccessProbabilities.put(threat_id, new ArrayList<SuccessProbability>());
								}
								applicableSuccessProbabilities.get(threat_id).add(prob);
								noProbabilitiesAdded = false;
							}

						}
					if (noProbabilitiesAdded) {
						if (emptyProbs.size() > 0) {
							for (SuccessProbability prob : emptyProbs) {
								if (!(prob.getSuccessProbabilityOrder() == 1)) {
									if (!applicableSuccessProbabilities.containsKey(threat_id)) {
										applicableSuccessProbabilities.put(threat_id, new ArrayList<SuccessProbability>());
									}
									applicableSuccessProbabilities.get(threat_id).add(prob);
									noProbabilitiesAdded = false;
								}
							}
						}
					}
				}
			}
			List<Integer> applicableFactorIDs = new ArrayList<Integer>();
			for (VulnerabilityEnablingQuestionResponse factor : vulnerabilityEnablingFactors) {
				if (factor.isResponse()) {
					applicableFactorIDs.add(factor.getFactor_id());
				}
			}
			/*
			 * Check success probabilities for attack enabling factors here
			 */
			for (Integer threat_id : applicableSuccessProbabilities.keySet()) {
				Map<Integer, SuccessProbability> scoredProbs = new HashMap<Integer, SuccessProbability>();
				int maxScore = 0;
				if (applicableSuccessProbabilities.get(threat_id).size() > 1) {
					for (SuccessProbability prob : applicableSuccessProbabilities.get(threat_id)) {
						if (prob.getProbabilities().isEmpty()) {
							continue;
						}
						int score = 0;
						if (prob.getProbabilities().size() > 1) {
							//Probability is due to countermeasures
							score++;
						}
						if (prob.getProbabilities().get(0).getC_vulnerability_enabling_factor_id() != null) {
							if (applicableFactorIDs.contains(new Integer(prob.getProbabilities().get(0).getC_vulnerability_enabling_factor_id()))){
								score++;
							}
						}
						if (score > maxScore) {
							maxScore = score;
						}
						if (scoredProbs.containsKey(score)) {
							if (scoredProbs.get(score).getProbabilities().get(0).getSuccess_probability_order() > prob.getProbabilities().get(0).getSuccess_probability_order()) {
								scoredProbs.put(score, prob);
							}
						}
					}
					if (!scoredProbs.isEmpty()) {
						applicableSuccessProbabilities.get(threat_id).clear();
						applicableSuccessProbabilities.get(threat_id).add(scoredProbs.get(maxScore));
					}
				}
			}
			
			
			Map<Integer, SuccessProbability> maxSuccessProbPerThreat = new HashMap<Integer, SuccessProbability>();
			SuccessProbability universal_max_prob = null;
			AttackPatternPlatformProvider capabilityProvider = new AttackPatternPlatformProvider();
			BusinessRiskProvider riskProvider = new BusinessRiskProvider();
			List<Integer> capability_killer_ids = new ArrayList<Integer>();
			
			for (Integer threat_id : applicableSuccessProbabilities.keySet()) {
				List<Integer> killer_ids = capabilityProvider.getCapabilityKillerIdsForAttackPattern(threat_id);
				capability_killer_ids.addAll(killer_ids);				
				SuccessProbability max_prob = null;
				for (SuccessProbability prob : applicableSuccessProbabilities.get(threat_id)) {
					if (max_prob == null) {
						max_prob = prob;
					} else if (max_prob.getSuccessProbabilityOrder() < prob.getSuccessProbabilityOrder()) {
						max_prob = prob;
					}
				}
				maxSuccessProbPerThreat.put(threat_id, max_prob);
				if (universal_max_prob == null) {
					universal_max_prob = max_prob;
				} else if (universal_max_prob.getSuccessProbabilityOrder() < max_prob.getSuccessProbabilityOrder()) {
					universal_max_prob = max_prob;
				}
			}
			
			/*
			 * Save the Report
			 */
			RiskReportProvider riskReportProvider = new RiskReportProvider();
			RiskReport riskReport = new RiskReport();
			riskReport.setService_id(service_id);
			riskReport.setAssessmentTimestamp(new Timestamp(System.currentTimeMillis()));
			riskReport.setAssessed_success_probability_order(universal_max_prob.getSuccessProbabilityOrder());
			
			ServiceExistenceProbability existenceProb = serviceProvider.getLastExistenceProbabilityForService(service_id, service.getOrganization_id());
			riskReport.setService_existence_probability_id(existenceProb.getService_existence_probability_id());
			riskReportProvider.persist(riskReport);
			
			List<BusinessRisk> risks = riskProvider.getBusinessRiskForServiceAndCapabilityKiller(service_id, capability_killer_ids);
			for (BusinessRisk risk : risks) {
				riskReportProvider.persistBusinessRiskAssignment(riskReport.getRisk_report_id(), risk.getBusiness_risk_id());
			}			
			List<Integer> used_killer_ids = riskProvider.getCapabilityKillerIDsForBusinessRisks(service_id, capability_killer_ids);
			
			for (Integer threat_id : maxSuccessProbPerThreat.keySet()) {
				ArrayList<RiskReportThreatControl> persistedRiskReportThreatControls = new ArrayList<RiskReportThreatControl>();
				NamedThreat namedThreat = attackPatternSuccessProbabilityProvider.getNamedThreatForAttackPatternId(threat_id);
				RiskReportThreat reportThreat = new RiskReportThreat();
				reportThreat.setThreat_id(threat_id);
				reportThreat.setRisk_report_id(riskReport.getRisk_report_id());
				reportThreat.setThreat_name(namedThreat.getThreat_name());
				reportThreat.setThreat_description(namedThreat.getThreat_description());
				reportThreat.setSuccess_probability_order(maxSuccessProbPerThreat.get(threat_id).getSuccessProbabilityOrder());
				riskReportProvider.persist(reportThreat);
				
				for (AttackPatternSuccessProbability sucProb : maxSuccessProbPerThreat.get(threat_id).getProbabilities()) {
					RiskReportThreatControl reportThreatControl = new RiskReportThreatControl();
					reportThreatControl.setControl_id(sucProb.getCourse_of_action_id()==null?0:sucProb.getCourse_of_action_id());
					reportThreatControl.setThreat_id(threat_id);
					reportThreatControl.setRisk_report_id(riskReport.getRisk_report_id());
					if (persistedRiskReportThreatControls.contains(reportThreatControl)) {
						continue;
					}
					reportThreatControl.setControl_name(sucProb.getCourse_of_action_name());
					reportThreatControl.setControl_description(sucProb.getCourse_of_action_description());
					riskReportProvider.persist(reportThreatControl);
					persistedRiskReportThreatControls.add(reportThreatControl);
				}
			}
			

			
			riskReport.setAssessed_success_probability_order(0);
			riskReportProvider.update(riskReport);

			Map<Integer, Map<OrderedKillChainPhase, AssessedThreat>> assessed_threats = new HashMap<Integer, Map<OrderedKillChainPhase, AssessedThreat>>();
			AttackPatternPrerequisiteProvider prerequisiteProvider = new AttackPatternPrerequisiteProvider();
			/*
			 * TODO: Add Kill Chain Phase for adjustment of success probabilities
			 */
			for (Integer threat_id : maxSuccessProbPerThreat.keySet()) {

				List<OrderedKillChainPhase> kill_chain_phases = prerequisiteProvider
						.getKillChainPhasesForAttackPattern(threat_id);
				if (!assessed_threats.containsKey(threat_id)) {
					assessed_threats.put(threat_id, new HashMap<OrderedKillChainPhase, AssessedThreat>());
				}
				for (OrderedKillChainPhase kill_chain_phase : kill_chain_phases) {
					if (!assessed_threats.get(threat_id).containsKey(kill_chain_phase)) {
						AssessedThreat aThreat = new AssessedThreat();
						aThreat.setThreat_id(threat_id);
						aThreat.setSuccessors(new ArrayList<AssessedThreat>());
						ArrayList<OrderedKillChainPhase> kPhases = new ArrayList<OrderedKillChainPhase>();
						kPhases.add(kill_chain_phase);
						aThreat.setKill_chain_phases(kPhases);
						aThreat.setOriginal_success_probability_order(
								maxSuccessProbPerThreat.get(threat_id).getSuccessProbabilityOrder());
						assessed_threats.get(threat_id).put(kill_chain_phase, aThreat);
					}
				}
			}
			for (Integer threat_id : assessed_threats.keySet()) {
				List<AttackPatternPrerequisite> prereqs = prerequisiteProvider
						.getRequiredPatternsForAttackPattern(threat_id);
				Map<OrderedKillChainPhase, AssessedThreat> threats = assessed_threats.get(threat_id);
				for (AttackPatternPrerequisite prereq : prereqs) {
					if (!assessed_threats.containsKey(prereq.getRequired_attack_pattern_id())) {
						//System.out.println("Threat "+prereq.getRequired_attack_pattern_id()+" is not included in the original threat set!");
						continue;
					}
					for (OrderedKillChainPhase reqKillChainPhase : assessed_threats
							.get(prereq.getRequired_attack_pattern_id()).keySet()) {
						for (OrderedKillChainPhase killChainPhase : threats.keySet()) {
							if (reqKillChainPhase.getKill_chain_order() < killChainPhase.getKill_chain_order()) {
								AssessedThreat reqThreat = assessed_threats.get(prereq.getRequired_attack_pattern_id())
										.get(reqKillChainPhase);
								AssessedThreat sucThreat = assessed_threats.get(threat_id).get(killChainPhase);
								reqThreat.getSuccessors().add(sucThreat);
							}
						}
					}
				}
			}
			adjustSuccessProbabilities(1, 5, assessed_threats);
			/*
			 * Persist Pivoted Threats
			 */
			Map<Integer, Integer> successProbabilityPerCapabilityKiller = new HashMap<Integer, Integer>();
			for (Integer threat_id : assessed_threats.keySet()) {
				Map<OrderedKillChainPhase, AssessedThreat> threatsPerKillChain = assessed_threats.get(threat_id);
				for (OrderedKillChainPhase reqKillChainPhase : threatsPerKillChain.keySet()) {
					List<AssessedThreat> sucThreats = threatsPerKillChain.get(reqKillChainPhase).getSuccessors();
					for (AssessedThreat sucThreat : sucThreats) {
						for (OrderedKillChainPhase sucKillChainPhase : sucThreat.getKill_chain_phases()) {
							if (sucKillChainPhase.getKill_chain_order() > reqKillChainPhase.getKill_chain_order()) {
								RiskReportThreatControlPivotedThreat pivotedThreat = new RiskReportThreatControlPivotedThreat();
								pivotedThreat.setThreat_id(threat_id);
								pivotedThreat.setKill_chain_phase_order(reqKillChainPhase.getKill_chain_order());
								pivotedThreat.setKill_chain_phase_name(reqKillChainPhase.getPhase_name());
								pivotedThreat.setKill_chain_name(reqKillChainPhase.getKill_chain_name());
								pivotedThreat.setRisk_report_id(riskReport.getRisk_report_id());
								pivotedThreat.setSucceeding_threat_id(sucThreat.getThreat_id());
								pivotedThreat.setSucceeding_kill_chain_phase_order(sucKillChainPhase.getKill_chain_order());
								pivotedThreat.setSucceeding_kill_chain_phase_name(sucKillChainPhase.getPhase_name());
								pivotedThreat.setSucceeding_kill_chain_name(sucKillChainPhase.getKill_chain_name());
								pivotedThreat.setSuccess_probability_order(sucThreat.getSuccess_probability_order()==0?sucThreat.getOriginal_success_probability_order():sucThreat.getSuccess_probability_order());
								/*
								if (threatsPerKillChain.get(reqKillChainPhase).getSuccess_probability_order() == 0) {
									pivotedThreat.setSuccess_probability_order(threatsPerKillChain.get(reqKillChainPhase).getOriginal_success_probability_order());
								}else {
									pivotedThreat.setSuccess_probability_order(threatsPerKillChain.get(reqKillChainPhase).getSuccess_probability_order());
								}*/
								riskReportProvider.persist(pivotedThreat);
								List<Integer> killer_ids = capabilityProvider.getCapabilityKillerIdsForAttackPattern(sucThreat.getThreat_id());
								for (Integer killer_id : killer_ids) {
									if (used_killer_ids.contains(killer_id)) {
										if (successProbabilityPerCapabilityKiller.containsKey(killer_id)) {
											Integer order = successProbabilityPerCapabilityKiller.get(killer_id);
											if (order > pivotedThreat.getSuccess_probability_order()) {
												continue;
											}
										}
										successProbabilityPerCapabilityKiller.put(killer_id, pivotedThreat.getSuccess_probability_order());
									}
								}
							}
						}
					}
					
				}
			}
			for (int killer_id : successProbabilityPerCapabilityKiller.keySet()) {
				
				ArrayList<Integer> capIDs = new ArrayList<Integer>();
				capIDs.add(killer_id);
				List<BusinessRisk> capRisks = riskProvider.getBusinessRiskForServiceAndCapabilityKiller(service_id, capIDs);
				System.out.println(capRisks);
				for (BusinessRisk capRisk : capRisks) {
					RiskReportCapabilityKillerSuccessProbability successProbability = new RiskReportCapabilityKillerSuccessProbability();
					successProbability.setBusiness_risk_id(capRisk.getBusiness_risk_id());
					successProbability.setCapability_killer_id(killer_id);
					successProbability.setRisk_report_id(riskReport.getRisk_report_id());
					successProbability.setSuccess_probability_order(successProbabilityPerCapabilityKiller.get(killer_id));
					riskReportProvider.persist(successProbability);
				}
			}

			return new ResponseEntity(riskReport.getRisk_report_id(), HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.FORBIDDEN);
	}
	
	public void adjustSuccessProbabilities(int current_kill_chain_phase, int last_kill_chain_phase,
			Map<Integer, Map<OrderedKillChainPhase, AssessedThreat>> assessed_threats) {
		if (!(current_kill_chain_phase >= last_kill_chain_phase)) {
			for (Integer threat_id : assessed_threats.keySet()) {
				for (OrderedKillChainPhase killChainPhase : assessed_threats.get(threat_id).keySet()) {
					if (killChainPhase.getKill_chain_order() == current_kill_chain_phase) {
						AssessedThreat reqThreat = assessed_threats.get(threat_id).get(killChainPhase);
						List<AssessedThreat> potSucThreats = reqThreat.getSuccessors();
						for (AssessedThreat potSucThreat : potSucThreats) {
							for (OrderedKillChainPhase sucKillChain : assessed_threats.get(potSucThreat.getThreat_id())
									.keySet()) {
								if (sucKillChain.getKill_chain_order() > killChainPhase.getKill_chain_order()) {
									AssessedThreat sucThreat = assessed_threats.get(potSucThreat.getThreat_id())
											.get(sucKillChain);
									if (sucThreat.getOriginal_success_probability_order() > reqThreat
											.getOriginal_success_probability_order()) {
										sucThreat
												.setSuccess_probability_order(reqThreat.getOriginal_success_probability_order());
									}
								}
							}
						}
						break;
					}
				}
			}
			adjustSuccessProbabilities(++current_kill_chain_phase, last_kill_chain_phase, assessed_threats);
		}
	}

}
