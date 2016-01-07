package info.borsutzky.bestfilmz.database.daos;

import info.borsutzky.bestfilmz.database.DatabaseException;
import info.borsutzky.bestfilmz.database.entities.Filmz;
import info.borsutzky.bestfilmz.database.entities.FilmzEntityListener;
import info.borsutzky.bestfilmz.database.entities.Filmz_;

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;

/**
 * Diese Klasse stellt methoden zum laden, speichern usw. von Filmen zur
 * verfügung.
 * 
 * @author songoku
 * 
 */

@NamedQueries({ @NamedQuery(name = "deleteFilm", query = "delete from filmz where imdb_link = :imdbCode") })
public class FilmzDao {

	@Inject
	FilmzEntityListener fel;

	private final EntityManager em;
	private static Logger log = LogManager.getLogger(FilmzDao.class.getName());

	/**
	 * default constructor.
	 * 
	 * @param em
	 *            {@link EntityManager}
	 */
	@Inject
	public FilmzDao(final EntityManager em) {
		FilmzDao.log.info("Is EntityManager null: " + (em == null));
		this.em = em;
	}

	/**
	 * Fügt einen film der Datenbank hinzu.
	 * 
	 * @param film
	 *            {@link Filmz}
	 * @return true if success, else false.
	 */
	public void save(final Filmz film) throws DatabaseException {
		this.em.getTransaction().begin();
		try {
			this.em.persist(film);
			this.em.getTransaction().commit();
			// this.em.clear(); // leider notwendig, da trigger die datenbank
			// noch
			// weiter veränder beim insert und deswegen der
			// persistencecontext nicht mehr synchron mit
			// der Datenbank ist.
			this.em.refresh(film);
		} catch (final PersistenceException e) {
			FilmzDao.log.error(e.getMessage());
			e.printStackTrace();
			FilmzDao.log.info("rolling back transaction.");
			this.em.getTransaction().rollback();
			throw new DatabaseException("saving Entity failed!", e);
		}
	}

	/**
	 * finds the film specified by the moviedid (primary key)
	 * 
	 * @param id
	 * @return
	 */
	public Filmz get(final int movieid) throws DatabaseException {
		Filmz filmz = null;
		try {
			filmz = this.em.find(Filmz.class, movieid);
			FilmzDao.log.debug("filmz null?: " + filmz == null);
		} catch (final PersistenceException e) {
			FilmzDao.log.error(e);
			throw new DatabaseException("get Entity by movieid " + movieid
					+ " failed!", e);
		}
		return filmz;
	}

	/**
	 * Findet in der Datenbank den passenden Filmz zu der spezifizierten IMDb
	 * URL.
	 * 
	 * @param imdbCode
	 *            {@link String}.
	 * @return {@link Filmz} die passende Filmz entity.
	 */
	public Filmz get(final String imdbCode) throws DatabaseException {
		try {
			final CriteriaBuilder criteriaBuilder = this.em
					.getCriteriaBuilder();
			final CriteriaQuery<Filmz> query = criteriaBuilder
					.createQuery(Filmz.class);
			final Root<Filmz> filmz = query.from(Filmz.class);
			final Predicate predicate = criteriaBuilder.equal(
					filmz.get(Filmz_.imdbCode), imdbCode);
			query.where(predicate);
			final Filmz result = this.em.createQuery(query).getSingleResult();
			return result;
		} catch (final PersistenceException e) {
			if (e instanceof NoResultException) {
				FilmzDao.log.warn("No film found with imdbCode = {}", imdbCode);
				FilmzDao.log.warn(e.getMessage());
				throw new DatabaseException("No film found with imdbCode = "
						+ imdbCode, e);
			}
			FilmzDao.log.error(e);
			throw new DatabaseException("getting Entity by imdbCode: "
					+ imdbCode + " failed!", e);
		}
	}

	/**
	 * Changes the Seen status of a Film in the Database.
	 * 
	 * @param imdbCode
	 *            {@link String}
	 * @param seen
	 *            {@link String} = TRUE or FALSE;
	 * @throws DatabaseException
	 */
	public void updateSeen(final String imdbCode, final String seen)
			throws DatabaseException {
		final Filmz film = this.get(imdbCode);
		film.setSeen(seen);
		this.save(film);
	}

	/**
	 * löscht einen Film aus der Tabelle Filmz mit der angegebenen Imdb URL.
	 * 
	 * @param imdbCode
	 *            {@link String} imdbCode.
	 * @return {@link Integer} Anzahl der gelöschten Filme. (immer 1 da Imdb URL
	 *         unique).
	 */
	public int delete(final String imdbCode) throws DatabaseException {
		try {
			this.em.getTransaction().begin();
			final TypedQuery<Filmz> filmzQuery = this.em.createNamedQuery(
					"deleteFilm", Filmz.class);
			filmzQuery.setParameter("imdbCode", imdbCode);
			final int numDeleted = filmzQuery.executeUpdate();
			this.em.getTransaction().commit();
			return numDeleted;
		} catch (final PersistenceException e) {
			FilmzDao.log.error("Deleting film with imdbCode = " + imdbCode
					+ " FAILED!", e);
			if (this.em.getTransaction().isActive())
				this.em.getTransaction().rollback();
			throw new DatabaseException("Deleting film with imdbCode = "
					+ imdbCode + " FAILED!", e);
		}
	}

	/**
	 * Deletes a given filmz Entity.
	 * 
	 * @param film
	 *            {@link Filmz} der zu löschende Film.
	 * @throws DatabaseException
	 *             databaseException
	 */
	public void delete(final Filmz film) throws DatabaseException {
		try {
			this.em.getTransaction().begin();
			this.em.remove(film);
			this.em.getTransaction().commit();
		} catch (final PersistenceException e) {
			FilmzDao.log.error("Deleting " + film.toString() + " FAILED!", e);
			if (this.em.getTransaction().isActive())
				this.em.getTransaction().rollback();
			throw new DatabaseException("Deleting " + film.toString()
					+ " FAILED!", e);
		}
	}
}
