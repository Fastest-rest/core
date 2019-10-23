package codes.fastest.core.validator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;

import codes.fastest.core.FastestResponse;
import codes.fastest.expval.ExpressionEvaluator;
import codes.fastest.expval.ExpressionEvaluatorNative;

public class ResponseValidator {

	private static ResponseValidator INSTANCE = null;

	private static Logger log = LoggerFactory.getLogger(ResponseValidator.class);
	
	private static final String BODY = "body";
	
	private ResponseValidator() {
		
		
	}
	
	public static ResponseValidator get() {
		
		if(INSTANCE == null) INSTANCE = new ResponseValidator();
		
		return INSTANCE;
	}
	
	public static FastestResult validate(FastestResponse fastestResponse, Config out) {
		
		get();
		
		FastestResult result = new FastestResult();
		
		if(fastestResponse.isJson()) {

			ExpressionEvaluator evaluator = new ExpressionEvaluatorNative();
			
			log.info("RULES =============== =============== ===============");
			
			boolean hasStatus = out.hasPath("status");
			if(hasStatus) {
				int status = out.getInt("status");
				if(fastestResponse.getStatus() != status) {
					//result.addError(String.format("Status Received: %s different than expected: %s", fastestResponse.getStatus(), status));
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
						String error = String.format("Expected header %s not found in response.", expectedHeaderName);
						//result.addError(error);
					} else {

						ConfigValue configValue = entry.getValue();

						String value = configValue.render();

						System.out.println("VALUE: " + value);

					}
				}
			}

			boolean hasBody = out.hasPath(BODY);
			if(hasBody) {

				JSONObject responseBody = new JSONObject(fastestResponse.getBody());
				
				
				
				Set<Entry<String, ConfigValue>> entrySetOut = out.getObject(BODY).entrySet();
				for(Entry<String, ConfigValue> entryOut : entrySetOut) {

					Object rule = entryOut.getValue().unwrapped();
					
					Object value = responseBody.get(entryOut.getKey());
					
					String fieldName = entryOut.getKey();
					
					FieldResult fieldResult = evaluator.evaluate(fieldName, rule, value);
					result.getFieldResults().add(fieldResult);
					
					
				}
			}

			log.debug("");
			log.debug("RESPONSE HEADER =============== =============== ===============");
			Map<String, List<String>> headers = fastestResponse.getHeaders();
			Set<Entry<String, List<String>>> entrySetHeaders = headers.entrySet();
			for(Entry<String, List<String>> entry : entrySetHeaders) {
				log.debug("{} => {}", entry.getKey(), entry.getValue());
			}

			/*log.debug("");
			log.debug("RESPONSE BODY   =============== =============== ===============");
			Set<Entry<String, Object>> entrySet = json.entrySet();
			for(Entry<String, Object> entry : entrySet) {
				log.debug("{} => {}", entry.getKey(), entry.getValue());
			}*/

			log.info("");
			log.info("RESULTS         =============== =============== ===============");
			for(FieldResult res : result.getFieldResults()) {
				if(res.isValid())
					log.info(res.getFieldName() + " is valid !");
				else
					log.info(res.getMessage());
			}
			
		}
		
		return result;
	}
	
	
}
