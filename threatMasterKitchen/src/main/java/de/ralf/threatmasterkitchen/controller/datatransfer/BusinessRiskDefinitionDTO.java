package de.ralf.threatmasterkitchen.controller.datatransfer;

import java.util.List;

public class BusinessRiskDefinitionDTO {
	private int business_risk_id;
	private String business_risk_name;
	private String business_risk_description;
	private long business_risk_impact;
	private int business_risk_default_order;
	private boolean business_risk_general;
	private List<Integer> capability_killer_ids;
	
	
	
	public int getBusiness_risk_id() {
		return business_risk_id;
	}
	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}
	public String getBusiness_risk_name() {
		return business_risk_name;
	}
	public void setBusiness_risk_name(String business_risk_name) {
		this.business_risk_name = business_risk_name;
	}
	public String getBusiness_risk_description() {
		return business_risk_description;
	}
	public void setBusiness_risk_description(String business_risk_description) {
		this.business_risk_description = business_risk_description;
	}
	public long getBusiness_risk_impact() {
		return business_risk_impact;
	}
	public void setBusiness_risk_impact(long business_risk_impact) {
		this.business_risk_impact = business_risk_impact;
	}
	public int getBusiness_risk_default_order() {
		return business_risk_default_order;
	}
	public void setBusiness_risk_default_order(int business_risk_default_order) {
		this.business_risk_default_order = business_risk_default_order;
	}
	public boolean isBusiness_risk_general() {
		return business_risk_general;
	}
	public void setBusiness_risk_general(boolean business_risk_general) {
		this.business_risk_general = business_risk_general;
	}
	public List<Integer> getCapability_killer_ids() {
		return capability_killer_ids;
	}
	public void setCapability_killer_ids(List<Integer> capability_killer_ids) {
		this.capability_killer_ids = capability_killer_ids;
	}
	
	
	
}
