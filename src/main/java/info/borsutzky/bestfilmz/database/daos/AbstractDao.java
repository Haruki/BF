package info.borsutzky.bestfilmz.database.daos;

import info.borsutzky.bestfilmz.database.DatabaseException;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;

/**
 * Abstract base class for default crud operations loading and persisting.
 * 
 * @author songoku
 * @since 21.10.2012
 * 
 * @param <ENTITY>
 *            entity the dao works on.
 * @param <PK>
 *            the entity's primary key type. (mostly integer)
 */
public class AbstractDao<ENTITY, PK extends Serializable> {

	private static Logger log = LogManager.getLogger(AbstractDao.class
			.getName());
	private final EntityManager entityManager;
	private final Class<ENTITY> persistentClass;

	/**
	 * Macht den EntityManager für erweiternde Klassen verfügbar.
	 * 
	 * @return {@link EntityManager} entityManager.
	 */
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	/**
	 * default Constructor for use with google guice.
	 * 
	 * @param entityManager
	 */
	@Inject
	@SuppressWarnings("unchecked")
	public AbstractDao(final EntityManager entityManager) {
		this.entityManager = entityManager;
		this.persistentClass = (Class<ENTITY>) ((ParameterizedType) this
				.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Default method for persisting an entity.
	 * 
	 * @param entity
	 *            of Type ENTITY
	 * @throws DatabaseException
	 *             in case of an error.
	 */
	public void save(final ENTITY entity) throws DatabaseException {
		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.persist(entity);
			this.entityManager.getTransaction().commit();
		} catch (final Exception e) {
			AbstractDao.log.error(e);
			this.entityManager.getTransaction().rollback();
			throw new DatabaseException("Saving Entity failed!", e);
		}
	}

	/**
	 * default getter for entitys.
	 * 
	 * @param primaryKey
	 *            of type PK
	 * @return ENTITY the entity of the extending class.
	 * @throws DatabaseException
	 *             in case of an error.
	 */
	public ENTITY get(final PK primaryKey) throws DatabaseException {
		try {
			return this.entityManager.find(this.getEntityClass(), primaryKey);
		} catch (final Exception e) {
			AbstractDao.log.error(
					"could not find a matching entity of type {} with pk={}",
					this.getEntityClass().toString(), primaryKey);
			throw new DatabaseException("not found "
					+ this.getEntityClass().toString() + " pk = " + primaryKey,
					e);
		}
	}

	/**
	 * Finds all entities of the type the extending dao specifies.
	 * 
	 * @return List of Entities
	 * @throws DatabaseException
	 *             in case of an error.
	 */
	public List<ENTITY> getAll() throws DatabaseException {
		try {
			final CriteriaBuilder criteriaBuilder = this.entityManager
					.getCriteriaBuilder();
			final CriteriaQuery<ENTITY> query = criteriaBuilder
					.createQuery(this.getEntityClass());
			// Root<ENTITY> entityRoot = query.from(this.getEntityClass());
			final List<ENTITY> resultList = this.entityManager.createQuery(
					query).getResultList();
			return resultList;
		} catch (final Exception e) {
			AbstractDao.log.error("getting all entities failed!", e);
			throw new DatabaseException("not found "
					+ this.getEntityClass().toString(), e);
		}
	}

	/**
	 * getter for implementing classes.
	 * 
	 * @return Class<ENTITY> the class object of the entity the extending dao
	 *         works with.
	 */
	protected Class<ENTITY> getEntityClass() {
		return this.persistentClass;
	}
}
