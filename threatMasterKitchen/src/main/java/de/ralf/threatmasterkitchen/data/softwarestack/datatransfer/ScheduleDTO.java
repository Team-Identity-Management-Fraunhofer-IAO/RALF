package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

public class ScheduleDTO {
	/*
	 * var schedule = {
				'firstDay':firstDay,
				'hours':rec_h,
				'days':rec_d,
				'weeks':rec_w,
				'months':rec_m,
				'matchingFocus':assessmentMatchingFocus,
				'notification':assessmentNotification,
				'assessmentFocus':assessmentFocus
		};
	 */
	private int scheduleId;
	private int appID;
	private String firstDay;
	private int hours;
	private int days;
	private int weeks;
	private int months;
	private String matchingFocus;
	private String notification;
	private String assessmentFocus;
	
	
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public int getAppID() {
		return appID;
	}
	public void setAppID(int appID) {
		this.appID = appID;
	}
	public String getFirstDay() {
		return firstDay;
	}
	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getWeeks() {
		return weeks;
	}
	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public String getMatchingFocus() {
		return matchingFocus;
	}
	public void setMatchingFocus(String matchingFocus) {
		this.matchingFocus = matchingFocus;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getAssessmentFocus() {
		return assessmentFocus;
	}
	public void setAssessmentFocus(String assessmentFocus) {
		this.assessmentFocus = assessmentFocus;
	}

}
