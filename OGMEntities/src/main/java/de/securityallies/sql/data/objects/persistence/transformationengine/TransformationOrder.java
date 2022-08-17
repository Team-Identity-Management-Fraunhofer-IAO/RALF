package de.securityallies.sql.data.objects.persistence.transformationengine;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transformation_order")
public class TransformationOrder {
	private int operator_id;
	private int transformation_order_id;
	private int transformation_sequence;
	private String transformation_order_name;
	
	public TransformationOrder() {
		
	}

	@Id
	@Column(name="operator_id", nullable=false)
	public int getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}

	@Column(name="transformation_order_id", nullable=false)
	public int getTransformation_order_id() {
		return transformation_order_id;
	}	

	public void setTransformation_order_id(int transformation_order_id) {
		this.transformation_order_id = transformation_order_id;
	}

	@Column(name="transformation_sequence", nullable=false)
	public int getTransformation_sequence() {
		return transformation_sequence;
	}

	public void setTransformation_sequence(int transformation_sequence) {
		this.transformation_sequence = transformation_sequence;
	}

	@Column(name="transformation_order_name", nullable=false)	
	public String getTransformation_order_name() {
		return transformation_order_name;
	}

	public void setTransformation_order_name(String transformation_order_name) {
		this.transformation_order_name = transformation_order_name;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof TransformationOrder) {
			if (((TransformationOrder) o).getOperator_id() == this.operator_id) {
				return true;
			}
		}
		return false;
	}
	
	public int hashCode() {
		return Objects.hash(operator_id);
	}
	
}
