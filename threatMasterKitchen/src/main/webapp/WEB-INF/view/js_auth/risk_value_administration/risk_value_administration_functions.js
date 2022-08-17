$(function(){
	colorRiskClasses($("div.risk-classes-template"));
	bindCSRFToAJAXHeaders();
	$("input.risk_classes_number").change(function(){
		var originalValue = $(this).parent().parent().next().data("origin-number");
		var newValue = $(this).val();
		var newWidth = Math.floor(500/newValue);
		if (newValue > originalValue){
			var riskClassTemplate = $(this).parent().parent().next().find("div.risk-classes-template");
			for (var i = 0; i < (newValue - originalValue); i++){
				var riskBox = riskClassTemplate.clone(true,true);
				riskBox.removeClass("risk-classes-template");
				riskClassTemplate.after(riskBox);
			}
		}else{
			for (var i = 0; i < originalValue - newValue; i++){
				var riskBox = $(this).parent().parent().next().find("div.risk-classes-template").next();
				riskBox.remove();
			}		
		}
		var riskStep = Math.floor(100/newValue);
		$(this).parent().parent().next().data("origin-number",newValue);
		reNumberRiskClasses($(this).parent().parent().next().find("div.risk-classes-template"),1,riskStep,newWidth);
	});
	var previousElement = null;
	var contextElement = null;
	var adjustClasses = false;
	var previousWidth = 0;
	var contextWidth = 0;
	var newPreviousWidth = 0;
	var newContextWidth = 0;
	$("div.risk-class").mousemove(function(){
		if (adjustClasses){
			if ($(this).is(contextElement)){
				newPreviousWidth = previousWidth+event.offsetX;
				newContextWidth = contextWidth-event.offsetX;
			}else if ($(this).is(previousElement)){
				var delta = event.offsetX-$(previousElement).width();
				newPreviousWidth = previousWidth+delta;
				newContextWidth = contextWidth-delta;
			}
		}
	});
	$("div.risk-classes-adjustor").mousedown(function(){
		contextElement = $(this).parent();
		contextWidth = $(contextElement).width();		
		newContextWidth = contextWidth;
		previousElement = $(this).parent().prev();
		previousWidth = $(previousElement).width();
		newPreviousWidth = previousWidth;
		if (!adjustClasses){
			adjustClasses = true;
		}
	});
	$("div.risk-class").mouseup(function(){
		if (adjustClasses){
			adjustClasses = false;
			var newTo = parseInt($(previousElement).attr("data-from"),10)+Math.floor((newPreviousWidth*100)/500);//Math.floor((newPreviousWidth*$(previousElement).attr("data-to"))/$(previousElement).width());
			console.log(newTo);
			
			$(previousElement).attr("data-to",newTo);
			$(contextElement).attr("data-from",(newTo+1));
			
			$(previousElement).width(newPreviousWidth);
			$(contextElement).width(newContextWidth);
			
			$(previousElement).find("input[name='risk_class_name']").css({width:newPreviousWidth-6});
			$(contextElement).find("input[name='risk_class_name']").css({width:newContextWidth-6});
			
			var h=  Math.floor((100 - (newTo+1)) * 120 / 100);
	    	var s = Math.abs((newTo+1) - 50)/50;
			$(contextElement).css({backgroundColor: hsv2rgb(h,s,1)});
			$(contextElement).find("input[name='risk_class_name']").css({backgroundColor: hsv2rgb(h,s,1)});
		}
	});
	$("#submit-risk-classes").click(function(){
		var riskClasses = [];
		$("div.risk-classification div.risk-class").each(function(){
			riskClasses.push({
				from: $(this).data("from"),
				to: $(this).data("to"),
				name: $(this).find("input[name='risk_class_name']").val()
				});
		});
		var impactClasses = [];
		$("div.impact-classification div.risk-class").each(function(){
			impactClasses.push({
				from: $(this).data("from"),
				to: $(this).data("to"),
				name: $(this).find("input[name='risk_class_name']").val()
				});
		});
		var probabilityClasses = [];
		$("div.probability-classification div.risk-class").each(function(){
			probabilityClasses.push({
				from: $(this).data("from"),
				to: $(this).data("to"),
				name: $(this).find("input[name='risk_class_name']").val()
				});
		});
		var classesJSON = {
			riskClasses: riskClasses,
			impactClasses: impactClasses,
			probabilityClasses: probabilityClasses
		};
		console.log(classesJSON);
		$("#loading-gif").css("display","block");
		$.ajax({
			method: "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify(classesJSON),
			url : "./updateRiskClasses",
			success : function(){
				$("#loading-gif").css("display","none");				
			},
			error : function(){
				alert("An error has occured. The risk classes have not been updated!");
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
	if ($(riskBox).length){
		var h=  Math.floor((100 - ($(riskBox).attr("data-from")-1)) * 120 / 100);
	    var s = Math.abs(($(riskBox).attr("data-from")-1) - 50)/50;
		riskBox.css({backgroundColor: hsv2rgb(h,s,1)});
		riskBox.find("input[name='risk_class_name']").css({backgroundColor: hsv2rgb(h,s,1)});
		colorRiskClasses(riskBox.next());
	}
}

function reNumberRiskClasses(riskBox,current,step,width){
	if ($(riskBox).length){
		riskBox.attr("data-from",current);
		if (!($(riskBox.next()).length)){
			riskBox.attr("data-to",100);
		}else{
			riskBox.attr("data-to",((current-1)+step));
		}
		
		var h=  Math.floor((100 - (current-1)) * 120 / 100);
	    var s = Math.abs((current-1) - 50)/50;
		riskBox.css({width:width, backgroundColor: hsv2rgb(h,s,1)});
		riskBox.find("input[name='risk_class_name']").css({border:"none", width:width-6, backgroundColor: hsv2rgb(h,s,1)});
		reNumberRiskClasses(riskBox.next(), (current+step),step,width);
	}
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