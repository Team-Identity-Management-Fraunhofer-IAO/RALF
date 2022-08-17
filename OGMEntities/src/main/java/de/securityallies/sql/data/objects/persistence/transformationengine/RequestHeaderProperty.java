package de.securityallies.sql.data.objects.persistence.transformationengine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="request_header_property")
public class RequestHeaderProperty {
	private int header_property_id;
	private int operator_id;
	private String header_property_key;
	private String header_property_value;
	
	public RequestHeaderProperty() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="header_property_id", nullable=false)
	public int getHeader_property_id() {
		return header_property_id;
	}

	public void setHeader_property_id(int header_property_id) {
		this.header_property_id = header_property_id;
	}

	@Column(name="operator_id", nullable=false)
	public int getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}

	@Column(name="header_property_key", nullable=false)
	public String getHeader_property_key() {
		return header_property_key;
	}

	public void setHeader_property_key(String header_property_key) {
		this.header_property_key = header_property_key;
	}

	@Column(name="header_property_value", nullable=false)
	public String getHeader_property_value() {
		return header_property_value;
	}

	public void setHeader_property_value(String header_property_value) {
		this.header_property_value = header_property_value;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof RequestHeaderProperty) {
			if (((RequestHeaderProperty) o).getHeader_property_id() == this.header_property_id) {
				return true;
			}
		}
		return false;
	}

}
