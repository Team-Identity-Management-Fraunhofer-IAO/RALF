package com.sql.data.objects.persistence.threatmaster.attackfactors;

import java.io.Serializable;

public class AttackMotivatingQuestionnaireContentPK implements Serializable{
	private int attack_motivating_questionnaire_id;
	private int factor_id1;
	private int factor_id2;
	
	public AttackMotivatingQuestionnaireContentPK(int attack_motivating_questionnaire_id, int factor_id1,
			int factor_id2) {
		super();
		this.attack_motivating_questionnaire_id = attack_motivating_questionnaire_id;
		this.factor_id1 = factor_id1;
		this.factor_id2 = factor_id2;
	}
	
	public AttackMotivatingQuestionnaireContentPK() {
		
	}

	public int getAttack_motivating_questionnaire_id() {
		return attack_motivating_questionnaire_id;
	}

	public void setAttack_motivating_questionnaire_id(int attack_motivating_questionnaire_id) {
		this.attack_motivating_questionnaire_id = attack_motivating_questionnaire_id;
	}

	public int getFactor_id1() {
		return factor_id1;
	}

	public void setFactor_id1(int factor_id1) {
		this.factor_id1 = factor_id1;
	}

	public int getFactor_id2() {
		return factor_id2;
	}

	public void setFactor_id2(int factor_id2) {
		this.factor_id2 = factor_id2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + attack_motivating_questionnaire_id;
		result = prime * result + factor_id1;
		result = prime * result + factor_id2;
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
		AttackMotivatingQuestionnaireContentPK other = (AttackMotivatingQuestionnaireContentPK) obj;
		if (attack_motivating_questionnaire_id != other.attack_motivating_questionnaire_id)
			return false;
		if (factor_id1 != other.factor_id1)
			return false;
		if (factor_id2 != other.factor_id2)
			return false;
		return true;
	}
		
}
