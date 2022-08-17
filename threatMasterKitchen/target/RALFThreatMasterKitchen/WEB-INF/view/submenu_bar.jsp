<c:if test="${application=='service_definition' && function!=''}">
		<div class="w3-col l10 m8 s6 w3-padding w3-light-grey">
			<div class="w3-bar w3-teal w3-card w3-left-align w3-medium">
				<a class="w3-bar-item w3-button w3-hide-medium w3-right w3-padding-medium" href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><span class="bar-white"></span><span class="bar-white"></span><span class="bar-white"></span></a>
				<c:choose>
					<c:when test="${function=='service_description'}">
						<a href="${adress_service_description}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>1. Service Definition</b></a>
					</c:when>
					<c:otherwise>
						<a href="${adress_service_description}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">1. Service Definition</a>
					</c:otherwise>
				</c:choose>
					<a href="${adress_risk_classes}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white" target="_blank" rel="noopener noreferrer">2. Risk Classes Definition</a>
					<a href="${adress_risk_phi}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white" target="_blank" rel="noopener noreferrer">3. Risk Combination Preference</a>
					<a href="${adress_factor_weighing}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white" target="_blank" rel="noopener noreferrer">4. Attack Existence Characteristics</a>
					<a href="${adress_existence_probability}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white" target="_blank" rel="noopener noreferrer">5. Attack Existence Questionnaire</a>
				<c:choose>
					<c:when test="${submenu=='impactOrdering'}">
						<a href="${adress_impact_ordering}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-white"><b>6. Impact Assessment</b></a>
					</c:when>
					<c:otherwise>
						<a href="${adress_impact_ordering}" class="w3-bar-item w3-button w3-hide-small w3-padding-medium w3-hover-white">6. Impact Assessment</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
</c:if>