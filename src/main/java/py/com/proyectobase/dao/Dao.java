package py.com.proyectobase.dao;

import java.io.Serializable;
import java.util.List;

import py.com.proyectobase.domain.Model;

/**
 * 
 * @author dcerrano
 */
public interface Dao<T extends Model> extends Serializable {

	/**
	 * Crea el objeto en la base de datos
	 * 
	 * @param object
	 *            el objeto a persistir
	 */
	void create(T object);

	/**
	 * Actualiza un objeto en la base de datos
	 * 
	 * @param object
	 *            El objeto que será actualizado
	 */
	void edit(T object);

	/**
	 * Elimina un objeto de la base de datos.
	 * 
	 * @param object
	 *            el objeto a eliminar.
	 */
	void destroy(T object);

	/**
	 * Obtiene un objeto de la base de datos a partir de su clave primaria
	 * 
	 * @param id
	 *            la clave primaria del objeto
	 * 
	 * @return el objeto, el registro que tenga como clave primaria el id
	 *         recibido como parámetro
	 */
	T find(Long id);

	/**
	 * Obtiene un objeto de la base de datos a partir de su código
	 * 
	 * @param code
	 *            el código del objeto
	 * 
	 * @return el objeto, el registro que tenga como código el objeto recibido
	 *         como parámetro
	 */
	T findByCode(String code);

	/**
	 * Lista todos los registros de una tabla
	 * 
	 * @return List<T> lista de registros
	 */
	List<T> findEntities();

	/**
	 * 
	 * @param activo
	 *            - si es true, retorna los registros activos; si es false,
	 *            retorna los registros inactivos
	 * 
	 * @return List<T> lista de registros
	 */
	List<T> findEntities(boolean activo);

	/**
	 * Listan los registros de la base de datos que coincidan con los parámetros
	 * recibidos.
	 * 
	 * @param where
	 *            la condición para recuperar los objetos, e.g, WHERE codigo = ?
	 *            AND estado = ?
	 * @param los
	 *            valores que deben ser seteados a la condición
	 * 
	 * @return List<T> lista de registros encontrados
	 */
	List<T> findEntitiesByCondition(String where, Object... params);

	/**
	 * Busca un objeto en la base de datos que coincida con los parámetros
	 * recibidos.
	 * 
	 * @param where
	 *            la condición para recuperar el objeto, e.g, WHERE codigo = ?
	 *            AND estado = ?
	 * @param los
	 *            valores que deben ser seteados a la condición
	 * 
	 * @return T el objeto
	 */
	T findEntityByCondition(String where, Object... params);

	void destroyEntitiesByCondition(String where, Object... params);

	Long countEntitiesByCondition(String where, Object... params);

	/**
	 * @return la clase de la entidad
	 */
	Class<T> getEntityClass();

	void setEntityClass(Class<T> clazz);

	/**
	 * @return el nombre del id de la entidad
	 */
	String getIdName();

	/**
	 * @return el nombre de la entidad
	 */
	String getEntityName();

	/**
	 * Verifica si los datos de los atributos de un bean son válidos. Esta
	 * validación se hace teniendo en cuenta las restricciones definidas en el
	 * modelo.
	 * 
	 * @param object
	 *            el objeto que será validado
	 * 
	 * @throws ValidationException
	 *             esta excepción se lanza cuando existen errores de validación.
	 *             Los errores son cargados dentro de un Map.<br/>
	 *             Por ej, si el atributo id del modelo Persona es null,
	 *             entonces un item del Map sería: key = "Persona.id", value =
	 *             "Id must not be null"
	 */
	void validate(T object);

	Long getMax(String columnName);

	Long getMax(String columnName, String where, Object... params);
}
