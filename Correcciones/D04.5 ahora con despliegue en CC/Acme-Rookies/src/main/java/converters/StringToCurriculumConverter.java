/*
 * StringToCompanyConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CurriculumRepository;
import domain.Curriculum;

@Component
@Transactional
public class StringToCurriculumConverter implements Converter<String, Curriculum> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private CurriculumRepository	curriculumRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Curriculum convert(final String text) {
		try {
			return this.curriculumRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
