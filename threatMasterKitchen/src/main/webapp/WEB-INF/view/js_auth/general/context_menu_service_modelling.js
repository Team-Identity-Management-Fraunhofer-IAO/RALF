$(function(){
	$('.stack').contextmenu(function(){
		$('#context-menu-stack').css('display','block');
		$('#context-menu-organization').css('display','none');
		$('#context-menu-service').css('display','none');
		$('#context-menu-stack').css('top',(event.pageY - 30)+'px');
		$('#context-menu-stack').css('left',(event.pageX)+'px');
		$('#add-new-risk-button').attr("href",$("#add-new-risk-button").data("context")+"/ServiceDefinition/ServiceModelling/"+$(this).data("service-id")+"/RiskIdentification/newRisk");
		$('#edit-risk-button').attr("href",$("#edit-risk-button").data("context")+"/ServiceDefinition/ServiceModelling/"+$(this).data("service-id")+"/RiskIdentification/"+$(this).data("business-risk-id"));
		return false;
	});	
	$('.subnet').contextmenu(function(){
		$('#context-menu-stack').css('display','none');
		$('#context-menu-organization').css('display','none');
		$('#context-menu-service').css('display','block');
		$('#context-menu-service').css('top',(event.pageY - 30)+'px');
		$('#context-menu-service').css('left',(event.pageX)+'px');
		$('#edit-service-button').attr("href",$("#edit-service-button").data("context")+"/ServiceDefinition/ServiceModelling/"+$(this).data("service-id"));
		$('#add-new-risk-button').attr("href",$("#add-new-risk-button").data("context")+"/ServiceDefinition/ServiceModelling/"+$(this).data("service-id")+"/RiskIdentification/newRisk");
		return false;
	});	
	$('.organisation').contextmenu(function(){
		$('#context-menu-stack').css('display','none');
		$('#context-menu-organization').css('display','block');
		$('#context-menu-service').css('display','none');
		$('#context-menu-organization').css('top',(event.pageY - 30)+'px');
		$('#context-menu-organization').css('left',(event.pageX)+'px');
		return false;
	});	
	$('body').click(function(){
		$('#context-menu-stack').css('display','none');
		$('#context-menu-organization').css('display','none');
		$('#context-menu-service').css('display','none');
	});
});