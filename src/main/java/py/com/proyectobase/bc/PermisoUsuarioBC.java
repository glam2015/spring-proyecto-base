package py.com.proyectobase.bc;

import java.util.List;

import py.com.proyectobase.domain.PermisoUsuario;
import py.com.proyectobase.domain.Usuario;

public interface PermisoUsuarioBC extends BusinessController<PermisoUsuario> {
	List<PermisoUsuario> listByUsuario(Usuario usuario);

}
