
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Company;
import domain.Position;
import domain.Problem;
import forms.ProblemForm;
import repositories.ProblemRepository;

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

	public ProblemForm createForm(final Problem problem) {
		final ProblemForm form = new ProblemForm();

		form.setTitle(problem.getTitle());
		form.setStatement(problem.getStatement());
		form.setHint(problem.getHint());
		form.setAttachments(problem.getAttachments());
		form.setIsDraft(true);
		form.setId(problem.getId());

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
		result.setIsDraft(form.getIsDraft());

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Position> findPositionAssociated(final int problemId) {

		return this.problemRepository.findPositionAssociated(problemId);
	}
}
