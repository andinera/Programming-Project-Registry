<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>
</head>
<body>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
	

	<table>
		<tr>
			<td><a href="<c:url value="/home" />">Home</a></td>
			<td><form name='searchBox' action="<c:url value='/user/search'/>" method='GET'>
				<input type='text' name='keyword' placeholder="User Search" value=''>
			</form></td>
			<td><c:url value="/j_spring_security_logout" var="logoutUrl"/>
				<form action="${logoutUrl}" method="post" id="logoutForm">
					<input type="hidden" name="${_csrf.parameterName}" value="{_csrf.token}"/>
				</form>
				<c:choose>
					<c:when test="${pageContext.request.userPrincipal.name != null}">
						<a href="javascript:formSubmit()" > Logout</a>
							| <a href="<c:url value="/user/profile?username=${pageContext.request.userPrincipal.name}" />">${pageContext.request.userPrincipal.name}</a>
					</c:when>
					<c:otherwise>
						<a href="<c:url value="/login" />">Login</a>
						<a href="<c:url value="/user/new/form" />">Create New User</a>
					</c:otherwise>
				</c:choose></td>
		</tr>
	</table>
</body>
</html>