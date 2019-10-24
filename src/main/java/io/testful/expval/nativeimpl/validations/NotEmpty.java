package io.testful.expval.nativeimpl.validations;

import java.util.Collections;
import java.util.List;

import io.testful.core.validator.FieldResult;
import io.testful.expval.nativeimpl.ValidationAttempt;
import io.testful.expval.nativeimpl.ValidationFunction;

public class NotEmpty implements ValidationFunction {

	public FieldResult validate(ValidationAttempt attempt) {
		
		FieldResult result = new FieldResult();
		result.setRule("isInt");
		result.setValue(attempt.getValue());
		result.setFieldName(attempt.getField());
		
		boolean valid = attempt.getValue() != null && attempt.getValue().toString().trim().length() > 0;
		
		result.setValid(valid);
		
		if(!valid) {
			result.setMessage(String.format(msg, attempt.getField(), attempt.getValue(), attempt.getExpressionRule()));
		}
		
		return result;
	}

	@Override
	public List<String> getParams() {
		return Collections.emptyList();
	}

}
