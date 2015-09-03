package py.com.proyectobase.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "usuario")
@Audited
public class Usuario extends Model implements Serializable {
	public static final int CODIGO_MAX_SIZE = 15;
	public static final int PASSWORD_MIN_SIZE = 6;
	public static final int PASSWORD_MAX_SIZE = 32;
	public static final int NOMBRE_MAX_SIZE = 50;
	public static final int APELLIDO_MAX_SIZE = 50;
	public static final int EMAIL_MAX_SIZE = 50;
	public static final int IDIOMA_MAX_SIZE = 15;
	public static final int DIRECCION_MAX_SIZE = 100;
	public static final int TELEFONO_MAX_SIZE = 20;
	public static final int CELULAR_MAX_SIZE = 20;
	public static final int HASH_MAX_SIZE = 32;

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "usuario_id_seq")
	@SequenceGenerator(name = "usuario_id_seq", sequenceName = "usuario_id_seq", allocationSize = 1)
	private Long id;

	@NotNull(message = "usuario.codigo.not_null")
	@NotBlank(message = "usuario.codigo.not_blank")
	@Size(max = CODIGO_MAX_SIZE, message = "usuario.codigo.max_size")
	@Column(name = "codigo", unique = true)
	private String codigo;

	private String password;

	@NotNull(message = NOT_NULL)
	private Boolean activo = true;

	@NotNull(message = "usuario.nombre.not_null")
	@NotBlank(message = "usuario.nombre.not_blank")
	@Size(max = NOMBRE_MAX_SIZE, message = "usuario.nombre.max_size")
	private String nombre;

	@NotNull(message = "usuario.apellido.not_null")
	@NotBlank(message = "usuario.apellido.not_blank")
	@Size(max = EMAIL_MAX_SIZE, message = MAX_SIZE_IS + APELLIDO_MAX_SIZE)
	private String apellido;

	@Email
	@Size(max = EMAIL_MAX_SIZE, message = MAX_SIZE_IS + EMAIL_MAX_SIZE)
	@NotBlank(message = "usuario.email.not_blank")
	@Column(unique = true)
	private String email;

	@Size(max = IDIOMA_MAX_SIZE, message = MAX_SIZE_IS + IDIOMA_MAX_SIZE)
	private String idioma;

	@Size(max = TELEFONO_MAX_SIZE)
	private String telefono;

	@Size(max = CELULAR_MAX_SIZE)
	private String celular;

	@Size(max = HASH_MAX_SIZE)
	private String hash;

	@Size(max = DIRECCION_MAX_SIZE)
	private String direccion;

	private Boolean esMasculino;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaUltimoAcceso;
	@Size(max = 64)
	private String ipUltimoAcceso;

	@Lob
	@Basic(fetch = FetchType.LAZY, optional = true)
	private byte[] foto;
	
	/* Modificacion se agrega una tienda o empresa */
	private Long idTienda;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Date getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	public String getIpUltimoAcceso() {
		return ipUltimoAcceso;
	}

	public void setIpUltimoAcceso(String ipUltimoAcceso) {
		this.ipUltimoAcceso = ipUltimoAcceso;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getEsMasculino() {
		if (esMasculino == null) {
			return true;
		}
		return this.esMasculino;
	}

	public void setEsMasculino(Boolean esMasculino) {
		this.esMasculino = esMasculino;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Long getIdTienda() {
		return idTienda;
	}

	public void setIdTienda(Long idTienda) {
		this.idTienda = idTienda;
	}

	@Override
	public String toString() {
		return nombre + " " + apellido;
	}
}