package com.sql.data.objects.persistence.threatmaster.datawarehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_ATTACK_PATTERN_SUCCESS_PROBABILITY")
public class AttackPatternSuccessProbability {
	private String id;
	private String attack_pattern_collection_id;
	private Integer course_of_action_id;
	private Integer attack_pattern_id;
	private Integer c_success_probability_id;
	private Integer c_vulnerability_enabling_factor_id;
	private String attack_pattern_name;
	private String attack_pattern_description;
	private String success_probability_name;
	private Integer success_probability_order;
	private String course_of_action_name;
	private String course_of_action_description;
	private String vulnerability_enabling_factor_name;
	private String vulnerability_enabling_factor_description;
	
	public AttackPatternSuccessProbability() {
		
	}

	@Id
	@Column(name="id", nullable=false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="c_success_probability_id", nullable=true)
	public Integer getC_success_probability_id() {
		return c_success_probability_id;
	}

	public void setC_success_probability_id(Integer c_success_probability_id) {
		this.c_success_probability_id = c_success_probability_id;
	}

	@Column(name="c_vulnerability_enabling_factor_id", nullable=true)
	public Integer getC_vulnerability_enabling_factor_id() {
		return c_vulnerability_enabling_factor_id;
	}

	public void setC_vulnerability_enabling_factor_id(Integer c_vulnerability_enabling_factor_id) {
		this.c_vulnerability_enabling_factor_id = c_vulnerability_enabling_factor_id;
	}

	@Column(name="attack_pattern_collection_id", nullable=true)
	public String getAttack_pattern_collection_id() {
		return attack_pattern_collection_id;
	}

	public void setAttack_pattern_collection_id(String attack_pattern_collection_id) {
		this.attack_pattern_collection_id = attack_pattern_collection_id;
	}

	public void setCourse_of_action_id(Integer course_of_action_id) {
		this.course_of_action_id = course_of_action_id;
	}
	
	@Column(name="course_of_action_id", nullable=true)
	public Integer getCourse_of_action_id() {
		return course_of_action_id;
	}

	@Column(name="attack_pattern_id", nullable=true)
	public Integer getAttack_pattern_id() {
		return attack_pattern_id;
	}

	public void setAttack_pattern_id(Integer attack_pattern_id) {
		this.attack_pattern_id = attack_pattern_id;
	}

	@Column(name="attack_pattern_name", nullable=true)
	public String getAttack_pattern_name() {
		return attack_pattern_name;
	}

	public void setAttack_pattern_name(String attack_pattern_name) {
		this.attack_pattern_name = attack_pattern_name;
	}

	@Column(name="attack_pattern_description", nullable=true)
	public String getAttack_pattern_description() {
		return attack_pattern_description;
	}

	public void setAttack_pattern_description(String attack_pattern_description) {
		this.attack_pattern_description = attack_pattern_description;
	}

	@Column(name="success_probability_name", nullable=true)
	public String getSuccess_probability_name() {
		return success_probability_name;
	}

	public void setSuccess_probability_name(String success_probability_name) {
		this.success_probability_name = success_probability_name;
	}

	@Column(name="success_probability_order", nullable=true)
	public Integer getSuccess_probability_order() {
		return success_probability_order;
	}

	public void setSuccess_probability_order(Integer success_probability_order) {
		this.success_probability_order = success_probability_order;
	}

	@Column(name="course_of_action_name", nullable=true)
	public String getCourse_of_action_name() {
		return course_of_action_name;
	}

	public void setCourse_of_action_name(String course_of_action_name) {
		this.course_of_action_name = course_of_action_name;
	}

	@Column(name="course_of_action_description", nullable=true)
	public String getCourse_of_action_description() {
		return course_of_action_description;
	}

	public void setCourse_of_action_description(String course_of_action_description) {
		this.course_of_action_description = course_of_action_description;
	}

	@Column(name="vulnerability_enabling_factor_name", nullable=true)
	public String getVulnerability_enabling_factor_name() {
		return vulnerability_enabling_factor_name;
	}

	public void setVulnerability_enabling_factor_name(String vulnerability_enabling_factor_name) {
		this.vulnerability_enabling_factor_name = vulnerability_enabling_factor_name;
	}

	@Column(name="vulnerability_enabling_factor_description", nullable=true)
	public String getVulnerability_enabling_factor_description() {
		return vulnerability_enabling_factor_description;
	}

	public void setVulnerability_enabling_factor_description(String vulnerability_enabling_factor_description) {
		this.vulnerability_enabling_factor_description = vulnerability_enabling_factor_description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attack_pattern_id == null) ? 0 : attack_pattern_id.hashCode());
		result = prime * result + ((c_success_probability_id == null) ? 0 : c_success_probability_id.hashCode());
		result = prime * result
				+ ((c_vulnerability_enabling_factor_id == null) ? 0 : c_vulnerability_enabling_factor_id.hashCode());
		result = prime * result + ((course_of_action_id == null) ? 0 : course_of_action_id.hashCode());
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
		AttackPatternSuccessProbability other = (AttackPatternSuccessProbability) obj;
		if (attack_pattern_id == null) {
			if (other.attack_pattern_id != null)
				return false;
		} else if (!attack_pattern_id.equals(other.attack_pattern_id))
			return false;
		if (c_success_probability_id == null) {
			if (other.c_success_probability_id != null)
				return false;
		} else if (!c_success_probability_id.equals(other.c_success_probability_id))
			return false;
		if (c_vulnerability_enabling_factor_id == null) {
			if (other.c_vulnerability_enabling_factor_id != null)
				return false;
		} else if (!c_vulnerability_enabling_factor_id.equals(other.c_vulnerability_enabling_factor_id))
			return false;
		if (course_of_action_id == null) {
			if (other.course_of_action_id != null)
				return false;
		} else if (!course_of_action_id.equals(other.course_of_action_id))
			return false;
		return true;
	}

	
	
	

}
