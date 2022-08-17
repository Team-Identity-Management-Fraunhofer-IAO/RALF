package de.ralf.threatmasterkitchen.controller.datatransfer;

import java.util.List;

public class MitigationReportDTO {
	private String platforms;
	private String collections;
	private List<ControlReportDTO> controls;
	
	public MitigationReportDTO() {
		
	}

	public String getPlatforms() {
		return platforms;
	}

	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}

	public String getCollections() {
		return collections;
	}

	public void setCollections(String collections) {
		this.collections = collections;
	}

	public List<ControlReportDTO> getControls() {
		return controls;
	}

	public void setControls(List<ControlReportDTO> controls) {
		this.controls = controls;
	}
	
	
}
