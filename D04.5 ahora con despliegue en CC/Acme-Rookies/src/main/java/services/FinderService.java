
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Finder;
import domain.Position;
import forms.FinderForm;
import repositories.FinderRepository;

@Service
@Transactional
public class FinderService extends AbstractService<FinderRepository, Finder> {

	@Autowired
	private FinderRepository	finderRepository;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private Validator			validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public FinderService() {
		super();
	}

	@Override
	public Finder create() {
		final Finder finder = new Finder();
		finder.setKeyword("");
		finder.setDeadline(new Date());
		finder.setMinimumSalary(0.0);
		finder.setMaximumDeadline(new Date());
		finder.setMoment(new Date());
		finder.setRookie(this.rookieService.findPrincipal());
		finder.setPositions(new ArrayList<Position>());

		return finder;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public FinderForm createForm() {
		final FinderForm form = new FinderForm();

		form.setKeyword("");
		form.setDeadline(new Date());
		form.setMinimumSalary(0.0);
		form.setMaximumDeadline(new Date());

		return form;
	}

	public Finder reconstruct(final FinderForm form, final BindingResult binding) {
		Finder result;

		if (form.getId() == 0)
			result = this.create();
		else
			result = this.finderRepository.findOne(form.getId());

		result.setKeyword(form.getKeyword());
		result.setDeadline(form.getDeadline());
		result.setMinimumSalary(form.getMinimumSalary());
		result.setMaximumDeadline(form.getMaximumDeadline());

		this.validator.validate(result, binding);

		if (binding.hasErrors()) {
			throw new ValidationException();
		}

		return result;
	}

	//Ancillary Methods--------------------------------------------

	public Collection<Position> findPositions(final String kw1, final String kw2, final String kw3, final String kw4, final String kw5, final String kw6, final Date deadline, final Date maximumDeadline, final double minimumSalary) {
		return this.finderRepository.findPositions(kw1, kw2, kw3, kw4, kw5, kw6, deadline, maximumDeadline, minimumSalary);
	}

	public Finder findPrincipal(final int rookieId) {
		Finder f = null;
		f = this.finderRepository.findPrincipal(rookieId);
		return f;
	}

	public FinderForm deconstruct(Finder finder) {
		FinderForm f = this.createForm();
		f.setKeyword(finder.getKeyword());
		f.setId(finder.getId());
		f.setDeadline(finder.getDeadline());
		f.setMinimumSalary(finder.getMinimumSalary());
		return f;
	}

}
