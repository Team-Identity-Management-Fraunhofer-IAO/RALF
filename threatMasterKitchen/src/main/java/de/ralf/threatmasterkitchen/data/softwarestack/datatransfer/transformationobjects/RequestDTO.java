package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects;

import java.util.List;

public class RequestDTO {
	private int transformation_order_id;
	private int operator_id;
	private String request_type;
	private String endpoint;
	private String request_name;
	private String request_body;
	private List<RequestHeaderDTO> headers;
	private int order;
	
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public String getRequest_type() {
		return request_type;
	}
	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getRequest_name() {
		return request_name;
	}
	public void setRequest_name(String request_name) {
		this.request_name = request_name;
	}
	public String getRequest_body() {
		return request_body;
	}
	public void setRequest_body(String request_body) {
		this.request_body = request_body;
	}
	public List<RequestHeaderDTO> getHeaders() {
		return headers;
	}
	public void setHeaders(List<RequestHeaderDTO> headers) {
		this.headers = headers;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getTransformation_order_id() {
		return transformation_order_id;
	}
	public void setTransformation_order_id(int transformation_order_id) {
		this.transformation_order_id = transformation_order_id;
	}
	
}
