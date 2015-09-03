package py.com.proyectobase.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import py.com.proyectobase.dao.PermisoDao;
import py.com.proyectobase.domain.Permiso;
import py.com.proyectobase.domain.Rol;

@Repository
@Scope("request")
public class PermisoDaoImpl extends DaoImpl<Permiso> implements PermisoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<Permiso> listByRol(Rol rol) {
		return findEntitiesByCondition(
				"WHERE id IN (SELECT rp.permiso.id FROM RolPermiso AS rp WHERE rp.rol.id = ?1)",
				rol.getId());
	}

}
