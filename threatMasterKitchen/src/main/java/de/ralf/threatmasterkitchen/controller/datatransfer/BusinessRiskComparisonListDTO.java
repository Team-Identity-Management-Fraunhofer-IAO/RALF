package de.ralf.threatmasterkitchen.controller.datatransfer;

import java.util.List;

public class BusinessRiskComparisonListDTO {
	private int business_risk_id;
	List<BusinessRiskComparisonDTO> compared_risks;
	public int getBusiness_risk_id() {
		return business_risk_id;
	}
	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}
	public List<BusinessRiskComparisonDTO> getCompared_risks() {
		return compared_risks;
	}
	public void setCompared_risks(List<BusinessRiskComparisonDTO> compared_risks) {
		this.compared_risks = compared_risks;
	}
	
	
}
