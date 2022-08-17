package com.sql.data.objects.aggregations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.sql.data.objects.persistence.report.AttackPattern;
import com.sql.data.objects.persistence.report.CombinationsPK;

public class AttackVector implements Serializable{
	private String vectorName;
	private String vectorDescription;
	private List<ComponentVulnerability> componentVulnerabilities;
	private List<AttackPattern> attackPatterns;
	
	public AttackVector() {
		this.componentVulnerabilities = new ArrayList<ComponentVulnerability>();
		this.attackPatterns = new ArrayList<AttackPattern>();
	}
	public String getVectorName() {
		return vectorName;
	}
	public void setVectorName(String vectorName) {
		this.vectorName = vectorName;
	}
	public String getVectorDescription() {
		return vectorDescription;
	}
	public void setVectorDescription(String vectorDescription) {
		this.vectorDescription = vectorDescription;
	}
	public List<ComponentVulnerability> getComponentVulnerabilities() {
		return componentVulnerabilities;
	}
	public void setComponentVulnerabilities(List<ComponentVulnerability> componentVulnerabilities) {
		this.componentVulnerabilities = componentVulnerabilities;
	}
	public List<AttackPattern> getAttackPatterns() {
		return attackPatterns;
	}
	public void setAttackPatterns(List<AttackPattern> attackPatterns) {
		this.attackPatterns = attackPatterns;
	}
	
	@Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AttackVector) {
            if (((AttackVector) o).getVectorName().equals(this.vectorName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vectorName);
    }
	
	
}
