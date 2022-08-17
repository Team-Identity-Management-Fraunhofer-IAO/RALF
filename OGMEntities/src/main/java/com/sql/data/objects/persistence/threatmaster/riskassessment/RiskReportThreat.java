package com.sql.data.objects.persistence.threatmaster.riskassessment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(RiskReportThreatPK.class)
@Table(name="risk_report_threat")
public class RiskReportThreat {
	private int threat_id;
	private int risk_report_id;
	private String threat_name;
	private String threat_description;
	private int success_probability_order;
	private String success_probability_name;
	
	public RiskReportThreat() {
		
	}
	
	@Id
	@Column(name="threat_id", nullable=false)
	public int getThreat_id() {
		return threat_id;
	}
	public void setThreat_id(int threat_id) {
		this.threat_id = threat_id;
	}
	
	@Id
	@Column(name="risk_report_id", nullable=false)
	public int getRisk_report_id() {
		return risk_report_id;
	}
	public void setRisk_report_id(int risk_report_id) {
		this.risk_report_id = risk_report_id;
	}
	
	@Column(name="threat_name", nullable=true)
	public String getThreat_name() {
		return threat_name;
	}
	public void setThreat_name(String threat_name) {
		this.threat_name = threat_name;
	}
	
	@Column(name="threat_description", nullable=true)
	public String getThreat_description() {
		return threat_description;
	}
	public void setThreat_description(String threat_description) {
		this.threat_description = threat_description;
	}
	
	@Column(name="success_probability_order", nullable=false)
	public int getSuccess_probability_order() {
		return success_probability_order;
	}
	public void setSuccess_probability_order(int success_probability_order) {
		this.success_probability_order = success_probability_order;
	}
	
	@Column(name="success_probability_name", nullable=true)
	public String getSuccess_probability_name() {
		return success_probability_name;
	}
	public void setSuccess_probability_name(String success_probability_name) {
		this.success_probability_name = success_probability_name;
	}
	
	
	

}
