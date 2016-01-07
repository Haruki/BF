package info.borsutzky.bestfilmz.database;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

/**
 * startet den PersistService, wenn der servlet filter das nicht erledigen kann.
 * z.B. für tests.
 * 
 * @author songoku
 * 
 */
public class PersistenceInitializer {

	@Inject
	PersistenceInitializer(PersistService service) {
		service.start();
	}

}
