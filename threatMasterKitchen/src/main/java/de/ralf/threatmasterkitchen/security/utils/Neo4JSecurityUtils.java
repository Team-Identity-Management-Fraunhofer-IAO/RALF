package de.ralf.threatmasterkitchen.security.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;

import com.sql.data.provider.UserGroupAuthorityProvider;
import com.sql.data.objects.persistence.authentication.Authority;
import com.sql.data.objects.persistence.authentication.UserGroupAssociation;

@Deprecated
public class Neo4JSecurityUtils {
	/*
	public Neo4JSecurityUtils() {
		
	}
	
	public List<UserGroupAssociation> getGroupsForUsername(String username){
		UserGroupAuthorityProvider userProvider = new UserGroupAuthorityProvider();
		return userProvider.getUserGroupAssociationsForUser(username);
	}
	
	public boolean isSWStackInGroupList(SWStack stack_raw, List<UserGroupAssociation> groups) {
		SWStackProvider stackProvider = new SWStackProvider();
		UserGroupAuthorityProvider userProvider = new UserGroupAuthorityProvider();
		SWStack stack = stackProvider.find(stack_raw.getId());
		Set<Group> stackGroups = stack.getGroups();
		if (!stackGroups.isEmpty()) {
			for (Group stackGroup : stackGroups) {
				for (UserGroupAssociation association : groups) {
					Authority auth = userProvider.getGroupAuthorityForGroupname(association.getGroupname());
					if (stackGroup.getGroupname().equals(association.getGroupname())) {
						return true;
					}else if(auth.getRole().equals("ROLE_ADMIN")) {
						return true;
					}
				}
			}
		}else {
			System.out.println("Stack Group Assignments are empty!");
		}
		return false;
	}
	
	public boolean isSWStackIdAuthorized(Long swStackId, List<UserGroupAssociation> associations) {
		SWStackProvider stackProvider = new SWStackProvider();
		Iterable<Group> groups = stackProvider.findGroupsForSWStackId(swStackId);
		for (Group group : groups) {
			for (UserGroupAssociation association : associations) {
				if (group.getGroupname().equals(association.getGroupname())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isComponentAuthorized(Long componentId, List<UserGroupAssociation> associations) {
		ComponentProvider componentProvider = new ComponentProvider();
		Iterable<Group> groups = componentProvider.getGroupsForComponentId(componentId);
		for (Group group : groups) {
			for (UserGroupAssociation association : associations) {
				if (group.getGroupname().equals(association.getGroupname())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public ArrayList<SWStack> getAuthorizedSWStacks(Authentication authentication){
		ObjectFactory obj = new ObjectFactory();
		Iterable<SWStack> allStacks = obj.getAllSWStacks();
		ArrayList<SWStack> stacks = new ArrayList<SWStack>();
		boolean isAdmin = new SecurityUtils().isAdmin(authentication);

		for (SWStack stack : allStacks) {
			if (isAdmin) {
				stacks.add(stack);
			} else {
				if (isSWStackInGroupList(stack, getGroupsForUsername(authentication.getName()))) {
					stacks.add(stack);
				}
			}
		}
		allStacks = null;
		return stacks;
	}
	
	public Iterable<Group> getGroupsForSWStack(long id){
		ObjectFactory obj = new ObjectFactory();
		Iterable<Group> groups = obj.getGroupsForSWStack(id);
		return groups;
	}
*/
}
