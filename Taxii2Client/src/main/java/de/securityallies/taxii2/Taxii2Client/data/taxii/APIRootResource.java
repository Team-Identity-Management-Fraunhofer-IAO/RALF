package de.securityallies.taxii2.Taxii2Client.data.taxii;

import java.util.List;

public class APIRootResource {

	private String title;
	private String description;
	private List<String> versions;
	private int max_content_length;
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
	public List<String> getVersions() {
		return versions;
	}
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}
	public int getMax_content_length() {
		return max_content_length;
	}
	public void setMax_content_length(int max_content_length) {
		this.max_content_length = max_content_length;
	}
	
	
	
}
