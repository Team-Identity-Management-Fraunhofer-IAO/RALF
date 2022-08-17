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
	src="${folderPosition}/js_auth/lib/vis-network.min.js"></script>
	<script type="text/javascript"
	src="${folderPosition}/js_auth/lib/vis-graph3d.min.js"></script>
<script type="text/javascript"
	src="${folderPosition}/js_auth/lib/jquery-3.5.0.min.js"></script>
<script type="text/javascript"
	src="${folderPosition}/js_auth/swStackAssessmentGraph.js"></script>
<script type="text/javascript"
	src="${folderPosition}/js_auth/ajaxLoader.js"></script>
<script type="text/javascript"
	src="${folderPosition}/js_auth/assessmentAjaxFunctions.js"></script>
<script type="text/javascript"
	src="${folderPosition}/js_auth/messages.js"></script>
<script type="text/javascript">
	var swStackId = ${swStackId};
	var title = "${swStackTitle}";
	var applicationName = "${applicationName}";
	var urlLocation = window.location.href.split("/");
	var applicationContext = urlLocation[0] + "//" + urlLocation[2] + "/"
			+ applicationName;
</script>

</head>
<body onload="init();">
	<div id="sidebar" ${shadowCodeLeft}>
		<img src="${folderPosition}/img_auth/ralf_logo.png" id="ralf_logo" />
		<div id="menu-container">
			<ul id="menu">
				<li class="menu disabled">Dashboard</li>
				<li class="menu"><a href="${folderPosition}/SWStackModelling">Modelling</a></li>
				<li class="menu disabled">Threats</li>
				<li class="menu active"><a
					href="${folderPosition}/Assessment/SWStack">Assessments</a></li>
				<li class="menu disabled">Controls</li>
				<li class="menu disabled">Settings</li>
			</ul>
		</div>
		<div class="menu vertical-separator"></div>
		<div id="usr-container">
			<ul id="usr">
				<li class="usr"><img
					src="${folderPosition}/img_auth/avatar.png" id="usrLogo" /><a href="${folderPosition}/user">${principal}</a></li>
				<li class="usr disabled">&nbsp;</li>
				<c:if test="${isAdmin}">
					<li class="usr"><a href="${folderPosition}/admin">Administration</a></li>
				</c:if>
				<li class="usr"><a href="<c:url value="/logout" />">Logoff</a></li>
			</ul>
		</div>

	</div>
	<div id="content">
		<div id="context-menu" ${shadowCodeTop}>
			<p id="context-menu-btn">
				<img id="context-menu-btn-img"
					src="${folderPosition}/img_auth/menu.png" />
			</p>
			<p id="context-menu-title">
				<span></span><img id="swStackEditBtn" class="enabledInline"
					src="${folderPosition}/img_auth/pen_white.png" />
			</p>
			<input id="swStackNameInput" type="text" name="swStackName"
				onchange="submitSWStackName(this)" value="Software Stack Name" /> <input
				id="swStackId" type="hidden" value="-1" />
			<p id="context-menu-close-btn">&times;</p>
		</div>
		<div id="context-hover-menu" ${shadowCodeTop}>
			<ul id="context-hover-menu-content">
				<li class="menu" id="createNewBtn" onclick="createNewSWStack();">Create
					new SW Stack</li> ${swStackSubmenu}
			</ul>
		</div>
		

		<div id="node-data-container"></div>
		<div id="edge-data-container"></div>

		<div id="cve-data-container"></div>
		
		<div id="capec-data-container"></div>
	</div>

</body>
</html>