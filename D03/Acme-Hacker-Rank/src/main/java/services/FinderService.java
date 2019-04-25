
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import domain.Finder;
import domain.Position;
import forms.FinderForm;

@Service
@Transactional
public class FinderService extends AbstractService<FinderRepository, Finder> {

	@Autowired
	private FinderRepository	finderRepository;

	@Autowired
	private HackerService		hackerService;

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
		finder.setHacker(this.hackerService.findPrincipal());
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
		form.setMoment(new Date());

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
		result.setMoment(form.getMoment());

		this.validator.validate(result, binding);

		return result;
	}

	//Ancillary Methods--------------------------------------------

	public Collection<Position> findPositions(final String kw1, final String kw2, final String kw3, final String kw4, final String kw5, final String kw6, final String deadline, final String maximumDeadline, final double minimumSalary) {
		return this.finderRepository.findPositions(kw1, kw2, kw3, kw4, kw5, kw6, deadline, maximumDeadline, minimumSalary);
	}

	public Finder findPrincipal(final int hackerId) {
		Finder f = null;
		f = this.finderRepository.findPrincipal(hackerId);
		return f;
	}

}
