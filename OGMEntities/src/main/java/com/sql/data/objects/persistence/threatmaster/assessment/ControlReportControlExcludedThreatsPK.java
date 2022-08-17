package com.sql.data.objects.persistence.threatmaster.assessment;

import java.io.Serializable;

public class ControlReportControlExcludedThreatsPK implements Serializable{
	private int control_report_control_id;
	private int threat_id;
	
	public ControlReportControlExcludedThreatsPK() {
		
	}
	
	public ControlReportControlExcludedThreatsPK(int control_report_control_id, int threat_id) {
		this.control_report_control_id = control_report_control_id;
		this.threat_id = threat_id;
	}

	public int getControl_report_control_id() {
		return control_report_control_id;
	}

	public void setControl_report_control_id(int control_report_control_id) {
		this.control_report_control_id = control_report_control_id;
	}

	public int getThreat_id() {
		return threat_id;
	}

	public void setThreat_id(int threat_id) {
		this.threat_id = threat_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + control_report_control_id;
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
		ControlReportControlExcludedThreatsPK other = (ControlReportControlExcludedThreatsPK) obj;
		if (control_report_control_id != other.control_report_control_id)
			return false;
		if (threat_id != other.threat_id)
			return false;
		return true;
	}

	
}
