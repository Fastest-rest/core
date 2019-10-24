package io.testful.expval.nativeimpl.validations;

import java.util.Arrays;
import java.util.List;

import io.testful.core.validator.FieldResult;
import io.testful.expval.nativeimpl.ValidationAttempt;
import io.testful.expval.nativeimpl.ValidationFunction;

public class Between implements ValidationFunction {

	private List<String> params;
	
	public Between() {
	
		params = Arrays.asList("min", "max");
		
	}
	
	public FieldResult validate(ValidationAttempt attempt) {
		
		boolean valid = false;

		if(attempt.getValue() != null) {
		
			Integer min = Integer.parseInt((String) attempt.getRuleParams().get("min"));
			Integer max = Integer.parseInt((String) attempt.getRuleParams().get("max"));

			try {
				
				Integer payload = Integer.valueOf(attempt.getValue().toString());
			
				valid = payload >= min && payload <= max;
		
			} catch(NumberFormatException nfe) {
				valid = false;
			}
		}

		FieldResult result = new FieldResult();
		result.setRule("between");
		result.setValue(attempt.getValue());
		result.setValid(valid);
		result.setFieldName(attempt.getField());
		
		if(!valid) {
			result.setMessage(String.format(msg, attempt.getField(), attempt.getValue(), attempt.getExpressionRule()));
		}
		
		return result;
	}
		
	@Override
	public List<String> getParams() {
		return params;
	}
	
}
