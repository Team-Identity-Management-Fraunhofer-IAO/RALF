package com.sql.data.objects.persistence.threatmaster.riskassessment;

import java.io.Serializable;

public class RiskReportThreatPK implements Serializable {
	private int threat_id;
	private int risk_report_id;
	
	public RiskReportThreatPK() {
		
	}

	public RiskReportThreatPK(int threat_id, int risk_report_id) {
		super();
		this.threat_id = threat_id;
		this.risk_report_id = risk_report_id;
	}

	public int getThreat_id() {
		return threat_id;
	}

	public void setThreat_id(int threat_id) {
		this.threat_id = threat_id;
	}

	public int getRisk_report_id() {
		return risk_report_id;
	}

	public void setRisk_report_id(int risk_report_id) {
		this.risk_report_id = risk_report_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		RiskReportThreatPK other = (RiskReportThreatPK) obj;
		if (risk_report_id != other.risk_report_id)
			return false;
		if (threat_id != other.threat_id)
			return false;
		return true;
	}
	
	
	

}
