package codes.fastest.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;

import static codes.fastest.core.Const.*;

public abstract class RequestBuilder {

	protected ExecutionConfiguration execConf;
	
	protected String contentType = null;

	protected String accept = null;
	

	
	private static final Logger log = LoggerFactory.getLogger(RequestBuilder.class);
	
	public RequestBuilder(ExecutionConfiguration execConf) {
		this.execConf = execConf;
	}

	public void processHeader() {
	
		boolean hasHeader = execConf.getIn().hasPath(HEADERS);
		
		if(hasHeader) {
			
			ConfigObject header = execConf.getIn().getObject(HEADERS);
			
			Set<Entry<String, ConfigValue>> entrySet = header.entrySet();
			for(Entry<String, ConfigValue> entry : entrySet) {
				
				String value = (String) entry.getValue().unwrapped();
				
				if(entry.getKey().equalsIgnoreCase(CONTENT_TYPE)) contentType = value; 

				if(entry.getKey().equalsIgnoreCase(ACCEPT)) accept = value; 
				
				getRequest().header(entry.getKey(), value);
			}
			
		}
		
	}
	
	protected abstract HttpRequest getRequest();
	
	public abstract void processBody();

	public FastestResponse execute() {
	
		FastestResponse response = new FastestResponse();
		
		try {
			
			if(hasAccept() && PATTERN_APP_JSON.matcher(getAccept()).find()) {
			}
		
			HttpResponse<String> httpResponse = getRequest().asString();
			
			response.setStatus(httpResponse.getStatus());
			response.setStatusText(httpResponse.getStatusText());
			response.setHeaders(httpResponse.getHeaders());
			response.setBody(httpResponse.getBody());
			
			
			
		} catch (UnirestException e) {
			log.error(e.getMessage(), e);
		}
		
		return response;
	}
	
	protected String getContentType() {
		return contentType;
	}

	protected boolean hasContentType() {
		return contentType != null;
	}

	protected String getAccept() {
		return accept;
	}
	
	protected boolean hasAccept() {
		return accept != null;
	}
	
	public static RequestBuilder fromMethod(ExecutionConfiguration execConfigParam) {
		
		RequestBuilder builder = null;
		
		String method = execConfigParam.getIn().getString(METHOD);
		
		if(POST.equals(method)) builder = new PostRequestBuilder(execConfigParam);
		if(GET.equals(method)) builder = new GetRequestBuilder(execConfigParam);
		
		return builder;
	}
	
	
	
	
}