package py.com.proyectobase.converters;

import org.springframework.core.convert.converter.Converter;

import py.com.proyectobase.dao.Dao;
import py.com.proyectobase.domain.Model;
import py.una.cnc.lib.core.util.AppLogger;

public abstract class ModelConverter<M extends Model> implements
		Converter<String, M> {

	private final AppLogger logger = new AppLogger(this.getClass());

	@Override
	public M convert(String idStr) {

		try {
			Long objId = Long.valueOf(idStr);
			Dao<M> dao = getDaoImpl();
			M object = dao.find(objId);
			return object;
		} catch (NumberFormatException nfe) {
			return null;
		} catch (Exception exc) {
			logger.error("Error al convertir objeto: {}", exc, exc.getMessage());
			return null;
		}
	}

	protected abstract Dao<M> getDaoImpl();
}
