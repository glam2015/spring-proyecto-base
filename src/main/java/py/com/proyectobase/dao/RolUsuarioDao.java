package py.com.proyectobase.dao;

import py.com.proyectobase.domain.Rol;
import py.com.proyectobase.domain.RolUsuario;
import py.com.proyectobase.domain.Usuario;

public interface RolUsuarioDao extends Dao<RolUsuario> {

	void quitar(Rol rol, Usuario usuario);

	void agregar(Rol rol, Usuario usuario);
}
