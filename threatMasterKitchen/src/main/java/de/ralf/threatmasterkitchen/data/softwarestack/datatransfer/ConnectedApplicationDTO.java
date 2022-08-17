package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

public class ConnectedApplicationDTO {
	private String fromSubnet;
	private String toSubnet;
	private Long fromId;
	private Long toId;
	
	public String getFromSubnet() {
		return fromSubnet;
	}
	public void setFromSubnet(String fromSubnet) {
		this.fromSubnet = fromSubnet;
	}
	public String getToSubnet() {
		return toSubnet;
	}
	public void setToSubnet(String toSubnet) {
		this.toSubnet = toSubnet;
	}
	public Long getFromId() {
		return fromId;
	}
	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}
	public Long getToId() {
		return toId;
	}
	public void setToId(Long toId) {
		this.toId = toId;
	}
	
}
