/*
 * StringToCritiqueConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CritiqueRepository;
import domain.Critique;

@Component
@Transactional
public class StringToCritiqueConverter implements Converter<String, Critique> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private CritiqueRepository	critiqueRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Critique convert(final String text) {
		try {
			return this.critiqueRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
