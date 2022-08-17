let timer, timeoutVal = 1000;
function initializeEditorGUI(){
	
	//Save and discard editor functions
	/*$("#editor-btn-discard-changes").click(function(){
		location.reload();
	});*/
	
	$("#editor-btn-save-changes").click(function(){
		saveEditor();
	});

	//Metadata functions
	
	//show description editor
	$("#metadata-description").click(function(){
		$(this).fadeToggle("slow", function(){
			$("#metadata-description-editor").text = $(this).text();
			$("#metadata-description-editor").fadeToggle("slow", "linear");
		});
	});
	
	//hide description editor
	$("#metadata-description-editor").on('keypress', function(e){
		if (e.which === 13){
			$(this).fadeToggle("slow", function(){
				$("#metadata-description").text($(this).val());
				$("#metadata-description").fadeToggle("slow", "linear");
			});
		}
	});
	
	//Section Functions
	
	//Add a new section
	$("#editor-btn-add-section").click(function(){
		var sectionName = $("#content-section-editor-section-name-input").val();
		var section = $("#content-section-template").clone(true,true);
		$(section).removeAttr("id");
		$(section).find("h2.content-section-header-text").text(sectionName);
		$("#content-section-editor").after(section);
		section.fadeToggle("slow", "linear");
		$("#content-section-editor-section-name-input").val("");
	});
	
	//Edit the section title
	$("button.editor-btn-edit-section").click(function(){
		var container = $(this).parent().parent().parent();
		var sectionHeader = $(container).find("h2.content-section-header-text");
		var sectionName = $(sectionHeader).text();
		$(container).find("input.input-section-name").val(sectionName);
		$(sectionHeader).fadeToggle("slow", function(){
			$(container).find("div.section-name-editor").fadeToggle("slow","linear");
		});
	});
	
	//Apply the section edits
	$("button.editor-btn-apply-edit-section").click(function(){
		var container = $(this).parent().parent().parent();
		var sectionHeader = $(container).find("input.input-section-name").val();
		$(container).find("h2.content-section-header-text").text(sectionHeader);
		$(container).find("div.section-name-editor").fadeToggle("slow", function(){
			$(container).find("h2.content-section-header-text").fadeToggle("slow","linear");
		});
	});
	
	//Delete the section
	$("button.editor-btn-delete-section").click(function(){
		if (confirm("This will delete the section with all its components. Are you sure?")){
			var container = $(this).parent().parent().parent();
			$(container).fadeToggle("slow", function(){
				$(container).remove();
			});
		}
	});
	
	//Add a new component
	$("button.editor-btn-add-component").click(function(){
		var container = $(this).parent().parent().parent();
		$("button.new-component-btn").attr("style","");
		$("button.edit-component-btn").attr("style","display:none");
		$(container).find("div.content-section-header").after($("#content-component-editor"));
		//$(container).find("br.content-section-last-break").before($("#content-component-editor"));
		$("#content-component-editor").fadeToggle("slow","linear");
		$("button.editor-btn-add-component").prop("disabled",true);
		$("button.editor-btn-edit-section").prop("disabled",true);
		$("button.editor-btn-delete-section").prop("disabled",true);
	});
	
	//Component Editor Functions
	
	//Dynamically change the title in the editor
	$("#cpe-part-id").on('input', function(){
		$("#content-component-editor-title").text($(this).val()+", "+$("#cpe-vendor-id").val()+", "+$("#cpe-product-id").val()+", version: "+$("#cpe-version-id").val());
	});
	$("#cpe-vendor-id").on('input', function(){
		$("#content-component-editor-title").text($("#cpe-part-id").val()+", "+$(this).val()+", "+$("#cpe-product-id").val()+", version: "+$("#cpe-version-id").val());
	});
	$("#cpe-product-id").on('input', function(){
		$("#content-component-editor-title").text($("#cpe-part-id").val()+", "+$("#cpe-vendor-id").val()+", "+$(this).val()+", version: "+$("#cpe-version-id").val());
	});
	$("#cpe-version-id").on('input', function(){
		$("#content-component-editor-title").text($("#cpe-part-id").val()+", "+$("#cpe-vendor-id").val()+", "+$("#cpe-product-id").val()+", version: "+$(this).val());
	});
	
	//Discard Component without saving
	$("#content-component-editor-btn-discard-component").click(function(){
		$("input.cpe-input").val("*");
		if ($("#component-own-dev").prop("checked")){
			$("#component-own-dev").prop("checked", false);
		}
		$("#content-component-editor").fadeToggle("slow","linear");
		$("button.editor-btn-add-component").prop("disabled",false);
		$("button.editor-btn-edit-section").prop("disabled",false);
		$("button.editor-btn-delete-section").prop("disabled",false);
		$("#content-component-editor-title").text("-");
	});
	
	//Save Component
	$("#content-component-editor-btn-save-component").click(function(){
		var container = $("#content-section-component-template").clone(true,true);
		container.removeAttr("id");
		container.attr("class", "w3-margin-bottom w3-container content-section-component");
		var parentContainer = $(this).parent().parent().parent().parent();
		$(parentContainer.find("br.content-section-last-break")).before(container);
		$(container.find("td.cpe-part-text")).text($("#cpe-part-id").val());
		$(container.find("td.cpe-vendor-text")).text($("#cpe-vendor-id").val());
		$(container.find("td.cpe-product-text")).text($("#cpe-product-id").val());
		
		var cmp = $("#cpe-version-cmp-id").val();
		if (cmp === "gt"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text(">");
		}else if (cmp === "gteq"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text(">=");
		}else if (cmp === "eq"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text("=");
		}else if (cmp === "lteq"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text("<=");
		}else if (cmp === "lt"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text("<");
		}
		$($(container.find("td.cpe-version-text")).find("span.cpe-version-text-version")).text($("#cpe-version-id").val());
		
		$(container.find("td.cpe-edition-text")).text($("#cpe-edition-id").val());
		$(container.find("td.cpe-language-text")).text($("#cpe-language-id").val());		
		$(container.find("td.cpe-update-text")).text($("#cpe-update-id").val());		
		$(container.find("td.cpe-sw-edition-text")).text($("#cpe-sw-edition-id").val());
		$(container.find("td.cpe-target-sw-text")).text($("#cpe-target-sw-id").val());
		$(container.find("td.cpe-target-hw-text")).text($("#cpe-target-hw-id").val());
		$(container.find("td.cpe-other-text")).text($("#cpe-other-id").val());
		$(container.find("b.component-title")).text($("#content-component-editor-title").text());
		if ($("#component-own-dev-id").prop("checked")){
			$(container.find("td.cpe-component-own-dev-text")).text("Yes");
		}else{
			$(container.find("td.cpe-component-own-dev-text")).text("No");
		}
		$("#content-component-editor").fadeToggle("slow",function(){
			container.fadeToggle("slow","linear");
			$("input.cpe-input").val("*");		
			$("button.editor-btn-add-component").prop("disabled",false);
			$("button.editor-btn-edit-section").prop("disabled",false);
			$("button.editor-btn-delete-section").prop("disabled",false);
			$("#content-component-editor-title").text("-");
		});
	});
	
	//Edit Component (Open Editor)
	$("button.content-component-edit-btn").click(function(){
		$("button.new-component-btn").attr("style","display:none");
		$("button.edit-component-btn").attr("style","");
		var parentContainer = $(this).parent().parent().parent()
		$(parentContainer).before($("#content-component-editor"));
		$("#cpe-part-id").val($(parentContainer.find("td.cpe-part-text")).text());
		$("#cpe-vendor-id").val($(parentContainer.find("td.cpe-vendor-text")).text());
		$("#cpe-product-id").val($(parentContainer.find("td.cpe-product-text")).text());
		
		$("#cpe-version-id").val($(parentContainer.find("span.cpe-version-text-version")).text());
		var cmp = $(parentContainer.find("span.cpe-version-text-cmp")).text();
		if (cmp === "<"){
			$("#cpe-version-cmp-id").val("lt");
		}else if (cmp === "<="){
			$("#cpe-version-cmp-id").val("lteq");
		}else if (cmp === "="){
			$("#cpe-version-cmp-id").val("eq");
		}else if (cmp === ">="){
			$("#cpe-version-cmp-id").val("gteq");
		}else if (cmp === ">"){
			$("#cpe-version-cmp-id").val("gt");
		}
		
		$("#cpe-edition-id").val($(parentContainer.find("td.cpe-edition-text")).text());
		$("#cpe-language-id").val($(parentContainer.find("td.cpe-language-text")).text());		
		$("#cpe-update-id").val($(parentContainer.find("td.cpe-update-text")).text());
		$("#cpe-sw-edition-id").val($(parentContainer.find("td.cpe-sw-edition-text")).text());
		$("#cpe-target-sw-id").val($(parentContainer.find("td.cpe-target-sw-text")).text());
		$("#cpe-target-hw-id").val($(parentContainer.find("td.cpe-target-hw-text")).text());
		$("#cpe-other-id").val($(parentContainer.find("td.cpe-other-text")).text());
		if ($(parentContainer.find("td.cpe-component-own-dev-text")).text() === "Yes"){
			$("#component-own-dev-id").prop("checked", true);
		}else{
			$("#component-own-dev-id").prop("checked", false);
		}
		
		$("#content-component-editor-title").text($(parentContainer.find("b.component-title")).text());
		$("button.editor-btn-add-component").prop("disabled",true);
		$("button.editor-btn-edit-section").prop("disabled",true);
		$("button.editor-btn-delete-section").prop("disabled",true);
		$(parentContainer).fadeToggle("slow",function(){
			$("#content-component-editor").fadeToggle("slow","linear");
		});
	});
	
	//Delete Component (Remove Component from Section)
	$("button.content-component-delete-btn").click(function(){
		if (confirm('You are about to delete this component. Are you sure?')){
			var container = $(this).parent().parent().parent();
			$(container).fadeToggle("slow", function(){
				$(container).remove();
			});
			
		}
	});
	
	//Discard edited component
	$("#content-component-editor-btn-discard-edit-component").click(function(){
		$("#content-component-editor").fadeToggle("slow",function(){
			$("#content-component-editor").next().fadeToggle("slow","linear");
			$("input.cpe-input").val("*");
			$("button.editor-btn-add-component").prop("disabled",false);
			$("button.editor-btn-edit-section").prop("disabled",false);
			$("button.editor-btn-delete-section").prop("disabled",false);
			$("#content-component-editor-title").text("-");
		});
	});
	
	//Apply changes to edited component
	$("#content-component-editor-btn-save-edit-component").click(function(){
		var container = $("#content-component-editor").next();
		$(container.find("td.cpe-part-text")).text($("#cpe-part-id").val());
		$(container.find("td.cpe-vendor-text")).text($("#cpe-vendor-id").val());
		$(container.find("td.cpe-product-text")).text($("#cpe-product-id").val());
		var cmp = $("#cpe-version-cmp-id").val();
		if (cmp === "gt"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text(">");
		}else if (cmp === "gteq"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text(">=");
		}else if (cmp === "eq"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text("=");
		}else if (cmp === "lteq"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text("<=");
		}else if (cmp === "lt"){
			$(container.find("td.cpe-version-text").find("span.cpe-version-text-cmp")).text("<");
		}
		$($(container.find("td.cpe-version-text")).find("span.cpe-version-text-version")).text($("#cpe-version-id").val());
		$(container.find("td.cpe-edition-text")).text($("#cpe-edition-id").val());
		$(container.find("td.cpe-language-text")).text($("#cpe-language-id").val());
		$(container.find("td.cpe-update-text")).text($("#cpe-update-id").val());
		$(container.find("td.cpe-sw-edition-text")).text($("#cpe-sw-edition-id").val());
		$(container.find("td.cpe-target-sw-text")).text($("#cpe-target-sw-id").val());
		$(container.find("td.cpe-target-hw-text")).text($("#cpe-target-hw-id").val());
		$(container.find("td.cpe-other-text")).text($("#cpe-other-id").val());
		if ($("#component-own-dev-id").prop("checked")){
			$(container.find("td.cpe-component-own-dev-text")).text("Yes");
		}else{
			$(container.find("td.cpe-component-own-dev-text")).text("No");
		}		
		
		$(container.find("b.component-title")).text($("#content-component-editor-title").text());
		
		$("#content-component-editor").fadeToggle("slow",function(){
			container.fadeToggle("slow","linear");
			$("input.cpe-input").val("*");		
			$("button.editor-btn-add-component").prop("disabled",false);
			$("button.editor-btn-edit-section").prop("disabled",false);
			$("button.editor-btn-delete-section").prop("disabled",false);
			$("#content-component-editor-title").text("-");
		});
	});
	
	//Get CPE Suggestions
	$(".cpe-input").on('keyup', function(){
		$("#cpe-part-id").prop("disabled",false);
		$("#cpe-version-cmp-id").prop("disabled",false);
		$("#cpe-version-id").prop("disabled",false);
		$("#cpe-vendor-id").prop("disabled",false);
		$("#cpe-edition-id").prop("disabled",false);
		$("#cpe-product-id").prop("disabled",false);
		$("#cpe-sw-edition-id").prop("disabled",false);
		$("#cpe-update-id").prop("disabled",false);
		$("#cpe-language-id").prop("disabled",false);
		$("#cpe-target-sw-id").prop("disabled",false);
		$("#cpe-other-id").prop("disabled",false);
		$("#cpe-target-hw-id").prop("disabled",false);
		window.clearTimeout(timer);
		timer = window.setTimeout(() =>{
			loadCPEProposalsByFieldValues(this);
		}, timeoutVal);		
	});
	
	//Delete an application
	$("#editor-btn-delete-application").click(function(){
		deleteApplication();
	});
	
	//Bind CSRF Tokens to AJAX Requests
	bindCSRFToAJAXHeaders();

}

