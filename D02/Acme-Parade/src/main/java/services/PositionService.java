
package services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import utilities.ConversionUtils;
import domain.Position;
import forms.PositionForm;

@Service
@Transactional
public class PositionService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private PositionRepository	positionRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator			validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public PositionService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Position create() {
		final Position position = new Position();
		position.setStrings(new HashMap<String, String>());
		return position;
	}

	public Position save(final Position position) {
		Assert.isTrue(position != null);
		return this.positionRepository.save(position);
	}

	public Iterable<Position> save(final Iterable<Position> positions) {
		Assert.isTrue(positions != null);
		return this.positionRepository.save(positions);
	}

	public void delete(final Position position) {
		Assert.isTrue(position != null);
		this.positionRepository.delete(position);
	}

	public void delete(final Iterable<Position> positions) {
		Assert.isTrue(positions != null);
		this.positionRepository.delete(positions);
	}

	public Position findOne(final int id) {
		return this.positionRepository.findOne(id);
	}

	public List<Position> findAll() {
		return this.positionRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public PositionForm createForm() {
		final PositionForm positionForm = new PositionForm();
		positionForm.setStrings("");
		return positionForm;
	}

	public Position reconstruct(final PositionForm positionForm, final BindingResult bindingResult) {
		Position result;

		if (positionForm.getId() == 0)
			result = this.create();
		else
			result = this.positionRepository.findOne(positionForm.getId());

		result.setStrings(ConversionUtils.stringToMap(positionForm.getStrings(), ":", ";"));

		this.validator.validate(result, bindingResult);

		return result;
	}

	public PositionForm deconstruct(final Position position) {
		final PositionForm positionForm = this.createForm();

		positionForm.setId(position.getId());
		positionForm.setStrings(ConversionUtils.mapToString(position.getStrings(), ":", ";"));

		return positionForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
