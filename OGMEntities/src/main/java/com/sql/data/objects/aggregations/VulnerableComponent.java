 package com.sql.data.objects.aggregations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;

import com.sql.data.objects.helper.HTMLSanitized;
import com.sql.data.objects.persistence.attackpatterns.CAPECCore;



public class VulnerableComponent implements HTMLSanitized {
	private Long swStackID;
	private Long componentID;
	private String part;
	private String vendor;
	private String product;
	private String version;
	private String comp_update;
	private String edition;
	private String sw_edition;
	private String comp_language;
	private String target_sw;
	private String target_hw;
	private String other;
	private List<ComponentVulnerability> componentVulnerabilities;
	private List<CAPECCore> attackPatterns;
	private List<AttackVector> vectors;
	
	public VulnerableComponent() {
		this.componentVulnerabilities = new ArrayList<ComponentVulnerability>();
		this.attackPatterns = new ArrayList<CAPECCore>();
		this.vectors = new ArrayList<AttackVector>();
	}
	
	public Long getComponentID() {
		return componentID;
	}

	public void setComponentID(Long componentID) {
		this.componentID = componentID;
	}

	public Long getSwStackID() {
		return swStackID;
	}

	public void setSwStackID(Long swStackID) {
		this.swStackID = swStackID;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getComp_update() {
		return comp_update;
	}

	public void setComp_update(String comp_update) {
		this.comp_update = comp_update;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getSw_edition() {
		return sw_edition;
	}

	public void setSw_edition(String sw_edition) {
		this.sw_edition = sw_edition;
	}

	public String getComp_language() {
		return comp_language;
	}

	public void setComp_language(String comp_language) {
		this.comp_language = comp_language;
	}

	public String getTarget_sw() {
		return target_sw;
	}

	public void setTarget_sw(String target_sw) {
		this.target_sw = target_sw;
	}

	public String getTarget_hw() {
		return target_hw;
	}

	public void setTarget_hw(String target_hw) {
		this.target_hw = target_hw;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public List<ComponentVulnerability> getComponentVulnerabilities() {
		return componentVulnerabilities;
	}

	public void setComponentVulnerabilities(List<ComponentVulnerability> componentVulnerabilities) {
		this.componentVulnerabilities = componentVulnerabilities;
	}

	public List<CAPECCore> getAttackPatterns() {
		return attackPatterns;
	}

	public void setAttackPatterns(List<CAPECCore> attackPatterns) {
		this.attackPatterns = attackPatterns;
	}

	public List<AttackVector> getVectors() {
		return vectors;
	}

	public void setVectors(List<AttackVector> vectors) {
		this.vectors = vectors;
	}

	@Override
	public void sanitizeHTMLStrings() {
		/*
		 * private String part;
	private String vendor;
	private String product;
	private String version;
	private String comp_update;
	private String edition;
	private String sw_edition;
	private String comp_language;
	private String target_sw;
	private String target_hw;
	private String other;
	private List<ComponentVulnerability> componentVulnerabilities;
	private List<CAPECCore> attackPatterns;
		 */
		this.part = StringEscapeUtils.escapeHtml4(this.part);
		this.product = StringEscapeUtils.escapeHtml4(this.product);
		this.version = StringEscapeUtils.escapeHtml4(this.version);
		this.comp_update = StringEscapeUtils.escapeHtml4(this.comp_update);
		this.edition = StringEscapeUtils.escapeHtml4(this.edition);
		this.sw_edition = StringEscapeUtils.escapeHtml4(this.sw_edition);
		this.comp_language = StringEscapeUtils.escapeHtml4(this.comp_language);
		this.target_sw = StringEscapeUtils.escapeHtml4(this.target_sw);
		this.target_hw = StringEscapeUtils.escapeHtml4(this.target_hw);
		this.other = StringEscapeUtils.escapeHtml4(this.other);
		this.vendor = StringEscapeUtils.escapeHtml4(this.vendor);
		
		for (ComponentVulnerability cV : componentVulnerabilities) {
			cV.setAttackComplexity(escape(cV.getAttackComplexity()));
			cV.setAttackVector(escape(cV.getAttackVector()));
			cV.setAvailabilityImpact(escape(cV.getAvailabilityImpact()));
			cV.setBaseSeverity(escape(cV.getBaseSeverity()));
			cV.setConfidentialityImpact(escape(cV.getConfidentialityImpact()));
			cV.setDescriptionText(escape(cV.getDescriptionText()));
			cV.setIdentifierString(escape(cV.getIdentifierString()));
			cV.setIntegrityImpact(escape(cV.getIntegrityImpact()));
			cV.setPrivilegesRequired(escape(cV.getPrivilegesRequired()));
			cV.setScope(escape(cV.getScope()));
			cV.setUri(escape(cV.getUri()));
			cV.setUserInteraction(escape(cV.getUserInteraction()));
			cV.setVectorString(escape(cV.getVectorString()));
		}
		for (CAPECCore capec : attackPatterns) {
			capec.setDescription(escape(capec.getDescription()));
			capec.setLikelihood(escape(capec.getLikelihood()));
			capec.setName(escape(capec.getName()));
			capec.setSeverity(escape(capec.getSeverity()));
		}
	}
	
	private String escape(String unescapedString) {
		return StringEscapeUtils.escapeHtml4(unescapedString);
	}

	
}
