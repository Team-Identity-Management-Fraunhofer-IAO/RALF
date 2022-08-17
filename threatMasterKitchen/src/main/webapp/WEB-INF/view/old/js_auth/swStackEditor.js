var selectedNodeId = -1;
var selectedFromId = -1;
var selectedToId = -1;
function drawGraph(nodes, edges) {
	var data = {
		nodes : nodes,
		edges : edges
	};
	var container = document.getElementById('network');
	var options = {
		edges : {
			smooth: {
			      type: "continuous",
			      forceDirection: "none"
			    },
			arrows : {
				to : {
					enabled : true,
					type : "arrow",
					scaleFactor : 2
				}
			}
		},
		physics : {
			barnesHut : {
				springLength : 300,
				springConstant : 0.04
			},
			forceAtlas2Based : {
				gravitationalConstant : 50,
				centralGravity : 0.005,
				springLength : 300,
				springConstant : 0.18,
				avoidOverlap : 1.5
			},
			maxVelocity : 146,
			solver : 'forceAtlas2Based',
			timestep : 0.35,
			stabilization : {
				enabled : true,
				iterations : 1000,
				updateInterval : 25
			}
		},
		manipulation : {
			enabled : false,
			initiallyActive : false,
			addNode : function(data, callback) {
				showNodeEditor(network, data, callback);
				// showMessage("alert","This function has not yet been
				// implemented!");
				// network.disableEditMode();

				// editNode(data, clearNodePopUp, callback);
			},
			editNode : function(data, callback) {
				network.disableEditMode();
				document.getElementById("nodeEditBtn").classList
						.remove("active");
				// filling in the popup DOM elements
				// document.getElementById('node-operation').innerHTML = "Edit
				// Node";
				// editNode(data, cancelNodeEdit, callback);
			},
			addEdge : function(data, callback) {
				if (data.from == data.to) {
					var r = confirm("Do you want to connect the node to itself?");
					if (r != true) {
						callback(null);
						return;
					}
				}
				saveEdgeData(data, callback, "requires");
				document.getElementById("edgeAddBtn").classList
						.remove("active");
			},
			editEdge : {
				editWithoutDrag : function(data, callback) {
					// document.getElementById('edge-operation').innerHTML =
					// "Edit Edge";
					editEdgeWithoutDrag(data, callback);
				}
			}
		}
	};
	network = new vis.Network(container, data, options);
	network.on("stabilizationIterationsDone", function () {
	    network.setOptions( { physics: false } );
	});
	network.on('click', function(properties) {
		var node = properties.nodes;

		var edge = properties.edges;
		if (node.length > 0) {
			selectedNodeId = node;
			selectedFromId = -1;
			selectedToId = -1;
			showNodeData(node);
			document.getElementById("nodeDeleteBtn").classList
					.remove("disabled");
			document.getElementById("nodeDeleteBtn").onclick = function() {
				this.classList.add("active");
				network.deleteSelected();
				deleteComponent(node);
				this.classList.remove("active");
			};
		} else {
			selectedNodeId = -1;
			selectedFromId = -1;
			selectedToId = -1;
			document.getElementById("node-property-container").classList
					.remove("enabled");
			document.getElementById("nodeDeleteBtn").classList.add("disabled");
			document.getElementById("nodeDeleteBtn").onclick = function() {
				this.classList.add("active");
				network.deleteSelected();
				deleteComponent(-1);
				this.classList.remove("active");
			};
		}

		if (edge.length > 0 && !(node.length > 0)) {
			selectedNodeId = -1;
			var connectedEdge = network.getConnectedNodes(edge);
			selectedFromId = connectedEdge[0];
			selectedToId = connectedEdge[1];
			document.getElementById("edgeDeleteBtn").classList
					.remove("disabled");
		} else {
			selectedNodeId = -1;
			selectedFromId = -1;
			selectedToId = -1;
			document.getElementById("edgeDeleteBtn").classList.add("disabled");
		}
	});

	document.getElementById("nodeAddBtn").onclick = function() {
		this.classList.add("active");
		network.addNodeMode();
	};

	document.getElementById("edgeAddBtn").onclick = function() {
		this.classList.add("active");
		network.addEdgeMode();

	}

	document.getElementById("edgeDeleteBtn").onclick = function() {
		this.classList.add("active");
		network.deleteSelected();
		this.classList.remove("active");
		this.classList.add("disabled");
		deleteRequirement(selectedFromId, selectedToId);		
		showMessage("message", "Relationship has been successfully deleted");
	}

	document.getElementById("nodePropertyEditBtn").onclick = function() {
		editNodeData();
	}

	document.getElementById("submitBtn").onclick = function() {
		saveNodeDataEditor(null, null, null);
	}

	document.getElementById("cancelBtn").onclick = function() {
		hideNodeDataEditor(network);
	}

}

function showNodeData(nodeId) {
	var nodeDataList = document.getElementById("node-" + nodeId);
	if (nodeDataList !== null) {
		document.getElementById("node-property-container").classList
				.add("enabled");
		document.getElementById("typePara").innerHTML = nodeDataList
				.getElementsByClassName("type")[0].innerHTML;
		document.getElementById("productPara").innerHTML = nodeDataList
				.getElementsByClassName("product")[0].innerHTML;
		document.getElementById("vendorPara").innerHTML = nodeDataList
				.getElementsByClassName("vendor")[0].innerHTML;
		document.getElementById("versionPara").innerHTML = nodeDataList
				.getElementsByClassName("version")[0].innerHTML;
		document.getElementById("updatePara").innerHTML = nodeDataList
				.getElementsByClassName("update")[0].innerHTML;
		document.getElementById("editionPara").innerHTML = nodeDataList
				.getElementsByClassName("edition")[0].innerHTML;
		document.getElementById("languagePara").innerHTML = nodeDataList
				.getElementsByClassName("language")[0].innerHTML;
		document.getElementById("sw_editionPara").innerHTML = nodeDataList
				.getElementsByClassName("sw_edition")[0].innerHTML;
		document.getElementById("target_hwPara").innerHTML = nodeDataList
				.getElementsByClassName("target_hw")[0].innerHTML;
		document.getElementById("target_swPara").innerHTML = nodeDataList
				.getElementsByClassName("target_sw")[0].innerHTML;
		document.getElementById("otherPara").innerHTML = nodeDataList
				.getElementsByClassName("other")[0].innerHTML;
		document.getElementById("nodeId").value = nodeId;
		if (nodeDataList.getElementsByClassName("codebase")[0].innerHTML == "true") {
			document.getElementById("codeBaseInput").checked = true;
		} else {
			document.getElementById("codeBaseInput").checked = false;
		}
	} else {
		document.getElementById("node-property-container").classList
				.remove("enabled");
		showMessage(
				"alert",
				"There is no data available for this component! This is unusual and could be due to a mismatch between the RALF client and server. Consider restarting the client.");
	}
}

function showNodeEditor(network, data, callback) {
	document.getElementById("node-property-container").classList.add("enabled");
	document.getElementById("nodeId").value = "-1";
	document.getElementById("submitBtn").onclick = function() {
		saveNodeDataEditor(network, data, callback);
	};
	document.getElementById("typeInput").value = "";
	document.getElementById("productInput").value = "";
	document.getElementById("vendorInput").value = "";
	document.getElementById("versionInput").value = "";
	document.getElementById("updateInput").value = "";
	document.getElementById("editionInput").value = "";
	document.getElementById("languageInput").value = "";
	document.getElementById("sw_editionInput").value = "";
	document.getElementById("target_hwInput").value = "";
	document.getElementById("target_swInput").value = "";
	document.getElementById("otherInput").value = "";
	
	document.getElementById("versionCmpInput").value="=";

	document.getElementById("typePara").innerHTML = "";
	document.getElementById("productPara").innerHTML = "";
	document.getElementById("vendorPara").innerHTML = "";
	document.getElementById("versionPara").innerHTML = "";
	document.getElementById("updatePara").innerHTML = "";
	document.getElementById("editionPara").innerHTML = "";
	document.getElementById("languagePara").innerHTML = "";
	document.getElementById("sw_editionPara").innerHTML = "";
	document.getElementById("target_hwPara").innerHTML = "";
	document.getElementById("target_swPara").innerHTML = "";
	document.getElementById("otherPara").innerHTML = "";
	
	document.getElementById("versionCmpPara").innerHTML = "=";
	
	editNodeData();
}

function appendLoadIcon(elem) {
	var loadDiv = document.createElement("div");
	loadDiv.classList.add("load-container");
	var loadIcon = document.createElement("img");
	loadIcon.classList.add("loadIcon");
	loadIcon.setAttribute("src", applicationContext+"/img_auth/loader.gif");
	loadDiv.appendChild(loadIcon);
	elem.appendChild(loadDiv);
}

function removeLoadIcon(elem) {
	var loadDivs = elem.getElementsByClassName("load-container");
	for (var i = 0; i < loadDivs.length; i++) {
		elem.removeChild(loadDivs[i]);
	}
}

function editNodeData() {
	document.getElementById("typeInput").value = document
			.getElementById("typePara").innerHTML;
	document.getElementById("productInput").value = document
			.getElementById("productPara").innerHTML;
	document.getElementById("vendorInput").value = document
			.getElementById("vendorPara").innerHTML;
	document.getElementById("versionInput").value = document
			.getElementById("versionPara").innerHTML;
	document.getElementById("updateInput").value = document
			.getElementById("updatePara").innerHTML;
	document.getElementById("editionInput").value = document
			.getElementById("editionPara").innerHTML;
	document.getElementById("languageInput").value = document
			.getElementById("languagePara").innerHTML;
	document.getElementById("sw_editionInput").value = document
			.getElementById("sw_editionPara").innerHTML;
	document.getElementById("target_hwInput").value = document
			.getElementById("target_hwPara").innerHTML;
	document.getElementById("target_swInput").value = document
			.getElementById("target_swPara").innerHTML;
	document.getElementById("otherInput").value = document
			.getElementById("otherPara").innerHTML;
	
	if (document.getElementById("versionCmpPara").innerHTML === "="){
		document.getElementById("versionCmpInput").value = "eq";
	}else if (document.getElementById("versionCmpPara").innerHTML === "&lt;"){
		document.getElementById("versionCmpInput").value = "lt";
	}else if (document.getElementById("versionCmpPara").innerHTML === "&lt;="){
		document.getElementById("versionCmpInput").value = "lteq";
	}else if (document.getElementById("versionCmpPara").innerHTML === "&gt;"){
		document.getElementById("versionCmpInput").value = "gt";
	}else if (document.getElementById("versionCmpPara").innerHTML === "&gt;="){
		document.getElementById("versionCmpInput").value = "gteq";
	}
	
	document.getElementById("codeBaseInput").disabled = false;
	document.getElementById("typePara").classList.remove("enabled");
	document.getElementById("productPara").classList.remove("enabled");
	document.getElementById("vendorPara").classList.remove("enabled");
	document.getElementById("versionPara").classList.remove("enabled");
	document.getElementById("updatePara").classList.remove("enabled");
	document.getElementById("editionPara").classList.remove("enabled");
	document.getElementById("languagePara").classList.remove("enabled");
	document.getElementById("sw_editionPara").classList.remove("enabled");
	document.getElementById("target_hwPara").classList.remove("enabled");
	document.getElementById("target_swPara").classList.remove("enabled");
	document.getElementById("otherPara").classList.remove("enabled");
	
	document.getElementById("versionCmpPara").classList.remove("enabled");

	document.getElementById("typeInput").classList.add("enabled");
	document.getElementById("productInput").classList.add("enabled");
	document.getElementById("vendorInput").classList.add("enabled");
	document.getElementById("versionInput").classList.add("enabled");
	document.getElementById("updateInput").classList.add("enabled");
	document.getElementById("editionInput").classList.add("enabled");
	document.getElementById("languageInput").classList.add("enabled");
	document.getElementById("sw_editionInput").classList.add("enabled");
	document.getElementById("target_hwInput").classList.add("enabled");
	document.getElementById("target_swInput").classList.add("enabled");
	document.getElementById("otherInput").classList.add("enabled");
	document.getElementById("versionCmpInput").classList.add("enabled");
	document.getElementById("cancelBtn").classList.add("enabledInline");
	document.getElementById("submitBtn").classList.add("enabledInline");
	document.getElementById("nodePropertyEditBtn").classList
			.remove("enabledInline");
}

function saveNodeDataEditor(network, data, callback) {
	var propertyContainer = document.getElementById("node-property-container");
	var id = document.getElementById("nodeId").value;
	var part = document.getElementById("typeInput").value;
	var product = document.getElementById("productInput").value;
	var vendor = document.getElementById("vendorInput").value;
	var version = document.getElementById("versionInput").value;
	var update = document.getElementById("updateInput").value;
	var edition = document.getElementById("editionInput").value;
	var language = document.getElementById("languageInput").value;
	var sw_edition = document.getElementById("sw_editionInput").value;
	var target_hw = document.getElementById("target_hwInput").value;
	var target_sw = document.getElementById("target_swInput").value;
	var other = document.getElementById("otherInput").value;
	var versionCmp = document.getElementById("versionCmpInput").value;
	
	if (versionCmp !== "lt" && versionCmp !== "lteq" && versionCmp !== "gt" && versionCmp !== "gteq"){
		versionCmp = "eq";
	}
	
	//@TODO: Ajax Persistence for Comparator
	
	// codebase
	var codebase = false;
	if (document.getElementById("codeBaseInput").checked == true) {
		codebase = true;
	}
	persistNode(id, 0, part, vendor, product, version, update, edition,
			language, sw_edition, target_sw, target_hw, other, versionCmp, codebase,
			network, data, callback)

	return false;
}

function hideNodeDataEditor(network) {
	document.getElementById("codeBaseInput").disabled = true;
	document.getElementById("typePara").classList.add("enabled");
	document.getElementById("productPara").classList.add("enabled");
	document.getElementById("vendorPara").classList.add("enabled");
	document.getElementById("versionPara").classList.add("enabled");
	document.getElementById("updatePara").classList.add("enabled");
	document.getElementById("editionPara").classList.add("enabled");
	document.getElementById("languagePara").classList.add("enabled");
	document.getElementById("sw_editionPara").classList.add("enabled");
	document.getElementById("target_hwPara").classList.add("enabled");
	document.getElementById("target_swPara").classList.add("enabled");
	document.getElementById("otherPara").classList.add("enabled");
	document.getElementById("versionCmpPara").classList.add("enabled");

	document.getElementById("typeInput").classList.remove("enabled");
	document.getElementById("productInput").classList.remove("enabled");
	document.getElementById("vendorInput").classList.remove("enabled");
	document.getElementById("versionInput").classList.remove("enabled");
	document.getElementById("updateInput").classList.remove("enabled");
	document.getElementById("editionInput").classList.remove("enabled");
	document.getElementById("languageInput").classList.remove("enabled");
	document.getElementById("sw_editionInput").classList.remove("enabled");
	document.getElementById("target_hwInput").classList.remove("enabled");
	document.getElementById("target_swInput").classList.remove("enabled");
	document.getElementById("otherInput").classList.remove("enabled");
	document.getElementById("versionCmpInput").classList.remove("enabled");
	document.getElementById("cancelBtn").classList.remove("enabledInline");
	document.getElementById("submitBtn").classList.remove("enabledInline");
	document.getElementById("nodePropertyEditBtn").classList
			.add("enabledInline");
	network.disableEditMode();
	
	document.getElementById("nodeAddBtn").classList.remove("active");
}

function saveEdgeData(data, callback, label) {
	if (typeof data.to === 'object')
		data.to = data.to.id
	if (typeof data.from === 'object')
		data.from = data.from.id
	data.label = label;
	persistRequirement(data.from, data.to);
	callback(data);
}

function removeListItem(item){
	$(item).remove();
}