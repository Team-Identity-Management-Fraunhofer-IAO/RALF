<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page isELIgnored="false"%>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>${pageTitle}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="${prefix}/styles_auth/w3.css">
<script src="${prefix}/js_auth/jquery-3.5.0.min.js"></script>
<script src="${prefix}/js_auth/jquery-ui.min.js"></script>
<script src="${prefix}/js_auth/admin_functions.js"></script>

<script>
	// Used to toggle the menu on small screens when clicking on the menu button
	function myFunction() {
	  var x = document.getElementById("navDemo");
	  if (x.className.indexOf("w3-show") == -1) {
	    x.className += " w3-show";
	  } else { 
	    x.className = x.className.replace(" w3-show", "");
	  }
	}
	prefix = "${prefix}";
	
	operatorMetadata = {
	<c:if test="${function=='Harvester'}">
		<c:forEach items="${operatorMetadata}" var="metadataList" varStatus="aLoop">
			'${metadataList.key}':[
			<c:forEach items="${metadataList.value}" var="metadata" varStatus="bLoop">
				'${metadata}'<c:if test="${!bLoop.last}">,</c:if>	
			</c:forEach>
			]<c:if test="${!aLoop.last}">,</c:if>
		</c:forEach>
	</c:if>
	};
</script>
</head>
<body class="w3-light-grey" onload="initializeAdminGUI()">

<%@ include file="menu_bar.jsp" %>"

<!-- Page Container -->
<div class="w3-content w3-margin-top" style="max-width:1400px; min-height: 99vh;">

  <!-- The Grid -->
  <div class="w3-row-padding">
	<div class="w3-col w3-large">
	&nbsp;
	</div>
	<div class="w3-col w3-large">
	&nbsp;
	</div>
	<div class="w3-col w3-large">
	&nbsp;
	</div>
	<div class="w3-col w3-large">
	&nbsp;
	</div>
	
	<div class="w3-row-padding">
		
		<div class="w3-container w3-card w3-white w3-margin-bottom" id="group-management" <c:choose><c:when test="${function=='Groups'}"></c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
			<div class="w3-cell-row">
				<div class="w3-cell">
					<h2 class="w3-text-grey"><i class="w3-margin-right"></i>Group Management</h2>
				</div>
				<div class="w3-cell w3-cell-bottom w3-right  w3-margin-right">
					<button id="show-new-group-editor-btn" class="schedule-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Create new group</button>
				</div>
			</div>
			<div class="w3-cell-row">
				<div class="w3-container w3-margin-bottom" id="new-group-editor" style="display: none;">
					<table class="w3-table w3-bordered w3-small">
						<tr>
							<th>Group</th>
							<th>Role</th>
							<th></th>
						</tr>
						<tr>
							<td><input type="text" placeholder="Name of the new group" name="group-name" class="group-name w3-input"><input type="hidden" name="group-id" class="group-id" value="-1" /></td>
							<td><select name="group-role" class="group-role w3-select">
											<option value="ROLE_SW_STACK_REVIEW" title="Can view application models">Application Reviewer</option>
											<option value="ROLE_SW_STACK_MODELLER" title="Can view, create, modify and delete application models">Application Modeller</option>
											<option value="ROLE_SW_STACK_DEVELOPER" title="Can view, create, modify and delete application models, create and view reports">Application Owner</option>
											<option value="ROLE_SW_STACK_AUDITOR" title="Can view SW Stacks and create and view reports">Application Auditor</option>
											<option value="ROLE_ADMIN" title="This is the superuser group. Use with great caution!">Administrator</option>
										</select></td>
							<td>
								<button id="create-new-group-btn" class="schedule-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Create new group</button>
								<button id="create-new-group-cancel-btn" class="schedule-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Cancel</button>
							</td>
						</tr>
					</table>
					<br />
				</div>
				<div class="w3-container w3-margin-bottom">
				<table class="w3-table w3-bordered w3-small w3-margin-bottom" id="group-overview-container">
					<tr>
						<th>Group</th>
						<th>Role</th>
						<th>Action</th>
					</tr>
					<c:forEach items="${userAuthorities}" var="authority">
						<tr>
							<td><span class="group-name">${authority.groupname}</span></td>
							<td><span class="group-authority" data-value="${authority.role}" title="<c:choose><c:when test="${authority.role=='ROLE_SW_STACK_REVIEW'}">Can view application models</c:when><c:when test="${authority.role=='ROLE_SW_STACK_MODELLER'}">Can view, create, modify and delete application models</c:when><c:when test="${authority.role=='ROLE_SW_STACK_DEVELOPER'}">Can view, create, modify and delete application models, and create and view reports</c:when><c:when test="${authority.role=='ROLE_SW_STACK_AUDITOR'}">Can view SW Stacks and create and view reports</c:when><c:when test="${authority.role=='ROLE_ADMIN'}">This is the superuser group. Use with great caution!</c:when></c:choose>">
							<c:choose>
								<c:when test="${authority.role=='ROLE_SW_STACK_REVIEW'}">
									Application Reviewer
								</c:when>
								<c:when test="${authority.role=='ROLE_SW_STACK_MODELLER'}">
									Application Modeller
								</c:when>
								<c:when test="${authority.role=='ROLE_SW_STACK_DEVELOPER'}">
									Application Owner
								</c:when>
								<c:when test="${authority.role=='ROLE_SW_STACK_AUDITOR'}">
									Application Auditor
								</c:when>
								<c:when test="${authority.role=='ROLE_ADMIN'}">
									Administrator
								</c:when>
							</c:choose>						
							</span>
							<select name="group-role" class="group-role w3-select" style="display:none;">
											<option value="ROLE_SW_STACK_REVIEW" title="Can view application models" <c:if test="${authority.role=='ROLE_SW_STACK_REVIEW'}">selected</c:if>>Application Reviewer</option>
											<option value="ROLE_SW_STACK_MODELLER" title="Can view, create, modify and delete application models" <c:if test="${authority.role=='ROLE_SW_STACK_MODELLER'}">selected</c:if>>Application Modeller</option>
											<option value="ROLE_SW_STACK_DEVELOPER" title="Can view, create, modify and delete application models, and create and view reports" <c:if test="${authority.role=='ROLE_SW_STACK_DEVELOPER'}">selected</c:if>>Application Owner</option>
											<option value="ROLE_SW_STACK_AUDITOR" title="Can view SW Stacks and create and view reports" <c:if test="${authority.role=='ROLE_SW_STACK_AUDITOR'}">selected</c:if>>Application Auditor</option>
											<option value="ROLE_ADMIN" title="This is the superuser group. Use with great caution!" <c:if test="${authority.role=='ROLE_ADMIN'}">selected</c:if> >Administrator</option>
										</select></td>
							<td>
								<button class="edit-group-btn w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Edit group</button>
								<button style="display: none;" class="update-group-btn w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Update group</button>
								<button style="display: none;" class="delete-group-btn w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Remove group</button>
								<button style="display: none;" class="update-group-cancel-btn w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Discard changes</button>
							</td>
						</tr>		
					</c:forEach>			
				</table>			
				<br />
				</div>
			</div>
		</div>
		<!-- Stack Management -->
		<div class="w3-container w3-card w3-white w3-margin-bottom" id="stack-management" <c:choose><c:when test="${function=='Stacks'}"></c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
			<div class="w3-cell-row">
				<div class="w3-cell">
					<h2 class="w3-text-grey"><i class="w3-margin-right"></i>Application Assignments</h2>
				</div>
				
			</div>
			<div class="w3-cell-row">
				<div class="w3-container w3-margin-bottom">
					<table class="w3-table w3-bordered w3-small w3-margin-bottom">
						<tr>
							<th>Group</th>
							<th>Stacks</th>
							<th>Action</th>
						</tr>
						<c:forEach items="${stackList}" var="group">
							<tr>
								<td>
									<span class="group-name">${group.authority.groupname}</span>
									<input type="hidden" name="group-id" class="group-id" value="${group.authority.groupname}" />
								</td>
								<td style="line-height: 22px;">
									<c:forEach items="${group.swStacks}" var="stack">
										<span data-id="${stack.id}" class="w3-round w3-teal w3-tiny stack-item w3-padding-small w3-margin-right">${stack.name} <b>&times;</b></span>
									</c:forEach>
								</td>
								<td>
									<select name="stack-list" class="stack-list w3-select">
										<c:forEach items="${allStacks}" var="stack">
											<option value="${stack.id}">${stack.name}</option>
										</c:forEach>
									</select>
									<button class="add-stack-to-group-btn schedule-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Add Application to Group</button>
								</td>
							</tr>
						</c:forEach>
					</table>			
					<br />
				</div>
			</div>
		</div>
		<!-- End of Stack Management --> 
		<!-- User Management -->
		<div class="w3-container w3-card w3-white w3-margin-bottom" id="user-management" <c:choose><c:when test="${function=='Users'}"></c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
			<div class="w3-cell-row">
				<div class="w3-cell">
					<h2 class="w3-text-grey"><i class="w3-margin-right"></i>User Management</h2>
				</div>
				<div class="w3-cell w3-cell-bottom w3-right  w3-margin-right">
					<button id="create-new-user-btn" class="schedule-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Create new user</button>
				</div>
			</div>
			<div class="w3-cell-row">
				<div class="w3-container w3-margin-bottom" id="new-user-editor" style="display: none;">
					<table class="w3-table w3-bordered w3-small">
						<tr>
							<th>Username</th>
							<th>Email</th>
							<th></th>
						</tr>
						<tr>
							<td><input type="text" placeholder="Please enter a username" name="user-name" class="user-name w3-input"></td>
							<td><input type="email" placeholder="Please enter the email adress" name="user-mail" class="user-mail w3-input"></td>
							<td>
								<button id="add-new-user-btn" class="schedule-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Add new user</button>
								<button id="close-new-user-editor-btn" class="schedule-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Cancel</button>
							</td>
						</tr>
					</table>
					<br />
				</div>
				<div class="w3-container w3-margin-bottom">
					<table class="w3-table w3-bordered w3-small w3-margrin-bottom">
						<tr>
							<td>Username</td>
							<td>Group(s)</td>
							<td>Actions</td>
						</tr>
						<c:forEach items="${userList}" var="user">
						<tr>
							<td class="username">${user.username}</td>
							<td>
								<c:forEach items="${user.groups}" var="group">
									<span data-id="${group.groupname}" class="w3-round w3-teal w3-tiny group-item w3-padding-small w3-margin-right">${group.groupname} <b>&times;</b></span>
								</c:forEach>
							</td>
							<td>
								<div class="w3-row">
									<div class="w3-twothird">
										<select name="user-group-list" class="w3-select user-group-list">
											<c:forEach items="${userAuthorities}" var="group">
												<option value="${group.groupname}">${group.groupname}</option>
											</c:forEach>
										</select>
									</div>
									<div class="w3-third">
										<input type="checkbox" class="w3-check" name="user-group-owner" disabled/><label>Owner of group?</label><br />
										
									</div>
								</div>
								<div class="w3-row">
									<div class="w3-twothird">
										&nbsp;
									</div>
									<div class="w3-third">
										<button style="margin-bottom: 4px !important" class="add-group-to-user-btn w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Add group to user</button>
										<button style="margin-bottom: 4px !important" class="reset-user-password-btn w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Reset Password</button>
										<button style="margin-bottom: 4px !important" class="delete-user-btn w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Delete User</button>
									</div>
								</div>
								
							</td>
						</tr>
						</c:forEach>
					</table>	
					<br />
				</div>
			</div>
		</div>
		<!-- End of User Management -->
		<!-- Begin of Data Harvesting Configuration -->
		<div class="w3-third" <c:choose><c:when test="${function=='Harvester'}"></c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
			<div class="w3-container w3-card w3-white w3-margin-bottom" id="data-management-header">
				<div class="w3-cell-row">
					<div class="w3-cell">
						<h2 class="w3-text-grey"><i class="w3-margin-right"></i>Data Harvesting</h2>
					</div>
					<div class="w3-cell w3-cell-bottom w3-right  w3-margin-right">
						<button id="create-new-data-harvester" class="new-data-harvester-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">New harvesting agent</button>
					</div>
				</div>
				<div class="w3-cell-row">
					<div class="w3-cell">
						<ul class="w3-ul">
							<c:forEach items="${transformationOrderList}" var="transformationOrder">
								<li><a href="./DataHarvesting/${transformationOrder['key']}">${transformationOrder['value']}</a></li>
							</c:forEach>							
						</ul>
						<br />
					</div>
					
				</div>
			</div>
		</div>
		<div class="w3-twothird" <c:choose><c:when test="${function=='Harvester'}"></c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
			<div class="w3-container w3-card w3-white w3-margin-bottom">
				<div class="w3-cell-row">
					<div class="w3-cell">
						<h2 class="w3-text-grey"><i class="w3-margin-right"></i>Execution Schedule</h2>
					</div>
					<div class="w3-rest">
						<table class="w3-table w3-bordered w3-small">
							<tr>
								<th>Execution Recurrence</th>
								<th>Next Execution: ${schedule.nextExecution}</th>
							</tr>
							<tr>
								<td>
									<input type="hidden" name="schedule-id" value="${transformation_order_id}" />
									<select class="w3-select schedule-edit-hours-input" name="schedule-hours">
										<option value="0" <c:if test="${schedule.hours==0}">selected</c:if>>0h</option>
										<option value="1" <c:if test="${schedule.hours==1}">selected</c:if>>1h</option>
										<option value="2" <c:if test="${schedule.hours==2}">selected</c:if>>2h</option>
										<option value="3" <c:if test="${schedule.hours==3}">selected</c:if>>3h</option>
										<option value="4" <c:if test="${schedule.hours==4}">selected</c:if>>4h</option>
										<option value="5" <c:if test="${schedule.hours==5}">selected</c:if>>5h</option>
										<option value="6" <c:if test="${schedule.hours==6}">selected</c:if>>6h</option>
										<option value="7" <c:if test="${schedule.hours==7}">selected</c:if>>7h</option>
										<option value="8" <c:if test="${schedule.hours==8}">selected</c:if>>8h</option>
										<option value="9" <c:if test="${schedule.hours==9}">selected</c:if>>9h</option>
										<option value="10" <c:if test="${schedule.hours==10}">selected</c:if>>10h</option>
										<option value="11" <c:if test="${schedule.hours==11}">selected</c:if>>11h</option>
										<option value="12" <c:if test="${schedule.hours==12}">selected</c:if>>12h</option>
										<option value="13" <c:if test="${schedule.hours==13}">selected</c:if>>13h</option>
										<option value="14" <c:if test="${schedule.hours==14}">selected</c:if>>14h</option>
										<option value="15" <c:if test="${schedule.hours==15}">selected</c:if>>15h</option>
										<option value="16" <c:if test="${schedule.hours==16}">selected</c:if>>16h</option>
										<option value="17" <c:if test="${schedule.hours==17}">selected</c:if>>17h</option>
										<option value="18" <c:if test="${schedule.hours==18}">selected</c:if>>18h</option>
										<option value="19" <c:if test="${schedule.hours==19}">selected</c:if>>19h</option>
										<option value="20" <c:if test="${schedule.hours==20}">selected</c:if>>20h</option>
										<option value="21" <c:if test="${schedule.hours==21}">selected</c:if>>21h</option>
										<option value="22" <c:if test="${schedule.hours==22}">selected</c:if>>22h</option>
										<option value="23" <c:if test="${schedule.hours==23}">selected</c:if>>23h</option>
									</select>
									<select class="w3-select schedule-edit-days-input" name="schedule-days">
										<option value="0" <c:if test="${schedule.days==0}">selected</c:if>>0d</option>
										<option value="1" <c:if test="${schedule.days==1}">selected</c:if>>1d</option>
										<option value="2" <c:if test="${schedule.days==2}">selected</c:if>>2d</option>
										<option value="3" <c:if test="${schedule.days==3}">selected</c:if>>3d</option>
										<option value="4" <c:if test="${schedule.days==4}">selected</c:if>>4d</option>
										<option value="5" <c:if test="${schedule.days==5}">selected</c:if>>5d</option>
										<option value="6" <c:if test="${schedule.days==6}">selected</c:if>>6d</option>
									</select>
									<select class="w3-select schedule-edit-weeks-input" name="schedule-weeks">
										<option value="0" <c:if test="${schedule.weeks==0}">selected</c:if>>0w</option>
										<option value="1" <c:if test="${schedule.weeks==1}">selected</c:if>>1w</option>
										<option value="2" <c:if test="${schedule.weeks==2}">selected</c:if>>2w</option>
										<option value="3" <c:if test="${schedule.weeks==3}">selected</c:if>>3w</option>
									</select>
									<select class="w3-select schedule-edit-months-input" name="schedule-months">
										<option value="0" <c:if test="${schedule.months==0}">selected</c:if>>0m</option>
										<option value="1" <c:if test="${schedule.months==1}">selected</c:if>>1m</option>
										<option value="2" <c:if test="${schedule.months==2}">selected</c:if>>2m</option>
										<option value="3" <c:if test="${schedule.months==3}">selected</c:if>>3m</option>
										<option value="4" <c:if test="${schedule.months==4}">selected</c:if>>4m</option>
										<option value="5" <c:if test="${schedule.months==5}">selected</c:if>>5m</option>
										<option value="6" <c:if test="${schedule.months==6}">selected</c:if>>6m</option>
										<option value="7" <c:if test="${schedule.months==7}">selected</c:if>>7m</option>
										<option value="8" <c:if test="${schedule.months==8}">selected</c:if>>8m</option>
										<option value="9" <c:if test="${schedule.months==9}">selected</c:if>>9m</option>
										<option value="10" <c:if test="${schedule.months==10}">selected</c:if>>10m</option>
										<option value="11" <c:if test="${schedule.months==11}">selected</c:if>>11m</option>
										<option value="12" <c:if test="${schedule.months==12}">selected</c:if>>12m</option>
									</select></td>
								<td><button class="new-harvester-schedule-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">Update Schedule</button></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="w3-twothird" <c:choose><c:when test="${function=='Harvester'}"></c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
			<div class="w3-container w3-card w3-white w3-margin-bottom">
				<div class="w3-cell-row">
					<div class="w3-cell">
						<h2 class="w3-text-grey"><i class="w3-margin-right"></i>${transformation_order_name}</h2>
						<input type="hidden" name="transformation_order_id" value="${transformation_order_id}" />
					</div>
					<div class="w3-cell w3-cell-bottom w3-right  w3-margin-right">
						<button class="execute-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-margin-right">Execute</button>
						<button class="dry-run-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-margin-right">Dry Run</button>
						<i class="w3-margin-right"></i>
						<button class="new-harvester-file-upload-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">New file upload</button>
						<button class="new-harvester-request-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">New request</button>
						<button class="new-harvester-transformation-rule-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">New transformation</button>
						<button class="new-harvester-operator-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small">New action</button>
					</div>
				</div>
				<div id="sortable">
				<div class="w3-cell-row w3-margin-top template-row">
					<div class="w3-border w3-round-large w3-tiny w3-third" id="file-upload-template">					
						<h6 class="w3-text-grey"><i class="w3-margin-right"></i><b><span class="order w3-badge w3-teal">1</span> File Upload</b></h6>
						<table class="w3-table w3-small">
							<tr>
								<td>Name:</td><td><input class="w3-input" name="file-upload-name" />
									<input name="operator-type" value="file_upload" type="hidden" />
								</td>
							</tr>
							<tr>
								<td>Type:</td><td>	<select class="w3-select" name="file_type">
														<option value="CSV">CSV</option>
														<option value="XML">XML</option>
														<option value="JSON">JSON</option>
													</select></td>
							</tr>
							<tr>
								<td>File:</td><td><input class="w3-input" type="file" name="uploadedFile" /></td>
							</tr>
							<tr>
								<td>Data starts at line:</td><td><input class="w3-input" name="first_data_line" value="0" /></td>
							</tr>
						</table>
						<div class="w3-cell-row">
							<button class="save-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Save</button>
							<button class="delete-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Delete</button>
						</div>
						<br />
					</div>
					<div class="w3-border w3-round-large w3-tiny w3-third" id="request-template">					
						<h6 class="w3-text-grey"><i class="w3-margin-right"></i><b><span class="order w3-badge w3-teal">1</span> Request</b></h6>
						<table class="w3-table w3-small">
							<tr>
								<td>Name:</td><td><input class="w3-input" name="request-name" />
									<input name="operator-type" value="request" type="hidden" />
								</td>
							</tr>
							<tr>
								<td>Type:</td><td>	<select class="w3-select" name="request-type">
														<option value="GET">GET</option>
														<option value="POST">POST</option>
													</select></td>
							</tr>
							<tr>
								<td>Endpoint:</td><td><input class="w3-input" name="request-endpoint" /></td>
							</tr>
							<tr>
								<td>Request Body:</td><td><textarea class="w3-input" name="request-body"></textarea></td>
							</tr>
							<tr>
								<td colspan="2">Headers <button class="new-header-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right">New header</button>
							</tr>
							<tr class="request-header-row-template">
								<td><input class="w3-input" name="header-key" /><input type="hidden" name="header-id" value="0" /></td><td><input class="w3-input" name="header-value" /></td>
							</tr>
						</table>
						<div class="w3-cell-row">
							<button class="save-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Save</button>
							<button class="delete-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Delete</button>
						</div>
						<br />
						
					</div>
				
					<div class="w3-border w3-round-large w3-tiny w3-third" id="transformation-template">
						<h6 class="w3-text-grey"><i class="w3-margin-right"></i><b><span class="order w3-badge w3-teal">2</span> Transformation</b></h6>
						<table class="w3-table w3-small">
							<tr>
								<td>Type:</td><td>	<select class="w3-select" name="transformation-type">
														<option value="JSONPATH">JSONPATH</option>
														<option value="XPATH">XPATH</option>
														<option value="LINE_SPLIT">LINE_SPLIT</option>
														<option value="CONSTANT">CONSTANT</option>
													</select>
													<input name="operator-type" value="transformation_rule" type="hidden" />
													</td>
							</tr>
							<tr>
								<td>Rule:</td><td><input class="w3-input" name="transformation-rule" /></td>
							</tr>
							<tr>
								<td>Target:</td><td><input class="w3-input" name="transformation-target" /></td>
							</tr>
						</table>
						<div class="w3-cell-row">
							<button class="save-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Save</button>
							<button class="delete-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Delete</button>
						</div>	
					</div>		
						
					<div class="w3-border w3-round-large w3-tiny w3-third" id="operator-template">
						<h6 class="w3-text-grey"><i class="w3-margin-right"></i><b><span class="order w3-badge w3-teal">5</span> Action</b></h6>
						<table class="w3-table w3-small">
							<tr>
								<td>Type:</td><td>	<select class="w3-select" name="operator-type">
														<c:forEach items="${operatorNames}" var="operatorName">
															<option value="${operatorName.key}">${operatorName.value}</option>
														</c:forEach>
													</select>
													<input name="operator-type" value="operator" type="hidden" />
													</td>
							</tr>
							<tr>
								<td colspan="2">
									<p>Required input attributes (String if not specified)</p>
									<ul class="w3-ul operator-metadata">
										<c:forEach items="${operatorMetadata[operator.operator.operator_type]}" var="metadata">
											<li>${metadata}</li>
										</c:forEach>
									</ul>
								</td>
							</tr>						
						</table>
						<div class="w3-cell-row">
							<button class="save-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Save</button>
							<button class="delete-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Delete</button>
						</div>
					</div>
				</div>
				<c:forEach items="${operators}" var="operator" varStatus="loop">
					<c:if test="${loop.index%3==0}">
						<c:if test="${loop.index>=3}"></div></c:if>
						<div class="sortable-list w3-cell-row w3-margin-top harvester-workflow-row">
					</c:if>
					<c:choose>
						<c:when test="${operator.containerType=='FILE_UPLOAD'}">
							<div class="sortable-item w3-border w3-round-large w3-tiny w3-third harvester-file-upload data-harvesting-function">					
								<h6 class="w3-text-grey"><i class="w3-margin-right"></i><b><span class="order w3-badge w3-teal">${loop.index+1}</span> File Upload</b></h6>
								<table class="w3-table w3-small">
									<tr>
										<td>Name:</td><td><input class="w3-input" name="file-upload-name" value="${operator.fileUpload.file_upload_name}" />
											<input name="operator-type" value="file_upload" type="hidden" />
											<input name="operator-id" value="${operator.fileUpload.operator_id}" type="hidden"/>
										</td>
									</tr>
									<tr>
										<td>Type:</td><td>	<select class="w3-select" name="file_type">
																<option value="CSV" <c:if test="${operator.fileUpload.file_type=='CSV'}">selected</c:if>>CSV</option>
																<option value="XML" <c:if test="${operator.fileUpload.file_type=='XML'}">selected</c:if>>XML</option>
																<option value="JSON" <c:if test="${operator.fileUpload.file_type=='JSON'}">selected</c:if>>JSON</option>
															</select></td>
									</tr>
									<tr>
										<td>File:</td><td><input class="w3-input" type="file" name="uploadedFile" /></td>
									</tr>
									<tr>
										<td>Data starts at line:</td><td><input class="w3-input" name="first_data_line" value="${operator.fileUpload.first_data_line}" /></td>
									</tr>
								</table>
								<div class="w3-cell-row">
									<button class="save-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Save</button>
									<button class="delete-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Delete</button>
								</div>
								<br />
							</div>
						</c:when>
						<c:when test="${operator.containerType=='REQUEST'}">
							<div class="sortable-item w3-border w3-round-large w3-tiny w3-third harvester-request data-harvesting-function">					
								<h6 class="w3-text-grey"><i class="w3-margin-right"></i><b><span class="order w3-badge w3-teal">${loop.index+1}</span> Request</b></h6>
								<table class="w3-table w3-small">
									<tr>
										<td>Name:</td>
										<td>
											<input class="w3-input" name="request-name" value="${operator.request.request_name}"/>
											<input name="operator-id" value="${operator.request.operator_id}" type="hidden"/>
											<input name="operator-type" value="request" type="hidden" />
										</td>
									</tr>
									<tr>
										<td>Type:</td><td>	<select class="w3-select" name="request-type">
																<option value="GET" <c:if test="${operator.request.request_type=='GET'}">selected</c:if>>GET</option>
																<option value="POST" <c:if test="${operator.request.request_type=='POST'}">selected</c:if>>POST</option>
															</select></td>
									</tr>
									<tr>
										<td>Endpoint:</td><td><input class="w3-input" name="request-endpoint" value="${operator.request.endpoint}"/></td>
									</tr>
									<tr>
										<td>Request Body:</td><td><textarea class="w3-input" name="request-body">${operator.request.body}</textarea></td>
									</tr>
									<tr>
										<td colspan="2">Headers <button class="new-header-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right">New header</button>
									</tr>
									<tr class="request-header-row-template">
										<td><input class="w3-input" name="header-key" /><input type="hidden" name="header-id" value="0" /></td><td><input class="w3-input" name="header-value" /></td>
									</tr>
									<c:forEach items="${headerMap[operator.request.operator_id]}" var="headerProperty">
										<tr class="request-header-row">
											<td><input class="w3-input" name="header-key" value="${headerProperty.header_property_key}"/><input type="hidden" name="header-id" value="${headerProperty.header_property_id}" /></td><td><input class="w3-input" name="header-value"  value="${headerProperty.header_property_value}"/></td>
										</tr>
									</c:forEach>
								</table>
								<div class="w3-cell-row">
									<button class="save-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Save</button>
									<button class="delete-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Delete</button>
								</div>
								<br />						
							</div>
						</c:when>
						<c:when test="${operator.containerType=='TRANSFORMATION_RULE'}">
							<div class="sortable-item w3-border w3-round-large w3-tiny w3-third harvester-transformation data-harvesting-function">
								<h6 class="w3-text-grey"><i class="w3-margin-right"></i><b><span class="order w3-badge w3-teal">${loop.index+1}</span> Transformation</b></h6>
								<table class="w3-table w3-small">
									<tr>
										<td>Type:</td><td>	<select class="w3-select" name="transformation-type">
																<option value="JSONPATH" <c:if test="${operator.transformationRule.rule_type=='JSONPATH'}">selected</c:if>>JSONPATH</option>
																<option value="XPATH" <c:if test="${operator.transformationRule.rule_type=='XPATH'}">selected</c:if>>XPATH</option>
																<option value="LINE_SPLIT" <c:if test="${operator.transformationRule.rule_type=='LINE_SPLIT'}">selected</c:if>>LINE_SPLIT</option>
																<option value="CONSTANT" <c:if test="${operator.transformationRule.rule_type=='CONSTANT'}">selected</c:if>>CONSTANT</option>
															</select>
															<input name="operator-id" value="${operator.transformationRule.operator_id}" type="hidden"/>
															<input name="operator-type" value="transformation_rule" type="hidden" /></td>
									</tr>
									<tr>
										<td>Rule:</td><td><input class="w3-input" name="transformation-rule" value="${operator.transformationRule.rule}"/></td>
									</tr>
									<tr>
										<td>Target:</td><td><input class="w3-input" name="transformation-target" value="${operator.transformationRule.target}"/></td>
									</tr>
								</table>
								<div class="w3-cell-row">
									<button class="save-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Save</button>
									<button class="delete-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Delete</button>
								</div>	
							</div>		
						</c:when>
						<c:when test="${operator.containerType=='OPERATOR'}">
							<div class="sortable-item w3-border w3-round-large w3-tiny w3-third harvester-operator data-harvesting-function">
								<h6 class="w3-text-grey"><i class="w3-margin-right"></i><b><span class="order w3-badge w3-teal">${loop.index+1}</span> Action</b></h6>
								<table class="w3-table w3-small">
									<tr>
										<td>Type:</td><td>	<select class="w3-select" name="operator-type">
																<c:forEach items="${operatorNames}" var="operatorName">
																	<option value="${operatorName.key}" <c:if test="${operatorName.key==operator.operator.operator_type}">selected</c:if>>${operatorName.value}</option>
																</c:forEach>
															</select>
															<input name="operator-id" value="${operator.operator.operator_id}" type="hidden"/>
															<input name="operator-type" value="operator" type="hidden" />
															</td>
															
									</tr>
									<tr>
										<td colspan="2">
											<p>Required input attributes (String if not specified)</p>
											<ul class="w3-ul operator-metadata">
												<c:forEach items="${operatorMetadata[operator.operator.operator_type]}" var="metadata">
													<li>${metadata}</li>
												</c:forEach>
												
											</ul>
										</td>
									</tr>						
								</table>
								<div class="w3-cell-row">
									<button class="save-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Save</button>
									<button class="delete-button w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-right w3-margin-right">Delete</button>
								</div>
							</div>
						</c:when>
					</c:choose>
				</c:forEach>
				</div>
				</div>
				<br class="last-break"/>
			</div>
		</div>
		<!-- End of Data Harvesting Configuration -->
	</div>
  <!-- End Grid -->
  </div>
  
  <!-- End Page Container -->
</div>

<footer class="w3-container w3-teal w3-center w3-margin-top">
  <p>RALF - Risk Analysis Platform</p>
  <p></p>
</footer>


</body></html>