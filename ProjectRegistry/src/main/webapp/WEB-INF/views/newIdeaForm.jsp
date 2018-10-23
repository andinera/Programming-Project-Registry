<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Post Idea</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<h2>New Idea Form</h2>
	<br>
	<form action="<c:url value='/idea/new/create'/>" method='POST'>
		<table>
			<tr>
				<td>Title:</td>
				<td><input type='text' name='title' value=''></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><textarea name='description' value=''></textarea>
			</tr>
			<tr>
				<td colspan='1'><input name="submit" type="submit" value="submit"/></td>
			</tr>
		</table>
	</form>
</body>
</html>