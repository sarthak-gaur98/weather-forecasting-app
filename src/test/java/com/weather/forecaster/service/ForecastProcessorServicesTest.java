package com.weather.forecaster.service;

import static com.weather.forecaster.constants.ServiceConstants.API_URL_PREFIX;
import static com.weather.forecaster.constants.ServiceConstants.API_URL_SUFFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.weather.forecaster.model.ForecastDailyParameters;
import com.weather.forecaster.model.ForecastInput;
import com.weather.forecaster.model.ForecastOutput;
import com.weather.forecaster.model.MainParameters;
import com.weather.forecaster.model.ResponseCode;
import com.weather.forecaster.model.UserOutput;
import com.weather.forecaster.model.WeatherAttributes;
import com.weather.forecaster.model.WindAttributes;
import com.weather.forecaster.service.impl.ForecastProcessorServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class ForecasterApplicationTests {

	private ForecastOutput forecastOutput = new ForecastOutput();
	
	@MockBean
	RestTemplate restTemplate;

	@Autowired
	ForecastProcessorServiceImpl forecastService;
	
	@Test
	public void testGetForecastOutput() {
		ForecastDailyParameters forecastDailyParameters = new ForecastDailyParameters();
		ForecastDailyParameters forecastDailyParameters2 = new ForecastDailyParameters();
		MainParameters mainParameters = new MainParameters();
		WeatherAttributes weatherAttributes = new WeatherAttributes();
		WindAttributes windAttributes = new WindAttributes();
		mainParameters.setTemp_max(20L);
		mainParameters.setTemp_min(10L);
		weatherAttributes.setMain("light rain");
		windAttributes.setSpeed(10.5);
		forecastDailyParameters.setDt(1678127005L);
		forecastDailyParameters.setMain(mainParameters);
		forecastDailyParameters.setWeather(Collections.singletonList(weatherAttributes));
		forecastDailyParameters.setWind(windAttributes);
		ArrayList<ForecastDailyParameters> parametersList = new ArrayList<>();
		parametersList.add(forecastDailyParameters);
		forecastDailyParameters2.setDt(1678127008L);
		forecastDailyParameters2.setMain(mainParameters);
		forecastDailyParameters2.setWeather(Collections.singletonList(weatherAttributes));
		forecastDailyParameters2.setWind(windAttributes);
		parametersList.add(forecastDailyParameters2);
		forecastOutput.setList(parametersList);
		when(restTemplate.getForObject(String.format("%s%s%s", API_URL_PREFIX, "london", API_URL_SUFFIX), ForecastOutput.class)).thenReturn(forecastOutput);
		
		UserOutput output = forecastService.getForecastOutput(ForecastInput.builder().city("london").build());
		
		assertEquals(output.getCode(), ResponseCode.SUCCESSFUL);
		assertEquals(output.getForecastUserOutputList().get(0).getMaxTemp(), "20.0");
		assertEquals(output.getForecastUserOutputList().get(0).getMinTemp(), "10.0");
		assertEquals(output.getForecastUserOutputList().get(0).getMessagesList().size(), 1);
	}
	
	@Test
	public void testGetForecastOutputWithHttpClientExcpetionError() {	
		when(restTemplate.getForObject(String.format("%s%s%s", API_URL_PREFIX, "london", API_URL_SUFFIX), ForecastOutput.class)).thenThrow(HttpClientErrorException.class);
		UserOutput output = forecastService.getForecastOutput(ForecastInput.builder().city("london").build());
		assertEquals(output.getCode(), ResponseCode.DATA_NOT_FOUND);
		
	}
	
	@Test
	public void testGetForecastOutputWithError() {	
		when(restTemplate.getForObject(String.format("%s%s%s", API_URL_PREFIX, "london", API_URL_SUFFIX), ForecastOutput.class)).thenThrow(RuntimeException.class);
		UserOutput output = forecastService.getForecastOutput(ForecastInput.builder().city("london").build());
		assertEquals(output.getCode(), ResponseCode.INTERNAL_SERVER_ERROR);		
	}
	
	@Test
	public void testGetForecastOutput2() {
		ForecastDailyParameters forecastDailyParameters = new ForecastDailyParameters();
		ForecastDailyParameters forecastDailyParameters2 = new ForecastDailyParameters();
		MainParameters mainParameters = new MainParameters();
		WeatherAttributes weatherAttributes = new WeatherAttributes();
		WindAttributes windAttributes = new WindAttributes();
		mainParameters.setTemp_max(400L);
		mainParameters.setTemp_min(10L);
		weatherAttributes.setMain("rain");
		windAttributes.setSpeed(6.5);
		forecastDailyParameters.setDt(1678127005L);
		forecastDailyParameters.setMain(mainParameters);
		forecastDailyParameters.setWeather(Collections.singletonList(weatherAttributes));
		forecastDailyParameters.setWind(windAttributes);
		ArrayList<ForecastDailyParameters> parametersList = new ArrayList<>();
		parametersList.add(forecastDailyParameters);
		forecastDailyParameters2.setDt(1678127008L);
		forecastDailyParameters2.setMain(mainParameters);
		weatherAttributes.setMain("snow");
		forecastDailyParameters2.setWeather(Collections.singletonList(weatherAttributes));
		forecastDailyParameters2.setWind(windAttributes);
		parametersList.add(forecastDailyParameters2);
		forecastOutput.setList(parametersList);
		when(restTemplate.getForObject(String.format("%s%s%s", API_URL_PREFIX, "london", API_URL_SUFFIX), ForecastOutput.class)).thenReturn(forecastOutput);
		UserOutput output = forecastService.getForecastOutput(ForecastInput.builder().city("london").build());
		
		assertEquals(output.getCode(), ResponseCode.SUCCESSFUL);
		assertEquals(output.getForecastUserOutputList().get(0).getMaxTemp(), "400.0");
		assertEquals(output.getForecastUserOutputList().get(0).getMinTemp(), "10.0");
		assertEquals(output.getForecastUserOutputList().get(0).getMessagesList().size(), 2);
	}
}
