
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ParadeRepository;
import security.LoginService;
import domain.AcmeFloat;
import domain.Parade;

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
		parade.setMoment(new Date());
		parade.setTitle("");
		if (parade.getTicker() == null || parade.getTicker().isEmpty()) {
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

		return parade;
	}

	public Parade save(final Parade parade) {
		Assert.isTrue(parade != null);
		if (parade.getMoment() == null)
			parade.setMoment(new Date());

		//TODO: if ticker existe en BBDD, generar nuevo, else, se guarda
		return this.paradeRepository.save(parade);
	}

	public void delete(final Parade parade) {
		Assert.isTrue(parade != null);

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

	/*
	 * public Parade reconstruct(final ParadeForm paradeForm, final BindingResult binding) {
	 * Parade result;
	 * 
	 * result = this.paradeRepository.findOne();
	 * 
	 * validator.validate(result, binding);
	 * 
	 * return result;
	 * }
	 */

}
