package de.securityallies.taxii2.Taxii2Client.data.stix;

import java.util.List;

import de.securityallies.taxii2.Taxii2Client.data.stix.MarkingDefinition;

public class Identity {
	private String id;
	private String created;
	private String modified;
	public final static String type = "identity";
	private String name;
	//optional
	private List<MarkingDefinition> object_marking_refs;
	private String description;
	private String roles;
	private String identity_class;
	private String sectors;
	private String contact_information;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public List<MarkingDefinition> getObject_marking_refs() {
		return object_marking_refs;
	}
	public void setObject_marking_refs(List<MarkingDefinition> object_marking_refs) {
		this.object_marking_refs = object_marking_refs;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getIdentity_class() {
		return identity_class;
	}
	public void setIdentity_class(String identity_class) {
		this.identity_class = identity_class;
	}
	public String getSectors() {
		return sectors;
	}
	public void setSectors(String sectors) {
		this.sectors = sectors;
	}
	public String getContact_information() {
		return contact_information;
	}
	public void setContact_information(String contact_information) {
		this.contact_information = contact_information;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Identity) {
			if (((Identity) o).getId().equals(this.getId())){
				return true;
			}
			return false;
		}
		return false;
	}

}
