/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.platforms;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(CPE_childrenPK.class)
@Table(name="cpe_children")
public class CPE_children implements Serializable{
    int father_cpeID;
    int child_cpeID;
    
    public CPE_children(){
    
    }   
    @Id
    @Column(name="father_cpeID",nullable=false)
    public int getFather_cpeID() {
        return father_cpeID;
    }

    public void setFather_cpeID(int father_cpeID) {
        this.father_cpeID = father_cpeID;
    }

    @Id
    @Column(name="child_cpeID",nullable=false)
    public int getChild_cpeID() {
        return child_cpeID;
    }

    public void setChild_cpeID(int child_cpeID) {
        this.child_cpeID = child_cpeID;
    }
    
}
