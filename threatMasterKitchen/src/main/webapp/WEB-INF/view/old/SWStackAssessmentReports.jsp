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
	src="${folderPosition}/js_auth/ajaxLoader.js"></script>
<script type="text/javascript"
	src="${folderPosition}/js_auth/messages.js"></script>
<script type="text/javascript" 
	src="${folderPosition}/js_auth/reportAjaxFunctions.js"></script>
<script type="text/javascript" 
	src="${folderPosition}/js_auth/reportFunctions.js"></script>
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
				<li class="usr"><a href="${folderPosition}/perform_logout">Logoff</a></li>
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
		<div id="context-submenu" ${displayCode}>
			<div id="context-submenu-btn-grid">
				<p
					class="context-submenu-btn context-submenu-btn-col15 context-submenu-btn-row1">S&nbsp;&nbsp;c&nbsp;&nbsp;h&nbsp;&nbsp;e&nbsp;&nbsp;d&nbsp;&nbsp;u&nbsp;&nbsp;l&nbsp;&nbsp;e&nbsp;&nbsp;&nbsp;&nbsp;R&nbsp;&nbsp;e&nbsp;&nbsp;p&nbsp;&nbsp;o&nbsp;&nbsp;r&nbsp;&nbsp;t</p>
				<p id="create-report-now-btn"
					class="context-submenu-btn context-submenu-btn-col1 context-submenu-btn-row2" onclick="newReport(true,false)">Now</p>
				<p id="create-report-later-btn"
					class="context-submenu-btn context-submenu-btn-col2 context-submenu-btn-row2 disabled"  onclick="newReport(false,false)">Later</p>
				<p id="create-report-recurring-btn"
					class="context-submenu-btn context-submenu-btn-col3 context-submenu-btn-row2 disabled"  onclick="newReport(false,true)">Recurring</p>
			</div>
		</div>

		<div id="content-container${displayCode}">
			<div id="alert-container"></div>
			<div class="report-container report-top">
				<div class="report-title">A&nbsp;&nbsp;s&nbsp;&nbsp;s&nbsp;&nbsp;e&nbsp;&nbsp;s&nbsp;&nbsp;s&nbsp;&nbsp;m&nbsp;&nbsp;e&nbsp;&nbsp;n&nbsp;&nbsp;t&nbsp;&nbsp;&nbsp;&nbsp;S&nbsp;&nbsp;c&nbsp;&nbsp;h&nbsp;&nbsp;e&nbsp;&nbsp;d&nbsp;&nbsp;u&nbsp;&nbsp;l&nbsp;&nbsp;e</div>
				<div class="report-header">
					<div class="report-ID">First Execution</div>
					<div class="report-time">
						L&nbsp;&nbsp;a&nbsp;&nbsp;s&nbsp;&nbsp;t&nbsp;&nbsp;&nbsp;&nbsp;E&nbsp;&nbsp;x&nbsp;&nbsp;e&nbsp;&nbsp;c&nbsp;&nbsp;u&nbsp;&nbsp;t&nbsp;&nbsp;i&nbsp;&nbsp;o&nbsp;&nbsp;n</div>
					<div class="report-explicit">
						S&nbsp;&nbsp;c&nbsp;&nbsp;o&nbsp;&nbsp;p&nbsp;&nbsp;e</div>
					<div class="report-actions">
						R&nbsp;&nbsp;e&nbsp;&nbsp;c&nbsp;&nbsp;u&nbsp;&nbsp;r&nbsp;&nbsp;r&nbsp;&nbsp;e&nbsp;&nbsp;n&nbsp;&nbsp;c&nbsp;&nbsp;e
					</div>
				</div>
				<div class="report" id="report-new">
					<div class="report-time">
						<input type="datetime-local" id="time-input"/>
					</div>
					<div class="report-explicit">
						<select name="explicit" id="explicit-dropdown">
							<option value="true">Only Software Stack</option>
							<option value="false">Additionally on potentially used components</option>
						</select>
					</div>
					<div class="report-actions-view">
						<select name="recurrence" id="recurrence-dropdown">
							<option value="none">No recurrence</option>
							<option value="weekly">Weekly recurrence</option>
							<option value="daily">Daily recurrence</option>
							<option value="6h">Every 6 hours</option>
							<option value="4h">Every 4 hours</option>
							<option value="2h">Every 2 hours</option>
						</select>
					</div>
					<div class="report-actions-delete">
						<a onclick="createReport(${swStackId})" id="report-schedule-btn">Schedule Report</a>
					</div>
				</div>
				<c:forEach var="fact" items="${schedulerFacts}">
					<div class="report">
						<div class="report-ID"><c:out value="${fact.timeString}"/></div>
						<div class="report-time"><c:out value="${fact.lastExecutedTimeString}"/></div>
						<div class="report-explicit">
							<c:choose>
								<c:when test="${fact.explicit}">
									Software Stack only
								</c:when>
								<c:otherwise>
									Additionally on potentially used components
								</c:otherwise>
							</c:choose>
						</div>
						<div class="report-actions-view">
							<c:out value="${fact.recurrence}"/>
						</div>
						<div class="report-actions-delete">
						
						</div>
					</div>
				</c:forEach>
				
			</div>
			<div class="report-container">
				<div class="report-header">
					<div class="report-ID">Report ID</div>
					<div class="report-time">
						T&nbsp;&nbsp;i&nbsp;&nbsp;m&nbsp;&nbsp;e</div>
					<div class="report-explicit">
						S&nbsp;&nbsp;c&nbsp;&nbsp;o&nbsp;&nbsp;p&nbsp;&nbsp;e</div>
					<div class="report-actions">
						A&nbsp;&nbsp;c&nbsp;&nbsp;t&nbsp;&nbsp;i&nbsp;&nbsp;o&nbsp;&nbsp;n&nbsp;&nbsp;s
					</div>
				</div>
				<div class="report" id="report-new">
					<div class="report-time">
						<input type="datetime-local" id="time-input"/>
					</div>
					<div class="report-explicit">
						<select name="explicit" id="explicit-dropdown">
							<option value="true">Only Software Stack</option>
							<option value="false">Additionally on potentially used components</option>
						</select>
					</div>
					<div class="report-actions-view">
						<select name="recurrence" id="recurrence-dropdown">
							<option value="none">No recurrence</option>
							<option value="weekly">Weekly recurrence</option>
							<option value="daily">Daily recurrence</option>
							<option value="6h">Every 6 hours</option>
							<option value="4h">Every 4 hours</option>
							<option value="2h">Every 2 hours</option>
						</select>
					</div>
					<div class="report-actions-delete">
						<a onclick="createReport(${swStackId})" id="report-schedule-btn">Schedule Report</a>
					</div>
				</div>
				<c:forEach var="reports" items="${reportList}">
					<div class="report">
						<div class="report-ID"><c:out value="${reports.reportID}"/></div>
						<div class="report-time"><c:out value="${reports.timeString}"/></div>
						<div class="report-explicit">
							<c:choose>
								<c:when test="${reports.explicit}">
									Software Stack only
								</c:when>
								<c:otherwise>
									Additionally on potentially used components
								</c:otherwise>
							</c:choose>
						</div>
						<div class="report-actions-view">
							<a href="Report/<c:out value="${reports.reportID}"/>">Show Assessment</a>
						</div>
						<div class="report-actions-delete">
							<a href="deleteReport/<c:out value="${reports.reportID}"/>">Delete Assessment</a>
						</div>
					</div>
				</c:forEach>
				
			</div>
		</div>
		<div id="report-data-container"></div>

	</div>

</body>
</html>