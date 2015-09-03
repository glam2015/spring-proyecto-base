package py.com.proyectobase.dao.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import py.com.proyectobase.dao.PermisoUsuarioDao;
import py.com.proyectobase.dao.RolUsuarioDao;
import py.com.proyectobase.domain.Rol;
import py.com.proyectobase.domain.RolUsuario;
import py.com.proyectobase.domain.Usuario;

@Repository
@Scope("request")
public class RolUsuarioDaoImpl extends DaoImpl<RolUsuario> implements
		RolUsuarioDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private PermisoUsuarioDao permisoUsuarioDao;

	@Override
	@Transactional
	public void quitar(Rol rol, Usuario usuario) {
		// borramos los permisos pertenecientes al usuario
		permisoUsuarioDao.deleteByRol(usuario, rol);
		// borramos rol de usuario
		destroyEntitiesByCondition("WHERE usuario.id = ?1 AND rol.id = ?2",
				usuario.getId(), rol.getId());

	}

	@Override
	@Transactional
	public void agregar(Rol rol, Usuario usuario) {
		permisoUsuarioDao.addByRol(usuario, rol);
		if (!hasRol(rol, usuario)) {
			RolUsuario rolUsuario = new RolUsuario();
			rolUsuario.setRol(rol);
			rolUsuario.setUsuario(usuario);
			create(rolUsuario);
		}
	}

	private boolean hasRol(Rol rol, Usuario usuario) {
		return countEntitiesByCondition(
				"WHERE rol.id = ?1 and usuario.id = ?2", rol.getId(),
				usuario.getId()) > 0;
	}

}
