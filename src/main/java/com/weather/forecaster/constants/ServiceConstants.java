package com.weather.forecaster.constants;

/**
 * 
 * @author Sarthak
 * 
 * Class having constants to be used throughout the service
 *
 */
public final class ServiceConstants {
	private ServiceConstants() {}
	public static final String API_URL_PREFIX = "https://api.openweathermap.org/data/2.5/forecast?q=";
	public static final String API_URL_SUFFIX = "&appid=4843a0b8480fc9e0971388c1adfbb02e&amp;cnt=10";
	public static final Long TIMESTAMP_MULTIPLIER = 1000L;
	public static final Long DAYS_THRESHOLD = 3L;
	public static final Long MILLISECONDS_TO_DAYS_FACTOR = 1000*24*60*60L;
	public static final Double TEMPERATURE_THRESHOLD = 313.5;
	public static final Double WIND_SPEED_THRESHOLD = 10.5;
}
