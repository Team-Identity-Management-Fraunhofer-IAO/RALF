$(function(){

	$("select[name='available_risk_list']").click(function(){
		var business_risk_id = $("select[name='available_risk_list'] option:selected").val();
		$("#business_risk_description li").css("display","none");
		$("#business_risk_description li[data-business-risk-id="+business_risk_id+"]").css("display","block");
		$("#business_risk_description li[data-business-risk-id="+business_risk_id+"] ul li").css("display","block");
	});
	$("select[name='service_risk_list']").click(function(){
		var business_risk_id = $("select[name='service_risk_list'] option:selected").val();
		$("#business_risk_description li").css("display","none");
		$("#business_risk_description li[data-business-risk-id="+business_risk_id+"]").css("display","block");
		$("#business_risk_description li[data-business-risk-id="+business_risk_id+"] ul li").css("display","block");
	});
	$("#assign_business_risk_btn").click(function(){
		var risk = $("select[name='available_risk_list'] option:selected");
		$("select[name='service_risk_list']").append(risk);
		return false;
	});
	$("#remove_business_risk_btn").click(function(){
		var risk = $("select[name='service_risk_list'] option:selected");
		$("select[name='available_risk_list']").append(risk);
		return false;
	});
	$("#service_modelling_form").submit(function(){
		$(this).find("option").prop("selected", true);
	});
});
