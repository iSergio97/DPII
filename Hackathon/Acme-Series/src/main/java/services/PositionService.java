
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Publisher;
import domain.Serie;
import domain.Problem;
import forms.SerieForm;
import repositories.PositionRepository;
import security.LoginService;

@Service
@Transactional
public class PositionService extends AbstractService<PositionRepository, Serie> {

	@Autowired
	private Validator			validator;

	@Autowired
	private CompanyService		companyService;

	private static final int	TICKER_LENGTH	= 4;
	private final Random		random			= new Random();
	private static final String	TICKER_NUMBER	= "0123456789";


	public PositionService() {
		super();
	}

	@Override
	public Serie create() {
		Serie position;
		position = new Serie();

		position.setTitle("");
		position.setDescription("");
		position.setProblems(new ArrayList<Problem>());
		position.setProfile("");
		position.setSkills("");
		position.setTechnologies("");
		position.setSalary(0);
		position.setIsDraft(true);
		this.generateTicker(position);
		position.setCompany(this.companyService.findPrincipal());
		position.setStatus("SUBMITTED");

		return position;
	}

	public SerieForm createForm() {
		SerieForm posForm;
		posForm = new SerieForm();

		posForm.setDescription("");
		posForm.setTitle("");
		posForm.setProfile("");
		posForm.setSalary(0);
		posForm.setSkills("");
		posForm.setTechnologies("");
		posForm.setStatus("SUBMITTED");
		posForm.setProblems(new ArrayList<Problem>());

		return posForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Serie save(final Serie position) {
		Assert.notNull(position);
		Serie res = this.findOne(position.getId());

		while (res == null || res.getId() == 0)
			try {
				res = this.repository.save(position);
			} catch (final Throwable ops) {
				this.generateTicker(position);
			}

		return res;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods
	public Serie reconstruct(final SerieForm position, final BindingResult bindingResult) {
		final Serie res;

		if (position.getId() == 0)
			res = this.create();
		else
			res = this.repository.findOne(position.getId());

		res.setTitle(position.getTitle());
		res.setDescription(position.getDescription());
		res.setProfile(position.getProfile());
		res.setSkills(position.getSkills());
		res.setTechnologies(position.getTechnologies());
		res.setSalary(position.getSalary());
		res.setDeadline(position.getDeadline());
		res.setStatus(position.getStatus());
		if (position.getProblems() == null)
			res.setProblems(new ArrayList<Problem>());
		else
			res.setProblems(position.getProblems());

		this.validator.validate(res, bindingResult);
		this.repository.flush();
		if (bindingResult.hasErrors())
			throw new ValidationException();

		return res;
	}

	private void generateTicker(final Serie position) {
		String ticker = "";
		final Publisher company = this.companyService.findByUserAccountId(LoginService.getPrincipal().getId());
		ticker += company.getCommercialName().substring(0, 4);
		ticker += "-";
		do
			for (int i = 0; i < PositionService.TICKER_LENGTH; ++i)
				ticker += PositionService.TICKER_NUMBER.charAt(this.random.nextInt(PositionService.TICKER_NUMBER.length()));
		while (this.repository.findByTicker(ticker).size() > 0);
		position.setTicker(ticker);
	}

	public List<Problem> findProblemsByCompany(final Publisher company) {
		return this.repository.findProblemsBycompany(company.getId());
	}

	public List<Serie> findPositionsByCompany(final Publisher company) {
		return this.repository.findPositionsByCompany(company.getId());
	}

	public Collection<Serie> findPositionsForPublic(final Publisher company) {
		return this.repository.findPositionForPublicAndCompany(company.getId());
	}

	public SerieForm deconstruct(final Serie pos) {
		final SerieForm pf = this.createForm();
		pf.setTitle(pos.getTitle());
		pf.setId(pos.getId());
		pf.setTechnologies(pos.getTitle());
		pf.setDescription(pos.getDescription());
		pf.setDeadline(pos.getDeadline());
		pf.setProfile(pf.getProfile());
		pf.setSkills(pos.getSkills());
		pf.setTechnologies(pos.getTechnologies());
		pf.setSalary(pos.getSalary());
		pf.setStatus(pos.getStatus());
		pf.setProblems(pos.getProblems());

		return pf;
	}

	public Double min() {
		return this.repository.min();
	}

	public Double max() {
		return this.repository.max();
	}

	public Double media() {
		return this.repository.media();
	}

	public Double stdDev() {
		return this.repository.stdDev();
	}

	public List<Publisher> companyMax() {
		return this.repository.companyMax();
	}

	public Collection<Serie> searchQuery(String text) {
		String query = "%" + text + "%";
		return this.repository.searchQuery(query);
	}
}
