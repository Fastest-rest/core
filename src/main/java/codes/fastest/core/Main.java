package codes.fastest.core;

public class Main {

	public static void main(String[] args) {
		
		FasTestParams params = new FasTestParams();
		params.configFolder = "/home/heitor.machado/devel/temp_test/endpoint";
		
		FasTest fasTest = new FasTest(params);
		
		fasTest.run();
		
	}
	
}