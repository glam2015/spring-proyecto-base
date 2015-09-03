/**
 * 
 * © Copyright 2013, Centro Nacional de Computación
 * 
 * http://www.cnc.una.py
 * 
 * El sistema PagosWeb es propiedad intelectual del Centro Nacional de
 * Computación (CNC).
 */
package py.com.proyectobase.util;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import py.com.proyectobase.main.ApplicationContextProvider;
import py.com.proyectobase.main.CoreConfig;

/**
 * @author dcerrano
 */
@Component
@Scope("session")
public class Util {

	private static final Integer OXFF = 0xFF;
	private static final Integer OX100 = 0x100;
	private MessageDigest messageDigest;
	private DecimalFormat decimalFormat;
	private SimpleDateFormat simpleDateFormat;
	private SimpleDateFormat timeStampFormat;
	private CoreConfig coreConfig;
	private DecimalFormatSymbols decimalFormatSymbols;

	private Util() {

	}

	public static Date getClonedDate(Date date) {

		if (date == null) {
			return null;
		} else {
			return (Date) date.clone();
		}
	}

	/**
	 * Recibe una clase que tenga parámetros genéricos y retorna la clase del
	 * parámetro cuya posición se indica. Ej, si se recibe la clase Generica
	 * &lt;T, K &gt; y la posición 0, se retorna la clase de T
	 * 
	 * @param clazz
	 *            la clase que tiene parámetros genéricos.
	 * @param position
	 *            la posición del parámetro
	 * @return la clase del primer parámetro de la clase.
	 */
	public static Class<?> getClassOfParam(Class<?> clazz, Integer position) {

		ParameterizedType superClass = (ParameterizedType) clazz
				.getGenericSuperclass();
		return (Class<?>) superClass.getActualTypeArguments()[position];
	}

	public boolean isImage(File file) {

		try {
			Image image = ImageIO.read(file);
			return image != null;
		} catch (IOException ex) {
			return false;
		}
	}

	public byte[] fileToArrayByte(File file) {

		FileInputStream fileInputStream = null;

		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFile;
	}

	public String getMD5(String value) throws NoSuchAlgorithmException {

		byte[] array = getMessageDigest().digest(
				value.getBytes(Charset.forName("UTF-8")));
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & OXFF) | OX100).substring(
					1, 3));
		}
		return sb.toString();
	}

	private MessageDigest getMessageDigest() throws NoSuchAlgorithmException {

		if (messageDigest == null) {
			messageDigest = MessageDigest.getInstance("MD5");
		}
		return messageDigest;
	}

	public SimpleDateFormat getDateFormat() {

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

	public DecimalFormat getDecimalFormat() {

		if (decimalFormat == null) {
			decimalFormat = new DecimalFormat(getCoreConfig()
					.getDecimalFormat(), getDecimalFormatSymbols());
		}
		return decimalFormat;
	}

	private DecimalFormatSymbols getDecimalFormatSymbols() {

		if (decimalFormatSymbols == null) {
			decimalFormatSymbols = DecimalFormatSymbols.getInstance();
			decimalFormatSymbols.setDecimalSeparator('.');
			decimalFormatSymbols.setGroupingSeparator(',');
		}
		return decimalFormatSymbols;
	}

	private CoreConfig getCoreConfig() {

		if (coreConfig == null) {
			coreConfig = (CoreConfig) ApplicationContextProvider
					.getBeanStatic("coreConfig");
		}
		return coreConfig;
	}

	public boolean areEquals(BigDecimal number1, BigDecimal number2) {

		if (number1 == null) {
			return number2 == null;
		}

		return number1.compareTo(number2) == 0;
	}

	public boolean areEquals(Integer number1, Integer number2) {

		if (number1 == null) {
			return number2 == null;
		}

		return number1.compareTo(number2) == 0;
	}
}
