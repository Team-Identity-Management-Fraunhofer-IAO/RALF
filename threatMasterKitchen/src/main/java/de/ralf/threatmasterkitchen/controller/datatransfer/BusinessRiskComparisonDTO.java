package de.ralf.threatmasterkitchen.controller.datatransfer;

public class BusinessRiskComparisonDTO {
	private String operator;
	private int compared_business_risk_id;
	private int comparison;
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getCompared_business_risk_id() {
		return compared_business_risk_id;
	}
	public void setCompared_business_risk_id(int compared_business_risk_id) {
		this.compared_business_risk_id = compared_business_risk_id;
	}
	public int getComparison() {
		return comparison;
	}
	public void setComparison(int comparison) {
		this.comparison = comparison;
	}
	
	
}
