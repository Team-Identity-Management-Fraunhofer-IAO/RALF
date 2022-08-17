package com.sql.data.objects.persistence.report;

import java.io.Serializable;
import java.util.Objects;

import com.sql.data.objects.persistence.platforms.CPE_childrenPK;

public class CombinationsPK implements Serializable{
	protected int combinationID;
	protected int cpeID;
	
	public CombinationsPK() {
		
	}
	
	public CombinationsPK(int combinationID, int cpeID) {
		this.combinationID = combinationID;
		this.cpeID = cpeID;
	}
	
	public int getCombinationID() {
		return combinationID;
	}

	public void setCombinationID(int combinationID) {
		this.combinationID = combinationID;
	}

	public int getCpeID() {
		return cpeID;
	}

	public void setCpeID(int cpeID) {
		this.cpeID = cpeID;
	}

	@Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof CombinationsPK) {
            if (((CombinationsPK) o).getCombinationID() == this.combinationID && ((CombinationsPK) o).getCpeID() == this.cpeID){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(combinationID, cpeID);
    }

}
