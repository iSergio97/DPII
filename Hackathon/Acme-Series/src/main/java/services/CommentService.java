/*
 * CommentService.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Comment;
import domain.Critique;
import forms.CommentForm;
import repositories.CommentRepository;

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
	// CRUD methods

	@Override
	public Comment create() {
		final Comment comment = super.create();
		Assert.notNull(comment);

		Assert.notNull(this.userService.findPrincipal());
		comment.setUser(this.userService.findPrincipal());

		return comment;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public CommentForm createForm() {
		return this.instanceClass(CommentForm.class);
	}

	public Comment reconstructForm(final CommentForm commentForm, final BindingResult binding) {
		Comment comment;

		if (commentForm.getId() == 0)
			comment = this.create();
		else {
			comment = this.create();
			Assert.isTrue(comment.getUser().equals(this.userService.findPrincipal()));
		}

		comment.setMoment(new Date());
		comment.setText(commentForm.getText());
		comment.setScore(commentForm.getScore());
		comment.setSerie(this.serieService.findOne(commentForm.getSerieId()));

		this.validator.validate(comment, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return comment;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Double[] getCommentsPerSerieStatistics() {
		return this.repository.getCommentsPerSerieStatistics();
	}

	public List<Critique> findAllByUserAccountId(final int userAccountId) {
		return this.repository.findAllByUserAccount(userAccountId);
	}

}
