package codes.fastest.core;

import static codes.fastest.util.Const.ENDPOINT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.andrejs.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.Unirest;

import codes.fastest.core.inspection.FastestResult;
import codes.fastest.core.inspection.Inspector;

public class Engine {

	private static final Logger log = LoggerFactory.getLogger(Engine.class);
	
	private List<ExecutionConfiguration> execConfigList = new ArrayList<>();
	
	public void registerExecConfig(ExecutionConfiguration execConfig) {
		
		log.info("Registering execution configuration for: " + execConfig.getRawEndpoint());
		
		execConfigList.add(execConfig);
		
	}
	
	public void execute() {
		
		log.trace("Executing ...");
		
		final List<FastestResult> listResults = new ArrayList<>();
		
		execConfigList.forEach(execConf -> {
			
			String endpoint = execConf.getIn().getString(ENDPOINT);
			
			log.info("executing {}", endpoint);
			
			RequestBuilder requestBuilder = RequestBuilder.fromExecConfig(execConf);
			requestBuilder.processHeaders();
			requestBuilder.processBody();

			FastestResponse response = requestBuilder.execute();
			
			FastestResult result = Inspector.inspect(response, execConf.getOut());
			
			listResults.add(result);

		});
		
		
		
	}

	public static void main(String[] args) {
		
		long ini = System.nanoTime();
		
		ScriptEngineManager factory = new ScriptEngineManager();
	    ScriptEngine engine = factory.getEngineByName("nashorn");
	    
	    long end = System.nanoTime();
	    
	    System.out.println(TimeUnit.NANOSECONDS.toMillis(end - ini) + " millis");
	    
	    Json obj = Json.of.string("{port: 80, name: 'coisas', value: null}");
	    
	    try {
		
	    	ini = System.nanoTime();
	    	
	    	engine.put("teste", "value");
	    	engine.put("source", obj);
	    	
	    	engine.eval("function isInt(val){return val != null && parseInt(val)}");
	    	engine.eval("function isFloat(val){return Number(val) === val && val % 1 !== 0}");
	    	engine.eval("function isStr(val){return val != null && typeof val == 'string'}");
	    	engine.eval("function strRange(val, min, max){return val != null && val.length > min && val.length < max}");
	    	engine.eval("function strSize(val, size){return val != null && val.length == size}");
	    	engine.eval("function notNull(val){return val != null}");
	    	engine.eval("function isNull(val){return val == null}");
	    	engine.eval("function options(val, opts){for(idx in opts){if(opts[idx] == val)return true;}return false;}");
	    	engine.eval("function regex(val, re){return val != null && re.exec(val) != null}");
	    	
	    	Object o = engine.eval("isInt(source.port) && source.port > 50");
	    	Object rn = engine.eval("strRange(source.value, 7, 7)");
	    	engine.eval("print('REG: ' + regex('cdbbdbsbz', /d(b+)d/g))");
	    	engine.eval("print('FLOAT:' +  isFloat(1.2))");
	    
	    	end = System.nanoTime();
	    
	    	System.out.println(o);
	    	System.out.println("Range: " + rn);
	    	
	    	System.out.println(TimeUnit.NANOSECONDS.toMillis(end - ini) + " millis");
	    	
		} catch (ScriptException e) {
			
			e.printStackTrace();
			
		}
		
	    
	}
	
	
	public void exit() {
		
		try {
			Unirest.shutdown();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
}
