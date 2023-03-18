<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.weather.forecaster.model.ResponseCode" %>

<!DOCTYPE html>
<html>
<head>
<style>
	input[type=text], select {
	  width: 100%;
	  padding: 12px 20px;
	  margin: 8px 0;
	  display: inline-block;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	  box-sizing: border-box;
	}
	
	input[type=submit] {
	  width: 100%;
	  background-color: #4CAF50;
	  color: white;
	  padding: 14px 20px;
	  margin: 8px 0;
	  border: none;
	  border-radius: 4px;
	  cursor: pointer;
	}
	
	input[type=submit]:hover {
	  background-color: #45a049;
	}
	
	form {
	  border-radius: 5px;
	  background-color: #f2f2f2;
	  padding: 20px;
	}
	
	p {
	  color: red;
	}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Search other cities</h2>
<form method="post" action="/api/forecast/input/submit">
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