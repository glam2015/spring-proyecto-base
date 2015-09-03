package py.com.proyectobase.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import py.com.proyectobase.dao.Dao;
import py.com.proyectobase.domain.Model;
import py.com.proyectobase.exception.DAOException;
import py.com.proyectobase.exception.ValidationException;
import py.com.proyectobase.main.SesionUsuario;
import py.una.cnc.lib.core.util.AppLogger;

/**
 * 
 * @author dcerrano
 */
@Repository
@Scope("request")
public abstract class DaoImpl<T extends Model> implements Dao<T> {

	private static final long serialVersionUID = 1L;
	private static final String UNCHECKED = "unchecked";
	private static final String ENTITY = "ENTITY";
	private Class<T> entityClass;
	private String entityName;
	private String idName;
	private Validator validator;
	private AppLogger appLogger;
	private Map<String, String> errorsMap;
	@Autowired
	private SesionUsuario sesionUsuario;
	@Autowired
	private MessageSource messageSource;

	@PostConstruct
	public void init() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public void create(T object) {

		validate(object);
		getEntityManager().persist(object);
		getLogger().info("Registro [{}] creado", object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public void edit(T object) {

		validate(object);
		getEntityManager().merge(object);
		getLogger().info("Objeto [{}] editado", object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public void destroy(T obj) {

		getLogger().info("Borrando registro [{}]", obj);
		/*
		 * EntityManager#remove() works only on entities which are managed in
		 * the current transaction/context. If you're retrieving the entity in
		 * an earlier transaction, storing it in the HTTP session and then
		 * attempting to remove it in a different transaction/context, this just
		 * won't work(Removing a detached instance).
		 */
		getEntityManager().remove(
				getEntityManager().contains(obj) ? obj : getEntityManager()
						.merge(obj));
		getLogger().info("Registro [{}] borrado", obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public T find(Long id) {

		getLogger().info("Buscando registro. Entidad: {}, ID: {}",
				getEntityName(), id);

		try {
			if (id == null) {
				throw new NoResultException();
			}
			return getEntityManager().find(getEntityClass(), id);
		} catch (NoResultException e) {
			getLogger().info("No se encontró registro. Entidad: {}, ID:{}", e,
					getEntityName(), id);
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public T findByCode(String code) throws DAOException {

		getLogger().info("findByCode: {} -> '{}' ", getEntityName(), code);
		return findEntityByCondition("WHERE " + getEntityName()
				+ ".codigo = ?1 ", code);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public List<T> findEntities() throws DAOException {

		return findEntities(true, -1, -1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<T> findEntities(boolean activo) {

		return findEntitiesByCondition("WHERE activo = ?1", activo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings(UNCHECKED)
	@Transactional
	public List<T> findEntitiesByCondition(String where, Object... params) {

		String sql = "select object(ENTITY) from ENTITY as ENTITY ";
		sql = sql.replace("ENTITY", getEntityName());
		Query query = createQuery(sql, where, params);
		List<T> list = query.getResultList();
		getLogger().info("Registros encontrados: {}", list.size());
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public T findEntityByCondition(String where, Object... params)
			throws DAOException {

		List<T> list = findEntitiesByCondition(where, params);
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Transactional
	@SuppressWarnings(UNCHECKED)
	private List<T> findEntities(boolean all, int maxResults, int firstResult) {

		EntityManager em = getEntityManager();
		String sql = "select object(ENTITY) from ENTITY as ENTITY";
		sql = sql.replace(ENTITY, getEntityName());
		getLogger().debug("Realizando consulta: {}", sql);

		Query query = em.createQuery(sql);
		if (!all) {
			query.setMaxResults(maxResults);
			query.setFirstResult(firstResult);
		}
		List<T> list = query.getResultList();
		getLogger().info("Registros encontrados: {}", list.size());
		return list;
	}

	@Override
	@Transactional
	public void destroyEntitiesByCondition(String where, Object... params) {

		String sql = "DELETE FROM ".concat(getEntityName());
		Query query = createQuery(sql, where, params);
		query.executeUpdate();
	}

	@Transactional
	protected Query createQuery(String sql, String where, Object... params) {

		String parameters = "";

		if (where != null) {
			sql = sql.concat(" ".concat(where));
		}

		Query query = getEntityManager().createQuery(sql);

		if (params != null) {
			int index = 1;
			for (Object param : params) {
				query = query.setParameter(index++, param);
				parameters = parameters + param + ", ";
			}
		}
		getLogger().debug("Ejecutando sentencia: {}, params: [{}]", sql,
				parameters);

		return query;
	}

	@Override
	@Transactional
	public Long countEntitiesByCondition(String where, Object... params) {

		String sql = "SELECT COUNT(*) FROM ".concat(getEntityName());
		Query query = createQuery(sql, where, params);
		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings(UNCHECKED)
	public Class<T> getEntityClass() {

		if (entityClass == null) {
			ParameterizedType superClass = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			entityClass = (Class<T>) superClass.getActualTypeArguments()[0];
		}
		return entityClass;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEntityClass(Class<T> clazz) {

		entityClass = clazz;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdName() {

		if (idName == null) {
			for (Field field : getEntityClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					idName = field.getName().toUpperCase();
					break;
				}
			}
		}
		return idName;
	}

	protected EntityManager getEntityManager() {

		return sesionUsuario.getEM();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEntityName() {

		if (entityName == null) {
			Entity entity = getEntityClass().getAnnotation(Entity.class);

			if (entity.name() == null || entity.name().compareTo("") == 0) {
				entityName = getEntityClass().getSimpleName();
			} else {
				entityName = entity.name();
			}
		}
		return entityName;
	}

	/**
	 * @param obj
	 *            el registro cuyo valor de ID se desea obtener Retorna el valor
	 *            del id de un registro
	 */
	protected Object getIdValue(Object obj) {

		if (obj == null) {
			return null;
		}

		try {
			for (Field field : obj.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					field.setAccessible(true);
					Object data = field.get(obj);
					field.setAccessible(false);
					return data;
				}
			}
			return null;
		} catch (IllegalAccessException iae) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(T object) {

		getLogger().info("Validando registro [{}]", object);
		Set<ConstraintViolation<T>> constraintViolations = getValidator()
				.validate(object);
		getErrorsMap().clear();

		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			String fieldName = constraintViolation.getPropertyPath().toString();
			/*
			 * Para identificar el campo con errores, se crea la llave
			 * conformada por el nombre de la entidad + el nombre del atributo
			 */
			getErrorsMap().put(
					getEntityClass().getSimpleName() + "." + fieldName,
					constraintViolation.getMessage());
		}
		/*
		 * permitir a las subclases que puedan agregar mas errores de
		 * validacion, sobreescribiendo el siguiente metodo
		 */
		addValidation(object);
		if (getErrorsMap().size() > 0) {
			getLogger().info("Errores de validacion de {}: {}",
					getEntityName(), getErrorsMap());
			throw new ValidationException(getErrorsMap());
		}
		getLogger().info("Registro OK");
	}

	/**
	 * validate(T object) valida teniendo en cuenta las restricciones de los
	 * modelos, sin embargo algunas validaciones como @Unique es necesario
	 * realizar en forma 'manual', consultando a la BD. Por lo tanto, este
	 * método se sobreescribe y se agregan los posibles errores de validación
	 */
	protected void addValidation(T object) {

	}

	protected Validator getValidator() {

		if (validator == null) {
			ValidatorFactory factory = Validation
					.buildDefaultValidatorFactory();
			validator = factory.getValidator();
		}
		return validator;
	}

	protected AppLogger getLogger() {

		if (appLogger == null) {
			appLogger = new AppLogger(getClass().getCanonicalName());
		}
		return appLogger;
	}

	public Map<String, String> getErrorsMap() {

		if (errorsMap == null) {
			errorsMap = new HashMap<String, String>();
		}
		return errorsMap;
	}

	public void appendError(String fieldName, String error) {

		getErrorsMap().put(fieldName, error);
	}

	@Override
	public Long getMax(String columnName) {

		return getMax(columnName, null);
	}

	@Override
	public Long getMax(String columnName, String where, Object... params) {

		String sql = "SELECT MAX(" + columnName + ") FROM " + getEntityName();

		Query query = createQuery(sql, where, params);
		Long max = (Long) query.getSingleResult();
		if (max == null) {
			max = 0L;
		}
		return max;
	}

}
