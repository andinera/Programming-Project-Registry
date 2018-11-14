<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Search</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<h2>Searched: ${keyword}</h2>
	<br>
	<table>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>
					<a href="<c:url value='/user/profile?username=${user.getUsername()}' />">${user.getUsername()}</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan=3 align=center>
				<a href="<c:url value='/user/search/proxy?userPage=${userPage-1}' />">&laquo;</a>
				<c:forEach begin="1" end="${numUserPages}" var="page">
				    <a href="<c:url value='/user/search/proxy?userPage=${page}' />">${page}</a>
				</c:forEach>
				<a href="<c:url value='/user/search/proxy?userPage=${userPage+1}' />">&raquo;</a>
			</td>
		</tr>
	</table>
</body>
</html>