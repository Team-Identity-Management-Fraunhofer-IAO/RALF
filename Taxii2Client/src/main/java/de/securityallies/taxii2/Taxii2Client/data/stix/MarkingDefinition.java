package de.securityallies.taxii2.Taxii2Client.data.stix;

import java.util.Map;

public class MarkingDefinition {
	public final static String type = "marking-definition";
	private String id;
	private String name;
	private Identity created_by_reF;
	private String created;
	private String modified;
	private String definition_type;
	private Map<String, String> definition;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Identity getCreated_by_reF() {
		return created_by_reF;
	}
	public void setCreated_by_reF(Identity created_by_reF) {
		this.created_by_reF = created_by_reF;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getDefinition_type() {
		return definition_type;
	}
	public void setDefinition_type(String definition_type) {
		this.definition_type = definition_type;
	}
	public Map<String, String> getDefinition() {
		return definition;
	}
	public void setDefinition(Map<String, String> definition) {
		this.definition = definition;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof MarkingDefinition) {
			if (((MarkingDefinition) o).getId().equals(this.getId())){
				return true;
			}
			return false;
		}
		return false;
	}
	
	
}
