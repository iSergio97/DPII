package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Area;

@Component
@Transactional
public class AreaToStringConverter implements Converter<Area, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Area area) {
		return area == null ? null : String.valueOf(area.getId());
	}

}
