package info.borsutzky.bestfilmz.greasemonkey.service;

import info.borsutzky.bestfilmz.database.daos.DBPropertiesDao;
import info.borsutzky.bestfilmz.services.DBProperties;

import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lists all valid AddMovieParameters. Allows validation.
 * 
 * @author songoku
 * @since 02.07.2013
 * 
 */
public enum AddMovieParameters {

	imdbcode, namedeutsch, nameoriginal, score, releasedate;

	private static Logger logger = LogManager
			.getLogger(AddMovieParameters.class.getName());

	/**
	 * Checks if all AddMovieParameters are present and have valid values.
	 * 
	 * @param inputParameter
	 * @throws ValidationException
	 */
	static void validate(final Map<String, String[]> inputParameter,
			final DBPropertiesDao dbPropertiesDao) throws ValidationException {
		for (final AddMovieParameters p : AddMovieParameters.values())
			if (!inputParameter.keySet().contains(p.toString()))
				throw new ValidationException("Missing Parameter "
						+ p.toString());
			else if (!inputParameter.get(p.toString())[0].matches(p
					.getRegEx(dbPropertiesDao)))
				throw new ValidationException("RegEx validation failed for "
						+ p.toString() + ": "
						+ inputParameter.get(p.toString())[0]);
	}

	/**
	 * Gibt den regulären Ausdruck zu einem bestimmten Parameter zurück.
	 * 
	 * @return {@link String} den regulären Ausdruck, der beschreibt, wie der
	 *         jeweilige Parameterwert aussehen muss.
	 * @throws ValidationException
	 *             exception
	 */
	private String getRegEx(final DBPropertiesDao dbPropertiesDao)
			throws ValidationException {
		try {
			switch (this) {
			case imdbcode:
				return DBProperties.IMDBCODE_REGEX
						.getValueFrom(dbPropertiesDao);

			case namedeutsch:
				return DBProperties.NAME_REGEX.getValueFrom(dbPropertiesDao);
			case nameoriginal:
				return DBProperties.NAME_REGEX.getValueFrom(dbPropertiesDao);
			case releasedate:
				return DBProperties.UNIXTIME_REGEX
						.getValueFrom(dbPropertiesDao);
			case score:
				return DBProperties.IMDBSCORE_REGEX
						.getValueFrom(dbPropertiesDao);
			default:
				AddMovieParameters.logger
						.error("Es konnte keine RegEx gefunden werden!");
				throw new ValidationException(
						"Es konnte kein RegEx gefunden werden.");
			}
		} catch (final PersistenceException e) {
			AddMovieParameters.logger
					.error("Datenbankproblem beim Abrufen der Properties: "
							+ e.getMessage());
			throw new ValidationException(
					"Datenbankproblem beim Abrufen der Properties: "
							+ e.getMessage(), e);
		}

	}
}
