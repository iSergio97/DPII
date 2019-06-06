
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import domain.Finder;
import domain.Serie;
import forms.FinderForm;
import repositories.FinderRepository;

@Service
@Transactional
public class FinderService extends AbstractService<FinderRepository, Finder> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private UserService userService;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Finder create() {
		final Finder finder = super.create();

		finder.setUser(this.userService.findPrincipal());

		return finder;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public FinderForm createForm() {
		return this.instanceClass(FinderForm.class);
	}

	public Finder reconstruct(final FinderForm finderForm, final BindingResult binding) {
		Finder result;

		if (finderForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(finderForm.getId());

		result.setKeyword(finderForm.getKeyword());
		result.setMinDate(finderForm.getMinDate());
		result.setMaxDate(finderForm.getMaxDate());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public FinderForm deconstruct(final Finder finder) {
		final FinderForm finderForm = this.createForm();

		finderForm.setId(finder.getId());
		finderForm.setKeyword(finder.getKeyword());
		finderForm.setMinDate(finder.getMinDate());
		finderForm.setMaxDate(finder.getMaxDate());

		return finderForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Collection<Serie> findSeries(final String keyword, final Date minDate, final Date maxDate) {
		return this.repository.findSeries(keyword, minDate, maxDate);
	}

	public Finder findByUser(final int userId) {
		return this.repository.findByUser(userId);
	}

	public Finder findByPrincipal() {
		return this.findByUser(this.userService.findPrincipal().getId());
	}

}
