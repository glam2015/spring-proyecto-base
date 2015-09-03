package py.com.proyectobase.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

@Entity
@Audited
public class Config extends Model {
    private static final String SECUENCIA = "config_id_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
    @SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
    private Long id;

    private Boolean changePasswordOnProfile;
    @Size(max = 64)
    private String decimalFormat;
    @Size(max = 64)
    private String dateFormat;
    @Size(max = 64)
    private String timeStampFormat;
    @Size(max = 64)
    private String dataSourceBaseName;
    @Size(max = 100)
    private String defaultDataSource;

    @Override
    public Long getId() {

        return id;
    }

    @Override
    public void setId(Long id) {

        this.id = id;
    }

    public Boolean getChangePasswordOnProfile() {

        return this.changePasswordOnProfile;
    }

    public void setChangePasswordOnProfile(Boolean changePasswordOnProfile) {

        this.changePasswordOnProfile = changePasswordOnProfile;
    }

    public String getDecimalFormat() {

        return decimalFormat;
    }

    public void setDecimalFormat(String decimalFormat) {

        this.decimalFormat = decimalFormat;
    }

    public String getDateFormat() {

        if (dateFormat == null) {
            dateFormat = "dd/MM/yyyy";
        }
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {

        this.dateFormat = dateFormat;
    }

    public String getTimeStampFormat() {

        if (timeStampFormat == null) {
            timeStampFormat = "dd/MM/yyyy hh:mm";
        }
        return timeStampFormat;
    }

    public void setTimeStampFormat(String timeStampFormat) {

        this.timeStampFormat = timeStampFormat;
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
