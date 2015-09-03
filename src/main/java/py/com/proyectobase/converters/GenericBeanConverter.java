package py.com.proyectobase.converters;

import java.util.Collections;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import py.com.proyectobase.domain.Model;
import py.una.cnc.lib.core.util.AppLogger;

public class GenericBeanConverter implements GenericConverter {
	AppLogger logger = new AppLogger(GenericBeanConverter.class);

	@Override
	public Object convert(Object objectId, TypeDescriptor arg1,
			TypeDescriptor modelType) {

		Model model;
		try {
			model = (Model) modelType.getType().newInstance();
			final Long id = Long.valueOf((String) objectId);
			model.setId(id);
			return model;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Collections.singleton(new ConvertiblePair(String.class,
				Model.class));
	}

}