package py.com.proyectobase.main;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import py.una.cnc.lib.db.DataSourcePool;
import py.una.cnc.lib.db.dataprovider.SQLSource;
import py.una.cnc.lib.db.dataprovider.SQLToJson;
import py.una.cnc.lib.db.dataprovider.SQLToJsonArray;

@Component
public class AppObjects {

    private SQLSource sqlSource;
    private SQLToJson sqlToJson;
    private SQLToJsonArray sqlToJsonArray;
    private DataSourcePool dataSourcePool;

    /**
     * Es necesario iniciar ya las conexiones al deployar la app, ya que puede
     * tardar
     */
    @PostConstruct
    public void init() {
        dataSourcePool = new DataSourcePool();
        dataSourcePool.init();
    }

    public DataSourcePool getDataSourcePool() {

        return dataSourcePool;
    }

    public SQLToJson getSqlToJson() {
        if (sqlToJson == null) {
            sqlToJson = new SQLToJson(getDataSourcePool());
        }
        return sqlToJson;
    }

    public SQLToJsonArray getSqlToJsonArray() {
        if (sqlToJsonArray == null) {
            sqlToJsonArray = new SQLToJsonArray(getDataSourcePool());
        }
        return sqlToJsonArray;
    }

    public SQLSource getSqlSource() {
        if (sqlSource == null) {
            sqlSource = new SQLSource();
            sqlSource.init();
        }
        return sqlSource;
    }

}
