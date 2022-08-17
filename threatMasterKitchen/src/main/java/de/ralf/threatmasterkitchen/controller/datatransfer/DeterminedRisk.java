package de.ralf.threatmasterkitchen.controller.datatransfer;

import java.util.List;

import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;

public class DeterminedRisk {
	private long weight;
	private long weight_pivoted;
	private BusinessRisk risk;
	private long monetary_value_min;
	private long monetary_value_max;
	private List<Integer> killer_ids;

	public DeterminedRisk() {
		
	}

	public List<Integer> getKiller_ids() {
		return killer_ids;
	}

	public void setKiller_ids(List<Integer> killer_ids) {
		this.killer_ids = killer_ids;
	}


	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public long getWeight_pivoted() {
		return weight_pivoted;
	}

	public void setWeight_pivoted(int weight_pivoted) {
		this.weight_pivoted = weight_pivoted;
	}

	public BusinessRisk getRisk() {
		return risk;
	}

	public void setRisk(BusinessRisk risk) {
		this.risk = risk;
	}

	public long getMonetary_value_min() {
		return monetary_value_min;
	}

	public void setMonetary_value_min(long monetary_value_min) {
		this.monetary_value_min = monetary_value_min;
	}

	public long getMonetary_value_max() {
		return monetary_value_max;
	}

	public void setMonetary_value_max(long monetary_value_max) {
		this.monetary_value_max = monetary_value_max;
	}
	
	
}
