package com.sql.data.objects.persistence.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sql.data.objects.persistence.vulnerabilities.CVECorePK;

@Entity
@IdClass(CombinationsPK.class)
@Table(name="combinations")
public class Combinations {
	private int combinationID;
	private int cpeID;
	
	public Combinations() {
		
	}
	
	@Id
	@Column(name="combinationID", nullable=false)
	public int getCombinationID() {
		return combinationID;
	}

	public void setCombinationID(int combinationID) {
		this.combinationID = combinationID;
	}

	@Id
	@Column(name="cpeID", nullable=false)
	public int getCpeID() {
		return cpeID;
	}

	public void setCpeID(int cpeID) {
		this.cpeID = cpeID;
	}
	
	
	
}
