package py.com.proyectobase.viewmodel;

public class RolUsuario {
	private Long rolid;
	private String codrol;
	private String descriprol;
	private Boolean selected;

	public Long getRolid() {
		return rolid;
	}

	public void setRolid(Long rolid) {
		this.rolid = rolid;
	}

	public String getCodrol() {
		return codrol;
	}

	public void setCodrol(String codrol) {
		this.codrol = codrol;
	}

	public String getDescriprol() {
		return descriprol;
	}

	public void setDescriprol(String descriprol) {
		this.descriprol = descriprol;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
