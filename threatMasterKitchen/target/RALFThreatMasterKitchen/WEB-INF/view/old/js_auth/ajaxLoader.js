/**
 * ajaxLoader javascript functions for retrieving and storing components,
 * relationships and platforms
 */
var ajaxNodes = [];
var ajaxEdges = [];
var lastLoaded = 0;

function init() {
	$("#context-menu-btn-img").click(function() {
		if ($("#context-hover-menu").css("display") == "none") {
			$("#context-hover-menu").slideDown("slow");
		} else {
			$("#context-hover-menu").slideUp("slow");
		}
	});
	$("#addGroupSubmitInput").click(function() {
		var listItem = $("<span></span>");
		var groupName = $("#swStackGroupInput").val();
		listItem.attr("class", "list-item");
		listItem.data("groupname", groupName);
		listItem.html(groupName + "&nbsp;&times;");
		listItem.click(function() {
			$(this).remove();
		});
		$("#swStackGroupList").append(listItem);
	});
	$("#newSWStackSubmitInput").click(
			function() {
				$("#context-menu-title").find("span").html(
						fancifyTitle($("#swStackNameInput").val()));
				$("#swStackNameInput").attr("disabled", true);
				$("#swStackNameInput").addClass("inputLoading");
				var swStackId = $("#swStackId").val();
				var groupItems = $("#swStackGroupList").find(".list-item");
				var groups = "";
				for (var i = 0; i < groupItems.length; i++) {
					groups += $(groupItems[i]).data("groupname");
					if (i != groupItems.length - 1) {
						groups += ",";
					}
				}
				if (swStackId == -1) {
					$.ajax({
						type : "POST",
						dataType : "json",
						url : applicationContext + "/SWStack/createSWStack",
						data : {
							name : $("#swStackNameInput").val(),
							groupList : groups
						},
						success : function(data) {
							submitSWStackName();
							window.location.href = applicationContext
									+ "/SWStackModelling/" + data.id;

						}
					});
				} else {
					$.ajax({
						type : "POST",
						dataType : "json",
						url : applicationContext + "/SWStack/updateSWStack",
						data : {
							id : $("#swStackId").val(),
							name : $("#swStackNameInput").val(),
							groupList : groups
						},
						success : function(data) {
							submitSWStackName();
							window.location.href = applicationContext
									+ "/SWStackModelling/" + data;

						}
					});

				}

			});
	$("#context-menu-title").find("span").html(fancifyTitle(title));
	$("#swStackId").val(swStackId);

	if (swStackId != -1) {
		$("#swStackNameInput").attr("value", title);
	}
	$("#swStackEditBtn").click(function() {
		$("#context-menu-title").fadeOut("fast", function() {
			$("#swStackNameInput").fadeIn("fast");
			$("#swStackGroupInput").fadeIn("fast");
			$("#addGroupSubmitInput").fadeIn("fast");
			$("#newSWStackSubmitInput").fadeIn("fast");
			$("#swStackGroupList").fadeIn("fast");
		});
	});
	loadGraph();
}

function sanitizeEmptyField(field) {
	if (field === "") {
		return "*";
	}
	return field;
}

function lockInputsInPropertyContainer(lock) {
	var inputs = $("#node-property-container").find("input");
	if (lock === true) {
		inputs.attr("disabled", true);
		inputs.addClass("inputLoading");
	} else {
		inputs.attr("disabled", false);
		inputs.removeClass("inputLoading");
	}
}

function parseComponents(data) {
	$('#node-data-container').empty();
	for (var i = 0; i < data.length; i++) {
		var metadataContainer = $("<ul></ul>");
		metadataContainer.attr("id", "node-" + data[i].id);
		var liType = $("<li></li>").attr("class", "type");
		liType.html(data[i].part);
		var liProduct = $("<li></li>").attr("class", "product");
		liProduct.html(data[i].product);
		var liVendor = $("<li></li>").attr("class", "vendor");
		liVendor.html(data[i].vendor);
		var liVersion = $("<li></li>").attr("class", "version");
		liVersion.html(data[i].version);
		var liUpdate = $("<li></li>").attr("class", "update");
		liUpdate.html(data[i].update);
		var liEdition = $("<li></li>").attr("class", "edition");
		liEdition.html(data[i].edition);
		var liLanguage = $("<li></li>").attr("class", "language");
		liLanguage.html(data[i].language);
		var liSw_edition = $("<li></li>").attr("class", "sw_edition");
		liSw_edition.html(data[i].sw_edition);
		var liTarget_sw = $("<li></li>").attr("class", "target_sw");
		liTarget_sw.html(data[i].target_sw);
		var liTarget_hw = $("<li></li>").attr("class", "target_hw");
		liTarget_hw.html(data[i].target_hw);
		var liOther = $("<li></li>").attr("class", "other");
		liOther.html(data[i].other);
		var liCodebase = $("<li></li>").attr("class", "codebase");
		liCodebase.html(data[i].codebase);
		metadataContainer.append(liType, liProduct, liVendor, liVersion,
				liUpdate, liEdition, liLanguage, liSw_edition, liTarget_hw,
				liTarget_sw, liOther, liCodebase);
		$('#node-data-container').append(metadataContainer);
		ajaxNodes.push({
			id : data[i].id,
			label : data[i].product + ":" + data[i].version
		});
	}
}

function parseRelationships(data) {
	for (var i = 0; i < data.length; i++) {
		ajaxEdges.push({
			label : data[i].label,
			from : data[i].fromId,
			to : data[i].toId
		});
	}
	drawGraph(ajaxNodes, ajaxEdges);
}