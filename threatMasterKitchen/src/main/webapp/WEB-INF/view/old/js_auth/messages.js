function fancifyTitle(title) {
	var newString = "";
	for (var i = 0; i < title.length; i++) {
		newString += title.charAt(i) + "&nbsp;&nbsp;";
	}
	return newString;
}

function showMessage(messageType, message){
	var messageBox = $("<div></div>");
	if (messageType == "alert"){
		messageBox.addClass("alert");
	}else if (messageType = "message"){
		messageBox.addClass("message");
	}else{
		return null;
	}
	messageBox.click = function(){
		this.style.opacity = 1.0;
	};
	alertTextPara = $("<p></p>");
	alertTextPara.addClass("alertText");
	alertTextPara.text(message);
	closeBtn = $("<p></p>");
	closeBtn.addClass("closebtn");
	closeBtn.click = function(){
		this.parentElement.style.display='none';
	};
	closeBtn.html("&times;");
	messageBox.append(alertTextPara);
	messageBox.append(closeBtn);
	messageBox.fadeOut(4000, function(){
		this.parentElement.removeChild(this);
	});
	$("#alert-container").append(messageBox);
	/*
	var messageBox = document.createElement("div");
	if (messageType == 'alert'){
		messageBox.classList.add("alert");
		
		
	}else if (messageType = 'message'){
		messageBox.classList.add("message");
	}else{
		return null;
	}
	messageBox.onclick = 
		
	alertTextPara = document.createElement("p");
	closeBtn = document.createElement("p");
	
	alertTextPara.classList.add("alertText");
	alertTextPara.innerHTML = message;
	closeBtn.classList.add("closebtn");
	closeBtn.onclick = 
	closeBtn.innerHTML = "&times;";
	messageBox.appendChild(alertTextPara);
	messageBox.appendChild(closeBtn);
	
	document.getElementById("alert-container").appendChild(messageBox);*/
}