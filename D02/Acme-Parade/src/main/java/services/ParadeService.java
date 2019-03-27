
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParadeRepository;
import security.LoginService;
import domain.AcmeFloat;
import domain.Parade;
import forms.ParadeForm;

@Service
@Transactional
public class ParadeService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private ParadeRepository	paradeRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private BrotherhoodService	brotherhoodService;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private Validator			validator;

	////////////////////////////////////////////////////////////////////////////////
	// Ticker generation fields

	private static final String	TICKER_ALPHABET	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int	TICKER_LENGTH	= 5;
	private final Random		random			= new Random();


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public ParadeService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Parade create() {
		final Parade parade = new Parade();
		parade.setBrotherhood(this.brotherhoodService.findByUserAccountId(LoginService.getPrincipal().getId()));
		parade.setAcmeFloats(new ArrayList<AcmeFloat>());
		parade.setIsDraft(true);
		parade.setDescription("");
		parade.setTitle("");
		if (parade.getTicker() == null || parade.getTicker().isEmpty())
			this.generateTicker(parade);
		return parade;
	}

	private void generateTicker(final Parade parade) {
		final Calendar calendar = new GregorianCalendar();
		String dateString = "";
		dateString += String.format("%02d", calendar.get(Calendar.YEAR) % 100);
		dateString += String.format("%02d", calendar.get(Calendar.MONTH) + 1);
		dateString += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
		dateString += "-";
		String ticker;
		do {
			ticker = dateString;
			for (int i = 0; i < ParadeService.TICKER_LENGTH; ++i)
				ticker += ParadeService.TICKER_ALPHABET.charAt(this.random.nextInt(ParadeService.TICKER_ALPHABET.length()));
		} while (this.paradeRepository.findByTicker(ticker).size() > 0);
		parade.setTicker(ticker);
	}

	public Parade save(final Parade parade) {
		Assert.notNull(parade);

		Parade res = null;
		while (res == null || res.getId() == 0)
			try {
				res = this.paradeRepository.save(parade);
			} catch (final Throwable oops) {
				this.generateTicker(parade);
			}

		return res;
	}

	public void delete(final Parade parade) {
		Assert.notNull(parade);
		Assert.isTrue(parade.getIsDraft());

		this.paradeRepository.delete(parade);
	}
	public Parade findOne(final int id) {
		return this.paradeRepository.findOne(id);
	}

	public List<Parade> findAll() {
		return this.paradeRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public List<Parade> findWithin30Days() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 30);
		final Date plus30Days = calendar.getTime();
		return this.paradeRepository.findBeforeDate(plus30Days);
	}

	public List<Parade> findAllByBrotherhoodAccountId(final int id) {
		return this.paradeRepository.findAllByBrotherhoodAccountId(id);
	}

	public List<Parade> findAllFinalByBrotherhoodAccountId(final int id) {
		return this.paradeRepository.findAllFinalByBrotherhoodAccountId(id);
	}

	public List<Parade> findAllFinal() {
		return this.paradeRepository.findAllFinal();
	}

	public List<Parade> findPossibleMemberParades(final int memberId) {
		return this.paradeRepository.findPossibleMemberParades(memberId);
	}

	public Parade reconstruct(final ParadeForm paradeForm, final BindingResult binding) {
		Parade result;

		if (paradeForm.getId() == 0)
			result = this.create();
		else
			result = this.paradeRepository.findOne(paradeForm.getId());

		result.setTitle(paradeForm.getTitle());
		result.setDescription(paradeForm.getDescription());
		result.setMoment(paradeForm.getMoment());
		result.setAcmeFloats(paradeForm.getAcmeFloats());

		this.validator.validate(result, binding);
		this.paradeRepository.flush();
		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

}
