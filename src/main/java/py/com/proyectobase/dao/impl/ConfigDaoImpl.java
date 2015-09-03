package py.com.proyectobase.dao.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import py.com.proyectobase.dao.ConfigDao;
import py.com.proyectobase.domain.Config;


@Repository
@Scope("session")
public class ConfigDaoImpl extends DaoImpl<Config> implements ConfigDao {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

}
