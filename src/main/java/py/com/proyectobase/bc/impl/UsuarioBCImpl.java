package py.com.proyectobase.bc.impl;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import py.com.proyectobase.bc.UsuarioBC;
import py.com.proyectobase.dao.UsuarioDao;
import py.com.proyectobase.domain.Usuario;
import py.com.proyectobase.exception.BusinessLogicException;
import py.com.proyectobase.exception.ObjectErrorException;
import py.com.proyectobase.main.Message;
import py.com.proyectobase.util.Util;

@Component
@Scope("request")
public class UsuarioBCImpl extends BusinessControllerImpl<Usuario> implements
		UsuarioBC {

	@Autowired
	private Util util;
	@Autowired
	private Message msg;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	public Usuario getUsuarioPorCodigo(String codigo) {

		return usuarioDao.findByCode(codigo);
	}

	@Override
	public Usuario findByHash(String hash) {

		return usuarioDao.findByHash(hash);
	}

	@Override
	public void create(Usuario usuario) {

		ObjectErrorException errors = new ObjectErrorException();

		encriptarPassword(usuario, errors);
		if (!errors.isEmpty()) {
			throw errors;
		}

		super.create(usuario);
	}

	@Override
	public void edit(Usuario usuario) {

		super.edit(usuario);
	}

	private void encriptarPassword(Usuario usuario, ObjectErrorException errors) {

		if (usuario.getPassword() == null
				|| usuario.getPassword().trim().length() == 0) {
			errors.add("password", msg.get("usuario.password.not_blank"));
		}

		try {
			String password = util.getMD5(usuario.getPassword());
			usuario.setPassword(password);
		} catch (NoSuchAlgorithmException algorithmException) {
			throw new BusinessLogicException("no se pudo generar contraseña");
		}
	}

	@Override
	public UsuarioDao getDAOInstance() {

		return usuarioDao;
	}

}
