package info.borsutzky.bestfilmz.greasemonkey.service;

import info.borsutzky.bestfilmz.database.daos.DBPropertiesDao;

import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.servlet.RequestParameters;

/**
 * Repräsentiert die ServletParameter. Alle Methoden geben korrekte Parameter
 * zurück oder eine Exception, falls Fehler vorliegen.
 * 
 * @author songoku
 * @since 02.07.2013
 * 
 */
public class ValidatedInputParameters {

	private static Logger logger = LogManager
			.getLogger(ValidatedInputParameters.class.getName());

	@Inject
	@RequestParameters
	private Map<String, String[]> inputParameter;

	@Inject
	DBPropertiesDao dbPropertiesDao;

	public Map<String, String[]> getInputParameter() throws ValidationException {
		ValidatedInputParameters.logger.info("starting validation...");
		ServiceParameters.validate(this.inputParameter, this.dbPropertiesDao);
		ValidatedInputParameters.logger
				.info("successfully completed validation.");
		return this.inputParameter;
	}

}
