
package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.EnrolmentRepository;
import security.LoginService;
import security.UserAccount;
import domain.Enrolment;
import domain.Position;

@Service
@Transactional
public class EnrolmentService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private EnrolmentRepository			enrolmentRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private MemberService				memberService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	@Autowired
	private Validator					validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public EnrolmentService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Enrolment create() {
		final Enrolment enrolment = new Enrolment();
		final int id = LoginService.getPrincipal().getId();
		enrolment.setMember(this.memberService.findByUserAccountId(id));
		enrolment.setMoment(new Date());
		enrolment.setPosition(this.systemConfigurationService.getSystemConfiguration().getLowestPosition());
		enrolment.setExitMoment(null);

		return enrolment;
	}

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

	public long count() {
		return this.enrolmentRepository.count();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Enrolment findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findOne(userAccount.getId());
	}

	public long countWithPosition(final Position position) {
		return this.enrolmentRepository.countWithPositionId(position.getId());
	}

	public boolean existWithPosition(final Position position) {
		return this.countWithPosition(position) > 0;
	}

}
