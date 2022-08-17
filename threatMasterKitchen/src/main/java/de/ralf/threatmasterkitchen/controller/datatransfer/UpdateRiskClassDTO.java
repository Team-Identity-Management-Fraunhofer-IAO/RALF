package de.ralf.threatmasterkitchen.controller.datatransfer;

import java.util.List;

public class UpdateRiskClassDTO {
	private List<RiskClassDTO> riskClasses;
	private List<RiskClassDTO> impactClasses;
	private List<RiskClassDTO> probabilityClasses;
	
	public UpdateRiskClassDTO() {
		
	}

	public List<RiskClassDTO> getRiskClasses() {
		return riskClasses;
	}

	public void setRiskClasses(List<RiskClassDTO> riskClasses) {
		this.riskClasses = riskClasses;
	}

	public List<RiskClassDTO> getImpactClasses() {
		return impactClasses;
	}

	public void setImpactClasses(List<RiskClassDTO> impactClasses) {
		this.impactClasses = impactClasses;
	}

	public List<RiskClassDTO> getProbabilityClasses() {
		return probabilityClasses;
	}

	public void setProbabilityClasses(List<RiskClassDTO> probabilityClasses) {
		this.probabilityClasses = probabilityClasses;
	}

}
