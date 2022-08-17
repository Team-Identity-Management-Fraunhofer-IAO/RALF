package com.sql.data.objects.persistence.authentication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ralf_groups")
public class Group {
	private String groupname;
	
	public Group() {
		
	}

	@Id
	@Column(name="groupname", nullable=false)
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
}
