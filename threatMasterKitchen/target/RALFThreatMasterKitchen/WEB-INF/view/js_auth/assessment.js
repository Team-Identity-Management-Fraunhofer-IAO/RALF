function initializeAssessment() {
	$("button.open-resource-button").click(function(){
		if ($(this).text() === "Show Resources"){
			$(this).text("Hide Resources");
			$(this).parent().parent().next().attr("style","display:table-row");
		}else if ($(this).text() === "Hide Resources"){
			$(this).text("Show Resources");
			$(this).parent().parent().next().attr("style","display:none");
		}
		
	});
	$("button.set-selected-irrelevant-button").click(function(){
		var table = $(this).parent().parent().find("table.assessment-overview-table");
		var identifiers = [];
		console.log($("#application-id"));
		var appID = $("#application-id").val();
		var componentID = $(this).parent().parent().find("input[name=component-id]").val();
		var reportID = $("#report-id").val();
		table.find("tr.cve-row").each(function(){
			if ($(this).find("input[name=select-cve]").prop("checked")){
				identifiers.push($(this).find("td.cve-id").text());
			}
		});
		var dataJSON = {
				'appID':appID,
				'componentID':componentID,
				'modification':'IRRELEVANT',
				'reportID':reportID,
				'identifiers':identifiers
		};
		
		$.ajax({
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(dataJSON),
			url : "../alterReport",
			success : function(data) {
				location.reload();
			},
			error : function() {
				alert("An error has occured! Please reload and try again.");
			}
		});
	});
	$("button.set-selected-treated-button").click(function(){
		var table = $(this).parent().parent().find("table.assessment-overview-table");
		var identifiers = [];
		console.log($("#application-id"));
		var appID = $("#application-id").val();
		var componentID = $(this).parent().parent().find("input[name=component-id]").val();
		var reportID = $("#report-id").val();
		table.find("tr.cve-row").each(function(){
			if ($(this).find("input[name=select-cve]").prop("checked")){
				identifiers.push($(this).find("td.cve-id").text());
			}
		});
		var dataJSON = {
				'appID':appID,
				'componentID':componentID,
				'modification':'TREATED',
				'reportID':reportID,
				'identifiers':identifiers
		};
		
		$.ajax({
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(dataJSON),
			url : "../alterReport",
			success : function(data) {
				for (var i = 0; i < identifiers.length; i++){
					$("tr[data-id-string="+identifiers[i]+"]").addClass("cve-treated");
					$("tr[data-id-string="+identifiers[i]+"]").addClass("cve-managed");
				}
				
			},
			error : function() {
				alert("An error has occured! Please reload and try again.");
			}
		});
	});
	
	$("canvas.assessment-viz")
			.each(
					function() {
						var ctx = $(this)[0].getContext("2d");
						var par = $(this).parent().parent().parent().parent();
						var tabNetwork = par.find("table.cve-table-network");
						var dataNetwork = [];
						$(tabNetwork.find("tr.cve-row")).each(function() {
							dataNetwork.push({
								x : $(this).find("td.impact").text(),
								y : $(this).find("td.exploitability").text(),
								r : $(this).find("td.cvss").text()
							});
						});
						var tabAdjacent = par.find("table.cve-table-adjacent");
						var dataAdjacent = [];
						$(tabAdjacent.find("tr.cve-row")).each(function() {
							dataAdjacent.push({
								x : $(this).find("td.impact").text(),
								y : $(this).find("td.exploitability").text(),
								r : $(this).find("td.cvss").text()
							});
						});
						var tabLocal = par.find("table.cve-table-local");
						var dataLocal = [];
						$(tabLocal.find("tr.cve-row")).each(function() {
							dataLocal.push({
								x : $(this).find("td.impact").text(),
								y : $(this).find("td.exploitability").text(),
								r : $(this).find("td.cvss").text()
							});
						});
						var tabPhysical = par.find("table.cve-table-physical");
						var dataPhysical = [];
						$(tabPhysical.find("tr.cve-row")).each(function() {
							dataPhysical.push({
								x : $(this).find("td.impact").text(),
								y : $(this).find("td.exploitability").text(),
								r : $(this).find("td.cvss").text()
							});
						});
						var chart = new Chart(
								ctx,
								{
									type : 'bubble',
									data : {
										datasets : [
												{
													label : 'Network Vector',
													data : dataNetwork,
													borderWidth : 1,
													backgroundColor : 'rgba(23,156,125,0.6)'
												},
												{
													label : 'Adjacent Network Vector',
													data : dataAdjacent,
													borderWidth : 1,
													backgroundColor : 'rgba(31,130,192,0.6)'
												},
												{
													label : 'Local Vector',
													data : dataLocal,
													borderWidth : 1,
													backgroundColor : 'rgba(168,175,175,0.6)'
												},
												{
													label : 'Physical Vector',
													data : dataPhysical,
													borderWidth : 1,
													backgroundColor : 'rgba(242,148,0,0.6)'
												} ]
									},
									options : {
										scales : {
											yAxes : [ {
												ticks : {
													max : 10,
													min : 0,
													stepSize : 0.5
												},
												scaleLabel : {
													display : true,
													labelString : 'Exploitability'
												}
											} ],
											xAxes : [ {
												ticks : {
													max : 10,
													min : 0,
													stepSize : 0.5
												},
												scaleLabel : {
													display : true,
													labelString : 'Impact'
												}
											} ],

										},
										tooltips : {
											callbacks : {
												label : function(tooltipItem,
														data) {
													if (data.datasets[tooltipItem.datasetIndex].label === 'Network Vector') {
														var index = tooltipItem.index;
														var row = $(
																$(tabNetwork)
																		.find(
																				"tr.cve-row"))
																.eq(index);
														var label = $(row)
																.find(".cve-id")
																.text()
																+ " CVSS:"
																+ $(row)
																		.find(
																				".cvss")
																		.text()
																+ " Exploitability:"
																+ $(row)
																		.find(
																				".exploitability")
																		.text()
																+ " impact:"
																+ $(row)
																		.find(
																				".impact")
																		.text();
														return label;
													} else if (data.datasets[tooltipItem.datasetIndex].label === 'Adjacent Network Vector') {
														var index = tooltipItem.index;
														var row = $(
																$(tabAdjacent)
																		.find(
																				"tr.cve-row"))
																.eq(index);
														var label = $(row)
																.find(".cve-id")
																.text()
																+ " CVSS:"
																+ $(row)
																		.find(
																				".cvss")
																		.text()
																+ " Exploitability:"
																+ $(row)
																		.find(
																				".exploitability")
																		.text()
																+ " impact:"
																+ $(row)
																		.find(
																				".impact")
																		.text();
														return label;
													} else if (data.datasets[tooltipItem.datasetIndex].label === 'Local Vector') {
														var index = tooltipItem.index;
														var row = $(
																$(tabLocal)
																		.find(
																				"tr.cve-row"))
																.eq(index);
														var label = $(row)
																.find(".cve-id")
																.text()
																+ " CVSS:"
																+ $(row)
																		.find(
																				".cvss")
																		.text()
																+ " Exploitability:"
																+ $(row)
																		.find(
																				".exploitability")
																		.text()
																+ " impact:"
																+ $(row)
																		.find(
																				".impact")
																		.text();
														return label;
													} else if (data.datasets[tooltipItem.datasetIndex].label === 'Physical Vector') {
														var index = tooltipItem.index;
														var row = $(
																$(tabPhysical)
																		.find(
																				"tr.cve-row"))
																.eq(index);
														var label = $(row)
																.find(".cve-id")
																.text()
																+ " CVSS:"
																+ $(row)
																		.find(
																				".cvss")
																		.text()
																+ " Exploitability:"
																+ $(row)
																		.find(
																				".exploitability")
																		.text()
																+ " impact:"
																+ $(row)
																		.find(
																				".impact")
																		.text();
														return label;
													}

												}
											}
										}
									}
								});
					});
	$(".expand-button").click(function() {
		if ($(this).text() === "\u25b2") {
			$(this).text("\u25bc");
		} else {
			$(this).html("\u25b2");
		}

		var p = $(this).parent().parent().parent();
		$(p.find("div.component-content")).slideToggle("slow");
	});
	$(".expand-attacks-button").click(function() {
		if ($(this).text() === "\u25b2") {
			$(this).text("\u25bc");
		} else {
			$(this).html("\u25b2");
		}

		var p = $(this).parent().parent().parent();
		$(p.find("div.cve-capec-container")).slideToggle("slow");
	});
	
	$("button.add-to-vulnerability-management-button").click(function(){
		var identifier = $(this).parent().parent().find("td.cve-id").text();
		var dataJSON = {
				'identifier' : identifier
		};
		$.ajax({
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(dataJSON),
			url : "../treatVulnerability",
			success : function(data) {
				alert("Vulnerability has been added to Vulnerability Management!");
				$("tr[data-id-string="+identifier+"]").addClass("cve-managed");
			},
			error : function() {
				alert("An error has occured! Please reload and try again.");
			}
		});
	});	
	
	$("input[name=select-all]").click(function(){
		var check = false;
		if ($(this).prop("checked")){
			check = true;
		}
		$(this).parent().parent().parent().find("input[name=select-cve]").prop("checked",check);
	});
	$("input[name=show-managed-vulnerabilities]").change(function(){
		if ($(this).prop("checked")){
			$(this).parent().parent().parent().find(".cve-managed").attr("style","display:table-row");
			console.log($(this).parent().parent());
		}else{
			$(this).parent().parent().parent().find(".cve-managed").attr("style","display:none");
		}
	});
	
	bindCSRFToAJAXHeaders();
	loadResourcesForCVEs();
}

function invokeActionOnNonExplicitCPE(object, appID, cpeID, reportID, action) {
	if (action === "add") {
		var parent = $(object).parent();
		var button = $(object);
		$(parent).find("div.non-explicit-cpe-selection-container")
				.fadeToggle("slow", function() {
					$(button).prop("disabled", true);
				});
	} else {
		sendActionOnNonExplicitCPE(object, appID, cpeID, reportID, action);
	}
}

function invokeAddAction(object, appID, cpeID, reportID) {
	var section = $(object).parent().find(
			"select.non-explicit-cpe-section-selector").val();
	sendActionOnNonExplicitCPE(object, appID, cpeID, reportID, "add", section);
}

function sendActionOnNonExplicitCPE(object, appID, cpeID, reportID, action,
		section) {
	var dataJSON = {
		'action' : action,
		'appID' : appID,
		'cpeId' : cpeID,
		'reportID' : reportID,
		'section' : section	
	}
	$.ajax({
		method : "POST",
		headers : {
			"Content-Type" : "application/json"
		},
		data : JSON.stringify(dataJSON),
		url : prefix+"../removeNonExplicitListing",
		success : function(data) {
			if (action === "add") {
				$(object).parent().parent().parent().parent().fadeToggle("slow","linear");
			} else {
				$(object).parent().parent().fadeToggle("slow", "linear");
			}
		},
		error : function() {
			alert("An error has occured! Please reload and try again.");
		}
	});
}

function updateAssessmentReportsMetadata(){
	$("div.assessment-container").each(function(){
		var id = $(this).find("input.assessment-application-id").val();
		var dataRequest = {
				"swStackID" : id,
				"status" : ""
		};
		var object = $(this);
		$.ajax({
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(dataRequest),
			url : "./Assessment_Reports/currentAssessmentMetadata",
			success : function(data) {				
				if (data["status"] === ""){
					$(object).find("span.processing").addClass("w3-grayscale");
					$(object).find("span.processing").addClass("w3-opacity");
					$(object).find("span.building_report").addClass("w3-grayscale");
					$(object).find("span.building_report").addClass("w3-opacity");
					$(object).find("span.cleaning_up").addClass("w3-grayscale");
					$(object).find("span.cleaning_up").addClass("w3-opacity");
				}else if (data["status"] === "processing"){
					$(object).find("span.processing").removeClass("w3-grayscale");
					$(object).find("span.processing").removeClass("w3-opacity");
					$(object).find("span.building_report").addClass("w3-grayscale");
					$(object).find("span.building_report").addClass("w3-opacity");
					$(object).find("span.cleaning_up").addClass("w3-grayscale");
					$(object).find("span.cleaning_up").addClass("w3-opacity");
				}else if (data["status"] === "building_report"){
					$(object).find("span.processing").addClass("w3-grayscale");
					$(object).find("span.processing").addClass("w3-opacity");
					$(object).find("span.building_report").removeClass("w3-grayscale");
					$(object).find("span.building_report").removeClass("w3-opacity");
					$(object).find("span.cleaning_up").addClass("w3-grayscale");
					$(object).find("span.cleaning_up").addClass("w3-opacity");
				}else if (data["status"] === "cleaning_up"){
					$(object).find("span.processing").addClass("w3-grayscale");
					$(object).find("span.processing").addClass("w3-opacity");
					$(object).find("span.building_report").addClass("w3-grayscale");
					$(object).find("span.building_report").addClass("w3-opacity");
					$(object).find("span.cleaning_up").removeClass("w3-grayscale");
					$(object).find("span.cleaning_up").removeClass("w3-opacity");
				} 
			},
			error : function() {
			}
		})
	});
}

function loadResourcesForCVEs(){
	$("tr.cve-row").each(function(){
		$(this).find("td.references").find("button.open-resource-button").text("Loading Resources");
		var object = $(this);
		var resourceLoader = setInterval(function(){loadingTimer(object);}, 500);
		var dataRequest = {
				'identifier': $(this).data("id-string")
		};
		var button = $(this).find("td.references").find("button.open-resource-button");
		$.ajax({
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(dataRequest),
			url : "../getResources",
			success : function(data) {				
				if (data.length === 0){
					clearInterval(resourceLoader);
					button.text("No Resources");
				}else{		
					var exploitExists = false;
					var patchExists = false;
					for (var i = 0; i < data.length; i++){
						var resource = data[i];
						var typeCell = $("<td></td>");
						var URLCell = $("<td></td>");
						var dateCell = $("<td></td>");
						var resourceRow = $("<tr></tr>");
						var tableId = "#resources-table-CVE-"+resource.reference.cveYear+"-"+resource.reference.cveID;
						var resourcesHeader = $(tableId).find("tr.cve-resources-header");
						for (var j = 0; j < resource.referenceTags.length; j++){
							var tagSpan = $("<span class='w3-round w3-padding-small'></span>");
							if (resource.referenceTags[j].tag === "Exploit"){
								tagSpan.addClass("w3-red");
								exploitExists = true;
							}else{
								if (resource.referenceTags[j].tag === "Patch"){
									patchExists = true;
								}
								tagSpan.addClass("w3-teal");
							}
							tagSpan.text(resource.referenceTags[j].tag);
							typeCell.append(tagSpan);
						}
						var URLLink = $("<a></a>");
						URLLink.attr("href",resource.reference.url);
						URLLink.text(data[i].reference.url);
						URLCell.append(URLLink);
						dateCell.text(data[i].reference.modifiedDate);
						resourceRow.append(typeCell);
						resourceRow.append(URLCell);
						resourceRow.append(dateCell);
						$(resourcesHeader).after(resourceRow);
					}
					clearInterval(resourceLoader);
					button.text("Show Resources");
					button.prop("disabled",false);
					if (exploitExists){
						var exploitTag = $("<p class='w3-red w3-round w3-padding-small w3-tiny'><b>Imminent Threat</b></p>");
						exploitTag.attr("title","There is an existing exploit for this vulnerability that could be used by attackers");
						button.before(exploitTag);
					}
					if (patchExists){
						var patchTag = $("<p class='w3-teal w3-round w3-padding-small w3-tiny'><b>Patch available</b></p>");
						patchTag.attr("title","A patch for this vulnerability is available");
						button.before(patchTag);
					}
				}
			},
			error : function() {
			}
		});
		$(this).find("td.references").find("button.open-resource-button").prop("disabled",true);
	});
}

function loadingTimer(object){
	if ($(object).find("td.references").find("button.open-resource-button").text()==="Loading Resources"){
		$(object).find("td.references").find("button.open-resource-button").text("Loading Resources.");
	}else if ($(object).find("td.references").find("button.open-resource-button").text()==="Loading Resources."){
		$(object).find("td.references").find("button.open-resource-button").text("Loading Resources..");
	}else if ($(object).find("td.references").find("button.open-resource-button").text()==="Loading Resources.."){
		$(object).find("td.references").find("button.open-resource-button").text("Loading Resources...");
	}else if ($(object).find("td.references").find("button.open-resource-button").text()==="Loading Resources..."){
		$(object).find("td.references").find("button.open-resource-button").text("Loading Resources....");
	}else if ($(object).find("td.references").find("button.open-resource-button").text()==="Loading Resources...."){
		$(object).find("td.references").find("button.open-resource-button").text("Loading Resources");
	}
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