package py.com.proyectobase.bc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import py.com.proyectobase.bc.PermisoUsuarioBC;
import py.com.proyectobase.dao.PermisoUsuarioDao;
import py.com.proyectobase.domain.PermisoUsuario;
import py.com.proyectobase.domain.Usuario;

@Component
@Scope("request")
public class PermisoUsuarioBCImpl extends
		BusinessControllerImpl<PermisoUsuario> implements PermisoUsuarioBC {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PermisoUsuarioDao permisoUsuarioDao;

	@Override
	public List<PermisoUsuario> listByUsuario(Usuario usuario) {
		return permisoUsuarioDao.listByUsuario(usuario);
	}

	@Override
	public PermisoUsuarioDao getDAOInstance() {
		return permisoUsuarioDao;
	}

}
