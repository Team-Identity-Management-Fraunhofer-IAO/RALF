/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.platforms;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="platform_combination")
public class PlatformCombination implements Serializable{
    int combinationID;
    String operator;
    int confID;
    
    public PlatformCombination(){
    
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="combinationID", nullable=false)
    public int getCombinationID() {
        return combinationID;
    }

    public void setCombinationID(int combinationID) {
        this.combinationID = combinationID;
    }

    @Column(name="operator", nullable=true)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name="confID",nullable=false)
    public int getConfID() {
        return confID;
    }

    public void setConfID(int confID) {
        this.confID = confID;
    }
    
    
    
}
