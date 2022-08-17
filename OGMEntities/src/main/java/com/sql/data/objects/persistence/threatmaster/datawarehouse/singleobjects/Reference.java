package com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="h_reference")
public class Reference {
	private int reference_id;
	private Timestamp loadTimestamp;
	private String collection_id;
	private String source_name;
	private String external_id;
	private String url;
	private String description;
	
	public Reference() {
		
	}

	@Id
	@Column(name="reference_id", nullable=false)
	public int getReference_id() {
		return reference_id;
	}

	public void setReference_id(int reference_id) {
		this.reference_id = reference_id;
	}

	@Column(name="loadTimestamp", nullable=false)
	public Timestamp getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Timestamp loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}

	@Column(name="collection_id", nullable=false)
	public String getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(String collection_id) {
		this.collection_id = collection_id;
	}

	@Column(name="source_name", nullable=false)
	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	@Column(name="external_id", nullable=false)
	public String getExternal_id() {
		return external_id;
	}

	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}

	@Column(name="url", nullable=false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="description", nullable=false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
