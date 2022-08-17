/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.vulnerabilities;

import java.io.Serializable;
import java.util.Objects;

public class CVECorePK implements Serializable{
    protected int cveYear;
    protected int cveID;
    
    public CVECorePK(){
    
    }
    
    public CVECorePK(int cveYear, int cveID){
        this.cveYear = cveYear;
        this.cveID = cveID;
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
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof CVECorePK) {
            if (((CVECorePK) o).cveID == this.cveID && ((CVECorePK) o).cveYear == this.cveYear){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cveYear, cveID);
    }
    
}
