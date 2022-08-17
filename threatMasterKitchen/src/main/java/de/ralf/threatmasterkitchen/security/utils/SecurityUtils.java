package de.ralf.threatmasterkitchen.security.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SecurityUtils {

	private List<String> modifyStackRoles;
	private List<String> viewStackRoles;
	private List<String> viewReportRoles;
	private List<String> createReportRoles;

	public SecurityUtils() {
		modifyStackRoles = new ArrayList<String>();
		viewStackRoles = new ArrayList<String>();
		viewReportRoles = new ArrayList<String>();
		createReportRoles = new ArrayList<String>();
		
		viewStackRoles.add("ROLE_SW_STACK_REVIEW");
		
		modifyStackRoles.add("ROLE_SW_STACK_MODELLER");
		viewStackRoles.add("ROLE_SW_STACK_MODELLER");
		
		modifyStackRoles.add("ROLE_SW_STACK_DEVELOPER");
		viewStackRoles.add("ROLE_SW_STACK_DEVELOPER");
		viewReportRoles.add("ROLE_SW_STACK_DEVELOPER");
		createReportRoles.add("ROLE_SW_STACK_DEVELOPER");
	
		viewStackRoles.add("ROLE_SW_STACK_AUDITOR");
		viewReportRoles.add("ROLE_SW_STACK_AUDITOR");
		createReportRoles.add("ROLE_SW_STACK_AUDITOR");
	}

	public boolean isAdmin(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkAuthorization(String action, Authentication authentication) {
		
		if (action.equals("modifyStacks")) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				if (canModifyStack(grantedAuthority.getAuthority())) {
					return true;
				}else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
					return true;
				}
			}
		}else if (action.equals("viewStacks")) {
			
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				if (canViewStack(grantedAuthority.getAuthority())) {
					return true;
				}else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
					return true;
				}
			}
		}else if (action.equals("viewReports")) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				if (canViewReports(grantedAuthority.getAuthority())) {
					return true;
				}else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
					return true;
				}
			}
		}else if (action.equals("createReports")) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				if (canCreateReports(grantedAuthority.getAuthority())) {
					return true;
				}else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean canModifyStack(String authority) {
		if (modifyStackRoles.contains(authority)) {
			return true;
		}
		return false;
	}
	
	private boolean canViewStack(String authority) {
		if (viewStackRoles.contains(authority)) {
			return true;
		}
		return false;
	}
	
	private boolean canViewReports(String authority) {
		if (viewReportRoles.contains(authority)) {
			return true;
		}
		return false;
	}
	
	private boolean canCreateReports(String authority) {
		if (createReportRoles.contains(authority)) {
			return true;
		}
		return false;
	}

}
