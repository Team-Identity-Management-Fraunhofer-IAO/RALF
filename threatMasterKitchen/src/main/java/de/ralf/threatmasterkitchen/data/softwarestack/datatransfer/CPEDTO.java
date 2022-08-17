package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CPEDTO{
	private String part;
	private String vendor;
	private String product;
	private String version;
	private String update;
	private String edition;
	private String sw_edition;
	private String language;
	private String target_sw;
	private String target_hw;
	private String other;
	
	public CPEDTO() {
		
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

	public String getSw_edition() {
		return sw_edition;
	}

	public void setSw_edition(String sw_edition) {
		this.sw_edition = sw_edition;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
	
	
	
}
