package com.sql.data.objects.persistence.threatmaster.datawarehouse;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_COURSE_OF_ACTION_ATTACK_PATTERN")
public class CourseOfActionAttackPattern {
	private String id;
	private int course_of_action_id;
	private Timestamp course_of_action_timestamp;
	private String course_of_action_name;
	private String course_of_action_created;
	private String course_of_action_modified;
	private String course_of_action_description;
	private String course_of_action_x_mitre_version;
	private boolean course_of_action_x_mitre_deprecated;
	private String course_of_action_collection_id;
	private int attack_pattern_id;
	private Timestamp attack_pattern_loadTimestamp;
	private String attack_pattern_name;
	private String attack_pattern_created;
	private String attack_pattern_modified;
	private String attack_pattern_description;
	private String attack_pattern_x_mitre_version;
	private boolean attack_pattern_x_mitre_is_subtechnique;
	private String attack_pattern_x_mitre_detection;
	private boolean attack_pattern_revoked;
	private String attack_pattern_collection_id;
	private String relationship_description;
	
	public CourseOfActionAttackPattern() {
		
	}

	@Id
	@Column(name="id", nullable=false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="course_of_action_id", nullable=true)
	public int getCourse_of_action_id() {
		return course_of_action_id;
	}

	public void setCourse_of_action_id(int course_of_action_id) {
		this.course_of_action_id = course_of_action_id;
	}

	@Column(name="course_of_action_timestamp", nullable=true)
	public Timestamp getCourse_of_action_timestamp() {
		return course_of_action_timestamp;
	}

	public void setCourse_of_action_timestamp(Timestamp course_of_action_timestamp) {
		this.course_of_action_timestamp = course_of_action_timestamp;
	}

	@Column(name="course_of_action_name", nullable=true)
	public String getCourse_of_action_name() {
		return course_of_action_name;
	}

	public void setCourse_of_action_name(String course_of_action_name) {
		this.course_of_action_name = course_of_action_name;
	}

	@Column(name="course_of_action_created", nullable=true)
	public String getCourse_of_action_created() {
		return course_of_action_created;
	}

	public void setCourse_of_action_created(String course_of_action_created) {
		this.course_of_action_created = course_of_action_created;
	}

	@Column(name="course_of_action_modified", nullable=true)
	public String getCourse_of_action_modified() {
		return course_of_action_modified;
	}

	public void setCourse_of_action_modified(String course_of_action_modified) {
		this.course_of_action_modified = course_of_action_modified;
	}

	@Column(name="course_of_action_description", nullable=true)
	public String getCourse_of_action_description() {
		return course_of_action_description;
	}

	public void setCourse_of_action_description(String course_of_action_description) {
		this.course_of_action_description = course_of_action_description;
	}

	@Column(name="course_of_action_x_mitre_version", nullable=true)
	public String getCourse_of_action_x_mitre_version() {
		return course_of_action_x_mitre_version;
	}

	public void setCourse_of_action_x_mitre_version(String course_of_action_x_mitre_version) {
		this.course_of_action_x_mitre_version = course_of_action_x_mitre_version;
	}

	@Column(name="course_of_action_x_mitre_deprecated", nullable=true)
	public boolean isCourse_of_action_x_mitre_deprecated() {
		return course_of_action_x_mitre_deprecated;
	}

	public void setCourse_of_action_x_mitre_deprecated(boolean course_of_action_x_mitre_deprecated) {
		this.course_of_action_x_mitre_deprecated = course_of_action_x_mitre_deprecated;
	}

	@Column(name="course_of_action_collection_id", nullable=true)
	public String getCourse_of_action_collection_id() {
		return course_of_action_collection_id;
	}

	public void setCourse_of_action_collection_id(String course_of_action_collection_id) {
		this.course_of_action_collection_id = course_of_action_collection_id;
	}

	@Column(name="attack_pattern_id", nullable=true)
	public int getAttack_pattern_id() {
		return attack_pattern_id;
	}

	public void setAttack_pattern_id(int attack_pattern_id) {
		this.attack_pattern_id = attack_pattern_id;
	}

	@Column(name="attack_pattern_loadTimestamp", nullable=true)
	public Timestamp getAttack_pattern_loadTimestamp() {
		return attack_pattern_loadTimestamp;
	}

	public void setAttack_pattern_loadTimestamp(Timestamp attack_pattern_loadTimestamp) {
		this.attack_pattern_loadTimestamp = attack_pattern_loadTimestamp;
	}

	@Column(name="attack_pattern_name", nullable=true)
	public String getAttack_pattern_name() {
		return attack_pattern_name;
	}

	public void setAttack_pattern_name(String attack_pattern_name) {
		this.attack_pattern_name = attack_pattern_name;
	}

	@Column(name="attack_pattern_created", nullable=true)
	public String getAttack_pattern_created() {
		return attack_pattern_created;
	}

	public void setAttack_pattern_created(String attack_pattern_created) {
		this.attack_pattern_created = attack_pattern_created;
	}

	@Column(name="attack_pattern_modified", nullable=true)
	public String getAttack_pattern_modified() {
		return attack_pattern_modified;
	}

	public void setAttack_pattern_modified(String attack_pattern_modified) {
		this.attack_pattern_modified = attack_pattern_modified;
	}

	@Column(name="attack_pattern_description", nullable=true)
	public String getAttack_pattern_description() {
		return attack_pattern_description;
	}

	public void setAttack_pattern_description(String attack_pattern_description) {
		this.attack_pattern_description = attack_pattern_description;
	}

	@Column(name="attack_pattern_x_mitre_version", nullable=true)
	public String getAttack_pattern_x_mitre_version() {
		return attack_pattern_x_mitre_version;
	}

	public void setAttack_pattern_x_mitre_version(String attack_pattern_x_mitre_version) {
		this.attack_pattern_x_mitre_version = attack_pattern_x_mitre_version;
	}

	@Column(name="attack_pattern_x_mitre_is_subtechnique", nullable=true)
	public boolean isAttack_pattern_x_mitre_is_subtechnique() {
		return attack_pattern_x_mitre_is_subtechnique;
	}

	public void setAttack_pattern_x_mitre_is_subtechnique(boolean attack_pattern_x_mitre_is_subtechnique) {
		this.attack_pattern_x_mitre_is_subtechnique = attack_pattern_x_mitre_is_subtechnique;
	}

	@Column(name="attack_pattern_x_mitre_detection", nullable=true)
	public String getAttack_pattern_x_mitre_detection() {
		return attack_pattern_x_mitre_detection;
	}

	public void setAttack_pattern_x_mitre_detection(String attack_pattern_x_mitre_detection) {
		this.attack_pattern_x_mitre_detection = attack_pattern_x_mitre_detection;
	}

	@Column(name="attack_pattern_revoked", nullable=true)
	public boolean isAttack_pattern_revoked() {
		return attack_pattern_revoked;
	}

	public void setAttack_pattern_revoked(boolean attack_pattern_revoked) {
		this.attack_pattern_revoked = attack_pattern_revoked;
	}

	@Column(name="attack_pattern_collection_id", nullable=true)
	public String getAttack_pattern_collection_id() {
		return attack_pattern_collection_id;
	}

	public void setAttack_pattern_collection_id(String attack_pattern_collection_id) {
		this.attack_pattern_collection_id = attack_pattern_collection_id;
	}

	@Column(name="relationship_description", nullable=true)
	public String getRelationship_description() {
		return relationship_description;
	}

	public void setRelationship_description(String relationship_description) {
		this.relationship_description = relationship_description;
	}
	
	
	
	

}
