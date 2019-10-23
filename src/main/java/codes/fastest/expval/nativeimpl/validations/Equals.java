package codes.fastest.expval.nativeimpl.validations;

import java.util.List;

import codes.fastest.core.validator.FieldResult;
import codes.fastest.expval.nativeimpl.ValidationAttempt;
import codes.fastest.expval.nativeimpl.ValidationFunction;

public class Equals implements ValidationFunction {

	private List<String> params;
	
	public Equals() {
	
		params = List.of("value");
		
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
