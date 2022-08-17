package com.sql.data.objects.persistence.threatmaster.risks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(BusinessRiskPairwiseComparisonPK.class)
@Table(name="business_risk_pairwise_comparison")
public class BusinessRiskPairwiseComparison {
	private int service_id;
	private int business_risk_id1;
	private int business_risk_id2;
	private int comparison;
	
	public BusinessRiskPairwiseComparison() {
		
	}

	@Id
	@Column(name="service_id", nullable=false)
	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	@Id
	@Column(name="business_risk_id1", nullable=false)
	public int getBusiness_risk_id1() {
		return business_risk_id1;
	}

	public void setBusiness_risk_id1(int business_risk_id1) {
		this.business_risk_id1 = business_risk_id1;
	}

	@Id
	@Column(name="business_risk_id2", nullable=false)
	public int getBusiness_risk_id2() {
		return business_risk_id2;
	}

	public void setBusiness_risk_id2(int busienss_risk_id2) {
		this.business_risk_id2 = busienss_risk_id2;
	}

	@Column(name="comparison", nullable=false)
	public int getComparison() {
		return comparison;
	}

	public void setComparison(int comparison) {
		this.comparison = comparison;
	}
	
}
