package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects;

public class TransformationOrderDTO {
	private int transformation_order_id;
	private int transformation_sequence;
	private int operator_id;
	private String transformation_order_name;
	
	public int getTransformation_order_id() {
		return transformation_order_id;
	}
	public void setTransformation_order_id(int transformation_order_id) {
		this.transformation_order_id = transformation_order_id;
	}
	public int getTransformation_sequence() {
		return transformation_sequence;
	}
	public void setTransformation_sequence(int transformation_sequence) {
		this.transformation_sequence = transformation_sequence;
	}
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public String getTransformation_order_name() {
		return transformation_order_name;
	}
	public void setTransformation_order_name(String transformation_order_name) {
		this.transformation_order_name = transformation_order_name;
	}
	
}
