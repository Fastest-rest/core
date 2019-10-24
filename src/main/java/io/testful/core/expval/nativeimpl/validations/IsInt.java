package io.testful.core.expval.nativeimpl.validations;

import java.util.Collections;
import java.util.List;

import io.testful.core.expval.nativeimpl.ValidationAttempt;
import io.testful.core.expval.nativeimpl.ValidationFunction;
import io.testful.core.validator.FieldResult;

public class IsInt implements ValidationFunction {

	public FieldResult validate(ValidationAttempt attempt) {
		
		FieldResult result = new FieldResult();
		result.setRule("isInt");
		result.setValue(attempt.getValue());
		result.setFieldName(attempt.getField());
		
		if(attempt.getValue() != null) {
			try {
				Integer.valueOf(attempt.getValue().toString());
				result.setValid(true);
			} catch(NumberFormatException nfe) {
				result.setValid(false);
				result.setMessage(String.format(msg, attempt.getField(), attempt.getValue(), attempt.getExpressionRule()));
			}
		}
		
		return result;
	}


	@Override
	public List<String> getParams() {
		return Collections.emptyList();
	}
	
}
