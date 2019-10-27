package io.testful.core;

import com.typesafe.config.Config;

public class ExecutionConfiguration {

	private String rawEndpoint;
	
	private Config requestSpecification;

	private Config responseValidation;
	
	public ExecutionConfiguration() {
	}
	
	public ExecutionConfiguration(String rawEndpoint, Config in, Config responseValidation) {
		super();
		this.rawEndpoint = rawEndpoint;
		this.requestSpecification = in;
		this.responseValidation = responseValidation;
	}
	
	public String getRawEndpoint() {
		return rawEndpoint;
	}

	public void setRawEndpoint(String rawEndpoint) {
		this.rawEndpoint = rawEndpoint;
	}

	public Config getRequestSpecification() {
		return requestSpecification;
	}

	public void setRequestSpecification(Config requestSpecification) {
		this.requestSpecification = requestSpecification;
	}

	public Config getResponseValidation() {
		return responseValidation;
	}

	public void setResponseValidation(Config responseValidation) {
		this.responseValidation = responseValidation;
	}

}
