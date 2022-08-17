package de.ralf.threatmasterkitchen.controller;


public abstract class AbstractController {
	private String applicationName = "RALF";
	

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
}
