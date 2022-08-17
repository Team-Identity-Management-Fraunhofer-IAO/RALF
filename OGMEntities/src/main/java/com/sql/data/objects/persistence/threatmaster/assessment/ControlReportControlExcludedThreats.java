package com.sql.data.objects.persistence.threatmaster.assessment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(ControlReportControlExcludedThreatsPK.class)
@Table(name="control_report_control_excludedThreats")
public class ControlReportControlExcludedThreats {
	private int control_report_control_id;
	private int threat_id;
	
	public ControlReportControlExcludedThreats() {
		
	}

	@Id
	@Column(name="control_report_control_id", nullable=false)
	public int getControl_report_control_id() {
		return control_report_control_id;
	}

	public void setControl_report_control_id(int control_report_control_id) {
		this.control_report_control_id = control_report_control_id;
	}

	@Id
	@Column(name="threat_id", nullable=false)
	public int getThreat_id() {
		return threat_id;
	}

	public void setThreat_id(int threat_id) {
		this.threat_id = threat_id;
	}
	
	
}
