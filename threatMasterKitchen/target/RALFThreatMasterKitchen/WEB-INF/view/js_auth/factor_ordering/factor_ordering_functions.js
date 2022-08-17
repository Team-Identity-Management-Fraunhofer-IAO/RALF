$(function(){
	bindCSRFToAJAXHeaders();
	$("#submit-factor-ordering-questionnaire-btn").click(function(){
		var organizationId = parseInt($("input[name='organization_id']").val(),10);
		var serviceId = parseInt($("input[name='service_id']").val(),10);
		var motivatingFactorComparisons = [];
		var vulnerabilityEnablingFactorComparisons = [];
		$("div.comparison-block").each(function(){
			var comparison = parseInt($(this).find("select[name='comparison']").val(), 10);
			var attackMotivatingFactorId = $(this).data("attack-motivating-factor-id");
			var attackMotivatingFactorComparisonId = $(this).data("attack-motivating-factor-compared-id");
			var comparisonOperator = $(this).find("input[name='comparison_motivating_operator_"+attackMotivatingFactorComparisonId+"_"+attackMotivatingFactorId+"']").val();
			var comparisonJSON = {
				factor_id : attackMotivatingFactorId,
				factor_comparison_id : attackMotivatingFactorComparisonId,
				comparison: comparison,
				comparison_operator: comparisonOperator
			};
			motivatingFactorComparisons.push(comparisonJSON);
		});
		$("div.comparison-block-vulnerability-enabling").each(function(){
			var comparison = parseInt($(this).find("select[name='comparison']").val(), 10);
			var vulnerabilityEnablingFactorId = $(this).data("vulnerability-enabling-factor-id");
			var vulnerabilityEnablingFactorComparisonId = $(this).data("vulnerability-enabling-factor-compared-id");
			var comparisonOperator = $(this).find("input[name='comparison_operator_"+vulnerabilityEnablingFactorComparisonId+"_"+vulnerabilityEnablingFactorId+"']").val();
			var comparisonJSON = {
				factor_id : vulnerabilityEnablingFactorId,
				factor_comparison_id : vulnerabilityEnablingFactorComparisonId,
				comparison: comparison,
				comparison_operator: comparisonOperator
			};
			vulnerabilityEnablingFactorComparisons.push(comparisonJSON);
		});
		var JSONText = {
			organization_id : organizationId,
			service_id : serviceId,
			motivatingFactorComparisons : motivatingFactorComparisons,
			vulnerabilityEnablingFactorComparisons : vulnerabilityEnablingFactorComparisons
		}
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(JSONText),
			url : "../../adjustWeights",
			success : function(data){
				alert("Factor Priorization successfully submitted!");
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
});

function bindCSRFToAJAXHeaders(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajaxSetup({
		beforeSend: function(xhr, settings){
			if (settings.type == 'POST'){
				xhr.setRequestHeader(header, token);
			}
		}
	});
}