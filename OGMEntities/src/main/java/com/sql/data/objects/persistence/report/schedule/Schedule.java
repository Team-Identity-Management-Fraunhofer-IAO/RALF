package com.sql.data.objects.persistence.report.schedule;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schedule")
public class Schedule {
	private int scheduleId;
	private String executionTimeString;
	private boolean explicit;
	private int swStackID;
	private String assessmentFocus;
	private String notification;
	
	public Schedule() {
		
	}

	@Id
	@Column(name="scheduleID", nullable=false)
	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Column(name="explicit", nullable=false)
	public boolean isExplicit() {
		return explicit;
	}

	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}

	@Column(name="swStackID", nullable=false)
	public int getSwStackID() {
		return swStackID;
	}

	public void setSwStackID(int swStackID) {
		this.swStackID = swStackID;
	}

	@Column(name="executionTimeString", nullable=false)
	public String getExecutionTimeString() {
		return executionTimeString;
	}

	public void setExecutionTimeString(String executionTimeString) {
		this.executionTimeString = executionTimeString;
	}
	
	@Column(name="assessmentFocus", nullable=false)
	public String getAssessmentFocus() {
		return assessmentFocus;
	}

	public void setAssessmentFocus(String assessmentFocus) {
		this.assessmentFocus = assessmentFocus;
	}

	@Column(name="notification", nullable=false)
	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}		
		if (!(o instanceof Schedule)) {
			return false;
		}		
		return ((Schedule) o).getScheduleId() == this.getScheduleId();
	}

	
}
