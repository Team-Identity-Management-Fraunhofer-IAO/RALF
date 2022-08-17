package de.securityallies.taxii2.Taxii2Client.data.taxii;

import java.util.List;

public class Collection {
	//mandatory
	private String id;
	//mandatory
	private String title;
	//optional
	private String description;
	//optional
	private String alias;
	//mandatory
	private boolean can_read;
	//mandatory
	private boolean can_write;
	//optional
	private List<String> media_types;
	
	public Collection() {
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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

	public boolean isCan_read() {
		return can_read;
	}

	public void setCan_read(boolean can_read) {
		this.can_read = can_read;
	}

	public boolean isCan_write() {
		return can_write;
	}

	public void setCan_write(boolean can_write) {
		this.can_write = can_write;
	}

	public List<String> getMedia_types() {
		return media_types;
	}

	public void setMedia_types(List<String> media_types) {
		this.media_types = media_types;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
	
}
