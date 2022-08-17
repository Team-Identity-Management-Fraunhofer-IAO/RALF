package de.securityallies.taxii2.Taxii2Client.data.stix;

public class Reference {
	private String source_name;
	private String external_id;
	private String url;
	private String description;
	
	public Reference() {
		
	}
	
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getExternal_id() {
		return external_id;
	}
	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Reference) {
			if (((Reference) o).getExternal_id().equals(this.getExternal_id()) && ((Reference) o).getSource_name().equals(this.getSource_name()) && ((Reference) o).getUrl().equals(this.getUrl())){
				return true;
			}
			return false;
		}
		return false;
	}
}
