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
@Table(name="configuration_node")
public class ConfigurationNode implements Serializable{
    int confID;
    String node_operator;
    String relationship_operator;
    int cveID;
    int cveYear;
    
    public ConfigurationNode(){
    
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="confID", nullable=false)
    public int getConfID() {
        return confID;
    }

    public void setConfID(int confID) {
        this.confID = confID;
    }

    @Column(name="node_operator", nullable=true)
    public String getNode_operator() {
        return node_operator;
    }

    public void setNode_operator(String node_operator) {
        this.node_operator = node_operator;
    }

    @Column(name="relationship_operator", nullable=true)
    public String getRelationship_operator() {
        return relationship_operator;
    }

    public void setRelationship_operator(String relationship_operator) {
        this.relationship_operator = relationship_operator;
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
