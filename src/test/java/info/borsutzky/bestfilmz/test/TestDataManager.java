package info.borsutzky.bestfilmz.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestDataManager {

	private static Logger logger = LogManager.getLogger(TestDataManager.class
			.getName());
	private Connection connection;

	/**
	 * Konstruktor.
	 */
	public TestDataManager() {
		try {
			this.connection = DriverManager.getConnection(
					// "jdbc:mysql://localhost/BestFilmz", "tomcat",
					// "gokutomcat");
					"jdbc:oracle:thin:@192.168.0.105:1521:xe", "bestfilmz",
					"bestfilmz");

		} catch (final SQLException e) {
			TestDataManager.logger.error(e);
		}
	}

	private Connection getConnection() {
		return this.connection;
	}

	/**
	 * Erstellt in der Datenbank einen einfachen Testfilm. Nur Tabelle Filmz.
	 * 
	 * @return {@link Integer} Anzahl eingefügter Filme.
	 */
	public Integer insertTestFilm() {
		final Connection con = this.getConnection();
		PreparedStatement st;
		try {
			st = con.prepareStatement("insert into filmz values(-10,'testname_deutsch','testname_original','http://www.imdb.com/tt999999',9.9,DEFAULT,DEFAULT,DEFAULT,DEFAULT)");
			return st.executeUpdate();
		} catch (final SQLException e) {
			TestDataManager.logger.error(e);
		}
		return null;
	}

	public Integer deleteTestFilm() {
		final Connection con = this.getConnection();
		PreparedStatement st;
		try {
			st = con.prepareStatement("delete from filmz where movieid = -10");
			return st.executeUpdate();
		} catch (final SQLException e) {
			TestDataManager.logger.error(e.getMessage());
		}
		return null;
	}

	public Integer delete098765() {
		final Connection con = this.getConnection();
		PreparedStatement st;
		try {
			st = con.prepareStatement("delete from filmz where imdb_code like '%098765%'");
			return st.executeUpdate();
		} catch (final SQLException e) {
			TestDataManager.logger.error(e.getMessage());
		}
		return null;
	}
}
