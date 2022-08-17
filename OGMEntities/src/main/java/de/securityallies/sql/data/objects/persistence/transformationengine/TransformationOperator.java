package de.securityallies.sql.data.objects.persistence.transformationengine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="transformation_operator")
public class TransformationOperator {
	private int operator_id;
	private String operator_type;
	
	public TransformationOperator() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="operator_id", nullable=false)
	public int getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}

	@Column(name="operator_type", nullable=false)
	public String getOperator_type() {
		return operator_type;
	}

	public void setOperator_type(String operator_type) {
		System.out.println("Setting "+operator_type);
		this.operator_type = operator_type;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof TransformationOperator) {
			if (((TransformationOperator) o).getOperator_id() == this.operator_id) {
				return true;
			}
		}
		return false;
	}
}
