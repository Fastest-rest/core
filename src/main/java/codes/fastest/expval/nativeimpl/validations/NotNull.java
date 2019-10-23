package codes.fastest.expval.nativeimpl.validations;

import java.util.Collections;
import java.util.List;

import codes.fastest.core.validator.FieldResult;
import codes.fastest.expval.nativeimpl.ValidationAttempt;
import codes.fastest.expval.nativeimpl.ValidationFunction;

public class NotNull implements ValidationFunction {

	public FieldResult validate(ValidationAttempt attempt) {
		
		boolean valid = attempt.getValue() != null;
		
		FieldResult result = new FieldResult();
		result.setValid(valid);
		result.setRule("Equals");
		result.setValue(attempt.getValue());
		result.setFieldName(attempt.getField());
		
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
