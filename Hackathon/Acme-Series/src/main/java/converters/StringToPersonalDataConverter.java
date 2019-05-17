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

import repositories.PersonalDataRepository;
import domain.PersonalData;

@Component
@Transactional
public class StringToPersonalDataConverter implements Converter<String, PersonalData> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private PersonalDataRepository	personalDataRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public PersonalData convert(final String text) {
		try {
			return this.personalDataRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
