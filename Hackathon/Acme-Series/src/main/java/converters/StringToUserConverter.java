/*
 * StringToUserConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.UserRepository;
import domain.User;

@Component
@Transactional
public class StringToUserConverter implements Converter<String, User> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private UserRepository	userRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public User convert(final String text) {
		try {
			return this.userRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
