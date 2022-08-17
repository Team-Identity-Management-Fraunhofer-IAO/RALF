package com.sql.data.objects.persistence.threatmaster.risks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="capability_killer")
public class CapabilityKiller {
	private int capability_killer_id;
	private String capability_killer_name;
	
	public CapabilityKiller() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="capability_killer_id", nullable=false)
	public int getCapability_killer_id() {
		return capability_killer_id;
	}

	public void setCapability_killer_id(int capability_killer_id) {
		this.capability_killer_id = capability_killer_id;
	}

	@Column(name="capability_killer_name", nullable=false)
	public String getCapability_killer_name() {
		return capability_killer_name;
	}

	public void setCapability_killer_name(String capability_killer_name) {
		this.capability_killer_name = capability_killer_name;
	}

	
}
