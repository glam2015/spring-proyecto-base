package py.com.proyectobase.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import py.com.proyectobase.dao.PermisoDao;
import py.com.proyectobase.dao.PermisoUsuarioDao;
import py.com.proyectobase.domain.Permiso;
import py.com.proyectobase.domain.PermisoUsuario;
import py.com.proyectobase.domain.Rol;
import py.com.proyectobase.domain.Usuario;

@Repository
@Scope("request")
public class PermisoUsuarioDaoImpl extends DaoImpl<PermisoUsuario> implements
		PermisoUsuarioDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PermisoDao permisoDao;

	@Transactional
	@Override
	public List<PermisoUsuario> listByUsuario(Usuario usuario) {

		return findEntitiesByCondition("WHERE usuario.id = ?1", usuario.getId());
	}

	@Transactional
	@Override
	public void deleteByRol(Usuario usuario, Rol rol) {
		destroyEntitiesByCondition(
				"WHERE usuario.id = ?1 AND permiso.id IN (SELECT rp.permiso.id FROM RolPermiso AS rp WHERE rp.rol.id = ?2)",
				usuario.getId(), rol.getId());
	}

	@Transactional
	@Override
	public void addByRol(Usuario usuario, Rol rol) {
		List<Permiso> permisos = permisoDao.listByRol(rol);

		for (Permiso permiso : permisos) {
			if (!hasPermiso(permiso, usuario)) {
				PermisoUsuario permUsu = new PermisoUsuario();
				permUsu.setPermiso(permiso);
				permUsu.setUsuario(usuario);

				create(permUsu);
			}
		}
	}

	private boolean hasPermiso(Permiso permiso, Usuario usuario) {
		return countEntitiesByCondition(
				"WHERE permiso.id = ?1 and usuario.id = ?2", permiso.getId(),
				usuario.getId()) > 0;
	}
}