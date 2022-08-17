package com.sql.data.objects.persistence.threatmaster.risks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="business_risk")
public class BusinessRisk {
	private int business_risk_id;
	private String business_risk_name;
	private String business_risk_description;
	private long business_risk_impact;
	private int business_risk_default_order;
	private int service_id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="business_risk_id", nullable=false)
	public int getBusiness_risk_id() {
		return business_risk_id;
	}
	public void setBusiness_risk_id(int business_risk_id) {
		this.business_risk_id = business_risk_id;
	}
	
	@Column(name="business_risk_name", nullable=false)
	public String getBusiness_risk_name() {
		return business_risk_name;
	}
	public void setBusiness_risk_name(String business_risk_name) {
		this.business_risk_name = business_risk_name;
	}
	
	@Column(name="business_risk_description", nullable=false)
	public String getBusiness_risk_description() {
		return business_risk_description;
	}
	public void setBusiness_risk_description(String business_risk_description) {
		this.business_risk_description = business_risk_description;
	}
	
	@Column(name="business_risk_impact", nullable=true)
	public long getBusiness_risk_impact() {
		return business_risk_impact;
	}
	public void setBusiness_risk_impact(long business_risk_impact) {
		this.business_risk_impact = business_risk_impact;
	}
	
	@Column(name="business_risk_default_order", nullable=true)
	public int getBusiness_risk_default_order() {
		return business_risk_default_order;
	}
	public void setBusiness_risk_default_order(int business_risk_default_order) {
		this.business_risk_default_order = business_risk_default_order;
	}
	
	@Column(name="service_id", nullable=true)
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
		result = prime * result + business_risk_default_order;
		result = prime * result + ((business_risk_description == null) ? 0 : business_risk_description.hashCode());
		result = prime * result + business_risk_id;
		result = prime * result + (int) (business_risk_impact ^ (business_risk_impact >>> 32));
		result = prime * result + ((business_risk_name == null) ? 0 : business_risk_name.hashCode());
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
		BusinessRisk other = (BusinessRisk) obj;
		if (business_risk_id != other.business_risk_id)
			return false;
		return true;
	}
	
	
	
}
