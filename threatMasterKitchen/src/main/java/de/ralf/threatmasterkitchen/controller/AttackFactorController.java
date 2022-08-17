package de.ralf.threatmasterkitchen.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactorWeights;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingQuestionnaireContent;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestionnaireContent;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorWeights;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskPairwiseComparison;
import com.sql.data.provider.threatmaster.AttackFactorQuestionnaireProvider;

import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.FactorComparisonDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.FactorComparisonListDTO;
import engines.ahp.FactorAHP;
import engines.ahp.factor.orderingObjects.Factor;
import engines.ahp.factor.orderingObjects.FactorOrdering;

@Controller
public class AttackFactorController {
	
	@PostMapping("/AssessmentPreferences/adjustWeights")
	public ResponseEntity adjustWeights(@RequestBody FactorComparisonListDTO comparisonList, Authentication authentication) {
		int organization_id = comparisonList.getOrganization_id();
		int service_id = comparisonList.getService_id();
		Timestamp loadTimestamp = new Timestamp(System.currentTimeMillis());
		List<FactorOrdering> motivatingFactorComparisonList = new ArrayList<FactorOrdering>();
		AttackFactorQuestionnaireProvider questionnaireProvider = new AttackFactorQuestionnaireProvider();
		List<Factor> motivatingFactors = new ArrayList<Factor>();
		AttackMotivatingQuestionnaire attackMotivatingQuestionnaire = new AttackMotivatingQuestionnaire();
		attackMotivatingQuestionnaire.setOrganization_id(organization_id);
		attackMotivatingQuestionnaire.setService_id(service_id);
		attackMotivatingQuestionnaire.setLoadTimestamp(loadTimestamp);
		questionnaireProvider.persist(attackMotivatingQuestionnaire);
		for (FactorComparisonDTO comparisonDTO : comparisonList.getMotivatingFactorComparisons()) {
			Factor fac = new Factor();
			fac.setFactor_id(comparisonDTO.getFactor_id());
			if (!(motivatingFactors.contains(fac))) {
				motivatingFactors.add(fac);
			}
			Factor compFac = new Factor();
			compFac.setFactor_id(comparisonDTO.getFactor_comparison_id());
			if (!(motivatingFactors.contains(compFac))) {
				motivatingFactors.add(compFac);
			}
			FactorOrdering factorOrdering = new FactorOrdering();
			if (comparisonDTO.getComparison_operator().equals("gteq")) {
				factorOrdering.setFactor_id1(comparisonDTO.getFactor_id());
				factorOrdering.setFactor_id2(comparisonDTO.getFactor_comparison_id());
			}else if (comparisonDTO.getComparison_operator().equals("lteq")) {
				factorOrdering.setFactor_id1(comparisonDTO.getFactor_comparison_id());
				factorOrdering.setFactor_id2(comparisonDTO.getFactor_id());
			}else {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			factorOrdering.setComparison(comparisonDTO.getComparison());
			motivatingFactorComparisonList.add(factorOrdering);
			AttackMotivatingQuestionnaireContent content = new AttackMotivatingQuestionnaireContent();
			content.setAttack_motivating_questionnaire_id(attackMotivatingQuestionnaire.getAttack_motivating_questionnaire_id());
			content.setComparison(factorOrdering.getComparison());
			content.setFactor_id1(factorOrdering.getFactor_id1());
			content.setFactor_id2(factorOrdering.getFactor_id2());
			content.setComparison_operator(comparisonDTO.getComparison_operator());
			questionnaireProvider.persist(content);
		}
		FactorAHP ahp = new FactorAHP(motivatingFactors, motivatingFactorComparisonList);
		double[] scores = ahp.compute();
		BigDecimal maxScore = new BigDecimal(0.0, MathContext.DECIMAL64);
		for (double score : scores) {
			if (maxScore.compareTo(new BigDecimal(score, MathContext.DECIMAL64)) < 0) {
				maxScore = new BigDecimal(score, MathContext.DECIMAL64);
			}
		}
		//Normalize scores
		for (int i = 0; i < scores.length; i++) {
			System.out.println(scores[i]);
			BigDecimal originalScore = new BigDecimal(scores[i],MathContext.DECIMAL64);
			BigDecimal newScore = originalScore.divide(maxScore, 2, RoundingMode.HALF_UP);
			double score = newScore.doubleValue();
			System.out.println(score);
			Factor fac = motivatingFactors.get(i);
			AttackMotivatingFactorWeights weight = new AttackMotivatingFactorWeights();
			weight.setAttack_motivating_factor_questionnaire_id(attackMotivatingQuestionnaire.getAttack_motivating_questionnaire_id());
			weight.setFactor_id(fac.getFactor_id());
			weight.setOrganization_id(organization_id);
			weight.setService_id(service_id);
			weight.setWeight(score);
			questionnaireProvider.persist(weight);
		}
		
		VulnerabilityEnablingFactorQuestionnaire vulnerabilityEnablingFactorQuestionnaire = new VulnerabilityEnablingFactorQuestionnaire();
		vulnerabilityEnablingFactorQuestionnaire.setOrganization_id(organization_id);
		vulnerabilityEnablingFactorQuestionnaire.setService_id(service_id);
		vulnerabilityEnablingFactorQuestionnaire.setLoadTimestamp(loadTimestamp);
		questionnaireProvider.persist(vulnerabilityEnablingFactorQuestionnaire);
		List<FactorOrdering> vulnerabilityEnablingFactorOrderings = new ArrayList<FactorOrdering>();
		List<Factor> vulnerabilityEnablingFactors = new ArrayList<Factor>();
		for (FactorComparisonDTO comparisonDTO : comparisonList.getVulnerabilityEnablingFactorComparisons()) {
			Factor fac = new Factor();
			fac.setFactor_id(comparisonDTO.getFactor_id());
			if (!(vulnerabilityEnablingFactors.contains(fac))) {
				vulnerabilityEnablingFactors.add(fac);
			}
			Factor compFac = new Factor();
			compFac.setFactor_id(comparisonDTO.getFactor_comparison_id());
			if (!(vulnerabilityEnablingFactors.contains(compFac))) {
				vulnerabilityEnablingFactors.add(compFac);
			}
			FactorOrdering factorOrdering = new FactorOrdering();
			if (comparisonDTO.getComparison_operator().equals("gteq")) {
				factorOrdering.setFactor_id1(comparisonDTO.getFactor_id());
				factorOrdering.setFactor_id2(comparisonDTO.getFactor_comparison_id());
			}else if (comparisonDTO.getComparison_operator().equals("lteq")) {
				factorOrdering.setFactor_id1(comparisonDTO.getFactor_comparison_id());
				factorOrdering.setFactor_id2(comparisonDTO.getFactor_id());
			}else {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			factorOrdering.setComparison(comparisonDTO.getComparison());
			vulnerabilityEnablingFactorOrderings.add(factorOrdering);
			VulnerabilityEnablingFactorQuestionnaireContent content = new VulnerabilityEnablingFactorQuestionnaireContent();
			content.setVulnerability_enabling_factor_questionnaire_id(vulnerabilityEnablingFactorQuestionnaire.getVulnerability_enabling_questionnaire_id());
			content.setComparison(factorOrdering.getComparison());
			content.setFactor_id1(factorOrdering.getFactor_id1());
			content.setFactor_id2(factorOrdering.getFactor_id2());
			content.setComparison_operator(comparisonDTO.getComparison_operator());
			questionnaireProvider.persist(content);
		}
		
		ahp = new FactorAHP(vulnerabilityEnablingFactors, vulnerabilityEnablingFactorOrderings);
		scores = ahp.compute();
		maxScore = new BigDecimal(0.0, MathContext.DECIMAL64);
		for (double score : scores) {
			if (maxScore.compareTo(new BigDecimal(score, MathContext.DECIMAL64)) < 0) {
				maxScore = new BigDecimal(score, MathContext.DECIMAL64);
			}
		}
		//Normalize scores
		for (int i = 0; i < scores.length; i++) {
			BigDecimal originalScore = new BigDecimal(scores[i], MathContext.DECIMAL64);
			BigDecimal newScore = originalScore.divide(maxScore, 2, RoundingMode.HALF_UP);
			double score = newScore.doubleValue();
			Factor fac = vulnerabilityEnablingFactors.get(i);
			VulnerabilityEnablingFactorWeights weight = new VulnerabilityEnablingFactorWeights();
			weight.setVulnerability_enabling_factor_questionnaire_id(vulnerabilityEnablingFactorQuestionnaire.getVulnerability_enabling_questionnaire_id());
			weight.setFactor_id(fac.getFactor_id());
			weight.setOrganization_id(organization_id);
			weight.setService_id(service_id);
			weight.setWeight(score);
			questionnaireProvider.persist(weight);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
