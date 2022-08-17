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
<script src="${context}/js_auth/existence_probability_questionnaire/existence_probability_questionnaire_functions.js"></script>
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
							<li class="organisation">${organization.organization_name}
								<ul>
									<c:forEach items="${services}" var="service">
										<li><a class="subnet" href="${context}/ServiceDefinition/ServiceModelling/${service.service_id}" data-service-id="${service.service_id}">${service.service_name}</a>
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
						
						<c:if test="${function2=='existenceProbabilityQuestionnaire'}">
							<input type="hidden" name="organization_id" value="${organization_id}" />
							<input type="hidden" name="service_id" value="${service_id}" />
							<div class="w3-row w3-padding">
								<div class="w3-col l3 s3 m3">
									<h3>Attack motivating factors</h3>
									<span>Please answer the following questions</span>
								</div>
								<div class="w3-col l9 s9 m9">
									&nbsp;
								</div>
								<c:forEach items="${attackMotivatingQuestions}" var="attackMotivatingQuestion">
									<div class="w3-col">
										<div class="w3-row w3-margin-bottom attack-motivating-question-container" data-attack-motivating-factor-id="${attackMotivatingQuestion.attack_motivating_factor_id}" data-attack-motivating-factor-question-id="${attackMotivatingQuestion.attack_motivating_factor_question_id}">
											<div class="w3-col l3 s3 m3">
												<span>${attackMotivatingQuestion.attack_motivating_factor_question}?</span>
											</div>
											<div class="w3-col l3 s3 m3">
												<label><input type="radio" name="attack_motivating_response_${attackMotivatingQuestion.attack_motivating_factor_id}" value="Yes" <c:if test="${attackMotivatingResponses[attackMotivatingQuestion.attack_motivating_factor_id].response}">checked</c:if>>Yes / Maybe</label>
												<label><input type="radio" name="attack_motivating_response_${attackMotivatingQuestion.attack_motivating_factor_id}" value="No" <c:if test="${not attackMotivatingResponses[attackMotivatingQuestion.attack_motivating_factor_id].response}">checked</c:if>>No</label>
											</div>
											<div class="w3-col l6 s6 m6">
												<textarea name="attack_motivating_justification_${attackMotivatingQuestion.attack_motivating_factor_id}" placeholder="Please provide a justification for your selection">${attackMotivatingResponses[attackMotivatingQuestion.attack_motivating_factor_id].justification}</textarea>
											</div>
										</div>
									</div>								
								</c:forEach>			
							</div>
							
							<div class="w3-row w3-padding">
								<div class="w3-col l3 s3 m3">
									<h3>Vulnerability enabling factors</h3>
									<span>Please answer the following questions</span>
								</div>
								<div class="w3-col l9 s9 m9">
									&nbsp;
								</div>
								<c:forEach items="${vulnerabilityEnablingQuestions}" var="vulnerabilityEnablingQuestion">
									<div class="w3-col">
										<div class="w3-row w3-margin-bottom vulnerability-enabling-question-container" data-vulnerability-enabling-factor-id="${vulnerabilityEnablingQuestion.vulnerability_enabling_factor_question_id}" data-vulnerability-enabling-factor-question-id="${vulnerabilityEnablingQuestion.vulnerability_enabling_factor_question_id}">
											<div class="w3-col l3 s3 m3">
												<span>${vulnerabilityEnablingQuestion.vulnerability_enabling_factor_question}?</span>
											</div>
											<div class="w3-col l3 s3 m3">
												<label><input type="radio" name="vulnerability_enabling_response_${vulnerabilityEnablingQuestion.vulnerability_enabling_factor_question_id}" value="Yes" <c:if test="${vulnerabilityEnablingResponses[vulnerabilityEnablingQuestion.vulnerability_enabling_factor_question_id].response}">checked</c:if>>Yes / Maybe</label>
												<label><input type="radio" name="vulnerability_enabling_response_${vulnerabilityEnablingQuestion.vulnerability_enabling_factor_question_id}" value="No" <c:if test="${not vulnerabilityEnablingResponses[vulnerabilityEnablingQuestion.vulnerability_enabling_factor_question_id].response}">checked</c:if>>No</label>
											</div>
											<div class="w3-col l6 s6 m6">
												<textarea name="vulnerability_enabling_justification_${vulnerabilityEnablingQuestion.vulnerability_enabling_factor_question_id}" placeholder="Please provide a justification for your selection">${vulnerabilityEnablingResponses[vulnerabilityEnablingQuestion.vulnerability_enabling_factor_question_id].justification}</textarea>
											</div>
										</div>
									</div>								
								</c:forEach>								
							</div>
							
							<div class="w3-col" style="text-align:right">
								<button id="submit-existence-probability-questionnaire-btn" class="w3-button w3-ripple w3-teal">Update</button>
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