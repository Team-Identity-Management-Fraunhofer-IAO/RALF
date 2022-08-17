package de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects;

import java.util.List;

import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternSuccessProbability;

public class SuccessProbability {
	private List<AttackPatternSuccessProbability> probabilities;
	private String successProbability;
	private int successProbabilityOrder;
	private int c_success_probability_id;
	
	public List<AttackPatternSuccessProbability> getProbabilities() {
		return probabilities;
	}
	public void setProbabilities(List<AttackPatternSuccessProbability> probabilities) {
		this.probabilities = probabilities;
	}
	public String getSuccessProbability() {
		return successProbability;
	}
	public void setSuccessProbability(String successProbability) {
		this.successProbability = successProbability;
	}
	public int getSuccessProbabilityOrder() {
		return successProbabilityOrder;
	}
	public void setSuccessProbabilityOrder(int successProbabilityOrder) {
		this.successProbabilityOrder = successProbabilityOrder;
	}
	public int getC_success_probability_id() {
		return c_success_probability_id;
	}
	public void setC_success_probability_id(int c_success_probability_id) {
		this.c_success_probability_id = c_success_probability_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + c_success_probability_id;
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
		SuccessProbability other = (SuccessProbability) obj;
		if (c_success_probability_id != other.c_success_probability_id)
			return false;
		return true;
	}
	
	
	
	

}
