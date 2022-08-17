/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.weaknesses;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="problemtype")
@IdClass(ProblemtypePK.class)
public class Problemtype implements Serializable {
    int cveYear;
    int cveID;
    int weaknessID;
    
    public Problemtype(){
    
    }
    
    @Id
    @Column(name="cveYear", nullable=false)
    public int getCveYear() {
        return cveYear;
    }

    public void setCveYear(int cveYear) {
        this.cveYear = cveYear;
    }

    @Id
    @Column(name="cveID", nullable=false)
    public int getCveID() {
        return cveID;
    }

    public void setCveID(int cveID) {
        this.cveID = cveID;
    }

    @Id
    @Column(name="weaknessID", nullable=false)
    public int getWeaknessID() {
        return weaknessID;
    }

    public void setWeaknessID(int weaknessID) {
        this.weaknessID = weaknessID;
    }
    
    public void parseWeaknessID(String weakness){
        String[] parts = weakness.split("-");
        if (parts.length == 2){
            this.weaknessID = Integer.parseInt(parts[1]);
        }
    }
    
    
}
