<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
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
<script
	src="${context}/js_auth/service_modelling/service_modelling_functions.js"></script>
<script
	src="${context}/js_auth/impact_ordering/impact_ordering_functions.js"></script>
<script
	src="${context}/js_auth/risk_assessment_print/risk_assessment_gui_functions.js"></script>
</head>
<body onload="init();">
	<!-- Page Container -->
	<div class="w3-margin-top loading" id="content-container"
		style="max-width: auto; min-height: 99vh;">
		<div class="w3-row front-page">
			<div class="w3-col l3 s3 m3">
				&nbsp;
			</div>
			<div class="w3-col l6 s6 m6" style="text-align:center">
				<img src="${context}/img/ralf_logo.png" id="ralf_logo" style="width: 250px"/>
		   		<p>A&nbsp;&nbsp;u&nbsp;&nbsp;t&nbsp;&nbsp;o&nbsp;&nbsp;m&nbsp;&nbsp;a&nbsp;&nbsp;t&nbsp;&nbsp;e&nbsp;&nbsp;d&nbsp;&nbsp;&nbsp;R&nbsp;&nbsp;i&nbsp;&nbsp;s&nbsp;&nbsp;k&nbsp;&nbsp;&nbsp;A&nbsp;&nbsp;n&nbsp;&nbsp;a&nbsp;&nbsp;l&nbsp;&nbsp;y&nbsp;&nbsp;s&nbsp;&nbsp;i&nbsp;&nbsp;s&nbsp;&nbsp;&nbsp;P&nbsp;&nbsp;l&nbsp;&nbsp;a&nbsp;&nbsp;t&nbsp;&nbsp;f&nbsp;&nbsp;o&nbsp;&nbsp;r&nbsp;&nbsp;m</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>Risk Report</p>
				<p>for Service</p>
				<p><b>${service.service_name}</b></p>
				<p>${service.service_description}</p>
			</div>
			<div class="w3-col l3 s3 m3">
				&nbsp;
			</div>
		</div>
		<!-- The Grid -->
		<div class="w3-row">

			<div class="w3-row">

				<div class="w3-col" style="padding-left: 16px; min-height: 99vh;">
					<c:if test="${function2=='risk_report'}">
						
						<input type="hidden" name="riskPhi" value="${riskPhi}" />
						<input type="hidden" name="riskPhiRisk" value="${riskPhiRisk}" />
						<input type="hidden" name="existenceProbability"
							value="${existenceProbability.existence_probability}" />
						<div
							class="w3-margin-right w3-padding w3-small w3-margin-bottom business-risk-overview risk-overview">
							<div class="w3-row">
								<div class="w3-col">
									<h2>Applicable Business Risks</h2>
								</div>
							</div>
							<c:forEach items="${determinedRisks}" var="risk">
								<div class="w3-row business-risk-container-print"
									id="business-risk-${risk.risk.business_risk_id}">
									<div class="w3-col">
										<h4>${risk.risk.business_risk_name}</h4>
										<p>Monetary value (exact):
											${risk.risk.business_risk_impact}&euro;</p>
										<p>Monetary value (estd.):
											${risk.monetary_value_min}&euro; ...
											${risk.monetary_value_max}&euro;</p>
									</div>
									<div class="w3-row">
										<div class="w3-col">
											<p>
												<b>Maximum Occurence Probability</b>
											</p>
											<p>The following matrix shows the risk that results out
												of the consideration of the maximum occurence probability of
												all assessed threats</p>
										</div>
									</div>
									<div class="w3-row">
										<div class="w3-col maximum-probability-matrix">
											<div class="w3-row">
												<div class="w3-col l1 s1 m1" style="text-align: center">
													<b>Impact</b></div>
											</div>
											<c:forEach items="${categoryRiskComputation}" var="yVal">
												<div class="w3-row maximum-prob">
													<div class="w3-col"
														style="text-align: center; width: 100px;">
														<b>${yVal.key.category_name}</b></div>
													<c:forEach items="${yVal.value}" var="xVal">

														<div class="w3-col prob-class"
															data-val="${xVal.value.risk_value_max}"
															data-x-val="${xVal.key.risk_value_max}"
															data-y-val="${yVal.key.risk_value_max}"
															style="border: 1px solid #000; text-align: center; margin: -1px -1px 0 0; width: 100px;">
															${xVal.value.category_name}</div>
													</c:forEach>
												</div>
											</c:forEach>
											<div class="w3-row">
												<div class="w3-col l1 s1 m1">&nbsp;</div>
												<c:forEach items="${orderedProbabilityCategories}"
													var="xVal">
													<div class="w3-col"
														style="text-align: center; width: 100px;">
														<b>${xVal.category_name}</b></div>
												</c:forEach>
												<div class="w3-col l1 s1 m1"><b>Probability</b></div>
											</div>

											
										</div>
										<div class="w3-row">
											<div class="w3-col">
												<p>
													<b>Pivoted Occurence Probability</b>
												</p>
												<p>
													The following matrix shows the risk that results out of the
													consideration of the pivoted occurence probability of all
													assessed threats.<br /> Pivoted occurence probability
													describes the occurence probability of each threat that
													results out of the minimum occurence probability of
													required threats.<br /> This probability has a limited
													reliability, as only ${pivotCoverage*100}&percnt; of the
													threats are included in this assessment.
												</p>
											</div>
										</div>
										<div class="w3-col pivoted-probability-matrix">
											<div class="w3-row">
												<div class="w3-col l1 s1 m1" style="text-align: center">
													<b>Impact</b></div>
											</div>
											<c:forEach items="${categoryRiskComputation}" var="yVal">
												<div class="w3-row maximum-prob">
													<div class="w3-col"
														style="text-align: center; width: 100px;">
														<b>${yVal.key.category_name}</b></div>
													<c:forEach items="${yVal.value}" var="xVal">

														<div class="w3-col prob-class"
															data-val="${xVal.value.risk_value_max}"
															data-x-val="${xVal.key.risk_value_max}"
															data-y-val="${yVal.key.risk_value_max}"
															style="border: 1px solid #000; text-align: center; margin: -1px -1px 0 0; width: 100px;">
															${xVal.value.category_name}</div>
													</c:forEach>
												</div>
											</c:forEach>
											<div class="w3-row">
												<div class="w3-col l1 s1 m1">&nbsp;</div>
												<c:forEach items="${orderedProbabilityCategories}"
													var="xVal">
													<div class="w3-col"
														style="text-align: center; width: 100px;">
														<b>${xVal.category_name}</b></div>
												</c:forEach>
												<div class="w3-col l1 s1 m1"><b>Probability</b></div>
											</div>

											
										</div>
									</div>
								</div>

							</c:forEach>
						</div>
						<div
							class="w3-margin-right w3-padding w3-small w3-margin-bottom risk-overview">
							<div
								class="w3-row w3-padding w3-leftbar border-ralf-blue w3-margin-bottom"
								style="display: none">
								<div class="w3-col l6 s6 m6">
									<h3>Affected Business Risks</h3>
									<div class="w3-row w3-padding">
										<c:forEach items="${determinedRisks}" var="risk">
											<div
												class="w3-col l4 s4 m4 w3-border w3-margin w3-padding business-risk-container"
												data-killer-ids="${risk.killer_ids}"
												data-val="${risk.weight}"
												data-id="${risk.risk.business_risk_id}">
												<p>
													<b>${risk.risk.business_risk_name}</b>
												</p>
												<p>Monetary value (exact):
													${risk.risk.business_risk_impact}&euro;</p>
												<p>Monetary value (estd.):
													${risk.monetary_value_min}&euro; ...
													${risk.monetary_value_max}&euro;</p>
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
							<div class="w3-row">
								<div class="w3-row threat-assessment-heading">
									<div class="w3-col l12 s12 m12 ">
										<h3>Threat Assessment</h3>
									</div>
									<div class="w3-col l10 s10 m10">
										<p>The following visualization shows the maximum success
											probability for each determined threat to the service.</p>
									</div>
									<div class="w3-col l2 s2 m2">
										<p>
											<b>Legend</b>
										</p>
										<div class="w3-row">
											<c:forEach items="${probabilityCategories}"
												var="probabilityCategory">
												<div style="font-size: 6pt" class="w3-col prob-legend"
													data-val="${probabilityCategory.value.risk_value_max}">
													${probabilityCategory.value.category_name}</div>
											</c:forEach>
										</div>
									</div>
								</div>
								
								<div class="w3-col l2 s2 m2 pivot-coverage-box"
									style="display: none">
									<p>Coverage of pivoting threats</p>
								</div>
								<div class="w3-col l4 s4 m4 pivot-coverage-box"
									style="display: none">
									<p>${pivotCoverage}&percnt;</p>
								</div>
								<div class="w3-row maximum-threat-container">

									
									<c:forEach begin="${minPhase}" end="${maxPhase}"
										varStatus="loop">
										<c:forEach items="${killChainOrder[loop.index]}"
											var="currentPhase">
											<c:if test="${threats.containsKey(currentPhase)}">
												<div class="phase-box w3-col"
													style="font-size: 4pt; width: 60px;"
													data-phase-index="${phaseIndices[currentPhase.phase_name]}">
													<div class="phase-heading">
														${currentPhase.phase_name}</div>
													<div class="phase-threat-box">
														<c:forEach items="${threats[currentPhase]}" var="threat">
															<c:set var="excludeThreat" value="false" />
															<c:forEach items="${excludedThreats}"
																var="excludedThreat">
																<c:if test="${excludedThreat==threat.threat_id}">
																	<c:set var="excludeThreat" value="true" />
																</c:if>
															</c:forEach>

															<div
																style="height: 48px; overflow: hidden; font-size: 4pt"
																class="threat-box<c:if test='${excludeThreat}'> excluded-threat</c:if><c:if test='${not empty killerIDsPerThreat[threat.threat_id]}'> capability-threat</c:if>"
																data-risk-report-id="${risk_report.risk_report_id}"
																data-threat-id="${threat.threat_id}"
																data-val="${threat.success_probability_order}"
																data-pivot-val="${pivotProbabilities[currentPhase][threat.threat_id].risk_value_max}"
																data-killer-ids="${killerIDsPerThreat[threat.threat_id]}">
																<div class="w3-row">
																	<div class="w3-col">${threat.threat_name}</div>

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
						<div
							class="w3-card w3-white w3-margin-right w3-small w3-margin-bottom alternative-mitigation-print risk-overview" style="display: none">
							<div class="w3-row">
								<div class="w3-col">
									<h3>Alternative Mitigation Options</h3>
								</div>
								<c:forEach items="${alternativeProbabilities}" var="pattern">
									<div class="w3-col alternative-probabilities-container"
										data-threat-id="${pattern.key}">
										<div class="w3-row">
											<div classs="w3-col">
												<p><b>${threatsById[pattern.key].threat_name}</b></p>
												<p><i>${threatsById[pattern.key].threat_description}</i></p>
											</div>
										</div>
										<c:forEach items="${pattern.value}" var="probability">
											<div class="w3-row alternative-probability">
												<div class="w3-col l8 s8 m8 w3-border-bottom">
													<div class="w3-row">
														<c:forEach items="${probability.value}" var="probItem"
															varStatus="loop">
															<div class="w3-col l8 s8 m8">
																<b>${probItem.course_of_action_name}</b><br /> <i>${probItem.course_of_action_description}</i>
															</div>
															<div class="w3-col l4 s4 m4">
																<c:if test="${loop.first}">
																	<c:choose>
																		<c:when
																			test="${not empty probItem.c_vulnerability_enabling_factor_id}">
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

												<c:forEach items="${probability.value}" var="probItem"
													varStatus="loop">
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
						<div
							class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom attacker-overview"
							style="display: none">
							<div class="w3-row w3-leftbar border-ralf-blue w3-padding">
								<div class="w3-col">
									<h3>Relevant Attackers</h3>
								</div>
								<!-- Motivated, Enabled and Patterned Attackers -->
								<c:forEach items="${motivatedAndEnabledAndPatternedAttackers}"
									var="attacker">
									<div class="w3-col w3-padding w3-margin w3-border">
										<div class="w3-row">
											<div class="w3-col l1 s1 m1">
												<b>${attacker.name}</b>
											</div>
											<div class="w3-col l3 s3 m3 w3-tiny">
												<span class="w3-tag w3-round w3-teal w3-padding">
													Motivated </span> <span class="w3-tag w3-round w3-teal w3-padding">
													Enabled </span> <span class="w3-tag w3-round w3-teal w3-padding">
													Included </span>
											</div>
											<div class="w3-col l8 s8 m8">
												<c:forEach
													items="${referencesPerAttacker[attacker.intrusion_set_id]}"
													var="reference" varStatus="loop">
													<c:if test="${not empty reference.url}">
														<c:if test="${loop.index!=0}">,</c:if>
														<a href="${reference.url}" target="_blank">${reference.external_id}
															(${reference.source_name})</a>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</div>
								</c:forEach>
								<!-- Motivated and Patterned Attackers -->
								<c:forEach items="${motivatedAndPatternedAttackers}"
									var="attacker">
									<div class="w3-col w3-padding w3-margin w3-border">
										<div class="w3-row">
											<div class="w3-col l1 s1 m1">
												<b>${attacker.name}</b>
											</div>
											<div class="w3-col l3 s3 m3 w3-tiny">
												<span class="w3-tag w3-round w3-teal w3-padding">
													Motivated </span> <span class="w3-tag w3-round w3-teal w3-padding">
													Included </span>
											</div>
											<div class="w3-col l8 s8 m8">
												<c:forEach
													items="${referencesPerAttacker[attacker.intrusion_set_id]}"
													var="reference" varStatus="loop">
													<c:if test="${not empty reference.url}">
														<c:if test="${loop.index!=0}">,</c:if>
														<a href="${reference.url}" target="_blank">${reference.external_id}
															(${reference.source_name})</a>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</div>
								</c:forEach>
								<!-- Enabled and Patterned Attackers -->
								<c:forEach items="${enabledAndPatternedAttackers}"
									var="attacker">
									<div class="w3-col w3-padding w3-margin w3-border">
										<div class="w3-row">
											<div class="w3-col l1 s1 m1">
												<b>${attacker.name}</b>
											</div>
											<div class="w3-col l3 s3 m3 w3-tiny">
												<span class="w3-tag w3-round w3-teal w3-padding">
													Enabled </span> <span class="w3-tag w3-round w3-teal w3-padding">
													Included </span>
											</div>
											<div class="w3-col l8 s8 m8">
												<c:forEach
													items="${referencesPerAttacker[attacker.intrusion_set_id]}"
													var="reference" varStatus="loop">
													<c:if test="${not empty reference.url}">
														<c:if test="${loop.index!=0}">,</c:if>
														<a href="${reference.url}" target="_blank">${reference.external_id}
															(${reference.source_name})</a>
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
													Included </span>
											</div>
											<div class="w3-col l8 s8 m8">
												<c:forEach
													items="${referencesPerAttacker[attacker.intrusion_set_id]}"
													var="reference" varStatus="loop">
													<c:if test="${not empty reference.url}">
														<c:if test="${loop.index!=0}">,</c:if>
														<a href="${reference.url}" target="_blank">${reference.external_id}
															(${reference.source_name})</a>
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
													Motivated </span>
											</div>
											<div class="w3-col l8 s8 m8">
												<c:forEach
													items="${referencesPerAttacker[attacker.intrusion_set_id]}"
													var="reference" varStatus="loop">
													<c:if test="${not empty reference.url}">
														<c:if test="${loop.index!=0}">,</c:if>
														<a href="${reference.url}" target="_blank">${reference.external_id}
															(${reference.source_name})</a>
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
													Enabled </span>
											</div>
											<div class="w3-col l8 s8 m8">
												<c:forEach
													items="${referencesPerAttacker[attacker.intrusion_set_id]}"
													var="reference" varStatus="loop">
													<c:if test="${not empty reference.url}">
														<c:if test="${loop.index!=0}">,</c:if>
														<a href="${reference.url}" target="_blank">${reference.external_id}
															(${reference.source_name})</a>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</div>
								</c:forEach>

							</div>

						</div>
						<div
							class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom malware-overview"
							style="display: none">
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
												<c:forEach
													items="${referencesPerMalware[attacker.malware_id]}"
													var="reference" varStatus="loop">
													<c:if test="${not empty reference.url}">
														<c:if test="${loop.index!=0}">,</c:if>
														<a href="${reference.url}" target="_blank">${reference.external_id}
															(${reference.source_name})</a>
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
									<div class="risk-class"
										data-val-min="${risk.value.risk_value_min}"
										data-val-max="${risk.value.risk_value_max}"
										data-name="${risk.value.category_name}"></div>
								</c:forEach>
							</div>
							<div id="probability-classes-box">
								<c:forEach items="${probabilityCategories}" var="probability">
									<div class="probability-class"
										data-val-min="${probability.value.risk_value_min}"
										data-val-max="${probability.value.risk_value_max}"
										data-name="${probability.value.category_name}"></div>
								</c:forEach>
							</div>
							<div id="impact-classes-box">
								<c:forEach items="${impactCategories}" var="impact">
									<div class="impact-class"
										data-val-min="${impact.value.risk_value_min}"
										data-val-max="${impact.value.risk_value_max}"
										data-name="${impact.value.category_name}"></div>
								</c:forEach>
							</div>
						</div>
					</c:if>
				</div>
			</div>





			<!-- End Grid -->
		</div>

		<!-- End Page Container -->
	</div>

	


</body>
</html>