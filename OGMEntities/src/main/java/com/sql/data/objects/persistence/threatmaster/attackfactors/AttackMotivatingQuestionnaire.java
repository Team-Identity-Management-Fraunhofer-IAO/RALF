package com.sql.data.objects.persistence.threatmaster.attackfactors;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="attack_motivating_questionnaire")
public class AttackMotivatingQuestionnaire {
	private int attack_motivating_questionnaire_id;
	private int organization_id;
	private int service_id;
	private Timestamp loadTimestamp;
	
	public AttackMotivatingQuestionnaire(){
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="attack_motivating_questionnaire_id", nullable=false)
	public int getAttack_motivating_questionnaire_id() {
		return attack_motivating_questionnaire_id;
	}

	public void setAttack_motivating_questionnaire_id(int attack_motivating_questionnaire_id) {
		this.attack_motivating_questionnaire_id = attack_motivating_questionnaire_id;
	}

	@Column(name="organization_id", nullable=false)
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

	@Column(name="loadTimestamp", nullable=false)
	public Timestamp getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Timestamp loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}
	
	

}
