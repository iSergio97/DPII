package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private ProcessionRepository		processionRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public ProcessionService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Procession save(final Procession procession) {
		Assert.isTrue(procession != null);
		return this.processionRepository.save(procession);
	}

	public Iterable<Procession> save(final Iterable<Procession> processions) {
		Assert.isTrue(processions != null);
		return this.processionRepository.save(processions);
	}

	public void delete(final Procession procession) {
		Assert.isTrue(procession != null);
		this.processionRepository.delete(procession);
	}

	public void delete(final Iterable<Procession> processions) {
		Assert.isTrue(processions != null);
		this.processionRepository.delete(processions);
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
