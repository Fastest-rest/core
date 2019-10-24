package io.testful.core;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;


public class FasTestParams {

	@Parameter
	public List<String> parameters = new ArrayList<>();

	@Parameter(names = {"-v"}, description = "Level of verbosity")
	public Integer verbose = 1;

	@Parameter(names = "-c", description = "Config folder", required = true)
	public String configFolder;

	@Parameter(names = "-d", description = "Debug mode")
	public boolean debug = false;

	@Parameter(names = "--help", help = true)
	public boolean help = false;
	
	
}
