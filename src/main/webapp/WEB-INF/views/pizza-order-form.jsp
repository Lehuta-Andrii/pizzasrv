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

	<form method="POST"  action="${pageContext.request.contextPath}/order">

		<c:forEach items="${pizzasList}" var="pizza">

		${pizza}
        <input name="pizzas[${pizza.id}]" value = "0" />
			<br>


		</c:forEach>
		<input value="Order" type="submit">
	</form>






</body>
</html>