package com.sql.data.objects.persistence.authentication;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="authorities")
public class Authority {
	private String groupname;
	private String role;
	
	public Authority() {
		
	}

	@Id
	@Column(name="groupname", nullable=false)
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name="role", nullable=false)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
}
