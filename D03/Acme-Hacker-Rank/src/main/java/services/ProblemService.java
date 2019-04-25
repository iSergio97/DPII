
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import domain.Company;
import domain.Problem;
import forms.ProblemForm;

@Service
@Transactional
public class ProblemService extends AbstractService<ProblemRepository, Problem> {

	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private Validator			validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public ProblemService() {
		super();
	}

	@Override
	public Problem create() {
		final Problem problem = new Problem();
		final Company c = this.companyService.findPrincipal();

		problem.setCompany(c);
		problem.setTitle("");
		problem.setStatement("");
		problem.setHint("");
		problem.setAttachments("");
		problem.setIsDraft(true);

		return problem;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public ProblemForm createForm() {
		final ProblemForm form = new ProblemForm();

		form.setTitle("");
		form.setStatement("");
		form.setHint("");
		form.setAttachments("");

		return form;
	}

	public Problem reconstruct(final ProblemForm form, final BindingResult binding) {
		Problem result;

		if (form.getId() == 0)
			result = this.create();
		else
			result = this.problemRepository.findOne(form.getId());

		result.setTitle(form.getTitle());
		result.setStatement(form.getStatement());
		result.setHint(form.getHint());
		result.setAttachments(form.getAttachments());

		this.validator.validate(result, binding);

		return result;
	}

}
