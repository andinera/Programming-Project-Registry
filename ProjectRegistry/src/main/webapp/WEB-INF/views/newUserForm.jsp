<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New User</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<br>
	<h2>New User Form</h2>
	<br>
	<br>
	
	<form action="<c:url value='/user/new/create'/>" method='POST'>
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
				<td><input name="submit" type="submit" value="submit"/></td>
			</tr>
		</table>
	</form>
</body>
</html>