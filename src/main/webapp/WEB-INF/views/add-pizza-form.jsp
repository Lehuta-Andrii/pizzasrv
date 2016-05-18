<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pizza Service</title>
</head>
<body>

	<form action="add" method="post">
		<br>Name:<input type="text" name="name"> <br>Type: <select
			name="pizzaType">
			<option value="MEAT">Meat</option>
			<option value="SEA">Sea</option>
			<option value="VEGETARIAN">Vegetarian</option>
		</select> <br>Price:<input type="text" name="price"> <br> <input
			type="submit" value="addPizza">
	</form>


</body>
</html>