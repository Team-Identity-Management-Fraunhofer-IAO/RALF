package com.sql.data.objects.persistence.threatmaster.services;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service_existence_probability")
public class ServiceExistenceProbability {
	private int service_existence_probability_id;
	private int service_id;
	private BigDecimal existence_probability;
	private BigDecimal attack_motivation_score;
	private BigDecimal attack_enabling_score;
	private Timestamp loadTimestamp;
	private int organization_id;
	
	public ServiceExistenceProbability() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="service_existence_probability_id", nullable=false)
	public int getService_existence_probability_id() {
		return service_existence_probability_id;
	}

	public void setService_existence_probability_id(int service_existence_probability_id) {
		this.service_existence_probability_id = service_existence_probability_id;
	}

	@Column(name="service_id", nullable=false)
	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	@Column(name="existence_probability", nullable=false)
	public BigDecimal getExistence_probability() {
		return existence_probability;
	}

	public void setExistence_probability(BigDecimal existence_probability) {
		this.existence_probability = existence_probability;
	}

	@Column(name="attack_motivation_score", nullable=false)
	public BigDecimal getAttack_motivation_score() {
		return attack_motivation_score;
	}

	public void setAttack_motivation_score(BigDecimal attack_motivation_score) {
		this.attack_motivation_score = attack_motivation_score;
	}

	@Column(name="attack_enabling_score", nullable=false)
	public BigDecimal getAttack_enabling_score() {
		return attack_enabling_score;
	}

	public void setAttack_enabling_score(BigDecimal attack_enabling_score) {
		this.attack_enabling_score = attack_enabling_score;
	}

	@Column(name="loadTimestamp", nullable=false)
	public Timestamp getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Timestamp loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}

	@Column(name="organization_id", nullable=false)
	public int getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(int organization_id) {
		this.organization_id = organization_id;
	}
	
	

}
