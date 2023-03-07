package com.weather.forecaster.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Sarthak
 * 
 * Model class for ForecastUserOutput having attributes to be used by client for displaying result
 *
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ForecastUserOutput {
	String date;
	String maxTemp;
	String minTemp;
	List<String> messagesList;
	List<String> weatherList;
}
