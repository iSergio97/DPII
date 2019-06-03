/*
 * CritiqueService.java
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

import domain.Critique;
import forms.CritiqueForm;
import repositories.CritiqueRepository;

@Service
@Transactional
public class CritiqueService extends AbstractService<CritiqueRepository, Critique> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private CriticService	criticService;
	@Autowired
	private SerieService	serieService;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Critique create() {
		final Critique critique = super.create();
		Assert.notNull(critique);

		Assert.notNull(this.criticService.findPrincipal());
		critique.setCritic(this.criticService.findPrincipal());

		return critique;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public CritiqueForm createForm() {
		return this.instanceClass(CritiqueForm.class);
	}

	public Critique reconstructForm(final CritiqueForm critiqueForm, final BindingResult binding) {
		final Critique critique;

		if (critiqueForm.getId() == 0)
			critique = this.create();
		else {
			critique = this.findOne(critiqueForm.getId());
			Assert.isTrue(critique.getCritic().equals(this.criticService.findPrincipal()));
		}

		critique.setMoment(new Date());
		critique.setText(critiqueForm.getText());
		critique.setScore(critiqueForm.getScore());
		critique.setSerie(this.serieService.findOne(critiqueForm.getSerieId()));

		this.validator.validate(critique, bindingResult);

		return critique;
	}

	public CritiqueForm deconstruct(final Critique critique) {
		final CritiqueForm critiqueForm = this.createForm();

		critiqueForm.setId(critique.getId());
		critiqueForm.setText(critique.getText());
		critiqueForm.setScore(critique.getScore());
		critiqueForm.setSerieId(critique.getSerie().getId());

		return critiqueForm;
	}

	public List<Critique> findAllByUserAccountId(final int userAccountId) {
		return this.repository.findAllByUserAccount(userAccountId);
	}

}
