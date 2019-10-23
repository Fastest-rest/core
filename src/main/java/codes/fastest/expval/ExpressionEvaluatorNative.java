package codes.fastest.expval;

import codes.fastest.core.validator.FieldResult;
import codes.fastest.expval.nativeimpl.RuleParser;
import codes.fastest.expval.nativeimpl.ValidationAttempt;
import codes.fastest.expval.nativeimpl.ValidationFunction;

public class ExpressionEvaluatorNative implements ExpressionEvaluator {

	private RuleParser ruleParser = null;
	
	public ExpressionEvaluatorNative() {
	
		ruleParser = new RuleParser();
		
	}
	
	@Override
	public FieldResult evaluate(String fieldName, Object expression, Object value) {
		
		ValidationAttempt attempt = new ValidationAttempt();
		attempt.setValue(value);
		attempt.setExpressionRule(expression);
		attempt.setField(fieldName);
		
		ValidationFunction function = ruleParser.parse(attempt);
		
		FieldResult result = function.validate(attempt);
		
		return result;
		
	}
	
	

	
}
