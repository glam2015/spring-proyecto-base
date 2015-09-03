package py.com.proyectobase.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import py.com.proyectobase.bc.UsuarioBC;
import py.com.proyectobase.domain.Usuario;
import py.com.proyectobase.main.ApplicationContextProvider;

@Controller
@Scope("request")
public class Pics {

	/**
     * 
     */

	@Autowired
	private UsuarioBC usuarioBC;

	@RequestMapping(value = "/userpic/{codusuario}")
	public void getUserPic(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String codusuario)
			throws IOException {

		Usuario usuario = null;

		try {
			usuario = usuarioBC.findByCode(codusuario);
		} catch (Exception e) {
			usuario = null;
		}

		InputStream is = null;

		/* Si no existe el usuario o no tiene foto, retornar una foto standar */
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setEsMasculino(true);

		}

		if (usuario.getFoto() == null) {

			String path = null;

			if (usuario.getEsMasculino()) {
				path = "/images/100/male_user.png";
			} else {
				path = "/images/100/female_user.png";
			}

			Resource resource = ApplicationContextProvider.getContext()
					.getResource(path);

			is = resource.getInputStream();
		} else {
			is = new ByteArrayInputStream(usuario.getFoto());
		}

		ServletOutputStream out = response.getOutputStream();
		IOUtils.copy(is, out);
		out.flush();
		out.close();
	}

}
