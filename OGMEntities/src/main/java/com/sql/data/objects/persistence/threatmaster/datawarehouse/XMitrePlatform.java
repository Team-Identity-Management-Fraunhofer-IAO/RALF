package com.sql.data.objects.persistence.threatmaster.datawarehouse;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="h_x_mitre_platforms")
public class XMitrePlatform {
	private int x_mitre_platforms_id;
	private String collection_id;
	private Timestamp loadTimestamp;
	private String x_mitre_platforms;
	
	public XMitrePlatform() {
		
	}

	@Id
	@Column(name="x_mitre_platforms_id", nullable=false)
	public int getX_mitre_platforms_id() {
		return x_mitre_platforms_id;
	}

	public void setX_mitre_platforms_id(int x_mitre_platforms_id) {
		this.x_mitre_platforms_id = x_mitre_platforms_id;
	}

	@Column(name="collection_id", nullable=false)
	public String getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(String collection_id) {
		this.collection_id = collection_id;
	}

	@Column(name="loadTimestamp", nullable=false)
	public Timestamp getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Timestamp loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}

	@Column(name="x_mitre_platform", nullable=false)
	public String getX_mitre_platforms() {
		return x_mitre_platforms;
	}

	public void setX_mitre_platforms(String x_mitre_platforms) {
		this.x_mitre_platforms = x_mitre_platforms;
	}
	
	

}
