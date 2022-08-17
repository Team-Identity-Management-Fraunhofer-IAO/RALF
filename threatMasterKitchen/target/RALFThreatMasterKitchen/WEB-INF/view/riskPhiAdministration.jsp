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
<script src="${context}/js_auth/risk_phi_administration/risk_phi_administration_functions.js"></script>
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
							<li class="organisation"><a href="${context}/AssessmentPreferences/${organization.organization_id}/-1/riskPhiSettings">${organization.organization_name}</a>
								<ul>
									<c:forEach items="${services}" var="service">
										<li><a class="subnet" href="${context}/AssessmentPreferences/${organization.organization_id}/${service.service_id}/riskPhiSettings" data-service-id="${service.service_id}">${service.service_name}</a>
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
					<c:if test="${function2=='riskPhiSettings'}">
						<div class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom">
							
								<div class="w3-row w3-leftbar border-ralf-blue w3-margin-bottom w3-padding">
									<div class="w3-col l12 m12 s12">
										<h4>Weighing of Probability Components</h4>
										<p>The following lever adjusts the weighing of probability components.</p>
										<p>If you move the lever to the left, a stronger weighing of the assessed existence of an attack is used when determining the occurence probability of a risk<br />
										Move the lever more to the left if you foresee that there are a lot of different varying reasons and potential threat agents that would be interested in attacking the organization</p>
										<p>If you move the lever to the right, a stronger weighing of the assessed success likelihood of an attack is used when determining the occurence probability of a risk<br />
										Move the lever more to the right if you foresee that there are fewer reasons and threat agents and you are more interested in mitigating foreseeable ways that your organization could be attacked</p>
									</div>
									<div class="w3-col l12 m12 s12">
										<c:choose>
											<c:when test="${empty riskPhi}">
												<input type="range" min="0" max="100" value="50" name="risk-phi-slider" style="width: 100%" data-type='occurence_probability'/>
											</c:when>
											<c:otherwise>
												<input type="range" min="0" max="100" value="${riskPhi.risk_phi*100}" name="risk-phi-slider" style="width: 100%" data-type='occurence_probability'/>
											</c:otherwise>
										</c:choose>
										
									</div>
									<div class="w3-col l2 m2 s2">
										<p>Only Existence Probability</p>
									</div>
									<div class="w3-col l2 m2 s2">
										<p>Existence Probability more important</p>
									</div>
									<div class="w3-col l4 m4 s4" style="text-align: center">
										<p>Equal importance</p>
									</div>
									<div class="w3-col l2 m2 s2" style="text-align: right">
										<p>Success Probability more important</p>
									</div>
									<div class="w3-col l2 m2 s2" style="text-align: right">
										<p>Only Success Probability</p>
									</div>
								</div>
								
								<div class="w3-row">
									<div class="w3-col" style="text-align:right">
										<button id="submit-probability-weighing" class="w3-button w3-ripple w3-teal">Submit Probability Component Weights</button>
									</div>
								</div>
						</div>
						<div class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom">
							<div class="w3-row w3-leftbar border-ralf-blue w3-margin-bottom w3-padding">
								<div class="w3-col l12 m12 s12">
									<h4>Visualization of Probability Component Weighing</h4>
									<p>The following matrix shows which occurence probability is assessed out of the respective existence and success probabilities</p>
								</div>
								<div class="w3-row">
									<c:choose>
										<c:when test="${empty categoryComputation}">
											<p>Please define risk, impact and probability categories first, before adjusting the probability component weights!</p>
										</c:when>
										<c:otherwise>
												<div class="w3-row">
													<div class="w3-col l1 s1 m1" style="text-align:center">
														Existence Probability
													</div>
												</div>
											<c:forEach items="${categoryComputation}" var="yVal">
												<div class="w3-row">
													<div class="w3-col l1 s1 m1" style="text-align: center">
														${yVal.key.category_name}
													</div>
													<c:forEach items="${yVal.value}" var="xVal">
														
														<div class="w3-col prob-class l1 s1 m1" data-val="${xVal.value.risk_value_max}" style="border: 1px solid #000; text-align: center; margin: -1px -1px 0 0">
															${xVal.value.category_name}
														</div>
													</c:forEach>
												</div>
											</c:forEach>
											<div class="w3-row" >
												<div class="w3-col l1 s1 m1">
													&nbsp;
												</div>
												<c:forEach items="${categoryComputation}" var="xVal" >
													<div class="w3-col l1 s1 m1" style="text-align:center; margin: -1px -1px 0 0;">
														${xVal.key.category_name}
													</div>
												</c:forEach>
												<div class="w3-col l1 s1 m1">
													Success Probability
												</div>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<!-- Risk Phi Settings for Risk Values -->
						<div class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom">
							
								<div class="w3-row w3-leftbar border-ralf-blue w3-margin-bottom w3-padding">
									<div class="w3-col l12 m12 s12">
										<h4>Weighing of Risk Components</h4>
										<p>The following lever adjusts the weighing of Risk components.</p>
										<p>If you move the lever to the left, a stronger weighing of the impact of an attack is used when determining the risk<br />
										Move the lever more to the left if you foresee that there maybe catastrophic damages that are more important to focus on, over the occurence probability of a risk</p>
										<p>If you move the lever to the right, a stronger weighing of the occurence probability is used when determining the risk<br />
										Move the lever more to the right if you foresee that there maybe numerous ways an attack could happen, but no or fewer catastrophic damages.</p>
									</div>
									<div class="w3-col l12 m12 s12">
										<c:choose>
											<c:when test="${empty riskPhiRisk}">
												<input type="range" min="0" max="100" value="50" name="risk-phi-risk-slider" style="width: 100%" data-type='risk'/>
											</c:when>
											<c:otherwise>
												<input type="range" min="0" max="100" value="${riskPhiRisk.risk_phi*100}" name="risk-phi-risk-slider" style="width: 100%" data-type='risk'/>
											</c:otherwise>
										</c:choose>
										
									</div>
									<div class="w3-col l2 m2 s2">
										<p>Only Impact</p>
									</div>
									<div class="w3-col l2 m2 s2">
										<p>Impact more important</p>
									</div>
									<div class="w3-col l4 m4 s4" style="text-align: center">
										<p>Equal importance</p>
									</div>
									<div class="w3-col l2 m2 s2" style="text-align: right">
										<p>Occurence Probability more important</p>
									</div>
									<div class="w3-col l2 m2 s2" style="text-align: right">
										<p>Only Occurence Probability</p>
									</div>
								</div>
						</div>
						<div class="w3-card w3-white w3-margin-right w3-padding w3-small">
							<div class="w3-row w3-leftbar border-ralf-blue w3-margin-bottom w3-padding">
								<div class="w3-col l12 m12 s12">
									<h4>Visualization of Risk Component Weighing</h4>
									<p>The following matrix shows which risks are assessed out of the respective impacts and occurence probabilities</p>
								</div>
								<div class="w3-row">
									<c:choose>
										<c:when test="${empty categoryRiskComputation}">
											<p>Please define risk, impact and probability categories first, before adjusting the probability component weights!</p>
										</c:when>
										<c:otherwise>
												<div class="w3-row">
													<div class="w3-col l1 s1 m1" style="text-align:center">
														Impact
													</div>
												</div>
											<c:forEach items="${categoryRiskComputation}" var="yVal">
												<div class="w3-row">
													<div class="w3-col l1 s1 m1" style="text-align: center">
														${yVal.key.category_name}
													</div>
													<c:forEach items="${yVal.value}" var="xVal">
														
														<div class="w3-col prob-class l1 s1 m1" data-val="${xVal.value.risk_value_max}" style="border: 1px solid #000; text-align: center; margin: -1px -1px 0 0">
															${xVal.value.category_name}
														</div>
													</c:forEach>
												</div>
											</c:forEach>
											<div class="w3-row" >
												<div class="w3-col l1 s1 m1">
													&nbsp;
												</div>
												<c:forEach items="${probabilityCategories}" var="xVal" >
													<div class="w3-col l1 s1 m1" style="text-align:center; margin: -1px -1px 0 0;">
														${xVal.category_name}
													</div>
												</c:forEach>
												<div class="w3-col l1 s1 m1">
													Occurence Probability
												</div>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</c:if>
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