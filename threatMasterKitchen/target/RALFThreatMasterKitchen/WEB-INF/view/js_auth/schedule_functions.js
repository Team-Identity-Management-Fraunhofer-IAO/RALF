function createSchedule(JSONData, rowObject){
	$
	.ajax({
		method : "POST",
		headers:{
			"Content-Type": "application/json"
		},
		data:JSON.stringify(JSONData),
		url : "./createSchedule",
		success: function(data){
			location.reload();
		},
		error: function(){
			$(rowObject).remove();
			alert("An error has occured! The schedule has not been saved!");
		}
	});
}

function updateSchedule(JSONData){
	$
	.ajax({
		method : "POST",
		headers:{
			"Content-Type": "application/json"
		},
		data:JSON.stringify(JSONData),
		url : "./updateSchedule",
		success: function(data){
			location.reload();
		},
		error: function(){
			$(rowObject).remove();
			alert("An error has occured! The schedule has not been updated!");
		}
	});
}

function deleteSchedule(JSONData){
	$
	.ajax({
		method : "POST",
		headers:{
			"Content-Type": "application/json"
		},
		data:JSON.stringify(JSONData),
		url : "./deleteSchedule",
		success: function(data){
			location.reload();
		},
		error: function(){
			$(rowObject).remove();
			alert("An error has occured! The schedule has not been updated!");
		}
	});
}

/*
 * 
 * CSRF Protection
 * 
 */
//Bind CSRF Token to AJAX Headers
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