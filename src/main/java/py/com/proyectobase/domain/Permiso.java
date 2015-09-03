package py.com.proyectobase.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Audited
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "codigo" }))
public class Permiso extends Model {
	public static final int CODIGO_MAX_SIZE = 15;
	public static final int DESCRIPCION_MAX_SIZE = 100;
	private static final String SECUENCIA = "permiso_id_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	@NotNull(message = "permiso.codigo.not_null")
	@NotBlank(message = "permiso.codigo.not_blank")
	@Size(max = CODIGO_MAX_SIZE)
	private String codigo;

	@NotNull(message = "permiso.descripcion.not_null")
	@NotBlank(message = "permiso.descripcion.not_blank")
	@Size(max = DESCRIPCION_MAX_SIZE)
	private String descripcion;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return codigo;
	}
}
