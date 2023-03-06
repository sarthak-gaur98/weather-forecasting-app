<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.weather.forecaster.model.ResponseCode" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="/api/forecast/input/submit">
	<h3>Search other cities</h3>
	<input type="text" name="city" placeholder="${city}">
	<input type="submit" value="Search">
</form>
<c:if test="${forecastOutput.code eq ResponseCode.INTERNAL_SERVER_ERROR}">
	<p>Internal Server Error: Check logs</p>
</c:if>
<c:if test="${forecastOutput.code eq ResponseCode.DATA_NOT_FOUND}">
	<p>Data not found for given city: ${city}. Please enter a valid input</p>
</c:if> 
</body>
</html>