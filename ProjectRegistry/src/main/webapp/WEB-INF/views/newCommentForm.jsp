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
	<h2>New Comment Form</h2>
	<br>
	<form action="<c:url value='/comment/new/create'/>" method='POST'>
		<table>
			<tr>
				<td>Comment:</td>
				<td><textarea name='comment' value=''></textarea>
			</tr>
			<tr>
				<td><input name="submit" type="submit" value="submit"/></td>
			</tr>
		</table>
	</form>
</body>
</html>