/*
 * StringToApplicationConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ApplicationRepository;
import domain.Application;

@Component
@Transactional
public class StringToApplicationConverter implements Converter<String, Application> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private ApplicationRepository	applicationRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Application convert(final String text) {
		try {
			return this.applicationRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
