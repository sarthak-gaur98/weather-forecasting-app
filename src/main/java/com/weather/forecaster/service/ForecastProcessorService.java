package com.weather.forecaster.service;

import com.weather.forecaster.model.ForecastInput;
import com.weather.forecaster.model.UserOutput;

/**
 * 
 * @author Sarthak
 * 
 * Interface having methods to generate output based on user input
 *
 */
public interface ForecastProcessorService {
	public UserOutput getForecastOutput(ForecastInput input);
}
