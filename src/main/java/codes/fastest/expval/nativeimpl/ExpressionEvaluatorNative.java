package codes.fastest.expval.nativeimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ExpressionEvaluatorNative {

	private Map<String, Function<?, Boolean>> map = new HashMap<>();
	
	public ExpressionEvaluatorNative() {
	
		map.put("isInt", Func::isInt);
		map.put("notNull", Func::notNull);
		map.put("notEmpty", Func::notEmpty);
		map.put("min", Func::min);
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean evaluate(String expression, Object target) {
		
		
		Function<Object, Boolean> function = (Function<Object, Boolean>) map.get(expression);
		
		return function.apply(target);
		
	}
	
	

	
}
