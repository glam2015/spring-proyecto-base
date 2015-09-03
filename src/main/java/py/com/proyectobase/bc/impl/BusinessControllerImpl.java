package py.com.proyectobase.bc.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import py.com.proyectobase.bc.BusinessController;
import py.com.proyectobase.dao.Dao;
import py.com.proyectobase.domain.Model;
import py.una.cnc.lib.core.util.AppLogger;

/**
 * 
 * @author dcerrano
 */
public abstract class BusinessControllerImpl<T extends Model> implements
		BusinessController<T> {

	private static final long serialVersionUID = 1L;
	private boolean newRecord;
	private T lastBeanRetrieved;
	private AppLogger appLogger;
	private boolean permissionRequired;

	public BusinessControllerImpl() {

		permissionRequired = true;
	}

	@Override
	public boolean isNewRecord() {

		return newRecord;
	}

	@Override
	public void setNewRecord(boolean newRecord) {

		this.newRecord = newRecord;
	}

	@Override
	public void save(T bean) {

		if (isNewRecord()) {
			create(bean);
		} else {
			edit(bean);
		}
		lastBeanRetrieved = bean;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void create(T obj) {

		getDAOInstance().create(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void edit(T obj) {

		getDAOInstance().edit(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void destroy(T obj) {

		getDAOInstance().destroy(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public T find(Long id) {

		lastBeanRetrieved = getDAOInstance().find(id);
		return lastBeanRetrieved;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public T findByCode(String code) {

		lastBeanRetrieved = getDAOInstance().findByCode(code);
		return lastBeanRetrieved;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<T> findEntities() {

		return getDAOInstance().findEntities();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<T> findEntities(boolean activo) {

		return getDAOInstance().findEntities(activo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean userCanSelect() {

		return checkPermission("_sel");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean userCanInsert() {

		return checkPermission("_ins");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean userCanUpdate() {

		return checkPermission("_upd");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean userCanDelete() {

		return checkPermission("_del");
	}

	private boolean checkPermission(String permission) {
		/*
		 * Creo que es mejor controlar permisos a nivel de controlador de vista,
		 * para no controlar a cada rato return !isPermissionRequired() ||
		 * getAuthorizer().userHasPermissions(
		 * getPermissionBaseName().concat(permission));
		 */
		return true;
	}

	@Override
	public String getPermissionBaseName() {

		return getDAOInstance().getEntityName();
	}

	@Override
	public boolean isPermissionRequired() {

		return permissionRequired;
	}

	@Override
	public void setPermissionRequired(boolean permissionRequired) {

		this.permissionRequired = permissionRequired;
	}

	@Override
	public abstract Dao<T> getDAOInstance();

	@Override
	public T getLastBeanRetrieved() {

		return lastBeanRetrieved;
	}

	public void setLastBeanRetrieved(T lastBeanRetrieved) {

		this.lastBeanRetrieved = lastBeanRetrieved;
	}

	protected AppLogger getLogger() {

		if (appLogger == null) {
			appLogger = new AppLogger(getClass().getCanonicalName());
		}
		return appLogger;
	}
}
