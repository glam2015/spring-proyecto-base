package py.com.proyectobase.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import py.com.proyectobase.main.ApplicationContextProvider;
import py.com.proyectobase.main.SesionUsuario;
import py.una.cnc.lib.core.util.AppLogger;

@Service
public class LoginSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private final AppLogger logger = new AppLogger(LoginSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		logger.info(" usuario '{}' autenticado.", authentication.getName());
		SesionUsuario sesionUsuario = (SesionUsuario) ApplicationContextProvider
				.getBeanStatic("sesionUsuario");
		if (sesionUsuario.getUsuarioTmp().getCodigo()
				.compareTo(userDetails.getUsername()) == 0) {
			sesionUsuario.setUsuario(sesionUsuario.getUsuarioTmp());
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}
}
