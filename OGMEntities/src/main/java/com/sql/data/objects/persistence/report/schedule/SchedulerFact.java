package com.sql.data.objects.persistence.report.schedule;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="scheduler_facts")
public class SchedulerFact {
	private int scheduleId;
	private boolean explicit;
	private int months;
	private int weeks;
	private int days;
	private int hours;
	private String timeString;
	private String lastExecutedTimeString;
	private int swStackId;
	private String assessmentFocus;
	private String notification;
	
	public SchedulerFact() {
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduleID", nullable = false)
	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	@Column(name="explicit", nullable = false)
	public boolean isExplicit() {
		return explicit;
	}

	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}

	@Column(name="swStackID", nullable = false)
	public int getSwStackId() {
		return swStackId;
	}

	public void setSwStackId(int swStackId) {
		this.swStackId = swStackId;
	}

	@Column(name="months", nullable=false)
	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	@Column(name="weeks", nullable=false)
	public int getWeeks() {
		return weeks;
	}

	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}

	@Column(name="days", nullable=false)
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Column(name="hours", nullable=false)
	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	@Column(name="timeString", nullable=false)
	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	@Column(name="lastExecutedTimeString", nullable=false)
	public String getLastExecutedTimeString() {
		return lastExecutedTimeString;
	}

	public void setLastExecutedTimeString(String lastExecutedTimeString) {
		this.lastExecutedTimeString = lastExecutedTimeString;
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
	
	
	
}
