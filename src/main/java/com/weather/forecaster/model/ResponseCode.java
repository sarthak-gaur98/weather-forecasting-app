package com.weather.forecaster.model;

/**
 * 
 * @author Sarthak
 * 
 * Enum having response codes and corresponding messages for server-client interactions
 *
 */
public enum ResponseCode {
	DATA_NOT_FOUND("The data could not be found for given request"),
	INTERNAL_SERVER_ERROR("Internal Server Error"),
	SUCCESSFUL("Successful");
	
	private String codeString;
	
	private ResponseCode(String codeString) {
		this.codeString =  codeString;
	}
	
	public String getCodeString() {
		return this.codeString;
	}
}
