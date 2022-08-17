package de.ralf.threatmasterkitchen.controller.datatransfer;

import java.util.List;

public class ServiceDefinitionDTO {
	private int service_id;
	private String service_name;
	private String service_description;
	private int organization_id;
	private List<Integer> service_risk_list;

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getService_description() {
		return service_description;
	}

	public void setService_description(String service_description) {
		this.service_description = service_description;
	}

	public int getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(int organization_id) {
		this.organization_id = organization_id;
	}

	public List<Integer> getService_risk_list() {
		return service_risk_list;
	}

	public void setService_risk_list(List<Integer> service_risk_list) {
		this.service_risk_list = service_risk_list;
	}
	
	
}

