package py.com.proyectobase.bc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import py.com.proyectobase.bc.PermisoBC;
import py.com.proyectobase.dao.PermisoDao;
import py.com.proyectobase.domain.Permiso;

@Component
@Scope("request")
public class PermisoBCImpl extends BusinessControllerImpl<Permiso> implements
		PermisoBC {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PermisoDao permisoDao;

	@Override
	public PermisoDao getDAOInstance() {
		return permisoDao;
	}

}
