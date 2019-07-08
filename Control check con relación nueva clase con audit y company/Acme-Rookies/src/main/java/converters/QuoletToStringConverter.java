/*
 * ActorToStringConverter.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Quolet;

@Component
@Transactional
public class QuoletToStringConverter implements Converter<Quolet, String> {

	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public String convert(final Quolet quolet) {
		return quolet == null ? null : String.valueOf(quolet.getId());
	}

}
