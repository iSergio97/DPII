/*
 * StringToCommentConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CommentRepository;
import domain.Comment;

@Component
@Transactional
public class StringToCommentConverter implements Converter<String, Comment> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private CommentRepository	commentRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Comment convert(final String text) {
		try {
			return this.commentRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
