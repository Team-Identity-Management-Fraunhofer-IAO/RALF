function initializeUserSettingsGUI(){
	$("#change-password-btn").click(function(){
		var parent = $(this).parent().parent().parent();
		var pass = $(parent).find("input[name=new-password]").val();
		var pass_repeat = $(parent).find("input[name=new-password-confirm]").val();
		if (pass === pass_repeat){
			$(parent).find("input[name=new-password]").attr("style","");
			$(parent).find("input[name=new-password-confirm]").attr("style","");
			var username = $(parent).find("input[name=username]").val();
			var userSettingsChangeData = {
				"username" : username,
				"password" : pass
			}
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(userSettingsChangeData),
				url : "./Settings/changePassword",
				success : function(data){
					alert("Your password has been successfully changed!");
				},
				error : function(){
					alert("An error has occured! Please try again!");
				}
			});
		}else{
			$(parent).find("input[name=new-password]").attr("style","border 1px solid red");
			$(parent).find("input[name=new-password-confirm]").attr("style","border 1px solid red");
			alert("Passwords do not match!");
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