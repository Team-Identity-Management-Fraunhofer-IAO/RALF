package com.sql.data.objects.persistence.threatmaster.riskassessment;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="risk_report")
public class RiskReport {
	private int risk_report_id;
	private int service_id;
	private Timestamp assessmentTimestamp;
	private int assessed_success_probability_order;
	private Integer service_existence_probability_id;
	
	public RiskReport() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="risk_report_id", nullable=false)
	public int getRisk_report_id() {
		return risk_report_id;
	}

	public void setRisk_report_id(int risk_report_id) {
		this.risk_report_id = risk_report_id;
	}

	@Column(name="service_id", nullable=false)
	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	@Column(name="assessmentTimestamp", nullable=false)
	public Timestamp getAssessmentTimestamp() {
		return assessmentTimestamp;
	}

	public void setAssessmentTimestamp(Timestamp assessmentTimestamp) {
		this.assessmentTimestamp = assessmentTimestamp;
	}

	@Column(name="assessed_success_probability_order", nullable=false)
	public int getAssessed_success_probability_order() {
		return assessed_success_probability_order;
	}

	public void setAssessed_success_probability_order(int assessed_success_probability_order) {
		this.assessed_success_probability_order = assessed_success_probability_order;
	}

	@Column(name="service_existence_probability_id", nullable=false)
	public Integer getService_existence_probability_id() {
		return service_existence_probability_id;
	}

	public void setService_existence_probability_id(Integer service_existence_probability_id) {
		this.service_existence_probability_id = service_existence_probability_id;
	}
	
	
}
