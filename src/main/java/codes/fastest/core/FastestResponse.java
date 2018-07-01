package codes.fastest.core;

import java.util.List;
import java.util.Map;

import static codes.fastest.core.Const.*;

public class FastestResponse {

	private int status;
	
	private String statusText;
	
	private Map<String, List<String>> headers;
	
	private String body;
	
	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	
	public boolean hasContentType() {
		return headers != null && headers.get(CONTENT_TYPE) != null;
	}
	
	public boolean isJson() {
		return hasContentType() && PATTERN_APP_JSON.matcher(headers.get(CONTENT_TYPE).get(0)).find();
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public int getStatus() {
		return status;
	}
	
}
