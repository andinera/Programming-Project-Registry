<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<td align=center>Date</td>
			<td align=center>Title</td>
			<td align=center>Votes</td>
		</tr>
		<c:forEach items="${ideas}" var="idea">
			<tr>
				<td><fmt:formatDate type="date" value="${idea.getDatePosted().getTime()}" /></td>
				<td><a href="<c:url value='/idea?id=${idea.getId()}' />">${idea.getTitle()}</a></td>
				<td align=center>${idea.voteCount()}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan=3 align=center>
				<a href="<c:url value='/user/profile?userPageOfIdeas=${ideaPage-1}' />">&laquo;</a>
				<c:forEach begin="1" end="${numIdeaPages}" var="page">
				    <a href="<c:url value='/user/profile?userPageOfIdeas=${page}' />">${page}</a>
				</c:forEach>
				<a href="<c:url value='/user/profile?userPageOfIdeas=${ideaPage+1}' />">&raquo;</a>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td><h3>Developments</h3></td>
		</tr>
	</table>
	<table>
		<tr>
			<td align=center>Idea</td>
			<td align=center>Link</td>
			<td align=center>Votes</td>
		</tr>
		<c:forEach items="${developments}" var="development">
			<tr>
				<td><a href="<c:url value='/idea?id=${development.getIdea().getId()}' />">${development.getIdea().getTitle()}</a></td>
				<td>${development.getLink()}</td>
				<td align=center>${development.voteCount()}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan=3 align=center>
				<a href="<c:url value='/user/profile?userPageOfDevelopments=${developmentPage-1}' />">&laquo;</a>
				<c:forEach begin="1" end="${numDevelopmentPages}" var="page">
				    <a href="<c:url value='/user/profile?userPageOfDevelopments=${page}' />">${page}</a>
				</c:forEach>
				<a href="<c:url value='/user/profile?userPageOfDevelopments=${developmentPage+1}' />">&raquo;</a>
			</td>
		</tr>
	</table>
</body>
</html>