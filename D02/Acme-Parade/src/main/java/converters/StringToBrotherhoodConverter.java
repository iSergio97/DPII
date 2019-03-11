
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BrotherhoodRepository;
import domain.Brotherhood;

@Component
@Transactional
public class StringToBrotherhoodConverter implements Converter<String, Brotherhood> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Brotherhood convert(final String text) {
		try {
			return this.brotherhoodRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
