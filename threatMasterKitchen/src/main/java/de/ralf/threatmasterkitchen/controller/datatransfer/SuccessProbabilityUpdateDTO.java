package de.ralf.threatmasterkitchen.controller.datatransfer;

import java.util.List;

public class SuccessProbabilityUpdateDTO {
	private String action;
	private int attack_pattern_id;
	private int c_success_probability_id;
	private List<Integer> controls;
	private List<Integer> factors;
	private int probability;
	
	public SuccessProbabilityUpdateDTO() {
		
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getAttack_pattern_id() {
		return attack_pattern_id;
	}

	public void setAttack_pattern_id(int attack_pattern_id) {
		this.attack_pattern_id = attack_pattern_id;
	}

	public List<Integer> getControls() {
		return controls;
	}

	public void setControls(List<Integer> controls) {
		this.controls = controls;
	}

	public List<Integer> getFactors() {
		return factors;
	}

	public void setFactors(List<Integer> factors) {
		this.factors = factors;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public int getC_success_probability_id() {
		return c_success_probability_id;
	}

	public void setC_success_probability_id(int c_success_probability_id) {
		this.c_success_probability_id = c_success_probability_id;
	}

}
