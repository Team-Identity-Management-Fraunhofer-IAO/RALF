package com.neo4j.data.objects.software.persistence;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label="SWStack")
public class SWStack {
	@Id @GeneratedValue
	private Long id;
	
	@Property(name="name")
	private String name;
	
	@Property(name="description")
	private String description;
	
	@Property(name="vectorNetwork")
	private String vectorNetwork;
	
	@Property(name="vectorAdjacent")
	private String vectorAdjacent;
	
	@Property(name="vectorLocal")
	private String vectorLocal;
	
	@Property(name="vectorPhysical")
	private String vectorPhysical;
	
	@Property(name="ip")
	private String ip;
	
	@Property(name="subnet")
	private String subnet;
	
	@Relationship(type = "includes", direction = Relationship.OUTGOING)
	private Set<Component> SWStackComponents;
	
	@Relationship(type = "contains", direction = Relationship.INCOMING)
	private Set<Group> groups;
	
	@Relationship(type="associatedSubnet", direction=Relationship.OUTGOING)
	private Set<SubnetAssociation> associatedSubnet;
	
	private boolean vulnerable;
	private boolean criticallyVulnerable;
	private boolean emptyStack;
	
	public SWStack() {
		this.name = "";
		this.SWStackComponents = new HashSet<Component>();
		this.groups = new HashSet<Group>();
		this.associatedSubnet = new HashSet<SubnetAssociation>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Component> getSWStackComponents() {
		return SWStackComponents;
	}

	public void setSWStackComponents(Set<Component> sWStackComponents) {
		SWStackComponents = sWStackComponents;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVectorNetwork() {
		return vectorNetwork;
	}

	public void setVectorNetwork(String vectorNetwork) {
		this.vectorNetwork = vectorNetwork;
	}

	public String getVectorAdjacent() {
		return vectorAdjacent;
	}

	public void setVectorAdjacent(String vectorAdjacent) {
		this.vectorAdjacent = vectorAdjacent;
	}

	public String getVectorLocal() {
		return vectorLocal;
	}

	public void setVectorLocal(String vectorLocal) {
		this.vectorLocal = vectorLocal;
	}

	public String getVectorPhysical() {
		return vectorPhysical;
	}

	public void setVectorPhysical(String vectorPhysical) {
		this.vectorPhysical = vectorPhysical;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}

	public Set<SubnetAssociation> getAssociatedSubnet() {
		return associatedSubnet;
	}

	public void setAssociatedSubnet(Set<SubnetAssociation> associatedSubnet) {
		this.associatedSubnet = associatedSubnet;
	}

	public boolean isVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}

	public boolean isCriticallyVulnerable() {
		return criticallyVulnerable;
	}

	public void setCriticallyVulnerable(boolean criticallyVulnerable) {
		this.criticallyVulnerable = criticallyVulnerable;
	}

	public boolean isEmptyStack() {
		return emptyStack;
	}

	public void setEmptyStack(boolean emptyStack) {
		this.emptyStack = emptyStack;
	}
	
}
