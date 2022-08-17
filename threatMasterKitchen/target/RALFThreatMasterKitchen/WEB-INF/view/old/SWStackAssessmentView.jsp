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
<body style="background-image: none;" onload="prepareFunctions();">
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
					class="context-submenu-btn context-submenu-btn-col13 context-submenu-btn-row1">V&nbsp;&nbsp;u&nbsp;&nbsp;l&nbsp;&nbsp;n&nbsp;&nbsp;e&nbsp;&nbsp;r&nbsp;&nbsp;a&nbsp;&nbsp;b&nbsp;&nbsp;i&nbsp;&nbsp;l&nbsp;&nbsp;i&nbsp;&nbsp;t&nbsp;&nbsp;i&nbsp;&nbsp;e&nbsp;&nbsp;s</p>
				<p id="viewSWStackAssessmentGraphBtn"
					class="context-submenu-btn context-submenu-btn-col1 context-submenu-btn-row2">Graph</p>
				<p id="viewSWStackAssessmentListBtn"
					class="context-submenu-btn context-submenu-btn-col2 context-submenu-btn-row2 active">List</p>
			</div>
		</div>

		<div id="content-container${displayCode}">
			<div id="alert-container"></div>
			<div id="cve-container">
				<c:forEach var="vulnerableComponents" items="${vulnerableComponentsList}">
					<div id="vulnerability-table-container-template" class="vulnerability-table-container" data-component-id="${vulnerableComponents.componentID}">
						<p class="vulnerability-table-title">${vulnerableComponents.vendor} ${vulnerableComponents.product} version: ${vulnerableComponents.version}</p>
						<div class="vulnerability-diagram-container">
							<div id="cve-component-visualization-${vulnerableComponents.componentID}"></div>
							<div id="cve-component-visualization-filters">
								<p class="cve-component-visualization-filters-label">Applied
									filters:</p>
								<p class="cve-component-visualization-filters-value">None</p>
							</div>
						</div>
						<div class="vulnerability-table">
							<div class="vulnerability-table-row">
								<p class="vulnerability-table-aggregated-header-column">
									List of currently known vulnerabilities</p>
							</div>
							<div class="vulnerability-table-row">
								<p class="vulnerability-table-column cve-identifier-string">
									CVE ID</p>
								<p class="vulnerability-table-column cve-uri">Component URI</p>
								<p class="vulnerability-table-column cve-vectorString">
									Vector Summary</p>
								<p class="sortable vulnerability-table-column cve-baseScore">
									CVSS Score <span class="operator">(&ndash;)</span>
								</p>
								<p class="vulnerability-table-column cve-description">
									Description</p>
								<p
									class="alphabeticallySortable vulnerability-table-column cve-attackVector">
									Attack Vector <span class="operator">(&ndash;)</span>
								</p>
								<p
									class="alphabeticallySortable vulnerability-table-column cve-attackComplexity">
									Attack Complexity <span class="operator">(&ndash;)</span>
								</p>
								<p
									class="alphabeticallySortable vulnerability-table-column cve-privilegesRequired">
									Privileges Required <span class="operator">(&ndash;)</span>
								</p>
								<p
									class="alphabeticallySortable vulnerability-table-column cve-userInteraction">
									User Interaction <span class="operator">(&ndash;)</span>
								</p>
								<p
									class="alphabeticallySortable vulnerability-table-column cve-scope">
									Scope <span class="operator">(&ndash;)</span>
								</p>
								<p
									class="alphabeticallySortable vulnerability-table-column cve-confidentialityImpact">
									Confidentiality Impact <span class="operator">(&ndash;)</span>
								</p>
								<p
									class="alphabeticallySortable vulnerability-table-column cve-integrityImpact">
									Integrity Impact <span class="operator">(&ndash;)</span>
								</p>
								<p
									class="alphabeticallySortable vulnerability-table-column cve-availabilityImpact">
									Availability Impact <span class="operator">(&ndash;)</span>
								</p>
								<p
									class="alphabeticallySortable vulnerability-table-column cve-baseSeverity">
									Severity <span class="operator">(&ndash;)</span>
								</p>
								<p
									class="sortable vulnerability-table-column cve-exploitabilityScore">
									Exploitability <span class="operator">(&ndash;)</span>
								</p>
								<p class="sortable vulnerability-table-column cve-impactScore">
									Impact Score <span class="operator">(&ndash;)</span>
								</p>
							</div>
							<div class="vulnerability-table-content-rows">
								<c:forEach var="componentVulnerabilities" items="${vulnerableComponents.componentVulnerabilities}">
									<div class="vulnerability-table-row">
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-identifier-string">
											${componentVulnerabilities.identifierString}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-uri">
											${componentVulnerabilities.uri}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-vectorString">
											${componentVulnerabilities.vectorString}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-baseScore">
											${componentVulnerabilities.baseScore}
										</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-description">
											${componentVulnerabilities.descriptionText}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-attackVector">
											${componentVulnerabilities.attackVector}
										</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-attackComplexity">
											${componentVulnerabilities.attackComplexity}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-privilegesRequired">
											${componentVulnerabilities.privilegesRequired}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-userInteraction">
											${componentVulnerabilities.userInteraction}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-scope">${componentVulnerabilities.scope}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-confidentialityImpact">
											${componentVulnerabilities.confidentialityImpact}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-integrityImpact">
											${componentVulnerabilities.integrityImpact}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-availabilityImpact">
											${componentVulnerabilities.availabilityImpact}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-baseSeverity">${componentVulnerabilities.baseSeverity}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-exploitabilityScore">
											${componentVulnerabilities.exploitabilityScore}</p>
										<p
											class="vulnerability-table-column vulnerability-table-content-column cve-impactScore">${componentVulnerabilities.impactScore}</p>
									</div>								
								</c:forEach>															
							</div>
						</div>
						<div id="attackpattern-table-template" class="attackpattern-table">
							<div class="attackpattern-table-row">
								<p class="attackpattern-table-aggregated-header-column">
									Anticipated possible Attacks</p>
							</div>
							<div class="attackpattern-table-row">
								<p class="attackpattern-table-column capec-id">ID</p>
								<p class="attackpattern-table-column capec-name">Name</p>
								
								<p class="attackpattern-table-column capec-description">Description</p>
							</div>
							<div class="attackpattern-table-content-rows">
								<c:forEach var="attackPatterns" items="${vulnerableComponents.attackPatterns}">
								<div class="attackpattern-table-row">
									<p class="attackpattern-table-column capec-id">${attackPatterns.id}</p>
									<p class="attackpattern-table-column capec-name">${attackPatterns.name}</p>
									
									<p class="attackpattern-table-column capec-description">${attackPatterns.description}</p>
								</div>
								</c:forEach>
								
							</div>
						</div>
					</div>
					
				</c:forEach>

			</div>
		</div>

		<div id="node-data-container"></div>
		<div id="edge-data-container"></div>
		<div id="cve-data-container">
			<c:forEach var="vulnerableComponents"
				items="${vulnerableComponentsList}">
				<c:forEach var="componentVulnerabilities"
					items="${vulnerableComponents.componentVulnerabilities}">
					<ul class="component-${vulnerableComponents.componentID}">
						<li class="year">${componentVulnerabilities.year}</li>
						<li class="id">${componentVulnerabilities.id}</li>
						<li class="identifierString">${componentVulnerabilities.identifierString}</li>
						<li class="vectorString">${componentVulnerabilities.vectorString}</li>
						<li class="attackVector">${componentVulnerabilities.attackVector}</li>
						<li class="attackComplexity">${componentVulnerabilities.attackComplexity}</li>
						<li class="privilegesRequired">${componentVulnerabilities.privilegesRequired}</li>
						<li class="userInteraction">${componentVulnerabilities.userInteraction}</li>
						<li class="scope">${componentVulnerabilities.scope}</li>
						<li class="confidentialityImpact">${componentVulnerabilities.confidentialityImpact}</li>
						<li class="integrityImpact">${componentVulnerabilities.integrityImpact}</li>
						<li class="availabilityImpact">${componentVulnerabilities.availabilityImpact}</li>
						<li class="baseScore">${componentVulnerabilities.baseScore}</li>
						<li class="baseSeverity">${componentVulnerabilities.baseSeverity}</li>
						<li class="exploitabilityScore">${componentVulnerabilities.exploitabilityScore}</li>
						<li class="impactScore">${componentVulnerabilities.impactScore}</li>
						<li class="descriptionText">${componentVulnerabilities.descriptionText}</li>
					</ul>
				</c:forEach>
			</c:forEach>
		</div>
		<div id="capec-data-container"></div>
	</div>

</body>
</html>