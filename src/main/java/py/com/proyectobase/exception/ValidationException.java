package py.com.proyectobase.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Map<String, String> errors;

	public ValidationException(Map<String, String> errors) {
		this.errors = errors;
	}

	public ValidationException(Map<String, String> errors, Throwable cause) {
		super(cause);
		this.errors = errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
