package com.sql.data.objects.helpers.attackpatterns;

public class NamedControl {
	private int control_id;
	private String control_name;
	private String control_description;
	
	public NamedControl() {
		
	}

	public int getControl_id() {
		return control_id;
	}

	public void setControl_id(int control_id) {
		this.control_id = control_id;
	}

	public String getControl_name() {
		return control_name;
	}

	public void setControl_name(String control_name) {
		this.control_name = control_name;
	}

	public String getControl_description() {
		return control_description;
	}

	public void setControl_description(String control_description) {
		this.control_description = control_description;
	}

}
