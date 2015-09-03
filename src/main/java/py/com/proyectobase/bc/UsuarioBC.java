package py.com.proyectobase.bc;

import py.com.proyectobase.domain.Usuario;

public interface UsuarioBC extends BusinessController<Usuario> {
	Usuario getUsuarioPorCodigo(String codigo);

	Usuario findByHash(String hash);
}
