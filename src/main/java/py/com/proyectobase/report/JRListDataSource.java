package py.com.proyectobase.report;

import java.util.Collection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JRListDataSource extends JRBeanCollectionDataSource {

	public JRListDataSource(Collection<?> beanCollection) {

		super(beanCollection);
	}

	protected Object getFieldValue(Object bean, JRField field)
			throws JRException {

		System.out.println(bean + field.getName());
		return getBeanProperty(bean, getPropertyName(field));
	}

}
