$(function(){
	bindCSRFToAJAXHeaders();
	$("div.prob-class").each(function(){
		colorRiskClasses($(this));
	});	
	$("#submit-probability-weighing").click(function(){
		var riskPhi = $("input[name='risk-phi-slider']").val()/100;
		var riskPhiType = $("input[name='risk-phi-slider']").attr("data-type");
		var riskPhiJSON={
			risk_phi_value : riskPhi,
			risk_phi_type : riskPhiType
		}
		$("#loading-gif").css("display","block");
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(riskPhiJSON),
			url : "./updateRiskPhi",
			success : function(){
				$("#loading-gif").css("display","none");		
				var riskPhi = $("input[name='risk-phi-risk-slider']").val()/100;
				var riskPhiType = $("input[name='risk-phi-risk-slider']").attr("data-type");
				var riskPhiJSON2={
					risk_phi_value : riskPhi,
					risk_phi_type : riskPhiType
				}
				$("#loading-gif").css("display","block");
				$.ajax({
					method: "POST",
					headers : {
						"Content-Type" : "application/json"
					},
					data : JSON.stringify(riskPhiJSON2),
					url : "./updateRiskPhi",
					success : function(){
						$("#loading-gif").css("display","none");		
						location.reload();		
					},
					error : function(){
						alert("An error has occured. The probability component weights have not been updated!");
						$("#loading-gif").css("display","none");
					}
				});	
			},
			error : function(){
				alert("An error has occured. The probability component weights have not been updated!");
				$("#loading-gif").css("display","none");
			}
		});
	});
	
});
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
function colorRiskClasses(riskBox){
		var h=  Math.floor((100 - ($(riskBox).attr("data-val")-1)) * 120 / 100);
	    var s = Math.abs(($(riskBox).attr("data-val")-1) - 50)/50;
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