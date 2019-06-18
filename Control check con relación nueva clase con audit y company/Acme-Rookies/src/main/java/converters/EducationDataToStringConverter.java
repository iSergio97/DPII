/*
 * EducationDataToStringConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EducationData;

@Component
@Transactional
public class EducationDataToStringConverter implements Converter<EducationData, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final EducationData educationData) {
		return educationData == null ? null : String.valueOf(educationData.getId());
	}

}
