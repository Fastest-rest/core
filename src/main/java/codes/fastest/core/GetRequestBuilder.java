package codes.fastest.core;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;

public class GetRequestBuilder extends RequestBuilder {

	private GetRequest request;
	
	public GetRequestBuilder(ExecutionConfiguration execConfig) {
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
