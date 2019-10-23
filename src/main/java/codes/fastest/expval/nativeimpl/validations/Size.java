package codes.fastest.expval.nativeimpl.validations;

import java.util.List;

import codes.fastest.core.validator.FieldResult;
import codes.fastest.expval.nativeimpl.ValidationAttempt;
import codes.fastest.expval.nativeimpl.ValidationFunction;

public class Size implements ValidationFunction {

	private List<String> params;
	
	public Size() {
	
		params = List.of("min", "max");
		
	}
	
	public FieldResult validate(ValidationAttempt attempt) {

		boolean valid = false;
		
		if(attempt.getValue() != null) {
		
			Integer min = Integer.parseInt((String) attempt.getRuleParams().get("min"));
			Integer max = Integer.parseInt((String) attempt.getRuleParams().get("max"));
			
			int length = attempt.getValue().toString().length();
			
			valid = length >= min && length <= max; 

		}
		
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
		return params;
	}
	
}
