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
						
					<ul>
						<c:forEach items="${organizations}" var="organization"> 
							<li class="organisation">${organization.organization_name}
								<ul>
									<c:forEach items="${services}" var="service">
										<li><a class="subnet" href="${context}/RiskAssessment/${service.service_id}" data-service-id="${service.service_id}">${service.service_name}</a>											
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
				
				<div class="w3-col l10 m8 s6 w3-light-grey" style="padding-left: 16px; min-height: 99vh;">
					
					<c:if test="${function2=='riskAssessmentOverview'}">
						<div class="w3-card w3-white w3-margin-right w3-padding w3-small w3-margin-bottom">
							<div class="w3-row w3-padding">
								<div class="w3-col text-align:right">
									<a href="./${service_id}/RiskQuestionnaire" class="w3-button w3-teal" style="display: block; float: right;">New Assessment</a>
								</div>
								<div class="w3-col">
									<div class="w3-row w3-leftbar border-ralf-blue w3-margin-bottom">
										<div class="w3-col">
											<h4>List of Reports</h4>
										<div>
										<c:forEach items="${riskReports}" var="riskReport">
											<div class="w3-col l2 m2 s2">
												<a href="${context}/RiskAssessment/${service_id}/RiskReporting/ViewReport/${riskReport.risk_report_id}">${riskReport.assessmentTimestamp}</a>
											</div>											
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
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