package py.com.proyectobase.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import py.com.proyectobase.main.ApplicationContextProvider;
import py.com.proyectobase.main.CoreConfig;

/**
 * Provides methods for manipulating instances of {@link Date} and
 * {@link Calendar} .
 * 
 * @author Diego Cerrano
 * @since 1.0
 * @version 1.0 Mar 12, 2014
 * 
 */
@Component
public class DateUtil {

	private CoreConfig coreConfig;
	private SimpleDateFormat simpleDateFormat;
	private SimpleDateFormat timeStampFormat;

	/**
	 * Receives an instance of {@link Date} and returns a copy
	 * 
	 * @param date
	 *            date to be copied
	 * @return <code>null</code> when date is null. Otherwise, a copy of
	 *         <code>date</code>
	 */
	public Date getClonedDate(Date date) {

		if (date == null) {
			return null;
		}

		return (Date) date.clone();
	}

	/**
	 * Receives an instance of {@link Calendar} and returns a copy
	 * 
	 * @param calendar
	 *            calendar to be copied
	 * @return <code>null</code> if <code>calendar</code> is null. Otherwise, a
	 *         copy of <code>calendar</code>.
	 */
	public Calendar getClonedCalendar(Calendar calendar) {

		if (calendar == null) {
			return null;
		}

		return (Calendar) calendar.clone();
	}

	/**
	 * Compares if a date is before another date. beforeDate < afterDate
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @return <ol>
	 *         <li><code>false</code> when both parameters are <code>null</code>
	 *         </li>
	 *         <li> <code>false</code> if <code>beforeDate</code> is null</li>
	 *         <li> <code>true</code> if <code>afterDate</code> is null</li>
	 *         </ol>
	 */
	public boolean isBefore(Date beforeDate, Date afterDate) {

		if ((beforeDate == null) && (afterDate == null)) {
			return false;
		}
		if (beforeDate == null) {
			return false;
		}

		if (afterDate == null) {
			return true;
		}
		return beforeDate.before(afterDate);
	}

	/**
	 * Determina si una fecha ocurrio despúes que otra o si ambas son iguales.
	 * 
	 * <ol>
	 * <li>Si ambas fechas son <code>null</code> o iguales, entonces retorna
	 * <code>true</code></li>
	 * <li>Si la fecha <code>before</code> es <code>null</code>, retorna
	 * <code>true</code></li>
	 * <li>Si la fecha <code>after</code> es <code>null</code> retorna
	 * <code>false</code></li>
	 * <li>Si <code>after</code> ocurrió después que <code>before</code> retorna
	 * <code>true</code></li>
	 * </ol>
	 * 
	 * 
	 * @param beforeDate
	 *            primera fecha, nullable.
	 * @param afterDate
	 *            fecha después, nullable
	 * @return condiciones especificadas arriba.
	 */
	public boolean isAfterOrEqual(Date beforeDate, Date afterDate) {

		if ((beforeDate == null) && (afterDate == null)) {
			return true;
		}
		if (beforeDate == null) {
			return true;
		}

		if (afterDate == null) {
			return false;
		}
		return beforeDate.before(afterDate) || beforeDate.equals(afterDate);
	}

	public boolean isTruncatedAfterOrEqual(Date beforeDate, Date afterDate) {

		if ((beforeDate == null) && (afterDate == null)) {
			return true;
		}
		if (beforeDate == null) {
			return true;
		}

		if (afterDate == null) {
			return false;
		}

		Date before = getTruncatedDate(beforeDate);
		Date after = getTruncatedDate(afterDate);
		return before.before(after) || before.equals(after);
	}

	/**
	 * Truncates a date's hours, minutes, seconds, miliseconds
	 * 
	 * @param date
	 *            the date to be truncated
	 * @return <code>null</code> when date is <code>null</code>. Otherwise, a
	 *         date without hours, minutes, seconds, miliseconds
	 */
	public Date getTruncatedDate(Date date) {

		if (date == null) {
			return null;
		}
		Calendar newCalendar = getCurrentCalendar();
		newCalendar.setTime(date);
		newCalendar.set(Calendar.MILLISECOND, 0);
		newCalendar.set(Calendar.SECOND, 0);
		newCalendar.set(Calendar.MINUTE, 0);
		newCalendar.set(Calendar.HOUR_OF_DAY, 0);
		return newCalendar.getTime();
	}

	/**
	 * Returns the current time as a {@link Date}.
	 * 
	 * @return fecha actual
	 */
	public Date getCurrentDate() {

		return getCurrentCalendar().getTime();
	}

	/**
	 * Returns the current time as a {@link Calendar}.
	 * 
	 * @return current calendar.
	 */
	public Calendar getCurrentCalendar() {

		return Calendar.getInstance();
	}

	public SimpleDateFormat getSimpleDateFormat() {

		if (simpleDateFormat == null) {
			simpleDateFormat = new SimpleDateFormat(getCoreConfig()
					.getDateFormat());
		}
		return simpleDateFormat;
	}

	public SimpleDateFormat getTimeStampFormat() {

		if (timeStampFormat == null) {
			timeStampFormat = new SimpleDateFormat(getCoreConfig()
					.getTimeStampFormat());
		}
		return timeStampFormat;
	}

	private CoreConfig getCoreConfig() {

		if (coreConfig == null) {
			coreConfig = (CoreConfig) ApplicationContextProvider
					.getBeanStatic("coreConfig");
		}
		return coreConfig;
	}

	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
}
