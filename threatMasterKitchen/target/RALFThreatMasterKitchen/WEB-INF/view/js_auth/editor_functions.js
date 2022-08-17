/*
 * 
 * CPE Suggestion Loader
 * 
 */
//Lock Editor Inputs

var abortCPESuggestionLoading = false;

function lockInputsInPropertyContainer(lock) {
	var inputs = $(".cpe-input");
	if (lock === true) {
		inputs.addClass("inputLoading");
	} else {
		inputs.removeClass("inputLoading");
	}
}

//Parse CPE Suggestion Data
function parseCPESuggestions(data) {
	var types = [];
	var products = [];
	var vendors = [];
	var updates = [];
	var editions = [];
	var languages = [];
	var versions = [];
	var sw_editions = [];
	var target_sws = [];
	var target_hws = [];
	var others = [];
	$("#types").empty();
	$("#products").empty();
	$("#vendors").empty();
	$("#updates").empty();
	$("#editions").empty();
	$("#languages").empty();
	$("#versions").empty();
	$("#sw_editions").empty();
	$("#target_sws").empty();
	$('#target_hws').empty();
	$('#others').empty();
	for (var i = 0; i < data.length; i++) {
		if (types.indexOf(data[i].part) == -1) {
			types.push(data[i].part);
			var type = $("<option></option>").attr("value", data[i].part);
			$("#types").append(type);
		}
		if (products.indexOf(data[i].product) == -1) {
			products.push(data[i].product);
			var product = $("<option></option>").attr("value", data[i].product);
			$("#products").append(product);
		}
		if (vendors.indexOf(data[i].vendor) == -1) {
			vendors.push(data[i].vendor);
			var vendor = $("<option></option>").attr("value", data[i].vendor);
			$("#vendors").append(vendor);
		}
		if (updates.indexOf(data[i].cpe_update) == -1) {
			updates.push(data[i].cpe_update);
			var update = $("<option></option>").attr("value",
					data[i].cpe_update);
			$("#updates").append(update);
		}
		if (editions.indexOf(data[i].edition) == -1) {
			editions.push(data[i].edition);
			var edition = $("<option></option>").attr("value", data[i].edition);
			$("#editions").append(edition);
		}
		if (languages.indexOf(data[i].cpe_language) == -1) {
			languages.push(data[i].cpe_language);
			var language = $("<option></option>").attr("value",
					data[i].cpe_language);
			$("#languages").append(language);
		}
		if (versions.indexOf(data[i].version) == -1) {
			versions.push(data[i].version);
			var version = $("<option></option>").attr("value", data[i].version);
			$("#versions").append(version);
		}// Ab hier
		if (sw_editions.indexOf(data[i].sw_edition) == -1) {
			sw_editions.push(data[i].sw_edition);
			var sw_edition = $("<option></option>").attr("value",
					data[i].sw_edition);
			$("#sw_editions").append(sw_edition);
		}
		if (target_sws.indexOf(data[i].target_sw) == -1) {
			target_sws.push(data[i].target_sw);
			var target_sw = $("<option></option>").attr("value",
					data[i].target_sw);
			$("#target_sws").append(target_sw);
		}
		if (target_hws.indexOf(data[i].target_hw) == -1) {
			target_hws.push(data[i].target_hw);
			var target_hw = $("<option></option>").attr("value",
					data[i].target_hw);
			$("#target_hws").append(target_hw);
		}
		if (others.indexOf(data[i].other) == -1) {
			others.push(data[i].other);
			var other = $("<option></option>").attr("value", data[i].other);
			$("#others").append(other);
		}
	}
}

//Load CPE Proposals
function loadCPEProposalsByFieldValues(inputObject) {	
	if (inputObject.value !== "") {
		lockInputsInPropertyContainer(true);
		var part = ""; // c
		var vendor = "";// c
		var product = "";// c
		var version = "";// c
		var update = "";// c
		var edition = "";// c
		var sw_edition = "";
		var language = "";// c
		var target_sw = "";
		var target_hw = "";
		var other = "";
		var codebase = "";
		part = sanitizeEmptyField($("#cpe-part-id").val());
		vendor = sanitizeEmptyField($("#cpe-vendor-id").val());
		product = sanitizeEmptyField($("#cpe-product-id").val());
		version = sanitizeEmptyField($("#cpe-version-id").val());
		update = sanitizeEmptyField($("#cpe-update-id").val());
		edition = sanitizeEmptyField($("#cpe-edition-id").val());
		language = sanitizeEmptyField($("#cpe-language-id").val());
		sw_edition = sanitizeEmptyField($("#cpe-sw-edition-id").val());
		target_sw = sanitizeEmptyField($("#cpe-target-sw-id").val());
		target_hw = sanitizeEmptyField($("#cpe-target-hw-id").val());
		other = sanitizeEmptyField($("#cpe-other-id").val());
		
		var dataJSON = {
				"part" : part,
				"vendor" : vendor,
				"product" : product,
				"version" : version,
				"update" : update,
				"edition" : edition,
				"sw_edition" : sw_edition,
				"language" : language,
				"target_sw" : target_sw,
				"target_hw" : target_hw,
				"other" : other
		};
		
		$
				.ajax({
					method : "POST",
					dataType : "json",
					headers:{
						"Content-Type": "application/json"
					},
					data:JSON.stringify(dataJSON),
					url : "./getCPEsByFieldValue",
					statusCode : {
						200 : function(data) {
							parseCPESuggestions(data);
							lockInputsInPropertyContainer(false);
						},
						404 : function(data) {
							showMessage("alert",
									"Service unreachable. Please check your connection!");
						}
					}

				});
	}
}

function sanitizeEmptyField(field) {
	if (field === "") {
		return "*";
	}
	return field;
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

/*
 * Functions for saving applications
 * 
*/
function saveEditor(){
	//Get metadata
	var metadatas = []
	var name = $("#application-name").val();
	var id = $("#application-id").val();
	var description = $("#metadata-description").text();
	var vectorNetwork = $("#metadata-vector-network-editor").val();
	var vectorAdjacent = $("#metadata-vector-adjacent-editor").val();
	var vectorLocal = $("#metadata-vector-local-editor").val();
	var vectorPhysical = $("#metadata-vector-physical-editor").val();
	var ip = $("input[name='ip-adress']").val();
	var subnet = $("input[name='subnet']").val();
	//Get Group assignments from checkbox group
	var assignments = [];
	$("input[name='roleAssignment']:checked").each(function(){
		assignments.push($(this).val());
	});
	var metadata = {
		
	};	
	//Get components and sections
	var contents = []
	$("div.content-section").each(function(){
		if ($(this).attr("id") !== "content-section-template"){
			var sectionName = $(this).find("h2.content-section-header-text").text();
			$($(this).find("div.w3-container.content-section-component")).each(function(){
				var part = $(this).find("td.cpe-part-text").text();
				var version = $(this).find("span.cpe-version-text-version").text();
				var versionCmp = $(this).find("span.cpe-version-text-cmp").text();
				var vendor = $(this).find("td.cpe-vendor-text").text();
				var product = $(this).find("td.cpe-product-text").text();
				var update = $(this).find("td.cpe-update-text").text();
				var targetSw = $(this).find("td.cpe-target-sw-text").text();
				var targetHw = $(this).find("td.cpe-target-hw-text").text();
				var edition = $(this).find("td.cpe-edition-text").text();
				var swEdition = $(this).find("td.cpe-sw-edition-text").text();
				var language = $(this).find("td.cpe-language-text").text();
				var other = $(this).find("td.cpe-other-text").text();
				var componentOwnDev = $(this).find("td.cpe-component-own-dev-text").text();
				var id = $(this).find("input.component-id").val();
				contents.push({
						"id" : id,
						"part" : part,
						"version" : version,
						"versionComparator" : versionCmp,
						"vendor" : vendor,
						"product" : product,
						"update" : update,
						"target_sw" : targetSw,
						"target_hw" : targetHw,
						"edition" : edition,
						"sw_edition" : swEdition,
						"language" : language,
						"other" : other,
						"codebase" : componentOwnDev,
						"section" : sectionName
				});
			});
		}
	});
	var applicationObject = {
		"name" : name,
		"id" : id,
		"description" : description,
		"vectorNetwork" : vectorNetwork,
		"vectorAdjacent" : vectorAdjacent,
		"vectorLocal" : vectorLocal,
		"vectorPhysical" : vectorPhysical,
		"ip" : ip,
		"subnet" : subnet,
		"assignments" : assignments,
		"components" : contents
	};
	
	$
	.ajax({
		method : "POST",
		dataType : "json",
		headers:{
			"Content-Type": "application/json"
		},
		data:JSON.stringify(applicationObject),
		url : "./save",
		statusCode : {
			200 : function(data) {
				alert("Changes have been successfully saved!");
			},
			404 : function(data) {
				alert("Server unreachable!");
			}
		}

	});
}

function deleteApplication(){
	var id = $("#application-id").val();
	var applicationObject = {
			"name" : '',
			"id" : id,
			"description" : '',
			"vectorNetwork" : '',
			"vectorAdjacent" : '',
			"vectorLocal" : '',
			"vectorPhysical" : '',
			"assignments" : [],
			"components" : []
		};
	
	$.ajax({
		method : "POST",
		headers : {
			"Content-Type" : "application/json"
		},
		data : JSON.stringify(applicationObject),
		url : "./delete",
		success : function(data) {
			window.location.assign(window.location.href+"/../../../Application_Modeling");
		},
		error : function() {
			alert("An error has occured! Please reload and try again.");
		}
	});
}