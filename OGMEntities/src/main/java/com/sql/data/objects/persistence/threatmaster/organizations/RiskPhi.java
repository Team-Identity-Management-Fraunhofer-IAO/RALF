package com.sql.data.objects.persistence.threatmaster.organizations;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="risk_phi")
public class RiskPhi {
	private int risk_phi_id;
	private int organization_id;
	private int service_id;
	private BigDecimal risk_phi;
	private Timestamp loadTimestamp;
	private String risk_phi_type;
	
	public RiskPhi() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="risk_phi_id", nullable=false)
	public int getRisk_phi_id() {
		return risk_phi_id;
	}

	public void setRisk_phi_id(int risk_phi_id) {
		this.risk_phi_id = risk_phi_id;
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

	@Column(name="risk_phi", nullable=false)
	public BigDecimal getRisk_phi() {
		return risk_phi;
	}

	public void setRisk_phi(BigDecimal risk_phi) {
		this.risk_phi = risk_phi;
	}

	@Column(name="loadTimestamp", nullable=false)
	public Timestamp getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Timestamp loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}

	@Column(name="risk_phi_type", nullable=false)
	public String getRisk_phi_type() {
		return risk_phi_type;
	}

	public void setRisk_phi_type(String risk_phi_type) {
		this.risk_phi_type = risk_phi_type;
	}
	
}
