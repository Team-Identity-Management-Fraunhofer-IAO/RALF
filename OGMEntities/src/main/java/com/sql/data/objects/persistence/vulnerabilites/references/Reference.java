package com.sql.data.objects.persistence.vulnerabilites.references;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reference")
public class Reference {
	private int refID;
	private int cveYear;
	private int cveID;
	private String url;
	private String name;
	private String refsource;
	private String modifiedDate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="refID", nullable=false)
	public int getRefID() {
		return refID;
	}
	public void setRefID(int refID) {
		this.refID = refID;
	}
	
	@Column(name="cveYear", nullable=false)
	public int getCveYear() {
		return cveYear;
	}
	public void setCveYear(int cveYear) {
		this.cveYear = cveYear;
	}
	
	@Column(name="cveID", nullable=false)
	public int getCveID() {
		return cveID;
	}
	public void setCveID(int cveID) {
		this.cveID = cveID;
	}
	
	@Column(name="url", nullable=true)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="name", nullable=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="refsource", nullable=true)
	public String getRefsource() {
		return refsource;
	}
	public void setRefsource(String refsource) {
		this.refsource = refsource;
	}
	
	@Column(name="modifiedDate", nullable=false)
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	

}
