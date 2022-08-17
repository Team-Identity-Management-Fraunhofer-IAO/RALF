/**
 * 
 */
function loadGraph() {
	if (swStackId != -1) {
		$.ajax({
			dataType : "json",
			url : applicationContext + "/SWStackComponents/getComponents/"
					+ swStackId,
			success : function(data) {
				ajaxNodes = [];
				parseComponents(data);
				$
						.ajax({
							dataType : "json",
							url : applicationContext
									+ "/SWStackComponents/getRequirements/"
									+ swStackId,
							success : function(data) {
								ajaxEdges = [];
								parseRelationships(data);
							}
						});
			}
		});
	}
}

function createNewSWStack() {
	$("#swStackId").val(-1);
	$("#context-menu-title").fadeOut("fast", function() {
		$("#swStackNameInput").fadeIn("fast");
		$("#swStackGroupInput").fadeIn("fast");
		$("#addGroupSubmitInput").fadeIn("fast");
		$("#newSWStackSubmitInput").fadeIn("fast");
		$("#swStackGroupList").fadeIn("fast");
;	});
}

function submitSWStackName() {
	$("#swStackGroupInput").fadeOut("fast");
	$("#addGroupSubmitInput").fadeOut("fast");
	$("#newSWStackSubmitInput").fadeOut("fast");
	$("#swStackGroupList").fadeOut("fast");
	$("#swStackNameInput").fadeOut("fast", function() {
		$("#context-menu-title").fadeIn("fast");
	});	
}

// /SWStackComponents/updateComponent/{id}/{cpeID}/{part}/{vendor}/{product}/{version}/{update}/{edition}/{language}/{sw_edition}/{target_sw}/{target_hw}/{other}/{isCodebase}
function persistNode(id, cpeID, part, vendor, product, version, update,
		edition, language, sw_edition, target_sw, target_hw, other, versionCmp, isCodebase,
		network, data, callback) {
	cpeID = 0;
	var propertyContainer = document.getElementById("node-property-container");
	appendLoadIcon(propertyContainer);

	/*
	 * if (network != null){
	 * 
	 * document.getElementById("nodeAddBtn").classList.remove("active");
	 * document.getElementById("submitBtn").onclick = function(){
	 * saveNodeDataEditor(null); }; data.id=99; /*Must be retrieved from DB
	 */
	/*
	 * data.label=document.getElementById("productInput").value+":"+document.getElementById("versionInput").value;
	 * callback(data); network.disableEditMode(); }
	 */
	if (swStackId != -1) {
		if (id != -1) {
			$
					.ajax({
						dataType : "json",
						url : applicationContext
								+ "/SWStackComponents/updateComponent/" + id
								+ "/" + cpeID + "/" + part + "/" + vendor + "/"
								+ product + "/" + version + "/" + update + "/"
								+ edition + "/" + language + "/" + sw_edition
								+ "/" + target_sw + "/" + target_hw + "/"
								+ other + "/" + versionCmp + "/" + isCodebase,
						statusCode : {
							200 : function(data) {
								showMessage("message", "Component " + product
										+ ":" + version
										+ " has been successfully updated!");
								setTimeout(function() {
									hideNodeDataEditor()
								}, 300);
								setTimeout(function() {
									removeLoadIcon(propertyContainer)
								}, 500);
								loadGraph();
							},
							404 : function(data) {
								showMessage("alert",
										"Service unreachable. Please check your connection!");
							}
						}
					});
		} else {
			$
					.ajax({
						dataType : "json",
						url : applicationContext
								+ "/SWStackComponents/addComponent/"
								+ swStackId + "/" + cpeID + "/" + part + "/"
								+ vendor + "/" + product + "/" + version + "/"
								+ update + "/" + edition + "/" + language + "/"
								+ sw_edition + "/" + target_sw + "/"
								+ target_hw + "/" + other + "/" + versionCmp + "/" + isCodebase,
						statusCode : {
							200 : function(data) {
								showMessage(
										"message",
										"Component "
												+ product
												+ ":"
												+ version
												+ " has been successfully added to the software stack!");
								setTimeout(function() {
									hideNodeDataEditor()
								}, 300);
								setTimeout(function() {
									removeLoadIcon(propertyContainer)
								}, 500);
								loadGraph();
							},
							404 : function(data) {
								showMessage("alert",
										"Service unreachable. Please check your connection!");
							}
						}
					});
		}
	}
}

function deleteComponent(id) {
	$.ajax({
		dataType : "json",
		url : applicationContext + "/SWStackComponents/deleteComponent/" + id,
		statusCode : {
			200 : function(data) {
				showMessage("message",
						"Component has been successfully deleted!");
			},
			404 : function(data) {
				showMessage("alert",
						"Service unreachable. Please check your connection!");
			}
		}
	})
}

function persistRequirement(originId, requiredId) {
	$.ajax({
		dataType : "json",
		url : applicationContext + "/SWStackComponents/addRequirement/"
				+ originId + "/" + requiredId,
		statusCode : {
			200 : function(data) {
				showMessage("message",
						"Requirement has been successfully added!");
			},
			404 : function(data) {
				showMessage("alert",
						"Service unreachable. Please check your connection!");
			}
		}
	});
}

function deleteRequirement(originId, requiredId) {
	$.ajax({
		dataType : "json",
		url : applicationContext + "/SWStackComponents/deleteRequirement/"
				+ originId + "/" + requiredId,
		statusCode : {
			200 : function(data) {
				showMessage("message",
						"Requirement has been successfully deleted!");
			},
			404 : function(data) {
				showMessage("alert",
						"Service unreachable. Please check your connection!");
			}
		}
	});
}

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
		part = sanitizeEmptyField($("#typeInput").val());
		vendor = sanitizeEmptyField($("#vendorInput").val());
		product = sanitizeEmptyField($("#productInput").val());
		version = sanitizeEmptyField($("#versionInput").val());
		update = sanitizeEmptyField($("#updateInput").val());
		edition = sanitizeEmptyField($("#editionInput").val());
		language = sanitizeEmptyField($("#languageInput").val());
		sw_edition = sanitizeEmptyField($("#sw_editionInput").val());
		target_sw = sanitizeEmptyField($("#target_swInput").val());
		target_hw = sanitizeEmptyField($("#target_hwInput").val());
		other = sanitizeEmptyField($("#otherInput").val());
		$
				.ajax({
					dataType : "json",
					url : applicationContext
							+ "/SWStackElements/getCPEsByFieldValue/" + part
							+ "/" + vendor + "/" + product + "/" + version
							+ "/" + update + "/" + edition + "/" + sw_edition
							+ "/" + language + "/" + target_sw + "/"
							+ target_hw + "/" + other,
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