package com.sql.data.objects.persistence.threatmaster.attackfactors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(AttackMotivatingQuestionnaireContentPK.class)
@Table(name="attack_motivating_questionnaire_content")
public class AttackMotivatingQuestionnaireContent {
	private int attack_motivating_questionnaire_id;
	private int factor_id1;
	private int factor_id2;
	private String comparison_operator;
	private int comparison;
	
	public AttackMotivatingQuestionnaireContent() {
		
	}

	@Id
	@Column(name="attack_motivating_questionnaire_id", nullable=false)
	public int getAttack_motivating_questionnaire_id() {
		return attack_motivating_questionnaire_id;
	}

	public void setAttack_motivating_questionnaire_id(int attack_motivating_questionnaire_id) {
		this.attack_motivating_questionnaire_id = attack_motivating_questionnaire_id;
	}

	@Id
	@Column(name="factor_id1", nullable=false)
	public int getFactor_id1() {
		return factor_id1;
	}

	public void setFactor_id1(int factor_id1) {
		this.factor_id1 = factor_id1;
	}

	@Id
	@Column(name="factor_id2", nullable=false)
	public int getFactor_id2() {
		return factor_id2;
	}

	public void setFactor_id2(int factor_id2) {
		this.factor_id2 = factor_id2;
	}

	@Column(name="comparison_operator", nullable=false)
	public String getComparison_operator() {
		return comparison_operator;
	}

	public void setComparison_operator(String comparison_operator) {
		this.comparison_operator = comparison_operator;
	}

	@Column(name="comparison", nullable=false)
	public int getComparison() {
		return comparison;
	}

	public void setComparison(int comparison) {
		this.comparison = comparison;
	}
	
}
