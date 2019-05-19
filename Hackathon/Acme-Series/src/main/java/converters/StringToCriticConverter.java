/*
 * StringToCriticConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CriticRepository;
import domain.Critic;

@Component
@Transactional
public class StringToCriticConverter implements Converter<String, Critic> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private CriticRepository	criticRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Critic convert(final String text) {
		try {
			return this.criticRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
