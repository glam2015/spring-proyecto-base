package py.com.proyectobase.report;

public class ColumnHeader {

	public static final int NONE_ROTATION = 0;
	public static final int LEFT_ROTATION = 1;
	public static final int RIGHT_ROTATION = 2;

	private String title;
	private String propertyName;
	private int width;
	private Class<?> contentClazz;
	private int rotation = NONE_ROTATION;
	private boolean sumInFooter;

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public int getWidth() {

		return width;
	}

	public void setWidth(int width) {

		this.width = width;
	}

	public String getPropertyName() {

		return propertyName;
	}

	public void setPropertyName(String propertyName) {

		this.propertyName = propertyName;
	}

	public Class<?> getContentClazz() {

		return contentClazz;
	}

	public void setContentClazz(Class<?> contentClazz) {

		this.contentClazz = contentClazz;
	}

	public boolean isSumInFooter() {

		return sumInFooter;
	}

	public void setSumInFooter(boolean sumInFooter) {

		this.sumInFooter = sumInFooter;
	}

	public int getRotation() {

		return rotation;
	}

	public void setRotation(int rotation) {

		this.rotation = rotation;
	}

}
