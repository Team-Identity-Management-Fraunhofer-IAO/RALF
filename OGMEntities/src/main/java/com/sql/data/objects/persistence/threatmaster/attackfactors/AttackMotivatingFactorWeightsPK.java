package com.sql.data.objects.persistence.threatmaster.attackfactors;

import java.io.Serializable;

public class AttackMotivatingFactorWeightsPK implements Serializable{
	private int attack_motivating_factor_questionnaire_id;
	private int factor_id;
	
	public AttackMotivatingFactorWeightsPK(int attack_motivating_factor_questionnaire_id, int factor_id) {
		super();
		this.attack_motivating_factor_questionnaire_id = attack_motivating_factor_questionnaire_id;
		this.factor_id = factor_id;
	}
	
	public AttackMotivatingFactorWeightsPK() {
		
	}

	public int getAttack_motivating_factor_questionnaire_id() {
		return attack_motivating_factor_questionnaire_id;
	}

	public void setAttack_motivating_factor_questionnaire_id(int attack_motivating_factor_questionnaire_id) {
		this.attack_motivating_factor_questionnaire_id = attack_motivating_factor_questionnaire_id;
	}

	public int getFactor_id() {
		return factor_id;
	}

	public void setFactor_id(int factor_id) {
		this.factor_id = factor_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + attack_motivating_factor_questionnaire_id;
		result = prime * result + factor_id;
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
		AttackMotivatingFactorWeightsPK other = (AttackMotivatingFactorWeightsPK) obj;
		if (attack_motivating_factor_questionnaire_id != other.attack_motivating_factor_questionnaire_id)
			return false;
		if (factor_id != other.factor_id)
			return false;
		return true;
	}
	
	

}
