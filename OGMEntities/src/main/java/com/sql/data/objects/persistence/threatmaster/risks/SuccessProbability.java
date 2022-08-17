package com.sql.data.objects.persistence.threatmaster.risks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="C_SUCCESS_PROBABILITY")
public class SuccessProbability {
	private int c_success_probability_id;
	private int attack_pattern_id;
	private String success_probability_name;
	private int success_probability_order;
	
	public SuccessProbability() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="c_success_probability_id", nullable=false)
	public int getC_success_probability_id() {
		return c_success_probability_id;
	}

	public void setC_success_probability_id(int c_success_probability_id) {
		this.c_success_probability_id = c_success_probability_id;
	}

	@Column(name="attack_pattern_id", nullable=false)
	public int getAttack_pattern_id() {
		return attack_pattern_id;
	}

	public void setAttack_pattern_id(int attack_pattern_id) {
		this.attack_pattern_id = attack_pattern_id;
	}

	@Column(name="success_probability_name", nullable=false)
	public String getSuccess_probability_name() {
		return success_probability_name;
	}

	public void setSuccess_probability_name(String success_probability_name) {
		this.success_probability_name = success_probability_name;
	}

	@Column(name="success_probability_order", nullable=false)
	public int getSuccess_probability_order() {
		return success_probability_order;
	}

	public void setSuccess_probability_order(int success_probability_order) {
		this.success_probability_order = success_probability_order;
	}
	
	

}
