package com.sql.data.objects.persistence.vulnerabilites.references;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reference_tag")
public class ReferenceTag {
	private int tagID;
	private int refID;
	private String tag;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tagID")
	public int getTagID() {
		return tagID;
	}
	public void setTagID(int tagID) {
		this.tagID = tagID;
	}
	
	@Column(name="refID", nullable=false)
	public int getRefID() {
		return refID;
	}
	public void setRefID(int refID) {
		this.refID = refID;
	}
	
	@Column(name="tag", nullable=true)
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	

}
