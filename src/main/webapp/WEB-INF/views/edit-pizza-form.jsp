<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pizza Service</title>
</head>
<body>


<form:form method="POST" commandName="pizza" action="${pageContext.request.contextPath}/pizzas/edit/${pizza.id}">

		Price:
		<form:input path="price"></form:input>
		<br>
		Name:
		<form:input path="name"></form:input>
		<br>
		Type:
		<form:select path="pizzaType" items="${types}"> </form:select>
		<br>
	
		<input value="Edit" type="submit">
		
	
</form:form>



</body>
</html>