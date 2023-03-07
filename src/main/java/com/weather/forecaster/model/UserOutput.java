package com.weather.forecaster.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Sarthak
 * 
 * Model class for UserOutput to be sent to client for displaying output 
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOutput {
	List<ForecastUserOutput> forecastUserOutputList;
	ResponseCode code;
	String messageString;
	
}
