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
public class DAOException extends RuntimeException {
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
