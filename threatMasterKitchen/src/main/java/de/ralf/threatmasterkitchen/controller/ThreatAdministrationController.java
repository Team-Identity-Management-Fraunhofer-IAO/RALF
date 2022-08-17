package de.ralf.threatmasterkitchen.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import com.sql.data.objects.persistence.threatmaster.organizations.RiskCategory;
import com.sql.data.objects.persistence.threatmaster.organizations.RiskCategoryBundle;
import com.sql.data.objects.persistence.threatmaster.organizations.RiskPhi;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;
import com.sql.data.objects.persistence.threatmaster.risks.SuccessProbability;
import com.sql.data.provider.threatmaster.BusinessRiskProvider;
import com.sql.data.provider.threatmaster.CapabilityKillerProvider;
import com.sql.data.provider.threatmaster.RiskCategoryProvider;
import com.sql.data.provider.threatmaster.RiskPhiProvider;
import com.sql.data.provider.threatmaster.datawarehouse.AttackPatternPlatformProvider;
import com.sql.data.provider.threatmaster.datawarehouse.AttackPatternSuccessProbabilityProvider;

import de.ralf.threatmasterkitchen.controller.datatransfer.BusinessRiskDefinitionDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.PlatformUpdateDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.RiskClassDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.RiskPhiDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.SuccessProbabilityUpdateDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.UpdateRiskClassDTO;

@Controller
public class ThreatAdministrationController {
	
	@PostMapping("/ThreatAdministration/PlatformManagement/updatePlatforms")
	public ResponseEntity saveRisk(@RequestBody final PlatformUpdateDTO platformUpdate,
			Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		boolean isAuthorized = false;
		if (isAdmin)
			isAuthorized = true;
		if (isAuthorized) {
			String[] platformArray = platformUpdate.getPlatforms().split(",");
			List<String> platforms = new ArrayList<String>(Arrays.asList(platformArray));
			AttackPatternPlatformProvider attackPatternPlatformProvider = new AttackPatternPlatformProvider();
			attackPatternPlatformProvider.updatePlatformsForAttackPattern(platformUpdate.getAttack_pattern_id(), platforms);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/ThreatAdministration/SuccessProbabilities/updateProbability")
	public ResponseEntity<Integer> updateProbability(@RequestBody final SuccessProbabilityUpdateDTO successProbabilityUpdate, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		boolean isAuthorized = false;
		if (isAdmin)
			isAuthorized = true;
		if (isAuthorized) {
			if (successProbabilityUpdate.getAction().equals("new")) {
				SuccessProbability prob = new SuccessProbability();
				prob.setAttack_pattern_id(successProbabilityUpdate.getAttack_pattern_id());
				prob.setSuccess_probability_order(successProbabilityUpdate.getProbability());
				prob.setSuccess_probability_name("");
				AttackPatternSuccessProbabilityProvider provider = new AttackPatternSuccessProbabilityProvider();
				provider.persistSuccessProbability(prob, successProbabilityUpdate.getFactors(), successProbabilityUpdate.getControls());
				return new ResponseEntity<Integer>(prob.getC_success_probability_id(), HttpStatus.OK);
			}else if (successProbabilityUpdate.getAction().equals("delete")) {
				AttackPatternSuccessProbabilityProvider provider = new AttackPatternSuccessProbabilityProvider();
				provider.deleteSuccessProbability(successProbabilityUpdate.getC_success_probability_id());
				return new ResponseEntity<Integer>(0,HttpStatus.OK);
			}
		}
		return new ResponseEntity<Integer>(-1,HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/AssessmentPreferences/{organization_id}/{service_id}/updateRiskPhi")
	public ResponseEntity updateRiskPhi(@PathVariable(name="organization_id", required=true) Integer organization_id, @PathVariable(name="service_id", required=true) Integer service_id, @RequestBody final RiskPhiDTO riskPhiDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		boolean isAuthorized = false;
		if (isAdmin)
			isAuthorized = true;
		if (isAuthorized) {
			RiskPhiProvider riskPhiProvider = new RiskPhiProvider();
			RiskPhi riskPhi = new RiskPhi();
			riskPhi.setOrganization_id(organization_id);
			riskPhi.setService_id(service_id);
			riskPhi.setRisk_phi(new BigDecimal(riskPhiDTO.getRisk_phi_value()));
			riskPhi.setLoadTimestamp(new Timestamp(System.currentTimeMillis()));
			riskPhi.setRisk_phi_type(riskPhiDTO.getRisk_phi_type());			
			riskPhiProvider.persist(riskPhi);
			return new ResponseEntity(HttpStatus.OK);
		}		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/AssessmentPreferences/{organization_id}/{service_id}/updateRiskClasses")
	public ResponseEntity updateRiskClasses(@PathVariable(name="organization_id", required=true) Integer organization_id, @PathVariable(name="service_id", required=true) Integer service_id, @RequestBody final UpdateRiskClassDTO updateRiskClassDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		boolean isAuthorized = false;
		if (isAdmin)
			isAuthorized = true;
		if (isAuthorized) {
			RiskCategoryProvider riskCategoryProvider= new RiskCategoryProvider();
			RiskCategoryBundle bundle = new RiskCategoryBundle();
			bundle.setOrganization_id(organization_id);
			bundle.setService_id(service_id);
			bundle.setLoadTimestmap(new Timestamp(System.currentTimeMillis()));
			riskCategoryProvider.persist(bundle);
			for (RiskClassDTO riskClass : updateRiskClassDTO.getRiskClasses()) {
				RiskCategory riskCategory = new RiskCategory();
				riskCategory.setRisk_category_bundle_id(bundle.getRisk_category_bundle_id());
				riskCategory.setCategory_name(riskClass.getName());
				riskCategory.setRisk_category_type("risk");
				riskCategory.setRisk_value_min(riskClass.getFrom());
				riskCategory.setRisk_value_max(riskClass.getTo());
				riskCategoryProvider.persist(riskCategory);
			}
			for (RiskClassDTO impactClass : updateRiskClassDTO.getImpactClasses()) {
				RiskCategory riskCategory = new RiskCategory();
				riskCategory.setRisk_category_bundle_id(bundle.getRisk_category_bundle_id());
				riskCategory.setCategory_name(impactClass.getName());
				riskCategory.setRisk_category_type("impact");
				riskCategory.setRisk_value_min(impactClass.getFrom());
				riskCategory.setRisk_value_max(impactClass.getTo());
				riskCategoryProvider.persist(riskCategory);
			}
			for (RiskClassDTO probabilityClass : updateRiskClassDTO.getProbabilityClasses()) {
				RiskCategory riskCategory = new RiskCategory();
				riskCategory.setRisk_category_bundle_id(bundle.getRisk_category_bundle_id());
				riskCategory.setCategory_name(probabilityClass.getName());
				riskCategory.setRisk_category_type("probability");
				riskCategory.setRisk_value_min(probabilityClass.getFrom());
				riskCategory.setRisk_value_max(probabilityClass.getTo());
				riskCategoryProvider.persist(riskCategory);
			}
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

}
