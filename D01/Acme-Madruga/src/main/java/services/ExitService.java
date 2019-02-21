
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Exit;

@Service
@Transactional
public class ExitService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private ExitService	exitService;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public ExitService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Exit create() {
		return this.exitService.create();
	}

	public Exit save(final Exit exit) {
		Assert.isTrue(exit != null);
		return this.exitService.save(exit);
	}

	public Iterable<Exit> save(final Iterable<Exit> exits) {
		Assert.isTrue(exits != null);
		return this.exitService.save(exits);
	}

	public Exit delete(final Exit exit) {
		Assert.isTrue(exit != null);
		return this.exitService.delete(exit);
	}

	public Iterable<Exit> delete(final Iterable<Exit> exits) {
		Assert.isTrue(exits != null);
		return this.exitService.delete(exits);
	}

	public Exit findOne(final int exitId) {
		return this.exitService.findOne(exitId);
	}

	public Iterable<Exit> findAll() {
		return this.exitService.findAll();
	}

}
