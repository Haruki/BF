package info.borsutzky.bestfilmz.services;

/**
 * Exception zeigt Fehler der Service Ebene an.
 * 
 * @author songoku
 * @since 14.02.2013
 * 
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {

	public ServiceException(final String message) {
		super(message);

	}

	public ServiceException(final Exception e, final String message) {
		super(message, e);
	}
}
