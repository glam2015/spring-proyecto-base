package py.com.proyectobase.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Audited
public class Rol extends Model implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int CODIGO_MAX_SIZE = 15;
	public static final int DESCRIPCION_MAX_SIZE = 100;

	private static final String SECUENCIA = "rol_id_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	@Size(max = CODIGO_MAX_SIZE)
	@NotBlank
	@NotNull
	@Column(unique = true)
	private String codigo;
	@Size(max = DESCRIPCION_MAX_SIZE)
	@Column(unique = true)
	private String descripcion;

	@Override
	public Long getId() {
		return this.id;
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
		return "Rol [codigo=" + codigo + "]";
	}

}