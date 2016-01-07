package info.borsutzky.bestfilmz.services;

import info.borsutzky.bestfilmz.database.DatabaseException;
import info.borsutzky.bestfilmz.database.daos.FilmzDao;
import info.borsutzky.bestfilmz.database.entities.Filmz;
import info.borsutzky.bestfilmz.greasemonkey.service.Movie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;

/**
 * Diese Klasse bildet den eigentlichen Service. Kern sind die drei Operationen,
 * die vom Greasemonkey-Script aufgerufen werden.
 * 
 * @author songoku
 * @since 12.02.2013
 * 
 */
public class BestFilmzService {

	@Inject
	private FilmzDao filmzDao;

	private static Logger log = LogManager.getLogger(BestFilmzService.class);

	public BestFilmzService() {
	}

	/**
	 * Methode zum Hinzufügen eines Movies zur Datenbank.
	 * 
	 * @param movie
	 *            {@link Movie}
	 */
	public void serviceAddMovie(final Filmz film) throws ServiceException {
		try {
			this.filmzDao.save(film);
			BestFilmzService.log.debug("Successfully inserted Movie: {}",
					film.getImdbCode());
		} catch (final DatabaseException e1) {
			BestFilmzService.log.error(e1);
			throw new ServiceException(e1, "Adding Movie failed!");
		}
	}

	/**
	 * sucht anhand der imdb url den film aus der Datenbank und gibt den
	 * passenden Film zurück.
	 * 
	 * @param imdburl
	 *            {@link String}
	 */
	public Filmz serviceGetMovie(final String imdburl) throws ServiceException {
		try {
			final Filmz filmzEntity = this.filmzDao.get(imdburl);
			return filmzEntity;
		} catch (final DatabaseException e) {
			BestFilmzService.log.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	/**
	 * Methode zum updaten eines films.
	 * 
	 * @param imdburl
	 *            {@link String}
	 * @param seen
	 *            {@link Boolean}
	 */
	public void serviceUpdateMovie(final String imdburl, final boolean seen)
			throws ServiceException {
		try {
			this.filmzDao.updateSeen(imdburl, String.valueOf(seen));
			BestFilmzService.log
					.debug("Successfully updated movie {} to status {}",
							imdburl, seen);
		} catch (final DatabaseException e1) {
			BestFilmzService.log.error(e1.getMessage());
			throw new ServiceException(e1, "Updating Movie failed!");
		}
	}

}
