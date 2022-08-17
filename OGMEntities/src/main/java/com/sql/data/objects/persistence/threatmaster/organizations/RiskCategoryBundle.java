package com.sql.data.objects.persistence.threatmaster.organizations;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="risk_category_bundle")
public class RiskCategoryBundle {
	private int risk_category_bundle_id;
	private int organization_id;
	private int service_id;
	private Timestamp loadTimestmap;
	
	public RiskCategoryBundle() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="risk_category_bundle_id", nullable=false)
	public int getRisk_category_bundle_id() {
		return risk_category_bundle_id;
	}

	public void setRisk_category_bundle_id(int risk_category_bundle_id) {
		this.risk_category_bundle_id = risk_category_bundle_id;
	}

	@Column(name="organization_id", nullable=false)
	public int getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(int organization_id) {
		this.organization_id = organization_id;
	}

	@Column(name="service_id", nullable=true)
	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	@Column(name="loadTimestamp", nullable=false)
	public Timestamp getLoadTimestmap() {
		return loadTimestmap;
	}

	public void setLoadTimestmap(Timestamp loadTimestmap) {
		this.loadTimestmap = loadTimestmap;
	}

}
