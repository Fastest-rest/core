package io.testful.core;

import static io.testful.core.util.Const.BODY;
import static io.testful.core.util.Const.ENDPOINT;
import static io.testful.core.util.Const.PATTERN_APP_JSON;

import java.util.stream.Collectors;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.typesafe.config.ConfigObject;

public class PostRequest extends Request {

	private HttpRequestWithBody request;

	public PostRequest(ExecutionConfiguration execConfig) {
		super(execConfig);
		
		String endpoint = execConf.getIn().getString(ENDPOINT);
		request = Unirest.post(endpoint);
	}
	
	@Override
	public void processBody() {
		
		boolean hasBody = execConf.getIn().hasPath(BODY);
		
		if(hasBody) {
			
			ConfigObject bodyObject = execConf.getIn().getObject(BODY);
			
			if(hasContentType() && PATTERN_APP_JSON.matcher(getContentType()).find()) {
				request.body(bodyObject.render());
			} else {
				
				String res = bodyObject.entrySet().stream()
								.map(entry -> entry.getKey() + "=" + entry.getValue().unwrapped())
								.collect(Collectors.joining("&"));
	
				request.body(res);
			}
			
			
		}
		
	}

	@Override
	protected HttpRequest getRequest() {
		return request;
	}	
	
}