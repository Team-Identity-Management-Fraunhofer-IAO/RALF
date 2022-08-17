package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

import java.util.List;

public class FactorComparisonListDTO {
	private int organization_id;
	private int service_id;
	private List<FactorComparisonDTO> motivatingFactorComparisons;
	private List<FactorComparisonDTO> vulnerabilityEnablingFactorComparisons;
	
	public FactorComparisonListDTO() {
		
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

	public List<FactorComparisonDTO> getMotivatingFactorComparisons() {
		return motivatingFactorComparisons;
	}

	public void setMotivatingFactorComparisons(List<FactorComparisonDTO> motivatingFactorComparisons) {
		this.motivatingFactorComparisons = motivatingFactorComparisons;
	}

	public List<FactorComparisonDTO> getVulnerabilityEnablingFactorComparisons() {
		return vulnerabilityEnablingFactorComparisons;
	}

	public void setVulnerabilityEnablingFactorComparisons(
			List<FactorComparisonDTO> vulnerabilityEnablingFactorComparisons) {
		this.vulnerabilityEnablingFactorComparisons = vulnerabilityEnablingFactorComparisons;
	}
	
}
