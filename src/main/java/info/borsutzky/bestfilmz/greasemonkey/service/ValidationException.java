package info.borsutzky.bestfilmz.greasemonkey.service;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValidationException(final String message) {
		super(message);
	}

	public ValidationException(final String message, final Exception e) {
		super(message, e);
	}
}
