package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

import java.util.List;

public class FactorQuestionnaireResponseDTO {
	private int organization_id;
	private int service_id;
	private List<FactorResponseDTO> attack_motivating_questions;
	private List<FactorResponseDTO> vulnerability_enabling_questions;
	
	public FactorQuestionnaireResponseDTO() {
		
	}

	public int getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(int organization_id) {
		this.organization_id = organization_id;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public List<FactorResponseDTO> getAttack_motivating_questions() {
		return attack_motivating_questions;
	}

	public void setAttack_motivating_questions(List<FactorResponseDTO> attack_motivating_questions) {
		this.attack_motivating_questions = attack_motivating_questions;
	}

	public List<FactorResponseDTO> getVulnerability_enabling_questions() {
		return vulnerability_enabling_questions;
	}

	public void setVulnerability_enabling_questions(List<FactorResponseDTO> vulnerability_enabling_questions) {
		this.vulnerability_enabling_questions = vulnerability_enabling_questions;
	}

	
}
