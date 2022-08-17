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
<script src="${context}/js_auth/risk_assessment/risk_assessment_gui_functions.js"></script>
</head>
<body class="w3-light-grey" onload="init();">
	<%@ include file="menu_bar.jsp"%>
	
	<!-- Page Container -->
	<div class="w3-margin-top loading" id="content-container"
		style="max-width: auto; min-height: 99vh;">

		<!-- The Grid -->
		<div class="w3-row">
			
			<div class="w3-row" style="background-color: #e7e9eb;">
				
				<div class="w3-col l2 m4 s6 stack-navigation" style="min-height: 200px; position: sticky; top: 85px; max-height: 93vh; overflow: auto;">
					<p>You are currently conducting an assessment for the service <b>${service.service_name}</b></p>
					<ul>
						<c:choose>
							<c:when test="${function3=='checkMitigations'}">
								
								<c:forEach items="${coursesOfAction}" var="alias">
									<li class="list-header"><span>${alias.key}</span>
									<ul>
									<c:forEach items="${alias.value}" var="courseOfAction">
										<li class="notApplyingMitigation"><a class="mitigation" data-mitigation-id="${courseOfAction.key}">${courseOfAction.value}</a></li>
									</c:forEach>
									</ul>
									</li>
								</c:forEach>
							</c:when>
						</c:choose>						
					</ul>
				</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				
				<div class="w3-col l10 m8 s6 w3-light-grey" style="padding-left: 16px; min-height: 99vh;">
					
					<div class="w3-card w3-white w3-margin-right w3-padding w3-small">
						<c:if test="${function2=='risk_questionnaire'}">
							<c:if test="${not empty collections}">
							<form method="GET" action="${context}/RiskAssessment/${service_id}/RiskQuestionnaire/page1" id="service_modelling_form">
							</c:if>
							<c:if test="${not empty platformPerGroup}">
							<form method="GET" action="${context}/RiskAssessment/${service_id}/RiskQuestionnaire/page2" id="service_modelling_form">
							</c:if>
							<c:if test="${not empty coursesOfAction}">
							<form method="GET" action="${context}/RiskAssessment/${service_id}/RiskQuestionnaire/page3" id="service_modelling_form">
							</c:if>
								<!-- <input type="hidden" name="service_id" value="${service_id}" />
								<input type="hidden" name="_csrf" value="${_csrf.token}" />
								<input type="hidden" name="_csrf_header" value="${_csrf.headerName}" /> -->
								
								<div class="w3-row w3-padding w3-margin-bottom">
									<div class="w3-col l11 s11 m11">&nbsp;</div>
									<div class="w3-col l1 s1 m1" style="text-align:right;">
										<c:choose>
											<c:when test="${function3=='checkMitigations'}">
												<input class="w3-button w3-ripple w3-teal mitigation-report-submit-btn" type="submit" value="Update"/>
											</c:when>
											<c:otherwise>
												<input class="w3-button w3-ripple w3-teal" type="submit" value="Update"/>	
											</c:otherwise>
										</c:choose>								
									</div>
								</div>
								<c:if test="${not empty collections}">
									<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
										<div class="w3-col">
											<h3>Service Characterization</h3>
										</div>
										<div class="w3-col">
											<span>The service uses...</span>
										</div>
										<c:forEach items="${collections}" var="collection">
											<div class="w3-col">
												<label for="${collection.collection_id}"><input type="checkbox" id="${collection.collection_id}" value="${collection.collection_id}" name="checkedCollections" />${collection.collection_question}</label>
											</div>
										</c:forEach>
									</div>
									<hr />
								</c:if>
								<c:if test="${not empty platformPerGroup}">
									<input type="hidden" name="checkedCollections" value="${checkedCollections}" />
									<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
										<div class="w3-col l12 s12 m12">
											<span>Which IT Platforms are used in the service?</span>
										</div>
										<c:forEach items="${platformPerGroup}" var="group">										
										<div class="w3-col l6 s6 m6">
											<div class="w3-row">
												<div class="w3-col">
													<h4>${group.key}</h4>
												</div>
												<c:forEach items="${group.value}" var="platform">
												<div class="w3-col l6 s6 m6">
													<label for="${platform}"><input type="checkbox" id="${platform}" value="${platform}" name="checkedPlatforms" class="w3-check"/>${platform}</label>
												</div>
												</c:forEach>
											</div>
											
										</div>
										</c:forEach>
										<c:if test="${not empty platformsWithoutGroup}">
											<div class="w3-col l6 s6 m6">
												<div class="w3-row">
													<div class="w3-col">
														<h4>Others (ungrouped)</h4>
													</div>
													<c:forEach items="${platformsWithoutGroup}" var="platform">
														<div class="w3-col l6 s6 m6">
															<label for="${platform}"><input type="checkbox" id="${platform}" value="${platform}" name="checkedPlatforms" class="w3-check"/>${platform}</label>
														</div>
													</c:forEach>
												</div>
											</div>
										</c:if>
									</div>
									<hr />
								</c:if>
								<c:if test="${function3=='checkMitigations'}">
									<input type="hidden" name="checkedCollections" value="${checkedCollections}" />
									<c:set var="pIDs" value="" />
									<c:forEach items="${platformIDs}" var="platformID" varStatus="loop">
										<c:set var="pIDs" value="${pIDs}${loop.first?'':','}${platformID}" />
									</c:forEach>
									<input type="hidden" name="checkedPlatformIDs" value="${pIDs}" />
									<c:forEach items="${coursesOfActionAttackPattern}" var="CoAPatternList">
									<div class="w3-row w3-leftbar border-ralf-blue w3-padding mitigation-box" data-mitigation-id="${CoAPatternList.value[0].course_of_action_id}" style="display:none">
										<form>
										<div class="w3-col l6 s6 m6">
											<p>${CoAPatternList.value[0].course_of_action_name}</p>
											<p><label for="application-fully-${CoAPatternList.value[0].course_of_action_id}"><input type="checkbox" class="control-application-fully-checkbox" id="application-fully-${CoAPatternList.value[0].course_of_action_id}" value="${CoAPatternList.value[0].course_of_action_id}" name="fullyAppliedControls">Applies for the whole service</label></p>
											<p><label for="application-partially-${CoAPatternList.value[0].course_of_action_id}"><input type="checkbox" class="control-application-partially-checkbox" id="application-partially-${CoAPatternList.value[0].course_of_action_id}" value="${CoAPatternList.value[0].course_of_action_id}" name="partiallyAppliedControls">Applies for parts of the service</label></p>
											<p><label for="${CoAPatternList.value[0].course_of_action_id}"><input type="checkbox" class="control-checkbox" id="${CoAPatternList.value[0].course_of_action_id}" value="${CoAPatternList.value[0].course_of_action_id}" name="fullyMitigatingControls">Applies to all threats</label></p>
											<p>${CoAPatternList.value[0].course_of_action_description}</p>
										</div>
										<div class="w3-col l6 s6 m6">
											<c:forEach items="${CoAPatternList.value}" var="CoAPattern">
												<div>
													<p>${CoAPattern.attack_pattern_name}</p>
													<p><label for="${CoAPattern.attack_pattern_id}"><input type="checkbox" class="threat-checkbox" id="${CoAPattern.attack_pattern_id}" value="${CoAPattern.attack_pattern_id}" data-mitigation-id="${CoAPattern.course_of_action_id}" name="notMitigatedThreats">The control does not apply to this threat</label></p>
													<!--<p>${CoAPattern.attack_pattern_description}</p>  -->
													<p>${CoAPattern.relationship_description}</p>
												</div>
												<hr />
											</c:forEach>												
										</div>
										</form>
									</div>
									</c:forEach>
								</c:if>
								
								<c:if test="${function3=='risk_assessment'}">
									<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
										<div class="w3-col l12 s12 m12">
											<span>The following threats apply to this service</span>
										</div>
										<c:forEach items="${compiledProbabilities}" var="pattern">
											<div class="w3-col l4 s4 m4">
												<p><b>${pattern.attack_pattern_name}</b> Success Probability:${pattern.success_probability_name}</p>
												<p><i>${pattern.attack_pattern_description}</i></p>
											</div>
										</c:forEach>
									</div>
									<hr />
								</c:if>
							</form>
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
	<p><a href="https://www.flaticon.com/free-icons/check" title="check icons">Icons created by hqrloveq - Flaticon</a></p>
	</footer>


</body>
</html>