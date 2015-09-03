package py.com.proyectobase.dao.impl;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import py.com.proyectobase.dao.UsuarioDao;
import py.com.proyectobase.domain.Usuario;

@Repository
@Scope("request")
public class UsuarioDaoImpl extends DaoImpl<Usuario> implements UsuarioDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transactional
	@Override
	public Usuario findByHash(String hash) {
		return findEntityByCondition("WHERE hash = ?1", hash);
	}

}
