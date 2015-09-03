package py.com.proyectobase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DateFormatter implements Formatter<Date> {

	private String dateFormat;
	private SimpleDateFormat simpleDateFormat;
	@Autowired
	private MessageSource messageSource;

	public DateFormatter() {
		super();
	}

	@Override
	public Date parse(final String text, final Locale locale)
			throws ParseException {
		final SimpleDateFormat dateFormat = createDateFormat(locale);
		return dateFormat.parse(text);
	}

	@Override
	public String print(final Date object, final Locale locale) {
		final SimpleDateFormat dateFormat = createDateFormat(locale);
		return dateFormat.format(object);
	}

	private SimpleDateFormat createDateFormat(final Locale locale) {
		final String format = this.messageSource.getMessage("date.format",
				null, locale);
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		return dateFormat;
	}

	public String getDateFormat() {
		if (dateFormat == null) {
			dateFormat = messageSource.getMessage("date.format", null,
					Locale.US);
		}
		return dateFormat;
	}

	private SimpleDateFormat getSimpleDateFormat() {
		if (simpleDateFormat == null) {
			simpleDateFormat = new SimpleDateFormat(getDateFormat());
		}
		return simpleDateFormat;
	}

	public String format(Date date) {
		return getSimpleDateFormat().format(date);
	}

}
