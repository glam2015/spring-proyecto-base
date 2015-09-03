package py.com.proyectobase.converters;

import org.springframework.stereotype.Component;

import py.com.proyectobase.dao.UsuarioDao;
import py.com.proyectobase.domain.Usuario;
import py.com.proyectobase.main.ApplicationContextProvider;

@Component
public class UsuarioConverter extends ModelConverter<Usuario> {

	@Override
	protected UsuarioDao getDaoImpl() {

		return (UsuarioDao) ApplicationContextProvider
				.getBeanStatic("usuarioDaoImpl");
	}
}
