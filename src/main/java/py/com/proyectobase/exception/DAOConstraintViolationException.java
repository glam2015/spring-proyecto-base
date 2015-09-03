/**
 *
 * © Copyright 2013, Centro Nacional de Computación
 *
 *     http://www.cnc.una.py
 *
 * El sistema PagosWeb es propiedad intelectual del
 * Centro Nacional de Computación (CNC).
 */
package py.com.proyectobase.exception;

@SuppressWarnings("serial")
public class DAOConstraintViolationException extends DAOException {
    public DAOConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOConstraintViolationException(String message) {
        super(message);
    }

    public DAOConstraintViolationException(Throwable cause) {
        super(cause);
    }
}
