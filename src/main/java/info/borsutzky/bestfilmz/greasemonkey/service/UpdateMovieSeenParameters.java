package info.borsutzky.bestfilmz.greasemonkey.service;

import info.borsutzky.bestfilmz.database.daos.DBPropertiesDao;
import info.borsutzky.bestfilmz.services.DBProperties;

import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lists all UpdateMovieSeenParameters. Allows validation.
 * 
 * @author songoku
 * @since 02.07.2013
 * 
 */
public enum UpdateMovieSeenParameters {
	imdbcode, seen;

	private static Logger logger = LogManager
			.getLogger(UpdateMovieSeenParameters.class.getName());

	static void validate(final Map<String, String[]> inputParameter,
			final DBPropertiesDao dbPropertiesDao) throws ValidationException {
		for (final UpdateMovieSeenParameters p : UpdateMovieSeenParameters
				.values())
			if (!inputParameter.keySet().contains(p.toString()))
				throw new ValidationException("Missing Parameter "
						+ p.toString());
			else if (!inputParameter.get(p.toString())[0].matches(p
					.getRegEx(dbPropertiesDao)))
				throw new ValidationException("RegEx validation failed for "
						+ p.toString());
	}

	private String getRegEx(final DBPropertiesDao dbPropertiesDao)
			throws ValidationException {
		try {
			switch (this) {
			case imdbcode:
				return DBProperties.IMDBCODE_REGEX
						.getValueFrom(dbPropertiesDao);
			case seen:
				return DBProperties.MOVIESEEN_REGEX
						.getValueFrom(dbPropertiesDao);
			default:
				UpdateMovieSeenParameters.logger.error("Keine RegEx gefunden!");
				throw new ValidationException("Keine RegEx gefunden!");
			}
		} catch (final PersistenceException e) {
			UpdateMovieSeenParameters.logger.error(
					"Datenbankfehler beim Zugriff auf die Properties: "
							+ e.getMessage(), e);
			throw new ValidationException(
					"Datenbankfehler beim Zugriff auf die Properties: "
							+ e.getMessage(), e);
		}

	}
};
