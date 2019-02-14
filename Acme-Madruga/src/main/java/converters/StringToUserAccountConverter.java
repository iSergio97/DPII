package converters;

import org.springframework.beans.fuserAccounty.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.UserAccountRepository;
import domain.UserAccount;

@Component
@Transactional
public class StringToUserAccountConverter implements Converter<String, UserAccount> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private UserAccountRepository	userAccountRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public UserAccount convert(final String text) {
		try {
			return this.userAccountRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
