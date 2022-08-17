/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.attackpatterns;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author kurowski
 */
public class CAPECATTACKRelationshipPK implements Serializable {

    protected int CAPECId;
    protected int ATTACKId;

    public CAPECATTACKRelationshipPK() {

    }

    public CAPECATTACKRelationshipPK(int CAPECId, int ATTACKId) {
        this.CAPECId = CAPECId;
        this.ATTACKId = ATTACKId;
    }

    public int getCAPECId() {
        return CAPECId;
    }

    public void setCAPECId(int CAPECId) {
        this.CAPECId = CAPECId;
    }

    public int getATTACKId() {
        return ATTACKId;
    }

    public void setATTACKId(int ATTACKId) {
        this.ATTACKId = ATTACKId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof CAPECATTACKRelationshipPK) {
            if (((CAPECATTACKRelationshipPK) o).CAPECId == this.CAPECId && ((CAPECATTACKRelationshipPK) o).ATTACKId == this.ATTACKId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(CAPECId, ATTACKId);
    }
}
