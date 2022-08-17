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
import javax.persistence.Table;

@Entity
@Table(name="CWECore")
public class CWECore implements Serializable{
    int weaknessID;
    String name;
    String description;
    String extended_description;
    String likelihood;
    
    public CWECore(){
    
    }

    @Id
    @Column(name="weaknessID", nullable=false)
    public int getWeaknessID() {
        return weaknessID;
    }
    
    public void setWeaknessID(int weaknessID) {
        this.weaknessID = weaknessID;
    }

    @Column(name="name", nullable=true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="description", nullable=true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="extended_description", nullable=true)
    public String getExtended_description() {
        return extended_description;
    }

    public void setExtended_description(String extended_description) {
        this.extended_description = extended_description;
    }

    @Column(name="likelihood", nullable=true)
    public String getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(String likelihood) {
        this.likelihood = likelihood;
    }
    
    
    
}
