package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ActorRepository;
import domain.Actor;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private ActorRepository	actorRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Actor convert(final String text) {
		try {
			return this.actorRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
