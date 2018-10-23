<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ProjectRegistry.com</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<h2>Home page</h2>
	<br>
	<a href="<c:url value='idea/new/form' />">Post Idea</a>
	<br>
	<table>
		<tr>
			<td>Date</td>
			<td>Title</td>
			<td>Poster</td>
			<td>Votes</td>
		</tr>
		<c:forEach items="${ideas}" var="idea">
			<tr>
				<td>${idea.getDatePosted().getTime()}</td>
				<td><a href="<c:url value='idea?id=${idea.getId()}' />">${idea.getTitle()}</a></td>
				<td><a href="<c:url value='user/profile?username=${idea.getPoster().getUsername()}' />">${idea.getPoster().getUsername()}</a></td>
				<td>${idea.numVotes()}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>