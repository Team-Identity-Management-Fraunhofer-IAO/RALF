package com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="s_intrusion_set")
public class IntrusionSet {
	private int intrusion_set_id;
	private Timestamp loadTimestamp;
	private String name;
	private String created;
	private String modified;
	private String description;
	private String x_mitre_version;
	
	public IntrusionSet() {
		
	}

	@Id
	@Column(name="intrusion_set_id", nullable=false)
	public int getIntrusion_set_id() {
		return intrusion_set_id;
	}

	public void setIntrusion_set_id(int intrusion_set_id) {
		this.intrusion_set_id = intrusion_set_id;
	}

	@Column(name="loadTimestamp", nullable=false)
	public Timestamp getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Timestamp loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}

	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="created", nullable=false)
	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	@Column(name="modified", nullable=false)
	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	@Column(name="description", nullable=false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="x_mitre_version", nullable=false)
	public String getX_mitre_version() {
		return x_mitre_version;
	}

	public void setX_mitre_version(String x_mitre_version) {
		this.x_mitre_version = x_mitre_version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + intrusion_set_id;
		result = prime * result + ((loadTimestamp == null) ? 0 : loadTimestamp.hashCode());
		result = prime * result + ((modified == null) ? 0 : modified.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((x_mitre_version == null) ? 0 : x_mitre_version.hashCode());
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
		IntrusionSet other = (IntrusionSet) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (intrusion_set_id != other.intrusion_set_id)
			return false;
		if (loadTimestamp == null) {
			if (other.loadTimestamp != null)
				return false;
		} else if (!loadTimestamp.equals(other.loadTimestamp))
			return false;
		if (modified == null) {
			if (other.modified != null)
				return false;
		} else if (!modified.equals(other.modified))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (x_mitre_version == null) {
			if (other.x_mitre_version != null)
				return false;
		} else if (!x_mitre_version.equals(other.x_mitre_version))
			return false;
		return true;
	}
	
	
}
