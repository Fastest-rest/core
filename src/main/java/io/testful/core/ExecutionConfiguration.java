package io.testful.core;

import com.typesafe.config.Config;

public class ExecutionConfiguration {

	private String rawEndpoint;
	
	private RequestSpecification requestSpecification; 
	
	private Config in;

	private Config out;
	
	public ExecutionConfiguration() {
	}
	
	public ExecutionConfiguration(String rawEndpoint, Config in, Config out) {
		super();
		this.rawEndpoint = rawEndpoint;
		this.in = in;
		this.out = out;
	}
	
	public String getRawEndpoint() {
		return rawEndpoint;
	}

	public void setRawEndpoint(String rawEndpoint) {
		this.rawEndpoint = rawEndpoint;
	}

	public Config getIn() {
		return in;
	}

	public void setIn(Config in) {
		this.in = in;
	}

	public Config getOut() {
		return out;
	}

	public void setOut(Config out) {
		this.out = out;
	}


	
	
	
}
