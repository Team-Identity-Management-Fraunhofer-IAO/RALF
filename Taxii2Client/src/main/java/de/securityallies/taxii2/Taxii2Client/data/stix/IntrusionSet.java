package de.securityallies.taxii2.Taxii2Client.data.stix;

import java.util.List;

public class IntrusionSet {
	private String id;
	private Identity created_by_ref;
	private String created;
	private String modified;
	public final static String type = "intrusion-set";
	private String name;
	
	//optional attributes
	private String description;
	private List<MarkingDefinition> object_marking_refs;
	private List<Reference> references;
	private List<String> aliases;
	
	//Mitre attributes
	private String x_mitre_version;
	private List<String> x_mitre_contributors;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Identity getCreated_by_ref() {
		return created_by_ref;
	}

	public void setCreated_by_ref(Identity created_by_ref) {
		this.created_by_ref = created_by_ref;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MarkingDefinition> getObject_marking_refs() {
		return object_marking_refs;
	}

	public void setObject_marking_refs(List<MarkingDefinition> object_marking_refs) {
		this.object_marking_refs = object_marking_refs;
	}

	public List<Reference> getReferences() {
		return references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	public String getX_mitre_version() {
		return x_mitre_version;
	}

	public void setX_mitre_version(String x_mitre_version) {
		this.x_mitre_version = x_mitre_version;
	}

	public String getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof IntrusionSet) {
			if (((IntrusionSet) o).getId().equals(this.getId())){
				return true;
			}
			return false;
		}
		return false;
	}

	public List<String> getX_mitre_contributors() {
		return x_mitre_contributors;
	}

	public void setX_mitre_contributors(List<String> x_mitre_contributors) {
		this.x_mitre_contributors = x_mitre_contributors;
	}
	

}
