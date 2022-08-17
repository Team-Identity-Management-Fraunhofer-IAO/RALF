package com.sql.data.objects.persistence.threatmaster.organizations;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="risk_category")
public class RiskCategory {
	private int risk_category_id;
	private int risk_category_bundle_id;
	private int risk_value_min;
	private int risk_value_max;
	private String category_name;
	private String risk_category_type;
	
	public RiskCategory() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="risk_category_id", nullable=false)
	public int getRisk_category_id() {
		return risk_category_id;
	}

	public void setRisk_category_id(int risk_category_id) {
		this.risk_category_id = risk_category_id;
	}

	@Column(name="risk_category_bundle_id", nullable=false)
	public int getRisk_category_bundle_id() {
		return risk_category_bundle_id;
	}

	public void setRisk_category_bundle_id(int risk_category_bundle_id) {
		this.risk_category_bundle_id = risk_category_bundle_id;
	}

	@Column(name="risk_value_min", nullable=false)
	public int getRisk_value_min() {
		return risk_value_min;
	}

	public void setRisk_value_min(int risk_value_min) {
		this.risk_value_min = risk_value_min;
	}

	@Column(name="risk_value_max", nullable=false)
	public int getRisk_value_max() {
		return risk_value_max;
	}

	public void setRisk_value_max(int risk_value_max) {
		this.risk_value_max = risk_value_max;
	}

	@Column(name="category_name", nullable=false)
	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	@Column(name="risk_category_type", nullable=false)
	public String getRisk_category_type() {
		return risk_category_type;
	}

	public void setRisk_category_type(String risk_category_type) {
		this.risk_category_type = risk_category_type;
	}
	
	

}
