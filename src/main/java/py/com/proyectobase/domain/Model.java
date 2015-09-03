package py.com.proyectobase.domain;



/**
 * Clase genérica de la cual deben heredar las otras entidades. 
 * @author dcerrano
 * 
 */
public abstract class Model {

	private static final int RESULT = 1;
	private static final int PRIME = 31;
	public static final int ZERO = 0;
	public static final String MAX_SIZE_IS = "MAX_SIZE_IS";
	public static final String MIN_SIZE_IS = "MIN_SIZE_IS";
	public static final String NOT_BLANK = "NOT_BLANK";
	public static final String NOT_NULL = "NOT_NULL";

	@Override
	public int hashCode() {

		final int prime = PRIME;
		int result = RESULT;
		result = prime * result + ((getId() == null) ? ZERO : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Model other = (Model) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!(getId().compareTo(other.getId()) == 0)) {
			return false;
		}
		return true;
	}

	public abstract Long getId();

	public abstract void setId(Long id);
}