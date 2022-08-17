function createReport(id) {
	var time = $("#time-input").val();
	var explicit = $("#explicit-dropdown").val();
	var recurrence = $("#recurrence-dropdown").val();
	showMessage("message", "Scheduling new report...");
	$("#time-input").prop("disabled", true);
	$("#explicit-dropdown").prop("disabled", true);
	$("#recurrence-dropdown").prop("disabled", true);
	$("#report-schedule-btn").click = function(){};
	$("#report-schedule-btn").html("<img src='../../img_auth/loader.gif' />");
	$.ajax({
		dataType : "json",
		url: applicationContext + "/Assessment/SWStack/createReport/"+id+"/"+time+"/"+explicit+"/"+recurrence,
		statusCode : {
			200 : function(data) {
				showMessage("message", "Report has been successfully created! Refreshing page...");
				setTimeout(function(){
					$("#report-new").fadeOut();
					location.reload();
				}, 100);
			},
			404 : function(data) {
				showMessage("alert",
						"Service unreachable. Please check your connection!");
			},
			500 : function(data){
				showMessage("alert", "A server error occured. Report has not been created.");
			}
		}
	})
	
}