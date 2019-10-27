package io.testful.core;

import javax.script.ScriptException;

import org.andrejs.json.Json;


public class Main {

	public static void main(String[] args) throws ScriptException {

		Json obj = Json.of.string("{port: 80, name: 'test', value: null}");

		/*ExpressionEvaluatorNative eval = new ExpressionEvaluatorNative();
		
		boolean x = eval.evaluate("size(2, 5)", "x");
		
		System.out.println(x);
		
		System.exit(1);*/

		TestfulParams params = new TestfulParams();
		//params.configFolder = "/home/heitor.machado/devel/temp_test/endpoint";
		params.configFolder = "D:/DEVEL/projetos/Testful/src/main/resources/jsonplaceholder/todo";

		Testful testfull = new Testful(params);

		testfull.run();

	}

}