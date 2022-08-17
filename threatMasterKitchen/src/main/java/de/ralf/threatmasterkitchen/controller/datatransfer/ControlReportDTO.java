package de.ralf.threatmasterkitchen.controller.datatransfer;

import java.util.ArrayList;
import java.util.List;

public class ControlReportDTO {
	int controlId;
	String appliesTo;
	boolean allThreats;
	List<Integer> excludedThreats;
	
	public ControlReportDTO() {

	}

	public int getControlId() {
		return controlId;
	}

	public void setControlId(int controlId) {
		this.controlId = controlId;
	}

	public String getAppliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(String appliesTo) {
		this.appliesTo = appliesTo;
	}

	public boolean isAllThreats() {
		return allThreats;
	}

	public void setAllThreats(boolean allThreats) {
		this.allThreats = allThreats;
	}

	public List<Integer> getExcludedThreats() {
		return excludedThreats;
	}

	public void setExcludedThreats(List<Integer> excludedThreats) {
		this.excludedThreats = excludedThreats;
	}
	
}
