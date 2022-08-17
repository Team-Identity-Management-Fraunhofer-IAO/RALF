package com.sql.data.objects.persistence.facts;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sql.data.objects.persistence.platforms.CPECore;
import com.sql.data.objects.persistence.platforms.ContainsPlatform;
import com.sql.data.objects.persistence.vulnerabilities.CVECore;

@Entity
@Table(name="vulnerablePlatforms")
public class VulnerablePlatformsFact implements Serializable{
	int factID;
	int cpeID;
	int cveYear;
	int cveID;
	int combinationID;
	String part;
	String vendor;
	String product;
	String version;
	String cpe_update;
	String edition;
	String cpe_language;
	String sw_edition;
	String target_sw;
	String target_hw;
	String other;
	String cpe_versionEnd;
	boolean include;
	String URI;
	boolean standardized;
	String identifierString;
	String vectorString;
	String attackVector;
	String attackComplexity;
	String privilegesRequired;
	String userInteraction;
	String scope;
	String confidentialityImpact;
	String integrityImpact;
	String availabilityImpact;
	float baseScore;
	String baseSeverity;
	float exploitabilityScore;
	float impactScore;
	boolean vulnerable;
	String versionStart;
	String versionEnd;
	boolean startIncluding;
	boolean endIncluding;
	
	public VulnerablePlatformsFact() {
		
	}	
	
	public VulnerablePlatformsFact(int factID, int cpeID, int cveYear, int cveID, int combinationID, String part,
			String vendor, String product, String version, String cpe_update, String edition, String cpe_language,
			String sw_edition, String target_sw, String target_hw, String other, String cpe_versionEnd, boolean include,
			String uRI, boolean standardized, String identifierString, String vectorString, String attackVector,
			String attackComplexity, String privilegesRequired, String userInteraction, String scope,
			String confidentialityImpact, String integrityImpact, String availabilityImpact, float baseScore,
			String baseSeverity, float exploitabilityScore, float impactScore, boolean vulnerable, String versionStart,
			String versionEnd, boolean startIncluding, boolean endIncluding) {
		super();
		this.factID = factID;
		this.cpeID = cpeID;
		this.cveYear = cveYear;
		this.cveID = cveID;
		this.combinationID = combinationID;
		this.part = part;
		this.vendor = vendor;
		this.product = product;
		this.version = version;
		this.cpe_update = cpe_update;
		this.edition = edition;
		this.cpe_language = cpe_language;
		this.sw_edition = sw_edition;
		this.target_sw = target_sw;
		this.target_hw = target_hw;
		this.other = other;
		this.cpe_versionEnd = cpe_versionEnd;
		this.include = include;
		URI = uRI;
		this.standardized = standardized;
		this.identifierString = identifierString;
		this.vectorString = vectorString;
		this.attackVector = attackVector;
		this.attackComplexity = attackComplexity;
		this.privilegesRequired = privilegesRequired;
		this.userInteraction = userInteraction;
		this.scope = scope;
		this.confidentialityImpact = confidentialityImpact;
		this.integrityImpact = integrityImpact;
		this.availabilityImpact = availabilityImpact;
		this.baseScore = baseScore;
		this.baseSeverity = baseSeverity;
		this.exploitabilityScore = exploitabilityScore;
		this.impactScore = impactScore;
		this.vulnerable = vulnerable;
		this.versionStart = versionStart;
		this.versionEnd = versionEnd;
		this.startIncluding = startIncluding;
		this.endIncluding = endIncluding;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="factID", nullable=false)
	public int getFactID() {
		return factID;
	}
	public void setFactID(int factID) {
		this.factID = factID;
	}
	@Column(name="cpeID",nullable=false)
	public int getCpeID() {
		return cpeID;
	}
	public void setCpeID(int cpeID) {
		this.cpeID = cpeID;
	}
	@Column(name="cveYear",nullable=false)
	public int getCveYear() {
		return cveYear;
	}
	public void setCveYear(int cveYear) {
		this.cveYear = cveYear;
	}
	@Column(name="cveID",nullable=false)
	public int getCveID() {
		return cveID;
	}
	public void setCveID(int cveID) {
		this.cveID = cveID;
	}
	@Column(name="combinationID",nullable=false)
	public int getCombinationID() {
		return combinationID;
	}
	public void setCombinationID(int combinationID) {
		this.combinationID = combinationID;
	}
	@Column(name="part",nullable=true)
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	@Column(name="vendor",nullable=true)
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	@Column(name="product",nullable=true)
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	@Column(name="version",nullable=true)
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Column(name="cpe_update",nullable=true)
	public String getCpe_update() {
		return cpe_update;
	}
	public void setCpe_update(String cpe_update) {
		this.cpe_update = cpe_update;
	}
	@Column(name="edition",nullable=true)
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	@Column(name="cpe_language",nullable=true)
	public String getCpe_language() {
		return cpe_language;
	}
	public void setCpe_language(String cpe_language) {
		this.cpe_language = cpe_language;
	}
	@Column(name="sw_edition",nullable=true)
	public String getSw_edition() {
		return sw_edition;
	}
	public void setSw_edition(String sw_edition) {
		this.sw_edition = sw_edition;
	}
	@Column(name="target_sw",nullable=true)
	public String getTarget_sw() {
		return target_sw;
	}
	public void setTarget_sw(String target_sw) {
		this.target_sw = target_sw;
	}
	@Column(name="target_hw",nullable=true)
	public String getTarget_hw() {
		return target_hw;
	}
	public void setTarget_hw(String target_hw) {
		this.target_hw = target_hw;
	}
	@Column(name="other",nullable=true)
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	@Column(name="cpe_versionEnd",nullable=true)
	public String getCpe_versionEnd() {
		return cpe_versionEnd;
	}
	public void setCpe_versionEnd(String cpe_versionEnd) {
		this.cpe_versionEnd = cpe_versionEnd;
	}
	@Column(name="include",nullable=true)
	public boolean isInclude() {
		return include;
	}
	public void setInclude(boolean include) {
		this.include = include;
	}
	@Column(name="URI",nullable=false)
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	@Column(name="standardized",nullable=true)
	public boolean isStandardized() {
		return standardized;
	}
	public void setStandardized(boolean standardized) {
		this.standardized = standardized;
	}
	@Column(name="identifierString",nullable=true)
	public String getIdentifierString() {
		return identifierString;
	}
	public void setIdentifierString(String identifierString) {
		this.identifierString = identifierString;
	}
	@Column(name="vectorString",nullable=true)
	public String getVectorString() {
		return vectorString;
	}
	public void setVectorString(String vectorString) {
		this.vectorString = vectorString;
	}
	@Column(name="attackVector",nullable=true)
	public String getAttackVector() {
		return attackVector;
	}
	public void setAttackVector(String attackVector) {
		this.attackVector = attackVector;
	}
	@Column(name="attackComplexity",nullable=true)
	public String getAttackComplexity() {
		return attackComplexity;
	}
	public void setAttackComplexity(String attackComplexity) {
		this.attackComplexity = attackComplexity;
	}
	@Column(name="privilegesRequired",nullable=true)
	public String getPrivilegesRequired() {
		return privilegesRequired;
	}
	public void setPrivilegesRequired(String privilegesRequired) {
		this.privilegesRequired = privilegesRequired;
	}
	@Column(name="userInteraction",nullable=true)
	public String getUserInteraction() {
		return userInteraction;
	}
	public void setUserInteraction(String userInteraction) {
		this.userInteraction = userInteraction;
	}
	@Column(name="scope",nullable=true)
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Column(name="confidentialityImpact",nullable=true)
	public String getConfidentialityImpact() {
		return confidentialityImpact;
	}
	public void setConfidentialityImpact(String confidentialityImpact) {
		this.confidentialityImpact = confidentialityImpact;
	}
	@Column(name="integrityImpact",nullable=true)
	public String getIntegrityImpact() {
		return integrityImpact;
	}
	public void setIntegrityImpact(String integrityImpact) {
		this.integrityImpact = integrityImpact;
	}
	@Column(name="availabilityImpact",nullable=true)
	public String getAvailabilityImpact() {
		return availabilityImpact;
	}
	public void setAvailabilityImpact(String availabilityImpact) {
		this.availabilityImpact = availabilityImpact;
	}
	@Column(name="baseScore",nullable=true)
	public float getBaseScore() {
		return baseScore;
	}
	public void setBaseScore(float baseScore) {
		this.baseScore = baseScore;
	}
	@Column(name="baseSeverity",nullable=true)
	public String getBaseSeverity() {
		return baseSeverity;
	}
	public void setBaseSeverity(String baseSeverity) {
		this.baseSeverity = baseSeverity;
	}
	@Column(name="exploitabilityScore",nullable=true)
	public float getExploitabilityScore() {
		return exploitabilityScore;
	}
	public void setExploitabilityScore(float exploitabilityScore) {
		this.exploitabilityScore = exploitabilityScore;
	}
	@Column(name="impactScore",nullable=true)
	public float getImpactScore() {
		return impactScore;
	}
	public void setImpactScore(float impactScore) {
		this.impactScore = impactScore;
	}
	@Column(name="vulnerable",nullable=true)
	public boolean isVulnerable() {
		return vulnerable;
	}
	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}
	@Column(name="versionStart",nullable=true)
	public String getVersionStart() {
		return versionStart;
	}
	public void setVersionStart(String versionStart) {
		this.versionStart = versionStart;
	}
	@Column(name="versionEnd",nullable=true)
	public String getVersionEnd() {
		return versionEnd;
	}
	public void setVersionEnd(String versionEnd) {
		this.versionEnd = versionEnd;
	}
	@Column(name="startIncluding",nullable=true)
	public boolean isStartIncluding() {
		return startIncluding;
	}
	public void setStartIncluding(boolean startIncluding) {
		this.startIncluding = startIncluding;
	}
	@Column(name="endIncluding",nullable=true)
	public boolean isEndIncluding() {
		return endIncluding;
	}
	public void setEndIncluding(boolean endIncluding) {
		this.endIncluding = endIncluding;
	}

	public void parseCPE(CPECore cpe) {
		this.cpeID = cpe.getCpeID();
		this.part = cpe.getPart();
		this.vendor = cpe.getVendor();
		this.product = cpe.getProduct();
		this.version = cpe.getVersion();
		this.cpe_update = cpe.getCpe_update();
		this.edition = cpe.getEdition();
		this.cpe_language = cpe.getCpe_language();
		this.sw_edition = cpe.getSw_edition();
		this.target_sw = cpe.getTarget_sw();
		this.target_hw = cpe.getTarget_hw();
		this.other = cpe.getOther();
		this.cpe_versionEnd = cpe.getVersionEND();
		this.include = cpe.isInclude();
		this.URI = cpe.getURI();
		this.standardized = cpe.isStandardized();
	}
	
	public void parseCVE(CVECore cve) {
		this.cveYear = cve.getCveYear();
		this.cveID = cve.getCveID();
		this.identifierString = cve.getIdentifierString();
		this.vectorString = cve.getVectorString();
		this.attackVector = cve.getAttackVector();
		this.attackComplexity = cve.getAttackComplexity();
		this.privilegesRequired = cve.getPrivilegesRequired();
		this.userInteraction = cve.getUserInteraction();
		this.scope = cve.getScope();
		this.confidentialityImpact = cve.getConfidentialityImpact();
		this.integrityImpact = cve.getIntegrityImpact();
		this.availabilityImpact = cve.getAvailabilityImpact();
		this.baseScore = cve.getBaseScore();
		this.baseSeverity = cve.getBaseSeverity();
		this.exploitabilityScore = cve.getExploitabilityScore();
		this.impactScore = cve.getImpactScore();
	}
	
	public void parseContainsPlatform(ContainsPlatform comb) {
		this.combinationID = comb.getCombinationID();
		this.vulnerable = comb.isVulnerable();
		this.versionStart = comb.getVersionStart();
		this.versionEnd = comb.getVersionEnd();
		this.startIncluding = comb.isStartIncluding();
		this.endIncluding = comb.isEndIncluding();
	}
	
	
}
