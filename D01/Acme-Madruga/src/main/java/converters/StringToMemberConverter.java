package converters;

import org.springframework.beans.fmembery.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MemberRepository;
import domain.Member;

@Component
@Transactional
public class StringToMemberConverter implements Converter<String, Member> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private MemberRepository	memberRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Member convert(final String text) {
		try {
			return this.memberRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
