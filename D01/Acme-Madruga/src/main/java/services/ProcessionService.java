
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import utilities.TickerGenerator;
import domain.AcmeFloat;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private ProcessionRepository	processionRepository;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

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
		if (procession.getTicker() == null || procession.getTicker().isEmpty())
			procession.setTicker(TickerGenerator.generateTicker());
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
