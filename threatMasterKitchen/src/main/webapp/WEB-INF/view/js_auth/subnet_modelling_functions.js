/**
 * 
 */
var cy;

const networkOrders = new Map([
	['impossible',1],['possibleRestricted',2],['possiblePrivate',3], ['possiblePublic',4]	
]);

const otherOrders = new Map([
	['impossible',1],['possibleOne',2],['possibleFew',3],['possibleNumerous',4]
]);

function removeConnectedApplication(elem){
		var toId = $(elem).parent().data('id');
		var fromId = $(elem).parent().parent().parent().data('id');
		var connectedApplicationData = {
			'fromId' : fromId,
			'toId' : toId,
			'fromSubnet' : '',
			'toSubnet' : ''
		};
		
		$.ajax({
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(connectedApplicationData),
			url : "./disconnectApplications",
			success : function(data){
				$(elem).parent().remove();
			},
			error : function(){
				alert("An error has occured! Please reload and try again.");
			}
		});
	
	
	
}

function initializeSubnetModellingFunctions(){
	bindCSRFToAJAXHeaders();	
	
	$("button.add-connected-application-button").click(function(){
		var toId = $(this).parent().parent().find("select[name='connected-application-selector']").val();
		var fromId = $(this).parent().parent().parent().parent().parent().parent().data("id");
		var connectedApplicationData = {
			'fromId' : fromId,
			'toId' : toId,
			'fromSubnet' : '',
			'toSubnet' : ''
		};
		var myself = $(this);
		$.ajax({
				method : "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				data : JSON.stringify(connectedApplicationData),
				url : "./connectApplications",
				success : function(data){
						var applicationSpan = $("<span></span>");
						applicationSpan.attr("data-id",myself.parent().parent().find("select[name='connected-application-selector']").val());
						applicationSpan.attr("class","w3-round w3-tiny group-item application-item w3-padding-small w3-margin-bottom");
						applicationSpan.html(myself.parent().parent().find("select[name='connected-application-selector'] option:selected").text()+" ("+myself.parent().parent().find("select[name='connected-application-selector'] option:selected").parent().attr('label')+")"+"<b style='cursor:pointer; float:right;' onclick='removeConnectedApplication(this);'>&times;</b>");
						myself.parent().parent().parent().parent().parent().parent().find("div.connected-applications").append(applicationSpan);
					},
				error : function(data){
						alert("An error has occured! Please reload and try again.");
					}			
		});
		
	});
	
	$("button.open-connected-application-editor").click(function(){
		var applicationSelector = $("#connected-application-selector");
		applicationSelector.css("display","block");
		$(".open-connected-application-editor").css("display","none");
		$(".close-connected-application-editor").css("display","none");
		$(this).parent().find("button.close-connected-application-editor").css("display","block");
		$(this).parent().parent().find("div.connected-application-selector-field").append(applicationSelector);
		var existingData = [];
		applicationSelector.find("option[value="+$(this).parent().parent().parent().data("id")+"]").attr("disabled",true);
		$(this).parent().parent().parent().find("span.group-item").each(function(){
			applicationSelector.find("option[value="+$(this).data("id")+"]").attr("disabled",true);
		});
		existingData.push($(this).parent().parent().parent().data("id"));
		existingData.push($(this).parent().parent().parent().find("span.group-item").data("id"));
	});
	
	$("button.close-connected-application-editor").click(function(){
		$("#connected-application-selector").css("display","none");
		$(".close-connected-application-editor").css("display","none");
		$(".open-connected-application-editor").css("display","block");
		$("#connected-application-selector").find("option").prop("disabled",false);
	});
	
	$("button.connect-same-subnet").click(function(){
		if (window.confirm("This will connect all applications that are in the same subnet. Are you sure?")){
			var subnets = [];
			$("#connected-application-selector").find("optgroup").each(function(){
				subnets.push($(this).attr("label"));
			});
			for (const fromSubnet of subnets){
				var connectedApplicationData = {
					'fromId' : 0,
					'toId' : 0,
					'fromSubnet' : fromSubnet,
					'toSubnet' : fromSubnet
				};
				$.ajax({
					method : "POST",
					headers : {
						"Content-Type" : "application/json"
					},
					data : JSON.stringify(connectedApplicationData),
					url : "./connectSameSubnet",
					success : function(data){
						location.reload();
					},
					error : function(data){
						alert("An error has occured! Please reload and try again.");
					}			
				});
			}
		}
	});
	
	
	
	/*
	var applicationSelectorTemplate = $("#connected-application-selector-template");
	$("td.connected-application-selector-container").each(function(){
		var applicationSelector = $("#connected-application-selector-template").clone(true, true);
		applicationSelector.attr("id","");
		applicationSelector.css("display","block");
		$(this).append(applicationSelector);
	});
	applicationSelectorTemplate.remove();
	*/
	
	
	
}

function initializeCytoscape(data){
	
	var jsonData = data;
	var subnets = [];
	/*
	{
		name:
		vectorNetwork:{
			min:...,
			max:...
		},
		vectorAdjacent:{
			min:...,
			max:...
		},
		vectorLocal:{
			min:...,
			max:...
		},
		vectorPhysical:{
			min:...,
			max:...
		}
	}
	*/
	var elements = [];
	for (var i = 0; i < jsonData.length; i++){
		var application = jsonData[i];
		var subnet = application.subnet;
		if (subnet == null){
			subnet = '*';
		}
		if (application.vectorNetwork == null){
			application.vectorNetwork = 'possiblePublic';
		}
		if (application.vectorAdjacent == null){
			application.vectorAdjacent = 'possibleNumerous';
		}
		if (application.vectorLocal == null){
			application.vectorLocal = 'possibleNumerous';
		}
		if (application.vectorPhysical == null){
			application.vectorPhysical = 'possibleNumerous';
		}
		var subnetExists = false;
		var subnetIndex = -1;
		for (var j = 0; j < subnets.length; j++){
			if (subnets[j].name === subnet){
				subnetExists = true;
				subnetIndex = j;
				break;
			} 
		}
		if (!subnetExists){
			subnets.push({
				name: subnet,
				vectorNetwork:{
					min: application.vectorNetwork,
					max: application.vectorNetwork
				},
				vectorAdjacent:{
					min: application.vectorAdjacent,
					max: application.vectorAdjacent
				},
				vectorLocal:{
					min: application.vectorLocal,
					max: application.vectorLocal
				},
				vectorPhysical:{
					min: application.vectorPhysical,
					max: application.vectorPhysical
				}				
			});
		}else if (subnetIndex != -1){
			if (networkOrders.get(subnets[subnetIndex].vectorNetwork.max) < networkOrders.get(application.vectorNetwork)){
				subnets[subnetIndex].vectorNetwork.max = application.vectorNetwork;
			}
			if (networkOrders.get(subnets[subnetIndex].vectorNetwork.min) > networkOrders.get(application.vectorNetwork)){
				subnets[subnetIndex].vectorNetwork.min = application.vectorNetwork;
			}
			
			if (otherOrders.get(subnets[subnetIndex].vectorAdjacent.max) < otherOrders.get(application.vectorAdjacent)){
				subnets[subnetIndex].vectorAdjacent.max = application.vectorAdjacent;
			}
			if (otherOrders.get(subnets[subnetIndex].vectorAdjacent.min) > otherOrders.get(application.vectorAdjacent)){
				subnets[subnetIndex].vectorAdjacent.min = application.vectorAdjacent;
			}
			
			if (otherOrders.get(subnets[subnetIndex].vectorLocal.max) < otherOrders.get(application.vectorLocal)){
				subnets[subnetIndex].vectorLocal.max = application.vectorLocal;
			}
			if (otherOrders.get(subnets[subnetIndex].vectorLocal.min) > otherOrders.get(application.vectorLocal)){
				subnets[subnetIndex].vectorLocal.min = application.vectorLocal;
			}
			
			if (otherOrders.get(subnets[subnetIndex].vectorPhysical.max) < otherOrders.get(application.vectorPhysical)){
				subnets[subnetIndex].vectorPhysical.max = application.vectorPhysical;
			}
			if (otherOrders.get(subnets[subnetIndex].vectorPhysical.min) > otherOrders.get(application.vectorPhysical)){
				subnets[subnetIndex].vectorPhysical.min = application.vectorLocal;
			}
		}
		elements.push({
			data:{
				id: application.id,
				ip: application.ip,
				name: application.name,
				parent: application.subnet
			},
			classes: 'application'
		});
	}
	
	cy = cytoscape({
		container: document.getElementById('subnet-graph'),
		elements: elements,
		style: [
			{
				selector: 'node',
				style:{
					shape: 'rectangle'
					
				}
			},
			{
				selector: '.application',
				style:{
					'background-color': '#fff',
					'border-width': 1,
					'border-style': 'solid',
					'text-halign': 'center',
					'text-valign': 'bottom',
					label: 'data(name)'
				}
			},
			{
				selector: '.subnet',
				style:{
					'background-color': '#fff',
					'border-width': 2,
					'border-style': 'dotted',
					label: 'data(id)'
				}
			}
			
		]
	});
	
	console.log(elements);
	
	cy.nodes('.application').style("display","none");
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