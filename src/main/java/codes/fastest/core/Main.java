package codes.fastest.core;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.andrejs.json.Json;
import org.json.JSONObject;
import org.mvel2.MVEL;

import codes.fastest.expval.ExpressionEvaluatorNative;


public class Main {

	public static void main(String[] args) throws ScriptException {
		

		
		Json obj = Json.of.string("{port: 80, name: 'test', value: null}");
		
		/*
		
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine engine = scriptEngineManager.getEngineByName("mvel");
		
		Object i = engine.eval("def isInt(val){val}");
		engine.put("source", obj);
		
		Object o = engine.eval("source");
		
		System.out.println(o);
		
		System.exit(1);*/
		
		/*Map<String, String> context = new HashMap<>();
		context.put("name", "test");
		
		MVEL.eval("def isInt(val){val}");
		
		
		
		Object eval = MVEL.eval("isInt(10)", obj);
		
		System.out.println(eval);
		
		System.exit(1);*/
		
		
		/*ExpressionEvaluatorNative eval = new ExpressionEvaluatorNative();
		
		boolean x = eval.evaluate("size(2, 5)", "x");
		
		System.out.println(x);
		
		System.exit(1);*/
		
		FasTestParams params = new FasTestParams();
		params.configFolder = "/home/heitor.machado/devel/temp_test/endpoint";
		
		FasTest fasTest = new FasTest(params);
		
		fasTest.run();
		
	}
	
}