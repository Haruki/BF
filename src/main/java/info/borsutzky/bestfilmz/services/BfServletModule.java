package info.borsutzky.bestfilmz.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

public class BfServletModule extends ServletModule {

	private static Logger log = LogManager.getLogger(BfServletModule.class);

	@Override
	protected void configureServlets() {
		log.info("start configuring guice servlet module.");
		super.configureServlets();
		install(new JpaPersistModule("BF"));
		filter("/*").through(PersistFilter.class);
		serve("/MovieServlet")
				.with(info.borsutzky.bestfilmz.greasemonkey.service.MovieServlet.class);
		log.info("finished config.");
	}

}
