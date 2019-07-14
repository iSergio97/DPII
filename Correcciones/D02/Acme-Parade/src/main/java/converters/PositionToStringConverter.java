
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Position;

@Component
@Transactional
public class PositionToStringConverter implements Converter<Position, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Position position) {
		return position == null ? null : String.valueOf(position.getId());
	}

}
