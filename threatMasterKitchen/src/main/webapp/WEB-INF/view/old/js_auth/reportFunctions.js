function newReport(now, recurring){
	if (now){
		$("#report-new").css("display","grid");
		$("#create-report-later-btn").addClass("disabled");
		$("#create-report-recurring-btn").addClass("disabled");
	}
}