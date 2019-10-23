package codes.fastest.core;

import static codes.fastest.util.Const.ENDPOINT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.Unirest;

import codes.fastest.core.validator.FastestResult;
import codes.fastest.core.validator.ResponseValidator;

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
			
			System.out.println(response.getBody());
			
			FastestResult result = ResponseValidator.validate(response, execConf.getOut());
			
			listResults.add(result);

		});
		
	}
	
	public void exit() {
		
		try {
			Unirest.shutdown();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
}
