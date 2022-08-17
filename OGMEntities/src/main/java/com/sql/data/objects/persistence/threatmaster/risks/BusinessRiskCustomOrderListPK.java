package com.sql.data.objects.persistence.threatmaster.risks;

import java.io.Serializable;

public class BusinessRiskCustomOrderListPK implements Serializable{
	protected int order_id;
	protected int business_risk_id;
	
	public BusinessRiskCustomOrderListPK() {
		
	}
	
	public BusinessRiskCustomOrderListPK(int order_id, int business_risk_id) {
		this.order_id = order_id;
		this.business_risk_id = business_risk_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
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
		result = prime * result + order_id;
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
		BusinessRiskCustomOrderListPK other = (BusinessRiskCustomOrderListPK) obj;
		if (business_risk_id != other.business_risk_id)
			return false;
		if (order_id != other.order_id)
			return false;
		return true;
	}
	
}
