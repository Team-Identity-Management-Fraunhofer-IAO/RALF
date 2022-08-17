package com.sql.data.objects.persistence.threatmaster.riskassessment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="attack_motivating_question_response")
public class AttackMotivatingQuestionResponse {
	private int attack_motivating_question_response_id;
	private int attack_motivating_questionnaire_response_id;
	private int attack_motivating_factor_question_id;
	private int factor_id;
	private boolean response;
	private String justification;
	
	public AttackMotivatingQuestionResponse() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="attack_motivating_question_response_id", nullable=false)
	public int getAttack_motivating_question_response_id() {
		return attack_motivating_question_response_id;
	}

	public void setAttack_motivating_question_response_id(int attack_motivating_question_response_id) {
		this.attack_motivating_question_response_id = attack_motivating_question_response_id;
	}

	@Column(name="attack_motivating_questionnaire_response_id", nullable=false)
	public int getAttack_motivating_questionnaire_response_id() {
		return attack_motivating_questionnaire_response_id;
	}

	public void setAttack_motivating_questionnaire_response_id(int attack_motivating_questionnaire_response_id) {
		this.attack_motivating_questionnaire_response_id = attack_motivating_questionnaire_response_id;
	}

	@Column(name="attack_motivating_factor_question_id", nullable=false)
	public int getAttack_motivating_factor_question_id() {
		return attack_motivating_factor_question_id;
	}

	public void setAttack_motivating_factor_question_id(int attack_motivating_factor_question_id) {
		this.attack_motivating_factor_question_id = attack_motivating_factor_question_id;
	}

	@Column(name="factor_id", nullable=false)
	public int getFactor_id() {
		return factor_id;
	}

	public void setFactor_id(int factor_id) {
		this.factor_id = factor_id;
	}
	
	@Column(name="response", nullable=false)
	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	@Column(name="justification", nullable=true)
	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}
	
	
}
