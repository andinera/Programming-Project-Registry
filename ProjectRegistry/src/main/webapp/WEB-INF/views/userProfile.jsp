<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<h2>${user.getUsername()}</h2>
	<br>
	<table>
		<tr>
			<td><h3>Ideas</h3></td>
		</tr>
	</table>
	<table>
		<tr>
			<td>Date</td>
			<td>Title</td>
			<td>Votes</td>
		</tr>
		<c:forEach items="${user.getIdeas()}" var="idea">
			<tr>
				<td>${idea.getDatePosted().getTime()}</td>
				<td><a href="<c:url value='/idea?id=${idea.getId()}' />">${idea.getTitle()}</a></td>
				<td>${idea.numVotes()}</td>
			</tr>
		</c:forEach>
	</table>
	<table>
		<tr>
			<td><h3>Developments</h3></td>
		</tr>
	</table>
	<table>
		<tr>
			<td>Idea</td>
			<td>Link</td>
			<td>Votes</td>
		</tr>
		<c:forEach items="${user.getDevelopments()}" var="development">
			<tr>
				<td><a href="<c:url value='/idea?id=${development.getIdea().getId()}' />">${development.getIdea().getTitle()}</a></td>
				<td>${development.getLink()}</td>
				<td>${development.numVotes()}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>