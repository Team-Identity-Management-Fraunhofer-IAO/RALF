package com.sql.data.objects.aggregations;

import java.io.Serializable;
import java.util.Objects;

public class RatedAttackVector implements Serializable{
	
	private String attackVector;
	private float minBase;
	private float maxBase;
	private float minExploitability;
	private float maxExploitability;
	private float minImpact;
	private float maxImpact;
	
	public String getAttackVector() {
		return attackVector;
	}
	public void setAttackVector(String attackVector) {
		this.attackVector = attackVector;
	}
	public float getMinBase() {
		return minBase;
	}
	public void setMinBase(float minBase) {
		this.minBase = minBase;
	}
	public float getMaxBase() {
		return maxBase;
	}
	public void setMaxBase(float maxBase) {
		this.maxBase = maxBase;
	}
	public float getMinExploitability() {
		return minExploitability;
	}
	public void setMinExploitability(float minExploitability) {
		this.minExploitability = minExploitability;
	}
	public float getMaxExploitability() {
		return maxExploitability;
	}
	public void setMaxExploitability(float maxExploitability) {
		this.maxExploitability = maxExploitability;
	}
	public float getMinImpact() {
		return minImpact;
	}
	public void setMinImpact(float minImpact) {
		this.minImpact = minImpact;
	}
	public float getMaxImpact() {
		return maxImpact;
	}
	public void setMaxImpact(float maxImpact) {
		this.maxImpact = maxImpact;
	}	
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof RatedAttackVector) {
			if (((RatedAttackVector) o).getAttackVector().equals(this.attackVector)){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode() {
		return Objects.hash(attackVector);
	}

}
