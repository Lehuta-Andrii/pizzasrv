<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pizza Service</title>
</head>
<body>

	<form:form method="POST" modelAttribute="pizzaAndAmmount"
		action="${pageContext.request.contextPath}/order">

		<c:forEach items="${pizzaAndAmmount.pizzas}" var="pizza">

		${pizza.key}
        <form:input path="pizzas[${pizza.key}]" />
			<br>


		</c:forEach>
		<input value="Order" type="submit">
	</form:form>






</body>
</html>