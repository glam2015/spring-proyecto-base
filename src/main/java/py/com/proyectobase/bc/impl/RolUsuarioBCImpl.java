package py.com.proyectobase.bc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import py.com.proyectobase.bc.RolUsuarioBC;
import py.com.proyectobase.dao.RolUsuarioDao;
import py.com.proyectobase.domain.Rol;
import py.com.proyectobase.domain.RolUsuario;
import py.com.proyectobase.domain.Usuario;

@Component
@Scope("request")
public class RolUsuarioBCImpl extends BusinessControllerImpl<RolUsuario>
		implements RolUsuarioBC {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private RolUsuarioDao rolUsuarioDao;

	@Override
	public RolUsuarioDao getDAOInstance() {
		return rolUsuarioDao;
	}

	@Transactional
	@Override
	public void quitar(Rol rol, Usuario usuario) {
		rolUsuarioDao.quitar(rol, usuario);

	}

	@Transactional
	@Override
	public void agregar(Rol rol, Usuario usuario) {
		rolUsuarioDao.agregar(rol, usuario);
	}

}
