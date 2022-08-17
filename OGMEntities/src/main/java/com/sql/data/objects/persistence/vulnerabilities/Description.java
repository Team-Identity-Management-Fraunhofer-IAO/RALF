/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.vulnerabilities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="description")
public class Description implements Serializable {
    int descId;
    String descriptionText;
    int cveID;
    int cveYear;
    
    public Description(){
    
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="descId", nullable=false)
    public int getDescId() {
        return descId;
    }

    public void setDescId(int descId) {
        this.descId = descId;
    }
    
    @Column(name="descriptionText", nullable=true)
    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    @Column(name="cveID", nullable=false)
    public int getCveID() {
        return cveID;
    }

    public void setCveID(int cveID) {
        this.cveID = cveID;
    }

    @Column(name="cveYear", nullable=false)
    public int getCveYear() {
        return cveYear;
    }

    public void setCveYear(int cveYear) {
        this.cveYear = cveYear;
    }
    
    
}
