/**
 * 
 * 
 */
var networks = [];
var datas = [];
var options = [];

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
								getCVEContents();

							}
						});
			}
		});
	}
}

function getCVEContents() {
	$
			.ajax({
				dataType : "json",
				url : applicationContext + "/Assessment/SWStack/getCVE/"
						+ swStackId,
				success : function(data) {
					for (var j = 0; j < data.length; j++) {
						var componentId = data[j].id;

						var nodeContainer = $("#node-" + componentId);
						var componentText = "Error";
						if (nodeContainer != null) {
							var nType = nodeContainer.find("li.type").text();
							var nProduct = nodeContainer.find("li.product")
									.text();
							var nVendor = nodeContainer.find("li.vendor")
									.text();
							var nVersion = nodeContainer.find("li.version")
									.text();

							componentText = fancifyTitle(nType + " " + nVendor
									+ " " + nProduct + ", Version: " + nVersion);
						}

						agVulnArray = data[j].aggregatedVulnerabilities;
						agVulnKeys = Object.keys(agVulnArray);

						var cont = $("#vulnerability-table-container-template")
								.clone();
						cont.appendTo($("#cve-container"));
						cont.find("p.vulnerability-table-title").html(
								componentText);

						for (var k = 0; k < agVulnKeys.length; k++) {
							var row = cont
									.find(
											"div.vulnerability-table-content-template-row")
									.clone();
							vuln = agVulnArray[agVulnKeys[k]];
							for (var l = 0; l < vuln.length; l++) {
								var year = $("<li></li>").attr("class", "year")
										.text(vuln[l].cveYear);
								var id = $("<li></li>").attr("class", "id")
										.text(vuln[l].cveID);
								var idString = $("<li></li>").attr("class",
										"identifierString").text(
										vuln[l].identifierString);
								row
										.find(
												"p.vulnerability-table-content-column.cve-identifier-string")
										.html(vuln[l].identifierString);
								var vectorString = $("<li></li>").attr("class",
										"vectorString").text(
										vuln[l].vectorString);
								row
										.find(
												"p.vulnerability-table-content-column.cve-vectorString")
										.html(vuln[l].vectorString);

								var attackVector = $("<li></li>").attr("class",
										"attackVector").text(
										vuln[l].attackVector);
								row
										.find(
												"p.vulnerability-table-content-column.cve-attackVector")
										.html(vuln[l].attackVector);

								var attackComplexity = $("<li></li>").attr(
										"class", "attackComplexity").text(
										vuln[l].attackComplexity);
								row
										.find(
												"p.vulnerability-table-content-column.cve-attackComplexity")
										.html(vuln[l].attackComplexity);

								var privilegesRequired = $("<li></li>").attr(
										"class", "privilegesRequired").text(
										vuln[l].privilegesRequired);
								row
										.find(
												"p.vulnerability-table-content-column.cve-privilegesRequired")
										.html(vuln[l].privilegesRequired);

								var userInteraction = $("<li></li>").attr(
										"class", "userInteraction").text(
										vuln[l].userInteraction);
								row
										.find(
												"p.vulnerability-table-content-column.cve-userInteraction")
										.html(vuln[l].userInteraction);

								var scope = $("<li></li>").attr("class",
										"scope").text(vuln[l].scope);
								row
										.find(
												"p.vulnerability-table-content-column.cve-scope")
										.html(vuln[l].scope);

								var confidentialityImpact = $("<li></li>")
										.attr("class", "confidentialityImpact")
										.text(vuln[l].confidentialityImpact);
								row
										.find(
												"p.vulnerability-table-content-column.cve-confidentialityImpact")
										.html(vuln[l].confidentialityImpact);

								var integrityImpact = $("<li></li>").attr(
										"class", "integrityImpact").text(
										vuln[l].integrityImpact);
								row
										.find(
												"p.vulnerability-table-content-column.cve-integrityImpact")
										.html(vuln[l].integrityImpact);

								var availabilityImpact = $("<li></li>").attr(
										"class", "availabilityImpact").text(
										vuln[l].availabilityImpact);
								row
										.find(
												"p.vulnerability-table-content-column.cve-availabilityImpact")
										.html(vuln[l].availabilityImpact);

								var baseScore = $("<li></li>").attr("class",
										"baseScore").text(vuln[l].baseScore);
								row
										.find(
												"p.vulnerability-table-content-column.cve-baseScore")
										.html(vuln[l].baseScore);

								var baseSeverity = $("<li></li>").attr("class",
										"baseSeverity").text(
										vuln[l].baseSeverity);
								row
										.find(
												"p.vulnerability-table-content-column.cve-baseSeverity")
										.html(vuln[l].baseSeverity);

								var exploitabilityScore = $("<li></li>").attr(
										"class", "exploitabilityScore").text(
										vuln[l].exploitabilityScore);
								row
										.find(
												"p.vulnerability-table-content-column.cve-exploitabilityScore")
										.html(vuln[l].exploitabilityScore);

								var impactScore = $("<li></li>").attr("class",
										"impactScore")
										.text(vuln[l].impactScore);
								row
										.find(
												"p.vulnerability-table-content-column.cve-impactScore")
										.html(vuln[l].impactScore);

								var descriptionText = $("<li></li>").attr(
										"class", "descriptionText").text(
										vuln[l].descriptionText);
								row
										.find(
												"p.vulnerability-table-content-column.cve-description")
										.html(vuln[l].descriptionText);

								var platformCombinations = vuln[l].platformCombinations;
								var alternativeCombinations = vuln[l].alternativeCombinations;

								var uri = $("<li></li>").attr("class", "uri")
										.text(vuln[l].uri);
								row
										.find(
												"p.vulnerability-table-content-column.cve-uri")
										.html(vuln[l].uri);
							}
							row.attr("class", "vulnerability-table-row");
							row
									.appendTo(cont
											.find("div.vulnerability-table-content-rows"));
							var container = $("<ul></ul>");
							container.attr("class", "component-" + componentId);
							container.append(year, id, idString, vectorString,
									attackVector, attackComplexity,
									privilegesRequired, userInteraction, scope,
									confidentialityImpact, integrityImpact,
									availabilityImpact, baseScore,
									baseSeverity, exploitabilityScore,
									impactScore, descriptionText, uri);
							$("#cve-data-container").append(container);
						}
						cont.attr("id", "component-cve-" + componentId);
						cont.data("componentId", componentId);
						cont.find(
								"div.vulnerability-table-content-template-row")
								.remove();
						cont.find("div#cve-component-visualization").attr("id","cve-component-visualization-"+componentId);
						cont.appendTo($("#cve-container"));
					}
					$('#vulnerability-table-container-template').remove();
					$('#network').css("display", "none");
					$('#cve-container').css("display", "block");
					prepareButtons();
					prepareGraphs();
					getCAPEC();
				}
			});
}

function getCAPEC(){
	var nodeIDs = $("#node-data-container").find("ul");
	for (var i = 0; i < nodeIDs.length; i++){
		var nodeID = $(nodeIDs[i]).attr("id").split("node-")[1];
		console.log(nodeID);
		var cveContainer = $("#cve-data-container").find("ul.component-"+nodeID);
		for (var j = 0; j < cveContainer.length; j++){
			var cveYear = $(cveContainer[j]).find("li.year").text();
			var cveID = $(cveContainer[j]).find("li.id").text();		
			$.ajax({
				dataType : "json",
				url : applicationContext + "/Assessment/SWStack/getAttackPatternsForCVE/"
						+ cveYear+"/"+cveID+"/"+nodeID,
				success: function(data){					
					for (var k = 0; k < data.length; k++){
						var nID = data[k].nodeID;
						var capec = data[k].capec;
						var cveID = data[k].cveID;
						if (cveID < 1000){
							cveID = "0"+cveID;
						}
						var cveYear = data[k].cveYear;
						var capecContainer = $("<ul></ul>").attr("class","component-"+nID);
						var capecID = capec.id;
						var capecName = capec.name;
						var capecDesc = capec.description;
						var idCont = $("<li></li>").attr("class","capec-id").text(capecID);
						var nameCont = $("<li></li>").attr("class","capec-name").text(capecName);
						var DescCont = $("<li></li>").attr("class","capec-description").text(capecDesc);
						var cveYearCont = $("<li></li>").attr("class","capec-cve-year").text(cveYear);
						var cveIDCont = $("<li></li>").attr("class","capec-cve-id").text(cveID);
						capecContainer.append(idCont,nameCont,DescCont,cveYearCont,cveIDCont);
						$("#capec-data-container").append(capecContainer);
						var row = $("#component-cve-"+nID).find("div.attackpattern-table").find("div.attackpattern-table-content-rows").find("div.attackpattern-table-content-row-template").clone(true);
						row.find("p.capec-id").text(capecID);
						row.find("p.capec-name").text(capecName);
						row.find("p.capec-description").text(capecDesc);
						row.find("p.capec-cveID").text("CVE-"+cveYear+"-"+cveID);
						row.attr("class","attackpattern-table-row");
						row.removeAttr("id");
						$("#component-cve-"+nID).find("div.attackpattern-table-content-rows").append(row);
					}
									
				}
			});
			
		}
		
	}
}

function prepareFunctions(){
	$("#context-menu-btn-img").click(function() {
		if ($("#context-hover-menu").css("display") == "none") {
			$("#context-hover-menu").slideDown("slow");
		} else {
			$("#context-hover-menu").slideUp("slow");
		}
	});

	$("#swStackNameInput").keyup(
			function(event) {
				if (event.which == 13) {
					$("#context-menu-title").find("span").html(
							fancifyTitle($("#swStackNameInput").val()));
					$("#swStackNameInput").attr("disabled", true);
					$("#swStackNameInput").addClass("inputLoading");
					var swStackId = $("#swStackId").val();
					if (swStackId == -1) {
						$.ajax({
							dataType : "json",
							url : applicationContext
									+ "/SWStack/createSWStack/"
									+ $("#swStackNameInput").val(),
							success : function(data) {
								$("#swStackNameInput").fadeOut(
										"fast",
										function() {
											$("#context-menu-title").fadeIn(
													"fast");
										});
								window.location.href = applicationContext
										+ "/SWStackModelling/" + data.id;
								
							}
						});
					} else {
				
						$.ajax({
							dataType : "json",
							url : applicationContext
									+ "/SWStack/updateSWStack/" + swStackId
									+ "/" + $("#swStackNameInput").val(),
							success : function(data) {
								$("#swStackNameInput").fadeOut(
										"fast",
										function() {
											$("#context-menu-title").fadeIn(
													"fast");
										});
								window.location.reload(true);
							}
						});
					}

				}
			});
	$("#context-menu-title").find("span").html(fancifyTitle(title));
	$("#swStackId").val(swStackId);
	$("#swStackNameInput").attr("value", title);
	$("#swStackEditBtn").click(function() {
		$("#context-menu-title").fadeOut("fast", function() {
			$("#swStackNameInput").fadeIn("fast");
		});
	});
	prepareButtons();
	prepareGraphs();
}

function prepareButtons() {
	$("div.vulnerability-table-row").find(
			"p.sortable.vulnerability-table-column").click(
			function(event) {
				var element = $(event.target);
				var container = element.parent().parent().parent();
				var sortableList = container.find(
						"div.vulnerability-table-content-rows").find(
						"div.vulnerability-table-row").clone(true);
				var listClass = getCVEClass(element.attr("class"));
				if (element.data("sortDirection") == "NONE") {
					sortNumerically(sortableList, container
							.find("div.vulnerability-table-content-rows"),
							listClass, "DESC");
					setAllButtonsToNeutral(container);
					element.find("span.operator").html("(&dArr;)");
					element.data("sortDirection", "DESC");
				} else if (element.data("sortDirection") == "DESC") {
					sortNumerically(sortableList, container
							.find("div.vulnerability-table-content-rows"),
							listClass, "ASC");
					setAllButtonsToNeutral(container);
					element.find("span.operator").html("(&uArr;)");
					element.data("sortDirection", "ASC");
				} else if (element.data("sortDirection") == "ASC") {
					sortNumerically(sortableList, container
							.find("div.vulnerability-table-content-rows"),
							listClass, "DESC");
					setAllButtonsToNeutral(container);
					element.find("span.operator").html("(&dArr;)");
					element.data("sortDirection", "DESC");
				}
			});
	$("div.vulnerability-table-row").find(
			"p.sortable.vulnerability-table-column").data("sortDirection",
			"NONE");

	$("div.vulnerability-table-row").find(
			"p.alphabeticallySortable.vulnerability-table-column").click(
			function(event) {
				var element = $(event.target);
				var container = element.parent().parent().parent();
				var sortableList = container.find(
						"div.vulnerability-table-content-rows").find(
						"div.vulnerability-table-row").clone(true);
				var listClass = getCVEClass(element.attr("class"));
				if (element.data("sortDirection") == "NONE") {
					sortAlphabetically(sortableList, container
							.find("div.vulnerability-table-content-rows"),
							listClass, "DESC");
					setAllButtonsToNeutral(container);
					element.find("span.operator").html("(&dArr;)");
					element.data("sortDirection", "DESC");
				} else if (element.data("sortDirection") == "DESC") {
					sortAlphabetically(sortableList, container
							.find("div.vulnerability-table-content-rows"),
							listClass, "ASC");
					setAllButtonsToNeutral(container);
					element.find("span.operator").html("(&uArr;)");
					element.data("sortDirection", "ASC");
				} else if (element.data("sortDirection") == "ASC") {
					sortAlphabetically(sortableList, container
							.find("div.vulnerability-table-content-rows"),
							listClass, "DESC");
					setAllButtonsToNeutral(container);
					element.find("span.operator").html("(&dArr;)");
					element.data("sortDirection", "DESC");
				}
			});
	$("div.vulnerability-table-row").find(
			"p.alphabeticallySortable.vulnerability-table-column").data(
			"sortDirection", "NONE");


	
	$("p.cve-component-visualization-filters-value").click(function(event){
		var container = $(event.target).parent().parent().parent();
		$(event.target).html("None").css("cursor","default");
		resetCVERows(container);
		resetAttackPattern(container);
	});
}

function setAllButtonsToNeutral(parentTable) {
	parentTable.find("span.operator").html("(&ndash;)");
	parentTable.find("p.sortable").data("sortDirection", "NONE");
	parentTable.find("p.alphabeticallySortable").data("sortDirection", "NONE");
}

function getCVEClass(classList) {
	var classArr = classList.split(" ");
	for (var i = 0; i < classArr.length; i++) {
		if (classArr[i].includes("cve")) {
			return classArr[i].trim();
		}
	}
}

function sortNumerically(elemList, parent, listClass, sortDirection) {
	parent.html("");
	elemList.sort(function(a, b) {
		if (sortDirection === "DESC") {
			return $(b).find("p." + listClass).text()
					- $(a).find("p." + listClass).text();
		} else if (sortDirection === "ASC") {
			return $(a).find("p." + listClass).text()
					- $(b).find("p." + listClass).text();
		}

	});
	parent.html(elemList);
}

function sortAlphabetically(elemList, parent, listClass, sortDirection) {
	parent.html("");
	elemList.sort(function(a, b) {
		if (sortDirection === "DESC") {
			if ($(a).find("p." + listClass).text() < $(b)
					.find("p." + listClass).text()) {
				return 1;
			} else {
				return -1;
			}
		} else if (sortDirection === "ASC") {
			if ($(a).find("p." + listClass).text() < $(b)
					.find("p." + listClass).text()) {
				return -1;
			} else {
				return 1;
			}
		}

	});
	parent.html(elemList);
}

function filterCVERows(tableContainer, parentElement, exploitability, impact, cvss){
	var rows = parentElement.find("div.vulnerability-table-row");
	rows.css("display","none");
	for (var i = 0; i < rows.length; i++){
		if (($(rows[i]).find("p.cve-exploitabilityScore").text() == exploitability)
				&&
			($(rows[i]).find("p.cve-impactScore").text() == impact)
			&&
			($(rows[i]).find("p.cve-baseScore").text() == cvss)){
			$(rows[i]).css("display","grid");
			filterAttackPatternByCVE(tableContainer, $(rows[i]).find("p.cve-identifier-string").text());
		}
	}
}

function filterAttackPatternByCVE(parentElement, CVEText){
	var patternRows = parentElement.find("div.attackpattern-table").find("div.attackpattern-table-content-rows");
	var capecRows = patternRows.find("div.attackpattern-table-row");
	capecRows.css("display","none");
	console.log(capecRows);
	for (var j = 0; j < capecRows.length; j++){
		if ($(capecRows[j]).find("p.capec-cveID").text() == CVEText){
			$(capecRows[j]).css("display","grid");
		}
	}
}

function resetCVERows(parentElement){
	parentElement.find("div.vulnerability-table-row").css("display","grid");
}

function resetAttackPattern(parentElement){
	parentElement.find("div.attackpattern-table-row").css("display","grid");
}

function prepareGraphs() {
	var tables = $.find("div.vulnerability-table-container");
	for (var i = 0; i < tables.length; i++) {
		var cId = $(tables[i]).data("component-id");
		componentCVEs = $("#cve-data-container").find("ul.component-" + cId);
		var container = document.getElementById("cve-component-visualization-"
				+ cId);
		var items = [];
		for (var j = 0; j < componentCVEs.length; j++) {
			var exploitabilityScore = $(componentCVEs[j]).find(
					"li.exploitabilityScore").text();
			var impactScore = $(componentCVEs[j]).find("li.impactScore").text();
			var cvss = $(componentCVEs[j]).find("li.baseScore").text();
			items.push({
				x : (exploitabilityScore*10)/10,
				y : (impactScore*10)/10,
				z : (cvss*10)/10,
				style: (cvss*10)/10
			});
		}
		datas[i] = new vis.DataSet(items);
		options[i] = {
			width: '400px',
			height: '400px',
			style: 'dot-size',
			showPerspective: false,
			showGrid: true,
			keepAspectRatio: true,
			legendLabel: 'CVSS',
			verticalRatio: 1.0,
			cameraPosition: {
				horizontal: 0,
				vertical: 1.8,
				distance: 1.6
			},
			dotSizeMinFraction: 0.1,
			dotSizeMaxFraction: 0.99,
			xLabel: 'Exploitability',
			yLabel: 'Impact',
			zLabel: 'CVSS',
			xMax: 9.9,
			xMin: 0.0,
			yMax: 9.9,
			yMin: 0.0,
			zMax: 9.9,
			zMin: 0.0
			
		};
		networks[i] = new vis.Graph3d(container, datas[i], options[i]);
		networks[i].on('click',function(point){
			console.log("click");
			var activeTable = $(this.containerElement).parent().parent();
			console.log(activeTable);
			var exploitability = point.x;
			var impact = point.y;
			var cvss = point.z;
			filterCVERows(activeTable, activeTable.find("div.vulnerability-table-content-rows"), exploitability, impact, cvss);
			activeTable.find("p.cve-component-visualization-filters-value").html("Exploitability: "+exploitability+", Impact: "+impact+", CVSS: "+cvss+" &times;").css("cursor","pointer");	
		})
	}
}