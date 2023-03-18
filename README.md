# Weather Forecasting Application
Weather forecasting application in spring boot

## Requirement Module : https://docs.google.com/document/d/1n437V2y-Oot7uWJt_ZRSF8L8dXK4hTWa/edit?usp=share_link&ouid=104365555466931079041&rtpof=true&sd=true

## Sequence Diagram :

![sdiagram](https://user-images.githubusercontent.com/43079536/226093024-5a478180-a60b-4bc9-a958-1ecd8c1dd9a9.PNG)


## Approach :

- A simple spring boot application build on RestController and JSP framework
- It starts with landing user on home page with GET request configured on "/api/forecast/home" asking for city input from user
- The user response invokes POST call on server configured at "/api/forecast/input/submit"
- The API call is made using RestTemplate whose bean is injected in service class following singleton pattern
- The API call returns success response in case the result is found otherwise throws HTTPClientException
- Succesful JSON response is converted to Java models using various model classes and processed by mapping all the entries of a given day with 3-hour interval in a Map<Integer,List> type structure since we need our output on day basis
- The max and min temperatures on a given day are calculated as well as suggestion and current weather conditions are obtained for the given day
- The appropriate response is sent to client and appropriate error is shown in case of Internal Server Error or Data not found
