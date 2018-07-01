package codes.fastest.core.inspection;

import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import java.util.Set;

import org.andrejs.json.Json;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

import codes.fastest.core.FastestResponse;

public class Inspector {

	private static Inspector INSTANCE = null;
	
	private ScriptEngine engine;
	
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
			
			System.out.println("RULES ========================");
			Set<Entry<String, ConfigValue>> entrySetOut = out.getObject("body").entrySet();
			for(Entry<String, ConfigValue> entryOut : entrySetOut) {
				System.out.println(entryOut.getKey() + " -> " + entryOut.getValue().unwrapped());
				
				//INSTANCE.engine.eval("response");	
				
			}
			
			System.out.println("RESPONSE ========================");
			Set<Entry<String, Object>> entrySet = json.entrySet();
			for(Entry<String, Object> entry : entrySet) {
				System.out.println(entry.getKey() + " -> " + entry.getValue());
			}
			
			
			
			
			
			
		}
		
		
		return result;
	}
	
	
}
