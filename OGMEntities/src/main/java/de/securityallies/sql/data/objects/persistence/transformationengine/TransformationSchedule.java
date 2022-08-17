package de.securityallies.sql.data.objects.persistence.transformationengine;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transformation_schedule")
public class TransformationSchedule {
	
	private int transformation_order_id;
	private int hours;
	private int days;
	private int weeks;
	private int months;
	private ZonedDateTime nextExecution;
	
	public TransformationSchedule() {
		
	}

	@Id
	@Column(name="transformation_order_id", nullable=false)
	public int getTransformation_order_id() {
		return transformation_order_id;
	}

	public void setTransformation_order_id(int transformation_order_id) {
		this.transformation_order_id = transformation_order_id;
	}

	@Column(name="hours", nullable=false)
	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	@Column(name="days", nullable=false)
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Column(name="weeks", nullable=false)
	public int getWeeks() {
		return weeks;
	}

	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}

	@Column(name="months", nullable=false)
	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	@Column(name="nextExecution", nullable=false)
	public ZonedDateTime getNextExecution() {
		return nextExecution;
	}

	public void setNextExecution(ZonedDateTime nextExecution) {
		this.nextExecution = nextExecution;
	}

}
