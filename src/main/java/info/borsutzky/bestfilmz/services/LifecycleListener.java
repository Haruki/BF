package info.borsutzky.bestfilmz.services;

import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;

@WebListener
public class LifecycleListener extends GuiceServletContextListener {

	private static Logger log = LogManager.getLogger(LifecycleListener.class);

	// @Override
	// public void contextDestroyed(ServletContextEvent servletContextEvent) {
	// log.info("destroying servletcontext: removing injector from servletContext.");
	// super.contextDestroyed(servletContextEvent);
	// servletContextEvent.getServletContext().removeAttribute("injector");
	// }
	//
	// @Override
	// public void contextInitialized(ServletContextEvent servletContextEvent) {
	// super.contextInitialized(servletContextEvent);
	// log.info("lifecycle init, adding injector to servletContext.");
	// servletContextEvent.getServletContext().setAttribute("injector",
	// getInjector());
	// }

	@Override
	protected Injector getInjector() {
		log.info("creating injector with BfServletModule..");
		return Guice.createInjector(Stage.PRODUCTION, new BfServletModule());
	}

}
