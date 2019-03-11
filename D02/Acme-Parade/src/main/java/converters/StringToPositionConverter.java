
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PositionRepository;
import domain.Position;

@Component
@Transactional
public class StringToPositionConverter implements Converter<String, Position> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private PositionRepository	positionRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Position convert(final String text) {
		try {
			return this.positionRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
