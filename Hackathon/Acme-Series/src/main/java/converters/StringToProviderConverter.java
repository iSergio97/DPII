
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProviderRepository;
import domain.Provider;

@Component
@Transactional
public class StringToProviderConverter implements Converter<String, Provider> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private ProviderRepository	providerRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Provider convert(final String text) {
		try {
			return this.providerRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
