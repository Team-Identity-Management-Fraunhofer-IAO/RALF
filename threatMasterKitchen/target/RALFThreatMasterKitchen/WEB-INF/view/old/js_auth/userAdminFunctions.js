function init(){
	$("#new-password-save").click(function(){
		var newPassword = $("#new-password-input").val();
		var newPasswordConfirm = $("#new-password-confirm-input").val();
		if (newPassword == newPasswordConfirm){
			
			changePassword(userName, newPassword);
		}else{
			alert("Cannot proceed. The passwords do not match!");
		}
	});
}

function changePassword(userN, newP){
	$
	.ajax({
		type : "POST",
		url : "./user/changePassword",
		data : {
			username : userN, password : newP
		},
		statusCode : {
			200 : function(data) {
				showMessage("message",
						"The password has been changed!");
				location.reload();
			},
			404 : function(data) {
				showMessage("alert",
						"Service unreachable. Please check your connection!");
			},
			500 : function(data) {
				showMessage("alert",
						"An error has occured! The password has not been changed!");
			}
		}
	});
}