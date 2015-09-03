/**
 * 
 * © Copyright 2013, Centro Nacional de Computación
 * 
 * http://www.cnc.una.py
 * 
 * El sistema PagosWeb es propiedad intelectual del Centro Nacional de
 * Computación (CNC).
 */
package py.com.proyectobase.exception;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class BusinessLogicException extends RuntimeException {

	private Map<String, String> errors;

	public BusinessLogicException(Map<String, String> errors) {

		this.errors = errors;
	}

	public BusinessLogicException(Map<String, String> errors, Throwable cause) {

		super(cause);
		this.errors = errors;
	}

	public BusinessLogicException(String message, Throwable cause) {

		super(message, cause);
	}

	public BusinessLogicException(String message) {

		super(message);
	}

	public BusinessLogicException(String fieldName, String error) {

		addError(fieldName, error);
	}

	public Map<String, String> getErrors() {

		if (errors == null) {
			errors = new HashMap<String, String>();
		}
		return errors;
	}

	public void setErrors(Map<String, String> errors) {

		this.errors = errors;
	}

	public void addError(String fieldName, String error) {

		getErrors().put(fieldName, error);
	}

}
