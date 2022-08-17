package com.sql.data.objects.persistence.threatmaster.assessment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="control_report_platforms")
public class ControlReportPlatform {
	private int control_report_platforms_id;
	private int control_report_id;
	private int platform_id;
	private String platform;
	
	public ControlReportPlatform() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="control_report_platforms_id", nullable=false)
	public int getControl_report_platforms_id() {
		return control_report_platforms_id;
	}

	public void setControl_report_platforms_id(int control_report_platforms_id) {
		this.control_report_platforms_id = control_report_platforms_id;
	}

	@Column(name="control_report_id", nullable=false)
	public int getControl_report_id() {
		return control_report_id;
	}

	public void setControl_report_id(int control_report_id) {
		this.control_report_id = control_report_id;
	}

	@Column(name="platform_id", nullable=false)
	public int getPlatform_id() {
		return platform_id;
	}

	public void setPlatform_id(int platform_id) {
		this.platform_id = platform_id;
	}

	@Column(name="platform", nullable=false)
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	

}
