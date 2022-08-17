package de.securityallies.taxii2.Taxii2Client.data.stix;

import java.util.List;

public class AttackPattern {
	//Required attributes
	private String id;
	public final static String type = "attack-pattern";
	private String name;
	private String created;
	private String modified;
	//Optional attributes
	private List<Reference> external_references;
	private List<MarkingDefinition> object_marking_refs;
	private String created_by_ref;
	private String description;
	private List<KillChainPhase> kill_chain_phases;
	private List<String> x_mitre_contributors;
	private String x_mitre_version;
	private boolean x_mitre_is_subtechnique;
	private List<String> x_mitre_permissions_required;
	private String x_mitre_detection;
	private List<String> x_mitre_data_sources;
	private List<String> x_mitre_platforms;
	private List<String> x_mitre_effective_permissions;
	private List<String> x_mitre_defense_bypassed;
	private boolean revoked;
	
	public AttackPattern() {
		
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
	public List<Reference> getExternal_references() {
		return external_references;
	}
	public void setExternal_references(List<Reference> external_references) {
		this.external_references = external_references;
	}
	public List<MarkingDefinition> getObject_marking_refs() {
		return object_marking_refs;
	}
	public void setObject_marking_refs(List<MarkingDefinition> object_marking_refs) {
		this.object_marking_refs = object_marking_refs;
	}
	public String getCreated_by_ref() {
		return created_by_ref;
	}
	public void setCreated_by_ref(String created_by_ref) {
		this.created_by_ref = created_by_ref;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<KillChainPhase> getKill_chain_phases() {
		return kill_chain_phases;
	}
	public void setKill_chain_phases(List<KillChainPhase> kill_chain_phases) {
		this.kill_chain_phases = kill_chain_phases;
	}
	public List<String> getX_mitre_contributors() {
		return x_mitre_contributors;
	}
	public void setX_mitre_contributors(List<String> x_mitre_contributors) {
		this.x_mitre_contributors = x_mitre_contributors;
	}
	public String getX_mitre_version() {
		return x_mitre_version;
	}
	public void setX_mitre_version(String x_mitre_version) {
		this.x_mitre_version = x_mitre_version;
	}

	public List<String> getX_mitre_permissions_required() {
		return x_mitre_permissions_required;
	}
	public void setX_mitre_permissions_required(List<String> x_mitre_permissions_required) {
		this.x_mitre_permissions_required = x_mitre_permissions_required;
	}
	public String getX_mitre_detection() {
		return x_mitre_detection;
	}
	public void setX_mitre_detection(String x_mitre_detection) {
		this.x_mitre_detection = x_mitre_detection;
	}
	public List<String> getX_mitre_data_sources() {
		return x_mitre_data_sources;
	}
	public void setX_mitre_data_sources(List<String> x_mitre_data_sources) {
		this.x_mitre_data_sources = x_mitre_data_sources;
	}
	public List<String> getX_mitre_platforms() {
		return x_mitre_platforms;
	}
	public void setX_mitre_platforms(List<String> x_mitre_platforms) {
		this.x_mitre_platforms = x_mitre_platforms;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}	
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof AttackPattern) {
			if (((AttackPattern) o).getId().equals(this.getId())){
				return true;
			}
			return false;
		}
		return false;
	}

	public List<String> getX_mitre_defense_bypassed() {
		return x_mitre_defense_bypassed;
	}

	public void setX_mitre_defense_bypassed(List<String> x_mitre_defense_bypassed) {
		this.x_mitre_defense_bypassed = x_mitre_defense_bypassed;
	}

	public List<String> getX_mitre_effective_permissions() {
		return x_mitre_effective_permissions;
	}

	public void setX_mitre_effective_permissions(List<String> x_mitre_effective_permissions) {
		this.x_mitre_effective_permissions = x_mitre_effective_permissions;
	}

	public boolean isX_mitre_is_subtechnique() {
		return x_mitre_is_subtechnique;
	}

	public void setX_mitre_is_subtechnique(boolean x_mitre_is_subtechnique) {
		this.x_mitre_is_subtechnique = x_mitre_is_subtechnique;
	}
	
}
