package py.com.proyectobase.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.ObjectError;

public class ObjectErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final List<ObjectError> errors = new ArrayList<>();

	public void add(String objectName, String defaultMessage) {
		ObjectError objectError = new ObjectError(objectName, defaultMessage);
		errors.add(objectError);
	}

	public List<ObjectError> getErrors() {
		return errors;
	}

	public boolean isEmpty() {
		return errors.isEmpty();
	}

}
