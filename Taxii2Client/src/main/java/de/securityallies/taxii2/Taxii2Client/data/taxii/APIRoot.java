package de.securityallies.taxii2.Taxii2Client.data.taxii;

import java.util.List;

public class APIRoot {
	private String title;
	private String description;
	private String contact;
	private String defaultRoot;
	private List<String> roots;
	
	public APIRoot() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDefaultRoot() {
		return defaultRoot;
	}

	public void setDefaultRoot(String defaultRoot) {
		this.defaultRoot = defaultRoot;
	}

	public List<String> getRoots() {
		return roots;
	}

	public void setRoots(List<String> roots) {
		this.roots = roots;
	}
	
	
	
}
