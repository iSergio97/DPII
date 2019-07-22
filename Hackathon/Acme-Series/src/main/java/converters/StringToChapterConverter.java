/*
 * StringToChapterConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ChapterRepository;
import domain.Chapter;

@Component
@Transactional
public class StringToChapterConverter implements Converter<String, Chapter> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private ChapterRepository	chapterRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Chapter convert(final String text) {
		try {
			return this.chapterRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
