package codes.fastest.expval.nativeimpl.validations;

import java.util.Collections;
import java.util.List;

import codes.fastest.core.validator.FieldResult;
import codes.fastest.expval.nativeimpl.ValidationAttempt;
import codes.fastest.expval.nativeimpl.ValidationFunction;

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
