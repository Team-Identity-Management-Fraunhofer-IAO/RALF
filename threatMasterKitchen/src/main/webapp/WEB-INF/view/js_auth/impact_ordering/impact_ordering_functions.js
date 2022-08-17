$(function(){
	bindCSRFToAJAXHeaders();
	$("div.risk-container").each(function(){
		colorRiskClasses($(this));
	});
	$("select[name='risk-category-selector']").change(function(){
		var newWeight = $(this).val();
		var business_risk_id = $(this).parent().parent().attr("data-business-risk-id");
		var order_id = $(this).parent().parent().attr("data-custom-order-id");
		var orderJSON = {
			order_id : order_id,
			business_risk_id : business_risk_id,
			weight: newWeight
		};
		$("#loading-gif").css("display","block");	
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(orderJSON),
			url : "./ImpactOrdering/updateWeight",
			success : function(){
				$("#loading-gif").css("display","none");		
				location.reload();		
			},
			error : function(){
				alert("An error has occured. The probability component weights have not been updated!");
				$("#loading-gif").css("display","none");
			}
		});
	});
	$("#submit-impact-ordering-questionnaire-btn").click(function(){
		var comparedRisksJSON = [];
		$(".reference-block").each(function(){
			var id = $(this).data('business-risk-reference-id');
			var comparisonsJSON = [];
			$(this).parent().find(".comparison-block").each(function(){
				var operator = $(this).find("input[type='radio']:checked").val();
				var comparison = parseInt($(this).find("select[name='comparison']").val());
				var comparedId = $(this).data('business-risk-compared-id');
				comparisonsJSON.push({'compared_business_risk_id': comparedId, 'operator':operator, 'comparison':comparison});
			});
			comparedRisksJSON.push({'business_risk_id': id, 'compared_risks':comparisonsJSON});
		});
		console.log({comparedRisksJSON});
		$.ajax({
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(comparedRisksJSON),
			url : "./saveOrder",
			statusCode : {
						200 : function(data) {
							alert("Success!");
						},
						404 : function(data) {
							alert("Error!");
						}
					}
		});
	});
	
	
});

function colorRiskClasses(riskBox){
	var h=  Math.floor((100 - ($(riskBox).attr("data-weight")-1)) * 120 / 100);
	var s = Math.abs(($(riskBox).attr("data-weight")-1) - 50)/50;
	riskBox.css({borderColor: hsv2rgb(h,s,1)});
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