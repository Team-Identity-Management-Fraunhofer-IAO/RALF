$(function(){
	bindCSRFToAJAXHeaders();
	$("#submit-existence-probability-questionnaire-btn").click(function(){
		var organization_id = parseInt($("input[name='organization_id']").val(),10);
		var service_id = parseInt($("input[name='service_id']").val(),10);
		var attack_motivating_questions = [];
		$("div.attack-motivating-question-container").each(function(){
			var attack_motivating_factor_id = parseInt($(this).data("attack-motivating-factor-id"),10);
			var attack_motivating_factor_question_id = parseInt($(this).data("attack-motivating-factor-question-id"),10);
			var response = $(this).find("input[name='attack_motivating_response_"+attack_motivating_factor_id+"']:checked").val()=='Yes'?true:false;
			var justification = $(this).find("textarea[name='attack_motivating_justification_"+attack_motivating_factor_id+"']").val();	
			attack_motivating_questions.push({
				factor_id : attack_motivating_factor_id,
				factor_question_id : attack_motivating_factor_question_id,
				response: response,
				justification: justification
			});
		});
		var vulnerability_enabling_questions = [];
		$("div.vulnerability-enabling-question-container").each(function(){
			var vulnerability_enabling_factor_id = parseInt($(this).data("vulnerability-enabling-factor-id"),10);
			var vulnerability_enabling_factor_question_id = parseInt($(this).data("vulnerability-enabling-factor-question-id"),10);
			var response = $(this).find("input[name='vulnerability_enabling_response_"+vulnerability_enabling_factor_id+"']:checked").val()=='Yes'?true:false;
			var justification = $(this).find("textarea[name='vulnerability_enabling_justification_"+vulnerability_enabling_factor_id+"']").val();	
			vulnerability_enabling_questions.push({
				factor_id : vulnerability_enabling_factor_id,
				factor_question_id : vulnerability_enabling_factor_question_id,
				response: response,
				justification: justification
			});
		});
		var JSONText = {
			organization_id : organization_id,
			service_id : service_id,
			attack_motivating_questions : attack_motivating_questions,
			vulnerability_enabling_questions : vulnerability_enabling_questions
		}
		console.log(JSONText);
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(JSONText),
				url : "../../submitExistenceProbabilityQuestionnaire",
				success : function(data){
					alert("Questionnaire successfully submitted!");
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