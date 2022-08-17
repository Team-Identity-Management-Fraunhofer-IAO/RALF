package com.sql.data.objects.persistence.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.text.StringEscapeUtils;

import com.sql.data.objects.persistence.attackpatterns.CAPECCore;

@Entity
@IdClass(AttackPatternPK.class)
@Table(name="attackPattern")
public class AttackPattern {
	private int CAPECID;
	private int vulnCompID;
	private String name;
	private String description;
	private String likelihood;
	private String severity;
	private String attackVector;
	
	public AttackPattern() {
		
	}

	@Id
	@Column(name="CAPECID", nullable=false)
	public int getCAPECID() {
		return CAPECID;
	}

	public void setCAPECID(int cAPECID) {
		CAPECID = cAPECID;
	}

	@Id
	@Column(name="vulnCompID", nullable=false)
	public int getVulnCompID() {
		return vulnCompID;
	}

	public void setVulnCompID(int vulnCompID) {
		this.vulnCompID = vulnCompID;
	}
	
	@Id
	@Column(name="attackVector", nullable=false)
	public String getAttackVector() {
		return attackVector;
	}

	public void setAttackVector(String attackVector) {
		this.attackVector = escape(attackVector);
	}

	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = escape(name);
	}

	@Column(name="description", nullable=false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = escape(description);
	}

	@Column(name="likelihood", nullable=false)
	public String getLikelihood() {
		return likelihood;
	}

	public void setLikelihood(String likelihood) {
		this.likelihood = escape(likelihood);
	}

	@Column(name="severity", nullable=false)
	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = escape(severity);
	}
	
	public void constructFromCAPEC(CAPECCore capec) {
		this.description = capec.getDescription();
		this.likelihood = capec.getLikelihood()==null?"":capec.getLikelihood();
		this.name = capec.getName();
		this.severity = capec.getSeverity()==null?"":capec.getSeverity();
		this.CAPECID = capec.getId();
	}

	private String escape(String unescapedString) {
		return StringEscapeUtils.escapeHtml4(unescapedString);
	}
}
