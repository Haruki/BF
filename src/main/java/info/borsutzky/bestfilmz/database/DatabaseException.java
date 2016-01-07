package info.borsutzky.bestfilmz.database;

/**
 * Wrapper for all errors from database.
 * 
 * @author songoku
 * @since 21.10.2012
 * 
 */
public class DatabaseException extends Exception {

	/**
	 * Constructor for wrapping root causes.
	 * 
	 * @param string
	 *            {@link String}
	 * @param e
	 *            {@link Exception}
	 */
	public DatabaseException(String string, Exception e) {
		super(string, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
