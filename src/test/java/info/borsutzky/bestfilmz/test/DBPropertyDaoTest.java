package info.borsutzky.bestfilmz.test;

import info.borsutzky.bestfilmz.database.PersistenceInitializer;
import info.borsutzky.bestfilmz.database.daos.DBPropertiesDao;
import info.borsutzky.bestfilmz.database.entities.DBProperty;
import info.borsutzky.bestfilmz.services.BfServletModule;
import info.borsutzky.bestfilmz.services.NamesLibrary;
import junit.framework.Assert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class DBPropertyDaoTest {

	private static Logger logger = LogManager.getLogger(DBPropertyDaoTest.class
			.getName());
	private static DBPropertiesDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final Injector injector = Guice.createInjector(new BfServletModule());
		injector.getInstance(PersistenceInitializer.class);
		DBPropertyDaoTest.dao = injector.getInstance(DBPropertiesDao.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Testet, ob das imdbLinkPrefix Property korrekt aus der DB geladen werden
	 * kann.
	 */
	@Test
	public void testFindByPropertyName() {
		DBPropertyDaoTest.logger.info("starting DBPropertyTest...");
		final DBProperty imdbPrefixProperty = DBPropertyDaoTest.dao
				.findByPropertyName(NamesLibrary.DBPropertyNames.imdb_link_prefix
						.toString());
		final String imdbLinkPrefix = imdbPrefixProperty.getPropValue();
		Assert.assertNotNull(imdbLinkPrefix);
		Assert.assertTrue(imdbLinkPrefix.length() > 0);
		Assert.assertTrue(imdbLinkPrefix.startsWith("http"));
		DBPropertyDaoTest.logger
				.info("DBPropertyTest completed successfully...");
	}

}
