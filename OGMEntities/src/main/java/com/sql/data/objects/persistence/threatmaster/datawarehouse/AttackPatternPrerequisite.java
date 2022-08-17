package com.sql.data.objects.persistence.threatmaster.datawarehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="C_ATTACK_PATTERN_PREREQUISITE")
public class AttackPatternPrerequisite {
	private int c_attack_pattern_prerequisite_id;
	private int attack_pattern_id;
	private int required_attack_pattern_id;
	private String collection_id;
	private int intrusion_set_id;
	
	public AttackPatternPrerequisite() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="c_attack_pattern_prerequisite_id", nullable=false)
	public int getC_attack_pattern_prerequisite_id() {
		return c_attack_pattern_prerequisite_id;
	}

	public void setC_attack_pattern_prerequisite_id(int c_attack_pattern_prerequisite_id) {
		this.c_attack_pattern_prerequisite_id = c_attack_pattern_prerequisite_id;
	}

	@Column(name="attack_pattern_id", nullable=false)
	public int getAttack_pattern_id() {
		return attack_pattern_id;
	}

	public void setAttack_pattern_id(int attack_pattern_id) {
		this.attack_pattern_id = attack_pattern_id;
	}

	@Column(name="required_attack_pattern_id", nullable=false)
	public int getRequired_attack_pattern_id() {
		return required_attack_pattern_id;
	}

	public void setRequired_attack_pattern_id(int required_attack_pattern_id) {
		this.required_attack_pattern_id = required_attack_pattern_id;
	}

	@Column(name="collection_id", nullable=false)
	public String getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(String collection_id) {
		this.collection_id = collection_id;
	}

	@Column(name="intrusion_set_id", nullable=false)
	public int getIntrusion_set_id() {
		return intrusion_set_id;
	}

	public void setIntrusion_set_id(int intrusion_set_id) {
		this.intrusion_set_id = intrusion_set_id;
	}
	
	
}
