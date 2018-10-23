<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Start Development</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<h2>New Development Form</h2>
	<br>
	<form action="<c:url value='/development/new/create'/>" method='POST'>
		<table>
			<tr>
				<td>Link:</td>
				<td><input type='text' name='link' value=''></td>
			</tr>
			<tr>
				<td><input name="submit" type="submit" value="submit"/></td>
			</tr>
		</table>
	</form>
</body>
</html>