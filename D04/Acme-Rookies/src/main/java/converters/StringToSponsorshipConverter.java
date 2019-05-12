
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SponsorshipRepository;
import domain.Sponsorship;

@Component
@Transactional
public class StringToSponsorshipConverter implements Converter<String, Sponsorship> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Sponsorship convert(final String text) {
		try {
			return this.sponsorshipRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
