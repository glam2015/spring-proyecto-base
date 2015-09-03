package py.com.proyectobase.main;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import py.com.proyectobase.domain.Usuario;
import py.com.proyectobase.spring.LoginSuccessHandler;
import py.una.cnc.lib.core.util.AppLogger;

/**
 * Clase utilizada para mantener la sesión del usuario una vez que se haya
 * autenticado
 * 
 * @author Diego Cerrano
 * @since 1.0
 * @version 1.0 Dec 2014
 * 
 */
@Component
@Scope("session")
public class SesionUsuario {
	;
	private final Map<String, Boolean> roleMap = new HashMap<String, Boolean>();
	private Usuario usuario;

	private Usuario usuarioTmp;
	private final AppLogger logger = new AppLogger(SesionUsuario.class);
	@Autowired
	private EntityManager entityManager;

	public EntityManager getEM() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Cuando el usuario ingresa sus credenciales, por su nombre de usuario ya
	 * se obtiene el usuario desde la BD, pero no se asegura que su contraseña
	 * sea la correcta, por esta razón solo es temporal.
	 * 
	 * @see LoginSuccessHandler
	 */
	public Usuario getUsuarioTmp() {
		return usuarioTmp;
	}

	public void setUsuarioTmp(Usuario usuarioTmp) {
		this.usuarioTmp = usuarioTmp;
	}

	public boolean hasRole(String role) {
		/*
		 * Para que no se busque con frecuencia en la base de datos, se guardan
		 * los roles en la sesión. Si el rol ya existe en el Map, se retorna su
		 * valor(true, false). En caso que no exista, se busca en las
		 * credenciales del Spring
		 */
		if (roleMap.containsKey(role)) {
			return roleMap.get(role);
		}

		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return false;
		}

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority())
					|| auth.getAuthority().compareTo("root") == 0) {
				roleMap.put(role, true);
				return true;
			}

		}

		roleMap.put(role, false);
		logger.info("Usuario {} no tiene permiso {}", authentication.getName(),
				role);
		return false;
	}

	public boolean isActive() {
		return usuario != null;
	}

}
