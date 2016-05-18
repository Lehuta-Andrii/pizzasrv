<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pizza Service</title>
</head>
<body>
		<c:forEach var="pizza" items="${pizzasList}">
			<c:out value="${pizza}" />
			<spring:url value="edit?id=${pizza.id}" var ="urlUpd" />
<%-- 			<a href="edit/${pizza.id}">Edit</a> --%>
<a href="${urlUpd}">Edit</a>
			<a href="remove/${pizza.id}">Remove</a>
			<br>
		</c:forEach>

</body>
</html>