package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects;

public class OperatorDTO {
	private int transformation_order_id;
	private int operator_id;
	private String operator_type;
	private int order;
	
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public String getOperator_type() {
		return operator_type;
	}
	public void setOperator_type(String operator_type) {
		this.operator_type = operator_type;
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
