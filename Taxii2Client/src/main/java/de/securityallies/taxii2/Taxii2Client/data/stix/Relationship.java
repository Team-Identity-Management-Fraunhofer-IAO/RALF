package de.securityallies.taxii2.Taxii2Client.data.stix;

import java.util.List;

public class Relationship {
	private String id;
	private Identity created_by_ref;
	public final static String type = "relationship";
	private String modified;
	private String created;
	private String source_ref;
	private String target_ref;
	//optional attributes
	private String description;
	private String relationship_type;
	private List<MarkingDefinition> object_marking_refs;
	private List<Reference> external_references;
	
	public Relationship() {
		
	}
	
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
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getSource_ref() {
		return source_ref;
	}
	public void setSource_ref(String source_ref) {
		this.source_ref = source_ref;
	}
	public String getTarget_ref() {
		return target_ref;
	}
	public void setTarget_ref(String target_ref) {
		this.target_ref = target_ref;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRelationship_type() {
		return relationship_type;
	}
	public void setRelationship_type(String relationship_type) {
		this.relationship_type = relationship_type;
	}

	public List<MarkingDefinition> getObject_marking_refs() {
		return object_marking_refs;
	}

	public void setObject_marking_refs(List<MarkingDefinition> object_marking_refs) {
		this.object_marking_refs = object_marking_refs;
	}

	public List<Reference> getExternal_references() {
		return external_references;
	}

	public void setExternal_references(List<Reference> external_references) {
		this.external_references = external_references;
	}

	public String getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Relationship) {
			if (((Relationship) o).getId().equals(this.getId())){
				return true;
			}
			return false;
		}
		return false;
	}

}
