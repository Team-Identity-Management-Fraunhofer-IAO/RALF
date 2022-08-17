package com.sql.data.objects.persistence.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="combinationList")
public class CombinationList {
	private int combinationListID;
	private int combinationID;
	
	public CombinationList() {
		
	}

	@Column(name="combinationListID", nullable=false)
	public int getCombinationListID() {
		return combinationListID;
	}

	public void setCombinationListID(int combinationListID) {
		this.combinationListID = combinationListID;
	}
	
	@Id
	@Column(name="combinationID", nullable=false)
	public int getCombinationID() {
		return combinationID;
	}

	public void setCombinationID(int combinationID) {
		this.combinationID = combinationID;
	}

}
