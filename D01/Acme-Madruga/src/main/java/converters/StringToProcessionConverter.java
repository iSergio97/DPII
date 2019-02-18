
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProcessionRepository;
import domain.Procession;

@Component
@Transactional
public class StringToProcessionConverter implements Converter<String, Procession> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private ProcessionRepository	processionRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Procession convert(final String text) {
		try {
			return this.processionRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
