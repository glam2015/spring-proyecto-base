package py.com.proyectobase.viewmodel;

import java.util.ArrayList;
import java.util.List;

import py.com.proyectobase.domain.Usuario;

public class RolesUsuario {
	private List<RolUsuario> rolUsuarioList;
	private Usuario usuario;
	private Long usuarioId;

	public List<RolUsuario> getRolUsuarioList() {
		if (rolUsuarioList == null) {
			rolUsuarioList = new ArrayList<>();
		}
		return rolUsuarioList;
	}

	public void setRolUsuarioList(List<RolUsuario> rolUsuarioList) {
		this.rolUsuarioList = rolUsuarioList;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {

		this.usuario = usuario;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

}
