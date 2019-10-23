package codes.fastest.expval;

import codes.fastest.core.validator.FieldResult;

public interface ExpressionEvaluator {

	public FieldResult evaluate(String fieldName, Object expression, Object target);
	
}
