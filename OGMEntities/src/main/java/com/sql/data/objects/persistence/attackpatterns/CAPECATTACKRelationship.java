/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.attackpatterns;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(CAPECATTACKRelationshipPK.class)
@Table(name="CAPECATTACKRelationship")
public class CAPECATTACKRelationship implements Serializable{
    int CAPECId;
    int ATTACKId;
    
    public CAPECATTACKRelationship(){
    
    }
    
    @Id
    @Column(name="CAPECId", nullable=false)
    public int getCAPECId() {
        return CAPECId;
    }

    public void setCAPECId(int CAPECId) {
        this.CAPECId = CAPECId;
    }

    @Id
    @Column(name="ATTACKId", nullable=false)
    public int getATTACKId() {
        return ATTACKId;
    }

    public void setATTACKId(int ATTACKId) {
        this.ATTACKId = ATTACKId;
    }
    
    
    
    
    
}
