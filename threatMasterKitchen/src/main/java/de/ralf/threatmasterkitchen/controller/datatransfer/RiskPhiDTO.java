package de.ralf.threatmasterkitchen.controller.datatransfer;

public class RiskPhiDTO {
	private double risk_phi_value;
	private String risk_phi_type;
	
	public RiskPhiDTO() {
		
	}

	public double getRisk_phi_value() {
		return risk_phi_value;
	}

	public void setRisk_phi_value(double risk_phi_value) {
		this.risk_phi_value = risk_phi_value;
	}

	public String getRisk_phi_type() {
		return risk_phi_type;
	}

	public void setRisk_phi_type(String risk_phi_type) {
		this.risk_phi_type = risk_phi_type;
	}

}
