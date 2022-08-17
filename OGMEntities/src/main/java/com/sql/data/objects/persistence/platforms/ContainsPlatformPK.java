/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.platforms;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author kurowski
 */
public class ContainsPlatformPK implements Serializable{
    protected int combinationID;
    protected int cpeID;
    
    public ContainsPlatformPK(){
    
    }
    
    public ContainsPlatformPK(int combinationID, int cpeID){
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
    public int hashCode() {
        return Objects.hash(combinationID,cpeID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContainsPlatformPK other = (ContainsPlatformPK) obj;
        if (this.combinationID != other.combinationID) {
            return false;
        }
        if (this.cpeID != other.cpeID) {
            return false;
        }
        return true;
    }
    
    
    
}
