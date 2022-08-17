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
@IdClass(ContainsPlatformPK.class)
@Table(name="contains_platform")
public class ContainsPlatform implements Serializable{
    int combinationID;
    int cpeID;
    boolean vulnerable;
    String versionStart;
    String versionEnd;
    boolean startIncluding;
    boolean endIncluding;
    
    public ContainsPlatform(){
    
    }
    
    @Id
    @Column(name="combinationID", nullable=false)
    public int getCombinationID() {
        return combinationID;
    }

    public void setCombinationID(int combinationID) {
        this.combinationID = combinationID;
    }

    @Id
    @Column(name="cpeID", nullable=false)
    public int getCpeID() {
        return cpeID;
    }

    public void setCpeID(int cpeID) {
        this.cpeID = cpeID;
    }

    @Column(name="vulnerable", nullable=true)
    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    @Column(name="versionStart", nullable=true)
    public String getVersionStart() {
        return versionStart;
    }

    public void setVersionStart(String versionStart) {
        this.versionStart = versionStart;
    }

    @Column(name="versionEnd", nullable=true)
    public String getVersionEnd() {
        return versionEnd;
    }

    public void setVersionEnd(String versionEnd) {
        this.versionEnd = versionEnd;
    }

    @Column(name="startIncluding", nullable=true)
    public boolean isStartIncluding() {
        return startIncluding;
    }

    public void setStartIncluding(boolean startIncluding) {
        this.startIncluding = startIncluding;
    }

    @Column(name="endIncluding", nullable=true)
    public boolean isEndIncluding() {
        return endIncluding;
    }

    public void setEndIncluding(boolean endIndluding) {
        this.endIncluding = endIndluding;
    }
    
    
    
}
