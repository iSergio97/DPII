package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Request;

@Component
@Transactional
public class RequestToStringConverter implements Converter<Request, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Request request) {
		return request == null ? null : String.valueOf(request.getId());
	}

}
