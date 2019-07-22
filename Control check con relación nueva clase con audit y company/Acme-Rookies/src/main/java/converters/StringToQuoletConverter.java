/*
 * StringToPersonalDataConverter.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Quolet;
import repositories.QuoletRepository;

@Component
@Transactional
public class StringToQuoletConverter implements Converter<String, Quolet> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private QuoletRepository quoletRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Quolet convert(final String text) {
		try {
			return this.quoletRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
