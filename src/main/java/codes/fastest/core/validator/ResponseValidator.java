package codes.fastest.core.validator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.andrejs.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;

import codes.fastest.core.FastestResponse;

public class ResponseValidator {

	private static ResponseValidator INSTANCE = null;
	
	private ScriptEngine engine;
	
	private static Logger log = LoggerFactory.getLogger(ResponseValidator.class);
	
	private ResponseValidator() {
		
		ScriptEngineManager factory = new ScriptEngineManager();
	    engine = factory.getEngineByName("nashorn");
		
	}
	
	public static ResponseValidator get() {
		
		if(INSTANCE == null) INSTANCE = new ResponseValidator();
		
		return INSTANCE;
	}
	
	public static FastestResult validate(FastestResponse fastestResponse, Config out) {
		
		get();
		
		FastestResult result = new FastestResult();
		
		if(fastestResponse.isJson()) {
		
			Json json = Json.of.string(fastestResponse.getBody());
		
			INSTANCE.engine.put("response", json);					
			
			log.info("RULES =============== =============== ===============");
			
			boolean hasStatus = out.hasPath("status");
			if(hasStatus) {
				int status = out.getInt("status");
				if(fastestResponse.getStatus() != status) {
					result.addError(String.format("Status Received: %s different than expected: %s", fastestResponse.getStatus(), status));
				}
			}
			
			boolean hasHeaders = out.hasPath("headers");
			if(hasHeaders) {
				
				ConfigObject headers = out.getObject("headers");
				System.out.println();
				Set<Entry<String, ConfigValue>> entrySet = headers.entrySet();

				for(Entry<String, ConfigValue> entry : entrySet) {
					String expectedHeaderName = entry.getKey();
					List<String> responseHeader = fastestResponse.getHeaders().get(entry.getKey());
					if(responseHeader == null || responseHeader.size() == 0) {
						String error = String.format("Expected header %s not found in response.", expectedHeaderName);
						result.addError(error);
					} else {

						ConfigValue configValue = entry.getValue();

						String value = configValue.render();

						System.out.println("VALUE: " + value);

					}
				}
			}

			boolean hasBody = out.hasPath("body");
			if(hasBody) {

				Set<Entry<String, ConfigValue>> entrySetOut = out.getObject("body").entrySet();
				for(Entry<String, ConfigValue> entryOut : entrySetOut) {

					System.out.println(entryOut.getKey() + " -> " + entryOut.getValue().unwrapped());

					//INSTANCE.engine.eval("response");

				}
			}

			log.info("");
			log.info("RESPONSE HEADER =============== =============== ===============");
			Map<String, List<String>> headers = fastestResponse.getHeaders();
			Set<Entry<String, List<String>>> entrySetHeaders = headers.entrySet();
			for(Entry<String, List<String>> entry : entrySetHeaders) {
				System.out.println(entry.getKey() + " -> " + entry.getValue());
			}

			log.info("RESPONSE BODY   =============== =============== ===============");
			Set<Entry<String, Object>> entrySet = json.entrySet();
			for(Entry<String, Object> entry : entrySet) {
				System.out.println(entry.getKey() + " -> " + entry.getValue());
			}

			log.info("");
			log.info("RESULTS         =============== =============== ===============");
			for(String error : result.getErrors()) {
				System.out.println(error);
			}
			
		}
		
		return result;
	}
	
	
}
