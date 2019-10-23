package codes.fastest.expval.nativeimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import codes.fastest.expval.nativeimpl.validations.Between;
import codes.fastest.expval.nativeimpl.validations.Equals;
import codes.fastest.expval.nativeimpl.validations.IsInt;
import codes.fastest.expval.nativeimpl.validations.Min;
import codes.fastest.expval.nativeimpl.validations.NotEmpty;
import codes.fastest.expval.nativeimpl.validations.NotNull;
import codes.fastest.expval.nativeimpl.validations.Size;

public class RuleParser {

	private Map<String, ValidationFunction> map = new HashMap<>();
	
	private String regExp = "([a-zA-Z]{1,10})\\((.*)\\)";
	
	private Pattern pattern = Pattern.compile(regExp);
	
	public RuleParser() {
	
		// int
		map.put("isInt", new IsInt());
		map.put("min", new Min());
		map.put("between", new Between());

		// String
		map.put("notNull", new NotNull());
		map.put("notEmpty", new NotEmpty());		
		map.put("size", new Size());
		
		// all
		map.put("equals", new Equals());
	}


	public ValidationFunction parse(ValidationAttempt attempt) {

		ValidationFunction func = null;
		
		Matcher matcher = null;
		
		if(attempt.getExpressionRule().getClass() == String.class) {
		
			matcher = pattern.matcher((String) attempt.getExpressionRule()); 
		
		}
			
		if(matcher != null && matcher.find()) {
	
			String funcName = matcher.group(1);
			
			func = map.get(funcName);
			
			if(matcher.groupCount() == 2) {
				String tempParams = matcher.group(2);
			
				if(tempParams != null && !tempParams.strip().isBlank()) {
				
					List<String> funcParams = func.getParams();
				
					String[] params = tempParams.split(",");

					if(funcParams.size() != params.length) {
						throw new RuntimeException("Parameters count on function " + funcName + " does not match function definition");
					}
					
					for(int i = 0; i < params.length; i++) {

						attempt.getRuleParams().put(funcParams.get(i), params[i]);
					}
				}
			}

		} else {

			func = map.get("equals");
			attempt.getRuleParams().put("value", attempt.getExpressionRule());
			
		}
		
		return func;
	}
	
}
