package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

import java.util.List;

public class ReportModificationDTO {
	public enum Modification {IRRELEVANT, TREATED}
	
	private String modification;
	private List<String> identifiers;
	private int reportID;
	private Long appID;
	private Long componentID;
	
	public String getModification() {
		return modification;
	}
	public void setModification(String modification) {
		this.modification = modification;
	}
	public List<String> getIdentifiers() {
		return identifiers;
	}
	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}
	public int getReportID() {
		return reportID;
	}
	public void setReportID(int reportID) {
		this.reportID = reportID;
	}
	public Long getAppID() {
		return appID;
	}
	public void setAppID(Long appID) {
		this.appID = appID;
	}
	public Long getComponentID() {
		return componentID;
	}
	public void setComponentID(Long componentID) {
		this.componentID = componentID;
	}
	
	
	
}
