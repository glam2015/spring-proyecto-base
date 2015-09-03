package py.com.proyectobase.domain.report;

public class UsuarioRep {
	private Long rownum;
	private Long id;
	private String codigo;
	private String nombre;
	private String apellido;
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	@Override
	public String toString() {
		return "UsuarioRep [rownum=" + rownum + ", id=" + id + ", codigo="
				+ codigo + ", nombre=" + nombre + "]";
	}

}
