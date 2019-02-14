package converters;

import org.springframework.beans.fsocialProfiley.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SocialProfileRepository;
import domain.SocialProfile;

@Component
@Transactional
public class StringToSocialProfileConverter implements Converter<String, SocialProfile> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public SocialProfile convert(final String text) {
		try {
			return this.socialProfileRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
