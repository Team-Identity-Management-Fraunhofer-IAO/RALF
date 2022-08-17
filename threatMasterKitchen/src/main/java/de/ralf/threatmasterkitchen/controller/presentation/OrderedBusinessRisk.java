package de.ralf.threatmasterkitchen.controller.presentation;

import com.sql.data.objects.persistence.threatmaster.organizations.RiskCategory;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrderList;

public class OrderedBusinessRisk {
	private BusinessRisk business_risk;
	private BusinessRiskCustomOrderList business_risk_custom_order_list;
	private RiskCategory impactCategory;
	private int weight;
	
	public BusinessRisk getBusiness_risk() {
		return business_risk;
	}
	public void setBusiness_risk(BusinessRisk business_risk) {
		this.business_risk = business_risk;
	}
	public BusinessRiskCustomOrderList getBusiness_risk_custom_order_list() {
		return business_risk_custom_order_list;
	}
	public void setBusiness_risk_custom_order_list(BusinessRiskCustomOrderList business_risk_custom_order_list) {
		this.business_risk_custom_order_list = business_risk_custom_order_list;
	}
	public RiskCategory getImpactCategory() {
		return impactCategory;
	}
	public void setImpactCategory(RiskCategory impactCategory) {
		this.impactCategory = impactCategory;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
}
