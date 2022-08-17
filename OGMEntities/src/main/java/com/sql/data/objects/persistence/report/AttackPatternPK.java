package com.sql.data.objects.persistence.report;

import java.io.Serializable;
import java.util.Objects;

public class AttackPatternPK implements Serializable{
	protected int CAPECID;
	protected int vulnCompID;
	protected String attackVector;
	
	public AttackPatternPK() {
		
	}
	
	public AttackPatternPK(int CAPECID, int vulnCompID, String attackVector) {
		this.CAPECID = CAPECID;
		this.vulnCompID = vulnCompID;
		this.attackVector = attackVector;
	}
	
	public int getCAPECID() {
		return CAPECID;
	}

	public void setCAPECID(int cAPECID) {
		CAPECID = cAPECID;
	}

	public int getVulnCompID() {
		return vulnCompID;
	}

	public void setVulnCompID(int vulnCompID) {
		this.vulnCompID = vulnCompID;
	}

	public String getAttackVector() {
		return attackVector;
	}

	public void setAttackVector(String attackVector) {
		this.attackVector = attackVector;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof AttackPatternPK) {
			if ((((AttackPatternPK) o).CAPECID == this.CAPECID) && (((AttackPatternPK) o).vulnCompID == this.vulnCompID) && (((AttackPatternPK) o).getAttackVector().equals(this.attackVector))){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode() {
		return Objects.hash(CAPECID, vulnCompID, attackVector);
	}
	
}
