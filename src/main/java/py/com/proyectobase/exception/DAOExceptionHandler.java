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


public final class DAOExceptionHandler {
    private DAOExceptionHandler() {

    }

    public static String getSaveExceptionMessage(Exception e) {
        String msg = "No se pudo guardar registro";

        Throwable th = e;
        while (th != null) {
            if (th.getMessage() != null
                    && th.getMessage().contains("duplicate key")) {
                msg = "No se pudo guardar registro. Llave duplicada";
                break;
            }
            th = th.getCause();
        }

        return msg;
    }

    public static String getDestroyExceptionMessage(Exception e) {
        String msg = "No se pudo borrar registro";
        Throwable th = e;
        while (th != null) {
            if (th.getMessage() != null
                    && th.getMessage().contains("foreign key constraint")) {
                msg = "No se puede borrar registro. Existen datos relacionados.";
                break;
            }
            th = th.getCause();
        }
        return msg;
    }
}
