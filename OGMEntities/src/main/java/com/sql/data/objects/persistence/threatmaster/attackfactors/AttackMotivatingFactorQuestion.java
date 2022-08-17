package com.sql.data.objects.persistence.threatmaster.attackfactors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="c_attack_motivating_factor_question")
public class AttackMotivatingFactorQuestion {
	private int attack_motivating_factor_question_id;
	private int attack_motivating_factor_id;
	private String attack_motivating_factor_question;
	
	public AttackMotivatingFactorQuestion() {
		
	}

	@Id
	@Column(name="attack_motivating_factor_question_id", nullable=false)
	public int getAttack_motivating_factor_question_id() {
		return attack_motivating_factor_question_id;
	}

	public void setAttack_motivating_factor_question_id(int attack_motivating_factor_question_id) {
		this.attack_motivating_factor_question_id = attack_motivating_factor_question_id;
	}

	@Column(name="attack_motivating_factor_id", nullable=false)
	public int getAttack_motivating_factor_id() {
		return attack_motivating_factor_id;
	}

	public void setAttack_motivating_factor_id(int attack_motivating_factor_id) {
		this.attack_motivating_factor_id = attack_motivating_factor_id;
	}

	@Column(name="attack_motivating_factor_question", nullable=false)
	public String getAttack_motivating_factor_question() {
		return attack_motivating_factor_question;
	}

	public void setAttack_motivating_factor_question(String attack_motivating_factor_question) {
		this.attack_motivating_factor_question = attack_motivating_factor_question;
	}
	
	

}
