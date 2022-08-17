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
public class CPE_childrenPK implements Serializable{
    protected int father_cpeID;
    protected int child_cpeID;
    
    public CPE_childrenPK(){

    }
    
    public CPE_childrenPK(int father_cpeID, int child_cpeID){
        this.father_cpeID = father_cpeID;
        this.child_cpeID = child_cpeID;
    }

    public int getFather_cpeID() {
        return father_cpeID;
    }

    public void setFather_cpeID(int father_cpeID) {
        this.father_cpeID = father_cpeID;
    }

    public int getChild_cpeID() {
        return child_cpeID;
    }

    public void setChild_cpeID(int child_cpeID) {
        this.child_cpeID = child_cpeID;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof CPE_childrenPK) {
            if (((CPE_childrenPK) o).father_cpeID == this.father_cpeID && ((CPE_childrenPK) o).child_cpeID == this.child_cpeID){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(father_cpeID, child_cpeID);
    }
    
}
