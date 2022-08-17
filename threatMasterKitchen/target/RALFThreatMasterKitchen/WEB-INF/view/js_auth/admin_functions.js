function initializeAdminGUI(){
	//Menu Functions
	$("#menu-group").click(function(){
		if ($("#menu-app").hasClass("w3-white")){
			$("#stack-management").fadeToggle("slow",function(){
				$("#group-management").fadeToggle("slow","linear");
			});
			$("#menu-app").removeClass("w3-white");
			$("#menu-app").addClass("w3-hover-white");
			$("#menu-group").removeClass("w3-hover-white");
			$("#menu-group").addClass("w3-white");
			$("#menu-app").attr("style","");
			$("#menu-group").attr("style","font-weight: bold");
		}else if($("#menu-user").hasClass("w3-white")){
			$("#user-management").fadeToggle("slow",function(){
				$("#group-management").fadeToggle("slow","linear");
			});
			$("#menu-user").removeClass("w3-white");
			$("#menu-user").addClass("w3-hover-white");
			$("#menu-group").removeClass("w3-hover-white");
			$("#menu-group").addClass("w3-white");
			$("#menu-user").attr("style","");
			$("#menu-group").attr("style","font-weight: bold");
		}
	});
	
	$("#menu-app").click(function(){
		if ($("#menu-group").hasClass("w3-white")){
			$("#group-management").fadeToggle("slow",function(){
				$("#stack-management").fadeToggle("slow","linear");
			});
			$("#menu-group").removeClass("w3-white");
			$("#menu-group").addClass("w3-hover-white");
			$("#menu-app").removeClass("w3-hover-white");
			$("#menu-app").addClass("w3-white");
			$("#menu-group").attr("style","");
			$("#menu-app").attr("style","font-weight: bold");
		}else if($("#menu-user").hasClass("w3-white")){
			$("#user-management").fadeToggle("slow",function(){
				$("#stack-management").fadeToggle("slow","linear");
			});
			$("#menu-user").removeClass("w3-white");
			$("#menu-user").addClass("w3-hover-white");
			$("#menu-app").removeClass("w3-hover-white");
			$("#menu-app").addClass("w3-white");
			$("#menu-user").attr("style","");
			$("#menu-app").attr("style","font-weight: bold");
		}
	});
	
	$("#menu-user").click(function(){
		if ($("#menu-group").hasClass("w3-white")){
			$("#group-management").fadeToggle("slow",function(){
				$("#user-management").fadeToggle("slow","linear");
			});
			$("#menu-group").removeClass("w3-white");
			$("#menu-group").addClass("w3-hover-white");
			$("#menu-user").removeClass("w3-hover-white");
			$("#menu-user").addClass("w3-white");
			$("#menu-group").attr("style","");
			$("#menu-user").attr("style","font-weight: bold");
		}else if($("#menu-app").hasClass("w3-white")){
			$("#stack-management").fadeToggle("slow",function(){
				$("#user-management").fadeToggle("slow","linear");
			});
			$("#menu-app").removeClass("w3-white");
			$("#menu-app").addClass("w3-hover-white");
			$("#menu-user").removeClass("w3-hover-white");
			$("#menu-user").addClass("w3-white");
			$("#menu-app").attr("style","");
			$("#menu-user").attr("style","font-weight: bold");
		}
	});
	
	//Show new group editor
	$("#show-new-group-editor-btn").click(function(){
		$("#new-group-editor").fadeToggle("slow","linear");
	});
	
	//Hide new group editor
	$("#create-new-group-cancel-btn").click(function(){
		var parent = $(this).parent().parent();
		$("#new-group-editor").fadeToggle("slow",function(){
			$(parent).find("input[name=group-name]").val("");
			$(parent).find("select[name=group-role]").val("ROLE_SW_STACK_REVIEW");
		});
	});
	
	//Add group
	$("#create-new-group-btn").click(function(){
		var parent = $(this).parent().parent();
		var groupName = $(parent).find("input[name=group-name]").val();
		var authority = $(parent).find("select[name=group-role]").val();
		var newGroupData = {
			"name" : groupName,
			"role" : authority,
			"stack" : -1
		};
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(newGroupData),
			url : prefix+"/admin/createNewGroup",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	
	//Show group editor
	$(".edit-group-btn").click(function(){
		var parent = $(this).parent().parent();
			
		$(parent).find("span.group-authority").fadeToggle("slow",function(){
			$(parent).find("select.group-role").fadeToggle("slow","linear");
		});		
		$(this).fadeToggle("slow",function(){
			$(parent).find("button.update-group-btn").fadeToggle("slow","linear");
			$(parent).find("button.delete-group-btn").fadeToggle("slow","linear");
			$(parent).find("button.update-group-cancel-btn").fadeToggle("slow","linear");
		});
	});
	
	//Close group editor
	$(".update-group-cancel-btn").click(function(){
		var parent = $(this).parent().parent();
		
		$(parent).find("select.group-role").fadeToggle("slow",function(){
			$(parent).find("span.group-authority").fadeToggle("slow","linear");
		});	
		
		$(parent).find("button.update-group-btn").fadeToggle("slow","linear");
		$(parent).find("button.delete-group-btn").fadeToggle("slow","linear");
		$(parent).find("button.update-group-cancel-btn").fadeToggle("slow",function(){
			$(parent).find("button.edit-group-btn").fadeToggle("slow","linear");
		});
		
		$(parent).find("select.group-role").val($(parent).find("span.group-authority").data("value"));
	});
	
	//Delete group
	$("button.delete-group-btn").click(function(){
		var parent = $(this).parent().parent();
		var groupName = $(parent).find("span.group-name").text();
		var deleteGroupData = {
			"name" : groupName,
			"role" : "",
			"stack" : -1
		}
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(deleteGroupData),
			url : prefix+"/admin/deleteGroup",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	
	//Update Group
	$("button.update-group-btn").click(function(){
		var parent = $(this).parent().parent();
		var groupName = $(parent).find("span.group-name").text();
		var authority = $(parent).find("select[name=group-role]").val();
		var updateGroupData = {
			"name" : groupName,
			"role" : authority,
			"stack" : -1
		}
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(updateGroupData),
			url : prefix+"/admin/alterGroup",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	
	//Add Application to Group
	$("button.add-stack-to-group-btn").click(function(){
		var parent = $(this).parent();
		var id = $(parent).parent().find("input[name=group-id]").val();
		var stack_id = $(parent).find("select[name=stack-list]").val();
		var addStackToGroupData = {
			"name" : id,
			"role" : "",
			"stack" : stack_id
		};
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(addStackToGroupData),
			url : prefix+"/admin/addStackToGroup",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	
	//Remove Application from Group
	$("span.stack-item").click(function(){
		var parent = $(this).parent();
		var id = $(parent).parent().find("input[name=group-id]").val();
		var stack_id = $(this).data("id");
		var addStackToGroupData = {
			"name" : id,
			"role" : "",
			"stack" : stack_id
		};
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(addStackToGroupData),
			url : prefix+"/admin/removeStackFromGroup",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	
	//Open new user Editor
	$("#create-new-user-btn").click(function(){
		$("#new-user-editor").fadeToggle("slow","linear");
	});
	
	//Close new user Editor
	$("#close-new-user-editor-btn").click(function(){
		$("#new-user-editor").fadeToggle("slow","linear");
	});
	
	//Add new user
	$("#add-new-user-btn").click(function(){
		
		var parent = $(this).parent().parent();
		if ($(parent).find("input[name=user-mail]").is(":valid")){
			var username = $(parent).find("input[name=user-name]").val();
			var email = $(parent).find("input[name=user-mail]").val();
			var addNewUserData = {
				"username" : username,
				"email" : email,
				"group" : "",
				"owner" : false
			};
			$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(addNewUserData),
			url : prefix+"/admin/createNewUser",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
		}else{
			alert("This is not a valid email adress!");
		}
	});
	
	//Add group to user
	$("button.add-group-to-user-btn").click(function(){
		var parent = $(this).parent().parent().parent().parent();
		var username = $(parent).find("td.username").text();
		var email = $(parent).find("td.mail").text();
		var group = $(parent).find("select[name=user-group-list]").val();
		var ownership = $(parent).find("input[name=user-group-owner]").prop("checked");
		var addGroupToUserData = {
			"username" : username,
			"email" : email,
			"group" : group,
			"owner" : ownership
		}
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(addGroupToUserData),
			url : prefix+"/admin/addAssignment",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	
	//Reset password of user
	$("button.reset-user-password-btn").click(function(){
		var parent = $(this).parent().parent().parent().parent();
		var username = $(parent).find("td.username").text();
		var resetUserPWData = {
			"username" : username,
			"email" : "",
			"group" : "",
			"owner" : false
		}
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(resetUserPWData),
			url : prefix+"/admin/resetPassword",
			success : function(data){
				alert("Password has been resetted. An email was sent to the user!");
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	
	//Delete user
	$("button.delete-user-btn").click(function(){
		var parent = $(this).parent().parent().parent().parent();
		var username = $(parent).find("td.username").text();
		var deleteUserData = {
			"username" : username,
			"email" : "",
			"group" : "",
			"owner" : false
		}
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(deleteUserData),
			url : prefix+"/admin/deleteUser",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	//Remove group from user
	$("span.group-item").click(function(){
		var groupname = $(this).data("id");
		var username = $(this).parent().parent().find("td.username").text();
		var removeGroupFromUserData = {
			"username" : username,
			"email" : "",
			"group" : groupname,
			"owner" : false
		};
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(removeGroupFromUserData),
			url : prefix+"/admin/removeAssignment",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	
	//Data Harvesting Functions
	//Add new Request
	$("button.new-harvester-request-button").click(function(){
		var maxOrder = getMaxOrder();
		var request = $("#request-template").clone(true, true);
		request.addClass("harvester-request");
		request.addClass("data-harvesting-function");
		request.attr("id","");
		var currentRow;
		console.log(request);
		if ((maxOrder % 3) === 0){
			currentRow = $("<div></div>");
			currentRow.addClass("w3-cell-row");
			currentRow.addClass("w3-margin-top");
			currentRow.addClass("harvester-workflow-row");
			$("br.last-break").before(currentRow);
		}else{
			currentRow = $("div.harvester-workflow-row").last();
		}
		maxOrder = maxOrder + 1;
		request.find("span.order").text(maxOrder);
		console.log(currentRow);
		currentRow.append(request);
	});
	
	//New Transformation Rule
	$("button.new-harvester-transformation-rule-button").click(function(){
		var maxOrder = getMaxOrder();
		var request = $("#transformation-template").clone(true, true);
		request.addClass("harvester-transformation");
		request.addClass("data-harvesting-function");
		request.attr("id","");
		var currentRow;
		console.log(request);
		if ((maxOrder % 3) === 0){
			currentRow = $("<div></div>");
			currentRow.addClass("w3-cell-row");
			currentRow.addClass("w3-margin-top");
			currentRow.addClass("harvester-workflow-row");
			$("br.last-break").before(currentRow);
		}else{
			currentRow = $("div.harvester-workflow-row").last();
		}
		maxOrder = maxOrder + 1;
		request.find("span.order").text(maxOrder);
		console.log(currentRow);
		currentRow.append(request);
	});
	
	//New Operator
	$("button.new-harvester-operator-button").click(function(){
		var maxOrder = getMaxOrder();
		var request = $("#operator-template").clone(true, true);
		request.addClass("harvester-operator");
		request.addClass("data-harvesting-function");
		request.attr("id","");
		var currentRow;
		console.log(request);
		if ((maxOrder % 3) === 0){
			currentRow = $("<div></div>");
			currentRow.addClass("w3-cell-row");
			currentRow.addClass("w3-margin-top");
			currentRow.addClass("harvester-workflow-row");
			$("br.last-break").before(currentRow);
		}else{
			currentRow = $("div.harvester-workflow-row").last();
		}
		maxOrder = maxOrder + 1;
		request.find("span.order").text(maxOrder);
		console.log(currentRow);
		currentRow.append(request);
	});
	
	//New Upload new-harvester-file-upload-button
	$("button.new-harvester-file-upload-button").click(function(){
		var maxOrder = getMaxOrder();
		var fileUpload = $("#file-upload-template").clone(true, true);
		fileUpload.addClass("harvester-file-upload");
		fileUpload.addClass("data-harvesting-function");
		fileUpload.attr("id","");
		var currentRow;
		if ((maxOrder % 3) === 0){
			currentRow = $("<div></div>");
			currentRow.addClass("w3-cell-row");
			currentRow.addClass("w3-margin-top");
			currentRow.addClass("harvester-workflow-row");
			$("br.last-break").before(currentRow);
		}else{
			currentRow = $("div.harvester-workflow-row").last();
		}
		maxOrder = maxOrder + 1;
		fileUpload.find("span.order").text(maxOrder);
		currentRow.append(fileUpload);
	});
	
	//Delete data harvesting function
	$("button.delete-button").click(function(){
		var type = $(this).parent().parent().find("input[name='operator-type']").val();
		var operator_id = $(this).parent().parent().find("input[name='operator-id']").val();
		var button = $(this);
		if (type === "request"){
			var ajaxData = {
				'request_body' : '',
				'endpoint' : '',
				'request_name' : '',
				'request_type' : '',
				'operator_id' : operator_id,
				'transformation_order_id' : 0,
				'order' : 0,
				'headers' : []
			};
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(ajaxData),
				url : "./deleteRequest",
				success : function(data){
					button.parent().parent().remove();
					reOrderAfterDeletion();
				},
				error : function(){
					alert("An error has occured! Please try again!");
				}
			});
		}else if (type === 'transformation_rule'){
			var ajaxData = {
				'operator_id' : operator_id,
				'order' : 0,
				'rule' : '',
				'target' : '',
				'rule_type' : '',
				'transformation_order_id' : 0
			};
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(ajaxData),
				url : "./deleteTransformationRule",
				success : function(data){
					button.parent().parent().remove();
					reOrderAfterDeletion();
				},
				error : function(){
					alert("An error has occured! Please try again!");
				}
			});
		}else if (type === 'operator'){
			var ajaxData = {
				'operator_id' : operator_id,
				'order' : 0,
				'transformation_order_id' : 0,
				'operator_type' : ''
			};
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(ajaxData),
				url : "./deleteOperator",
				success : function(data){
					button.parent().parent().remove();
					reOrderAfterDeletion();
				},
				error : function(){
					alert("An error has occured! Please try again!");
				}
			});
		}else if (type === 'file_upload'){
			var ajaxData = {
				'operator_id' : operator_id,
				'file_type':'',
				'file_upload_name' : '',
				'first_data_line' : 0,
				'order' : 0,
				'transformation_order_id' : 0,
				'uploadedFile' : 0				
			}
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(ajaxData),
				url : "./deleteFileUpload",
				success : function(data){
					button.parent().parent().remove();
					reOrderAfterDeletion();
				},
				error : function(){
					alert("An error has occured! Please try again!");
				}
			});
		}
	});
	
	//Create new Data Harvester
	$("button#create-new-data-harvester").click(function(){
		var harvesterName = prompt("Pleave provide a name for the data harvester");
		//console.log(harvesterName);
		var ajaxData = {
			transformation_order_id : 0,
			transformation_sequence : 0,
			operator_id : 0,
			transformation_order_name : harvesterName
		}
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(ajaxData),
			url : "./DataHarvesting/createNewOrder",
			success : function(data){
				location.reload();
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	
	//Save changes to Data Harvesting operator
	$("button.save-button").click(function(){
		save(this);
	});
	
	//Change Operator Metadata when Operator type switches
	$("select[name='operator-type']").change(function(){
		var type = $(this).val();
		var metadataContainer = $(this).parent().parent().parent().find("ul.operator-metadata");
		metadataContainer.empty();
		var metadataList = operatorMetadata[type];
		for (var i = 0; i < metadataList.length; i++){
			var listEntry = $("<li></li>");
			listEntry.text(metadataList[i]);
			metadataContainer.append(listEntry);
		} 
	});
	
	//Dry Run Data Harvester
	$("button.dry-run-button").click(function(){
		var transformation_order_id = $(this).parent().parent().parent().parent().parent().find("input[name='transformation_order_id']").val();
		var ajaxData = {
			'transformation_order_id' : transformation_order_id,
			'operator_id' : 0,
			'transformation_order_name' : '',
			'transformation_sequence': 0
		};
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(ajaxData),
			url : "./dryRun",
			statusCode : {
				200 : function(data) {
					alert(JSON.stringify(data));
				}
			}
		
		});
	});
	
	//Run Data Harvester
	$("button.execute-button").click(function(){
		var transformation_order_id = $(this).parent().parent().parent().parent().parent().find("input[name='transformation_order_id']").val();
		var ajaxData = {
			'transformation_order_id' : transformation_order_id,
			'operator_id' : 0,
			'transformation_order_name' : '',
			'transformation_sequence': 0
		};
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(ajaxData),
			url : "./execute",
			statusCode : {
				200 : function(data) {
					alert(JSON.stringify(data));
				}
			}
		
		});
	});
	
	//New Request Header
	$("button.new-header-button").click(function(){
		var tmpl = $(this).parent().parent().parent().find("tr.request-header-row-template").clone(true,true);
		var cont = $(this).parent().parent().parent();
		tmpl.attr("class","request-header-row");
		cont.append(tmpl);
	});
	
	//Update schedule for data harvester
	$("button.new-harvester-schedule-button").click(function(){
		var scheduleId = $(this).parent().parent().find("input[name=schedule-id]").val();
		var hours = $(this).parent().parent().find("select.schedule-edit-hours-input").val();
		var months = $(this).parent().parent().find("select.schedule-edit-months-input").val();
		var days = $(this).parent().parent().find("select.schedule-edit-days-input").val();
		var weeks = $(this).parent().parent().find("select.schedule-edit-weeks-input").val();
		
		var ajaxData = {
			'transformation_order_id' : scheduleId,
			'hours' : hours,
			'days' : days,
			'weeks' : weeks,
			'months' : months
		};
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(ajaxData),
			url : "./scheduleDataHarvester",
			statusCode : {
				200 : function() {
					alert("Schedule has been updated");
				}
			}
		
		});
	});
	
	//Sortable function for data harvester
	$("#sortable .sortable-list").sortable({
		connectWith: '#sortable .sortable-list',
		placeholder: 'placeholder w3-border w3-round-large w3-tiny w3-third',
		stop: function(){
			reOrderAfterDeletion();
		}
	});
	
	//Show loading animation for every ajax call
	$(document).ajaxSend(function(){
		$("#loading-gif").css("display","block");
	});
	//Hide loading animation after ajax call
	$(document).ajaxComplete(function(){
		$("#loading-gif").css("display","none");
	});
	
	
	bindCSRFToAJAXHeaders();
} 

function save(obj){
		var type = $(obj).parent().parent().find("input[name='operator-type']").val();
		var operator_id = $(obj).parent().parent().find("input[name='operator-id']").val();
		if (typeof operator_id === 'undefined'){
			operator_id = 0;
		}
		
		if (type === "request"){
			var request_body = $(obj).parent().parent().find("textarea[name='request-body']").val();
			var endpoint = $(obj).parent().parent().find("input[name='request-endpoint']").val();
			var name = $(obj).parent().parent().find("input[name='request-name']").val();
			var request_type = $(obj).parent().parent().find("select[name='request-type']").val();
			var transformation_order_id = $(obj).parent().parent().parent().parent().parent().find("input[name='transformation_order_id']").val();
			var order = $(obj).parent().parent().find("span.order").text();
			var headers = [];
			$(obj).parent().parent().find("tr.request-header-row").each(function(){
				var header_key = $(this).find("input[name='header-key']").val();
				var header_val = $(this).find("input[name='header-value']").val();
				var header_id = $(this).find("input[name='header-id']").val();
				headers.push(
					{
						'operator_id' : operator_id,
						'header_property_id' : header_id,
						'header_property_key' : header_key,
						'header_property_value' : header_val
					}
				);
			});
			var ajaxData = {
				'request_body' : request_body,
				'endpoint' : endpoint,
				'request_name' : name,
				'request_type' : request_type,
				'operator_id' : operator_id,
				'transformation_order_id' : transformation_order_id,
				'order' : order,
				'headers' : headers
			};
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(ajaxData),
				url : "./updateRequest",
				success : function(data){
					
					//location.reload();
				},
				error : function(){
					alert("An error has occured! Please try again!");
				}
			});
		}else if (type === 'transformation_rule'){
			var transformation_order_id = $(obj).parent().parent().parent().parent().parent().find("input[name='transformation_order_id']").val();
			var order = $(obj).parent().parent().find("span.order").text();
			var transformation_type = $(obj).parent().parent().find("select[name='transformation-type']").val();
			var transformation_rule = $(obj).parent().parent().find("input[name='transformation-rule']").val();
			var transformation_target = $(obj).parent().parent().find("input[name='transformation-target']").val();
			var ajaxData = {
				'operator_id' : operator_id,
				'order' : order,
				'rule' : transformation_rule,
				'target' : transformation_target,
				'rule_type' : transformation_type,
				'transformation_order_id' : transformation_order_id
			};
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(ajaxData),
				url : "./updateTransformationRule",
				success : function(data){
					location.reload();
				},
				error : function(){
					alert("An error has occured! Please try again!");
				}
			});
		}else if (type === 'operator'){
			var transformation_order_id = $(obj).parent().parent().parent().parent().parent().find("input[name='transformation_order_id']").val();
			var order = $(obj).parent().parent().find("span.order").text();
			var operator_type = $(obj).parent().parent().find("select[name='operator-type']").val();
			var ajaxData = {
				'operator_id' : operator_id,
				'order' : order,
				'transformation_order_id' : transformation_order_id,
				'operator_type' : operator_type
			};
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(ajaxData),
				url : "./updateOperator",
				success : function(data){
					location.reload();
				},
				error : function(){
					alert("An error has occured! Please try again!");
				}
			});
		}else if (type === 'file_upload'){
			var transformation_order_id = $(obj).parent().parent().parent().parent().parent().find("input[name='transformation_order_id']").val();
			var order = $(obj).parent().parent().find("span.order").text();
			var file_upload_name = $(obj).parent().parent().find("input[name='file-upload-name']").val();
			var file_type = $(obj).parent().parent().find("select[name='file_type']").val();
			var file = $(obj).parent().parent().find("input[name='uploadedFile']")[0].files[0];
			var first_data_line = $(obj).parent().parent().find("input[name='first_data_line']").val();
			var data = new FormData();
			data.append("file", file);
			data.append("properties", new Blob(
				[JSON.stringify(
					{
						"file_type" : file_type,
						"transformation_order_id" : transformation_order_id,
						"file_upload_name" : file_upload_name,
						"first_data_line" : first_data_line
					}
				)], {
					type: "application/json"
				}
			));
			
			data.append("first_data_line",first_data_line);
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : undefined
				},
				cache: false,
				contentType: false,
				processData: false,
				data : data,
				url : "./updateFileUpload",
				success : function(data){
					location.reload();
				},
				error : function(){
					alert("An error has occured! Please try again!");
				}
			});
		}
}

function getMaxOrder(){
	var maxOrder = 0;
	$(".data-harvesting-function").each(function(){
		var order = parseInt($(this).find("span.order").text());
		if ($(this).hasClass("harvester-operator") | $(this).hasClass("harvester-transformation") | $(this).hasClass("harvester-request")){
			if (order > maxOrder){
				maxOrder = order;
			}
		}
	});	
	return maxOrder;
}

function reOrderAfterDeletion(){
	var lastOrder = 0;
	$(".data-harvesting-function").each(function(){
		var order = parseInt($(this).find("span.order").text());
		if (order === (lastOrder + 1)){
			lastOrder = order;
		}else{
			$(this).find("span.order").text(lastOrder+1);
			lastOrder = lastOrder + 1;
			var operator_id = $(this).find("input[name='operator-id']").val();
			var ajaxData = {
				'operator_id' : operator_id,
				'transformation_order_id' : 0,
				'transformation_order_name' : '',
				'transformation_sequence' : lastOrder
			};
			$.ajax({
				method: "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(ajaxData),
				url : "./reorder",
				success : function(data){
					
				},
				error : function(){ 
					alert("An error has occured! Please try again!");
				}
			});
		}
		
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