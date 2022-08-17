package com.sql.data.objects.persistence.threatmaster.assessment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="control_report_control")
public class ControlReportControl {
	private int control_report_control_id;
	private int control_id;
	private int control_report_id;
	private String appliesTo;
	private boolean allThreats;
	
	public ControlReportControl() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="control_report_control_id", nullable=false)
	public int getControl_report_control_id() {
		return control_report_control_id;
	}

	public void setControl_report_control_id(int control_report_control_id) {
		this.control_report_control_id = control_report_control_id;
	}

	@Column(name="control_id", nullable=false)
	public int getControl_id() {
		return control_id;
	}

	public void setControl_id(int control_id) {
		this.control_id = control_id;
	}

	@Column(name="control_report_id", nullable=false)
	public int getControl_report_id() {
		return control_report_id;
	}

	public void setControl_report_id(int control_report_id) {
		this.control_report_id = control_report_id;
	}

	@Column(name="appliesTo", nullable=false)
	public String getAppliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(String appliesTo) {
		this.appliesTo = appliesTo;
	}

	@Column(name="allThreats", nullable=false)
	public boolean isAllThreats() {
		return allThreats;
	}

	public void setAllThreats(boolean allThreats) {
		this.allThreats = allThreats;
	}
	
	

}
