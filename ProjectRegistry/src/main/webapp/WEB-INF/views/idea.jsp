<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Idea</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<h2>Idea Page</h2>
	<br>
	<table>
		<tr>
			<td>Title: </td>
			<td>${idea.getTitle()}</td>
		</tr>
		<tr>
			<td>Poster: </td>
			<td>${idea.getPoster().getUsername()}</td>
		</tr>
		<tr>
			<td>Date posted: </td>
			<td>${idea.getDatePosted().getTime()}</td>
		</tr>
		<tr>
			<td>Date modified: </td>
			<td>${idea.getDateModified().getTime()}</td>
		</tr>
	</table>
	<br>
	${idea.getDescription()}
	<br>
	<table>
		<tr>
			<td><h3>Developments</h3></td>
			<td><a href="<c:url value='development/new/form' />">Share Development</a></td>
		<tr>
	</table>
	<table>
		<tr>
			<td>Developer</td>
			<td>Link</td>
			<td>Votes</td>
		</tr>
		<c:forEach items="${idea.getDevelopments()}" var="development">
			<tr>
				<td>${development.getDeveloper().getUsername()}</td>
				<td>${development.getLink()}</td>
				<td>${development.numVotes()}</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<table>
		<tr>
			<td><h3>Comments</h3></td>
			<td><a href="<c:url value='comment/new/form' />">New Comment</a></td>
		<tr>
	</table>
	<table>
		<tr>
			<td>Name</td>
			<td>DateTime</td>
			<td>Comment</td>
			<td>Votes</td>
		</tr>
		<c:forEach items="${idea.getComments()}" var="comment">
			<tr>
				<td>${comment.getCommenter().getUsername()}</td>
				<td>${comment.getDateTimePosted().getTime()}</td>
				<td>${comment.getComment()}</td>
				<td>${comment.numVotes()}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>