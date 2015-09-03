package py.com.proyectobase.main;

import java.io.Serializable;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import py.una.cnc.lib.core.util.AppLogger;

/**
 * Esta clase se utiliza para obtener la instancia de un bean ({@link Component}
 * ) del contexto. En muchos casos, es necesario obtener un componente
 * dinámicamente, por lo tanto usar {@link Autowired} no es conveniente, se
 * tendrían que declarar todas las variables por utilizarse.
 * 
 * @author dcerrano
 */

public class ApplicationContextProvider implements ApplicationContextAware,
		Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -7793020747022711574L;
	private static ApplicationContext context;
	private static final AppLogger logger = new AppLogger(
			ApplicationContextProvider.class.getCanonicalName());

	public ApplicationContext getApplicationContext() {
		setContext(context);
		return getContext();
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) {
		setContext(ctx);
	}

	public Object getBean(String name) {
		return getBeanStatic(name);
	}

	public static Object getBeanStatic(String name) {
		try {
			return context.getBean(name);
		} catch (NoSuchBeanDefinitionException e) {
			logger.errorNoTrace("NO SE ENCONTRÓ BEAN: {}", e, name);
		} catch (BeansException bex) {
			logger.error("getBeanStatic", bex);
		} catch (Exception ex) {
			logger.error("getBeanStatic", ex);
		}
		return null;
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		ApplicationContextProvider.context = context;
	}
}
