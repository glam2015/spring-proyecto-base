package py.com.proyectobase.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "usuario_id",
		"permiso_id" }))
public class PermisoUsuario extends Model {
	private static final String SECUENCIA = "permiso_usuario_id_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	@NotNull(message = "permiso_usuario.usuario.not_null")
	@ManyToOne
	private Usuario usuario;

	@NotNull(message = "permiso_usuario.permiso.not_null")
	@ManyToOne
	private Permiso permiso;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Permiso getPermiso() {
		return permiso;
	}

	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}

	@Override
	public String toString() {
		return "PermisoUsuario [usuario=" + usuario + ", permiso=" + permiso
				+ "]";
	}

}
