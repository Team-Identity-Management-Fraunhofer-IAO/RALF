package com.sql.data.objects.persistence.threatmaster.riskassessment;

import java.io.Serializable;

public class RiskReportThreatControlPivotedThreatPK implements Serializable{
	private int threat_id;
	private int kill_chain_phase_order;
	private int risk_report_id;
	private int required_threat_id;
	private int required_kill_chain_phase_order;
	
	public RiskReportThreatControlPivotedThreatPK() {
		
	}

	public RiskReportThreatControlPivotedThreatPK(int threat_id, int kill_chain_phase_order, int risk_report_id,
			int required_threat_id, int required_kill_chain_phase_order) {
		super();
		this.threat_id = threat_id;
		this.kill_chain_phase_order = kill_chain_phase_order;
		this.risk_report_id = risk_report_id;
		this.required_threat_id = required_threat_id;
		this.required_kill_chain_phase_order = required_kill_chain_phase_order;
	}

	public int getThreat_id() {
		return threat_id;
	}

	public void setThreat_id(int threat_id) {
		this.threat_id = threat_id;
	}

	public int getKill_chain_phase_order() {
		return kill_chain_phase_order;
	}

	public void setKill_chain_phase_order(int kill_chain_phase_order) {
		this.kill_chain_phase_order = kill_chain_phase_order;
	}

	public int getRisk_report_id() {
		return risk_report_id;
	}

	public void setRisk_report_id(int risk_report_id) {
		this.risk_report_id = risk_report_id;
	}

	public int getRequired_threat_id() {
		return required_threat_id;
	}

	public void setRequired_threat_id(int required_threat_id) {
		this.required_threat_id = required_threat_id;
	}

	public int getRequired_kill_chain_phase_order() {
		return required_kill_chain_phase_order;
	}

	public void setRequired_kill_chain_phase_order(int required_kill_chain_phase_order) {
		this.required_kill_chain_phase_order = required_kill_chain_phase_order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + kill_chain_phase_order;
		result = prime * result + required_kill_chain_phase_order;
		result = prime * result + required_threat_id;
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
		RiskReportThreatControlPivotedThreatPK other = (RiskReportThreatControlPivotedThreatPK) obj;
		if (kill_chain_phase_order != other.kill_chain_phase_order)
			return false;
		if (required_kill_chain_phase_order != other.required_kill_chain_phase_order)
			return false;
		if (required_threat_id != other.required_threat_id)
			return false;
		if (risk_report_id != other.risk_report_id)
			return false;
		if (threat_id != other.threat_id)
			return false;
		return true;
	}
	
	

}
