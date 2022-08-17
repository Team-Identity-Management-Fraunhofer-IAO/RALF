package com.sql.data.objects.persistence.threatmaster.attackfactors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="c_attack_motivating_factor")
public class AttackMotivatingFactor {
	private int attack_motivating_factor_id;
	private String attack_motivating_factor_name;
	private String attack_motivating_factor_description;
	
	public AttackMotivatingFactor() {
		
	}

	@Id
	@Column(name="attack_motivating_factor_id", nullable=false)
	public int getAttack_motivating_factor_id() {
		return attack_motivating_factor_id;
	}

	public void setAttack_motivating_factor_id(int attack_motivating_factor_id) {
		this.attack_motivating_factor_id = attack_motivating_factor_id;
	}

	@Column(name="attack_motivating_factor_name", nullable=false)
	public String getAttack_motivating_factor_name() {
		return attack_motivating_factor_name;
	}

	public void setAttack_motivating_factor_name(String attack_motivating_factor_name) {
		this.attack_motivating_factor_name = attack_motivating_factor_name;
	}

	@Column(name="attack_motivating_factor_description", nullable = true)
	public String getAttack_motivating_factor_description() {
		return attack_motivating_factor_description;
	}

	public void setAttack_motivating_factor_description(String attack_motivating_factor_description) {
		this.attack_motivating_factor_description = attack_motivating_factor_description;
	}
	
	
}
