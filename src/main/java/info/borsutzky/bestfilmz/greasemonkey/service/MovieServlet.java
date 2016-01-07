package info.borsutzky.bestfilmz.greasemonkey.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Dieses Servlet ist die Schnittstelle zum Greasemonkey-Script.
 * (GET/ADD/UPDATE)
 * 
 * @author songoku
 * @since 02.07.2013
 * 
 */
@Singleton
public class MovieServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(HttpServlet.class
			.getName());

	@Inject
	Provider<GreasemonkeyServiceAdapter> inputHandlerProvider;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovieServlet() {
		super();
	}

	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		MovieServlet.log.info("init MovieServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		MovieServlet.log.info("processing request...");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		this.inputHandlerProvider.get().start();

	}

}
