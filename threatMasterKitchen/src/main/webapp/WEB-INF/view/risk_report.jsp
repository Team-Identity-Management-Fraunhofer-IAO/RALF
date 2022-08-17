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
<script	src="${context}/js_auth/service_modelling/service_modelling_functions.js"></script>
<script	src="${context}/js_auth/impact_ordering/impact_ordering_functions.js"></script>
<script	src="${context}/js_auth/risk_assessment/risk_assessment_gui_functions.js"></script>
</head>
<body class="w3-light-grey" onload="init();">
	<%@ include file="menu_bar.jsp"%>

	<!-- Page Container -->
	<div class="w3-margin-top loading" id="content-container"
		style="max-width: auto; min-height: 99vh;">

		<!-- The Grid -->
		<div class="w3-row">

			<div class="w3-row" style="background-color: #e7e9eb;">

				<div class="w3-col l2 m4 s6 stack-navigation"
					style="min-height: 200px; position: sticky; top: 53px; max-height: 93vh; overflow: auto;">
					<ul>
						<c:choose>
							<c:when test="${function3=='checkMitigations'}">
								<input
									class="w3-button w3-ripple w3-teal mitigation-report-submit-btn"
									type="submit" value="Submit" />
								<c:forEach items="${coursesOfAction}" var="alias">
									<li class="list-header"><span>${alias.key}</span>
										<ul>
											<c:forEach items="${alias.value}" var="courseOfAction">
												<li class="notApplyingMitigation"><a class="mitigation"
													data-mitigation-id="${courseOfAction.key}">${courseOfAction.value}</a></li>
											</c:forEach>
										</ul></li>
								</c:forEach>

							</c:when>
							<c:otherwise>
								<c:forEach items="${organizations}" var="organization">
									<li class="organisation">${organization.organization_name}
										<ul>
											<c:forEach items="${services}" var="service">
												<li><a class="subnet"
													href="${context}/RiskAssessment/${service.service_id}"
													data-service-id="${service.service_id}">${service.service_name}</a>
												</li>
											</c:forEach>
										</ul>
									</li>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>
				<div class="w3-col l10 m8 s6 w3-light-grey">&nbsp;</div>

				<div class="w3-col l10 m8 s6 w3-light-grey"
					style="padding-left: 16px; min-height: 99vh;">
					<c:if test="${function2=='risk_report'}">
					<div class="w3-bar w3-teal w3-card w3-margin-bottom">
						<a class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white show-risk-overview-btn">Risk Overview</a>
						<a class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white show-attacker-overview-btn">Relevant Attackers</a>
						<a class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white show-malware-overview-btn">Relevant Malware</a>
						<a class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white" href="./${risk_report.risk_report_id}/print" target="_blank">Print Summary</a>
					</div>
					<input type="hidden" name="riskPhi" value="${riskPhi}" />
					<input type="hidden" name="riskPhiRisk" value="${riskPhiRisk}" />
					<input type="hidden" name="existenceProbability" value="${existenceProbability.existence_probability}" />
					<div class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom risk-overview">
							<div class="w3-row">
								<div class="w3-row">
									<div class="w3-col l1 s1 m1" style="text-align: center">
										Impact</div>
								</div>
								<c:forEach items="${categoryRiskComputation}" var="yVal">
									<div class="w3-row">
										<div class="w3-col" style="text-align: center; width: 100px;">
											${yVal.key.category_name}</div>
										<c:forEach items="${yVal.value}" var="xVal">

											<div class="w3-col prob-class"
												data-val="${xVal.value.risk_value_max}" data-x-val="${xVal.key.risk_value_max}" data-y-val="${yVal.key.risk_value_max}" 
												style="border: 1px solid #000; text-align: center; margin: -1px -1px 0 0; width: 100px;">
												${xVal.value.category_name}</div>
										</c:forEach>										
									</div>									
								</c:forEach>
								<div class="w3-row">
									<div class="w3-col l1 s1 m1">
										&nbsp;
									</div>
									<c:forEach items="${orderedProbabilityCategories}" var="xVal">
										<div class="w3-col" style="text-align: center; width: 100px;">
											${xVal.category_name}
										</div>
									</c:forEach>
									<div class="w3-col l1 s1 m1">
										Probability
									</div>
								</div>
								<c:forEach items="${determinedRisks}" var="risk">
											<div class="risk-hover w3-tag w3-teal w3-padding" id="risk-tag-${risk.risk.business_risk_id}" style="overflow: hidden; width: 90px; height:28px; font-size:6pt; display:none; position: absolute; opacity:0.8">
												${risk.risk.business_risk_name}
											</div>
								</c:forEach>
							</div>
						</div>
							<div class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom risk-overview">
							<div
								class="w3-row w3-padding w3-leftbar border-ralf-blue w3-margin-bottom">
								<div class="w3-col l6 s6 m6">
									<h3>Affected Business Risks</h3>
									<div class="w3-row w3-padding">
										<c:forEach items="${determinedRisks}" var="risk">
											<div class="w3-col l4 s4 m4 w3-border w3-margin w3-padding business-risk-container" data-killer-ids="${risk.killer_ids}" data-val="${risk.weight}" data-id="${risk.risk.business_risk_id}">
												<p><b>${risk.risk.business_risk_name}</b></p>
												<p>Monetary value (exact): ${risk.risk.business_risk_impact}&euro;</p>
												<p>Monetary value (estd.): ${risk.monetary_value_min}&euro; ... ${risk.monetary_value_max}&euro;</p>
												<p>${impactCategories[risk.weight].category_name}</p>									
											</div>
										</c:forEach>										
									</div>
								</div>
								<div class="w3-col l6 s6 m6">
									<h3>Unaffected Business Risks</h3>
									<div class="w3-row w3-padding">
										<c:forEach items="${unaffectedRisks}" var="risk">
											<div class="w3-col l4 s4 m4 w3-border">
												<span>${risk.business_risk_name}</span><br /> <span>${risk.business_risk_impact}</span>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="w3-row w3-padding w3-leftbar border-ralf-blue">
								<div class="w3-col l12 s12 m12 ">
									<h3>Threat Assessment</h3>
								</div>
								<div class="w3-col l6 s6 m6">
									<p>Probability Determination Mode</p>
									<div class="container">
										<div class="switch">
											<input type="radio" id="maxVal" name="probabilityOption"
												value="max" checked /> 
											<label for="maxVal">Maximum</label> 
											<input type="radio" id="pivotVal" name="probabilityOption"
												value="pivot" /> 
											<label for="maxVal">Pivoted
												Threats</label> 
										</div>
									</div>
								</div>
								<div class="w3-col l2 s2 m2 pivot-coverage-box" style="display: none">
									<p>Coverage of pivoting threats</p>
								</div>
								<div class="w3-col l4 s4 m4 pivot-coverage-box" style="display: none">
									<p>${pivotCoverage}&percnt;</p>
								</div>
								<div class="w3-row" style="min-width: 2000px; overflow: auto">
									
									<div class="pivot-box" style="display: none">
										<svg width="${pivotBoxWidth}" height="${pivotBoxHeight}">
										<defs> <marker id="arrowhead" markerWidth="10"
											markerHeight="7" refX="0" refY="3.5" orient="auto">
										<polygon points="0 0, 10 3.5, 0 7" /> </marker> </defs> 
										<c:forEach
											items="${arrows}" var="arrow">
											<c:set var="excludeThreat" value="false" />
															<c:forEach items="${excludedThreats}" var="excludedThreat">
																<c:if test="${excludedThreat==arrow.threat_id}">
																	<c:set var="excludeThreat" value="true" />
																</c:if>
																<c:if test="${excludedThreat==arrow.suc_threat_id}">
																	<c:set var="excludeThreat" value="true" />
																</c:if>
															</c:forEach>
											<line data-from-id="${arrow.threat_id}" data-to-id="${arrow.suc_threat_id}" data-phase-index="${arrow.kill_chain_index}" opacity=0.1 x1="${arrow.x0}" y1="${arrow.y0}"
												x2="${arrow.x1}" y2="${arrow.y1}" stroke="#000"
												stroke-width="1" marker-end="url(#arrowhead" )" <c:if test="${excludeThreat}">visibility="hidden"</c:if> />
										</c:forEach> </svg>
									</div>
									<c:forEach begin="${minPhase}" end="${maxPhase}"
										varStatus="loop">
										<c:forEach items="${killChainOrder[loop.index]}"
											var="currentPhase">
											<c:if test="${threats.containsKey(currentPhase)}">
												<div class="phase-box w3-col" data-phase-index="${phaseIndices[currentPhase.phase_name]}">
													<div class="phase-heading">
														${currentPhase.phase_name}</div>
													<div class="phase-threat-box">
														<c:forEach items="${threats[currentPhase]}" var="threat">
															<c:set var="excludeThreat" value="false" />
															<c:forEach items="${excludedThreats}" var="excludedThreat">
																<c:if test="${excludedThreat==threat.threat_id}">
																	<c:set var="excludeThreat" value="true" />
																</c:if>
															</c:forEach>
															
															<div class="threat-box<c:if test='${excludeThreat}'> excluded-threat</c:if><c:if test='${not empty killerIDsPerThreat[threat.threat_id]}'> capability-threat</c:if>" data-risk-report-id="${risk_report.risk_report_id}" data-threat-id="${threat.threat_id}" data-val="${threat.success_probability_order}" data-pivot-val="${pivotProbabilities[currentPhase][threat.threat_id].risk_value_max}" data-killer-ids="${killerIDsPerThreat[threat.threat_id]}">
																<div class="w3-row">
																	<div class="w3-col l10 m10 s10">
																		${threat.threat_name}
																	</div>
																	<div class="w3-col l2 s2 m2">
																		
																		<span class="exclude-threat-from-analysis-btn w3-tiny" style="cursor:pointer; <c:if test="${excludeThreat}">display:none</c:if>"><b>&times;</b></span>
																		<span class="include-threat-in-analysis-btn w3-tiny" style="cursor:pointer; <c:if test="${not excludeThreat}">display:none</c:if>"><b>&plus;</b></span>
																	</div>
																	<div class="w3-col">
																		<span>Success Probability:</span>
																	</div>
																	<div class="w3-col">
																		<span class="success-probability">(${probabilityCategories[threat.success_probability_order].category_name})</span>
																		<span class="pivoted-success-probability" style="display:none">(${probabilityCategories[pivotProbabilities[currentPhase][threat.threat_id].risk_value_max].category_name})</span>	
																	</div>
																</div>
															</div>
															
														</c:forEach>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</c:forEach>
									
								</div>
							</div>
					</div>
					<div class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom risk-overview">
						<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
							<div class="w3-col">
								<h3>Alternative Probabilities</h3>
							</div>
							<c:forEach items="${alternativeProbabilities}" var="pattern">
								<div class="w3-col alternative-probabilities-container" data-threat-id="${pattern.key}" style="display: none;">
									<c:forEach items="${pattern.value}" var="probability">		
										<div class="w3-row alternative-probability">	
											<div class="w3-col l8 s8 m8 w3-border-bottom">	
												<div class="w3-row">		
													<c:forEach items="${probability.value}" var="probItem" varStatus="loop">											
														<div class="w3-col l8 s8 m8">
															<b>${probItem.course_of_action_name}</b><br />
															<i>${probItem.course_of_action_description}</i>	
														</div>	
														<div class="w3-col l4 s4 m4">
															<c:if test="${loop.first}">
																<c:choose>
																	<c:when test="${not empty probItem.c_vulnerability_enabling_factor_id}">
																		Enabling Factor:<br />
																		<i>${vulnerabilityEnablingFactorNames[probItem.c_vulnerability_enabling_factor_id]}</i>
																	</c:when>
																	<c:otherwise>
																		<i>Without Vulnerability Enabling Factor</i>
																	</c:otherwise>
																</c:choose>
															</c:if>
														</div>											
													</c:forEach>
												</div>
											</div>
											
											<c:forEach items="${probability.value}" var="probItem" varStatus="loop">
												<c:if test="${loop.first}">
													<div class="w3-col l4 s4 m4">
														${probabilityCategories[probItem.success_probability_order].category_name}
													</div>
												</c:if>
											</c:forEach>
											
										</div>											
									</c:forEach>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom attacker-overview" style="display:none">
						<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
							<div class="w3-col">
								<h3>Relevant Attackers</h3>
							</div>
							<!-- Motivated, Enabled and Patterned Attackers -->
							<c:forEach items="${motivatedAndEnabledAndPatternedAttackers}" var="attacker">
								<div class="w3-col w3-padding w3-margin w3-border">
									<div class="w3-row">
										<div class="w3-col l1 s1 m1">
											<b>${attacker.name}</b>
										</div>
										<div class="w3-col l3 s3 m3 w3-tiny">
											<span class="w3-tag w3-round w3-teal w3-padding">
												Motivated
											</span>
											<span class="w3-tag w3-round w3-teal w3-padding">
												Enabled
											</span>
											<span class="w3-tag w3-round w3-teal w3-padding">
												Included
											</span>
										</div>
										<div class="w3-col l8 s8 m8">
										<c:forEach items="${referencesPerAttacker[attacker.intrusion_set_id]}" var="reference" varStatus="loop">
											<c:if test="${not empty reference.url}">												
												<c:if test="${loop.index!=0}">,</c:if><a href="${reference.url}" target="_blank">${reference.external_id} (${reference.source_name})</a>
											</c:if>
										</c:forEach>
										</div>
									</div>
								</div>
							</c:forEach>
							<!-- Motivated and Patterned Attackers -->
							<c:forEach items="${motivatedAndPatternedAttackers}" var="attacker">
								<div class="w3-col w3-padding w3-margin w3-border">
									<div class="w3-row">
										<div class="w3-col l1 s1 m1">
											<b>${attacker.name}</b>
										</div>
										<div class="w3-col l3 s3 m3 w3-tiny">
											<span class="w3-tag w3-round w3-teal w3-padding">
												Motivated
											</span>
											<span class="w3-tag w3-round w3-teal w3-padding">
												Included
											</span>
										</div>
										<div class="w3-col l8 s8 m8">
										<c:forEach items="${referencesPerAttacker[attacker.intrusion_set_id]}" var="reference" varStatus="loop">
											<c:if test="${not empty reference.url}">												
												<c:if test="${loop.index!=0}">,</c:if><a href="${reference.url}" target="_blank">${reference.external_id} (${reference.source_name})</a>
											</c:if>
										</c:forEach>
										</div>
									</div>
								</div>
							</c:forEach>
							<!-- Enabled and Patterned Attackers -->
							<c:forEach items="${enabledAndPatternedAttackers}" var="attacker">
								<div class="w3-col w3-padding w3-margin w3-border">
									<div class="w3-row">
										<div class="w3-col l1 s1 m1">
											<b>${attacker.name}</b>
										</div>
										<div class="w3-col l3 s3 m3 w3-tiny">
											<span class="w3-tag w3-round w3-teal w3-padding">
												Enabled
											</span>
											<span class="w3-tag w3-round w3-teal w3-padding">
												Included
											</span>
										</div>
										<div class="w3-col l8 s8 m8">
										<c:forEach items="${referencesPerAttacker[attacker.intrusion_set_id]}" var="reference" varStatus="loop">
											<c:if test="${not empty reference.url}">												
												<c:if test="${loop.index!=0}">,</c:if><a href="${reference.url}" target="_blank">${reference.external_id} (${reference.source_name})</a>
											</c:if>
										</c:forEach>
										</div>
									</div>
								</div>
							</c:forEach>
							<!-- Patterned Attackers -->
							<c:forEach items="${onlyPatternedAttackers}" var="attacker">
								<div class="w3-col w3-padding w3-margin w3-border">
									<div class="w3-row">
										<div class="w3-col l1 s1 m1">
											<b>${attacker.name}</b>
										</div>
										<div class="w3-col l3 s3 m3 w3-tiny">
											<span class="w3-tag w3-round w3-teal w3-padding">
												Included
											</span>
										</div>
										<div class="w3-col l8 s8 m8">
										<c:forEach items="${referencesPerAttacker[attacker.intrusion_set_id]}" var="reference" varStatus="loop">
											<c:if test="${not empty reference.url}">												
												<c:if test="${loop.index!=0}">,</c:if><a href="${reference.url}" target="_blank">${reference.external_id} (${reference.source_name})</a>
											</c:if>
										</c:forEach>
										</div>
									</div>
								</div>
							</c:forEach>
							<!-- Motivated Attackers -->
							<c:forEach items="${onlyMotivatedAttackers}" var="attacker">
								<div class="w3-col w3-padding w3-margin w3-border">
									<div class="w3-row">
										<div class="w3-col l1 s1 m1">
											<b>${attacker.name}</b>
										</div>
										<div class="w3-col l3 s3 m3 w3-tiny">
											<span class="w3-tag w3-round w3-teal w3-padding">
												Motivated
											</span>
										</div>
										<div class="w3-col l8 s8 m8">
										<c:forEach items="${referencesPerAttacker[attacker.intrusion_set_id]}" var="reference" varStatus="loop">
											<c:if test="${not empty reference.url}">												
												<c:if test="${loop.index!=0}">,</c:if><a href="${reference.url}" target="_blank">${reference.external_id} (${reference.source_name})</a>
											</c:if>
										</c:forEach>
										</div>
									</div>
								</div>
							</c:forEach>
							<!-- Enabled Attackers -->
							<c:forEach items="${onlyEnabledAttackers}" var="attacker">
								<div class="w3-col w3-padding w3-margin w3-border">
									<div class="w3-row">
										<div class="w3-col l1 s1 m1">
											<b>${attacker.name}</b>
										</div>
										<div class="w3-col l3 s3 m3 w3-tiny">
											<span class="w3-tag w3-round w3-teal w3-padding">
												Enabled
											</span>
										</div>
										<div class="w3-col l8 s8 m8">
										<c:forEach items="${referencesPerAttacker[attacker.intrusion_set_id]}" var="reference" varStatus="loop">
											<c:if test="${not empty reference.url}">												
												<c:if test="${loop.index!=0}">,</c:if><a href="${reference.url}" target="_blank">${reference.external_id} (${reference.source_name})</a>
											</c:if>
										</c:forEach>
										</div>
									</div>
								</div>
							</c:forEach>
							
						</div>
						
					</div>
					<div class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom malware-overview" style="display:none">
						<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
							<div class="w3-col">
								<h3>Relevant Malware</h3>
							</div>
							<!-- Motivated, Enabled and Patterned Attackers -->
							<c:forEach items="${relevantMalware}" var="attacker">
								<div class="w3-col w3-padding w3-margin w3-border">
									<div class="w3-row">
										<div class="w3-col l1 s1 m1">
											<b>${attacker.name}</b>
										</div>
										<div class="w3-col l11 s11 m11">
										<c:forEach items="${referencesPerMalware[attacker.malware_id]}" var="reference" varStatus="loop">
											<c:if test="${not empty reference.url}">												
												<c:if test="${loop.index!=0}">,</c:if><a href="${reference.url}" target="_blank">${reference.external_id} (${reference.source_name})</a>
											</c:if>
										</c:forEach>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					<div style="display: none" id="data-box">
						<div id="risk-classes-box">
							<c:forEach items="${riskCategories}" var="risk">
								<div class="risk-class" data-val-min="${risk.value.risk_value_min}" data-val-max="${risk.value.risk_value_max}" data-name="${risk.value.category_name}">
								</div>
							</c:forEach>
						</div>
						<div id="probability-classes-box">
							<c:forEach items="${probabilityCategories}" var="probability">
								<div class="probability-class" data-val-min="${probability.value.risk_value_min}" data-val-max="${probability.value.risk_value_max}" data-name="${probability.value.category_name}">
								</div>
							</c:forEach>
						</div>
						<div id="impact-classes-box">
							<c:forEach items="${impactCategories}" var="impact">
								<div class="impact-class" data-val-min="${impact.value.risk_value_min}" data-val-max="${impact.value.risk_value_max}" data-name="${impact.value.category_name}">
								</div>
							</c:forEach>
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
	<p>
		<a href="https://www.flaticon.com/free-icons/check"
			title="check icons">Icons created by hqrloveq - Flaticon</a>
	</p>
	</footer>


</body>
</html>