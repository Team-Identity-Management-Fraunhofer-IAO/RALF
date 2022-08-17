package com.sql.data.objects.helpers.attackpatterns;

public class NamedThreat {
	private int threat_id;
	private String threat_name;
	private String threat_description;
	
	public NamedThreat() {
		
	}

	public int getThreat_id() {
		return threat_id;
	}

	public void setThreat_id(int threat_id) {
		this.threat_id = threat_id;
	}

	public String getThreat_name() {
		return threat_name;
	}

	public void setThreat_name(String threat_name) {
		this.threat_name = threat_name;
	}

	public String getThreat_description() {
		return threat_description;
	}

	public void setThreat_description(String threat_description) {
		this.threat_description = threat_description;
	}

}
