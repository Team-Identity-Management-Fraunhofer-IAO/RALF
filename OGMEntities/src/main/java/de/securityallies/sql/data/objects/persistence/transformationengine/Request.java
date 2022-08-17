package de.securityallies.sql.data.objects.persistence.transformationengine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sql.data.objects.persistence.PersistentObjectInterface;

@Entity
@Table(name="request")
public class Request{
	private int operator_id;
	private String request_type;
	private String endpoint;
	private String request_name;
	private String body;
	
	public Request() {
		
	}
	
	@Id
	@Column(name="operator_id", nullable=false)
	public int getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}

	@Column(name="request_type", nullable = false)
	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		if (request_type == null) {
			this.request_type = "GET";
		}else if (request_type.equals("GET")) {
			this.request_type = "GET";
		}else if (request_type.equals("POST")) {
			this.request_type = "POST";
		}
		this.request_type = request_type;
	}

	@Column(name="endpoint", nullable=false)
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	@Column(name="request_name", nullable=false)
	public String getRequest_name() {
		return request_name;
	}

	public void setRequest_name(String request_name) {
		this.request_name = request_name;
	}
	
	@Column(name="body", nullable=false)	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof Request) {
			if (((Request) o).getOperator_id() == this.operator_id) {
				return true;
			}
		}
		return false;
	}

}
