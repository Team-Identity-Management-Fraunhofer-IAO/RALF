package com.neo4j.data.objects.software.persistence;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

/**
 * 
 * POJO for Software Stack Component
 *
 */
@NodeEntity(label="SWComponent")
public class Component {
	@Id @GeneratedValue
	private Long id;
	
	@Property(name="cpeID")
	private Long cpeID;
	@Property(name="part")
	private String part;
	@Property(name="vendor")
	private String vendor;
	@Property(name="product")
	private String product;
	@Property(name="version")
	private String version;
	@Property(name="update")
	private String update;
	@Property(name="edition")
	private String edition;
	@Property(name="language")
	private String language;
	@Property(name="sw_edition")
	private String sw_edition;
	@Property(name="target_sw")
	private String target_sw;
	@Property(name="target_hw")
	private String target_hw;
	@Property(name="other")
	private String other;
	@Property(name="codebase")
	private boolean isCodebase;
	@Property(name="section")
	private String section;
	
	/*
	 * Values lt (<), lteq (<=), eq (=), gt (>), gteq (>)
	 */
	@Property(name="versionComparator")
	private String versionCmp;
	
	@Relationship(type = "requires", direction = Relationship.OUTGOING)
    private Set<Component> requiredComponents;
	
	public Component() {
		this.cpeID = Long.valueOf(-1);
		this.part = "";
		this.vendor = "";
		this.product = "";
		this.version = "";
		this.update = "";
		this.edition = "";
		this.language = "";
		this.sw_edition = "";
		this.target_sw = "";
		this.target_hw = "";
		this.other = "";
		this.versionCmp = "=";
		this.isCodebase = false;
		this.requiredComponents = new HashSet<Component>();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getCpeID() {
		return cpeID;
	}

	public void setCpeID(long cpeID) {
		this.cpeID = cpeID;
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

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSw_edition() {
		return sw_edition;
	}

	public void setSw_edition(String sw_edition) {
		this.sw_edition = sw_edition;
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

	public boolean isCodebase() {
		return isCodebase;
	}

	public void setCodebase(boolean isCodebase) {
		this.isCodebase = isCodebase;
	}

	public Set<Component> getRequiredComponents() {
		return requiredComponents;
	}

	public void setRequiredComponents(Set<Component> requiredComponents) {
		this.requiredComponents = requiredComponents;
	}
	
	public String getVersionCmp() {
		return versionCmp;
	}

	public void setVersionCmp(String versionCmp) {
		this.versionCmp = versionCmp;
	}
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public boolean equals(Object obj) {
		if (obj.getClass() == Component.class) {
			Component comp = (Component) obj;
			if (comp.getId().equals(this.getId())){
				return true;
			}else {
				return false;
			}
		}else if (this == obj){
			return true;
		}else {
			return false;
		}
		
	}
	
}
