/*
 * StringToProblemConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProblemRepository;
import domain.Problem;

@Component
@Transactional
public class StringToProblemConverter implements Converter<String, Problem> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private ProblemRepository	problemRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Problem convert(final String text) {
		try {
			return this.problemRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
