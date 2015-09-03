package py.com.proyectobase.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import py.com.proyectobase.bc.PermisoUsuarioBC;
import py.com.proyectobase.bc.UsuarioBC;
import py.com.proyectobase.domain.PermisoUsuario;
import py.com.proyectobase.domain.Usuario;
import py.com.proyectobase.main.ApplicationContextProvider;
import py.com.proyectobase.main.SesionUsuario;
import py.una.cnc.lib.core.util.AppLogger;

@Service
public class UserRoleService implements UserDetailsService {
	private final AppLogger logger = new AppLogger(UserRoleService.class);

	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		logger.info("Verificando credenciales de usuario: {}", username);

		UsuarioBC usuarioBC = (UsuarioBC) ApplicationContextProvider
				.getBeanStatic("usuarioBCImpl");

		Usuario usuario = usuarioBC.findByCode(username);
		if (usuario == null) {
			return getUsuarioAnonimo(username);
		}

		SesionUsuario sesionUsuario = (SesionUsuario) ApplicationContextProvider
				.getBeanStatic("sesionUsuario");
		sesionUsuario.setUsuarioTmp(usuario);
		return buildUserForAuthentication(usuario);
	}

	private UserDetails buildUserForAuthentication(final Usuario usuario) {

		PermisoUsuarioBC permisoUsuarioBC = (PermisoUsuarioBC) ApplicationContextProvider
				.getBeanStatic("permisoUsuarioBCImpl");

		final List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
		List<PermisoUsuario> permisoUsuarios = permisoUsuarioBC
				.listByUsuario(usuario);
		logger.debug("Permisos de {}", usuario.getCodigo());

		for (final PermisoUsuario pu : permisoUsuarios) {
			logger.debug("{}", pu.getPermiso().getCodigo());
			@SuppressWarnings("serial")
			GrantedAuthority permiso = new GrantedAuthority() {

				@Override
				public String getAuthority() {
					return pu.getPermiso().getCodigo();
				}
			};
			permisos.add(permiso);

		}

		@SuppressWarnings("serial")
		UserDetails userDetail = new UserDetails() {

			@Override
			public boolean isEnabled() {
				return true;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}

			@Override
			public boolean isAccountNonLocked() {
				return true;
			}

			@Override
			public boolean isAccountNonExpired() {

				return true;
			}

			@Override
			public String getUsername() {

				return usuario.getCodigo();
			}

			@Override
			public String getPassword() {

				return usuario.getPassword();
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {

				return permisos;
			}
		};
		return userDetail;
	}

	private UserDetails getUsuarioAnonimo(final String username) {
		@SuppressWarnings("serial")
		UserDetails usuarioAnonimo = new UserDetails() {

			@Override
			public boolean isEnabled() {
				return false;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}

			@Override
			public boolean isAccountNonLocked() {
				return true;
			}

			@Override
			public boolean isAccountNonExpired() {

				return true;
			}

			@Override
			public String getUsername() {

				return username;
			}

			@Override
			public String getPassword() {

				return null;
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {

				return null;
			}
		};
		return usuarioAnonimo;
	}
}
