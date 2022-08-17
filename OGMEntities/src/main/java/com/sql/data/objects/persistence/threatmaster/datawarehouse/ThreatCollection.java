package com.sql.data.objects.persistence.threatmaster.datawarehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="C_COLLECTION")
public class ThreatCollection {
	private String collection_id;
	private String collection_name;
	private String collection_question;
	
	public ThreatCollection() {
		
	}

	@Id
	@Column(name="collection_id",nullable=false)
	public String getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(String collection_id) {
		this.collection_id = collection_id;
	}

	@Column(name="collection_name",nullable=false)
	public String getCollection_name() {
		return collection_name;
	}

	public void setCollection_name(String collection_name) {
		this.collection_name = collection_name;
	}

	@Column(name="collection_question",nullable=false)
	public String getCollection_question() {
		return collection_question;
	}

	public void setCollection_question(String collection_question) {
		this.collection_question = collection_question;
	}
	
	

}
