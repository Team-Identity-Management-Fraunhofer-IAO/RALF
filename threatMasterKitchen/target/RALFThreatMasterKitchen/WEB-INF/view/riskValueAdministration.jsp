<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script src="${context}/js_auth/general/context_menu_service_modelling.js"></script>
<script src="${context}/js_auth/risk_value_administration/risk_value_administration_functions.js"></script>
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
					<ul>
						<c:forEach items="${organizations}" var="organization"> 
							<li class="organisation"><a href="${context}/AssessmentPreferences/${organization.organization_id}/-1/riskValueSettings">${organization.organization_name}</a>
								<ul>
									<c:forEach items="${services}" var="service">
										<li><a class="subnet" href="${context}/AssessmentPreferences/${organization.organization_id}/${service.service_id}/riskValueSettings" data-service-id="${service.service_id}">${service.service_name}</a>
											<ul>
												<c:forEach items="${risks_per_service[service.service_id]}" var="business_risk">
													<li><a class="stack" data-business-risk-id="${business_risk.business_risk_id}" data-service-id="${service.service_id}" href="${context}/ServiceDefinition/ServiceModelling/${service.service_id}/RiskIdentification/${business_risk.business_risk_id}">${business_risk.business_risk_name}</a></li>
												</c:forEach>
											</ul>
										</li>									
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
						
					</ul>
				</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				
	    		<%@ include file="submenu_bar.jsp" %>
				<div class="w3-col l10 m8 s6 w3-light-grey" style="padding-left: 16px; min-height: 99vh;">
					
					<div class="w3-card w3-white w3-margin-right w3-padding w3-small">
						<c:if test="${function2=='riskValueSettings'}">
							<div class="w3-row w3-leftbar border-ralf-blue w3-padding w3-margin-bottom">
								<div class="w3-col l12 m12 s12">
									<h4>Risk Classes</h4>
								</div>
								<div class="w3-col l2 s2 m2">&nbsp;</div>
								<div class="w3-col l10 s10 m10">
									<div style="width:500px; height: 20px; border: 1px solid #000; border-top:0px; padding: 0px" class="w3-margin-bottom">
										<div style="width:49px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">1%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">10%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">20%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">30%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">40%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">50%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">60%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">70%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">80%</div>
										<div style="width:49px; height: 10px; float: left; margin-top: 9px; font-size:6pt;">90%</div>
									</div>
								</div>
								<div class="w3-col l2 s2 m2"><label><input type="number" class="risk_classes_number" min="1" max="9" value="1">Number of classes</label></div>
								<div class="w3-col l10 s10 m10 risk-classes risk-classification" data-bundle-id="-1" data-origin-number="1">
									<c:choose>
										<c:when test="${empty riskCategories}">
											<div class="risk-classes-template risk-class" style="width:500px;float:left;background-color:rgb(0,255,0)" data-from="1" data-to="100"data-id="-1">
												<div class="risk-classes-adjustor" style="width: 5px; cursor: col-resize; float:left;">&nbsp;</div>
												<input type="text" name="risk_class_name" placeholder="name me!" style="background-color:rgb(0,255,0); border:0;"/>
											</div>
										</c:when>
										<c:otherwise>
											<c:forEach items="${riskCategories}" var="riskCategory" varStatus="loop">
												<c:set var="templateClass" value="" />
												<c:if test="${loop.index==0}">
													<c:set var="templateClass" value="risk-classes-template " />
												</c:if>
												<c:set var="width" value="${(500*(riskCategory.risk_value_max-riskCategory.risk_value_min+1))/100}" />
												<div class="${templateClass}risk-class" style="width:${width}px;float:left;background-color:rgb(0,255,0)" data-from="${riskCategory.risk_value_min}" data-to="${riskCategory.risk_value_max}" data-id="-1">
													<div class="risk-classes-adjustor" style="width: 5px; cursor: col-resize; float:left;">&nbsp;</div>
													<input type="text" name="risk_class_name" placeholder="name me!" style="width:${width-6}px; background-color:rgb(0,255,0); border:0;" value="${riskCategory.category_name}"/>
												</div>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									
																		
								</div>
							</div>
							<div class="w3-row w3-leftbar border-ralf-blue w3-padding w3-margin-bottom">
								<div class="w3-col l12 m12 s12">
									<h4>Impact Classes</h4>
								</div>
								<div class="w3-col l2 s2 m2">&nbsp;</div>
								<div class="w3-col l10 s10 m10">
									<div style="width:500px; height: 20px; border: 1px solid #000; border-top:0px; padding: 0px" class="w3-margin-bottom">
										<div style="width:49px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">1%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">10%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">20%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">30%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">40%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">50%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">60%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">70%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">80%</div>
										<div style="width:49px; height: 10px; float: left; margin-top: 9px; font-size:6pt;">90%</div>
									</div>
								</div>
								<div class="w3-col l2 s2 m2"><label><input type="number" class="risk_classes_number" min="1" max="9" value="1">Number of classes</label></div>
								<div class="w3-col l10 s10 m10 risk-classes impact-classification" data-bundle-id="-1" data-origin-number="1">
									<c:choose>
										<c:when test="${empty impactCategories}">
											<div class="risk-classes-template risk-class" style="width:500px;float:left;background-color:rgb(0,255,0)" data-from="1" data-to="100"data-id="-1">
												<div class="risk-classes-adjustor" style="width: 5px; cursor: col-resize; float:left;">&nbsp;</div>
												<input type="text" name="risk_class_name" placeholder="name me!" style="background-color:rgb(0,255,0); border:0;"/>
											</div>
										</c:when>
										<c:otherwise>
											<c:forEach items="${impactCategories}" var="riskCategory" varStatus="loop">
												<c:set var="templateClass" value="" />
												<c:if test="${loop.index==0}">
													<c:set var="templateClass" value="risk-classes-template " />
												</c:if>
												<c:set var="width" value="${(500*(riskCategory.risk_value_max-riskCategory.risk_value_min+1))/100}" />
												<div class="${templateClass}risk-class" style="width:${width}px;float:left;background-color:rgb(0,255,0)" data-from="${riskCategory.risk_value_min}" data-to="${riskCategory.risk_value_max}" data-id="-1">
													<div class="risk-classes-adjustor" style="width: 5px; cursor: col-resize; float:left;">&nbsp;</div>
													<input type="text" name="risk_class_name" placeholder="name me!" style="width:${width-6}px; background-color:rgb(0,255,0); border:0;" value="${riskCategory.category_name}"/>
												</div>
											</c:forEach>
										</c:otherwise>
									</c:choose>									
								</div>
							</div>
							<div class="w3-row w3-leftbar border-ralf-blue w3-padding w3-margin-bottom">
								<div class="w3-col l12 m12 s12">
									<h4>Probability Classes</h4>
								</div>
								<div class="w3-col l2 s2 m2">&nbsp;</div>
								<div class="w3-col l10 s10 m10">
									<div style="width:500px; height: 20px; border: 1px solid #000; border-top:0px; padding: 0px" class="w3-margin-bottom">
										<div style="width:49px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">1%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">10%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">20%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">30%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">40%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">50%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">60%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">70%</div>
										<div style="width:50px; height: 10px; border-right: 1px solid #000; float: left; margin-top: 9px; font-size:6pt;">80%</div>
										<div style="width:49px; height: 10px; float: left; margin-top: 9px; font-size:6pt;">90%</div>
									</div>
								</div>
								<div class="w3-col l2 s2 m2"><label><input type="number" class="risk_classes_number" min="1" max="9" value="1">Number of classes</label></div>
								<div class="w3-col l10 s10 m10 risk-classes probability-classification" data-bundle-id="-1" data-origin-number="1">
									<c:choose>
										<c:when test="${empty probabilityCategories}">
											<div class="risk-classes-template risk-class" style="width:500px;float:left;background-color:rgb(0,255,0)" data-from="1" data-to="100"data-id="-1">
												<div class="risk-classes-adjustor" style="width: 5px; cursor: col-resize; float:left;">&nbsp;</div>
												<input type="text" name="risk_class_name" placeholder="name me!" style="background-color:rgb(0,255,0); border:0;"/>
											</div>
										</c:when>
										<c:otherwise>
											<c:forEach items="${probabilityCategories}" var="riskCategory" varStatus="loop">
												<c:set var="templateClass" value="" />
												<c:if test="${loop.index==0}">
													<c:set var="templateClass" value="risk-classes-template " />
												</c:if>
												<c:set var="width" value="${(500*(riskCategory.risk_value_max-riskCategory.risk_value_min+1))/100}" />
												<div class="${templateClass}risk-class" style="width:${width}px;float:left;background-color:rgb(0,255,0)" data-from="${riskCategory.risk_value_min}" data-to="${riskCategory.risk_value_max}" data-id="-1">
													<div class="risk-classes-adjustor" style="width: 5px; cursor: col-resize; float:left;">&nbsp;</div>
													<input type="text" name="risk_class_name" placeholder="name me!" style="width:${width-6}px; background-color:rgb(0,255,0); border:0;" value="${riskCategory.category_name}"/>
												</div>
											</c:forEach>
										</c:otherwise>
									</c:choose>									
								</div>
							</div>
							<div class="w3-row">
								<div class="w3-col" style="text-align:right">
									<button id="submit-risk-classes" class="w3-button w3-ripple w3-teal">Submit Risk Classification</button>
								</div>
							</div>
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