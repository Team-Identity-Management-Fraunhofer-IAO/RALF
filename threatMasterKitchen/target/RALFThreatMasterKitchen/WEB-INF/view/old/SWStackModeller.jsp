<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" type="text/css" href="${folderPosition}/styles_auth/stylesw3c.css">
<script type="text/javascript" src="${folderPosition}/js_auth/lib/vis-network.min.js"></script>
<script type="text/javascript" src="${folderPosition}/js_auth/lib/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="${folderPosition}/js_auth/swStackEditor.js"></script>
<script type="text/javascript" src="${folderPosition}/js_auth/ajaxLoader.js"></script>
<script type="text/javascript" src="${folderPosition}/js_auth/editorAjaxFunctions.js"></script>
<script type="text/javascript" src="${folderPosition}/js_auth/messages.js"></script>
<script type="text/javascript">
	var swStackId = ${swStackId};
	var title = "${swStackTitle}";
	var applicationName = "${applicationName}";
	var urlLocation = window.location.href.split("/");
	var applicationContext = urlLocation[0]+"//"+urlLocation[2]+"/"+applicationName;
</script>

</head>
<body onload="init();">
	<div id="sidebar" ${shadowCodeLeft}>
		<img src="${folderPosition}/img_auth/ralf_logo.png" id="ralf_logo" /> 
		<div id="menu-container">
			<ul id="menu">
				<li class="menu disabled">Dashboard</li>
				<li class="menu active"><a href="${folderPosition}/SWStackModelling">Modelling</a></li>
				<li class="menu disabled">Threats</li>
				<li class="menu"><a href="${folderPosition}/Assessment/SWStack">Assessments</a></li>
				<li class="menu disabled">Controls</li>
				<li class="menu disabled">Settings</li>
			</ul>
		</div>
		<div class="menu vertical-separator"></div>
		<div id="usr-container">
			<ul id="usr">
				<li class="usr"><img src="${folderPosition}/img_auth/avatar.png" id="usrLogo" /><a href="${folderPosition}/user">${principal}</a></li>
				<li class="usr disabled">&nbsp;</li>
				<c:if test="${isAdmin}">
					<li class="usr"><a href="${folderPosition}/admin">Administration</a></li>
				</c:if>
				<li class="usr"><a href="${folderPosition}/perform_logout">Logoff</a></li>
			</ul>
		</div>
		
	</div>
		<div id="content">
			<div id="context-menu"${shadowCodeTop}>
				<p id="context-menu-btn"><img id="context-menu-btn-img" src="${folderPosition}/img_auth/menu.png" /></p>
				<p id="context-menu-title"><span></span><img id="swStackEditBtn" class="enabledInline" src="${folderPosition}/img_auth/pen_white.png" /></p>
				<!--  onchange="submitSWStackName(this)"  -->
				<input id="swStackNameInput" type="text" name="swStackName" value="" placeholder="Please enter a name for the SW Stack and associate a user group"/>
				<span id="swStackGroupList">G&nbsp;&nbsp;r&nbsp;&nbsp;o&nbsp;&nbsp;u&nbsp;&nbsp;p&nbsp;&nbsp;s&nbsp;&nbsp;: 
					<c:forEach var="stackGroup" items="${stackGroups}">
						<span class="list-item" onclick="removeListItem(this)" data-groupname="${stackGroup.groupname}">
							${stackGroup.groupname}&nbsp;&times;
						</span>
					</c:forEach>
				</span>
				<input id="swStackGroupInput" type="text" name="swStackGroups" list="groups" placeholder="Select a user group" />
				<input id="swStackId" type="hidden" value="-1" />
				<input id="addGroupSubmitInput" type="submit" value="Add Group" />
				<input id="newSWStackSubmitInput" type="submit" value="Save" />
				<p id="context-menu-close-btn">&times;</p>
				<datalist id="groups">
					<c:forEach items="${groups}" var="group">
						<option value="${group.groupname}" />
					</c:forEach>					
				</datalist>
			</div>
			<div id="context-hover-menu"${shadowCodeTop}>
				<ul id="context-hover-menu-content">
					<li class="menu" id="createNewBtn" onclick="createNewSWStack();">Create new SW Stack</li>
					${swStackSubmenu}			
				</ul>
			</div>
			<div id="context-submenu"${displayCode}>
				<div id="context-submenu-btn-grid">
					<p class="context-submenu-btn context-submenu-btn-col13 context-submenu-btn-row1">C&nbsp;&nbsp;o&nbsp;&nbsp;m&nbsp;&nbsp;p&nbsp;&nbsp;o&nbsp;&nbsp;n&nbsp;&nbsp;e&nbsp;&nbsp;n&nbsp;&nbsp;t</p>
					<p id="nodeAddBtn" class="context-submenu-btn context-submenu-btn-col1 context-submenu-btn-row2">Add</p>
					<p id = "nodeDeleteBtn" class="context-submenu-btn context-submenu-btn-col2 context-submenu-btn-row2 disabled">Delete</p>
					<p class="context-submenu-btn context-submenu-btn-col46 context-submenu-btn-row1">D&nbsp;&nbsp;e&nbsp;&nbsp;p&nbsp;&nbsp;e&nbsp;&nbsp;n&nbsp;&nbsp;d&nbsp;&nbsp;e&nbsp;&nbsp;n&nbsp;&nbsp;c&nbsp;&nbsp;y</p>
					<p id="edgeAddBtn" class="context-submenu-btn context-submenu-btn-col4 context-submenu-btn-row2">Add</p>
					<p id = "edgeDeleteBtn" class="context-submenu-btn context-submenu-btn-col5 context-submenu-btn-row2 disabled">Delete</p>
				</div>
			</div>
			
			<div id="content-container${displayCode}">
				<div id="alert-container">
					
				</div>
				<div id="network"></div>
				<div id="property-container">										
					<div id="node-property-container">
						<p id="property-container-title">P&nbsp;&nbsp;r&nbsp;&nbsp;o&nbsp;&nbsp;p&nbsp;&nbsp;e&nbsp;&nbsp;r&nbsp;&nbsp;t&nbsp;&nbsp;i&nbsp;&nbsp;e&nbsp;&nbsp;s&nbsp;&nbsp;&nbsp;&nbsp;<img id="nodePropertyEditBtn" class="enabledInline" src="${folderPosition}/img_auth/pen.png" /></p>
						<form class="node-property-form">
						
						
							<input type="hidden" name="id" value="-1" id="nodeId"/>
							<input type="hidden" name="cpeId" value="0" />
							<label for="typeInput" class="node-property-container-label">Type</label>
							<input list="types" type="text" name="type" id="typeInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id = "typePara" class="inputPara enabled"></p>
							<label for="productInput" class="node-property-container-label">Product</label>
							<input list="products" type="text" name="product" id="productInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="productPara" class="inputPara enabled"></p>
							<label for="vendorInput" class="node-property-container-label">Vendor</label>
							<input list="vendors" type="text" name="vendor" id="vendorInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="vendorPara" class="inputPara enabled"></p>
							<label for="versionInput" class="node-property-container-label">Version</label>
							<div class="versionInputContainer">
								<p id="versionCmpPara">=</p>
								<select name="versionComparator" id="versionCmpInput">
									<option value="gt">&gt;</option>
									<option value="gteq">&gt;=</option>
									<option value="eq" selected>=</option>
									<option value="lteq">&lt;=</option>
									<option value="lt">&lt;</option>
								</select>
							</div>
							<input list="versions" type="text" name="version" id="versionInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="versionPara" class="inputPara enabled"></p>
							<label for="updateInput" class="node-property-container-label">Update</label>
							<input list="updates" type="text" name="update" id="updateInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="updatePara" class="inputPara enabled"></p>
							<label for="editionInput" class="node-property-container-label" title="This field is only for backwards compatibility with old components in the database and is equivalent to Software edition">Edition*</label>
							<input list="editions" type="text" name="edition" id="editionInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="editionPara" class="inputPara enabled"></p>
							<label for="languageInput" class="node-property-container-label">Language</label>
							<input list="languages" type="text" name="language" id="languageInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="languagePara" class="inputPara enabled"></p>
							<label for="sw_editionInput" class="node-property-container-label" title="This field is equivalent to Edition and should be used creating new components in the database">Software Edition*</label>
							<input list="sw_editions" type="text" name="sw_edition" id="sw_editionInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="sw_editionPara" class="inputPara enabled"></p>
							<label for="target_sw" class="node-property-container-label">Target Software</label>
							<input list="target_sws" type="text" name="target_sw" id="target_swInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="target_swPara" class="inputPara enabled"></p>
							<label for="target_hw" class="node-property-container-label">Target Hardware</label>
							<input list="target_hws" type="text" name="target_hw" id="target_hwInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="target_hwPara" class="inputPara enabled"></p>
							<label for="other" class="node-property-container-label">Other</label>
							<input list="others" type="text" name="other" id="otherInput" value="" onchange="loadCPEProposalsByFieldValues(this)" />
							<p id="otherPara" class="inputPara enabled"></p>
							<label for="codebase" class="node-property-container-label">Codebase</label>
							<input type="checkbox" name="codebase" id="codeBaseInput" class="node-property-container-checkbox" value="isCodebase" disabled/>							
							<div id="node-property-container-controls">
								<button id="cancelBtn" type="button" value="Reset">Cancel</button>
								<button id="submitBtn" type="button" value="Submit">Save</button>
							</div>
							<datalist id="types">
							</datalist>
							<datalist id="products">
							</datalist>
							<datalist id="vendors">
							</datalist>
							<datalist id="versions">
							</datalist>
							<datalist id="updates">
							</datalist>
							<datalist id="editions">
							</datalist>
							<datalist id="languages">
							</datalist>
							<datalist id="sw_editions">
							</datalist>
							<datalist id="target_sws">
							</datalist>
							<datalist id="target_hws">
							</datalist>
							<datalist id="others">
							</datalist>
						</form>
						
					</div>
					
				</div>
				
			</div>
			
			<div id="node-data-container">
				
			</div>
			<div id="edge-data-container">
			</div>
		</div>
   
</body>
</html>