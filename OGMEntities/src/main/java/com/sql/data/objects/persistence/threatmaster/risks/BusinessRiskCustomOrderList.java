package com.sql.data.objects.persistence.threatmaster.risks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(BusinessRiskCustomOrderListPK.class)
@Table(name="business_risk_custom_order_list")
public class BusinessRiskCustomOrderList {
	private int order_id;
	private int business_risk_id;
	private long business_risk_impact_min;
	private long business_risk_impact_max;
	private double weight;
	
	public BusinessRiskCustomOrderList() {
		
	}

	@Id
	@Column(name="order_id", nullable=false)
	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	@Id
	@Column(name="business_risk_id", nullable=false)
	public int getBusiness_risk_id() {
		return business_risk_id;
	}

	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}

	@Column(name="weight", nullable=true)	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Column(name="business_risk_impact_min", nullable=true)
	public long getBusiness_risk_impact_min() {
		return business_risk_impact_min;
	}

	public void setBusiness_risk_impact_min(long business_risk_impact_min) {
		this.business_risk_impact_min = business_risk_impact_min;
	}
	
	@Column(name="business_risk_impact_max", nullable=true)
	public long getBusiness_risk_impact_max() {
		return business_risk_impact_max;
	}

	public void setBusiness_risk_impact_max(long business_risk_impact_max) {
		this.business_risk_impact_max = business_risk_impact_max;
	}

}
