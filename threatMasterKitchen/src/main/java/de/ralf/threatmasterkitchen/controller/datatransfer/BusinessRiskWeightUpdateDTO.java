package de.ralf.threatmasterkitchen.controller.datatransfer;

public class BusinessRiskWeightUpdateDTO {
	private int order_id;
	private int business_risk_id;
	private int weight;
	
	public BusinessRiskWeightUpdateDTO() {
		
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getBusiness_risk_id() {
		return business_risk_id;
	}

	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
