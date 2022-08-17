package com.sql.data.objects.aggregations;

import java.util.ArrayList;
import java.util.List;

import com.sql.data.objects.persistence.platforms.CPECore;

/*
 * Non-persistent POJO Class for storing platforms that combined with the platform of an aggregated vulnerability are affected by the cve
 * 
 */
public class VulnerablePlatformCombination {
	
	private List<CPECore> platforms;
	private String operator;
	
	public VulnerablePlatformCombination() {
		this.platforms = new ArrayList<CPECore>();
	}

	public List<CPECore> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<CPECore> platforms) {
		this.platforms = platforms;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	

}
