package io.testful.core.expval.nativeimpl.validations;

import java.util.Arrays;
import java.util.List;

import io.testful.core.expval.nativeimpl.ValidationAttempt;
import io.testful.core.expval.nativeimpl.ValidationFunction;
import io.testful.core.validator.FieldResult;

public class Min implements ValidationFunction {

	private List<String> params;
	
	public Min() {

		params = Arrays.asList("min");
		
	}
	
	public FieldResult validate(ValidationAttempt attempt) {
		
		boolean valid = false;
		
		if(attempt.getValue() != null) {

			Integer min = Integer.valueOf((String) attempt.getRuleParams().get("min"));
			
			try {
				Integer payload = Integer.valueOf(attempt.getValue().toString());
				
				valid = payload >= min;
				
			} catch(NumberFormatException nfe) {
				valid = false;
			}
		
		}

		FieldResult result = new FieldResult();
		result.setRule("isInt");
		result.setValue(attempt.getValue());
		result.setValid(valid);
		result.setFieldName(attempt.getField());
		
		if(!result.isValid()) {
			result.setMessage(String.format(msg, attempt.getField(), attempt.getValue(), attempt.getExpressionRule()));
		}
		
		return result;
	}

	@Override
	public List<String> getParams() {
		
		return params;
	}
	
}
