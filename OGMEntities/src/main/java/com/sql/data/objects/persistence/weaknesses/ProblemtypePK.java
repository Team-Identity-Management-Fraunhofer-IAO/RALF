/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.weaknesses;

import java.io.Serializable;
import java.util.Objects;


public class ProblemtypePK implements Serializable{
    protected int cveYear;
    protected int cveID;
    protected int weaknessID;
    
    public ProblemtypePK(){
    
    }
    
    public ProblemtypePK(int cveYear, int cveID, int weaknessID){
        this.cveYear = cveYear;
        this.cveID = cveID;
        this.weaknessID = weaknessID;
    }

    public int getCveYear() {
        return cveYear;
    }

    public void setCveYear(int cveYear) {
        this.cveYear = cveYear;
    }

    public int getCveID() {
        return cveID;
    }

    public void setCveID(int cveID) {
        this.cveID = cveID;
    }

    public int getWeaknessID() {
        return weaknessID;
    }

    public void setWeaknessID(int weaknessID) {
        this.weaknessID = weaknessID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cveYear,cveID,weaknessID);
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
        final ProblemtypePK other = (ProblemtypePK) obj;
        if (this.cveYear != other.cveYear) {
            return false;
        }
        if (this.cveID != other.cveID) {
            return false;
        }
        if (this.weaknessID != other.weaknessID) {
            return false;
        }
        return true;
    }
    
    
}
