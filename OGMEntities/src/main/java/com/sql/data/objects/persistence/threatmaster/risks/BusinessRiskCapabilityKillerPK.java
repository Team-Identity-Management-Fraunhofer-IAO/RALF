package com.sql.data.objects.persistence.threatmaster.risks;

import java.io.Serializable;

public class BusinessRiskCapabilityKillerPK implements Serializable{
	protected int capability_killer_id;
	protected int business_risk_id;
	
	public BusinessRiskCapabilityKillerPK() {
		
	}
	
	public BusinessRiskCapabilityKillerPK(int capability_killer_id,int business_risk_id) {
		this.capability_killer_id = capability_killer_id;
		this.business_risk_id = business_risk_id;
	}

	public int getCapability_killer_id() {
		return capability_killer_id;
	}

	public void setCapability_killer_id(int capability_killer_id) {
		this.capability_killer_id = capability_killer_id;
	}

	public int getBusiness_risk_id() {
		return business_risk_id;
	}

	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + business_risk_id;
		result = prime * result + capability_killer_id;
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
		BusinessRiskCapabilityKillerPK other = (BusinessRiskCapabilityKillerPK) obj;
		if (business_risk_id != other.business_risk_id)
			return false;
		if (capability_killer_id != other.capability_killer_id)
			return false;
		return true;
	}
	
}
