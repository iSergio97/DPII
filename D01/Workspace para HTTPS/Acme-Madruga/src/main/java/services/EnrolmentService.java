
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EnrolmentRepository;
import domain.Enrolment;

@Service
@Transactional
public class EnrolmentService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private EnrolmentRepository	enrolmentRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public EnrolmentService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Enrolment save(final Enrolment enrolment) {
		Assert.isTrue(enrolment != null);
		return this.enrolmentRepository.save(enrolment);
	}

	public Iterable<Enrolment> save(final Iterable<Enrolment> enrolments) {
		Assert.isTrue(enrolments != null);
		return this.enrolmentRepository.save(enrolments);
	}

	public void delete(final Enrolment isRegistered) {
		Assert.isTrue(isRegistered != null);
		this.enrolmentRepository.delete(isRegistered);
	}

	public void delete(final Iterable<Enrolment> isRegistereds) {
		Assert.isTrue(isRegistereds != null);
		this.enrolmentRepository.delete(isRegistereds);
	}

	public Enrolment findOne(final int id) {
		return this.enrolmentRepository.findOne(id);
	}

	public List<Enrolment> findAll() {
		return this.enrolmentRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
