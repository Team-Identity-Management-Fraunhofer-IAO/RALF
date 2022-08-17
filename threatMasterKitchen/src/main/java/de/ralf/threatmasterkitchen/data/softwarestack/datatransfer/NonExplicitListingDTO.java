package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

public class NonExplicitListingDTO {
	private int cpeId;
	private String action;
	private String section;
	private Long appID;
	private int reportID;
	
	public NonExplicitListingDTO() {
		
	}


	public int getCpeId() {
		return cpeId;
	}

	public void setCpeId(int cpeId) {
		this.cpeId = cpeId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Long getAppID() {
		return appID;
	}

	public void setAppID(Long appID) {
		this.appID = appID;
	}

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

}
