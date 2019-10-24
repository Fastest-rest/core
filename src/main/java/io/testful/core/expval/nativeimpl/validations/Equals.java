package io.testful.core.expval.nativeimpl.validations;

import java.util.Arrays;
import java.util.List;

import io.testful.core.expval.nativeimpl.ValidationAttempt;
import io.testful.core.expval.nativeimpl.ValidationFunction;
import io.testful.core.validator.FieldResult;

public class Equals implements ValidationFunction {

	private List<String> params;
	
	public Equals() {
	
		params = Arrays.asList("value");
		
	}
	
	public FieldResult validate(ValidationAttempt attempt) {
		
		Object value = attempt.getRuleParams().get("value");

		System.out.println("EQUALS: " + attempt.getValue().getClass() + "|" + attempt.getValue() + " => (" + value.getClass() + ")" + value);
		
		boolean valid = attempt.getValue().equals(value);
		
		FieldResult result = new FieldResult();
		result.setValid(valid);
		result.setRule("Equals");
		result.setValue(attempt.getValue());
		result.setFieldName(attempt.getField());
		
		if(!valid) {
			result.setMessage(String.format(msg, attempt.getField(), "(" + attempt.getValue().getClass() + ")" + attempt.getValue(), attempt.getExpressionRule()));
		}
		
		return result;
	}

	@Override
	public List<String> getParams() {
		return params;
	}
	
}
