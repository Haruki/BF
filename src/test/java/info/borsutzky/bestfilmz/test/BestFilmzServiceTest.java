package info.borsutzky.bestfilmz.test;

import info.borsutzky.bestfilmz.database.PersistenceInitializer;
import info.borsutzky.bestfilmz.database.entities.Filmz;
import info.borsutzky.bestfilmz.services.BestFilmzService;
import info.borsutzky.bestfilmz.services.BfServletModule;
import info.borsutzky.bestfilmz.services.ServiceException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class BestFilmzServiceTest {

	private static Logger logger = LogManager
			.getLogger(BestFilmzServiceTest.class.getName());
	private static BestFilmzService bfs;
	private static TestDataManager tdm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final Injector injector = Guice.createInjector(new BfServletModule());
		injector.getInstance(PersistenceInitializer.class);
		BestFilmzServiceTest.bfs = injector.getInstance(BestFilmzService.class);
		BestFilmzServiceTest.tdm = injector.getInstance(TestDataManager.class);
		BestFilmzServiceTest.tdm.insertTestFilm();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BestFilmzServiceTest.tdm.deleteTestFilm();
	}

	@Test
	public void testServiceAddMovie() throws ParseException, ServiceException {
		// Create TestMovie2:
		final Filmz filmz = new Filmz();
		final String imdbCode = "http://www.imdb.com/tt098765";
		final BigDecimal rating = new BigDecimal(1.0);
		final String nameDeutsch = "Test Film 2";
		final String nameOriginal = "Test Movie the Second";
		filmz.setImdbCode(imdbCode);
		filmz.setImdbRating(rating);
		filmz.setNameDeutsch(nameDeutsch);
		filmz.setNameOriginal(nameOriginal);
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd HH:mm:ss");
		final java.sql.Date testTimeStamp = new java.sql.Date(sdf.parse(
				"2004.05.18 00:00:00").getTime());
		filmz.setReleaseDate(testTimeStamp);
		// final Timestamp createTimestamp = new
		// Timestamp(sdf.parse("1980.01.01")
		// .getTime());
		BestFilmzServiceTest.bfs.serviceAddMovie(filmz);
		final Filmz filmz2 = BestFilmzServiceTest.bfs.serviceGetMovie(imdbCode);
		Assert.assertEquals(imdbCode, filmz2.getImdbCode());
		Assert.assertEquals(rating, filmz2.getImdbRating());
		Assert.assertEquals(nameDeutsch, filmz2.getNameDeutsch());
		Assert.assertEquals(nameOriginal, filmz.getNameOriginal());
		Assert.assertEquals(testTimeStamp.getTime(), filmz2.getReleaseDate()
				.getTime());
		// Assert.assertEquals(createTimestamp, filmz2.getCreateTime());
		Assert.assertEquals(new Integer(1),
				BestFilmzServiceTest.tdm.delete098765());
	}

	@Test
	public void testServiceGetMovie() throws ServiceException {
		final Filmz film = BestFilmzServiceTest.bfs
				.serviceGetMovie("http://www.imdb.com/tt999999");
		BestFilmzServiceTest.logger.info(film.getImdbCode());
		BestFilmzServiceTest.logger.info(film.getImdbRating());
		BestFilmzServiceTest.logger.info(film.getCreateTime());
		Assert.assertEquals("http://www.imdb.com/tt999999", film.getImdbCode());
		Assert.assertEquals(new BigDecimal("9.9"), film.getImdbRating());
	}

	@Test
	public void testServiceUpdateMovie() {
	}

}
