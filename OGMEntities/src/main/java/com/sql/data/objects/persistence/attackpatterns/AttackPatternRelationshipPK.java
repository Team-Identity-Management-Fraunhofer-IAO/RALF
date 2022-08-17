/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.attackpatterns;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author kurowski
 */
public class AttackPatternRelationshipPK implements Serializable{
    protected int idLeft;
    protected int idRight;
    
    public AttackPatternRelationshipPK(){
    
    }
    
    public AttackPatternRelationshipPK(int idLeft, int idRight){
        this.idLeft = idLeft;
        this.idRight = idRight;
    }
    
    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (o instanceof AttackPatternRelationshipPK){
            if ((((AttackPatternRelationshipPK) o).getidLeft() == this.idLeft) && (((AttackPatternRelationshipPK) o).getidRight() == this.idRight))
                return true;
        }
        return false;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(idLeft, idRight);
    }
    
    

    public int getidLeft() {
        return idLeft;
    }

    public void setIdLeft(int idLeft) {
        this.idLeft = idLeft;
    }

    public int getidRight() {
        return idRight;
    }

    public void setidRight(int idRight) {
        this.idRight = idRight;
    }
    
    
}
