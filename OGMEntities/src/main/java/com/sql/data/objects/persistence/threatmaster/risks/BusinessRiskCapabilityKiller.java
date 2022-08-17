package com.sql.data.objects.persistence.threatmaster.risks;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(BusinessRiskCapabilityKillerPK.class)
@Table(name="business_risk_capability_killer")
public class BusinessRiskCapabilityKiller {
	private int business_risk_id;
	private int capability_killer_id;
	
	@Id
	@Column(name="business_risk_id", nullable=false)
	public int getBusiness_risk_id() {
		return business_risk_id;
	}
	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}
	
	@Id
	@Column(name="capability_killer_id", nullable=false)
	public int getCapability_killer_id() {
		return capability_killer_id;
	}
	public void setCapability_killer_id(int capability_killer_id) {
		this.capability_killer_id = capability_killer_id;
	}
	
	
	
}

