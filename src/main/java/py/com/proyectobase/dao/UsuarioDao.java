package py.com.proyectobase.dao;

import py.com.proyectobase.domain.Usuario;


public interface UsuarioDao extends Dao<Usuario> {
	Usuario findByHash(String hash);

}
