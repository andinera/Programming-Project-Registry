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
	<br>
	<c:forEach items="${users}" var="user">
		${user.getUsername()}<br>
	</c:forEach>

</body>
</html>