
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Request;

@Service
@Transactional
public class RequestService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private RequestRepository	requestRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public RequestService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Request save(final Request request) {
		Assert.isTrue(request != null);
		return this.requestRepository.save(request);
	}

	public Iterable<Request> save(final Iterable<Request> requests) {
		Assert.isTrue(requests != null);
		return this.requestRepository.save(requests);
	}

	public void delete(final Request request) {
		Assert.isTrue(request != null);
		this.requestRepository.delete(request);
	}

	public void delete(final Iterable<Request> requests) {
		Assert.isTrue(requests != null);
		this.requestRepository.delete(requests);
	}

	public Request findOne(final int id) {
		return this.requestRepository.findOne(id);
	}

	public List<Request> findAll() {
		return this.requestRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
