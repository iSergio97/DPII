/*
 * StringToUserAccountConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import security.UserAccount;
import security.UserAccountRepository;

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
