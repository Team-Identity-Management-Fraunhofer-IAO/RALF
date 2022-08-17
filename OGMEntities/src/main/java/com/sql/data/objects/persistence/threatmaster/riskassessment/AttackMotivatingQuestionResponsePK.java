package com.sql.data.objects.persistence.threatmaster.riskassessment;

import java.io.Serializable;

public class AttackMotivatingQuestionResponsePK implements Serializable {
	private int attack_motivating_questionnaire_response_id;
	private int attack_motivating_factor_question_id;
	private int factor_id;
	
	public AttackMotivatingQuestionResponsePK() {
		
	}
	
	public AttackMotivatingQuestionResponsePK(int attack_motivating_questionnaire_response_id,
			int attack_motivating_factor_question_id, int factor_id) {
		super();
		this.attack_motivating_questionnaire_response_id = attack_motivating_questionnaire_response_id;
		this.attack_motivating_factor_question_id = attack_motivating_factor_question_id;
		this.factor_id = factor_id;
	}

	public int getAttack_motivating_questionnaire_response_id() {
		return attack_motivating_questionnaire_response_id;
	}

	public void setAttack_motivating_questionnaire_response_id(int attack_motivating_questionnaire_response_id) {
		this.attack_motivating_questionnaire_response_id = attack_motivating_questionnaire_response_id;
	}

	public int getAttack_motivating_factor_question_id() {
		return attack_motivating_factor_question_id;
	}

	public void setAttack_motivating_factor_question_id(int attack_motivating_factor_question_id) {
		this.attack_motivating_factor_question_id = attack_motivating_factor_question_id;
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
		result = prime * result + attack_motivating_factor_question_id;
		result = prime * result + attack_motivating_questionnaire_response_id;
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
		AttackMotivatingQuestionResponsePK other = (AttackMotivatingQuestionResponsePK) obj;
		if (attack_motivating_factor_question_id != other.attack_motivating_factor_question_id)
			return false;
		if (attack_motivating_questionnaire_response_id != other.attack_motivating_questionnaire_response_id)
			return false;
		if (factor_id != other.factor_id)
			return false;
		return true;
	}
	
}
