package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

public class FactorResponseDTO {
	private int factor_id;
	private int factor_question_id;
	private boolean response;
	private String justification;
	
	public FactorResponseDTO() {
		response = false;
	}

	public int getFactor_id() {
		return factor_id;
	}

	public void setFactor_id(int factor_id) {
		this.factor_id = factor_id;
	}

	public int getFactor_question_id() {
		return factor_question_id;
	}

	public void setFactor_question_id(int factor_question_id) {
		this.factor_question_id = factor_question_id;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}
	
	
}
