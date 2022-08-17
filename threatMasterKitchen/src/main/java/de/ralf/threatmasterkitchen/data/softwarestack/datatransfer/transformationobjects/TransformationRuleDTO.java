package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects;

public class TransformationRuleDTO {
	private int transformation_order_id;
	private int operator_id;
	private String rule;
	private String target;
	private String rule_type;
	private int order;
	
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getRule_type() {
		return rule_type;
	}
	public void setRule_type(String rule_type) {
		this.rule_type = rule_type;
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
