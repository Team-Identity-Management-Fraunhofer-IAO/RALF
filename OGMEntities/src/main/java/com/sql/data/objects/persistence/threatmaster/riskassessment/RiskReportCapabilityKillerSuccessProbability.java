package com.sql.data.objects.persistence.threatmaster.riskassessment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="risk_report_capability_killer_success_probability")
public class RiskReportCapabilityKillerSuccessProbability {
	private int risk_report_capability_killer_success_probability_id;
	private int risk_report_id;
	private int capability_killer_id;
	private int business_risk_id;
	private int success_probability_order;
	
	public RiskReportCapabilityKillerSuccessProbability() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="risk_report_capability_killer_success_probability_id", nullable=false)
	public int getRisk_report_capability_killer_success_probability_id() {
		return risk_report_capability_killer_success_probability_id;
	}

	public void setRisk_report_capability_killer_success_probability_id(
			int risk_report_capability_killer_success_probability_id) {
		this.risk_report_capability_killer_success_probability_id = risk_report_capability_killer_success_probability_id;
	}
	
	@Column(name="risk_report_id", nullable=false)
	public int getRisk_report_id() {
		return risk_report_id;
	}

	public void setRisk_report_id(int risk_report_id) {
		this.risk_report_id = risk_report_id;
	}

	@Column(name="capability_killer_id", nullable=false)
	public int getCapability_killer_id() {
		return capability_killer_id;
	}

	public void setCapability_killer_id(int capability_killer_id) {
		this.capability_killer_id = capability_killer_id;
	}

	@Column(name="business_risk_id", nullable=false)
	public int getBusiness_risk_id() {
		return business_risk_id;
	}

	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}

	@Column(name="success_probability_order", nullable=false)
	public int getSuccess_probability_order() {
		return success_probability_order;
	}

	public void setSuccess_probability_order(int success_probability_order) {
		this.success_probability_order = success_probability_order;
	}

}
