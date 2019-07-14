
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AreaRepository;
import utilities.ConversionUtils;
import domain.Area;
import forms.AreaForm;

@Service
@Transactional
public class AreaService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private AreaRepository	areaRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	@Autowired
	private Validator		validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public AreaService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Area create() {
		final Area area = new Area();
		area.setName("Default name");
		area.setPictures(new ArrayList<String>());
		return area;
	}

	public Area save(final Area area) {
		Assert.isTrue(area != null);
		return this.areaRepository.save(area);
	}

	public Iterable<Area> save(final Iterable<Area> areas) {
		Assert.isTrue(areas != null);
		return this.areaRepository.save(areas);
	}

	public void delete(final Area area) {
		Assert.isTrue(area != null);
		this.areaRepository.delete(area);
	}

	public void delete(final Iterable<Area> areas) {
		Assert.isTrue(areas != null);
		this.areaRepository.delete(areas);
	}

	public Area findOne(final int id) {
		return this.areaRepository.findOne(id);
	}

	public List<Area> findAll() {
		return this.areaRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public AreaForm createForm() {
		final AreaForm areaForm = new AreaForm();
		areaForm.setName("");
		areaForm.setPictures("");
		return areaForm;
	}

	public Area reconstruct(final AreaForm areaForm, final BindingResult bindingResult) {
		Area result;

		if (areaForm.getId() == 0)
			result = this.create();
		else
			result = this.areaRepository.findOne(areaForm.getId());

		result.setName(areaForm.getName());
		result.setPictures(ConversionUtils.stringToList(areaForm.getPictures(), " "));

		this.validator.validate(result, bindingResult);

		return result;
	}

	public AreaForm deconstruct(final Area area) {
		final AreaForm areaForm = this.createForm();

		areaForm.setId(area.getId());
		areaForm.setName(area.getName());
		areaForm.setPictures(ConversionUtils.listToString(area.getPictures(), " "));

		return areaForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
