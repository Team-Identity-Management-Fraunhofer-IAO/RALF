package de.ralf.threatmasterkitchen.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrder;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrderList;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskPairwiseComparison;
import com.sql.data.objects.persistence.threatmaster.services.Service;
import com.sql.data.provider.threatmaster.BusinessRiskOrderProvider;
import com.sql.data.provider.threatmaster.BusinessRiskProvider;
import com.sql.data.provider.threatmaster.CapabilityKillerProvider;
import com.sql.data.provider.threatmaster.ServiceProvider;

import de.ralf.threatmasterkitchen.controller.datatransfer.BusinessRiskComparisonDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.BusinessRiskComparisonListDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.BusinessRiskDefinitionDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.BusinessRiskWeightUpdateDTO;
import de.ralf.threatmasterkitchen.controller.datatransfer.ServiceDefinitionDTO;
import engines.ahp.BusinessRiskAHP;

@Controller
public class ServiceModellingController {

	@PostMapping("/ServiceDefinition/ServiceModelling/saveService")
	public RedirectView saveService(@ModelAttribute final ServiceDefinitionDTO serviceDefinition,
			Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			if (serviceDefinition != null) {
				int service_id = serviceDefinition.getService_id();
				String service_name = serviceDefinition.getService_name();
				String service_description = serviceDefinition.getService_description();
				int organization_id = serviceDefinition.getOrganization_id();
				ServiceProvider serviceProvider = new ServiceProvider();
				Service persistentService = null;
				if ((service_id == -1) || (persistentService = serviceProvider.find(service_id)) == null) {
					persistentService = new Service();
				}
				persistentService.setService_name(service_name);
				persistentService.setService_description(service_description);
				persistentService.setOrganization_id(organization_id);
				if ((service_id == -1) || (persistentService = serviceProvider.find(service_id)) == null) {
					serviceProvider.persist(persistentService);
				} else {
					serviceProvider.update(persistentService);
				}
				BusinessRiskProvider businessRiskProvider = new BusinessRiskProvider();
				businessRiskProvider.deleteAllBusinessRiskAssociationsForService(service_id);
				if (serviceDefinition.getService_risk_list() != null) {
					System.out.println(serviceDefinition.getService_risk_list());
					for (Integer business_risk_id : serviceDefinition.getService_risk_list()) {
						BusinessRisk businessRisk = businessRiskProvider.find(business_risk_id);
						if (businessRisk != null) {
							businessRiskProvider.persist(businessRisk, persistentService.getService_id());
						}
					}
				}

				return new RedirectView("/RALFThreatMasterKitchen/ServiceDefinition/ServiceModelling/"
						+ persistentService.getService_id());
			}
		}
		return new RedirectView("/RALFThreatMasterKitchen/ServiceDefinition");
	}

	@PostMapping("/ServiceDefinition/ServiceModelling/{service_id}/RiskIdentification/saveRisk")
	public RedirectView saveRisk(@ModelAttribute final BusinessRiskDefinitionDTO businessRiskDefinition,
			@PathVariable(name = "service_id", required = true) Integer service_id, Authentication authentication) {
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
			if (service_id != null && service_id != -1) {
				int business_risk_id = businessRiskDefinition.getBusiness_risk_id();
				String business_risk_name = businessRiskDefinition.getBusiness_risk_name();
				String business_risk_description = businessRiskDefinition.getBusiness_risk_description();
				long business_risk_impact = businessRiskDefinition.getBusiness_risk_impact();
				int business_risk_default_order = businessRiskDefinition.getBusiness_risk_default_order();
				boolean business_risk_general = businessRiskDefinition.isBusiness_risk_general();
				BusinessRiskProvider businessRiskProvider = new BusinessRiskProvider();
				BusinessRisk businessRisk = new BusinessRisk();
				if (business_risk_id != -1) {
					businessRisk = businessRiskProvider.find(business_risk_id);
				}
				businessRisk.setBusiness_risk_name(business_risk_name);
				businessRisk.setBusiness_risk_description(business_risk_description);
				businessRisk.setBusiness_risk_impact(business_risk_impact);
				businessRisk.setBusiness_risk_default_order(business_risk_default_order);
				if (business_risk_general) {
					businessRisk.setService_id(service_id);
				} else {
					businessRisk.setService_id(0);
				}
				if (business_risk_id != -1)
					businessRiskProvider.update(businessRisk);
				else
					businessRiskProvider.persist(businessRisk);
				CapabilityKillerProvider capabilityKillerProvider = new CapabilityKillerProvider();
				List<Integer> capabilityKillerIDs = businessRiskDefinition.getCapability_killer_ids();
				if (capabilityKillerIDs != null) {
					for (Integer capabilityKillerID : capabilityKillerIDs) {
						capabilityKillerProvider.persistForBusinessRisk(capabilityKillerID,
								businessRisk.getBusiness_risk_id());
					}
				}
				businessRiskProvider.persist(businessRisk, service_id);
				if (business_risk_id != -1)
					return new RedirectView("/RALFThreatMasterKitchen/ServiceDefinition/ServiceModelling/" + service_id
							+ "/RiskIdentification/" + business_risk_id);
				else
					return new RedirectView(
							"/RALFThreatMasterKitchen/ServiceDefinition/ServiceModelling/" + service_id);
			}
		}
		return new RedirectView("/RALFThreatMasterKitchen/ServiceDefinition");
	}

	@PostMapping("/ServiceDefinition/ServiceModelling/{service_id}/ImpactOrdering/updateWeight")
	public ResponseEntity updateWeight(@RequestBody final BusinessRiskWeightUpdateDTO weightUpdateDTO, @PathVariable(name="service_id", required=true) Integer service_id, Authentication authentication) {
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
			BusinessRiskOrderProvider orderProvider = new BusinessRiskOrderProvider();
			BusinessRiskCustomOrderList customOrderList = orderProvider.getCustomOrderList(weightUpdateDTO.getOrder_id(), weightUpdateDTO.getBusiness_risk_id());
		
			double newWeight = ((double) weightUpdateDTO.getWeight())/100;
			customOrderList.setWeight(newWeight);
			System.out.println(customOrderList.getWeight());
			orderProvider.updateBusinessRiskCustomOrderList(customOrderList);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PostMapping("/ServiceDefinition/ServiceModelling/{service_id}/ImpactOrdering/saveOrder")
	public ResponseEntity saveOrder(
			@RequestBody final List<BusinessRiskComparisonListDTO> businessRiskComparisonListDTO,
			@PathVariable(name = "service_id", required = true) Integer service_id, Authentication authentication) {
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
			if (service_id != null && service_id != -1) {
				BusinessRiskOrderProvider orderProvider = new BusinessRiskOrderProvider();
				orderProvider.clearBusinessRiskPairwiseComparisonList(service_id);
				List<BusinessRiskPairwiseComparison> pairwiseComparisonList = new ArrayList<BusinessRiskPairwiseComparison>();
				BusinessRiskProvider riskProvider = new BusinessRiskProvider();
				List<BusinessRisk> businessRisks = riskProvider.getAssignedBusinessRisksForService(service_id);
				for (BusinessRiskComparisonListDTO businessRiskComparisonList : businessRiskComparisonListDTO) {
					int business_risk_id = businessRiskComparisonList.getBusiness_risk_id();
					for (BusinessRiskComparisonDTO businessRiskComparison : businessRiskComparisonList
							.getCompared_risks()) {
						int compared_risk_id = businessRiskComparison.getCompared_business_risk_id();
						String operator = businessRiskComparison.getOperator();
						int comp = businessRiskComparison.getComparison();
						BusinessRiskPairwiseComparison comparison = new BusinessRiskPairwiseComparison();
						comparison.setService_id(service_id);
						if (operator.equals("gteq")) {
							comparison.setBusiness_risk_id1(business_risk_id);
							comparison.setBusiness_risk_id2(compared_risk_id);
						} else if (operator.equals("lteq")) {
							comparison.setBusiness_risk_id1(compared_risk_id);
							comparison.setBusiness_risk_id2(business_risk_id);
						} else {
							return new ResponseEntity(HttpStatus.NOT_FOUND);
						}
						comparison.setComparison(comp);
						System.out.println(comp);
						orderProvider.persist(comparison);
						pairwiseComparisonList.add(comparison);
					}
				}
				BusinessRiskAHP ahp = new BusinessRiskAHP(businessRisks, pairwiseComparisonList);
				double[] weights = ahp.compute();
				List<BusinessRiskCustomOrderList> customOrderList = new ArrayList<BusinessRiskCustomOrderList>();
				for (int i = 0; i < weights.length; i++) {
					BusinessRiskCustomOrderList customOrder = new BusinessRiskCustomOrderList();
					BusinessRisk businessRisk = businessRisks.get(i);
					if (businessRisk.getBusiness_risk_impact() != 0) {
						customOrder.setBusiness_risk_impact_max(businessRisk.getBusiness_risk_impact());
						customOrder.setBusiness_risk_impact_min(businessRisk.getBusiness_risk_impact());
					}
					customOrder.setBusiness_risk_id(businessRisk.getBusiness_risk_id());
					customOrder.setWeight(weights[i]);
					customOrderList.add(customOrder);
				}
				customOrderList.sort(new Comparator<BusinessRiskCustomOrderList>() {
					public int compare(BusinessRiskCustomOrderList o1, BusinessRiskCustomOrderList o2) {
						if (o1.getWeight() > o2.getWeight())
							return 1;
						if (o1.getWeight() < o2.getWeight())
							return -1;
						return 0;
					}
				});
				long upperBound = 0;
				for (BusinessRiskCustomOrderList customOrder : customOrderList) {
					if (upperBound < customOrder.getBusiness_risk_impact_min()) {
						upperBound = customOrder.getBusiness_risk_impact_min();
					}
					if (customOrder.getBusiness_risk_impact_max() == 0) {
						customOrder.setBusiness_risk_impact_max(upperBound);
					}
				}

				long lowerBound = 0;
				for (int i = customOrderList.size() - 1; i >= 0; i--) {
					if (lowerBound < customOrderList.get(i).getBusiness_risk_impact_max()) {
						lowerBound = customOrderList.get(i).getBusiness_risk_impact_max();
					}
					if (customOrderList.get(i).getBusiness_risk_impact_min() == 0) {
						customOrderList.get(i).setBusiness_risk_impact_min(lowerBound);
					}
				}

				BusinessRiskCustomOrder businessRiskCustomOrder = new BusinessRiskCustomOrder();
				businessRiskCustomOrder.setService_id(service_id);
				orderProvider.persist(businessRiskCustomOrder);
				for (BusinessRiskCustomOrderList businessRiskCustomOrderList : customOrderList) {
					businessRiskCustomOrderList.setOrder_id(businessRiskCustomOrder.getOrder_id());
					orderProvider.persist(businessRiskCustomOrderList);
				}
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
