package io.testful.expval;

import io.testful.core.validator.FieldResult;

public interface ExpressionEvaluator {

	public FieldResult evaluate(String fieldName, Object expression, Object target);
	
}
