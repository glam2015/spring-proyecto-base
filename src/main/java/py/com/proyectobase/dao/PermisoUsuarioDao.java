package py.com.proyectobase.dao;

import java.util.List;

import py.com.proyectobase.domain.PermisoUsuario;
import py.com.proyectobase.domain.Rol;
import py.com.proyectobase.domain.Usuario;

public interface PermisoUsuarioDao extends Dao<PermisoUsuario> {
	List<PermisoUsuario> listByUsuario(Usuario usuario);

	void deleteByRol(Usuario usuario, Rol rol);

	void addByRol(Usuario usuario, Rol rol);
}
