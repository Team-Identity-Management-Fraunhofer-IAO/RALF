$(function(){
	bindCSRFToAJAXHeaders();
	$("div.course_of_action_descriptor").toggle(false);
	$("div.factor_descriptor").toggle(false);
	$("div.pattern-box").toggle(false);
	$("select[name='collection_selector']").change(function(){
		window.location = './SuccessProbabilities/'+$(this).val();
	});
	$("a.control-help-btn").click(function(){
		var id = $(this).parent().data("id");
		$("div.course_of_action_descriptor").css("display","none");
		$(this).parent().next().css("left",event.pageX);
		$(this).parent().next().css("top",event.pageY);
		$(this).parent().next().toggle(true);
		
	});
	$("a.factor-help-btn").click(function(){
		var id = $(this).parent().data("id");
		$("div.factor_descriptor").css("display","none");
		$(this).parent().next().css("left",event.pageX);
		$(this).parent().next().css("top",event.pageY);
		$(this).parent().next().css("display","block");
	});
	$("a.control-close-btn").click(function(){
		$(this).parent().css("display","none");
	});
	$("a.factor-close-btn").click(function(){
		$(this).parent().css("display","none");
	});
	$("input.pattern-checkbox").click(function(){
		var id=$(this).data("id");
		if ($(this).is(":checked")){
			$("input.pattern_"+id).prop("checked",true);
			$("#pattern_box_"+id).css("display","block");
		}else{
			$("input.pattern_"+id).prop("checked",false);
			$("#pattern_box_"+id).css("display","none");
		}
	});
	$("span.add-new-probability-btn").click(function(){
		var templateBox = $(this).parent().parent().find("div.probability-template-box").clone(true, true);
		templateBox.removeClass("probability-template-box");
		templateBox.addClass("new-probability-box");
		$(this).parent().parent().find("div.header-box").after(templateBox);
	});
	$("a.control-add-btn").click(function(){
		var tag = $(this).parent().clone(true,true);
		var dest = $(this).parent().parent().parent().find("div.new-probability-box div.control-box");
		tag.find("a.control-add-btn").remove();
		tag.find("a.control-help-btn").remove();
		dest.append(tag);
	});
	$("a.factor-add-btn").click(function(){
		var tag = $(this).parent().clone(true,true);
		var dest = $(this).parent().parent().parent().find("div.new-probability-box div.factor-box");
		tag.find("a.factor-add-btn").remove();
		tag.find("a.factor-help-btn").remove();
		dest.append(tag);
	});
	$("a.platform-add-btn").click(function(){
		var tag = $(this).parent().clone(true,true);
		var dest = $(this).parent().parent().parent().find("div.new-probability-box div.platform-box");
		tag.find("a.platform-add-btn").remove();
		dest.append(tag);
	});
	$("a.probability-action-delete-box").click(function(){
		var id = $(this).data("id");
		var updateJSON = {
			action: 'delete',
			attack_pattern_id: -1,
			c_success_probability_id: id,
			probability: 0,
			controls : [],
			factors: []
		};
		$("#loading-gif").css("display","block");
		var probabilityBox = $(this).parent().parent();
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(updateJSON),
			url : "./updateProbability",
			success : function(data){
				probabilityBox.remove();
				$("#loading-gif").css("display","none");
			},
			error : function(){
				alert("An error has occured. The success probability has not been updated!");
				$("#loading-gif").css("display","none");
			}
		});
	});
	$("a.probability-action-save-box").click(function(){
		var probabilityBox = $(this).parent().parent().parent();
		var newProbabilityBox = $(this).parent().parent();
		var patternID = probabilityBox.data("id");
		var controlIDs = [];
		newProbabilityBox.find("div.control-box span").each(function(){
			controlIDs.push($(this).data("id"));
		});
		var factorIDs = [];
		newProbabilityBox.find("div.factor-box span").each(function(){
			factorIDs.push($(this).data("id"));
		});
		var probability = parseInt(newProbabilityBox.find("input.success_probability_slider").val());
		var updateJSON = {
			action: 'new',
			attack_pattern_id: patternID,
			c_success_probability_id: -1,
			probability: probability,
			controls : controlIDs,
			factors: factorIDs
		};
		$("#loading-gif").css("display","block");
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(updateJSON),
			url : "./updateProbability",
			success : function(data){
				var id = data;
				$("#loading-gif").css("display","none");
				newProbabilityBox.find("a.probability-action-delete-box").css("display","block");
				newProbabilityBox.find("a.probability-action-delete-box").data("id",id);
				newProbabilityBox.find("a.probability-action-save-box").remove();
				newProbabilityBox.removeClass("new-probability-box");
				newProbabilityBox.addClass("probability-box");
			},
			error : function(){
				alert("An error has occured. The success probability has not been updated!");
				$("#loading-gif").css("display","none");
			}
		});
	});
	$("form.update-platform-form").submit(function(){
		var attack_pattern_id = $(this).find("input[name='attack_pattern_id']").val();
		var platforms = $(this).find("input[name='platforms']").val();
		var platformJSON = {
			attack_pattern_id: attack_pattern_id,
			platforms: platforms
		};
		$("#loading-gif").css("display","block");
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(platformJSON),
			url : "./PlatformManagement/updatePlatforms",
			success : function(data){
				var id = data;
				$("#loading-gif").css("display","none");
			},
			error : function(){
				alert("An error has occured. The platform has not been updated!");
				$("#loading-gif").css("display","none");
			}
		});
		return false;
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