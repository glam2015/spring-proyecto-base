package py.com.proyectobase.dao.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import py.com.proyectobase.dao.RolDao;
import py.com.proyectobase.domain.Rol;

@Repository
@Scope("request")
public class RolDaoImpl extends DaoImpl<Rol> implements RolDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
