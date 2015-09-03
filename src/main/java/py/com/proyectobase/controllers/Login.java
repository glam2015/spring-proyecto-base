package py.com.proyectobase.controllers;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import py.com.proyectobase.domain.Usuario;
import py.com.proyectobase.main.ApplicationContextProvider;
import py.com.proyectobase.main.SesionUsuario;
import py.una.cnc.lib.core.util.AppLogger;

@Controller
@Scope("request")
public class Login {

	private final AppLogger logger = new AppLogger(Login.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model,
			@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request) {

		if (error != null) {
			model.addAttribute("error", "Invalid username and password!");
			logger.info("error: {}", error);
		}
		SesionUsuario sesionUsuario = (SesionUsuario) ApplicationContextProvider
				.getBeanStatic("sesionUsuario");
		if (sesionUsuario.isActive()) {
			Usuario usuario = sesionUsuario.getUsuario();
			logger.info("Sesión activa: {} {}", usuario.getNombre(),
					usuario.getApellido());
			return "redirect:/inicio";
		} else {
			logger.info("Sin sesión iniciada");
		}

		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(ModelMap model, HttpServletRequest request) {

		SecurityContextHolder.clearContext();
		request.getSession().invalidate();

		return "redirect:/inicio";
	}

	@RequestMapping(value = "/403")
	public String accessDenied(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "error", required = false) String error) {

		Enumeration<String> params = request.getParameterNames();
		logger.info(
				"************************** PARAMS **********************************, error: {}",
				error);
		while (params.hasMoreElements()) {
			String name = params.nextElement();
			logger.info("{}: {}", name, request.getParameter(name));
		}

		return "403";
	}
}
