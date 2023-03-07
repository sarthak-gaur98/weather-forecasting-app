<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Forecast Page</title>
<style>
	.card {
	  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
	  transition: 0.3s;
	  width: 30%;
	}
	
	.card:hover {
	  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
	}
	
	.container {
	  padding: 2px 16px;
	}
	
	input[type=text], select {
	  padding: 12px 20px;
	  margin: 8px 0;
	  display: inline-block;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	  box-sizing: border-box;
	}
	
	input[type=submit] {
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
	
	h2 {
	  color: green;
	}
	
	.weatherHeaders {
	  color: blue;
	}
	
	li {
	  color: yellow
	}
</style>
</head>
<body>
<form method="post" action="/api/forecast/input/submit">
	<h3>Search other cities</h3>
	<input type="text" name="city" placeholder="${city}">
	<input type="submit" value="Search">
</form>
<br>
<h2> Displaying results for : ${city}</h2>
<c:forEach items="${forecastOutput.forecastUserOutputList}" var="forecast">
	<div class="mainBlock">
		<div class="card">
		  <div>
		  	<h3>Weather report for : ${forecast.date}</h3>
		  </div>
		  <div class="container">
		    <p class="weatherHeaders">Max Temp: ${forecast.maxTemp} Kelvin</p>
		    <p class="weatherHeaders">Min Temp: ${forecast.minTemp} Kelvin</p>
		    <c:if test="${fn:length(forecast.weatherList) > 0}">
		    	<p>Speculated weather conditions: </p>
		    	<ul>
			    	<c:forEach items="${forecast.weatherList}" var="condition">
				    	<li>${condition}</li>
			    	</c:forEach>
		    	</ul>
		    </c:if>
		     <c:if test="${fn:length(forecast.messagesList) > 0}">
		    	Tips for current weather: 
		    	<ul>
			    	<c:forEach items="${forecast.messagesList}" var="message">
				    	<li>${message}</li>
			    	</c:forEach>
		    	</ul>
		    </c:if>    
		  </div>
		</div>
	</div>
</c:forEach>
</body>
</html>