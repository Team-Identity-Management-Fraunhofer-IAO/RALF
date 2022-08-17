function init() {
	$("#createUser").click(function() {
		var username = $("#userNameInput").val();
		var email = $("#emailInput").val();
		createUser(username, email);
	});
	$(".resetPasswordButton").click(function() {
		var username = $(this).parent().data("username");
		resetPassword(username);
	});
	$(".removeUserButton").click(function() {
		var username = $(this).parent().data("username");
		deleteUser(username);
	});
	$(".alterAssignmentButton").click(function(){
		var username = $(this).parent().data("username");
		var cont = $(this).parent().next();
		var groupname = cont.find(".groupNameInput").val();
		var owner = cont.find(".ownerInput").prop("checked");
		addAssignment(username, groupname, owner);
	});
	$(".removeAssignmentButton").click(function(){
		var username = $(this).parent().data("username");
		var assignment = $(this).parent().data("groupname");
		removeAssignment(username,assignment);
	});
	$("#addGroup").click(function() {
		$("body").css("cursor", "wait");
		$(this).attr("disabled", "disabled");
		var groupn = $("#roleNameInput").val();
		var authn = $("#newGroupRightsSelector").val();
		addNewGroup(groupn, authn);
	});
	$(".alterGroupButton").click(function() {
		$("body").css("cursor", "wait");
		$(this).attr("disabled", "disabled");
		var groupn = $(this).parent().prev().text().trim();
		var authn = $(this).parent().next().find(".role-right-selector").val();
		alterGroup(groupn, authn);
	});
	$(".removeGroupButton").click(function() {
		$("body").css("cursor", "wait");
		$(this).attr("disabled", "disabled");
		var groupn = $(this).parent().prev().text().trim();
		deleteGroup(groupn);
	});
	var listContainer = $(".SWStackList").find(".list-items-single");
	listContainer.find(".list-item").click(function() {
		var stackname = $(this).data("stackname");
		var groupname = $(this).parent().parent().prev().text().trim();
		removeStackFromGroup(groupname, stackname);
	});
	listContainer.find(".list-items-function").click(function() {
		var par = $(this).parent().parent();
		par.find(".stacksAddInput").css("display", "inline");
		par.find(".stacksAddBtn").css("display", "inline");
		par.find(".stacksCancelBtn").css("display", "inline");
		$(this).css("display", "none");
	});
	listContainer.parent().find(".stacksAddBtn").click(function() {
		var par = $(this).parent();
		var stackName = par.find(".stacksAddInput").val();
		var groupName = par.prev().text().trim();
		par.find(".stacksAddInput").css("display", "none");
		par.find(".stacksAddInput").val("");
		par.find(".stacksAddBtn").css("display", "none");
		par.find(".stacksCancelBtn").css("display", "none");
		$("body").css("cursor", "wait");
		addStackToGroup(groupName, stackName);
	});
	listContainer.parent().find(".stacksCancelBtn").click(
			function() {
				var par = $(this).parent();
				par.find(".list-items-single").find(".list-items-function")
						.css("display", "inline");
				par.find(".stacksAddInput").css("display", "none");
				par.find(".stacksAddInput").val("");
				par.find(".stacksAddBtn").css("display", "none");
				par.find(".stacksCancelBtn").css("display", "none");
			});
}

function removeAssignment(usern, groupn){
	$
	.ajax({
		type : "POST",
		url : "./userManagement/removeAssignment",
		data : {
			username : usern, groupname : groupn
		},
		statusCode : {
			200 : function(data) {
				showMessage("message",
						"User has been removed from the group");
				location.reload();
			},
			404 : function(data) {
				showMessage("alert",
						"Service unreachable. Please check your connection!");
			},
			500 : function(data) {
				showMessage("alert",
						"An error has occured! The user has not been removed from the group!");
			}
		}
	});
}


function addAssignment(usern, groupn, own){
	$
	.ajax({
		type : "POST",
		url : "./userManagement/addAssignment",
		data : {
			username : usern, groupname : groupn, owner: own
		},
		statusCode : {
			200 : function(data) {
				showMessage("message",
						"User has been added to the group");
				location.reload();
			},
			404 : function(data) {
				showMessage("alert",
						"Service unreachable. Please check your connection!");
			},
			500 : function(data) {
				showMessage("alert",
						"An error has occured! The user has not been added to the group!");
			}
		}
	});
}

function deleteUser(usern) {
	if (confirm("Are you sure you want to delete this user?")) {
		$
				.ajax({
					type : "POST",
					url : "./userManagement/deleteUser",
					data : {
						username : usern
					},
					statusCode : {
						200 : function(data) {
							showMessage("message",
									"User has been deleted");
							location.reload();
						},
						404 : function(data) {
							showMessage("alert",
									"Service unreachable. Please check your connection!");
						},
						500 : function(data) {
							showMessage("alert",
									"An error has occured! The user has not been deleted!");
						}
					}
				});
	}
}

function resetPassword(usern) {
	$
			.ajax({
				type : "POST",
				url : "./userManagement/resetPassword",
				data : {
					username : usern
				},
				statusCode : {
					200 : function(data) {
						showMessage("message", "Password has been resetted!");
					},
					404 : function(data) {
						showMessage("alert",
								"Service unreachable. Please check your connection!");
					},
					500 : function(data) {
						showMessage("alert",
								"An error has occured! The password has not been resetted!");
					}
				}
			});
}

function createUser(usern, emailn) {
	$
			.ajax({
				type : "POST",
				url : "./userManagement/createNewUser",
				data : {
					username : usern,
					email : emailn
				},
				statusCode : {
					200 : function(data) {
						showMessage("message",
								"User has been successfully created!");
						location.reload();
					},
					404 : function(data) {
						showMessage("alert",
								"Service unreachable. Please check your connection!");
					},
					500 : function(data) {
						showMessage("alert",
								"An error has occured! The user has not been created!");
					}
				}
			});
}

function deleteGroup(groupn) {
	$
			.ajax({
				type : "POST",
				url : "./groupManagement/deleteGroup",
				data : {
					groupname : groupn
				},
				statusCode : {
					200 : function(data) {
						showMessage("message",
								"Group has been successfully deleted!");
						location.reload();
					},
					404 : function(data) {
						showMessage("alert",
								"Service unreachable. Please check your connection!");
					},
					403 : function(data) {
						showMessage("alert",
								"I cannot let you do this! The administration group can not be altered!");
						setTimeout(function() {
							location.reload();
						}, 500);
					},
					500 : function(data) {
						showMessage("alert",
								"An error has occured! The group has not been deleted!");
					}
				}
			});
}

function alterGroup(groupn, authn) {
	$
			.ajax({
				type : "POST",
				url : "./groupManagement/alterGroup",
				data : {
					groupname : groupn,
					authorityName : authn
				},
				statusCode : {
					200 : function(data) {
						showMessage("message",
								"Group has been successfully updated!");
						location.reload();
					},
					404 : function(data) {
						showMessage("alert",
								"Service unreachable. Please check your connection!");
					},
					403 : function(data) {
						showMessage("alert",
								"I cannot let you do this! The administration group can not be altered!");
						setTimeout(function() {
							location.reload();
						}, 500);
					},
					500 : function(data) {
						showMessage("alert",
								"An error has occured! The group has not been updated!");
					}
				}
			});
}

function addNewGroup(groupn, authn) {
	$
			.ajax({
				type : "POST",
				url : "./groupManagement/createNewGroup",
				data : {
					groupname : groupn,
					authorityName : authn
				},
				statusCode : {
					200 : function(data) {
						showMessage("message",
								"Group has been successfully created!");
						location.reload();
					},
					404 : function(data) {
						showMessage("alert",
								"Service unreachable. Please check your connection!");
					},
					500 : function(data) {
						showMessage("alert",
								"An error has occured! The group has not been created!");
					}
				}
			});
}

function addStackToGroup(groupn, stackn) {
	$
			.ajax({
				type : "POST",
				url : "./SWStackManagement/addStackToGroup",
				data : {
					groupname : groupn,
					stackname : stackn
				},
				statusCode : {
					200 : function(data) {
						$(".SWStackList").find(".list-items-single").find(
								".list-items-function")
								.css("display", "inline");
						showMessage("message",
								"Softwarestack has been successfully added to the group!");
						location.reload();
					},
					404 : function(data) {
						showMessage("alert",
								"Service unreachable. Please check your connection!");
					},
					500 : function(data) {
						showMessage("alert",
								"An error has occured! The softwarestack has not been added to the group!");
					}
				}

			});
}

function removeStackFromGroup(groupn, stackn) {
	$
			.ajax({
				type : "POST",
				url : "./SWStackManagement/removeStackFromGroup",
				data : {
					groupname : groupn,
					stackname : stackn
				},
				statusCode : {
					200 : function(data) {
						showMessage("message",
								"Softwarestack has been successfully removed from the group!");
						location.reload();
					},
					404 : function(data) {
						showMessage("alert",
								"Service unreachable. Please check your connection!");
					},
					500 : function(data) {
						showMessage("alert",
								"An error has occured! The softwarestack has not been removed from the group!");
					}
				}

			});
}