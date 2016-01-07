package info.borsutzky.bestfilmz.database.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * provides default values for several entity fields.
 * 
 * @author songoku
 * 
 */
public class EntityDefaultValues {

	private static Logger log = LogManager.getLogger(EntityDefaultValues.class
			.getName());

	public enum Filmz {
		SEEN_TIME, SEEN;

		/**
		 * returns the default value for the enum, naming the entity field.
		 * 
		 * @return Object den Default Wert fuer die entsprechende Spalte der
		 *         Tabelle Filmz.
		 */
		public Object getDefault() {
			switch (this) {
			case SEEN_TIME:
				final SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				try {
					return sdf.parse("1980-01-01 00:00:00");
				} catch (final ParseException e) {
					EntityDefaultValues.log.error(
							"parsing dateString failed! {}", e.getMessage());
					return null;
				}
			case SEEN:
				return "false";
			default:
				return null;
			}
		}
	}

}
