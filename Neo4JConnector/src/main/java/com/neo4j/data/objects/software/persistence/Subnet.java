package com.neo4j.data.objects.software.persistence;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label="Subnet")
public class Subnet {
	
	@Id @GeneratedValue
	private Long id;
	
	@Property(name="ipRange")
	private String ipRange;
	
	@Relationship(type="associatedSubnet", direction=Relationship.INCOMING)
	private Set<SubnetAssociation> associatedApplications;
	
	public Subnet() {
		this.associatedApplications = new HashSet<SubnetAssociation>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpRange() {
		return ipRange;
	}

	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}

	public Set<SubnetAssociation> getAssociatedApplications() {
		return associatedApplications;
	}

	public void setAssociatedApplications(Set<SubnetAssociation> associatedApplications) {
		this.associatedApplications = associatedApplications;
	}	

}
