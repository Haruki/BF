package info.borsutzky.bestfilmz.greasemonkey.service;

import info.borsutzky.bestfilmz.database.entities.Filmz;
import info.borsutzky.bestfilmz.services.BestFilmzService;
import info.borsutzky.bestfilmz.services.ServiceException;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.google.inject.Inject;

/**
 * Diese Klasse bildet die Schnittstelle zwischen dem eigentlichen Service
 * (BestFilmzService) und der Technologiespezifischen (Servlet) Schnittstelle
 * nach außen. Eine Einstiegsmethode (start()) verteilt nach Analyse der
 * Requestparameter die Requests auf die Service Methoden des
 * technologieneutralen BestFilmzService.
 * 
 * @author songoku
 * @since 31.03.2013
 * 
 */
public class GreasemonkeyServiceAdapter {

	private static Logger log = LogManager
			.getLogger(GreasemonkeyServiceAdapter.class);;

	@Inject
	private HttpServletResponse response;

	private Map<String, String[]> inputParameter;

	@Inject
	private BestFilmzService bfs;

	@Inject
	ValidatedInputParameters validatedInputParameters;

	private void handleAddMovie() {
		final Movie movie = new Movie();
		try {
			movie.setImdbCode(this.inputParameter
					.get(AddMovieParameters.imdbcode.toString())[0]);
			movie.setNameDeutsch(this.inputParameter
					.get(AddMovieParameters.namedeutsch.toString())[0]);
			movie.setNameOriginal(this.inputParameter
					.get(AddMovieParameters.nameoriginal.toString())[0]);
			movie.setImdbRating(new BigDecimal(this.inputParameter
					.get(AddMovieParameters.score.toString())[0]));
			movie.setReleaseDate(Long.valueOf(this.inputParameter
					.get(AddMovieParameters.releasedate.toString())[0]));
			this.bfs.serviceAddMovie(movie.toFilmz());
			this.serviceSucccessResponse(new ResponseMessage(
					"Successfully added Movie with Code = "
							+ movie.getImdbCode()));
		} catch (final ServiceException e) {
			this.serviceError(e.getMessage());
		}

	}

	private void handleGetMovie() {
		try {
			GreasemonkeyServiceAdapter.log.info("handleGetMovie()");
			final String imdbCode = this.inputParameter
					.get(GetMovieParameters.imdbcode.toString())[0];
			GreasemonkeyServiceAdapter.log.info("imdbCode: " + imdbCode);
			final Filmz film = this.bfs.serviceGetMovie(imdbCode);
			final Movie movie = Movie.createFromEntity(film);
			this.serviceSucccessResponse(movie);
		} catch (final ServiceException e) {
			this.serviceError(e.getMessage());
		}

	}

	private void handleUpdateMovieSeen() {
		try {
			final String imdbCode = this.inputParameter
					.get(UpdateMovieSeenParameters.imdbcode.toString())[0];
			final boolean seen = Boolean.valueOf(this.inputParameter
					.get(UpdateMovieSeenParameters.seen.toString())[0]);
			this.bfs.serviceUpdateMovie(imdbCode, seen);
			this.serviceSucccessResponse(new ResponseMessage(
					"Successfully updated Movie with Code = " + imdbCode));
		} catch (final ServiceException e) {
			this.serviceError(e.getMessage());
		}
	}

	/**
	 * Diese Methode ist der Einstiegspunkt, der aus dem Servlet aufgerufen
	 * wird.
	 * 
	 */
	public void start() {
		try {
			this.inputParameter = this.validatedInputParameters
					.getInputParameter();
			final String[] serviceValues = this.inputParameter
					.get(ServiceParameters.SERVICE_PARAMETER_NAME);
			switch (ServiceParameters.valueOf(serviceValues[0])) {
			case getmovie:
				this.handleGetMovie();
				break;
			case addmovie:
				this.handleAddMovie();
				break;
			case updatemovieseen:
				this.handleUpdateMovieSeen();
				break;
			default:
				this.serviceError("invalid service parameter value!");
			}
		} catch (final ValidationException e) {
			GreasemonkeyServiceAdapter.log
					.error("Validation of Request failed: " + e.getMessage());
			this.serviceError("Validation of Request failed: " + e.getMessage());
		}
	}

	/**
	 * Im Fehlerfall wird einer Fehlermeldung an den BrowserClient gesendet.
	 * Http Status Code ist dabei immer 500. Wird auch von außen verwendet
	 * (InputHandler).
	 * 
	 * @param message
	 *            {@link String}
	 */
	public void serviceError(final String message) {
		GreasemonkeyServiceAdapter.log.error(message);
		final JSONObject jsonError = new JSONObject();
		jsonError.put("error", message);
		try (Writer writer = this.response.getWriter()) {
			writer.write(jsonError.toString());
		} catch (final IOException e1) {
			GreasemonkeyServiceAdapter.log.error(e1.getMessage());
			e1.printStackTrace();
		}
	}

	/**
	 * Diese Methode dient dem Versenden von Responses im Erfolgsfall.
	 * 
	 * @param message
	 *            {@link String}
	 */
	private void serviceSucccessResponse(final JsonResponse jsonResponse) {
		GreasemonkeyServiceAdapter.log
				.info("Notifying Client that the request has been successfully handled!");
		GreasemonkeyServiceAdapter.log.info("Response Text: "
				+ jsonResponse.asJson().toString());
		final JSONObject json = new JSONObject();
		json.put("success", jsonResponse.asJson());
		try (Writer writer = this.response.getWriter()) {
			writer.write(json.toString());
		} catch (final IOException e) {
			GreasemonkeyServiceAdapter.log.error(e.getMessage());
			e.printStackTrace();
		}

	} // public static void main(String[] args) throws Exception{
		// Map<String,String[]> testmap = new HashMap();
		// testmap.put("service", new String[]{"slkdf","lskdf"});
		// new InputHandler(testmap);
		// }
}
