package codes.fastest.expval.nativeimpl;

import java.util.HashMap;
import java.util.Map;

public class ValidationAttempt {

	private String field;
	
	private Object value;
	
	private Object expressionRule;
	
	private Map<String, Object> ruleParams = new HashMap<>();


	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Map<String, Object> getRuleParams() {
		return ruleParams;
	}

	public void setRuleParams(Map<String, Object> ruleParams) {
		this.ruleParams = ruleParams;
	}

	public void setExpressionRule(Object expressionRule) {
		this.expressionRule = expressionRule;
	}

	public Object getExpressionRule() {
		return expressionRule;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}
