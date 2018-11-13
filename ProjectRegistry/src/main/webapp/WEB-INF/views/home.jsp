<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<table>
		<tr>
			<td><a href="<c:url value='/idea/new/form' />">Post Idea</a></td>
		</tr>
	</table>
	<form action="<c:url value='/home/filter'/>" method='POST'>
		<table>
			<tr>
				<td>Start date</td>
				<td>Stop date</td>
			</tr>
			<tr>
				<td><input type="date" name='startDate'></td>
				<td><input type="date" name='stopDate'></td>
				<td><input name="submit" type="submit" value="Filter"/></td>
			</tr>
		</table>
	</form>
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
				<td><fmt:formatDate type="date" value="${idea.getDatePosted().getTime()}" /></td>
				<td><a href="<c:url value='/idea?id=${idea.getId()}' />">${idea.getTitle()}</a></td>
				<td><a href="<c:url value='/user/profile?username=${idea.getPoster().getUsername()}' />">${idea.getPoster().getUsername()}</a></td>
				<td>${idea.voteCount()}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan=3 align=center>
				<a href="<c:url value='/home/proxy?homePage=${homePage-1}' />">&laquo;</a>
				<c:forEach begin="1" end="${numHomePages}" var="page">
				    <a href="<c:url value='/home/proxy?homePage=${page}' />">${page}</a>
				</c:forEach>
				<a href="<c:url value='/home/proxy?homePage=${homePage+1}' />">&raquo;</a>
			</td>
		</tr>
	</table>
</body>
</html>