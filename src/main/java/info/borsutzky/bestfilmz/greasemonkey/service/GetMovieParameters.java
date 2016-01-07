package info.borsutzky.bestfilmz.greasemonkey.service;

import info.borsutzky.bestfilmz.database.daos.DBPropertiesDao;
import info.borsutzky.bestfilmz.services.DBProperties;

import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lists all GetMovieParameters. Allows validation.
 * 
 * @author songoku
 * @since 02.07.2013
 * 
 */

public enum GetMovieParameters {
	imdbcode;

	private static Logger logger = LogManager
			.getLogger(GetMovieParameters.class.getName());

	public static void validate(final Map<String, String[]> inputParameter,
			final DBPropertiesDao dbPropertiesDao) throws ValidationException {
		for (final GetMovieParameters p : GetMovieParameters.values())
			if (!inputParameter.keySet().contains(p.toString()))
				throw new ValidationException("Missing Parameter "
						+ p.toString());
			else if (!inputParameter.get(p.toString())[0].matches(p
					.getRegEx(dbPropertiesDao))) {
				GetMovieParameters.logger.debug("imdbcode: "
						+ inputParameter.get(p.toString())[0] + " pattern: "
						+ p.getRegEx(dbPropertiesDao));
				GetMovieParameters.logger.debug("match?: "
						+ inputParameter.get(p.toString())[0].matches(p
								.getRegEx(dbPropertiesDao)));
				throw new ValidationException("RegEx validation failed for "
						+ p.toString());
			}

	}

	private String getRegEx(final DBPropertiesDao dbPropertiesDao)
			throws ValidationException {
		try {
			switch (this) {
			case imdbcode:
				return DBProperties.IMDBCODE_REGEX
						.getValueFrom(dbPropertiesDao);
			default:
				GetMovieParameters.logger.error("Keine RegEx gefunden.");
				throw new ValidationException("Keine RegEx gefunden!");
			}
		} catch (final PersistenceException e) {
			GetMovieParameters.logger.error(
					"Fehler beim Laden der Properties aus der Datenbank: "
							+ e.getMessage(), e);
			throw new ValidationException(
					"Fehler beim Laden der Properties aus der Datenbank: "
							+ e.getMessage(), e);
		}
	}
}
