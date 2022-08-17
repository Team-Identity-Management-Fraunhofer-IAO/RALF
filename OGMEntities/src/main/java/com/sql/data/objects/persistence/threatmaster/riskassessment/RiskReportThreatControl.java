package com.sql.data.objects.persistence.threatmaster.riskassessment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(RiskReportThreatControlPK.class)
@Table(name="risk_report_threat_control")
public class RiskReportThreatControl {
	private int control_id;
	private int threat_id;
	private int risk_report_id;
	private String control_name;
	private String control_description;
	
	public RiskReportThreatControl() {
		
	}

	@Id
	@Column(name="control_id", nullable=false)
	public int getControl_id() {
		return control_id;
	}

	public void setControl_id(int control_id) {
		this.control_id = control_id;
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
	
	@Column(name="control_name", nullable=true)
	public String getControl_name() {
		return control_name;
	}

	public void setControl_name(String control_name) {
		this.control_name = control_name;
	}

	@Column(name="control_description", nullable=true)
	public String getControl_description() {
		return control_description;
	}

	public void setControl_description(String control_description) {
		this.control_description = control_description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + control_id;
		result = prime * result + risk_report_id;
		result = prime * result + threat_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RiskReportThreatControl other = (RiskReportThreatControl) obj;
		if (control_id != other.control_id)
			return false;
		if (risk_report_id != other.risk_report_id)
			return false;
		if (threat_id != other.threat_id)
			return false;
		return true;
	}
	
	

}
