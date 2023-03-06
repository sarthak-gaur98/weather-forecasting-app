package com.weather.forecaster.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.weather.forecaster.model.ResponseCode;
import com.weather.forecaster.model.UserOutput;
import com.weather.forecaster.service.ForecastProcessorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForecastReportControllerTest {
	
	@Autowired
	ForecastReportController forecastReportController;
	
	@MockBean
	ForecastProcessorService forecastProcessorService;
	
	@Mock
	Model model;
	
	@Test
	public void testGetController() {
		forecastReportController.getHomePage();
	}
	
	@Test
	public void testPostController() {
		when(forecastProcessorService.getForecastOutput(any())).thenReturn(UserOutput.builder().code(ResponseCode.SUCCESSFUL).build());
		
		forecastReportController.postInput(model, "test");
	}
	
	@Test
	public void testPostControllerWithFailure() {
		when(forecastProcessorService.getForecastOutput(any())).thenReturn(UserOutput.builder().code(ResponseCode.INTERNAL_SERVER_ERROR).build());
		forecastReportController.postInput(model, "test");
	}
}
