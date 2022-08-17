function initializeKnowledgeManager(){
	$("button.add-action-network-btn").click(function(){
		showEditor();
	});
	$("button.add-action-adjacent-btn").click(function(){	
		showEditor();
	});
	$("button.add-action-physical-btn").click(function(){	
		showEditor();
	});
	$("button.add-action-local-btn").click(function(){
		showEditor();
	});
	
	$("button.save-action-btn").click(function(){
		var vectorExposure = [];
		var privilegesRequired = [];
		var userInteraction = [];
		var vectors = [];
		$.each($("input[name='vector']:checked"),function(){
			vectors.push($(this).val());
		});
		$.each($("input[name=vectorExposure]:checked"),function(){
			vectorExposure.push($(this).val());
		});
		$.each($("input[name=privilegesRequired]:checked"), function(){
			privilegesRequired.push($(this).val());
		});
		$.each($("input[name=userInteraction]:checked"), function(){
			userInteraction.push($(this).val());
		});
		var title = $("input[name=title]").val();
		var description = $("textarea[name=description]").val();
		var dataJSON = {
			'title' : title,
			'description' : description,
			'vector' : vectors,
			'vectorExposure' : vectorExposure,
			'privilegesRequired' : privilegesRequired,
			'userInteraction' : userInteraction
		};		
		console.log(dataJSON);
		$.ajax({
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(dataJSON),
			url : "./addVulnerabilityManagementKnowledge",
			success : function(data) {
				alert("Knowledge has been addded to the database!");
				location.reload();
			},
			error : function() {
				alert("An error has occured! Please reload and try again.");
			}
		});
	});
	bindCSRFToAJAXHeaders();
}

function showEditor(){
	$("#action-viewer").fadeToggle("slow", function(){
		$("#action-editor").fadeToggle("slow","linear");
	});
}

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