package com.sql.data.objects.persistence.threatmaster.risks;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="attack_motivating_questionnaire_response")
public class AttackMotivatingQuestionnaireResponse {
	private int attack_motivating_questionnaire_response_id;
	private Timestamp loadTimestamp;
	private int organization_id;
	private int service_id;
	
	public AttackMotivatingQuestionnaireResponse() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="attack_motivating_questionnaire_response_id", nullable=false)
	public int getAttack_motivating_questionnaire_response_id() {
		return attack_motivating_questionnaire_response_id;
	}

	public void setAttack_motivating_questionnaire_response_id(int attack_motivating_questionnaire_response_id) {
		this.attack_motivating_questionnaire_response_id = attack_motivating_questionnaire_response_id;
	}

	@Column(name="loadTimestamp", nullable=false)
	public Timestamp getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Timestamp loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}

	@Column(name="organization_id",nullable=false)
	public int getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(int organization_id) {
		this.organization_id = organization_id;
	}

	@Column(name="service_id", nullable=false)
	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	
	

}
