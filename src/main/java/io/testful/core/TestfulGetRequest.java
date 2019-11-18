package io.testful.core;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;

public class TestfulGetRequest extends Request {

	private GetRequest request;
	
	public TestfulGetRequest(ExecutionConfiguration execConfig) {

		super(execConfig);

		String endpoint = execConf.getIn().getString("endpoint");

		request = Unirest.get(endpoint);

	}
	
	@Override
	public void processBody() {

	}

	@Override
	protected HttpRequest getRequest() {
		return request;
	}

}
