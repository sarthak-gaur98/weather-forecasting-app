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
	  width: 40%;
	}
	
	.card:hover {
	  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
	}
	
	.container {
	  padding: 2px 16px;
	}
	
	.mainBlock {
		display: inline-block;
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
		  	<h2>Weather report for : ${forecast.date}</h2>
		  </div>
		  <div class="container">
		    <p>Max Temp: ${forecast.maxTemp} Kelvin</p>
		    <p>Min Temp: ${forecast.minTemp} Kelvin</p>
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