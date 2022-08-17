package com.sql.data.objects.persistence.metadata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="metadata")
public class Metadata implements Serializable{
	Long metadataID;
	String dateString;
	String comment;
	
	public Metadata() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="metadataID", nullable=false)
	public Long getMetadataID() {
		return metadataID;
	}

	public void setMetadataID(Long metadataID) {
		this.metadataID = metadataID;
	}
	
	@Column(name="dateString", nullable=false)
	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	@Column(name="comment", nullable=false)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

}
