package py.com.proyectobase.dataprovider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import py.com.proyectobase.domain.Usuario;
import py.com.proyectobase.exception.DAOException;
import py.com.proyectobase.main.AppObjects;
import py.com.proyectobase.main.CoreConfig;
import py.com.proyectobase.main.SesionUsuario;
import py.com.proyectobase.viewmodel.RolUsuario;
import py.una.cnc.lib.db.dataprovider.DataTableModel;
import py.una.cnc.lib.db.dataprovider.SQLToObject;

@Component
@Scope("session")
public class DataTableSqlDs {

	public static boolean WITHOUT_WHERE = false;
	public static boolean WITH_WHERE = true;
	public static int ALL_ROWS = 0;

	@Autowired
	private CoreConfig coreConfig;
	@Autowired
	private AppObjects appObjects;
	@Autowired
	private SesionUsuario sesionUsuario;
	private final Map<String, String> sqlMap = new HashMap<String, String>();

	private final Map<String, SQLToObject<?>> sqlToObjectMap = new HashMap<String, SQLToObject<?>>();

	private SQLToObject<RolUsuario> sqlToObjectRolUsuario;

	public String getSql(String key) {

		String sql = sqlMap.get(key);
		if (sql == null) {
			sql = appObjects.getSqlSource().get(key);
			sqlMap.put(key, sql);
		}
		return sql;
	}

	public SQLToObject<?> getSqlToObject(String key) {

		SQLToObject<?> sqlToObject = sqlToObjectMap.get(key);
		if (sqlToObject == null) {
			sqlToObject = new SQLToObject<>(appObjects.getDataSourcePool());

			sqlToObjectMap.put(key, sqlToObject);
		}
		return sqlToObject;
	}

	public DataTableModel<?> getDataTable(@NotNull Integer rowNumStart,
			@NotNull Integer rowNumEnd, @NotNull String orderBy,
			@Nullable String filterValue, String selectName, String filterName,
			boolean withWhere, Class<?> recordClass) {

		String dataSourceName = coreConfig.getDefaultDataSource();

		return getDataTable(dataSourceName, rowNumStart, rowNumEnd, orderBy,
				filterValue, selectName, filterName, withWhere, recordClass);
	}

	public DataTableModel<?> getDataTable(@NotNull String dataSourceName,
			@NotNull Integer rowNumStart, @NotNull Integer rowNumEnd,
			@NotNull String orderBy, @Nullable String filterValue,
			String selectName, String filterName, boolean withWhere,
			Class<?> recordClass) {

		SQLToObject<?> sqlToObject = getSqlToObject(selectName);
		sqlToObject.setRecordClass(recordClass);

		return sqlToObject.getData(dataSourceName, getSql(selectName), orderBy,
				withWhere, rowNumStart, rowNumEnd, filterValue, null,
				getSql(filterName));
	}

	public List<RolUsuario> getRolesUsuario(Usuario usuario) {

		String dsName = coreConfig.getDefaultDataSource();
		try {
			return getSqlToObjectRolUsuario().createList(dsName,
					getSql("select_roles_usuario"), usuario.getId());
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	private SQLToObject<RolUsuario> getSqlToObjectRolUsuario() {

		if (sqlToObjectRolUsuario == null) {
			sqlToObjectRolUsuario = new SQLToObject<>(
					appObjects.getDataSourcePool());
			sqlToObjectRolUsuario.setClassOfT(RolUsuario.class);
		}
		return sqlToObjectRolUsuario;
	}

	public String getJsonDataTable(String dataSourceName,
			String selectFromWhere, @Nullable String orderBy,
			boolean whereAlreadyExists, @NotNull Integer rowNumStart,
			@NotNull Integer rowNumEnd, @Nullable String filterValue,
			@Nullable List<Object> previousParams,
			@Nullable String filterableColumn) {

		try {

			return appObjects.getSqlToJson().getData(dataSourceName,
					selectFromWhere, orderBy, whereAlreadyExists, rowNumStart,
					rowNumEnd, filterValue, previousParams, filterableColumn);

		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}
