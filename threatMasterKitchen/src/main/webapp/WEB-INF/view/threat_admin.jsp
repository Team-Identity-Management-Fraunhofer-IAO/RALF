<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page isELIgnored="false"%>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="${context}/styles_auth/w3.css">
<script src="${context}/js_auth/jquery-3.5.0.min.js"></script>
<script src="${context}/js_auth/progressBar.js"></script>
<script src="${context}/js_auth/cytoscape.min.js"></script>
<script src="${context}/js_auth/general/context_menu_service_modelling.js"></script>
<script src="${context}/js_auth/threat_administration/threat_administration_functions.js"></script>
</head>
<body class="w3-light-grey" onload="init();">
	<%@ include file="menu_bar.jsp"%>
	
	<!-- Page Container -->
	<div class="w3-margin-top loading" id="content-container"
		style="max-width: auto; min-height: 99vh;">

		<!-- The Grid -->
		<div class="w3-row">
			
			<div class="w3-row" style="background-color: #e7e9eb;">
				<div id="context-menu-stack" class="w3-card w3-dark-gray">
					<ul class="w3-ul w3-hoverable">
						<li><a id="edit-risk-button" data-context="${context}">Edit Business Risk</a></li>
						<li>Delete Business Risk</li>
						<li><a id="add-new-risk-button" data-context="${context}">Create new Business Risk</a></li>
					</ul>
				</div>
				<div id="context-menu-service" class="w3-card w3-dark-gray">
					<ul class="w3-ul w3-hoverable">
						<li><a id="edit-service-button" data-context="${context}">Edit Service</a></li>
						<li>Delete Service</li>
						<li><a id="add-new-risk-button" data-context="${context}">Create new Business Risk</a></li>
					</ul>
				</div>
				<div id="context-menu-organization" class="w3-card w3-dark-gray">
					<ul class="w3-ul w3-hoverable">
						<li>Edit Organization</li>
						<li>Delete Organization</li>
						<li><a href="${context}/ServiceDefinition/ServiceModelling/-1">Add Business Service to Organization</a></li>
					</ul>
				</div>
				<div class="w3-col l2 m4 s6 stack-navigation" style="min-height: 200px; position: sticky; top: 85px; max-height: 93vh; overflow: auto;">
					<c:if test="${function2=='success_probabilities'}">
						<select name="collection_selector" class="w3-select">
							<c:forEach items="${collections}" var="collection">
								<option value="${collection.collection_id}">${collection.collection_name}</option>
							</c:forEach>
						</select>
						<c:if test="${function3=='collection_selected'}">
							<ul>
								<c:forEach items="${patternsPerKillChainPhase}" var="kill_chain_attack_pattern">
									<li><p>${kill_chain_attack_pattern.key.phase_name}</p>
										<ul>
											<c:forEach items="${kill_chain_attack_pattern.value}" var="attack_pattern">
												<li><label></label><input type="checkbox" name="pattern_${attack_pattern.attack_pattern_id}" class="pattern-checkbox pattern_${attack_pattern.attack_pattern_id}" data-id="${attack_pattern.attack_pattern_id}"/>${attack_pattern.attack_pattern_name}</li></label>
											</c:forEach>
										</ul>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</c:if>
					
				</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				
	    		<%@ include file="submenu_bar.jsp" %>
				<div class="w3-col l10 m8 s6 w3-light-grey" style="padding-left: 16px; min-height: 99vh;">
					
					<div class="w3-card w3-white w3-margin-right w3-padding w3-small">
						<c:if test="${function2=='platform_management'}">
								<c:if test="${not empty assignmentsPerPattern}">
									<c:set var="current_collection" value="" />
									<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
										<c:forEach items="${assignmentsPerPattern}" var="attackPattern">
											<div class="w3-col w3-border-bottom l12 s12 m12">
												<c:if test="${current_collection!=attackPattern.value[0].x_mitre_platforms_collection_id}">
													<c:set var="current_collection" value="${attackPattern.value[0].x_mitre_platforms_collection_id}" />
													<h2>${attackPattern.value[0].x_mitre_platforms_collection_id}</h2>
												</c:if>
												<div class="w3-row">
													<div class="w3-col l12 m12 s12">
														<p><b>${attackPattern.value[0].attack_pattern_name}</b></p>
														<p><i>${attackPattern.value[0].attack_pattern_description}</i>
													</div>
													<div class="w3-col l12 m12 s12">
														
															<form method="POST" action="${context}/ThreatAdministration/PlatformManagement/updatePlatforms" class="update-platform-form">
																<input type="hidden" name="_csrf" value="${_csrf.token}" />
																<input type="hidden" name="_csrf_header" value="${_csrf.headerName}" />
																<input type="hidden" name="attack_pattern_id" value="${attackPattern.value[0].attack_pattern_id}" />
																<c:set var="platforms" value="" />
																<c:forEach items="${attackPattern.value}" var="platform" varStatus="loop">
																	<c:set var="platforms" value="${platforms}${loop.first?'':','}${platform.x_mitre_platforms_x_mitre_platform}" />																	
																</c:forEach>										
																<input type="text" name="platforms" value="${platforms}" style="display:block; width: 60%"/>
																<input class="w3-button w3-ripple w3-teal w3-right update-platform-btn" type="submit" value="Update"/>	
															</form>
														
													</div>
												</div>
											</div>
										</c:forEach>
									</div>
								</c:if>
						</c:if>	
						<c:if test="${function2=='success_probabilities'}">
							<c:forEach items="${allPatterns}" var="pattern">
								<div class="w3-row w3-padding w3-leftbar border-ralf-blue pattern-box w3-margin-bottom" id="pattern_box_${pattern.value[0].attack_pattern_id}" data-id="${pattern.value[0].attack_pattern_id}">
									<div class="w3-col l12 m12 s12">
										<h4>${pattern.value[0].attack_pattern_name}</h4>
										<p>${pattern.value[0].attack_pattern_description}</p>
									</div>
									<div class="w3-col l1 m1 s1 w1-padding">
										<span class="w3-badge w3-teal w3-border w3-border-white add-new-probability-btn" data-id="${pattern.value[0].attack_pattern_id}">
											<b>&plus;</b>
										</span>
									</div>
									<div class="w3-col l3 m3 s3 w3-padding">
										<c:forEach items="${pattern.value}" var="control">
											<span class="w3-tag w3-round w3-teal w3-border w3-border-white" data-id="${control.course_of_action_id}">
												${control.course_of_action_name} <a class="help-btn control-help-btn">&quest;</a><a class="add-btn control-add-btn">&plus;</a>
											</span>
											<div id="course_of_action_descriptor_${control.course_of_action_id}" class="w3-card w3-white w3-padding course_of_action_descriptor" style="position: absolute; overflow: auto; width: 250px; height: 400px;">
												<a style="float:right" class="control-close-btn">&times;</a>
												<p><br /><br />${control.course_of_action_description}</p>
											</div>
										</c:forEach>
									</div>
									<div class="w3-col l4 s4 m4 w3-padding">
										<c:forEach items="${enablingFactors}" var="factor">
											<span class="w3-tag w3-round w3-teal w3-border w3-border-white" data-id="${factor.c_vulnerability_enabling_factor_id}">
												${factor.vulnerability_enabling_factor_name} <a class="help-btn factor-help-btn">&quest;</a><a class="add-btn factor-add-btn">&plus;</a>
											</span>
											<div id="factor_descriptor_${factor.c_vulnerability_enabling_factor_id}" class="w3-card w3-white w3-padding factor_descriptor" style="position: absolute; overflow: auto; width: 250px; height: 400px;">
												<a style="float:right" class="factor-close-btn">&times;</a>
												<p><br /><br />${factor.vulnerability_enabling_factor_description}</p>
											</div>
										</c:forEach>
									</div>
									
									<c:if test="${probabilityIDs.containsKey(pattern.value[0].attack_pattern_id)}">
										<div class="w3-row probability-box header-box">
											<div class="w3-col l1 m1 s1">
												<p><b>Action</b></p>
											</div>
											<div class="w3-col l3 m3 s3">
												<p><b>Given Controls</b></p>
											</div>
											<div class="w3-col l3 m3 s3">
												<p><b>Given Enabling Factors</b></p>
											</div>
											<div class="w3-col l5 m5 s5">
												<p><b>Success Probability</b></p>
											</div>
											
										</div>
										<div class="w3-row probability-template-box w3-margin-bottom" data-id="-1">
											<div class="w3-col l1 s1 m1 action-box">
												<a class="probability-action-save-box">Save</a>
												<a class="probability-action-delete-box" style="display:none;">Delete</a>
											</div>
											<div class="w3-col l3 s3 m3 control-box">
												&nbsp;
											</div>
											<div class="w3-col l3 s3 m3 factor-box">
												&nbsp;
											</div>
											<div class="w3-col l5 m5 s5 probability-box">
												<input type="range" min="1" max="100" value="${patternProbabilities[probability_id][0].success_probability_order}" class="success_probability_slider" />
											</div>
										</div>
									<!-- Action | Platforms | Controls | Factors | Probability | Reference -->
										<c:forEach items="${probabilityIDs[pattern.value[0].attack_pattern_id]}" var="probability_id">
											<div class="w3-row probability-box w3-margin-bottom" data-id="${probability_id}">
												<div class="w3-col l1 m1 s1">
													<a class="probability-action-delete-box" data-id="${probability_id}">Delete</a>
												</div>
												<div class="w3-col l3 m3 s3">
													<c:forEach items="${patternProbabilities[probability_id]}" var="item">
														<span class="w3-tag w3-round w3-teal w3-border w3-border-white" data-id="${item.course_of_action_id}">
															${item.course_of_action_name}
														</span>
													</c:forEach>
												</div>
												<div class="w3-col l3 m3 s3">
													<c:forEach items="${patternProbabilities[probability_id]}" var="item">
														<span class="w3-tag w3-round w3-teal w3-border w3-border-white" data-id="${item.c_vulnerability_enabling_factor_id}">
															${item.vulnerability_enabling_factor_name}
														</span>
													</c:forEach>
												</div>
												<div class="w3-col l5 m5 s5">
													<input type="range" min="1" max="100" value="${patternProbabilities[probability_id][0].success_probability_order}" class="success_probability_slider" />
												</div>
											</div>
										</c:forEach>									
									</c:if>									
								</div>
							</c:forEach>
						</c:if>					
					</div>
				</div>
				<div class="w3-col l2 m4 s6" style="background-color: #e7e9eb;">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
			</div>
			

			
			

			<!-- End Grid -->
		</div>

		<!-- End Page Container -->
	</div>

	<footer class="w3-container w3-teal w3-center">
	<p>RALF - Risk Analysis Platform</p>
	<p></p>
	</footer>


</body>
</html>