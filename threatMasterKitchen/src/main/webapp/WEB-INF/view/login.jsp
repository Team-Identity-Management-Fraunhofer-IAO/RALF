<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" type="text/css" href="styles/stylesw3c.css">
</head>
<body>
   <div id="login-container">
	   <div id="login-container-header">
		   <img src="./img/ralf_logo.png" id="ralf_logo" />
		   <h1>A&nbsp;&nbsp;u&nbsp;&nbsp;t&nbsp;&nbsp;o&nbsp;&nbsp;m&nbsp;&nbsp;a&nbsp;&nbsp;t&nbsp;&nbsp;e&nbsp;&nbsp;d&nbsp;&nbsp;&nbsp;R&nbsp;&nbsp;i&nbsp;&nbsp;s&nbsp;&nbsp;k&nbsp;&nbsp;&nbsp;A&nbsp;&nbsp;n&nbsp;&nbsp;a&nbsp;&nbsp;l&nbsp;&nbsp;y&nbsp;&nbsp;s&nbsp;&nbsp;i&nbsp;&nbsp;s&nbsp;&nbsp;&nbsp;P&nbsp;&nbsp;l&nbsp;&nbsp;a&nbsp;&nbsp;t&nbsp;&nbsp;f&nbsp;&nbsp;o&nbsp;&nbsp;r&nbsp;&nbsp;m</h1>
	   </div>
	   <div id="login-container-body">
		   <form name='f' action="login" method='POST'>
			  <label for="usrInput" id="usrInputLabel">User</label>
			  <input type='text' name='username' id="usrInput" value=''>
			  <label for="pwInput" id="pwInputLabel">Password</label>
			  <input type='password' name='password' id="pwInput" value=''>
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			  <input name="submit" type="submit" id="loginInput" value="Login" />
			  
		  </form>
	  </div>
  </div>
</body>
</html>