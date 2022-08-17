package com.sql.data.objects.persistence.authentication;

import java.io.Serializable;
import java.util.Objects;

public class UserGroupAssociationPK implements Serializable{
	protected String username;
	protected String groupname;
	
	public UserGroupAssociationPK() {
	}

	public UserGroupAssociationPK(String username, String groupname) {
		this.username = username;
		this.groupname = groupname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	@Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof UserGroupAssociationPK) {
        	if ((((UserGroupAssociationPK) o).username == this.username) && (((UserGroupAssociationPK) o).groupname == this.groupname)){
        		return true;
        	}
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, groupname);
    }
	
}