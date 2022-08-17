package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

public class FactorComparisonDTO {
	private int factor_id;
	private int factor_comparison_id;
	private int comparison;
	private String comparison_operator;
	
	public FactorComparisonDTO() {
		
	}

	public int getFactor_id() {
		return factor_id;
	}

	public void setFactor_id(int factor_id) {
		this.factor_id = factor_id;
	}

	public int getFactor_comparison_id() {
		return factor_comparison_id;
	}

	public void setFactor_comparison_id(int factor_comparison_id) {
		this.factor_comparison_id = factor_comparison_id;
	}

	public int getComparison() {
		return comparison;
	}

	public void setComparison(int comparison) {
		this.comparison = comparison;
	}

	public String getComparison_operator() {
		return comparison_operator;
	}

	public void setComparison_operator(String comparison_operator) {
		this.comparison_operator = comparison_operator;
	}
	
}
