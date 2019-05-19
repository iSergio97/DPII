/*
 * SerieService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import repositories.SerieRepository;
import domain.Serie;
import forms.SerieForm;

@Service
@Transactional
public class SerieService extends AbstractService<SerieRepository, Serie> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private PublisherService	publisherService;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public SerieService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Serie create() {
		final Serie serie = super.create();

		serie.setStatus("ON EMISSION");
		serie.setPublisher(this.publisherService.findPrincipal());

		return serie;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public SerieForm createForm() {
		return this.instanceClass(SerieForm.class);
	}

	public Serie reconstruct(final SerieForm serieForm, final BindingResult binding) {
		Serie result;

		if (serieForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(serieForm.getId());

		result.setTitle(serieForm.getTitle());
		result.setDescription(serieForm.getDescription());
		result.setBanner(serieForm.getBanner());
		result.setStartDate(serieForm.getStartDate());
		result.setEndDate(serieForm.getEndDate());
		result.setStatus(serieForm.getStatus());
		result.setGenre(serieForm.getGenre());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public SerieForm deconstruct(final Serie serie) {
		final SerieForm serieForm = this.createForm();

		serieForm.setId(serie.getId());
		serieForm.setTitle(serie.getTitle());
		serieForm.setDescription(serie.getDescription());
		serieForm.setBanner(serie.getBanner());
		serieForm.setStartDate(serie.getStartDate());
		serieForm.setEndDate(serie.getEndDate());
		serieForm.setStatus(serie.getStatus());
		serieForm.setGenre(serie.getGenre());

		return serieForm;
	}

}
