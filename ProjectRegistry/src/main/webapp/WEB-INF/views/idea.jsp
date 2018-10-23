<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<td><a href="<c:url value='user/profile?username=${idea.getPoster().getUsername()}' />">${idea.getPoster().getUsername()}</a></td>
		</tr>
		<tr>
			<td>Date posted: </td>
			<td><fmt:formatDate type="date" value="${idea.getDatePosted().getTime()}" /></td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr>
			<td>${idea.getDescription()}</td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr>
			<td>
				<form action="<c:url value='/idea/new/vote?upVote=true'/>" method='POST'>
				<input name="submit" type="submit" value="UpVote"/></form>
			</td>
			<td>Votes: ${idea.voteCount()}</td>
			<td>
				<form action="<c:url value='/idea/new/vote?upVote=false'/>" method='POST'>
				<input name="submit" type="submit" value="DownVote"/></form>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td><h3>Developments</h3></td>
			<td><a href="<c:url value='development/new/form' />">New Development</a></td>
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
				<td><a href="<c:url value='user/profile?username=${development.getDeveloper().getUsername()}' />">${development.getDeveloper().getUsername()}</a></td>
				<td>${development.getLink()}</td>
				<td>
					<table>
						<tr>
							<td>
								<form action="<c:url value='/development/new/vote?upVote=true&developmentId=${development.getId()}'/>" method='POST'>
								<input name="submit" type="submit" value="UpVote"/></form>
							</td>
						</tr>
						<tr>
							<td>
								${development.voteCount()}
							</td>
						</tr>
						<tr>
							<td>
								<form action="<c:url value='/development/new/vote?upVote=false&developmentId=${development.getId()}'/>" method='POST'>
								<input name="submit" type="submit" value="DownVote"/></form>
							</td>
						</tr>
					</table>
				</td>
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
				<td><a href="<c:url value='user/profile?username=${comment.getCommenter().getUsername()}' />">${comment.getCommenter().getUsername()}</a></td>
				<td><fmt:formatDate type="date" value="${comment.getDateTimePosted().getTime()}" /></td>
				<td>${comment.getComment()}</td>
				<td>
					<table>
						<tr>
							<td>
								<form action="<c:url value='/comment/new/vote?upVote=true&commentId=${comment.getId()}'/>" method='POST'>
								<input name="submit" type="submit" value="UpVote"/></form>
							</td>
						</tr>
						<tr>
							<td>
								${comment.voteCount()}
							</td>
						</tr>
						<tr>
							<td>
								<form action="<c:url value='/comment/new/vote?upVote=false&commentId=${comment.getId()}'/>" method='POST'>
								<input name="submit" type="submit" value="DownVote"/></form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>