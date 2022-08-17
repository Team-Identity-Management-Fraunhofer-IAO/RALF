package com.sql.data.objects.persistence.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vulnerableComponent")
public class VulnerableComponent {

	private int vulnCompID;
	private int swStackID;
	private int componentID;
	private int reportID;
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
	private String URI;

	public VulnerableComponent() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vulnCompID", nullable = false)
	public int getVulnCompID() {
		return vulnCompID;
	}

	public void setVulnCompID(int vulnCompID) {
		this.vulnCompID = vulnCompID;
	}

	@Column(name = "reportID", nullable = false)
	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	@Column(name = "swStackID", nullable = false)
	public int getSwStackID() {
		return swStackID;
	}

	public void setSwStackID(int swStackID) {
		this.swStackID = swStackID;
	}

	@Column(name = "componentID", nullable = false)
	public int getComponentID() {
		return componentID;
	}

	public void setComponentID(int componentID) {
		this.componentID = componentID;
	}

	@Column(name = "part", nullable = false)
	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	@Column(name = "vendor", nullable = false)
	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Column(name = "product", nullable = false)
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Column(name = "version", nullable = false)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "comp_update", nullable = false)
	public String getComp_update() {
		return comp_update;
	}

	public void setComp_update(String comp_update) {
		this.comp_update = comp_update;
	}

	@Column(name = "edition", nullable = false)
	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	@Column(name = "sw_edition", nullable = false)
	public String getSw_edition() {
		return sw_edition;
	}

	public void setSw_edition(String sw_edition) {
		this.sw_edition = sw_edition;
	}

	@Column(name = "comp_language", nullable = false)
	public String getComp_language() {
		return comp_language;
	}

	public void setComp_language(String comp_language) {
		this.comp_language = comp_language;
	}

	@Column(name = "target_sw", nullable = false)
	public String getTarget_sw() {
		return target_sw;
	}

	public void setTarget_sw(String target_sw) {
		this.target_sw = target_sw;
	}

	@Column(name = "target_hw", nullable = false)
	public String getTarget_hw() {
		return target_hw;
	}

	public void setTarget_hw(String target_hw) {
		this.target_hw = target_hw;
	}

	@Column(name = "other", nullable = false)
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	@Column(name="URI", nullable=false)
	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	
}
