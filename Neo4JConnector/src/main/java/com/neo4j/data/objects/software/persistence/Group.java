package com.neo4j.data.objects.software.persistence;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label="Group")
public class Group {
	@Id @GeneratedValue
	private Long id;
	
	@Property(name="groupname")
	private String groupname;
	
	@Relationship(type = "contains", direction = Relationship.OUTGOING)
	private Set<SWStack> SWStacks;
	
	public Group() {
		this.groupname = "";
		this.SWStacks = new HashSet<SWStack>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Set<SWStack> getSWStacks() {
		return SWStacks;
	}

	public void setSWStacks(Set<SWStack> sWStacks) {
		SWStacks = sWStacks;
	}
	
}