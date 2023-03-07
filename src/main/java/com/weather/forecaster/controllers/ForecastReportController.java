package com.weather.forecaster.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.weather.forecaster.model.ForecastInput;
import com.weather.forecaster.model.ResponseCode;
import com.weather.forecaster.model.UserOutput;
import com.weather.forecaster.service.ForecastProcessorService;

/**
 * 
 * @author Sarthak
 * 
 * Main controller class for routing requests and responses
 *
 */

@RestController
@RequestMapping("/api/forecast")
public class ForecastReportController {

	@Autowired
	ForecastProcessorService forecastProcessorService;
	
	//Display home page
	@GetMapping("/home")
	public ModelAndView getHomePage() {
		return new ModelAndView("home");
	}
	
	//Post call to generate response and route to proper view
	@PostMapping("/input/submit")
	public ModelAndView postInput(Model model, String city) {
		
		ForecastInput forecastInput = ForecastInput.builder()
				.city(city)
				.build();
		UserOutput forecastUserOutput = forecastProcessorService.getForecastOutput(forecastInput);
		
		model.addAttribute("forecastOutput", forecastUserOutput);
		model.addAttribute("city", city);
		if (forecastUserOutput.getCode() == ResponseCode.SUCCESSFUL) {
			return new ModelAndView("forecast");
		} else {
			return new ModelAndView("error");
		}
	}
}
