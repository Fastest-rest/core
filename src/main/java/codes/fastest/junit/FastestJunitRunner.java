package codes.fastest.junit;

import java.io.File;
import java.net.URL;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import codes.fastest.core.exception.ConfigurationErrorException;

public class FastestJunitRunner extends Runner {

	private Class<?> testClass;
	private Description description;

	public FastestJunitRunner() {
	}

	public FastestJunitRunner(Class<?> testClass) {
		this.testClass = testClass;
		this.description = Description.createSuiteDescription(testClass);
	}

	@Override
	public Description getDescription() {

		return description;

	}

	@Override
	public void run(RunNotifier notifier) {
		
		notifier.fireTestRunStarted(description);
		
		URL resource = testClass.getClassLoader().getResource("fastest");
		
		if(resource == null) {
			Failure failure = new Failure(description, new ConfigurationErrorException("Missing fastest folder."));
			notifier.fireTestFailure(failure);
			notifier.fireTestFinished(description);
			return;
		}
		
		File fastest = new File(resource.getFile());
		
		if(!fastest.exists() || !fastest.isDirectory()) {
			Failure failure = new Failure(description, new ConfigurationErrorException("Missing fastest folder."));
			notifier.fireTestFailure(failure);
			notifier.fireTestFinished(description);
			return;
		} 
		
		// make things happen
		
		Server server = new Server(8080);

        WebAppContext wacHandler = new WebAppContext();
        wacHandler.setContextPath("/");
        wacHandler.setResourceBase("target/classes");
        //wacHandler.setWar("swa-0.0.1-SNAPSHOT.war");
        wacHandler.setConfigurationDiscovered(true);
        wacHandler.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/classes/.*");
		
        wacHandler.setConfigurations(new Configuration[] { 
	        new AnnotationConfiguration(), 
	        new WebInfConfiguration(), 
	        new WebXmlConfiguration(), 
	        new MetaInfConfiguration(), 
	        new FragmentConfiguration(),
	        new EnvConfiguration(), 
	        new PlusConfiguration(), 
	        new JettyWebXmlConfiguration() 
        });
        
        server.setHandler(wacHandler);

        try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		notifier.fireTestFinished(description);
		
	}

}
