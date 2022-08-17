package com.sql.data.objects.persistence.threatmaster.datawarehouse;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_INTRUSION_SET_ATTACK_PATTERN")
public class IntrusionSetAttackPattern {
	private String id;
	private int intrusion_set_id;
	private Timestamp intrusion_set_loadTimestamp;
	private String intrusion_set_name;
	private String intrusion_set_created;
	private String intrusion_set_modified;
	private String intrusion_set_description;
	private String intrusion_set_x_mitre_version;
	private String intrusion_set_collection_id;
	private int attack_pattern_id;
	private Timestamp attack_pattern_loadTimestamp;
	private String attack_pattern_name;
	private String attack_pattern_created;
	private String attack_pattern_modified;
	private String attack_pattern_description;
	private String attack_pattern_x_mitre_version;
	private String attack_pattern_x_mitre_is_subtechnique;
	private String attack_pattern_x_mitre_detection;
	private String attack_pattern_revoked;
	private String attack_pattern_collection_id;
	
	public IntrusionSetAttackPattern() {
		
	}

	@Id
	@Column(name="id", nullable=false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="intrusion_set_id", nullable=false)
	public int getIntrusion_set_id() {
		return intrusion_set_id;
	}

	public void setIntrusion_set_id(int intrusion_set_id) {
		this.intrusion_set_id = intrusion_set_id;
	}

	@Column(name="intrusion_set_loadTimestamp", nullable=false)
	public Timestamp getIntrusion_set_loadTimestamp() {
		return intrusion_set_loadTimestamp;
	}

	public void setIntrusion_set_loadTimestamp(Timestamp intrusion_set_loadTimestamp) {
		this.intrusion_set_loadTimestamp = intrusion_set_loadTimestamp;
	}

	@Column(name="intrusion_set_name", nullable=false)
	public String getIntrusion_set_name() {
		return intrusion_set_name;
	}

	public void setIntrusion_set_name(String intrusion_set_name) {
		this.intrusion_set_name = intrusion_set_name;
	}

	@Column(name="intrusion_set_created", nullable=false)
	public String getIntrusion_set_created() {
		return intrusion_set_created;
	}

	public void setIntrusion_set_created(String intrusion_set_created) {
		this.intrusion_set_created = intrusion_set_created;
	}

	@Column(name="intrusion_set_modified", nullable=false)
	public String getIntrusion_set_modified() {
		return intrusion_set_modified;
	}

	public void setIntrusion_set_modified(String intrusion_set_modified) {
		this.intrusion_set_modified = intrusion_set_modified;
	}

	@Column(name="intrusion_set_description", nullable=false)
	public String getIntrusion_set_description() {
		return intrusion_set_description;
	}

	public void setIntrusion_set_description(String intrusion_set_description) {
		this.intrusion_set_description = intrusion_set_description;
	}

	@Column(name="intrusion_set_x_mitre_version", nullable=false)
	public String getIntrusion_set_x_mitre_version() {
		return intrusion_set_x_mitre_version;
	}

	public void setIntrusion_set_x_mitre_version(String intrusion_set_x_mitre_version) {
		this.intrusion_set_x_mitre_version = intrusion_set_x_mitre_version;
	}

	@Column(name="intrusion_set_collection_id", nullable=false)
	public String getIntrusion_set_collection_id() {
		return intrusion_set_collection_id;
	}

	public void setIntrusion_set_collection_id(String intrusion_set_collection_id) {
		this.intrusion_set_collection_id = intrusion_set_collection_id;
	}

	@Column(name="attack_pattern_id", nullable=false)
	public int getAttack_pattern_id() {
		return attack_pattern_id;
	}

	public void setAttack_pattern_id(int attack_pattern_id) {
		this.attack_pattern_id = attack_pattern_id;
	}

	@Column(name="attack_pattern_loadTimestamp", nullable=false)
	public Timestamp getAttack_pattern_loadTimestamp() {
		return attack_pattern_loadTimestamp;
	}

	public void setAttack_pattern_loadTimestamp(Timestamp attack_pattern_loadTimestamp) {
		this.attack_pattern_loadTimestamp = attack_pattern_loadTimestamp;
	}

	@Column(name="attack_pattern_name", nullable=false)
	public String getAttack_pattern_name() {
		return attack_pattern_name;
	}

	public void setAttack_pattern_name(String attack_pattern_name) {
		this.attack_pattern_name = attack_pattern_name;
	}

	@Column(name="attack_pattern_created", nullable=false)
	public String getAttack_pattern_created() {
		return attack_pattern_created;
	}

	public void setAttack_pattern_created(String attack_pattern_created) {
		this.attack_pattern_created = attack_pattern_created;
	}

	@Column(name="attack_pattern_modified", nullable=false)
	public String getAttack_pattern_modified() {
		return attack_pattern_modified;
	}

	public void setAttack_pattern_modified(String attack_pattern_modified) {
		this.attack_pattern_modified = attack_pattern_modified;
	}

	@Column(name="attack_pattern_description", nullable=false)
	public String getAttack_pattern_description() {
		return attack_pattern_description;
	}

	public void setAttack_pattern_description(String attack_pattern_description) {
		this.attack_pattern_description = attack_pattern_description;
	}

	@Column(name="attack_pattern_x_mitre_version", nullable=false)
	public String getAttack_pattern_x_mitre_version() {
		return attack_pattern_x_mitre_version;
	}

	public void setAttack_pattern_x_mitre_version(String attack_pattern_x_mitre_version) {
		this.attack_pattern_x_mitre_version = attack_pattern_x_mitre_version;
	}

	@Column(name="attack_pattern_x_mitre_is_subtechnique", nullable=false)
	public String getAttack_pattern_x_mitre_is_subtechnique() {
		return attack_pattern_x_mitre_is_subtechnique;
	}

	public void setAttack_pattern_x_mitre_is_subtechnique(String attack_pattern_x_mitre_is_subtechnique) {
		this.attack_pattern_x_mitre_is_subtechnique = attack_pattern_x_mitre_is_subtechnique;
	}

	@Column(name="attack_pattern_x_mitre_detection", nullable=false)
	public String getAttack_pattern_x_mitre_detection() {
		return attack_pattern_x_mitre_detection;
	}

	public void setAttack_pattern_x_mitre_detection(String attack_pattern_x_mitre_detection) {
		this.attack_pattern_x_mitre_detection = attack_pattern_x_mitre_detection;
	}

	@Column(name="attack_pattern_revoked", nullable=false)
	public String getAttack_pattern_revoked() {
		return attack_pattern_revoked;
	}

	public void setAttack_pattern_revoked(String attack_pattern_revoked) {
		this.attack_pattern_revoked = attack_pattern_revoked;
	}

	@Column(name="attack_pattern_collection_id", nullable=false)
	public String getAttack_pattern_collection_id() {
		return attack_pattern_collection_id;
	}

	public void setAttack_pattern_collection_id(String attack_pattern_collection_id) {
		this.attack_pattern_collection_id = attack_pattern_collection_id;
	}
	
}
