package codes.fastest.core;

import java.util.regex.Pattern;

public class Const {

	public static final String APP_JSON = "application/json";
	public static final Pattern PATTERN_APP_JSON = Pattern.compile(APP_JSON);

	public static final String GET     = "GET";
	public static final String POST    = "POST";
	public static final String DELETE  = "DELETE";
	public static final String PUT     = "PUT";
	public static final String OPTIONS = "OPTIONS";
	public static final String HEAD    = "HEAD";
	public static final String PATCH   = "PATCH";
	
	public static final String IN   = "IN"; 
	public static final String OUT  = "OUT"; 
	public static final String FLOW = "FLOW";
	
	public static final String HEADERS  = "headers";
	public static final String BODY     = "body";
	public static final String ENDPOINT = "endpoint";
	public static final String METHOD   = "method";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT   = "Accept";
	
}
