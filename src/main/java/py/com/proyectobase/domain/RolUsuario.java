package py.com.proyectobase.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

/**
 * 
 * @author dcerrano
 */
@Entity
@Audited
public class RolUsuario extends Model implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String SECUENCIA = "rol_usuario_id_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA, allocationSize = 1)
	private Long id;
	@NotNull(message = "rolusuario.usuario.rol.not_null")
	@ManyToOne
	private Rol rol;
	@NotNull(message = "rolusuario.usuario.not_null")
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Usuario usuario;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "RolUsuario [rol=" + rol + ", usuario=" + usuario + "]";
	}

}