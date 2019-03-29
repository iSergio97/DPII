/*
 * StringToEducationDataConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.EducationDataRepository;
import domain.EducationData;

@Component
@Transactional
public class StringToEducationDataConverter implements Converter<String, EducationData> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private EducationDataRepository	educationDataRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public EducationData convert(final String text) {
		try {
			return this.educationDataRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
