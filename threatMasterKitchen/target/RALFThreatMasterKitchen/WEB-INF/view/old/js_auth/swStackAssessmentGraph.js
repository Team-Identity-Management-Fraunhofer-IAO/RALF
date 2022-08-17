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
		} else {
			selectedNodeId = -1;
			selectedFromId = -1;
			selectedToId = -1;
			document.getElementById("node-property-container").classList
					.remove("enabled");
		}

		if (edge.length > 0 && !(node.length > 0)) {
			selectedNodeId = -1;
			var connectedEdge = network.getConnectedNodes(edge);
			selectedFromId = connectedEdge[0];
			selectedToId = connectedEdge[1];			
		} else {
			selectedNodeId = -1;
			selectedFromId = -1;
			selectedToId = -1;
		}
	});

	

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