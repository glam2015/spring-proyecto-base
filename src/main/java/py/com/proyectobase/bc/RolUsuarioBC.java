package py.com.proyectobase.bc;

import py.com.proyectobase.domain.Rol;
import py.com.proyectobase.domain.RolUsuario;
import py.com.proyectobase.domain.Usuario;


public interface RolUsuarioBC extends BusinessController<RolUsuario> {

	void quitar(Rol rol, Usuario usuario);

	void agregar(Rol rol, Usuario usuario);
}
