
package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private FinderRepository	finderRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public FinderService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Finder create() {
		final Finder f = new Finder();
		f.setKeyword("");
		final Date minDate = new Date();
		f.setMinimumDate(minDate);
		final Date maxDate = minDate;
		maxDate.setYear(minDate.getYear() + 1);
		f.setMaximumDate(maxDate);
		return f;
	}

	public Finder save(final Finder finder) {
		Assert.isTrue(finder != null);
		return this.finderRepository.save(finder);
	}

	public Iterable<Finder> save(final Iterable<Finder> finders) {
		Assert.isTrue(finders != null);
		return this.finderRepository.save(finders);
	}

	public void delete(final Finder finder) {
		Assert.isTrue(finder != null);
		this.finderRepository.delete(finder);
	}

	public void delete(final Iterable<Finder> finders) {
		Assert.isTrue(finders != null);
		this.finderRepository.delete(finders);
	}

	public Finder findOne(final int id) {
		return this.finderRepository.findOne(id);
	}

	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
