package com.sql.data.objects.persistence.threatmaster.services;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(ServiceBusinessRiskPK.class)
@Table(name="service_business_risk")
public class ServiceBusinessRisk {
	private int business_risk_id;
	private int service_id;
	
	public ServiceBusinessRisk() {
		
	}

	@Id
	@Column(name="business_risk_id", nullable=false)
	public int getBusiness_risk_id() {
		return business_risk_id;
	}

	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}

	@Id
	@Column(name="service_id", nullable=false)
	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	
	
}
