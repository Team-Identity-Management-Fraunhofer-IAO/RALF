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
<script src="${context}/js_auth/factor_ordering/factor_ordering_functions.js"></script>
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
						
						<c:if test="${function2=='attackFactorWeighing'}">
							<input type="hidden" name="organization_id" value="${organization_id}" />
							<input type="hidden" name="service_id" value="${service_id}" />
							<div class="w3-row w3-padding">
								<div class="w3-col l3 s3 m3">
									<h3>Attack motivating factors</h3>
								</div>
								<div class="w3-col l9 s9 m9">
									&nbsp;
								</div>
								<c:forEach begin="0" end="${motivatingFactors.size()-2}" varStatus="loop">
									<c:set var="motivatingFactor" value="${motivatingFactors[loop.index]}" />
									<div class="w3-col">
										<div class="w3-row w3-margin-bottom">
											<div class="w3-col l3 s3 m3 reference-block" data-attack-motivating-factor-id="${motivatingFactor.attack_motivating_factor_id}">
												<span>${motivatingFactor.attack_motivating_factor_name}</span><br />
												<span>${motivatingFactor.attack_motivating_factor_description}</span>
											</div>
											<div class="w3-col l9 s9 m9">
												<c:forEach begin="${loop.index}" end="${motivatingFactors.size()-1}" varStatus="refLoop">
													<c:set var="comparedFactor" value="${motivatingFactors[refLoop.index]}" />
													<c:if test="${comparedFactor.attack_motivating_factor_id != motivatingFactor.attack_motivating_factor_id}">
														<div class="w3-row w3-margin-bottom w3-border-bottom">
															<div class="w3-col l3 s3 m3 comparison-block" data-attack-motivating-factor-id="${motivatingFactor.attack_motivating_factor_id}" data-attack-motivating-factor-compared-id="${comparedFactor.attack_motivating_factor_id}">
																<div class="w3-row">
																	<div class="w3-col">
																		<select class="w3-select" name="comparison">
																			<option value="1">Equal</option>
																			<option value="2">Slightly more than equal</option>
																			<option value="3">Weak importance</option>
																			<option value="4">Slightly less weak importance</option>
																			<option value="5">Strong importance</option>
																			<option value="6">Slightly stronger importance</option>
																			<option value="7">Demonstrated importance</option>
																			<option value="8">Nearly extreme importance</option>
																			<option value="9">Extreme importance</option>
																		</select>
																	</div>
																</div>
																<div class="w3-row">
																	<div class="w3-col l6 s6 m6">
																		<label><input type="radio" class="w3-radio" name="comparison_motivating_operator_${comparedFactor.attack_motivating_factor_id}_${motivatingFactor.attack_motivating_factor_id}" value="gteq" checked><br />&gt;</label>
																	</div>
																	<div class="w3-col l6 s6 m6" style="text-align: right">
																		<label><input type="radio" class="w3-radio" name="comparison_motivating_operator_${comparedFactor.attack_motivating_factor_id}_${motivatingFactor.attack_motivating_factor_id}" value="lteq"><br />&lt;</label>
																	</div>
																</div>
															</div>
															<div class="w3-col l1 s1 m1">
															&nbsp;
															</div>
															<div class="w3-col l8 s8 m8 compared-risk-block" data-attack-motivating-factor-id="${motivatingFactor.attack_motivating_factor_id}" data-attack-motivating-factor-compared-id="${compatedFactor.attack_motivating_factor_id}">
																<span>${comparedFactor.attack_motivating_factor_name}</span><br />
																<span>${comparedFactor.attack_motivating_factor_description}</span>																
															</div>
														</div>													
													</c:if>		
												</c:forEach>
											</div>
										</div>
									</div>
								</c:forEach>								
							</div>
							
							<div class="w3-row w3-padding">
								<div class="w3-col l3 s3 m3">
									<h3>Vulnerability enabling factors</h3>
								</div>
								<div class="w3-col l9 s9 m9">
									&nbsp;
								</div>
								<c:forEach begin="0" end="${vulnerabilityEnablingFactors.size()-2}" varStatus="loop">
									<c:set var="vulnerabilityEnablingFactor" value="${vulnerabilityEnablingFactors[loop.index]}" />
									<div class="w3-col">
										<div class="w3-row w3-margin-bottom">
											<div class="w3-col l3 s3 m3 reference-block" data-attack-motivating-factor-id="${vulnerabilityEnablingFactor.c_vulnerability_enabling_factor_id}">
												<span>${vulnerabilityEnablingFactor.vulnerability_enabling_factor_name}</span>
												<span>${vulnerabilityEnablingFactor.vulnerability_enabling_factor_description}</span>
											</div>
											<div class="w3-col l9 s9 m9">
												<c:forEach begin="${loop.index}" end="${vulnerabilityEnablingFactors.size()-1}" varStatus="refLoop">
													<c:set var="comparedFactor" value="${vulnerabilityEnablingFactors[refLoop.index]}" />
													<c:if test="${comparedFactor.c_vulnerability_enabling_factor_id != vulnerabilityEnablingFactor.c_vulnerability_enabling_factor_id}">
														<div class="w3-row">
															<div class="w3-col l3 s3 m3 comparison-block-vulnerability-enabling" data-vulnerability-enabling-factor-id="${vulnerabilityEnablingFactor.c_vulnerability_enabling_factor_id}" data-vulnerability-enabling-factor-compared-id="${comparedFactor.c_vulnerability_enabling_factor_id}">
																<div class="w3-row">
																	<div class="w3-col">
																		<select class="w3-select" name="comparison">
																			<option value="1">Equal</option>
																			<option value="2">Slightly more than equal</option>
																			<option value="3">Weak importance</option>
																			<option value="4">Slightly less weak importance</option>
																			<option value="5">Strong importance</option>
																			<option value="6">Slightly stronger importance</option>
																			<option value="7">Demonstrated importance</option>
																			<option value="8">Nearly extreme importance</option>
																			<option value="9">Extreme importance</option>
																		</select>
																	</div>
																</div>
																<div class="w3-col l6 s6 m6">
																	<label><input type="radio" class="w3-radio" name="comparison_operator_${comparedFactor.c_vulnerability_enabling_factor_id}_${vulnerabilityEnablingFactor.c_vulnerability_enabling_factor_id}" value="gteq" checked>&gt;</label>
																</div>
																<div class="w3-col l6 s6 m6" style="text-align: right">
																	<label><input type="radio" class="w3-radio" name="comparison_operator_${comparedFactor.c_vulnerability_enabling_factor_id}_${vulnerabilityEnablingFactor.c_vulnerability_enabling_factor_id}" value="lteq">&lt;</label>
																</div>
															</div>
															<div class="w3-col l1 s1 m1">
															&nbsp;
															</div>
															<div class="w3-col l8 s8 m8 compared-risk-block" data-vulnerability-enabling-factor-id="${vulnerabilityEnablingFactor.c_vulnerability_enabling_factor_id}" data-attack-motivating-factor-compared-id="${comparedFactor.c_vulnerability_enabling_factor_id}">
																<span>${comparedFactor.vulnerability_enabling_factor_name}</span>
																<span>${comparedFactor.vulnerability_enabling_factor_description}</span>																
															</div>
														</div>													
													</c:if>		
												</c:forEach>
											</div>
										</div>
									</div>
								</c:forEach>								
							</div>
							
							<div class="w3-col" style="text-align:right">
								<button id="submit-factor-ordering-questionnaire-btn" class="w3-button w3-ripple w3-teal">Update</button>
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