<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page isELIgnored="false"%>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="./styles_auth/w3.css">
<script src="./js_auth/jquery-3.5.0.min.js"></script>
<script src="./js_auth/user_functions.js"></script>

<script>
// Used to toggle the menu on small screens when clicking on the menu button
function myFunction() {
  var x = document.getElementById("navDemo");
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
  } else { 
    x.className = x.className.replace(" w3-show", "");
  }
}
</script>
</head><body class="w3-light-grey" onload="initializeUserSettingsGUI()">
<%@ include file="menu_bar.jsp" %>"
<!-- Page Container -->
<div class="w3-content w3-margin-top" style="max-width:1400px; min-height: 99vh;">

  <!-- The Grid -->
  <div class="w3-row-padding">
	<div class="w3-col w3-large">
	&nbsp;
	</div>
	<div class="w3-col w3-large">
	&nbsp;
	</div>
	<div class="w3-col w3-large">
	&nbsp;
	</div>
	<div class="w3-col w3-large">
	&nbsp;
	</div>
	<div class="w3-row-padding">
		<div class="w3-card w3-white w3-margin-bottom">
			<div class="w3-container">
				<div class="w3-cell-row">
					<div class="w3-cell">
						<h2>User Settings</h2>
					</div>
				</div>			
			</div>
		</div>
	</div>
	<div class="w3-row-padding">
		<div class="w3-container w3-card w3-white w3-margin-bottom" id="group-management">
			<div class="w3-cell-row">
				<div class="w3-cell">
					<h2 class="w3-text-grey">Account Settings</h2>
				</div>
				<div class="w3-cell w3-cell-bottom w3-right w3-margin-right">
					
				</div>
			</div>
			<div class="w3-cell-row">
				<div class="w3-row-padding">
					<div class="w3-third w3-small">
						<input type="hidden" name="username" value="${principal}" />
						<input type="password" placeholder="Enter new Password" name="new-password" class="w3-input w3-margin-bottom"/>
						<input type="password" placeholder="Reenter new password" name="new-password-confirm" class="w3-input w3-margin-bottom"/>
					</div>
					
				</div>
				<div class="w3-container">
					<div class="w3-third">
						<button id="change-password-btn" class="w3-button w3-ripple w3-round w3-teal w3-small w3-padding-small w3-margin-bottom">Change password</button>
					</div>
				</div>
			</div>
		</div>
	</div>
  <!-- End Grid -->
  </div>
  
  <!-- End Page Container -->
</div>

<footer class="w3-container w3-teal w3-center w3-margin-top">
  <p>RALF - Risk Analysis Platform</p>
  <p></p>
</footer>


</body></html>