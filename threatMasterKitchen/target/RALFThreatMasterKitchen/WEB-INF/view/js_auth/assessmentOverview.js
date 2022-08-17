function initializeAssessmentOverview(){
	var ctx = document.getElementById("assessment-viz").getContext("2d");
	var label_data = createLabels();
	var network_data = createNetworkVectorData();
	var network_data_treated = createNetworkTreatedVectorData();
	var network_data_managed = createNetworkManagedVectorData();
	var adjacent_data = createAdjacentNetworkVectorData();
	var adjacent_data_treated = createAdjacentNetworkTreatedVectorData();
	var adjacent_data_managed = createAdjacentNetworkManagedVectorData();
	var local_data = createLocalVectorData();
	var local_data_treated = createLocalTreatedVectorData();
	var local_data_managed = createLocalManagedVectorData();
	var physical_data = createPhysicalVectorData();
	var physical_data_treated = createPhysicalTreatedVectorData();
	var physical_data_managed = createPhysicalManagedVectorData();
	Chart.defaults.global.defaultFontFamily = 'system';
	var data = [];
	data.push({
		label: 'Network Vector',
		data: network_data,
		borderWidth: 1,
		backgroundColor: 'rgba(23,156,125,0.6)'
	});
	if (!isArrayEmpty(network_data_managed)){
		data.push({
					label: 'Managed',
					data: network_data_managed,
					borderWidth: 1,
					backgroundColor: 'rgba(23,156,125,0.4)'
				});
	}
	if (!isArrayEmpty(network_data_treated)){
		data.push({
			label: 'Treated',
			data: network_data_treated,
			borderWidth: 1,
			backgroundColor: 'rgba(23,156,125,0.2)'
		});
	}
	data.push({
		label: 'Adjacent Network Vector',
		data: adjacent_data,
		borderWidth: 1,
		backgroundColor: 'rgba(31,130,192,0.6)'
	});
	if (!isArrayEmpty(adjacent_data_managed)){
		data.push({
					label: 'Managed',
					data: adjacent_data_managed,
					borderWidth: 1,
					backgroundColor: 'rgba(31,130,192,0.4)'
				});
	}
	if (!isArrayEmpty(adjacent_data_treated)){
		data.push({
					label: 'Treated',
					data: adjacent_data_treated,
					borderWidth: 1,
					backgroundColor: 'rgba(31,130,192,0.2)'
				});
	}
	data.push({
					label: 'Local Vector',
					data: local_data,
					borderWidth: 1,
					backgroundColor: 'rgba(168,175,175,0.6)'
				});
	if (!isArrayEmpty(local_data_managed)){
		data.push({
			label: 'Managed',
			data: local_data_managed,
			borderWidth: 1,
			backgroundColor: 'rgba(168,175,175,0.4)'
		});
	}
	if (!isArrayEmpty(local_data_treated)){
		data.push({
			label: 'Treated',
			data: local_data_treated,
			borderWidth: 1,
			backgroundColor: 'rgba(168,175,175,0.2)'
		});
	}
	data.push({
					label: 'Physical Vector',
					data: physical_data,
					borderWidth: 1,
					backgroundColor: 'rgba(242,148,0,0.6)'
				});
	if (!isArrayEmpty(physical_data_managed)){
		data.push({
			label: 'Managed',
			data: physical_data_managed,
			borderWidth: 1,
			backgroundColor: 'rgba(242,148,0,0.4)'
		});
	}
	if (!isArrayEmpty(physical_data_treated)){
		data.push({
			label: 'Treated',
			data: physical_data_treated,
			borderWidth: 1,
			backgroundColor: 'rgba(242,148,0,0.2)'
		});
	}
	var overview = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: label_data,
			datasets: data
		},
		options: {
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	});
}

function initializeAssessment(){
	//ToDo write Graph initialization for each component
	var ctx = document.getElementById("assessment-viz").getContext("2d");
	var label_data = createLabels();
	var network_data = createNetworkVectorData();
	var network_data_treated = createNetworkTreatedVectorData();
	var network_data_managed = createNetworkManagedVectorData();
	var adjacent_data = createAdjacentNetworkVectorData();
	var adjacent_data_treated = createAdjacentNetworkTreatedVectorData();
	var adjacent_data_managed = createAdjacentNetworkManagedVectorData();
	var local_data = createLocalVectorData();
	var local_data_treated = createLocalTreatedVectorData();
	var local_data_managed = createLocalManagedVectorData();
	var physical_data = createPhysicalVectorData();
	var physical_data_treated = createPhysicalTreatedVectorData();
	var physical_data_managed = createPhysicalManagedVectorData();
	Chart.defaults.global.defaultFontFamily = 'system';
	var overview = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: label_data,
			datasets: [
				{
					label: 'Network Vector (Not managed and untreated)',
					data: network_data,
					borderWidth: 1,
					backgroundColor: 'rgba(23,156,125,0.6)'
				},{
					label: 'Network Vector (Managed)',
					data: network_data_managed,
					borderWidth: 1,
					backgroundColor: 'rgba(23,156,125,0.4)'
				},{
					label: 'Network Vector (Treated)',
					data: network_data_treated,
					borderWidth: 1,
					backgroundColor: 'rgba(23,156,125,0.2)'
				},{
					label: 'Adjacent Network Vector (Not managed and untreated)',
					data: adjacent_data,
					borderWidth: 1,
					backgroundColor: 'rgba(31,130,192,0.6)'
				},{
					label: 'Adjacent Network Vector (Managed)',
					data: adjacent_data_managed,
					borderWidth: 1,
					backgroundColor: 'rgba(31,130,192,0.4)'
				},{
					label: 'Adjacent Network Vector (Treated)',
					data: adjacent_data_treated,
					borderWidth: 1,
					backgroundColor: 'rgba(31,130,192,0.2)'
				},{
					label: 'Local Vector (Not managed and untreated)',
					data: local_data,
					borderWidth: 1,
					backgroundColor: 'rgba(168,175,175,0.6)'
				},{
					label: 'Local Vector (Managed)',
					data: local_data_managed,
					borderWidth: 1,
					backgroundColor: 'rgba(168,175,175,0.4)'
				},{
					label: 'Local Vector (Treated)',
					data: local_data_treated,
					borderWidth: 1,
					backgroundColor: 'rgba(168,175,175,0.2)'
				},{
					label: 'Physical Vector (Not managed and untreated)',
					data: physical_data,
					borderWidth: 1,
					backgroundColor: 'rgba(242,148,0,0.6)'
				},{
					label: 'Physical Vector (Managed)',
					data: physical_data_managed,
					borderWidth: 1,
					backgroundColor: 'rgba(242,148,0,0.4)'
				},{
					label: 'Physical Vector (Treated)',
					data: physical_data_treated,
					borderWidth: 1,
					backgroundColor: 'rgba(242,148,0,0.2)'
				}
			]
		},
		options: {
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}			
		}
	});
	
}

function isArrayEmpty(arr){
	for (var i = 0; i < arr.length; i++){
		if (arr[i] !== ""){
			return false;
		}
	}
	return true;
}

function createLabels(){
	var labels = [];
	$("b.assessment-caption").each(function(){
		labels.push($(this).text());
	});
	return labels;
}

function createNetworkVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push(
			$($($(this).find("td.cvss-network")).find("span.val-max")).text());
	});
	return data;
}

function createNetworkTreatedVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push(
			$($($(this).find("td.cvss-network-treated")).find("span.val-max")).text());
	});
	return data;
}

function createNetworkManagedVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push(
			$($($(this).find("td.cvss-network-managed")).find("span.val-max")).text());
	});
	return data;
}

function createAdjacentNetworkVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push($($($(this).find("td.cvss-adjacent")).find("span.val-max")).text());
	});
	return data;
}

function createAdjacentNetworkTreatedVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push($($($(this).find("td.cvss-adjacent-treated")).find("span.val-max")).text());
	});
	return data;
}


function createAdjacentNetworkManagedVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push($($($(this).find("td.cvss-adjacent-managed")).find("span.val-max")).text());
	});
	return data;
}


function createLocalVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push($($($(this).find("td.cvss-local")).find("span.val-max")).text());
	});
	return data;
}

function createLocalTreatedVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push($($($(this).find("td.cvss-local-treated")).find("span.val-max")).text());
	});
	return data;
}

function createLocalManagedVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push($($($(this).find("td.cvss-local-managed")).find("span.val-max")).text());
	});
	return data;
}

function createPhysicalVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push($($($(this).find("td.cvss-physical")).find("span.val-max")).text());
	});
	return data;
}

function createPhysicalTreatedVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push($($($(this).find("td.cvss-physical-treated")).find("span.val-max")).text());
	});
	return data;
}

function createPhysicalManagedVectorData(){
	var data = [];
	$("table.assessment-overview-table").each(function(){
		data.push($($($(this).find("td.cvss-physical-managed")).find("span.val-max")).text());
	});
	return data;
}