
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.DomainEntity;
import repositories.AbstractRepository;

@Service
@Transactional
public class AbstractService<T extends DomainEntity> {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	AbstractRepository<T> abstractRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public T save(final T t) {
		Assert.isTrue(t != null);
		return this.abstractRepository.save(t);
	}

	public void delete(final T actor) {
		Assert.isTrue(actor != null);
		this.abstractRepository.delete(actor);
	}

	public void delete(final Iterable<T> t) {
		Assert.isTrue(t != null);
		this.abstractRepository.delete(t);
	}

	public T findOne(final int id) {
		return this.abstractRepository.findOne(id);
	}

	public List<T> findAll() {
		return this.abstractRepository.findAll();
	}

}
