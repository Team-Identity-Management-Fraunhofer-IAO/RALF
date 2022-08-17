package com.sql.data.objects.persistence.threatmaster.services;

import java.io.Serializable;

public class ServiceBusinessRiskPK implements Serializable{
	protected int business_risk_id;
	protected int service_id;
	
	public ServiceBusinessRiskPK() {
		
	}

	public int getBusiness_risk_id() {
		return business_risk_id;
	}

	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + business_risk_id;
		result = prime * result + service_id;
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
		ServiceBusinessRiskPK other = (ServiceBusinessRiskPK) obj;
		if (business_risk_id != other.business_risk_id)
			return false;
		if (service_id != other.service_id)
			return false;
		return true;
	}

}
