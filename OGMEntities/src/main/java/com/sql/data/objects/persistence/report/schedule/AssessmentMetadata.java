package com.sql.data.objects.persistence.report.schedule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="assessment_metadata")
public class AssessmentMetadata {
	private int scheduleID;
	private String status;
	private int swStackID;
	
	public AssessmentMetadata() {
		
	}

	@Id
	@Column(name="scheduleID", nullable=false)
	public int getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}

	@Column(name="status", nullable=false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="swStackID", nullable=false)
	public int getSwStackID() {
		return swStackID;
	}

	public void setSwStackID(int swStackID) {
		this.swStackID = swStackID;
	}

}
