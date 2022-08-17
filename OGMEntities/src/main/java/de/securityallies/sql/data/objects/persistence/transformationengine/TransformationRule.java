package de.securityallies.sql.data.objects.persistence.transformationengine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transformation_rule")
public class TransformationRule{
	private int operator_id;
	private String rule;
	private String target;
	private String rule_type;
	
	public TransformationRule() {
		
	}

	@Id
	@Column(name="operator_id", nullable=false)
	public int getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}

	@Column(name="rule", nullable=false)
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Column(name="target", nullable=false)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Column(name="rule_type", nullable=false)
	public String getRule_type() {
		return rule_type;
	}

	public void setRule_type(String transformation_rule) {
		if (transformation_rule != null) {
			if (transformation_rule.equals("JSONPATH")) {
				this.rule_type = "JSONPATH";
			}else if (transformation_rule.equals("XPATH")) {
				this.rule_type = "XPATH";
			} else if (transformation_rule.equals("LINE_SPLIT")) {
				this.rule_type = "LINE_SPLIT";
			} else if (transformation_rule.equals("CONSTANT")){
				this.rule_type = "CONSTANT";
			}else {
				this.rule_type = "XPATH";
			}
		}else {
			this.rule_type = "XPATH";
		}
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof TransformationRule) {
			if (((TransformationRule) o).getOperator_id() == this.operator_id) {
				return true;
			}
		}
		return false;
	}
	
	
}
