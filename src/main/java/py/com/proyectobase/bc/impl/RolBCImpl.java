package py.com.proyectobase.bc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import py.com.proyectobase.bc.RolBC;
import py.com.proyectobase.dao.RolDao;
import py.com.proyectobase.domain.Rol;

@Component
@Scope("request")
public class RolBCImpl extends BusinessControllerImpl<Rol> implements RolBC {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private RolDao rolDao;

	@Override
	public RolDao getDAOInstance() {
		return rolDao;
	}

}
