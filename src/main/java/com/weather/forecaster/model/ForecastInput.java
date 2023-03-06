package com.weather.forecaster.model;

import lombok.Builder;
import lombok.Getter;

/**
 * 
 * @author Sarthak
 * 
 * Model class for ForecastInput to be fed in API call 
 *
 */
@Builder
@Getter
public class ForecastInput {
	String city;
}
