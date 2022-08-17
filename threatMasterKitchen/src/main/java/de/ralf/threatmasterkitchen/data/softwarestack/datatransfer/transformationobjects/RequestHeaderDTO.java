package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects;

public class RequestHeaderDTO {
	private int header_property_id;
	private int operator_id;
	private String header_property_key;
	private String header_property_value;
	
	public int getHeader_property_id() {
		return header_property_id;
	}
	public void setHeader_property_id(int header_property_id) {
		this.header_property_id = header_property_id;
	}
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public String getHeader_property_key() {
		return header_property_key;
	}
	public void setHeader_property_key(String header_property_key) {
		this.header_property_key = header_property_key;
	}
	public String getHeader_property_value() {
		return header_property_value;
	}
	public void setHeader_property_value(String header_property_value) {
		this.header_property_value = header_property_value;
	}
}
