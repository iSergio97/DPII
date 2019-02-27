
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

import repositories.ProcessionRepository;
import domain.AcmeFloat;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private ProcessionRepository	processionRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private BrotherhoodService		brotherhoodService;

	////////////////////////////////////////////////////////////////////////////////
	// Ticker generation fields

	private static final String		TICKER_ALPHABET	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int		TICKER_LENGTH	= 5;
	private final Random			random			= new Random();


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public ProcessionService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Procession create() {
		final Procession procession = new Procession();
		procession.setBrotherhood(this.brotherhoodService.create());
		procession.setAcmeFloats(new ArrayList<AcmeFloat>());

		return procession;
	}

	public Procession save(final Procession procession) {
		Assert.isTrue(procession != null);
		if (procession.getTicker() == null || procession.getTicker().isEmpty()) {
			final Calendar calendar = new GregorianCalendar();
			String dateString = "";
			dateString += String.format("%02d", calendar.get(Calendar.YEAR) % 100);
			dateString += String.format("%02d", calendar.get(Calendar.MONTH) + 1);
			dateString += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
			dateString += "-";
			String ticker;
			do {
				ticker = dateString;
				for (int i = 0; i < ProcessionService.TICKER_LENGTH; ++i)
					ticker += ProcessionService.TICKER_ALPHABET.charAt(this.random.nextInt(ProcessionService.TICKER_ALPHABET.length()));
			} while (this.processionRepository.findByTicker(ticker).size() > 0);
			procession.setTicker(ticker);
		}
		if (procession.getMoment() == null)
			procession.setMoment(new Date());
		return this.processionRepository.save(procession);
	}

	public void delete(final Procession procession) {
		Assert.isTrue(procession != null);

		this.processionRepository.delete(procession);
	}

	public Procession findOne(final int id) {
		return this.processionRepository.findOne(id);
	}

	public List<Procession> findAll() {
		return this.processionRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
