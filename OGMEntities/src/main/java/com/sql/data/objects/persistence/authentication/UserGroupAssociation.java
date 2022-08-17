package com.sql.data.objects.persistence.authentication;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="user_group_association")
@IdClass(UserGroupAssociationPK.class)
public class UserGroupAssociation {
	private String username;
	private String groupname;
	private boolean owner;
	
	public UserGroupAssociation() {
		
	}	

	@Id
	@Column(name="username", nullable=false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Id
	@Column(name="groupname",nullable=false)
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	@Column(name="isOwner", nullable=false)
	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean isOwner) {
		this.owner = isOwner;
	}

	
}
