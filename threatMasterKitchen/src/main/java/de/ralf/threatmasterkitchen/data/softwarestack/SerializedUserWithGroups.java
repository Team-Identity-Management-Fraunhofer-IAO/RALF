package de.ralf.threatmasterkitchen.data.softwarestack;

import java.util.ArrayList;
import java.util.List;

import com.sql.data.objects.persistence.authentication.UserGroupAssociation;

public class SerializedUserWithGroups {

	private String username;
	private List<UserGroupAssociation> groups;

	public SerializedUserWithGroups() {
		groups = new ArrayList<UserGroupAssociation>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<UserGroupAssociation> getGroups() {
		return groups;
	}

	public void setGroups(List<UserGroupAssociation> groups) {
		this.groups = groups;
	}

}
