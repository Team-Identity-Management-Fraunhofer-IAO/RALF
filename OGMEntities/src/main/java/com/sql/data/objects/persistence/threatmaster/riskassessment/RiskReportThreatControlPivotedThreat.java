package com.sql.data.objects.persistence.threatmaster.riskassessment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="risk_report_threat_control_pivoted_threat")
public class RiskReportThreatControlPivotedThreat {
	private int pivoted_threat_id;
	private int threat_id;
	private int kill_chain_phase_order;
	private String kill_chain_phase_name;
	private String kill_chain_name;
	private int risk_report_id;
	private int succeeding_threat_id;
	private int succeeding_kill_chain_phase_order;
	private String succeeding_kill_chain_phase_name;
	private String succeeding_kill_chain_name;
	private int success_probability_order;
	private String success_probability_name;
	
	public RiskReportThreatControlPivotedThreat() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pivoted_threat_id", nullable=false)
	public int getPivoted_threat_id() {
		return pivoted_threat_id;
	}
	public void setPivoted_threat_id(int pivoted_threat_id) {
		this.pivoted_threat_id = pivoted_threat_id;
	}

	@Column(name="threat_id", nullable=false)
	public int getThreat_id() {
		return threat_id;
	}
	public void setThreat_id(int threat_id) {
		this.threat_id = threat_id;
	}
	
	@Column(name="kill_chain_phase_order", nullable=false)
	public int getKill_chain_phase_order() {
		return kill_chain_phase_order;
	}
	public void setKill_chain_phase_order(int kill_chain_phase_order) {
		this.kill_chain_phase_order = kill_chain_phase_order;
	}
	
	@Column(name="kill_chain_phase_name", nullable=true)
	public String getKill_chain_phase_name() {
		return kill_chain_phase_name;
	}
	public void setKill_chain_phase_name(String kill_chain_phase_name) {
		this.kill_chain_phase_name = kill_chain_phase_name;
	}
	
	@Column(name="risk_report_id", nullable=false)
	public int getRisk_report_id() {
		return risk_report_id;
	}
	public void setRisk_report_id(int risk_report_id) {
		this.risk_report_id = risk_report_id;
	}
	
	@Column(name="succeeding_threat_id", nullable=false)
	public int getSucceeding_threat_id() {
		return succeeding_threat_id;
	}
	public void setSucceeding_threat_id(int succeeding_threat_id) {
		this.succeeding_threat_id = succeeding_threat_id;
	}
	
	@Column(name="succeeding_kill_chain_phase_order", nullable=false)
	public int getSucceeding_kill_chain_phase_order() {
		return succeeding_kill_chain_phase_order;
	}
	public void setSucceeding_kill_chain_phase_order(int succeeding_kill_chain_phase_order) {
		this.succeeding_kill_chain_phase_order = succeeding_kill_chain_phase_order;
	}
	
	@Column(name="succeeding_kill_chain_phase_name", nullable=true)
	public String getSucceeding_kill_chain_phase_name() {
		return succeeding_kill_chain_phase_name;
	}
	public void setSucceeding_kill_chain_phase_name(String succeeding_kill_chain_phase_name) {
		this.succeeding_kill_chain_phase_name = succeeding_kill_chain_phase_name;
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

	@Column(name="kill_chain_name", nullable=false)
	public String getKill_chain_name() {
		return kill_chain_name;
	}

	public void setKill_chain_name(String kill_chain_name) {
		this.kill_chain_name = kill_chain_name;
	}

	@Column(name="succeeding_kill_chain_name", nullable=false)
	public String getSucceeding_kill_chain_name() {
		return succeeding_kill_chain_name;
	}

	public void setSucceeding_kill_chain_name(String succeeding_kill_chain_name) {
		this.succeeding_kill_chain_name = succeeding_kill_chain_name;
	}
	
	
}
