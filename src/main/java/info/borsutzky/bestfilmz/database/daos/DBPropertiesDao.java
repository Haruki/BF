package info.borsutzky.bestfilmz.database.daos;

import info.borsutzky.bestfilmz.database.entities.DBProperty;
import info.borsutzky.bestfilmz.database.entities.DBProperty_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;

/**
 * Dao fuer den Zugriff auf die Datenbank Properties. (z.B. imdb-url erweiterung
 * etc.)
 * 
 * @author songoku
 * @since 08.06.2013
 * 
 */
public class DBPropertiesDao extends AbstractDao<DBProperty, Integer> {

	@Inject
	public DBPropertiesDao(final EntityManager em) {
		super(em);
	}

	/**
	 * Sucht eine Datenbank-Property anhand des PropertyNamens (unique) und gibt
	 * den gefundenen {@link DBProperty} zurück.
	 * 
	 * @param propertyName
	 *            {@link String}
	 * @return DBPropertyName dbPropertyName
	 */
	public DBProperty findByPropertyName(final String propertyName) {
		final CriteriaBuilder criteriaBuilder = this.getEntityManager()
				.getCriteriaBuilder();
		final CriteriaQuery<DBProperty> criteriaQuery = criteriaBuilder
				.createQuery(DBProperty.class);
		final Root<DBProperty> dbPropertyRoot = criteriaQuery
				.from(DBProperty.class);
		criteriaQuery.select(dbPropertyRoot).where(
				criteriaBuilder.equal(dbPropertyRoot.get(DBProperty_.propName),
						propertyName));
		return this.getEntityManager().createQuery(criteriaQuery)
				.getSingleResult();
	}
}
