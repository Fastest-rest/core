package codes.fastest.expval.nativeimpl.validations;

import java.util.List;

import codes.fastest.core.validator.FieldResult;
import codes.fastest.expval.nativeimpl.ValidationAttempt;
import codes.fastest.expval.nativeimpl.ValidationFunction;

public class Min implements ValidationFunction {

	private List<String> params;
	
	public Min() {

		params = List.of("min");
		
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
