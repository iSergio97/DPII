package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Member;

@Component
@Transactional
public class MemberToStringConverter implements Converter<Member, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Member member) {
		return member == null ? null : String.valueOf(member.getId());
	}

}
