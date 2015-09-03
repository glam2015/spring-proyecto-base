package py.com.proyectobase.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

/**
 * Provee internacionalización de mensajes
 * 
 * @author Diego Cerrano
 * @since 1.0
 * @version 1.0 Dec 2014
 * 
 */
@Repository
@Scope("session")
public class Message {
	@Autowired
	private MessageSource messageSource;

	public String get(String msg) {

		return messageSource.getMessage(msg, null, msg,
				LocaleContextHolder.getLocale());
	}
}
