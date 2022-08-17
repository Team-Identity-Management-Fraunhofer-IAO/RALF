$(window).on('load', function(){
	console.log("Calculating Occurence Probabilities");
	calculateOccurenceProbabilities("max");
	calculateOccurenceProbabilities("pivot");
	window.print();
});
var currentProbabilityType = "max";
var threatBoxHover = true;
var riskBoxHover = true;
$(function(){
	bindCSRFToAJAXHeaders();
	$("div.prob-class").each(function(){
		colorRiskClasses($(this),"max");
	});
	calculatePivotingProbabilities(0);
	$("div.maximum-threat-container div.threat-box").each(function(){
		colorRiskClasses($(this),"max");
	});
	$("div.prob-legend").each(function(){
		colorRiskClasses($(this),"max"); 
	});
	$("a.show-risk-overview-btn").click(function(){
		$("div.risk-overview").css("display","block");
		$("div.attacker-overview").css("display","none");
		$("div.malware-overview").css("display","none");
	});
	$("a.show-attacker-overview-btn").click(function(){
		$("div.risk-overview").css("display","none");
		$("div.attacker-overview").css("display","block");
		$("div.malware-overview").css("display","none");
	});
	$("a.show-malware-overview-btn").click(function(){
		$("div.risk-overview").css("display","none");
		$("div.attacker-overview").css("display","none");
		$("div.malware-overview").css("display","block");
	});
	$("div.business-risk-container").mouseover(function(){
		var riskId2 = $(this).attr("data-id");
		if (riskBoxHover){
			if (!riskTagUnpositioned(riskId2)){			
				var riskId = $(this).attr("data-id");
				$("div.business-risk-container").each(function(){
					var riskId2 = $(this).attr("data-id");
					$("#risk-tag-"+riskId2).css("display","none");
				});
				$("#risk-tag-"+riskId).css("display","block");
			}
		}
	});
	$("div.business-risk-container").mouseleave(function(){
		if (riskBoxHover){
			$("div.business-risk-container").each(function(){
				var riskId2 = $(this).attr("data-id");
				if (!riskTagUnpositioned(riskId2)){
					$("#risk-tag-"+riskId2).css("display","block");
				}
			});
		}
	});
	$("div.business-risk-container").click(function(){
		var riskId2 = $(this).attr("data-id");
		if (riskBoxHover){
			if (!riskTagUnpositioned(riskId2)){					
				var riskId = $(this).attr("data-id");
				$("div.business-risk-container").each(function(){
					var riskId2 = $(this).attr("data-id");
					$("#risk-tag-"+riskId2).css("display","none");
				});
				$("#risk-tag-"+riskId).css("display","block");
				riskBoxHover = false;
			}
		}else{
			$("div.business-risk-container").each(function(){
				var riskId2 = $(this).attr("data-id");
				if ($("#risk-tag-"+riskId2).css("display")!="none"){
					$("#risk-tag-"+riskId2).css("display","block");
				}
			});
			riskBoxHover = true;
		}
	});
	$("input[name='probabilityOption']").change(function(){
		if ($(this).val()=="max"){
			currentProbabilityType = "max";
			calculateOccurenceProbabilities("max");
			$("div.pivot-box").css("display","none");
			$("div.pivot-coverage-box").css("display","none");
			$("div.threat-box").each(function(){
				colorRiskClasses($(this),"max");
			});
			$("div.success-probability").css("display","block");
			$("div.pivoted-success-probability").css("display","none");
		}else if ($(this).val()=='pivot'){
			currentProbabilityType = "pivot";
			calculateOccurenceProbabilities("pivot");
			$("div.pivot-box").css("display","block");
			$("div.pivot-coverage-box").css("display","block");
			$("div.threat-box").each(function(){
				colorRiskClasses($(this),"pivot");
			});
			$("div.success-probability").css("display","none");
			$("div.pivoted-success-probability").css("display","block");
		}
	});
	$("div.threat-box").mouseover(function(){
		if (threatBoxHover){
			var threat_id = $(this).data("threat-id");
			$("div.pivot-box svg line").each(function(){
				$(this).attr("opacity","0.1");
				if (($(this).data("from-id")==threat_id) || ($(this).data("to-id")==threat_id)){
					$(this).attr("opacity","1.0");
				}
			});		
		}
	});
	$("div.threat-box").click(function(){
		$("div.threat-box").css("opacity","0.7");
		if (threatBoxHover){
			var threat_id = $(this).data("threat-id");
			$(this).css("opacity","1.0");
			$("div.pivot-box svg line").each(function(){
				$(this).attr("opacity","0.1");
				if (($(this).data("from-id")==threat_id) || ($(this).data("to-id")==threat_id)){
					$(this).attr("opacity","1.0");
				}
			});	
			$("div.alternative-probabilities-container").css("display","none");
			$("div.alternative-probabilities-container[data-threat-id='"+threat_id+"']").css("display","block");
			threatBoxHover = false;
		}else{
			var threat_id = $(this).data("threat-id");
			$("div.pivot-box svg line").each(function(){
				$(this).attr("opacity","0.1");
			});	
			$("div.alternative-probabilities-container").css("display","none");
			threatBoxHover = true;
		}
	});
	$("span.exclude-threat-from-analysis-btn").click(function(){
		var threat_id = $(this).parent().parent().parent().attr("data-threat-id");
		var risk_report_id = $(this).parent().parent().parent().attr("data-risk-report-id");
		var btn = $(this);
		$("#loading-gif").css("display","block");	
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : "",
			url : "../excludeThreat/"+risk_report_id+"/"+threat_id,
			success : function(data){
				$("#loading-gif").css("display","none");
				$("div.threat-box").each(function(){
					if ($(this).attr("data-threat-id")==threat_id){
						$(this).find("span.exclude-threat-from-analysis-btn").css("display","none");
						$(this).find("span.include-threat-in-analysis-btn").css("display","block");
						$(this).addClass("excluded-threat");	
						calculatePivotingProbabilities(0);
						
						console.log(currentProbabilityType);
						$("div.pivot-box svg line").each(function(){
							if (($(this).data("from-id")==threat_id) || ($(this).data("to-id")==threat_id)){
								$(this).attr("visibility","hidden");
							}
						});						
					}
					colorRiskClasses($(this),currentProbabilityType);
				});
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	$("span.include-threat-in-analysis-btn").click(function(){
		var threat_id = $(this).parent().parent().parent().attr("data-threat-id");
		var risk_report_id = $(this).parent().parent().parent().attr("data-risk-report-id");
		$("#loading-gif").css("display","block");	
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : "",
			url : "../includeThreat/"+risk_report_id+"/"+threat_id,
			success : function(data){
				$("#loading-gif").css("display","none");
				$("div.threat-box").each(function(){
					if ($(this).attr("data-threat-id")==threat_id){
						$(this).find("span.exclude-threat-from-analysis-btn").css("display","block");
						$(this).find("span.include-threat-in-analysis-btn").css("display","none");
						$(this).removeClass("excluded-threat");		
						calculatePivotingProbabilities(0);
						$("div.pivot-box svg line").each(function(){
							if (($(this).data("from-id")==threat_id) || ($(this).data("to-id")==threat_id)){
								$(this).attr("visibility","visible");
							}
						});	
					}					
					colorRiskClasses($(this),currentProbabilityType);
				});	
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
	});
	$("a.mitigation").click(function(){
		$("div.mitigation-box").css("display","none");
		var mitigationId = $(this).data("mitigation-id");
		$('div.mitigation-box[data-mitigation-id="'+mitigationId+'"]').css("display","block");
	});
	$("input.control-checkbox").change(function(){
		var mitigationId = $(this).val();
		if ($(this).is(":checked")){
			$(this).parent().parent().parent().parent().find("input[name='notMitigatedThreats']").prop("checked", false);			
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("notApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("partiallyApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().addClass("fullyApplyingMitigation");
		}else{
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().addClass("notApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("partiallyApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("fullyApplyingMitigation");
		}
	});
	$("input.threat-checkbox").change(function(){
		if ($(this).is(":checked")){
			$(this).parent().parent().parent().parent().parent().find("input[name='fullyMitigatingControls']").prop("checked", false);
			var mitigationId = $(this).data("mitigation-id");
			if (($(this).parent().parent().parent().parent().parent().find("input.control-application-fully-checkbox").is(":checked")) || ($(this).parent().parent().parent().parent().parent().find("input.control-application-partially-checkbox").is(":checked"))){
				$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("fullyApplyingMitigation");
				$("li a[data-mitigation-id='"+mitigationId+"']").parent().addClass("partiallyApplyingMitigation");
				$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("notApplyingMitigation");
			}
		}
	});
	$("input.control-application-fully-checkbox").change(function(){
		var mitigationId = $(this).val();
		if ($(this).is(":checked")){
			$(this).parent().parent().parent().find("input.control-application-partially-checkbox").prop("checked",false);
			if ($(this).parent().parent().parent().find("input.control-checkbox").is(":checked")){
				$("li a[data-mitigation-id='"+mitigationId+"']").parent().addClass("fullyApplyingMitigation");
				$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("partiallyApplyingMitigation");
				$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("notApplyingMitigation");
			}else{
				$("li a[data-mitigation-id='"+mitigationId+"']").parent().addClass("partiallyApplyingMitigation");
				$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("fullyApplyingMitigation");
				$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("notApplyingMitigation");
			}								
		}else{
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().addClass("notApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("partiallyApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("fullyApplyingMitigation");
		}
	});
	$("input.control-application-partially-checkbox").change(function(){
		var mitigationId = $(this).val();
		if ($(this).is(":checked")){
			$(this).parent().parent().parent().find("input.control-application-fully-checkbox").prop("checked",false);
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("notApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().addClass("partiallyApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("fullyApplyingMitigation");
		}else{
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().addClass("notApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("partiallyApplyingMitigation");
			$("li a[data-mitigation-id='"+mitigationId+"']").parent().removeClass("fullyApplyingMitigation");
		}
	});
	$("input.mitigation-report-submit-btn").click(function(){
		var controls = [];
		$("div.mitigation-box").each(function(){
			var mitigationId = $(this).data("mitigation-id");
			var appliesTo = "";
			if ($(this).find("input.control-application-fully-checkbox").is(":checked")){
				appliesTo = "fullyAppliedControl";
			}else if ($(this).find("input.control-application-partially-checkbox").is(":checked")){
				appliesTo = "partiallyAppliedControl";
			}else{
				appliesTo = "doesNotApply";
			}
			var allThreats = false;
			if ($(this).find("input.control-checkbox").is(":checked")){
				allThreats = true;
			}
			var excludedThreats = [];
			if (!allThreats){
				$(this).find("input.threat-checkbox").each(function(){
					if ($(this).is(":checked")){
						excludedThreats.push($(this).val());
					}
				})
			}
			controls.push({
				controlId : mitigationId,
				appliesTo : appliesTo,
				allThreats: allThreats,
				excludedThreats: excludedThreats
			});			
		});
		var platforms=$("input[name='checkedPlatformIDs']").val();
			var collections=$("input[name='checkedCollections']").val();
			var mitigationJSON = {
				platforms : platforms,
				collections : collections,
				controls: controls
		}
		$("#loading-gif").css("display","block");	
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(mitigationJSON),
			url : "./submitControlReport",
			success : function(data){
				$("#loading-gif").css("display","none");	
				var control_report_id = data;
				$("#loading-gif").css("display","block");	
				createRiskReport(control_report_id);
			},
			error : function(){
				alert("An error has occured! Please try again!");
			}
		});
		return false;
	});
});
function calculateOccurenceProbabilities(type){
	var riskPhi = parseFloat($("input[name='riskPhi']").val());
	var riskPhiRisk = parseFloat($("input[name='riskPhiRisk']").val());
	var existenceProbability = parseFloat($("input[name='existenceProbability']").val());
	var occurenceProbabilityCategorized=0;
	if (type === "max"){
		$("div.maximum-probability-matrix div.prob-class").css("opacity","0.2");
	}else if (type==="pivot"){
		$("div.pivoted-probability-matrix div.prob-class").css("opacity","0.2");
	}
		
	$("div.business-risk-container").each(function(occurenceProbabilityCategorized){
		var impactCategorized = $(this).attr("data-val");
		var riskId = $(this).attr("data-id");		
		if (type === "max"){
			
			var successProbability = getMaxSuccessProbability(type);	
			var occurenceProbability = Math.round(riskPhi*successProbability+(1-riskPhi)*existenceProbability);
			var occurenceProbabilityCategorized = getMaxCategoryValue(occurenceProbability,"probability");
			$("#business-risk-"+riskId+" div.maximum-probability-matrix div.prob-class").each(function(){
				if ($(this).attr("data-y-val") == impactCategorized){
					if ($(this).attr("data-x-val")==occurenceProbabilityCategorized){
						$(this).css("opacity","1.0");
						//$("#risk-tag-"+riskId).css("display","block");
					}
				}
			});
		}else if (type==="pivot"){
			
			var killerId = $(this).data("killer-ids");		
			var killerIDSuccProb = getMaxSuccessProbabilityForKillerIDs(killerId);
			var applicableProbs = [];
			for (var i = 0; i < killerId.length; i++){
				if (killerIDSuccProb.has(killerId[i])){
					applicableProbs.push(killerIDSuccProb.get(killerId[i]));
				}
			}
			var maxProb = 0;
			for (var i = 0; i < applicableProbs.length; i++){
				if (applicableProbs[i] > maxProb){
					maxProb = applicableProbs[i];
				}
			}
			if (maxProb != 0){
				var occurenceProbability = Math.round(riskPhi*maxProb+(1-riskPhi)*existenceProbability);
				var occurenceProbabilityCategorized = getMaxCategoryValue(occurenceProbability,"probability");
				$("#business-risk-"+riskId+" div.pivoted-probability-matrix div.prob-class").each(function(){
					if ($(this).attr("data-y-val") == impactCategorized){
						if ($(this).attr("data-x-val")==occurenceProbabilityCategorized){
							$(this).css("opacity","1.0");
						}
					}
				});		
			}
		}
	});
}
var riskTagUnpositioned = function(riskId){
	if ($("#risk-tag-"+riskId).css("top")=="0px" && $("#risk-tag-"+riskId).css("left")=="0px"){
		return true;
	}
	return false;
}
function calculatePivotingProbabilities(phaseIndex){
	$("div.phase-box").each(function(){
		if ($(this).data("phase-index")==phaseIndex){
			$(this).find("div.threat-box").each(function(){
				pivotProbabilities($(this),phaseIndex);		
			});					
		}
	});
	phaseIndex++;
	$("div.phase-box").each(function(){
		if ($(this).data("phase-index") == phaseIndex){
			calculatePivotingProbabilities(phaseIndex);
		}
	});
}
function pivotProbabilities(threatBox,phaseIndex){
	var maxProbs = [];
	$("div.pivot-box svg line").each(function(){
		if ($(this).data("to-id") == threatBox.data("threat-id")){
			var fromId = $(this).data("from-id");
			$("div.threat-box").each(function(){
				if ($(this).data("threat-id")==fromId){
					if ($(this).parent().parent().data("phase-index")<phaseIndex){
						if (!($(this).hasClass("excluded-threat"))){
							if (!($(this).data("pivot-val"))){
								maxProbs.push($(this).data("val"));
							}else{
								maxProbs.push($(this).data("pivot-val"));
							}
							
						}
					}
				}
			});
		}		
	});
	var maxProb = 0;
	for (var i = 0; i < maxProbs.length; i++){
		if (maxProbs[i] > maxProb){
			maxProb = maxProbs[i];
		}
	}
	if ((maxProb > 0) && (maxProb < $(threatBox).data("val"))){		
		$(threatBox).attr("data-pivot-val",maxProb);
	}else if (maxProb > 0){
		$(threatBox).attr("data-pivot-val",$(threatBox).data("val"));
	}
	
}

var getMaxCategoryValue = function(value,category){
	var maxCategoryValue = value;
	$("div."+category+"-class").each(function(){		
		if (value >= $(this).attr("data-val-min")){
			if (value <= $(this).attr("data-val-max")){
				maxCategoryValue = $(this).attr("data-val-max");
			}
		}
	});
	return maxCategoryValue;
}
var getMaxSuccessProbability = function(type){
	var exProb = 0;
	$("div.threat-box").each(function(){
		if (!($(this).hasClass("excluded-threat"))){
			var attrVal="";
			if (type === "max"){
				attrVal="data-val";	
			}else if (type === "pivot"){
				attrVal="data-pivot-val";
			}
			var val = parseInt($(this).attr(attrVal));
			if (val > exProb){
				console.log(exProb+" > "+val);
				exProb = val;
			}
		}
	});
	return exProb;
}
var getMaxSuccessProbabilityForKillerIDs = function(killer_ids){
	var exProb = 0;
	var probPerKiller = new Map();
	$("div.threat-box").each(function(){
		if ($(this).data("killer-ids")){
			var killerIDs = $(this).data("killer-ids");
			for (var i = 0; i < killerIDs.length; i++){
				var proceed = false;
				for (var j = 0; j < killer_ids.length; j++){
					if(killerIDs[i] == killer_ids[j]){
						proceed = true;
						break;		
					}
				}
				if (proceed){
					if (probPerKiller.has(killerIDs[i])){
						if ($(this).attr("data-pivot-val") > probPerKiller.get(killerIDs[i])){
							probPerKiller.set(killerIDs[i],$(this).attr("data-pivot-val"));
						}
					}else{
						probPerKiller.set(killerIDs[i],$(this).attr("data-pivot-val"));
					}
				}
			}
		}
	});
	return probPerKiller;
}

function createRiskReport(control_report_id){
	$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : "{}",
			url : "../RiskReporting/"+control_report_id,
			success : function(data){
				var risk_report_id = data;
				$("#loading-gif").css("display","none");	
				window.location = "../RiskReporting/ViewReport/"+risk_report_id;
			},
			error : function(){
				alert("An error has occured! The risk report has not been created!");
			}
		});
		return false;
}
function colorRiskClasses(riskBox, type){
	var attrVal = "";
	if (type=="max"){
		attrVal = "data-val";
	} else if (type="pivot"){
		attrVal = "data-pivot-val";
	}
		var h=  Math.floor((100 - ($(riskBox).attr(attrVal)-1)) * 120 / 100);
	    var s = Math.abs(($(riskBox).attr(attrVal)-1) - 50)/50;
		riskBox.css({backgroundColor: hsv2rgb(h,s,1)});
}

var hsv2rgb = function(h, s, v) {
  // adapted from http://schinckel.net/2012/01/10/hsv-to-rgb-in-javascript/
  var rgb, i, data = [];
  if (s === 0) {
    rgb = [v,v,v];
  } else {
    h = h / 60;
    i = Math.floor(h);
    data = [v*(1-s), v*(1-s*(h-i)), v*(1-s*(1-(h-i)))];
    switch(i) {
      case 0:
        rgb = [v, data[2], data[0]];
        break;
      case 1:
        rgb = [data[1], v, data[0]];
        break;
      case 2:
        rgb = [data[0], v, data[2]];
        break;
      case 3:
        rgb = [data[0], data[1], v];
        break;
      case 4:
        rgb = [data[2], data[0], v];
        break;
      default:
        rgb = [v, data[0], data[1]];
        break;
    }
  }
  return '#' + rgb.map(function(x){
    return ("0" + Math.round(x*255).toString(16)).slice(-2);
  }).join('');
};
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