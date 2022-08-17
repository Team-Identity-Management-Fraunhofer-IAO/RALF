package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects;

public class TransformationOrderScheduleDTO {
	private int transformation_order_id;
	private int hours;
	private int days;
	private int weeks;
	private int months;
	
	public TransformationOrderScheduleDTO() {
		
	}
	
	public int getTransformation_order_id() {
		return transformation_order_id;
	}
	public void setTransformation_order_id(int transformation_order_id) {
		this.transformation_order_id = transformation_order_id;
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
	
	

}
