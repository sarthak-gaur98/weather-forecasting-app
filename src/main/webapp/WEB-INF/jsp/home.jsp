<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Weather Forecast Home page</h1>
<form method="post" action="/api/forecast/input/submit">
	<h3>Enter city name</h3>
	<input type="text" name="city" placeholder="Enter city name to view forecast">
	<input type="submit" value="Search">
</form>
</body>
</html>