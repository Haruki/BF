package info.borsutzky.bestfilmz.greasemonkey.service;

import info.borsutzky.bestfilmz.database.daos.DBPropertiesDao;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lists all valid "service" parameter values. Allows to validate, if values are
 * correct.
 * 
 * @author songoku
 * @since 02.07.2013
 * 
 */
public enum ServiceParameters {
	getmovie, addmovie, updatemovieseen;

	private static Logger logger = LogManager.getLogger(ServiceParameters.class
			.getName());
	public static String SERVICE_PARAMETER_NAME = "service";

	/**
	 * Checks, if given RequestParameters form a valid Request. Throws
	 * ValidationException if validation fails.
	 * 
	 * @param inputParameter
	 *            parameter map (servlet)
	 * @throws ValidationException
	 *             exception.
	 */
	public static void validate(final Map<String, String[]> inputParameter,
			final DBPropertiesDao dbPropertiesDao) throws ValidationException {
		final String[] serviceValues = inputParameter
				.get(ServiceParameters.SERVICE_PARAMETER_NAME);
		if (serviceValues == null || serviceValues.length != 1
				|| ServiceParameters.valueOf(serviceValues[0]) == null)
			throw new ValidationException(
					"Missing or mistyped or multiple service parameter.");
		ServiceParameters.valueOf(serviceValues[0]).validateParameters(
				inputParameter, dbPropertiesDao);
	}

	/**
	 * Dispatches the validation to the appropriate enum of parameters, which
	 * are e.g.: Get, Add or Update-Parameters.
	 * 
	 * @param inputParameter
	 *            Parameter map (servlet)
	 * @throws ValidationException
	 */
	private void validateParameters(final Map<String, String[]> inputParameter,
			final DBPropertiesDao dbPropertiesDao) throws ValidationException {
		switch (this) {
		case addmovie:
			AddMovieParameters.validate(inputParameter, dbPropertiesDao);
			break;
		case getmovie:
			GetMovieParameters.validate(inputParameter, dbPropertiesDao);
			break;
		case updatemovieseen:
			UpdateMovieSeenParameters.validate(inputParameter, dbPropertiesDao);
			break;
		default:
			ServiceParameters.logger
					.error("inputparameters cant match valid parameter list!");
			throw new ValidationException("Invalid InputParameters");

		}
	}
}
