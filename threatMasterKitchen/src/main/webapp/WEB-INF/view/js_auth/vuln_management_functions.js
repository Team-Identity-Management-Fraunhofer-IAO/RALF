function initializeWatchlistSettings(){
	$("button.watchlist-save-btn").click(function(){
		var id = $("input[name=current-cve-id]").val();
		var notification = $("select[name=vulnerability-notification]").val();
		var treated = $("input[name=treated]").prop("checked");
		var mail = $("input[name=mail]").val();
		var dataJSON = {
			'id' : id,
			'notification' : notification,
			'treated' : treated,
			'mail' : mail
		}
		$.ajax({
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(dataJSON),
			url : "../updateManagedVulnerability",
			success : function(data) {
				alert("Vulnerability has been updated!");
				location.reload();
			},
			error : function() {
				alert("An error has occured! Please reload and try again.");
			}
		});
	});
	$("#show-treated-checkbox").change(function(){
		if ($(this).prop("checked") == false){
			$("li.treated").attr("style","display:none");
		}else{
			$("li.treated").attr("style","display:block");
		}
	});
	bindCSRFToAJAXHeaders();
}

function initialize(){
	$("#show-treated-checkbox").change(function(){
		if ($(this).prop("checked") == false){
			$("li.treated").attr("style","display:none");
		}else{
			$("li.treated").attr("style","display:block");
		}
	});
	bindCSRFToAJAXHeaders();
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