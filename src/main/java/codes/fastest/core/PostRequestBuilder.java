package codes.fastest.core;

import static codes.fastest.util.Const.BODY;
import static codes.fastest.util.Const.ENDPOINT;
import static codes.fastest.util.Const.PATTERN_APP_JSON;

import java.util.stream.Collectors;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.typesafe.config.ConfigObject;

public class PostRequestBuilder extends RequestBuilder {

	private HttpRequestWithBody request;

	public PostRequestBuilder(ExecutionConfiguration execConfig) {
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