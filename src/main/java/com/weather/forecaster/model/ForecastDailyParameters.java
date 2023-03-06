package com.weather.forecaster.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
 * Model class for ForecastDailyParameters covering the required fields in JSON list
 *
 */
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForecastDailyParameters {
	long dt;
	MainParameters main;
	List<WeatherAttributes> weather;
	WindAttributes wind;
}
