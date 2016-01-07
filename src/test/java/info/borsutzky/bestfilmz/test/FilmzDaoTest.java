/**
 * 
 */
package info.borsutzky.bestfilmz.test;

import info.borsutzky.bestfilmz.database.DatabaseException;
import info.borsutzky.bestfilmz.database.PersistenceInitializer;
import info.borsutzky.bestfilmz.database.daos.FilmzDao;
import info.borsutzky.bestfilmz.database.entities.Filmz;
import info.borsutzky.bestfilmz.services.BfServletModule;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * tests the functionality of the FilmzDao
 * 
 * @author songoku
 * 
 */
public class FilmzDaoTest {

	private static Logger log = LogManager.getLogger(FilmzDaoTest.class
			.getName());
	private static FilmzDao dao;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FilmzDaoTest.log.info("creating injector and FilmzDao:");
		final Injector injector = Guice.createInjector(new BfServletModule());
		injector.getInstance(PersistenceInitializer.class);
		FilmzDaoTest.dao = injector.getInstance(FilmzDao.class);
	}

	/**
	 * tests adding a film to the database.
	 * 
	 * @throws DatabaseException
	 * 
	 */
	@Test
	public void testDao() throws DatabaseException {
		FilmzDaoTest.log.info("creating test filmz");

		final String testFilmImdbUrl = "http://fantasielink/2344";

		// evtl. vorhandene entity finden und löschen:
		Filmz testFilm = null;
		try {
			testFilm = FilmzDaoTest.dao.get(testFilmImdbUrl);
		} catch (final DatabaseException e) {
			FilmzDaoTest.log.info("TestFilm ist nicht in der Datenbank!");
		}
		FilmzDaoTest.log.info("is filmz entity null?" + (testFilm == null));
		if (testFilm != null) {
			FilmzDaoTest.log
					.info("test entity is already present in database! deleting...");
			FilmzDaoTest.dao.delete(testFilm);
			// test again:
			FilmzDaoTest.log.info("loading film again to ensure its deleted.");
			testFilm = FilmzDaoTest.dao.get(testFilmImdbUrl);
			Assert.assertTrue(testFilm == null);
		}

		// creating Entity
		final Filmz film = new Filmz();
		final String nameDeutsch = "testfilm deutsch äöü?ß";
		film.setNameDeutsch(nameDeutsch);
		final String nameOriginal = "网络孔子学院originalnamemitsonderzeichen sollte alles reinpassen :)";
		film.setNameOriginal(nameOriginal);
		final String imdbUrl = "http://fantasielink/2344";
		film.setImdbCode(imdbUrl);
		final java.sql.Date releaseDate = java.sql.Date.valueOf("2012-01-01");
		film.setReleaseDate(releaseDate);
		final BigDecimal rating = new BigDecimal(9.0);
		film.setImdbRating(rating);

		// saving Entity
		FilmzDaoTest.log.info("trying insert of testfilm");
		FilmzDaoTest.dao.save(film);
		FilmzDaoTest.log.info("inserting testfilm complete.");

		// check saved data: loading entity
		FilmzDaoTest.log.info("loading saved testFilm and compare values:");
		final Filmz filmReloaded = FilmzDaoTest.dao.get(imdbUrl);
		Assert.assertEquals(nameDeutsch, filmReloaded.getNameDeutsch());
		Assert.assertEquals(nameOriginal, filmReloaded.getNameOriginal());
		Assert.assertEquals(releaseDate, filmReloaded.getReleaseDate());
		Assert.assertEquals(rating, filmReloaded.getImdbRating());
		// check default values from db:
		Assert.assertEquals("false", filmReloaded.getSeen());
		// create reference Date:
		final Calendar calendar = new GregorianCalendar(1980, 0, 1);
		final Date defaultSeenDate = calendar.getTime();
		Assert.assertEquals(defaultSeenDate, filmReloaded.getSeenTime());
		// set seen true and check
		Assert.assertEquals("false", filmReloaded.getSeen());
		FilmzDaoTest.dao.updateSeen(testFilmImdbUrl, "true");
		Assert.assertEquals("true", filmReloaded.getSeen());

		// deleting entity
		FilmzDaoTest.log.info("deleting testFilm and terminating test!");
		FilmzDaoTest.dao.delete(film);
		FilmzDaoTest.log.info("FilmzDao test complete.");

	}
}
