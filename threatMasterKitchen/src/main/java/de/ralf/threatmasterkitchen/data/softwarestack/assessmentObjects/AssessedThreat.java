package de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects;

import java.util.List;
import java.util.Map;

import com.sql.data.objects.helpers.attackpatterns.OrderedKillChainPhase;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternSuccessProbability;

public class AssessedThreat {
	private int threat_id;
	private String threat_name;
	private String threat_description;
	private int original_success_probability_order;
	private int success_probability_order;
	private List<AssessedThreat> successors;
	private String kill_chain_phase_name;
	private int kill_chain_phase_order;
	private Map<Integer, List<AttackPatternSuccessProbability>> alternativeProbabilities;
	private List<OrderedKillChainPhase> kill_chain_phases;
	
	public AssessedThreat() {
		
	}

	public int getThreat_id() {
		return threat_id;
	}

	public void setThreat_id(int threat_id) {
		this.threat_id = threat_id;
	}

	public String getThreat_name() {
		return threat_name;
	}

	public void setThreat_name(String threat_name) {
		this.threat_name = threat_name;
	}

	public String getThreat_description() {
		return threat_description;
	}

	public void setThreat_description(String threat_description) {
		this.threat_description = threat_description;
	}

	public int getOriginal_success_probability_order() {
		return original_success_probability_order;
	}

	public void setOriginal_success_probability_order(int original_success_probability_order) {
		this.original_success_probability_order = original_success_probability_order;
	}

	public int getSuccess_probability_order() {
		return success_probability_order;
	}

	public void setSuccess_probability_order(int success_probability_order) {
		this.success_probability_order = success_probability_order;
	}

	public List<AssessedThreat> getSuccessors() {
		return successors;
	}

	public void setSuccessors(List<AssessedThreat> successors) {
		this.successors = successors;
	}

	public String getKill_chain_phase_name() {
		return kill_chain_phase_name;
	}

	public void setKill_chain_phase_name(String kill_chain_phase_name) {
		this.kill_chain_phase_name = kill_chain_phase_name;
	}

	public int getKill_chain_phase_order() {
		return kill_chain_phase_order;
	}

	public void setKill_chain_phase_order(int kill_chain_phase_order) {
		this.kill_chain_phase_order = kill_chain_phase_order;
	}

	public Map<Integer, List<AttackPatternSuccessProbability>> getAlternativeProbabilities() {
		return alternativeProbabilities;
	}

	public void setAlternativeProbabilities(Map<Integer, List<AttackPatternSuccessProbability>> alternativeProbabilities) {
		this.alternativeProbabilities = alternativeProbabilities;
	}

	public List<OrderedKillChainPhase> getKill_chain_phases() {
		return kill_chain_phases;
	}

	public void setKill_chain_phases(List<OrderedKillChainPhase> kill_chain_phases) {
		this.kill_chain_phases = kill_chain_phases;
	}
	
	
	
}
