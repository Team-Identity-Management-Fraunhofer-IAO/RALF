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
public class CAPECCWERelationshipPK implements Serializable {

    protected int CAPECId;
    protected int CWEId;

    public CAPECCWERelationshipPK() {

    }

    public CAPECCWERelationshipPK(int CAPECId, int CWEId) {
        this.CAPECId = CAPECId;
        this.CWEId = CWEId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof CAPECCWERelationshipPK) {
            if (((CAPECCWERelationshipPK) o).CAPECId == this.CAPECId && ((CAPECCWERelationshipPK) o).CWEId == this.CWEId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(CAPECId, CWEId);
    }

    public int getCAPECId() {
        return CAPECId;
    }

    public void setCAPECId(int CAPECId) {
        this.CAPECId = CAPECId;
    }

    public int getCWEId() {
        return CWEId;
    }

    public void setCWEId(int CWEId) {
        this.CWEId = CWEId;
    }

}
