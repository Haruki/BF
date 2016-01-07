package info.borsutzky.bestfilmz.services;

import info.borsutzky.bestfilmz.database.daos.DBPropertiesDao;

/**
 * Central class for accessing dbproperties. DbProperties represent the
 * configuration.
 * 
 * @author songoku
 * @since 05.07.2013
 * 
 */
public enum DBProperties {
	NAME_REGEX, IMDBCODE_REGEX, UNIXTIME_REGEX, IMDBSCORE_REGEX, MOVIESEEN_REGEX, IMDB_LINK_PREFIX;

	/**
	 * Returns the configured value of the current property from the database.
	 * Attention: throws unchecked PersistenceException on errors.
	 * 
	 * @return {@link String} The Value from the Database.
	 */
	public String getValueFrom(final DBPropertiesDao dbPropertiesDao) {
		return dbPropertiesDao.findByPropertyName(this.toString())
				.getPropValue();
	}

}
