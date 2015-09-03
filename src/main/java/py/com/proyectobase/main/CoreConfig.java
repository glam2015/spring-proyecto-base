package py.com.proyectobase.main;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import py.com.proyectobase.dao.ConfigDao;
import py.com.proyectobase.domain.Config;
import py.com.proyectobase.exception.DAOException;
import py.una.cnc.lib.core.util.AppLogger;

/**
 * 
 * @author dcerrano
 */
@Component
@Scope("session")
public class CoreConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer filterTextChangeTimeout = 0;
    private Boolean changePasswordOnProfile = true;
    private final int profilePicMaxSize = 1048576;
    private String decimalFormat;
    private String dateFormat;
    private String timeStampFormat;

    private String decimalSeparator = ".";
    private String groupingSeparator = ",";
    private String dataSourceBaseName = "java:jboss/datasources/";

    private String defaultDataSource = "java:jboss/datasources/defaultDataSource";

    private final AppLogger logger;
    @Autowired
    private ConfigDao configDao;

    public CoreConfig() {

        logger = new AppLogger(getClass().getCanonicalName());
        init();
    }

    @PostConstruct
    public void loadConfig() {

        Config config = null;
        try {
            // TODO:
            List<Config> list = configDao.findEntities();
            if (list.size() > 0) {
                config = list.get(0);
            }
        } catch (DAOException e) {
            logger.error("Al cargar configuraciones: {}", e, e.getMessage());
        }
        if (config == null) {
            logger.info("No existe registro de configuración. Cargando valores por defecto");

            return;
        }

        changePasswordOnProfile = config.getChangePasswordOnProfile();

        timeStampFormat = config.getTimeStampFormat();
        if (config.getDateFormat() != null) {
            dateFormat = config.getDateFormat();
        }
        if (config.getDecimalFormat() != null) {
            decimalFormat = config.getDecimalFormat();
        }
        dataSourceBaseName = config.getDataSourceBaseName();
        if (config.getDefaultDataSource() != null) {
            defaultDataSource = config.getDefaultDataSource();
        }
        logger.info(
                "Configuraciones: defaultDataSource: {}, changePasswordOnProfile: {}",
                defaultDataSource, changePasswordOnProfile);
    }

    private void init() {

        decimalFormat = "###,###,###.##";
        dateFormat = "dd/MM/yyyy";
        timeStampFormat = "dd/MM/yyyy HH:mm";

    }

    public Boolean getChangePasswordOnProfile() {

        if (changePasswordOnProfile == null) {
            return false;
        }
        return this.changePasswordOnProfile;
    }

    public void setChangePasswordOnProfile(Boolean changePasswordOnProfile) {

        this.changePasswordOnProfile = changePasswordOnProfile;
    }

    public int getProfilePicMaxSize() {

        return profilePicMaxSize;
    }

    public static long getSerialversionuid() {

        return serialVersionUID;
    }

    public AppLogger getLogger() {

        return logger;
    }

    public String getDecimalFormat() {

        return decimalFormat;
    }

    public String getDateFormat() {

        return dateFormat;
    }

    public String getTimeStampFormat() {

        return timeStampFormat;
    }

    public Integer getFilterTextChangeTimeout() {

        return filterTextChangeTimeout;
    }

    public void setFilterTextChangeTimeout(Integer filterTextChangeTimeout) {

        this.filterTextChangeTimeout = filterTextChangeTimeout;
    }

    public String getDecimalSeparator() {

        return decimalSeparator;
    }

    public void setDecimalSeparator(String decimalSeparator) {

        this.decimalSeparator = decimalSeparator;
    }

    public String getGroupingSeparator() {

        return groupingSeparator;
    }

    public void setGroupingSeparator(String groupingSeparator) {

        this.groupingSeparator = groupingSeparator;
    }

    public String getDataSourceBaseName() {
        return dataSourceBaseName;
    }

    public void setDataSourceBaseName(String dataSourceBaseName) {
        this.dataSourceBaseName = dataSourceBaseName;
    }

    public String getDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(String defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }
}
