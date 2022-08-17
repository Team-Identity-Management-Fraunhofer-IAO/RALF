package de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects;

public class AssessmentControl {
	
	public static enum ControlApplication {appliesFully, appliesPartially};
	
	private ControlApplication appliesTo;
	private int control_id;
	
	public ControlApplication getAppliesTo() {
		return appliesTo;
	}
	public void setAppliesTo(ControlApplication appliesTo) {
		this.appliesTo = appliesTo;
	}
	public int getControl_id() {
		return control_id;
	}
	public void setControl_id(int control_id) {
		this.control_id = control_id;
	}
	
	
	

}
