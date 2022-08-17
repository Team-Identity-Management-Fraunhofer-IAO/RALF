/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.attackpatterns;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="attackPatternRelationship")
@IdClass(AttackPatternRelationshipPK.class)
public class AttackPatternRelationship implements Serializable{

    int idLeft;

    int idRight;
    String nature;
    
    public AttackPatternRelationship(){
    
    }
    
    @Id
    @Column(name="idLeft", nullable=false)
    public int getIdLeft() {
        return idLeft;
    }

    public void setIdLeft(int idLeft) {
        this.idLeft = idLeft;
    }
    
    @Id
    @Column(name="idRight", nullable=false)
    public int getIdRight() {
        return idRight;
    }

    public void setIdRight(int idRight) {
        this.idRight = idRight;
    }
    
    @Column(name="nature", nullable=true)
    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
    
    
}
