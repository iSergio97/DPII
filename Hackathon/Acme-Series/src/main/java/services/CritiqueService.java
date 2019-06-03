/*
 * CritiqueService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import repositories.CritiqueRepository;
import domain.Critique;
import forms.CritiqueForm;

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

		critique.setCritic(this.criticService.findPrincipal());

		return critique;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public CritiqueForm createForm() {
		return this.instanceClass(CritiqueForm.class);
	}

	public Critique reconstructForm(final CritiqueForm critiqueForm, final BindingResult bindingResult) {
		final Critique critique;

		if (critiqueForm.getId() == 0)
			critique = this.create();
		else
			critique = this.findOne(critiqueForm.getId());

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

}
