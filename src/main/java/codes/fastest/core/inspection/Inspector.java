package codes.fastest.core.inspection;

import java.util.List;
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

public class Inspector {

	private static Inspector INSTANCE = null;
	
	private ScriptEngine engine;
	
	private static Logger log = LoggerFactory.getLogger(Inspector.class);
	
	private Inspector() {
		
		ScriptEngineManager factory = new ScriptEngineManager();
	    engine = factory.getEngineByName("nashorn");
		
	}
	
	public static Inspector get() {
		
		if(INSTANCE == null) INSTANCE = new Inspector();
		
		return INSTANCE;
	}
	
	public static FastestResult inspect(FastestResponse fastestResponse, Config out) {
		
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
				Set<Entry<String, ConfigValue>> entrySet = headers.entrySet();
				for(Entry<String, ConfigValue> entry : entrySet) {
					String expectedHeaderName = entry.getKey();
					List<String> responseHeader = fastestResponse.getHeaders().get(entry.getKey());
					if(responseHeader == null || responseHeader.size() == 0) {
						result.addError(String.format("Expected header %s not found in response.", expectedHeaderName));
					} else {
						
						ConfigValue value = entry.getValue();
						
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
			log.info("RESPONSE =============== =============== ===============");
			Set<Entry<String, Object>> entrySet = json.entrySet();
			for(Entry<String, Object> entry : entrySet) {
				System.out.println(entry.getKey() + " -> " + entry.getValue());
			}
			
			log.info("");
			log.info("RESULTS =============== =============== ===============");
			for(String error : result.getErrors()) {
				System.out.println(error);
			}
			
		}
		
		return result;
	}
	
	
}
