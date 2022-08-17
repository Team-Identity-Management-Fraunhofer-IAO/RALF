<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" type="text/css"
	href="${folderPosition}/styles_auth/stylesw3c.css">
<script type="text/javascript"
	src="${folderPosition}/js_auth/lib/jquery-3.5.0.min.js"></script>
<script type="text/javascript"
	src="${folderPosition}/js_auth/messages.js"></script>
<script type="text/javascript" 
	src="${folderPosition}/js_auth/adminFunctions.js"></script>
<!-- 
<script type="text/javascript"
	src="${folderPosition}/js_auth/ajaxLoader.js"></script>

<script type="text/javascript" 
	src="${folderPosition}/js_auth/reportAjaxFunctions.js"></script>
<script type="text/javascript" 
	src="${folderPosition}/js_auth/reportFunctions.js"></script>
-->
<script type="text/javascript">
	var swStackId = ${swStackId};
	var title = "${swStackTitle}";
	var applicationName = "${applicationName}";
	var urlLocation = window.location.href.split("/");
	var applicationContext = urlLocation[0] + "//" + urlLocation[2] + "/"
			+ applicationName;
</script>

</head>
<body onload="init();" style="background-image: none;">
	<div id="sidebar">
		<img src="${folderPosition}/img_auth/ralf_logo.png" id="ralf_logo" />
		<div id="menu-container">
			<ul id="menu">
				<li class="menu disabled">Dashboard</li>
				<li class="menu"><a href="${folderPosition}/SWStackModelling">Modelling</a></li>
				<li class="menu disabled">Threats</li>
				<li class="menu"><a
					href="${folderPosition}/Assessment/SWStack">Assessments</a></li>
				<li class="menu disabled">Controls</li>
				<li class="menu disabled">Settings</li>
			</ul>
		</div>
		<div class="menu vertical-separator"></div>
		<div id="usr-container">
			<ul id="usr">
				<li class="usr disabled"><img
					src="${folderPosition}/img_auth/avatar.png" id="usrLogo" /><a href="${folderPosition}/user">${principal}</a></li>
				<li class="usr disabled">&nbsp;</li>
				<c:if test="${isAdmin}">
					<li class="usr active"><a href="${folderPosition}/admin">Administration</a></li>
				</c:if>
				<li class="usr"><a href="${folderPosition}/perform_logout">Logoff</a></li>
			</ul>
		</div>

	</div>
	<div id="content">
		<div id="context-menu">
			<p id="context-menu-btn" class="admin">
				
			</p>
			<p id="context-menu-title">
				A&nbsp;&nbsp;d&nbsp;&nbsp;m&nbsp;&nbsp;i&nbsp;&nbsp;n&nbsp;&nbsp;i&nbsp;&nbsp;s&nbsp;&nbsp;t&nbsp;&nbsp;r&nbsp;&nbsp;a&nbsp;&nbsp;t&nbsp;&nbsp;i&nbsp;&nbsp;o&nbsp;&nbsp;n
			</p>
		</div>
		<div id="context-submenu">
				<div id="context-submenu-btn-grid">
					<p class="context-submenu-btn context-submenu-btn-col13 context-submenu-btn-row1">G&nbsp;&nbsp;r&nbsp;&nbsp;o&nbsp;&nbsp;u&nbsp;&nbsp;p&nbsp;&nbsp;s</p>
					<p id="nodeAddBtn" class="context-submenu-btn context-submenu-btn-col1 context-submenu-btn-row2"><a href="${folderPosition}/admin/groupManagement">Group List</a></p>
					<p id = "nodeDeleteBtn" class="context-submenu-btn context-submenu-btn-col2 context-submenu-btn-row2"><a href="${folderPosition}/admin/SWStackManagement">Stack List</a></p>
					<p class="context-submenu-btn context-submenu-btn-col46 context-submenu-btn-row1">U&nbsp;&nbsp;s&nbsp;&nbsp;e&nbsp;&nbsp;r&nbsp;&nbsp;s</p>
					<p id="edgeAddBtn" class="context-submenu-btn context-submenu-btn-col4 context-submenu-btn-row2"><a href="${folderPosition}/admin/userManagement">User List</a></p>
				</div>
			</div>
		<div id="content-container">
			<div id="alert-container"></div>
			<c:choose>
				<c:when test="${adminAction == 'groupManagement'}">
					<div id="role-management-container">
						<div id="role-list-container">				
							<div class="col1to3 table-header-no-line">
									G&nbsp;&nbsp;r&nbsp;&nbsp;o&nbsp;&nbsp;u&nbsp;&nbsp;p&nbsp;&nbsp;&nbsp;&nbsp;M&nbsp;&nbsp;a&nbsp;&nbsp;n&nbsp;&nbsp;a&nbsp;&nbsp;g&nbsp;&nbsp;e&nbsp;&nbsp;m&nbsp;&nbsp;e&nbsp;&nbsp;n&nbsp;&nbsp;t&nbsp;&nbsp;&dash;&nbsp;&nbsp;G&nbsp;&nbsp;r&nbsp;&nbsp;o&nbsp;&nbsp;u&nbsp;&nbsp;p&nbsp;&nbsp;&nbsp;&nbsp;L&nbsp;&nbsp;i&nbsp;&nbsp;s&nbsp;&nbsp;t
							</div>
							<div id="role-list">
								
								<div class="col1 table-header">
									N&nbsp;&nbsp;a&nbsp;&nbsp;m&nbsp;&nbsp;e
								</div>
								<div class="col2 table-header">
									A&nbsp;&nbsp;c&nbsp;&nbsp;t&nbsp;&nbsp;i&nbsp;&nbsp;o&nbsp;&nbsp;n
								</div>
								<div class="col3 table-header">
									R&nbsp;&nbsp;i&nbsp;&nbsp;g&nbsp;&nbsp;h&nbsp;&nbsp;t&nbsp;&nbsp;s
								</div>
								<div id="role-list-new-group-name" class="role-list-body col1"><input type="text" placeholder="Name of the new group" id="roleNameInput"></div>
								<div id="role-list-new-group-save" class="role-list-body col2"><input name="submit" type="submit" id="addGroup" value="create new" /></div>
								<div id="role-list-new-group-rights" class="role-list-body col3">
									<select name="access_right" class="role-right-selector" id="newGroupRightsSelector">
										<option value="SWStackReview" title="Can view Software Stacks">SW Stack Reviewer</option>
										<option value="SWStackModelling" title="Can view, create, modify and delete SW Stacks">SW Stack Modeller</option>
										<option value="SWStackDeveloper" title="Can view, create, modify and delete SW Stacks, create and view reports">SW Stack Developer</option>
										<option value="SWStackAuditor" title="Can view SW Stacks and create and view reports">SW Stack Auditor</option>
										<option value="Administrator" title="This is the superuser group. Use with great caution!">Administrator</option>
									</select>
								</div>
								<c:forEach items="${userAuthorities}" var="group">
									<div class="role-list-body col1"><c:out value="${group.groupname}"/></div>
									<div class="role-list-body col2">
										
										<input name="delete" type="submit" class="removeGroupButton" value="delete" <c:if test="${group.groupname == 'administration'}"><c:out value="disabled" /></c:if>/>
										<input name="alter" type="submit" class="alterGroupButton" value="update" <c:if test="${group.groupname == 'administration'}"><c:out value="disabled" /></c:if> />
									</div>
									<div class="role-list-body col3">
										<select name="access_right" class="role-right-selector" <c:if test="${group.groupname == 'administration'}"><c:out value="disabled" /></c:if>>
											<c:choose>
												<c:when test="${group.role == 'ROLE_SW_STACK_REVIEW'}">
													<option value="SWStackReview" title="Can view Software Stacks" selected="selected">SW Stack Reviewer</option>
													<option value="SWStackModelling" title="Can view, create, modify and delete SW Stacks">SW Stack Modeller</option>
													<option value="SWStackDeveloper" title="Can view, create, modify and delete SW Stacks, create and view reports">SW Stack Developer</option>
													<option value="SWStackAuditor" title="Can view SW Stacks and create and view reports">SW Stack Auditor</option>
													<option value="Administrator" title="This is a superuser group. Use with great caution!">Administrator</option>
												</c:when>
												<c:when test="${group.role == 'ROLE_SW_STACK_MODELLER'}">
													<option value="SWStackReview" title="Can view Software Stacks">SW Stack Reviewer</option>
													<option value="SWStackModelling" title="Can view, create, modify and delete SW Stacks" selected="selected">SW Stack Modeller</option>
													<option value="SWStackDeveloper" title="Can view, create, modify and delete SW Stacks, create and view reports">SW Stack Developer</option>
													<option value="SWStackAuditor" title="Can view SW Stacks and create and view reports">SW Stack Auditor</option>
													<option value="Administrator" title="This is a superuser group. Use with great caution!">Administrator</option>
												</c:when>
												<c:when test="${group.role == 'ROLE_SW_STACK_DEVELOPER'}">
													<option value="SWStackReview" title="Can view Software Stacks">SW Stack Reviewer</option>
													<option value="SWStackModelling" title="Can view, create, modify and delete SW Stacks">SW Stack Modeller</option>
													<option value="SWStackDeveloper" title="Can view, create, modify and delete SW Stacks, create and view reports" selected="selected">SW Stack Developer</option>
													<option value="SWStackAuditor" title="Can view SW Stacks and create and view reports">SW Stack Auditor</option>
													<option value="Administrator" title="This is a superuser group. Use with great caution!">Administrator</option>
												</c:when>
												<c:when test="${group.role == 'ROLE_SW_STACK_AUDITOR'}">
													<option value="SWStackReview" title="Can view Software Stacks">SW Stack Reviewer</option>
													<option value="SWStackModelling" title="Can view, create, modify and delete SW Stacks">SW Stack Modeller</option>
													<option value="SWStackDeveloper" title="Can view, create, modify and delete SW Stacks, create and view reports">SW Stack Developer</option>
													<option value="SWStackAuditor" title="Can view SW Stacks and create and view reports" selected="selected">SW Stack Auditor</option>
													<option value="Administrator" title="This is a superuser group. Use with great caution!">Administrator</option>
												</c:when>
												<c:when test="${group.role ==  'ROLE_ADMIN'}">
													<option value="SWStackReview" title="Can view Software Stacks">SW Stack Reviewer</option>
													<option value="SWStackModelling" title="Can view, create, modify and delete SW Stacks">SW Stack Modeller</option>
													<option value="SWStackDeveloper" title="Can view, create, modify and delete SW Stacks, create and view reports">SW Stack Developer</option>
													<option value="SWStackAuditor" title="Can view SW Stacks and create and view reports">SW Stack Auditor</option>
													<option value="Administrator" title="This is a superuser group. Use with great caution!" selected="selected">Administrator</option>
												</c:when>
											</c:choose>
										</select>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:when>
					<c:when test="${adminAction == 'SWStackManagement'}">
						<div id="role-management-container">
							<datalist id="stacks">
								<c:forEach items="${allStacks}" var="stack">
									<option data-stackId="${stack.id}" value="${stack.name}" />
								</c:forEach>
							</datalist>
							<div id="role-list-container">				
								<div class="col1to3 table-header-no-line">
										G&nbsp;&nbsp;r&nbsp;&nbsp;o&nbsp;&nbsp;u&nbsp;&nbsp;p&nbsp;&nbsp;&nbsp;&nbsp;M&nbsp;&nbsp;a&nbsp;&nbsp;n&nbsp;&nbsp;a&nbsp;&nbsp;g&nbsp;&nbsp;e&nbsp;&nbsp;m&nbsp;&nbsp;e&nbsp;&nbsp;n&nbsp;&nbsp;t&nbsp;&nbsp;&dash;&nbsp;&nbsp;S&nbsp;&nbsp;o&nbsp;&nbsp;f&nbsp;&nbsp;t&nbsp;&nbsp;w&nbsp;&nbsp;a&nbsp;&nbsp;r&nbsp;&nbsp;e&nbsp;&nbsp;&nbsp;&nbsp;S&nbsp;&nbsp;t&nbsp;&nbsp;a&nbsp;&nbsp;c&nbsp;&nbsp;k&nbsp;&nbsp;s
								</div>
								<div id="role-list">
									<div class="col1 table-header">
										G&nbsp;&nbsp;r&nbsp;&nbsp;o&nbsp;&nbsp;u&nbsp;&nbsp;p
									</div>
									<div class="col2to3 table-header">
										S&nbsp;&nbsp;o&nbsp;&nbsp;f&nbsp;&nbsp;t&nbsp;&nbsp;w&nbsp;&nbsp;a&nbsp;&nbsp;r&nbsp;&nbsp;e&nbsp;&nbsp;&nbsp;&nbsp;S&nbsp;&nbsp;t&nbsp;&nbsp;a&nbsp;&nbsp;c&nbsp;&nbsp;k&nbsp;&nbsp;s
									</div>
									<c:forEach items="${stackList}" var="group">
										<div class="role-list-body col1">
											<c:out value="${group.authority.groupname}"/>
										</div>
										<div class="role-list-body col2to3 SWStackList">
											<span class="list-items-single">
												<c:forEach items="${group.swStacks}" var="stack">
													<span data-stackname="${stack.name}" data-stackId="${stack.id}"class="list-item"><c:out value="${stack.name}"/>&nbsp;&times;</span>
												</c:forEach>
												<span class="list-items-function">+</span>
											</span>
											
											<input type="text" list="stacks" class="stacksAddInput" placeholder="SW stack name"/>
											<span class="list-items-function stacksAddBtn">&crarr;</span>
											<span class="list-items-function stacksCancelBtn">&times;</span>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</c:when>
					<c:when test="${adminAction == 'userManagement'}">
						<div id="role-management-container">
						<div id="role-list-container">				
							<div class="col1to3 table-header-no-line">
									U&nbsp;&nbsp;s&nbsp;&nbsp;e&nbsp;&nbsp;r&nbsp;&nbsp;&nbsp;&nbsp;L&nbsp;&nbsp;i&nbsp;&nbsp;s&nbsp;&nbsp;t
							</div>
							<div id="role-list">
								<datalist id="groups">
									<c:forEach items="${userAuthorities}" var="group">
										<option value="${group.groupname}" />
									</c:forEach>
								</datalist>
								<div class="col1 table-header">
									U&nbsp;&nbsp;s&nbsp;&nbsp;e&nbsp;&nbsp;r
								</div>
								<div class="col2 table-header">
									A&nbsp;&nbsp;c&nbsp;&nbsp;t&nbsp;&nbsp;i&nbsp;&nbsp;o&nbsp;&nbsp;n
								</div>
								<div class="col3 table-header">
									G&nbsp;&nbsp;r&nbsp;&nbsp;o&nbsp;&nbsp;u&nbsp;&nbsp;p&nbsp;&nbsp;(&nbsp;&nbsp;s&nbsp;&nbsp;)
								</div>
								<div id="role-list-new-group-name" class="role-list-body col1">
									<input type="text" placeholder="Username" id="userNameInput">
									<input type="text" placeholder="Email" id="emailInput">
								</div>
								<div id="role-list-new-group-save" class="role-list-body col2"><input name="submit" type="submit" id="createUser" value="create new user" /></div>
								<div class="role-list-body col1 separator-row"></div>
								<div class="role-list-body col2 separator-row"></div>
								<div class="role-list-body col3 separator-row"></div>
								<c:forEach items="${userList}" var="user">
									<div class="role-list-body col1"><c:out value="${user.username}"/></div>
									<div data-username="${user.username}" class="role-list-body col2">									
										<input name="resetPW" type="submit" class="resetPasswordButton" value="reset password" />	
										<input name="delete" type="submit" class="removeUserButton" value="delete user"/>
										<input name="alter" type="submit" class="alterAssignmentButton" value="add assignment"/>
									</div>
									<div data-username="${user.username}" class="role-list-body col3">
										<input type="text" list="groups" class="groupNameInput" placeholder="Select group"/>										
										<input type="checkbox" class="ownerInput" name="owner-checkbox" />
										<label for="owner-checkbox" class="checkbox-label">Owner?</label>
									</div>	
									<c:forEach items="${user.groups}" var="group">
										<div data-username="${user.username}" data-groupname="${group.groupname}" class="role-list-body col2">										
											<input name="delete" type="submit" class="removeAssignmentButton" value="remove assignment"/>
										</div>
										<div class="role-list-body col3">
											<c:out value="${group.groupname}" /><c:if test="${group.owner}">&nbsp;(Owner)</c:if>
										</div>	
									</c:forEach>
									<div class="role-list-body col1 separator-row"></div>
									<div class="role-list-body col2 separator-row"></div>
									<div class="role-list-body col3 separator-row"></div>
								</c:forEach>
							</div>
						</div>
					</c:when>
				</c:choose>
				<div id="role-SWStackAssociation-container">
				</div>
				<div id="role-access-rights-container">
				</div>
				<div id="user-role-association-container">
				</div>
			</div>
			<div id="user-management-container">
				<div id="add-new-user-container"></div>
				<div id="current-user-list-container">
				</div>
			</div>
		</div>

	</div>

</body>
</html>