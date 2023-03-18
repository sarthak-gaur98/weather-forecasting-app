package com.weather.forecaster.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.weather.forecaster.model.ForecastDailyParameters;
import com.weather.forecaster.model.ForecastInput;
import com.weather.forecaster.model.ForecastOutput;
import com.weather.forecaster.model.ForecastUserOutput;
import com.weather.forecaster.model.ResponseCode;
import com.weather.forecaster.model.UserOutput;
import com.weather.forecaster.service.ForecastProcessorService;
import static com.weather.forecaster.constants.ServiceConstants.API_URL_PREFIX;
import static com.weather.forecaster.constants.ServiceConstants.TIMESTAMP_MULTIPLIER;
import static com.weather.forecaster.constants.ServiceConstants.DAYS_THRESHOLD;
import static com.weather.forecaster.constants.ServiceConstants.MILLISECONDS_TO_DAYS_FACTOR;
import static com.weather.forecaster.constants.ServiceConstants.TEMPERATURE_THRESHOLD;
import static com.weather.forecaster.constants.ServiceConstants.WIND_SPEED_THRESHOLD;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Sarthak
 * 
 * Implementation for ForecastProcessorService
 *
 */

@Service
@PropertySource("classpath:keys.properties")
public class ForecastProcessorServiceImpl implements ForecastProcessorService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${API_KEY}")
	private String apiKey;
	
	/**
	 * Fetch output from API call converting JSON object to ForecastOutput object
	 * and return required output based on API call response
	 */
	@Cacheable("city-input")
	@Override
	public UserOutput getForecastOutput(ForecastInput input) {
		System.out.println("API Key: "+apiKey);
		try {
			String city = input.getCity();
			ForecastOutput forecastQueryOutput = restTemplate.getForObject(String.format("%s%s%s", API_URL_PREFIX, city, apiKey), ForecastOutput.class);
			System.out.println("Data: " + forecastQueryOutput);
			Map<Integer, List<ForecastDailyParameters>> forecastOutputMap = convertOutputToMap(forecastQueryOutput);
			return generateOutput(forecastOutputMap);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return UserOutput.builder()
					.code(ResponseCode.DATA_NOT_FOUND)
					.messageString(ResponseCode.DATA_NOT_FOUND.getCodeString())
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return UserOutput.builder()
					.code(ResponseCode.INTERNAL_SERVER_ERROR)
					.messageString(ResponseCode.INTERNAL_SERVER_ERROR.getCodeString())
					.build();
		}
	}

	/**
	 * 
	 * @param forecastQueryOutput
	 * @return Map<Integer, List<ForecastDailyParameters>>
	 * @throws RuntimeException
	 * 
	 * Creates and returns a mapping of day from Date to the List of ForecastDailyParameters since
	 * we want to show cumulative data of a day based on data for 3-hour intervals
	 */
	private Map<Integer, List<ForecastDailyParameters>> convertOutputToMap(ForecastOutput forecastQueryOutput) throws RuntimeException {
		try {
			Map<Integer, List<ForecastDailyParameters>> mapOutput = new HashMap<>();
			for (ForecastDailyParameters parameters : forecastQueryOutput.getList()) {
				Long dateTimestamp = parameters.getDt();
				Date date = new Date(dateTimestamp*TIMESTAMP_MULTIPLIER);
				if (mapOutput.containsKey(date.getDay())) {
					List<ForecastDailyParameters> parametersList = mapOutput.get(date.getDay());
					parametersList.add(parameters);
					mapOutput.put(date.getDay(), parametersList);
				} else {
					List<ForecastDailyParameters> parametersList = new ArrayList<>();
					parametersList.add(parameters);
					mapOutput.put(date.getDay(), parametersList);
				}
			}
			return mapOutput;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @param forecastOutputMap
	 * @return UserOutput
	 * @throws RuntimeException
	 * 
	 * Generates output based on temperatures at different times of day and weather conditions
	 * 1) Calculates maximum and minimum temperature on a given day
	 * 2) Based on different weather conditions generates suggestions
	 */
	private UserOutput generateOutput(Map<Integer, List<ForecastDailyParameters>> forecastOutputMap) throws RuntimeException {
		try {
			List<ForecastUserOutput>  result = new ArrayList<>(); 
			for (Map.Entry<Integer, List<ForecastDailyParameters>> entry : forecastOutputMap.entrySet()) {
				Long differenceInDays = getDifferenceInDays(entry.getValue().get(0));
				
				if (differenceInDays < DAYS_THRESHOLD) {
					ForecastUserOutput forecastUserOutput = new ForecastUserOutput();
					Set<String> weatherMessageSet = new HashSet<>();
					Set<String> weatherConditionSet = new HashSet<>();
					forecastUserOutput.setDate(new Date(entry.getValue().get(0).getDt()*TIMESTAMP_MULTIPLIER).toString());
					Double maxTemp = 0.0, minTemp = 99999.0;
					
					for(ForecastDailyParameters parameters : entry.getValue()) {
						maxTemp = Math.max(maxTemp, parameters.getMain().getTemp_max());
						minTemp = Math.min(minTemp, parameters.getMain().getTemp_min());
						updateListParameters(parameters, weatherConditionSet, weatherMessageSet);
					}
					
					forecastUserOutput.setMaxTemp(maxTemp.toString());
					forecastUserOutput.setMinTemp(minTemp.toString());
					
					if (maxTemp > TEMPERATURE_THRESHOLD) {
						weatherMessageSet.add("Use lotion Sunny outside");
					}
					
					forecastUserOutput.setMessagesList(new ArrayList<String>(weatherMessageSet));
					forecastUserOutput.setWeatherList(new ArrayList<String>(weatherConditionSet));
					
					result.add(forecastUserOutput);
				}
			}
			return UserOutput.builder()
					.code(ResponseCode.SUCCESSFUL)
					.forecastUserOutputList(result)
					.messageString(ResponseCode.SUCCESSFUL.getCodeString())
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private Long getDifferenceInDays(ForecastDailyParameters forecastDailyParameters) {
		return (forecastDailyParameters.getDt()*TIMESTAMP_MULTIPLIER - System.currentTimeMillis())/(MILLISECONDS_TO_DAYS_FACTOR);
	}
	
	private void updateListParameters(ForecastDailyParameters parameters, Set<String> weatherMessageSet, Set<String> weatherConditionSet) {
		weatherConditionSet.add(parameters.getWeather().get(0).getDescription());
		if (parameters.getWind().getSpeed() >= WIND_SPEED_THRESHOLD) {
			weatherMessageSet.add("It's too windy outside, watch out!");
		}
		switch (parameters.getWeather().get(0).getMain().toLowerCase()) {
			case "extreme": {
				weatherMessageSet.add("Storm is brewing! Avoid going outside");
				break;
			}
			case "rain": {
				weatherMessageSet.add("Carry an umbrella");
				break;
			}
			case "light rain": {
				weatherMessageSet.add("Carry an umbrella");
				break;
			}
			case "snow": {
				weatherMessageSet.add("Wear full clothes it is going to snow");
				break;
			}
			case "light snow": {
				weatherMessageSet.add("Wear full clothes it is going to snow");
				break;
			}
		}
	}

}
