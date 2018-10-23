<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Form</title>
</head>
<body onload='document.loginForm.username.focus();'>
	<jsp:include page="header.jsp" />
	<br>
	<h2>Login</h2>
	<br>
	<div id="login-box">
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		
		<form name='loginForm' action="<c:url value='j_spring_security_check'/>" method='POST'>
			<table>
				<tr>
					<td>Username:</td>
					<td><input type='text' name='username' value=''></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='text' name='password' value=''></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit" value="submit"/></td>
				</tr>
			</table>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</div>
</body>
</html>