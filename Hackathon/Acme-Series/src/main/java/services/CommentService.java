/*
 * CommentService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import repositories.CommentRepository;
import domain.Comment;
import forms.CommentForm;

@Service
@Transactional
public class CommentService extends AbstractService<CommentRepository, Comment> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private SerieService	serieService;
	@Autowired
	private UserService		userService;


	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public CommentForm createForm() {
		return this.instanceClass(CommentForm.class);
	}

	public Comment reconstructForm(final CommentForm commentForm, final BindingResult bindingResult) {
		final Comment comment = this.create();

		comment.setText(commentForm.getText());
		comment.setScore(commentForm.getScore());
		comment.setSerie(this.serieService.findOne(commentForm.getSerieId()));
		comment.setUser(this.userService.findPrincipal());

		this.validator.validate(comment, bindingResult);

		return comment;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Double[] getCommentsPerSerieStatistics() {
		return this.repository.getCommentsPerSerieStatistics();
	}

}
