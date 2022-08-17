package de.ralf.threatmasterkitchen.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.sql.data.provider.ComponentVulnerabilityAndAttackPatternProvider;
import com.sql.data.provider.UserGroupAuthorityProvider;
import de.ralf.threatmasterkitchen.data.softwarestack.SerializedUserWithGroups;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.GroupDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.PasswordChangeDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.UserDTO;
import de.ralf.threatmasterkitchen.security.utils.RandomPasswordGenerator;


import com.sql.data.objects.aggregations.VulnerableComponent;
import com.sql.data.objects.persistence.authentication.Authority;
import com.sql.data.objects.persistence.authentication.Group;
import com.sql.data.objects.persistence.authentication.User;
import com.sql.data.objects.persistence.authentication.UserGroupAssociation;

@PropertySource("classpath:db.properties")
@Controller
public class AdminController extends AbstractController {
/*	@Autowired
	private Environment env;

	private final String newUserMail = "Hi!<br />"
			+ "A new user account has just been created for you at the risk analysis platform RALF.<br />"
			+ "This account enables you to use the SW Stack Assessment module with all associated rights provided to you.<br />"
			+ "<br />" + "Your credentials are shown in the following.<br />" + "Username:${username}" + "<br />"
			+ "Password:${password}<br />" + "<br />"
			+ "Please change your password when you log in for the first time.<br /><br />"
			+ "Thank you for using RALF!";

	private final String resetPasswordMail = "Hi!<br />" + "Your password has been resetted.<br />"
			+ "Your new password is:${password}<br />" + "<br />"
			+ "Please change your password when you log in.<br /><br />"
			+ "Thank you for using RALF!";

	public AdminController() {
		super();
	}

	@PostMapping(value = "/admin/resetPassword")
	public ResponseEntity resetUserPassword(@RequestBody final UserDTO userDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			String username = userDTO.getUsername();
			if (!username.equals("admin")) {
				UserGroupAuthorityProvider userProvider = new UserGroupAuthorityProvider();
				User user = userProvider.getUser(username);
				if (user != null) {
					RandomPassword password = new RandomPassword();
					user.setPassword(password.getPasswordHash());
					userProvider.updateUser(user);						
					//Mailer mailer= new Mailer();					
					try {
						mailer.parseConfiguration(new ClassPathResource("endpoints.properties").getFile());
						mailer.setRecipient(user.getEmail());
						Map<String, String> parameters = new HashMap<String, String>();
						parameters.put("password", password.getPasswordClear());					
						mailer.setMessage(new ClassPathResource("ResetPasswordMail.tmpl").getFile(), parameters);
						//mailer.setMessage(mailtext);
						mailer.setSubject("[RALF SW Stack Assessment] Your password has been resetted");
						mailer.sendMessage();
						parameters = null;
						password = null;
						return new ResponseEntity(HttpStatus.OK);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/admin/deleteUser")
	public ResponseEntity deleteUser(@RequestBody final UserDTO userDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			String username = userDTO.getUsername();
			if (username != "admin") {
				UserGroupAuthorityProvider userProvider = new UserGroupAuthorityProvider();
				User user = userProvider.getUser(username);
				userProvider.deleteUser(user);
				return new ResponseEntity(HttpStatus.OK);
			}
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/admin/createNewUser")
	public ResponseEntity createNewUser(@RequestBody final UserDTO userDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			String username = userDTO.getUsername();
			String email = userDTO.getEmail();
			if (username == null || email == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else if (username.equals("") || email.equals("")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			RandomPassword password = new RandomPassword();
			User newUser = new User();
			newUser.setChangePassword(true);
			newUser.setEnabled(true);
			newUser.setEmail(email);
			newUser.setPassword(password.getPasswordHash());
			newUser.setUsername(username);
			UserGroupAuthorityProvider userProvider = new UserGroupAuthorityProvider();
			userProvider.persistUser(newUser);
			UserGroupAssociation association = new UserGroupAssociation();
			association.setGroupname("RALF_USERS");
			association.setUsername(username);
			association.setOwner(false);
			userProvider.persistUserGroupAssociation(association);
			
	
			Mailer mailer= new Mailer();
			
			try {
				mailer.parseConfiguration(new ClassPathResource("endpoints.properties").getFile());
				mailer.setRecipient(newUser.getEmail());
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("username", username);
				parameters.put("password", password.getPasswordClear());					
				mailer.setMessage(new ClassPathResource("NewUserMail.tmpl").getFile(), parameters);
				mailer.setSubject("[RALF SW Stack Assessment] A new user account has been created for you");
				mailer.sendMessage();
				parameters = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			password = null;
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/admin/addAssignment")
	public ResponseEntity addAssignment(@RequestBody final UserDTO userDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			String username = userDTO.getUsername();
			String groupname = userDTO.getGroup();
			Boolean isOwner = userDTO.isOwner();
			if (username == null || groupname == null || isOwner == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else if (username.equals("") || groupname.equals("")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			UserGroupAuthorityProvider userProvider = new UserGroupAuthorityProvider();
			UserGroupAssociation association = new UserGroupAssociation();
			association.setUsername(username);
			association.setGroupname(groupname);
			association.setOwner(isOwner);
			userProvider.persistUserGroupAssociation(association);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/admin/removeAssignment")
	public ResponseEntity removeAssignment(@RequestBody final UserDTO userDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			String username = userDTO.getUsername();
			String groupname = userDTO.getGroup();
			if (username == null || groupname == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else if (username.equals("") || groupname.equals("")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			UserGroupAuthorityProvider userProvider = new UserGroupAuthorityProvider();
			UserGroupAssociation association = userProvider.getUserGroupAssociationForUserAndGroup(username, groupname);
			if (association != null) {
				userProvider.deleteUserGroupAssociation(association);
				return new ResponseEntity(HttpStatus.OK);
			}
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/admin/createNewGroup")
	public ResponseEntity createGroup(@RequestBody final GroupDTO groupDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			UserGroupAuthorityProvider userGroupAuthorityProvider = new UserGroupAuthorityProvider();
			String authority = groupDTO.getRole();
			String groupname = groupDTO.getName();
			if (authority == null || groupname == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else if (authority.equals("") || groupname.equals("")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			if (!authority.equals("ROLE_SW_STACK_REVIEW") && !authority.equals("ROLE_SW_STACK_MODELLER")
					&& !authority.equals("ROLE_SW_STACK_DEVELOPER") && !authority.equals("ROLE_SW_STACK_AUDITOR")
					&& !authority.equals("Administrator")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			Authority myAuthority = new Authority();
			myAuthority.setGroupname(groupname);
			myAuthority.setRole(authority);
			userGroupAuthorityProvider.addNewGroupWithAuthority(myAuthority);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/admin/alterGroup")
	public ResponseEntity alterGroup(@RequestBody final GroupDTO groupDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			String groupname = groupDTO.getName();
			String authority = groupDTO.getRole();
			if (authority == null || groupname == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else if (authority.equals("") || groupname.equals("")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			if (!authority.equals("ROLE_SW_STACK_REVIEW") && !authority.equals("ROLE_SW_STACK_MODELLER")
					&& !authority.equals("ROLE_SW_STACK_DEVELOPER") && !authority.equals("ROLE_SW_STACK_AUDITOR")
					&& !authority.equals("Administrator")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			if (groupname.equals("administration") || groupname.equals("RALF_USERS")) {
				return new ResponseEntity(HttpStatus.FORBIDDEN);
			}
			UserGroupAuthorityProvider userGroupAuthorityProvider = new UserGroupAuthorityProvider();
			Authority myAuthority = userGroupAuthorityProvider.getGroupAuthorityForGroupname(groupname);
			myAuthority.setRole(authority);
			userGroupAuthorityProvider.peristAuthorityAssignment(myAuthority);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/admin/deleteGroup")
	public ResponseEntity deleteGroup(@RequestBody final GroupDTO groupDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			String groupname = groupDTO.getName();
			if (groupname == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}else if (groupname.equals("")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}else if (groupname.equals("administration") || groupname.equals("RALF_USERS")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			UserGroupAuthorityProvider userGroupAuthorityProvider = new UserGroupAuthorityProvider();
			Group group = userGroupAuthorityProvider.getGroupByGroupname(groupname);
			userGroupAuthorityProvider.deleteGroup(group);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/admin/addStackToGroup")
	public ResponseEntity addStackToGroup(@RequestBody final GroupDTO groupDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			Long stackID = groupDTO.getStack();
			String groupname = groupDTO.getName();
			if (stackID == null || groupname == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}else if (stackID.equals(Long.valueOf(-1)) || stackID.equals(Long.valueOf(0)) || groupname.equals("")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			SWStackProvider swStackProvider = new SWStackProvider();
			swStackProvider.createGroupWithSWStack(stackID, groupname);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/admin/removeStackFromGroup")
	public ResponseEntity removeStackFromGroup(@RequestBody final GroupDTO groupDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			Long stackID = groupDTO.getStack();
			String groupname = groupDTO.getName();
			if (stackID == null || groupname == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}else if (stackID.equals(Long.valueOf(-1)) || stackID.equals(Long.valueOf(0)) || groupname.equals("")) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}			
			SWStackProvider swStackProvider = new SWStackProvider();
			swStackProvider.removeStackFromGroup(stackID, groupname);
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/Settings/changePassword")
	public ResponseEntity changePassword(@RequestBody final PasswordChangeDTO passwordChange, Authentication authentication) {
		String authUsername = authentication.getName();
		if (passwordChange.getUsername().equals(authUsername)) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String passwordHash = encoder.encode(passwordChange.getPassword());
			passwordChange.setPassword(null);
			UserGroupAuthorityProvider userProvider = new UserGroupAuthorityProvider();
			User user = userProvider.getUser(passwordChange.getUsername());
			user.setPassword(passwordHash);
			userProvider.updateUser(user);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	
	

	private class RandomPassword {
		private String passwordClear;
		private String passwordHash;

		public RandomPassword() {
			RandomPasswordGenerator pwGenerator = new RandomPasswordGenerator();
			passwordClear = pwGenerator.createRandomPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			passwordHash = encoder.encode(passwordClear);
		}

		public String getPasswordClear() {
			return passwordClear;
		}

		public void setPasswordClear(String passwordClear) {
			this.passwordClear = passwordClear;
		}

		public String getPasswordHash() {
			return passwordHash;
		}

		public void setPasswordHash(String passwordHash) {
			this.passwordHash = passwordHash;
		}

	}
*/
}
