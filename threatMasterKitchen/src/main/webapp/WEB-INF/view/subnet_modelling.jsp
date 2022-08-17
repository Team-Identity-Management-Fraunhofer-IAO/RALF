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
<script src="${context}/js_auth/cytoscape.min.js"></script>
<script src="${context}/js_auth/subnet_modelling_functions.js"></script>
<script src="${context}/js_auth/general/context_menu_service_modelling.js"></script>
<script src="${context}/js_auth/service_modelling/service_modelling_functions.js"></script>
<script src="${context}/js_auth/impact_ordering/impact_ordering_functions.js"></script>
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
				<div class="w3-col l2 m4 s6 stack-navigation" style="min-height: 200px; position: sticky; top: 52px; max-height: 93vh; overflow: auto;">
				
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
				
				<div class="w3-col l10 m8 s6 w3-light-grey w3-margin-bottom">&nbsp;</div>
				
	    		<%@ include file="submenu_bar.jsp" %>
				<div class="w3-col l10 m8 s6 w3-light-grey" style="padding-left: 16px; min-height: 99vh;">
					
					<div class="w3-card w3-white w3-margin-right w3-padding w3-small">
						<c:if test="${function2=='serviceModelling'}">
							<form method="POST" action="${context}/ServiceDefinition/ServiceModelling/saveService" id="service_modelling_form">
								<input type="hidden" name="service_id" value="${service_id}" />
								<input type="hidden" name="organization_id" value="1" />
								<input type="hidden" name="_csrf" value="${_csrf.token}" />
								<input type="hidden" name="_csrf_header" value="${_csrf.headerName}" />
								<div class="w3-row w3-padding w3-margin-bottom">
									<div class="w3-col l11 s11 m11">&nbsp;</div>
									<div class="w3-col l1 s1 m1" style="text-align:right;">
										<input class="w3-button w3-ripple w3-teal" type="submit" value="Update"/>									
									</div>
								</div>
								<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
									<div class="w3-col l1 s1 m1">
										<span>Service Name</span>
									</div>
									<div class="w3-col l11 s11 m11">
										<input type="text" class="w3-input" name="service_name" value="${service_name}" placeholder="Please provide a name for the business service" />
									</div>
									<div class="w3-col l1 s1 m1">
										<span>Service Description</span>
									</div>
									<div class="w3-col l11 s11 m11">
										<textarea class="w3-input" name="service_description" placeholder="Please provide a short description for the business service">${service_description}</textarea>
									</div>
								</div>
								<hr />
								<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
									<div class="w3-col l2 s2 m2">
										<span>Applicable Business Risks</span>
									</div>
									<div class="w3-col l2 s2 m2">
										&nbsp;
									</div>
									<div class="w3-col l2 s2 m2">
										<span>Available Business Risks</span>
									</div>
									<div class="w3-col l6 s6 m6">
										<span>Business Risk Description</span>
									</div>
									<div class="w3-col l2 s2 m2">
										<select name="service_risk_list" size="10" multiple>
											<c:forEach items="${business_risks}" var="business_risk">
												<option value="${business_risk.business_risk_id}">${business_risk.business_risk_name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="w3-col l2 s2 m2" style="text-align:center">
										<a class="w3-button w3-ripple" href="${context}/ServiceDefinition/ServiceModelling/${service_id}/RiskIdentification/newRisk">New Risk</a><br />
										<button type="button" class="w3-button w3-ripple" id="assign_business_risk_btn">&lt;</button><br />
										<button type="button" class="w3-button w3-ripple" id="remove_business_risk_btn">&gt;</button>
									</div>
									<div class="w3-col l2 s2 m2">
										<select name="available_risk_list" size="10">
											<c:forEach items="${available_business_risks}" var="available_business_risk">
												<option value="${available_business_risk.business_risk_id}">${available_business_risk.business_risk_name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="w3-col l6 s6 m6">
										<ul id="business_risk_description">
											<c:forEach items="${available_business_risks}" var="business_risk">
												<li data-business-risk-id="${business_risk.business_risk_id}">
													<ul>
														<li><b>Description:</b> ${business_risk.business_risk_description}</li>
														<li><b>Impact:</b> ${business_risk.business_risk_impact}</li>
														<li><b>Default Order:</b>${business_risk.business_risk_default_order}</li>
														<li><b>Capability Killers:</b>
															<ul>
																<c:forEach items="${capability_killers_per_business_risk[business_risk.business_risk_id]}" var="capability_killer">
																	<li>${capability_killer.capability_killer_name}</li>
																</c:forEach>
															</ul>
														</li>
													</ul>
												</li>
											</c:forEach>
											<c:forEach items="${business_risks}" var="business_risk">
												<li data-business-risk-id="${business_risk.business_risk_id}">
													<ul>
														<li><b>Description:</b> ${business_risk.business_risk_description}</li>														
														<li><b>Impact:</b> ${business_risk.business_risk_impact}</li>
														<li><b>Default Order:</b>${business_risk.business_risk_default_order}</li>
														<li><b>Capability Killers:</b>
															<ul>
																<c:forEach items="${capability_killers_per_business_risk[business_risk.business_risk_id]}" var="capability_killer">
																	<li>${capability_killer.capability_killer_name}</li>
																</c:forEach>
															</ul>
														</li>
													</ul>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</form>
						</c:if>
						<c:if test="${function2=='risk_identification'}">
							<form method="POST" action="${context}/ServiceDefinition/ServiceModelling/${service_id}/RiskIdentification/saveRisk">
								<input type="hidden" name="_csrf" value="${_csrf.token}" />
								<input type="hidden" name="_csrf_header" value="${_csrf.headerName}" />
								<input type="hidden" name="business_risk_id" value="${business_risk_id}" />
								<div class="w3-row w3-leftbar border-ralf-blue w3-padding w3-margin-bottom">
									<div class="w3-col l2 s2 m2">
										<span>Business Risk Name *</span>
									</div>
									<div class="w3-col l10 s10 m10">
										<input type="text" class="w3-input" name="business_risk_name" value="${business_risk_name}" placeholder="Please provide a name for the business service" />
									</div>
									<div class="w3-col l2 s2 m2">
										<span>Damage Description *</span>
									</div>
									<div class="w3-col l10 s10 m10">
										<textarea class="w3-input" name="business_risk_description" placeholder="Please provide a short description for the business service">${business_risk_description}</textarea>
									</div>
									<div class="w3-col l12 s12 m12">
										&nbsp;
									</div>
									<div class="w3-col l2 s2 m2">
										<span>Impact (&euro;)</span>
									</div>
									<div class="w3-col l3 s3 m3">
										<input class="w3-input" type="number" name="business_risk_impact" value="${business_risk_impact}" min="0" />
									</div>
									<div class="w3-col l3 s3 m3 w3-padding">&nbsp;</div>
									<div class="w3-col l1 s1 m1">
										<span>Order</span>
									</div>
									<div class="w3-col l3 s3 m3">
										<c:choose>
											<c:when test="${not empty applicableCategory}">
												<select name="business_risk_default_order" class="w3-select">
													<c:forEach items="${impactCategories}" var="impactCategory">														
														<option value="${impactCategory.risk_value_max}"<c:if test="${impactCategory.risk_category_id==applicableCategory.risk_category_id}"> selected</c:if>>${impactCategory.category_name}</option>
													</c:forEach>
												</select>
											</c:when>
											<c:otherwise>
												<input class="w3-input" type="number" name="business_risk_default_order" value="${business_risk_default_order}" min="0" max="100"/>
											</c:otherwise>
										</c:choose>
										
									</div>									
								</div>
								<hr />
								<div class="w3-row w3-leftbar border-ralf-blue w3-padding w3-margin-bottom">
									<div class="w3-col l12 s12 m12">
										<fieldset>
											<legend>Please characterize this damage</legend>
											<c:forEach items="${capability_killers}" var="capability_killer">
												<label><input class="w3-check" type="checkbox" name="capability_killer_ids" value="${capability_killer.capability_killer_id}"
													<c:forEach items="${business_risk_capability_killers}" var="business_risk_capability_killer">
														<c:if test="${business_risk_capability_killer.capability_killer_id==capability_killer.capability_killer_id}">
															checked
														</c:if>
													</c:forEach>
												/>${capability_killer.capability_killer_name}</label><br />
											</c:forEach>
										</fieldset>
									</div>
								</div>
								<div class="w3-row w3-padding">
									<div class="w3-col l10 s10 m10">&nbsp;</div>
									<div class="w3-col l2 s2 m2" style="text-align:right;">
										<label for="business_risk_general">Applies to this service only </label>
										<input type="checkbox" name="business_risk_general" id="business_risk_general" checked />
									</div>
									<div class="w3-col l10 s10 m10">&nbsp;</div>
									<div class="w3-col l2 s2 m2" style="text-align:right;">
										<span class="w3-tiny">Fields marked with * are mandatory</span>
									</div>
									<div class="w3-col l11 s11 m11">&nbsp;</div>
									<div class="w3-col l1 s1 m1" style="text-align:right;">
										
										<input class="w3-button w3-ripple w3-teal" type="submit" value="Update"/>									
									</div>
								</div>							
							</form>
						</c:if>
						<c:if test="${function2=='impact_ordering'}">
							<div class="w3-row w3-padding">
								<div class="w3-col l10 s10 m10">
									<c:choose>
										<c:when test="${function3=='auto_generated_order'}">
											<h3>This is an auto generated order!</h3>
											<span class="w3-red"><b>Please revise!</b></span>	
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</div>
								<div class="w3-col l2 s2 m2">
									<a class="w3-button w3-ripple w3-teal" href="${context}/ServiceDefinition/ServiceModelling/${service_id}/ImpactOrdering/ModifyOrder">Modify Impact Order</a>
								</div>
								<div class="w3-col">
									<div class="w3-row">
										<c:forEach begin="0" end="20" varStatus="loop">
											<div class="w3-col l2 m2 s2" style="border-right: 1px dashed #000; text-align:right;">
												<span>${(20-loop.index)*5}%&nbsp;&nbsp;<span>
											</div>
											<div class="w3-col l9 m9 s9" id="container-${20-loop.index}">
												&nbsp;
												<div class="w3-row">
												<c:set var="riskIndex">${20-loop.index}</c:set>
												${risksPerSegment['riskIndex']}
												<c:forEach items="${risksPerSegment[riskIndex]}" var="risk">
													<div class="w3-col risk-container" style="border-left: 6px solid; "data-weight="${risk.weight}" data-custom-order-id="${risk.business_risk_custom_order_list.order_id}" data-business-risk-id="${risk.business_risk_custom_order_list.business_risk_id}">
														<span>${risk.business_risk.business_risk_name}</span><br />
														<span><i>Description: ${risk.business_risk.business_risk_description}</i></span><br >
														<c:choose>
															<c:when test="${risk.business_risk.business_risk_impact==0}">
																<span>Maximum impact: ${risk.business_risk_custom_order_list.business_risk_impact_min} &euro;</span><br />
																<span>Minimum impact: ${risk.business_risk_custom_order_list.business_risk_impact_max} &euro;</span><br />
															</c:when>
															<c:otherwise>
																<span>Impact: ${risk.business_risk.business_risk_impact} &euro;</span><br />
															</c:otherwise>
														</c:choose>
														<span>Priority: ${risk.business_risk_custom_order_list.weight*100} %</span><br /><br />
														<label>Risk Category: 
															<select name="risk-category-selector">
																<c:forEach items="${impactCategories}" var="impactCategory">															
																	<option value="${impactCategory.risk_value_max}"<c:if test="${impactCategory.risk_category_id==risk.impactCategory.risk_category_id}"> selected</c:if>>${impactCategory.category_name}</option>
																</c:forEach>
															</select>
														</label>
													</div>
												</c:forEach>
												</div>
											</div>
										</c:forEach>
										
									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${function2=='modify_impact_ordering'}">
							<div class="w3-row w3-padding">
								<div class="w3-col l3 s3 m3">
									<h3>Impact Order</h3>
								</div>
								<div class="w3-col l9 s9 m9">
									&nbsp;
								</div>
								<c:forEach items="${questionnaire_blocks}" var="questionnaire_block">
									<div class="w3-col">
										<div class="w3-row">
											<div class="w3-col l3 s3 m3 reference-block" data-business-risk-reference-id="${questionnaire_block.key.business_risk_id}">
												<span>${questionnaire_block.key.business_risk_name}</span>
												<c:if test="${questionnaire_block.key.business_risk_impact!=0}">
												<span>[${questionnaire_block.key.business_risk_impact} &euro;]</span>
												</c:if>
											</div>
											<div class="w3-col l9 s9 m9">
												<c:forEach items="${questionnaire_block.value}" var="compared_risk">			
												<div class="w3-row">
													<div class="w3-col l3 s3 m3 comparison-block" data-business-risk-reference-id="${questionnaire_block.key.business_risk_id}" data-business-risk-compared-id="${compared_risk.business_risk_id}">
														<div class="w3-row">														
															<div class="w3-col">
																<select class="w3-select" name="comparison">
																	<option value="1">Equal</option>
																	<option value="2">Slightly more than equal</option>
																	<option value="3">Weak importance</option>
																	<option value="4">Slightly less weak importance</option>
																	<option value="5">Strong importance</option>
																	<option value="6">Slighlty stronger importance</option>
																	<option value="7">Demonstrated importance</option>
																	<option value="8">Nearly extreme importance</option>
																	<option value="9">Extreme importance</option>
																</select>
															</div>
														</div>
														<div class="w3-row">
															<div class="w3-col l6 s6 m6">
																<label><input type="radio" class="w3-radio" name="comparison_operator_${compared_risk.business_risk_id}_${questionnaire_block.key.business_risk_id}" value="gteq"><br />&gt;</label>
															</div>
															<div class="w3-col l6 s6 m6" style="text-align:right">
																<label><input type="radio" class="w3-radio" name="comparison_operator_${compared_risk.business_risk_id}_${questionnaire_block.key.business_risk_id}" value="lteq"><br />&lt;</label>								
															</div>
														</div>		
															
													</div>
													<div class="w3-col l9 s9 m9 compared-risk-block" data-business-risk-reference-id="${questionnaire_block.key.business_risk_id}" data-business-risk-compared-id="${compared_risk.business_risk_id}">
														<span>${compared_risk.business_risk_name}</span>
														<c:if test="${compared_risk.business_risk_impact!=0}">
														<span>[${compared_risk.business_risk_impact} &euro;]</span>
														</c:if>				
													</div>			
												</div>
												</c:forEach>
											</div>	
										</div>
									</div>
								</c:forEach>
							</div>
							<div class="w3-col" style="text-align:right">
								<input type="button" id="submit-impact-ordering-questionnaire-btn" class="w3-button w3-ripple w3-teal">Update</button>
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