package com.neo4j.data.objects.software.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="associatedSubnet")
public class SubnetAssociation {
	
	@StartNode
	SWStack stack;
	
	@EndNode
	Subnet subnet;
	
	@Property(name="excludeIPs")
	private String excludeIPs;
	
	
	
	private List<String> excludeIPList;
	
	public SubnetAssociation() {
		this.excludeIPList = new ArrayList<String>();	
	}

	public SWStack getStack() {
		return stack;
	}

	public void setStack(SWStack stack) {
		this.stack = stack;
	}

	public Subnet getSubnet() {
		return subnet;
	}

	public void setSubnet(Subnet subnet) {
		this.subnet = subnet;
	}

	public String getExcludeIPs() {
		return excludeIPs;
	}

	public void setExcludeIPs(String excludeIPs) {
		this.excludeIPs = excludeIPs;
		
	}

	public List<String> getExcludeIPList() {
		if (this.excludeIPs != null && this.excludeIPList.isEmpty()) {
			Collections.addAll(this.excludeIPList, excludeIPs.split(";"));
		}
		return excludeIPList;
	}

	public void setExcludeIPList(List<String> excludeIPList) {
		this.excludeIPList = excludeIPList;
	}

}
