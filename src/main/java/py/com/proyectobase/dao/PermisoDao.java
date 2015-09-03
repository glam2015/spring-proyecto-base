package py.com.proyectobase.dao;

import java.util.List;

import py.com.proyectobase.domain.Permiso;
import py.com.proyectobase.domain.Rol;

public interface PermisoDao extends Dao<Permiso> {
	List<Permiso> listByRol(Rol rol);
}
