package de.securityallies.taxii2.Taxii2Client.data.stix;

import java.util.List;
import java.util.Map;

public class CourseOfAction {
	//Required Attributes
	private String id;
	public final static String type = "course-of-action";
	private String name;
	private String created;
	private String modified;
	
	//Optional Attributes
	private Identity created_by_ref;
	private String description;
	private List<MarkingDefinition> object_marking_refs;
	private List<Reference> external_references;
	
	//Vendor specific attributes
	private String x_mitre_version;
	private boolean x_mitre_deprecated = false;
	
	//Vendor specific attributes map
	private Map<String, String> vendorAttributes;
	
	public CourseOfAction() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Identity getCreated_by_ref() {
		return created_by_ref;
	}

	public void setCreated_by_ref(Identity created_by_ref) {
		this.created_by_ref = created_by_ref;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getVendorAttributes() {
		return vendorAttributes;
	}

	public void setVendorAttributes(Map<String, String> vendorAttributes) {
		this.vendorAttributes = vendorAttributes;
	}

	public String getType() {
		return type;
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

	public String getX_mitre_version() {
		return x_mitre_version;
	}

	public void setX_mitre_version(String x_mitre_version) {
		this.x_mitre_version = x_mitre_version;
	}

	public boolean isX_mitre_deprecated() {
		return x_mitre_deprecated;
	}

	public void setX_mitre_deprecated(boolean x_mitre_deprecated) {
		this.x_mitre_deprecated = x_mitre_deprecated;
	}	
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof CourseOfAction) {
			if (((CourseOfAction) o).getId().equals(this.getId())){
				return true;
			}
			return false;
		}
		return false;
	}

}
