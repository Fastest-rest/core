package io.testful.core;

import static io.testful.util.Const.DELETE;
import static io.testful.util.Const.FLOW;
import static io.testful.util.Const.GET;
import static io.testful.util.Const.HEAD;
import static io.testful.util.Const.IN;
import static io.testful.util.Const.OPTIONS;
import static io.testful.util.Const.OUT;
import static io.testful.util.Const.PATCH;
import static io.testful.util.Const.POST;
import static io.testful.util.Const.PUT;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValueFactory;

import io.testful.core.exception.ConfigurationErrorException;
import io.testful.util.Str;
import io.testful.util.Valid;

public class FasTest {

	private FasTestParams params;

	private List<String> acceptedConfigDash = Arrays.asList(IN, OUT, FLOW);

	private Engine engine;

	private static final Logger log = LoggerFactory.getLogger(FasTest.class);

	public FasTest(FasTestParams params) {

		this.params = params;

		engine = new Engine();

	}

	public void run() {

		log.trace("Running ...");

		validateParams(params);
		readConfig(params);

		engine.execute();

		engine.exit();

	}

	public void validateParams(FasTestParams params) {

		log.trace("Validating paramenters ...");

		if(Str.isEmpty(params.configFolder)) {
			throw new InvalidParameterException("Config folder is needed.");
		}
		
		File folder = new File(params.configFolder);
		if(!folder.isDirectory()) {
			throw new InvalidParameterException("Config folder must be a folder.");
		}
		
		String[] list = folder.list((file, fileName) -> fileName.endsWith("fas"));
		if(list.length == 0) {
			throw new InvalidParameterException("Config folder must have at least one IN and one OUT fas file.");
		}

		Map<String, Integer> map = new HashMap<>();
		
		for(int i = 0; i < list.length; i++) {
			
			// remove extension
			int pos = list[i].lastIndexOf(".");
			if (pos > 0 && pos < (list[i].length() - 1)) { // If '.' is not the first or last character.
				list[i] = list[i].substring(0, pos);
			}
			
			int dashIdx = list[i].indexOf('-');
			if(dashIdx == -1) { 
				throw new ConfigurationErrorException("Malformed fas file: " + list[i] + " missing dash.");
			}
			
			String[] parts = list[i].split("-");
			if(!acceptedConfigDash.contains(parts[1])) {
				throw new ConfigurationErrorException("Malformed fas file: " + list[i] + " config dash invalid. Options are: " + acceptedConfigDash);
			}
			
			if(IN.equals(parts[1])) map.merge(parts[0], 1, Integer::sum);
			if(OUT.equals(parts[1])) map.merge(parts[0], -1, Integer::sum);			
		}
		
		map.forEach((key, value) -> {
			
			if(value < 0) throw new ConfigurationErrorException("Malformed fas file pair: " + key + ". Missing IN file.");
			if(value > 0) throw new ConfigurationErrorException("Malformed fas file pair: " + key + ". Missing OUT file.");
			
		});
		
	}
	
	public void validateConfigFile(Config config, String type) {

		if(IN.equals(type)) {

			boolean get     = config.hasPath(GET);
			boolean post    = config.hasPath(POST);
			boolean delete  = config.hasPath(DELETE);
			boolean put     = config.hasPath(PUT);
			boolean options = config.hasPath(OPTIONS);
			boolean head    = config.hasPath(HEAD);
			boolean patch   = config.hasPath(PATCH);

			//System.out.println(config.root().render());

			if(!Valid.onlyOneTrue(get, post, delete, put, options, head, patch)) {
				throw new ConfigurationErrorException("IN configuration must have exactly one endpoint configured.");
			}

		}

	}
	
	public Config processInConfig(Config config) {

		boolean get     = config.hasPath(GET);
		boolean post    = config.hasPath(POST);
		boolean delete  = config.hasPath(DELETE);
		boolean put     = config.hasPath(PUT);
		boolean options = config.hasPath(OPTIONS);
		boolean head    = config.hasPath(HEAD);
		boolean patch   = config.hasPath(PATCH);
		
		String method = null;
		if(get)     method = GET;
		if(post)    method = POST;
		if(delete)  method = DELETE;
		if(put)     method = PUT;
		if(options) method = OPTIONS;
		if(head)    method = HEAD;
		if(patch)   method = PATCH;
		
		// set method in config 
		config = config.withValue("method", ConfigValueFactory.fromAnyRef(method));
		
		String rawEndpoint = config.getString(method);
		String endpoint = rawEndpoint;
		
		/*String[] schemes = {"http", "https"};
		UrlValidator urlValidator = new UrlValidator(schemes);
		if (!urlValidator.isValid(rawEndpoint)) {
			endpoint =  "http://localhost" + rawEndpoint;
		}*/
		
		// set endpoint in config
		config = config.withValue("endpoint", ConfigValueFactory.fromAnyRef(endpoint));
		
		System.out.println(config.root().render(ConfigRenderOptions.defaults().setComments(false).setOriginComments(false)));
		
		return config;
	}
	
	public void readConfig(FasTestParams params) {
		
		log.trace("Reading configurations ...");
		
		File folder = new File(params.configFolder);
		
		String[] list = folder.list((file, fileName) -> fileName.endsWith("-IN.fas"));
		
		for(int i = 0; i < list.length; i++) {
			
			String[] parts = list[i].split("-");
			String rawEndpoint = parts[0];
			
			Config inConfig = ConfigFactory.parseFile(new File(folder + "/" + list[i]));
			validateConfigFile(inConfig, IN);
			inConfig = processInConfig(inConfig);
			
			Config outConfig = ConfigFactory.parseFile(new File(folder + "/" + list[i].replace(IN, OUT)));
			validateConfigFile(inConfig, OUT);
			
			ExecutionConfiguration execConfig = new ExecutionConfiguration(rawEndpoint, inConfig, outConfig);
			engine.registerExecConfig(execConfig);
		}
		
	}
	

	
	public static void main(String[] args) {
		
		FasTestParams params = new FasTestParams();
		JCommander jcomm = JCommander.newBuilder().addObject(params).build();
		try {
			jcomm.parse(args);
		} catch(ParameterException e) {
			System.out.println(e.getMessage());
			jcomm.usage();
			return;
		}
		
		if(params.help) {
			jcomm.usage();
			return;
		}
		
		FasTest fastest = new FasTest(params);
		
		fastest.run();
		
		
	}
	
}
