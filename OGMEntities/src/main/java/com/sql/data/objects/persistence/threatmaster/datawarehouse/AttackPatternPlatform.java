package com.sql.data.objects.persistence.threatmaster.datawarehouse;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="V_ATTACK_PATTERN_PLATFORM")
public class AttackPatternPlatform {
	private String id;
	private int attack_pattern_id;
	private Timestamp attack_pattern_loadTimestamp;
	private String attack_pattern_name;
	private String attack_pattern_created;
	private String attack_pattern_modified;
	private String attack_pattern_description;
	private String attack_pattern_x_mitre_version;
	private String attack_pattern_x_mitre_is_subtechnique;
	private String attack_pattern_x_mitre_detection;
	private boolean attack_pattern_revoked;
	private int x_mitre_platforms_id;
	private String x_mitre_platforms_collection_id;
	private Timestamp x_mitre_platforms_loadTimestamp;
	private String x_mitre_platforms_x_mitre_platform;

	public AttackPatternPlatform() {
		
	}

	@Id
	@Column(name="id", nullable=false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	public boolean isAttack_pattern_revoked() {
		return attack_pattern_revoked;
	}

	public void setAttack_pattern_revoked(boolean attack_pattern_revoked) {
		this.attack_pattern_revoked = attack_pattern_revoked;
	}

	@Column(name="x_mitre_platforms_id", nullable=false)
	public int getX_mitre_platforms_id() {
		return x_mitre_platforms_id;
	}

	public void setX_mitre_platforms_id(int x_mitre_platforms_id) {
		this.x_mitre_platforms_id = x_mitre_platforms_id;
	}

	@Column(name="x_mitre_platforms_collection_id", nullable=false)
	public String getX_mitre_platforms_collection_id() {
		return x_mitre_platforms_collection_id;
	}

	public void setX_mitre_platforms_collection_id(String x_mitre_platforms_collection_id) {
		this.x_mitre_platforms_collection_id = x_mitre_platforms_collection_id;
	}

	@Column(name="x_mitre_platforms_loadTimestamp", nullable=false)
	public Timestamp getX_mitre_platforms_loadTimestamp() {
		return x_mitre_platforms_loadTimestamp;
	}

	public void setX_mitre_platforms_loadTimestamp(Timestamp x_mitre_platforms_loadTimestamp) {
		this.x_mitre_platforms_loadTimestamp = x_mitre_platforms_loadTimestamp;
	}

	@Column(name="x_mitre_platforms_x_mitre_platform", nullable=false)
	public String getX_mitre_platforms_x_mitre_platform() {
		return x_mitre_platforms_x_mitre_platform;
	}

	public void setX_mitre_platforms_x_mitre_platform(String x_mitre_platforms_x_mitre_platform) {
		this.x_mitre_platforms_x_mitre_platform = x_mitre_platforms_x_mitre_platform;
	}
	
	
	
}
