FROM openjdk:17
EXPOSE 8080
ADD target/weather-forecast-image.jar weather-forecast-image.jar
ENTRYPOINT ["java","-jar","/weather-forecast-image.jar"]