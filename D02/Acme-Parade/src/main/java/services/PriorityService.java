
package services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PriorityRepository;
import domain.Priority;

@Service
@Transactional
public class PriorityService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private PriorityRepository	priorityRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public PriorityService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Priority create() {
		final Priority priority = new Priority();
		priority.setStrings(new HashMap<String, String>());
		return priority;
	}

	public Priority save(final Priority priority) {
		Assert.isTrue(priority != null);
		return this.priorityRepository.save(priority);
	}

	public Iterable<Priority> save(final Iterable<Priority> priorities) {
		Assert.isTrue(priorities != null);
		return this.priorityRepository.save(priorities);
	}

	public void delete(final Priority priority) {
		Assert.isTrue(priority != null);
		this.priorityRepository.delete(priority);
	}

	public void delete(final Iterable<Priority> priorities) {
		Assert.isTrue(priorities != null);
		this.priorityRepository.delete(priorities);
	}

	public Priority findOne(final int id) {
		return this.priorityRepository.findOne(id);
	}

	public List<Priority> findAll() {
		return this.priorityRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
