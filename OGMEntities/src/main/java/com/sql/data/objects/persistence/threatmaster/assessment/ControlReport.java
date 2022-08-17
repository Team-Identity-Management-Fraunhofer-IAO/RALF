package com.sql.data.objects.persistence.threatmaster.assessment;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="control_report")
public class ControlReport {
	private int control_report_id;
	private int service_id;
	private Timestamp report_timestamp;
	
	public ControlReport() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="control_report_id", nullable=false)
	public int getControl_report_id() {
		return control_report_id;
	}

	public void setControl_report_id(int control_report_id) {
		this.control_report_id = control_report_id;
	}

	@Column(name="service_id", nullable=false)
	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	@Column(name="report_timestamp", nullable=false)
	public Timestamp getReport_timestamp() {
		return report_timestamp;
	}

	public void setReport_timestamp(Timestamp report_timestamp) {
		this.report_timestamp = report_timestamp;
	}
	
	

}
