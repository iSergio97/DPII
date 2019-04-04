
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import domain.Position;
import domain.Problem;
import forms.PositionForm;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private Validator			validator;


	public PositionService() {
		super();
	}

	public Position create() {
		Position position;
		position = new Position();

		position.setTitle("");
		position.setDescription("");
		position.setProblems(new ArrayList<Problem>());
		position.setProfileRequired("");
		position.setSkillsRequired("");
		position.setTechnologiesRequired("");
		position.setSalaryOffered(0);
		position.setDraft(true);
		position.setStatus("");

		return position;
	}

	public PositionForm createForm() {
		PositionForm posForm;
		posForm = new PositionForm();

		posForm.setDescription("");
		posForm.setTitle("");
		posForm.setProfileRequired("");
		posForm.setSalaryOffered(0);
		posForm.setSkillsRequired("");
		posForm.setTechnologiesRequired("");

		return posForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Position save(final Position position) {
		Assert.isTrue(position != null);
		//TODO: Añadir ticker a la hora de guardar
		return this.positionRepository.save(position);
	}

	public Iterable<Position> save(final Iterable<Position> positions) {
		Assert.isTrue(positions != null);
		return this.positionRepository.save(positions);
	}

	public void delete(final Position Position) {
		Assert.isTrue(Position != null);
		this.positionRepository.delete(Position);
	}

	public void delete(final Iterable<Position> Positions) {
		Assert.isTrue(Positions != null);
		this.positionRepository.delete(Positions);
	}

	public Position findOne(final int id) {
		return this.positionRepository.findOne(id);
	}

	public List<Position> findAll() {
		return this.positionRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods
	public Position reconstruct(final PositionForm position, final BindingResult bindingResult) {
		final Position res;

		if (position.getId() == 0)
			res = this.create();
		else
			res = this.positionRepository.findOne(position.getId());

		res.setTitle(position.getTitle());
		res.setDescription(position.getDescription());
		res.setProfileRequired(position.getProfileRequired());
		res.setSkillsRequired(position.getSkillsRequired());
		res.setTechnologiesRequired(position.getTechnologiesRequired());
		res.setSalaryOffered(position.getSalaryOffered());
		res.setDeadline(position.getDeadline());
		res.setStatus(position.getStatus());
		res.setSubmitMoment(new Date());

		this.validator.validate(res, bindingResult);
		this.positionRepository.flush();
		if (bindingResult.hasErrors())
			throw new ValidationException();

		return res;
	}
}
