
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AcmeFloatRepository;
import domain.AcmeFloat;
import domain.Parade;

@Service
@Transactional
public class AcmeFloatService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private AcmeFloatRepository	acmeFloatRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public AcmeFloatService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public AcmeFloat create() {
		final AcmeFloat result = new AcmeFloat();

		// set fields
		result.setTitle("");
		result.setDescription("");
		result.setPictures(new ArrayList<String>());
		// set relationships
		result.setParades(new ArrayList<Parade>());
		result.setBrotherhood(null);

		return result;
	}

	public AcmeFloat save(final AcmeFloat acmeFloat) {
		Assert.isTrue(acmeFloat != null);
		return this.acmeFloatRepository.save(acmeFloat);
	}

	public Iterable<AcmeFloat> save(final Iterable<AcmeFloat> acmeFloats) {
		Assert.isTrue(acmeFloats != null);
		return this.acmeFloatRepository.save(acmeFloats);
	}

	public void delete(final AcmeFloat acmeFloat) {
		Assert.isTrue(acmeFloat != null);
		this.acmeFloatRepository.delete(acmeFloat);
	}

	public void delete(final Iterable<AcmeFloat> acmeFloats) {
		Assert.isTrue(acmeFloats != null);
		this.acmeFloatRepository.delete(acmeFloats);
	}

	public AcmeFloat findOne(final int id) {
		return this.acmeFloatRepository.findOne(id);
	}

	public List<AcmeFloat> findAll() {
		return this.acmeFloatRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Collection<AcmeFloat> findAcmeFloats(final int id) {
		return this.acmeFloatRepository.findFloats(id);
	}

}
