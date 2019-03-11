
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AreaRepository;
import domain.Area;

@Component
@Transactional
public class StringToAreaConverter implements Converter<String, Area> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private AreaRepository	areaRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Area convert(final String text) {
		try {
			return this.areaRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
