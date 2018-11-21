<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
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
			<td><a href="<c:url value='/user/profile?username=${idea.getPoster().getUsername()}' />">${idea.getPoster().getUsername()}</a></td>
		</tr>
		<tr>
			<td>Date posted: </td>
			<td><fmt:formatDate type="date" value="${idea.getDatePosted().getTime()}" /></td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr>
			<td colspan=2>${idea.getDescription()}</td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr>
	</table>
	<table>
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
			<td><a href="<c:url value='/development/new/form' />">New Development</a></td>
		<tr>
	</table>
	<table>
		<tr>
			<td align=center>Developer</td>
			<td align=center>Link</td>
			<td align=center>Votes</td>
		</tr>
		<c:forEach items="${developments}" var="development">
			<tr>
				<td><a href="<c:url value='/user/profile?username=${development.getDeveloper().getUsername()}' />">${development.getDeveloper().getUsername()}</a></td>
				<td>${development.getLink()}</td>
				<td>
					<table>
						<tr>
							<td align=center>
								<form action="<c:url value='/development/new/vote?upVote=true&developmentId=${development.getId()}'/>" method='POST'>
								<input name="submit" type="submit" value="UpVote"/></form>
							</td>
						</tr>
						<tr>
							<td align=center>
								${development.voteCount()}
							</td>
						</tr>
						<tr>
							<td align=center>
								<form action="<c:url value='/development/new/vote?upVote=false&developmentId=${development.getId()}'/>" method='POST'>
								<input name="submit" type="submit" value="DownVote"/></form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan=3 align=center>
				<a href="<c:url value='/idea?ideaPageOfDevelopments=${developmentPage-1}' />">&laquo;</a>
				<c:forEach begin="1" end="${numDevelopmentPages}" var="page">
				    <a href="<c:url value='/idea?ideaPageOfDevelopments=${page}' />">${page}</a>
				</c:forEach>
				<a href="<c:url value='/idea?ideaPageOfDevelopments=${developmentPage+1}' />">&raquo;</a>
			</td>
		</tr>
	</table>
	<br>
	<table>
		<tr>
			<td><h3>Comments</h3></td>
			<td><a href="<c:url value='/comment/new/form' />">New Comment</a></td>
		<tr>
	</table>
	<table>
		<tr>
			<td align=center>Name</td>
			<td align=center>DateTime</td>
			<td align=center>Comment</td>
			<td align=center>Votes</td>
		</tr>
		<c:forEach items="${comments}" var="comment">
			<tr>
				<td><a href="<c:url value='/user/profile?username=${comment.getCommenter().getUsername()}' />">${comment.getCommenter().getUsername()}</a></td>
				<td><fmt:formatDate type="date" value="${comment.getDateTimePosted().getTime()}" /></td>
				<td>${comment.getComment()}</td>
				<td>
					<table>
						<tr>
							<td align=center>
								<form action="<c:url value='/comment/new/vote?upVote=true&commentId=${comment.getId()}'/>" method='POST'>
								<input name="submit" type="submit" value="UpVote"/></form>
							</td>
						</tr>
						<tr>
							<td align=center>
								${comment.voteCount()}
							</td>
						</tr>
						<tr>
							<td align=center>
								<form action="<c:url value='/comment/new/vote?upVote=false&commentId=${comment.getId()}'/>" method='POST'>
								<input name="submit" type="submit" value="DownVote"/></form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan=3 align=center>
				<a href="<c:url value='/idea?ideaPageOfComments=${commentPage-1}' />">&laquo;</a>
				<c:forEach begin="1" end="${numCommentPages}" var="page">
				    <a href="<c:url value='/idea?ideaPageOfComments=${page}' />">${page}</a>
				</c:forEach>
				<a href="<c:url value='/idea?ideaPageOfComments=${commentPage+1}' />">&raquo;</a>
			</td>
		</tr>
	</table>
</body>
</html>