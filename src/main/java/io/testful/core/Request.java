package io.testful.core;

import java.util.Map.Entry;

import static io.testful.core.util.Const.ACCEPT;
import static io.testful.core.util.Const.CONTENT_TYPE;
import static io.testful.core.util.Const.GET;
import static io.testful.core.util.Const.HEADERS;
import static io.testful.core.util.Const.METHOD;
import static io.testful.core.util.Const.PATTERN_APP_JSON;
import static io.testful.core.util.Const.POST;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;

public abstract class Request {

	
	protected ExecutionConfiguration execConf;
	
	protected String contentType = null;

	protected String accept = null;
	

	private static final Logger log = LoggerFactory.getLogger(Request.class);
	
	public Request(ExecutionConfiguration execConf) {

		this.execConf = execConf;
	}

	public void processHeaders() {
	
		boolean hasHeader = execConf.getRequestSpecification().hasPath(HEADERS);
		
		if(hasHeader) {
			
			ConfigObject header = execConf.getRequestSpecification().getObject(HEADERS);
			
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
	
	public static class Builder {
		

		public static Request fromExecConfig(ExecutionConfiguration execConfig) {
			
			Request req = null;
			
			String method = execConfig.getRequestSpecification().getString(METHOD);
			
			// TODO: needs factory
			if(POST.equals(method)) req = new TestfulPostRequest(execConfig);
			if(GET.equals(method)) req = new TestfulGetRequest(execConfig);
			
			req.processHeaders();
			req.processBody();
			
			return req;
		}

	

		
	} 
	

	
	
	
	
}
