/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.vulnerabilities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(CVECorePK.class)
@Table(name = "cve")
public class CVECore implements Serializable {

    int cveYear;
    int cveID;
    String identifierString;
    String vectorString;
    String attackVector;
    String attackComplexity;
    String privilegesRequired;
    String userInteraction;
    String scope;
    String confidentialityImpact;
    String integrityImpact;
    String availabilityImpact;
    float baseScore;
    String baseSeverity;
    float exploitabilityScore;
    float impactScore;
    String modifiedDate;

    public CVECore() {
    	modifiedDate = "";
    }

    @Id
    @Column(name = "cveYear", nullable = false)
    public int getCveYear() {
        return cveYear;
    }

    public void setCveYear(int cveYear) {
        this.cveYear = cveYear;
    }

    @Id
    @Column(name = "cveID", nullable = false)
    public int getCveID() {
        return cveID;
    }

    public void setCveID(int cveID) {
        this.cveID = cveID;
    }

    @Column(name = "identifierString", nullable = true)
    public String getIdentifierString() {
        return identifierString;
    }

    public void setIdentifierString(String identifierString) {
        this.identifierString = identifierString;
    }

    public void parseIdentifierString(String identifierString) {
        String[] parts = identifierString.split("-");
        if (parts.length == 3) {
            this.cveYear = Integer.parseInt(parts[1]);
            this.cveID = Integer.parseInt(parts[2]);
        }
    }

    @Column(name="vectorString", nullable=true)
    public String getVectorString() {
        return vectorString;
    }

    public void setVectorString(String vectorString) {
        this.vectorString = vectorString;
    }

    @Column(name="attackVector", nullable=true)
    public String getAttackVector() {
        return attackVector;
    }

    public void setAttackVector(String attackVector) {
        this.attackVector = attackVector;
    }

    @Column(name="attackComplexity", nullable=true)
    public String getAttackComplexity() {
        return attackComplexity;
    }

    public void setAttackComplexity(String attackComplexity) {
        this.attackComplexity = attackComplexity;
    }

    @Column(name="privilegesRequired", nullable=true)
    public String getPrivilegesRequired() {
        return privilegesRequired;
    }

    public void setPrivilegesRequired(String privilegesRequired) {
        this.privilegesRequired = privilegesRequired;
    }

    @Column(name="userInteraction", nullable=true)
    public String getUserInteraction() {
        return userInteraction;
    }

    public void setUserInteraction(String userInteraction) {
        this.userInteraction = userInteraction;
    }

    @Column(name="scope", nullable=true)
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Column(name="confidentialityImpact", nullable=true)
    public String getConfidentialityImpact() {
        return confidentialityImpact;
    }

    public void setConfidentialityImpact(String confidentialityImpact) {
        this.confidentialityImpact = confidentialityImpact;
    }

    @Column(name="integrityImpact", nullable=true)
    public String getIntegrityImpact() {
        return integrityImpact;
    }

    public void setIntegrityImpact(String integrityImpact) {
        this.integrityImpact = integrityImpact;
    }
    
    @Column(name="availabilityImpact", nullable=true)
    public String getAvailabilityImpact() {
        return availabilityImpact;
    }

    public void setAvailabilityImpact(String availabilityImpact) {
        this.availabilityImpact = availabilityImpact;
    }

    @Column(name="baseScore", nullable=true)
    public float getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(float baseScore) {
        this.baseScore = baseScore;
    }

    @Column(name="baseSeverity", nullable=true)
    public String getBaseSeverity() {
        return baseSeverity;
    }

    public void setBaseSeverity(String baseSeverity) {
        this.baseSeverity = baseSeverity;
    }

    @Column(name="exploitabilityScore", nullable=true)
    public float getExploitabilityScore() {
        return exploitabilityScore;
    }

    public void setExploitabilityScore(float exploitabilityScore) {
        this.exploitabilityScore = exploitabilityScore;
    }

    @Column(name="impactScore", nullable=true)
    public float getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(float impactScore) {
        this.impactScore = impactScore;
    }

    @Column(name="modifiedDate", nullable=false)
	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
    
    

}
