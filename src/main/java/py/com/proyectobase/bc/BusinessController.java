package py.com.proyectobase.bc;

import java.io.Serializable;
import java.util.List;

import py.com.proyectobase.dao.Dao;
import py.com.proyectobase.domain.Model;
import py.com.proyectobase.exception.BusinessLogicException;
import py.com.proyectobase.exception.DAOException;

public interface BusinessController<T extends Model> extends Serializable {

	/**
	 * Crea el objeto en la base de datos
	 * 
	 * @param object
	 *            el objeto a persistir
	 * @throws ValidationException
	 *             cuando los atributos del bean no tienen valores requeridos
	 * @throws BusinessLogicException
	 *             cuando no se cumplen las reglas del negocio
	 * @throws DAOException
	 *             cuando ocurre un error de acceso a los datos
	 */
	void create(T obj);

	/**
	 * Actualiza un objeto en la base de datos
	 * 
	 * @param object
	 *            el objeto a actualizar
	 * @throws ValidationException
	 *             cuando los atributos del bean no tienen valores requeridos
	 * @throws BusinessLogicException
	 *             cuando no se cumplen las reglas del negocio
	 * @throws DAOException
	 *             cuando ocurre un error de acceso a los datos
	 */
	void edit(T obj);

	/**
	 * Elimina un objeto de la base de datos.
	 * 
	 * @param object
	 *            el objeto a eliminar.
	 * 
	 * @throws BusinessLogicException
	 *             cuando no se cumplen las reglas del negocio
	 * @throws DAOException
	 *             cuando ocurre un error de acceso a los datos
	 */
	void destroy(T obj);

	/**
	 * Obtiene un objeto de la base de datos a partir de su clave primaria
	 * 
	 * @param id
	 *            la clave primaria del objeto
	 * 
	 * @return el objeto, el registro que tenga como clave primaria el id
	 *         recibido como parámetro
	 * 
	 * @throws DAOException
	 *             cuando ocurre error de acceso a los datos
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
	 * 
	 * @throws DAOException
	 *             cuando ocurre error de acceso a los datos
	 */

	T findByCode(String code);

	/**
	 * Obtiene un objeto de la base de datos a partir de su id almacenado en el
	 * map. Ej, si la entidad es Persona y el map (key= id, value = 10),
	 * entonces se busca en la bd la persona con id = 10.
	 * 
	 * @param map
	 *            un map que contiene como llave el nombre de id de la entidad
	 * 
	 * @return el objeto que se recupera de la base de datos a partir de su id
	 * 
	 * @throws DAOException
	 *             cuando ocurre error de acceso a los datos
	 */

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
	 * Lista todos los registros de una tabla
	 * 
	 * @return List<T> lista de registros
	 * 
	 * @throws DAOException
	 *             cuando ocurre error de acceso a los datos
	 */
	List<T> findEntities() throws DAOException;

	/**
	 * Este método debe retornar una instancia del DAO perteneciente al
	 * controlador. Es decir, si el controlador maneja datos de la entidad
	 * Persona, entonces debe existir PersonaDAO, y se debe retornar una
	 * instancia de PersonaDAOImpl
	 */
	Dao<T> getDAOInstance();

	/**
	 * @return true si el usuario tiene permisos para ver registros
	 */
	boolean userCanSelect();

	/**
	 * @return true si el usuario tiene permisos para insertar registros
	 */
	boolean userCanInsert();

	/**
	 * @return true si el usuario tiene permisos para modificar registros
	 */
	boolean userCanUpdate();

	/**
	 * @return true si el usuario tiene permisos para borrar registros
	 */
	boolean userCanDelete();

	/**
	 * Sirve para identificar si se trata de inserción o actualización de datos.
	 * 
	 * @return true si es nuevo registro. false en otro caso
	 */
	boolean isNewRecord();

	/**
	 * Indicar si se va a guardar un nuevo registro o se va a actualizar
	 */
	void setNewRecord(boolean newRecord);

	/**
	 * Guarda el bean en la base de datos
	 * 
	 * @param bean
	 *            el objeto que se va a insertar o actualizar en la base de
	 *            datos
	 */
	void save(T bean);

	/**
	 * Los permisos más comunes sobre un registro son para ver, insertar, editar
	 * y borrar. Si se establece un permiso base 'abm_persona', entonces se
	 * generarán los permisos: 'abm_persona_sel' para ver, 'abm_persona_ins'
	 * para insertar,'abm_persona_upd' para editar, 'abm_persona_del' para
	 * borrar
	 */
	String getPermissionBaseName();

	/**
	 * @return true si es que se va a controlar permisos para realizar
	 *         operaciones. false si no se requiere control de permisos. Por
	 *         defecto es true
	 * 
	 */
	boolean isPermissionRequired();

	/**
	 * @param true si es que se va a controlar permisos para realizar
	 *        operaciones. false si no se requiere control de permisos. Default
	 *        true
	 */
	void setPermissionRequired(boolean permissionRequired);

	/**
	 * @return el último bean buscado y encontrado con el controlador
	 */
	T getLastBeanRetrieved();
}
