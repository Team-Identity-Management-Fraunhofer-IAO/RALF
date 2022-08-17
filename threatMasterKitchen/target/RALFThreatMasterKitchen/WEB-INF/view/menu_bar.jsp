<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-card w3-left-align w3-medium" style="line-height: 30pt; background-color: #1f82c0; color: #fff;">
    <img src="${context}/img_auth/ralf_logo_very_small.png" class="w3-bar-item" style="background-color: #1f82c0; padding: 5px 28px 0 15px;"/>
    <c:choose>
    	<c:when test="${application=='service_definition'}">
    		<a href="${adress_service_definition}" class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right" style="padding-top: 5px; padding-bottom: 2px"><b>Service Definition</b></a>
    	</c:when>
    	<c:otherwise>
    		<a href="${adress_service_definition}" class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right w3-hover-white" style="padding-top: 5px; padding-bottom: 2px">Service Definition</a>
    	</c:otherwise>
    </c:choose>
    <c:choose>
    	<c:when test="${application=='risk_assessment'}">
    		<a href="${adress_risk_assessment}" class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right" style="padding-top: 5px; padding-bottom: 2px;"><b>Risk Assessment</b></a>
    	</c:when>
    	<c:otherwise>
    		<a href="${adress_risk_assessment}" class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right" style="padding-top: 5px; padding-bottom: 2px;">Risk Assessment</a>
    	</c:otherwise>
    </c:choose>
        
    <a href="" class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right w3-disabled" style="padding-top: 5px; padding-bottom: 2px;">Threat Modeling</a>
    
    <a href="" class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right w3-disabled" style="padding-top: 5px; padding-bottom: 2px;">Reporting</a>
    
    <!-- End of Disabled Applications -->
	<i class="w3-margin-left"></i>
	<c:choose>
    	<c:when test="${application=='settings'}">
    		<a href="${adress_settings}" class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right" style="padding-top: 5px; padding-bottom: 2px"><b>Settings</b></a>
    	</c:when>
    	<c:otherwise>
    		<a href="${adress_settings}" class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right w3-hover-white" style="padding-top: 5px; padding-bottom: 2px">Settings</a>
    	</c:otherwise>
    </c:choose>
	<c:if test="${isAdmin}">
		<c:choose>
	    	<c:when test="${application=='administration'}">
	    		<div class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right w3-dropdown-hover" style="padding-top: 5px; padding-bottom: 2px"><b>Administration</b>
	    			<div class="w3-dropdown-content w3-bar-block w3-border">
	    				<a class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right" style="padding-top: 5px; padding-bottom: 2px">Platform Management</a>
	    				<a class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right" style="padding-top: 5px; padding-bottom: 2px">Threat Management</a>
	    			</div>
	    		</div>
	    	</c:when>
	    	<c:otherwise>
	    		<div class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right w3-dropdown-hover" style="padding-top: 5px; padding-bottom: 2px">Administration
	    			<div class="w3-dropdown-content w3-bar-block w3-border">
	    				<a class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right" style="padding-top: 5px; padding-bottom: 2px">Platform Management</a>
	    				<a class="w3-bar-item w3-button w3-hide-small w3-medium w3-padding-left w3-padding-right" style="padding-top: 5px; padding-bottom: 2px">Threat Management</a>
	    			</div>
	    		</div>
	    	</c:otherwise>
    	</c:choose>
		
	</c:if>
	
	<!-- Loading image -->
	<div id="loading-gif">
		<img src="${context}/img_auth/loader.gif"/>
	</div>
  	<form action="${adress_logoff}" class="w3-right" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<button class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-tiny w3-right" style="padding: 5px 16px 2px 16px !important">Logged in as ${principal} (Logout)</button>
	</form>
  </div>
  <div class="w3-bar w3-teal w3-card w3-left-align w3-medium">
  	<c:if test="${application=='vulnerability_assessment'}">  
	    <a class="w3-bar-item w3-button w3-hide-medium w3-right w3-padding-medium" href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><span class="bar-white"></span><span class="bar-white"></span><span class="bar-white"></span></a>
	    <c:choose>	
	    	<c:when test="${function=='dashboard'}">
	    		<a href="${adress_dashboard}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>Dashboard</b></a>
	    	</c:when>
	    	<c:otherwise>
	    		<a href="${adress_dashboard}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">Dashboard</a>
	    	</c:otherwise>
	    </c:choose>
	    <c:choose>	
	    	<c:when test="${function=='application_modelling'}">
	    		<a href="${adress_application_modelling}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>Application Modelling</b></a>
	    	</c:when>
	    	<c:otherwise>
	    		<a href="${adress_application_modelling}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">Application Modelling</a>
	    	</c:otherwise>
	    </c:choose>
	    <c:choose>	
	    	<c:when test="${function=='assessment_schedules'}">
	    		<a href="${adress_assessment_schedules}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>Assessment Schedules</b></a>
	    	</c:when>
	    	<c:otherwise>
	    		<a href="${adress_assessment_schedules}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">Assessment Schedules</a>
	    	</c:otherwise>
	    </c:choose>
	    <c:choose>	
	    	<c:when test="${function=='assessment_reports'}">
	    		<a href="${adress_assessment_reports}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>Assessment Reports</b></a>
	    	</c:when>
	    	<c:otherwise>
	    		<a href="${adress_assessment_reports}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">Assessment Reports</a>
	    	</c:otherwise>
	    </c:choose>
	</c:if>
	
	<c:if test="${application=='vulnerability_management'}">
		<a class="w3-bar-item w3-button w3-hide-medium w3-right w3-padding-medium" href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><span class="bar-white"></span><span class="bar-white"></span><span class="bar-white"></span></a>
	    <c:choose>	
	    	<c:when test="${function=='subnet_modelling'}">
	    		<a href="${adress_subnet_modelling}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>Vulnerability Identification</b></a>
	    	</c:when>
	    	<c:otherwise>
	    		<a href="${adress_subnet_modelling}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">Vulnerability Identification</a>
	    	</c:otherwise>
	    </c:choose>
	    <c:choose>	
	    	<c:when test="${function=='vulnerability_list'}">
	    		<a href="${adress_vulnerability_list}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>Vulnerability Analysis</b></a>
	    	</c:when>
	    	<c:otherwise>
	    		<a href="${adress_vulnerability_list}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">Vulnerability Analysis</a>
	    	</c:otherwise>
	    </c:choose>
	    <c:choose>	
	    	<c:when test="${function=='vulnerability_contextualization'}">
	    		<a href="${adress_vulnerability_contextualization}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>Vulnerability Management</b></a>
	    	</c:when>
	    	<c:otherwise>
	    		<a href="${adress_vulnerability_contextualization}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">Vulnerability Management</a>
	    	</c:otherwise>
	    </c:choose>
	</c:if>
	
	<c:if test="${application=='administration'}">
		<a class="w3-bar-item w3-button w3-hide-medium w3-right w3-padding-medium" href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><span class="bar-white"></span><span class="bar-white"></span><span class="bar-white"></span></a>
		<c:choose>
			<c:when test="${function=='Groups'}">
				<a href="${adress_administration_group_management}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>User Groups</b></a>
			</c:when>
			<c:otherwise>
				<a href="${adress_administration_group_management}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">User Groups</a>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${function=='Stacks'}">
				<a href="${adress_administration_application_assignments}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>Application Authorizations</b></a>
			</c:when>
			<c:otherwise>
				<a href="${adress_administration_application_assignments}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">Application Authorizations</a>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${function=='Users'}">
				<a href="${adress_administration_user_management}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>User Accounts</b></a>
			</c:when>
			<c:otherwise>
				<a href="${adress_administration_user_management}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">User Accounts</a>
			</c:otherwise>
		</c:choose>
		<i class="w3-margin-right"></i>
		<c:choose>
			<c:when test="${function=='Harvester'}">
				<a href="${adress_administration_data_harvesting}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>Data Harvesting</b></a>
			</c:when>
			<c:otherwise>
				<a href="${adress_administration_data_harvesting}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">Data Harvesting</a>
			</c:otherwise>
		</c:choose>
	</c:if>
	
  </div>

  <!-- Navbar on small screens -->
  <c:if test="${application=='administration'}">
	  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-medium">
	  <a href="${adress_dashboard}" class="w3-bar-item w3-button w3-padding-large">Application Threat Dashboard</a>
	    <a href="${adress_application_modelling}" class="w3-bar-item w3-button w3-padding-large">Application Modelling</a>
	    <a href="${adress_assessment_schedules}" class="w3-bar-item w3-button w3-padding-large">Assessment Schedules</a>
	    <a href="${adress_assessment_reports}" class="w3-bar-item w3-button w3-padding-large">Assessment Reports</a>
	    <a href="${adress_vulnerability_management}" class="w3-bar-item w3-button w3-padding-large">Vulnerability Management</a>
		<a href="${adress_settings}" class="w3-bar-item w3-button w3-padding-large">Settings</a>
		<c:if test="${isAdmin}">
			<a href="${adress_administration}" class="w3-bar-item w3-button w3-padding-large"><b>Administration</b></a>
		</c:if>
	  </div>
  </c:if>

</div>
