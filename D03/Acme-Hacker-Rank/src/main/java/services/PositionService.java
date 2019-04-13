
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Company;
import domain.Position;
import domain.Problem;
import forms.PositionForm;
import repositories.PositionRepository;
import security.LoginService;

@Service
@Transactional
public class PositionService extends AbstractService<Position> {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private Validator			validator;

	@Autowired
	private CompanyService		companyService;

	private static final int	TICKER_LENGTH	= 5;
	private final Random		random			= new Random();
	private static final String	TICKER_NUMBER	= "0123456789";


	public PositionService() {
		super();
	}

	public Position create() {
		Position position;
		position = new Position();

		position.setTitle("");
		position.setDescription("");
		position.setProblems(new ArrayList<Problem>());
		position.setProfile("");
		position.setSkills("");
		position.setTechnologies("");
		position.setSalary(0);
		position.setDraft(true);
		this.generateTicker(position);

		return position;
	}

	public PositionForm createForm() {
		PositionForm posForm;
		posForm = new PositionForm();

		posForm.setDescription("");
		posForm.setTitle("");
		posForm.setProfile("");
		posForm.setSalary(0);
		posForm.setSkills("");
		posForm.setTechnologies("");
		posForm.setProblems(new ArrayList<Problem>());

		return posForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Iterable<Position> save(final Iterable<Position> positions) {
		Assert.isTrue(positions != null);
		return this.positionRepository.save(positions);
	}

	@Override
	public Position save(final Position position) {
		Assert.notNull(position);
		Position res = this.findOne(position.getId());

		while (res == null || res.getId() == 0)
			try {
				res = this.positionRepository.save(position);
			} catch (final Throwable ops) {
				this.generateTicker(position);
			}

		return res;
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
		res.setProfile(position.getProfile());
		res.setSkills(position.getSkills());
		res.setTechnologies(position.getTechnologies());
		res.setSalary(position.getSalary());
		res.setDeadline(position.getDeadline());
		if (position.getProblems() == null)
			res.setProblems(new ArrayList<Problem>());
		else
			res.setProblems(position.getProblems());
		res.setCompany(this.companyService.findPrincipal());

		this.validator.validate(res, bindingResult);
		this.positionRepository.flush();
		if (bindingResult.hasErrors())
			throw new ValidationException();

		return res;
	}

	private void generateTicker(final Position position) {
		String ticker = "";
		final Company company = this.companyService.findByUserAccountId(LoginService.getPrincipal().getId());
		ticker += company.getCommercialName().substring(0, 4);
		ticker += "-";
		do
			for (int i = 0; i < PositionService.TICKER_LENGTH; ++i)
				ticker += PositionService.TICKER_NUMBER.charAt(this.random.nextInt(PositionService.TICKER_NUMBER.length()));
		while (this.positionRepository.findByTicker(ticker).size() > 0);
		position.setTicker(ticker);
	}

	public List<Problem> findProblemsByCompany(final Company company) {
		return this.positionRepository.findProblemsBycompany(company.getId());
	}

}
