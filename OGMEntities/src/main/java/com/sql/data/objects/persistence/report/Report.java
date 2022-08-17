package com.sql.data.objects.persistence.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Report")
public class Report {
	private int reportID;
	private String timeString;
	private boolean explicit;
	private Long swStackID;
	
	public Report() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reportID", nullable=false)
	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	@Column(name="timeString", nullable=false)
	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	@Column(name="explicit", nullable=false)
	public boolean isExplicit() {
		return explicit;
	}

	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}

	@Column(name="swStackID", nullable=false)
	public Long getSwStackID() {
		return swStackID;
	}

	public void setSwStackID(Long swStackID) {
		this.swStackID = swStackID;
	}
	
	
	
	
}
