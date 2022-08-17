package com.neo4j.data.objects.software;

import java.util.ArrayList;
import java.util.List;

import com.neo4j.data.objects.software.persistence.Component;

public class ComponentCombination {
	private List<Component> components;
	
	public ComponentCombination() {
		this.components = new ArrayList<Component>();
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}
	
}
