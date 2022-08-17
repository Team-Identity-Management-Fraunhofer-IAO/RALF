package com.sql.data.objects.persistence.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nonExplicitCPEList")
public class NonExplicitCPEList {
	private int nonExplicitListing;
	private int reportID;
	private int cveYear;
	private int cveID;
	private int cpeID;
	private String URI;
	private int combinationID;
	private boolean partOfApplication;
	
	public NonExplicitCPEList() {
		this.partOfApplication = false;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="nonExplicitListing", nullable = false)
	public int getNonExplicitListing() {
		return nonExplicitListing;
	}
	
	public void setNonExplicitListing(int nonExplicitCPEListing) {
		this.nonExplicitListing = nonExplicitCPEListing;
	}
	
	@Column(name="reportID", nullable=false)
	public int getReportID() {
		return reportID;
	}
	
	public void setReportID(int reportID) {
		this.reportID = reportID;
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
	
	@Column(name="cpeID", nullable=false)
	public int getCpeID() {
		return cpeID;
	}
	
	public void setCpeID(int cpeID) {
		this.cpeID = cpeID;
	}

	@Column(name="URI", nullable=false)
	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	@Column(name="combinationID", nullable=false)
	public int getCombinationID() {
		return combinationID;
	}

	public void setCombinationID(int combinationID) {
		this.combinationID = combinationID;
	}

	@Column(name="partOfApplication", nullable=false)
	public boolean isPartOfApplication() {
		return partOfApplication;
	}

	public void setPartOfApplication(boolean partOfApplication) {
		this.partOfApplication = partOfApplication;
	}
	
}
