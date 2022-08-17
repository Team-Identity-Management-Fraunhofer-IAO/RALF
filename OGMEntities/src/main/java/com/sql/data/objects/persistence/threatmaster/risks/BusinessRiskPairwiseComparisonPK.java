package com.sql.data.objects.persistence.threatmaster.risks;

import java.io.Serializable;

public class BusinessRiskPairwiseComparisonPK implements Serializable{
	protected int service_id;
	protected int business_risk_id1;
	protected int business_risk_id2;
	
	public BusinessRiskPairwiseComparisonPK() {
		super();
	}

	public BusinessRiskPairwiseComparisonPK(int service_id, int business_risk_id1, int business_risk_id2) {
		super();
		this.service_id = service_id;
		this.business_risk_id1 = business_risk_id1;
		this.business_risk_id2 = business_risk_id2;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public int getBusiness_risk_id1() {
		return business_risk_id1;
	}

	public void setBusiness_risk_id1(int business_risk_id1) {
		this.business_risk_id1 = business_risk_id1;
	}

	public int getBusiness_risk_id2() {
		return business_risk_id2;
	}

	public void setBusiness_risk_id2(int business_risk_id2) {
		this.business_risk_id2 = business_risk_id2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + business_risk_id1;
		result = prime * result + business_risk_id2;
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
		BusinessRiskPairwiseComparisonPK other = (BusinessRiskPairwiseComparisonPK) obj;
		if (business_risk_id1 != other.business_risk_id1)
			return false;
		if (business_risk_id2 != other.business_risk_id2)
			return false;
		if (service_id != other.service_id)
			return false;
		return true;
	}

}
