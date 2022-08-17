package com.sql.data.objects.persistence.threatmaster.attackfactors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(AttackMotivatingFactorWeightsPK.class)
@Table(name="attack_motivating_factor_weights")
public class AttackMotivatingFactorWeights {
	private int attack_motivating_factor_questionnaire_id;
	private int factor_id;
	private int service_id;
	private int organization_id;
	private double weight;
	
	public AttackMotivatingFactorWeights() {
		
	}

	@Id
	@Column(name="attack_motivating_questionnaire_id", nullable=false)
	public int getAttack_motivating_factor_questionnaire_id() {
		return attack_motivating_factor_questionnaire_id;
	}

	public void setAttack_motivating_factor_questionnaire_id(int attack_motivating_factor_questionnaire_id) {
		this.attack_motivating_factor_questionnaire_id = attack_motivating_factor_questionnaire_id;
	}
	
	@Id
	@Column(name="factor_id", nullable=false)
	public int getFactor_id() {
		return factor_id;
	}

	public void setFactor_id(int factor_id) {
		this.factor_id = factor_id;
	}
	
	@Column(name="service_id", nullable=false)
	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	
	@Column(name="organization_id", nullable=false)
	public int getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(int organization_id) {
		this.organization_id = organization_id;
	}

	@Column(name="weight", nullable=false)
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
}
