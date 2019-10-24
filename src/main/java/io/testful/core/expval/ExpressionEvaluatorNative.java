package io.testful.core.expval;

import io.testful.core.expval.nativeimpl.RuleParser;
import io.testful.core.expval.nativeimpl.ValidationAttempt;
import io.testful.core.expval.nativeimpl.ValidationFunction;
import io.testful.core.validator.FieldResult;

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
