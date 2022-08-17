package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

import java.util.List;

public class ApplicationDTO {


	private String name;
	private long id;
	private String description;
	private List<String> assignments;
	private List<ComponentDTO> components;
	private String vectorNetwork;
	private String vectorAdjacent;
	private String vectorLocal;
	private String vectorPhysical;
	private String ip;
	private String subnet;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getAssignments() {
		return assignments;
	}
	public void setAssignments(List<String> assignments) {
		this.assignments = assignments;
	}
	public List<ComponentDTO> getComponents() {
		return components;
	}
	public void setComponents(List<ComponentDTO> components) {
		this.components = components;
	}
	public String getVectorNetwork() {
		return vectorNetwork;
	}
	public void setVectorNetwork(String vectorNetwork) {
		this.vectorNetwork = vectorNetwork;
	}
	public String getVectorAdjacent() {
		return vectorAdjacent;
	}
	public void setVectorAdjacent(String vectorAdjacent) {
		this.vectorAdjacent = vectorAdjacent;
	}
	public String getVectorLocal() {
		return vectorLocal;
	}
	public void setVectorLocal(String vectorLocal) {
		this.vectorLocal = vectorLocal;
	}
	public String getVectorPhysical() {
		return vectorPhysical;
	}
	public void setVectorPhysical(String vectorPhysical) {
		this.vectorPhysical = vectorPhysical;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSubnet() {
		return subnet;
	}
	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}
	
}
