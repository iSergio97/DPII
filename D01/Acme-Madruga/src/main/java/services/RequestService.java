
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.LoginService;
import domain.Member;
import domain.Request;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private RequestRepository	requestRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private MemberService		memberService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private Validator			validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public RequestService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Request create() {
		final Request request = new Request();

		final Member member = this.memberService.findPrincipal();
		request.setMember(member);
		request.setProcession(this.processionService.create());
		request.setHLine(1);
		request.setVLine(1);
		request.setStatus("PENDING");
		request.setReason("");
		return request;
	}

	public RequestForm createForm() {
		final RequestForm request = new RequestForm();

		request.setStatus("PENDING");
		request.setHLine(1);
		request.setVLine(1);
		request.setReason("");

		return request;
	}

	public Request save(final Request request) {
		Assert.isTrue(request != null);
		if (request.getStatus().equals("REJECTED"))
			Assert.isTrue(request.getReason() != null);
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

	//Reconstruct methods

	public Request reconstruct(final Request request, final BindingResult bindingResult) {
		Request res;

		if (request.getId() == 0)
			res = request;
		else {
			res = this.requestRepository.findOne(request.getId());
			res.setStatus(request.getStatus());
			res.setHLine(request.getHLine());
			res.setVLine(request.getVLine());
			res.setReason(request.getReason());
			res.setProcession(request.getProcession());
			res.setMember(request.getMember());

			this.validator.validate(res, bindingResult);
		}
		return res;
	}

	public Request reconstructForm(final RequestForm form, final BindingResult bindingResult) {
		final Request res;

		if (form.getId() == 0)
			res = this.create();
		else
			res = this.requestRepository.findOne(form.getId());

		res.setStatus(form.getStatus());
		res.setHLine(form.getHLine());
		res.setVLine(form.getVLine());
		res.setReason(form.getReason());
		res.setProcession(form.getProcession());
		res.setMember(this.memberService.findByUserAccountId(LoginService.getPrincipal().getId()));

		this.validator.validate(res, bindingResult);

		return res;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Double getAcceptedRatio() {
		return this.requestRepository.getAcceptedRatio();
	}

	public Double getRejectedRatio() {
		return this.requestRepository.getRejectedRatio();
	}

	public Double getPendingRatio() {
		return this.requestRepository.getPendingRatio();
	}

	public List<Member> getMembersWithAtLeastTenPercentOfTheMaximumNumberOfAcceptedRequests() {
		final List<Object[]> membersRequests = this.requestRepository.getMembersOrderedByNumberOfAcceptedRequests();
		final List<Member> result = new ArrayList<Member>();
		if (membersRequests.size() == 0)
			return result;
		final Long maximumNumberOfRequests = (Long) membersRequests.get(0)[1];
		final Double tenPercentOfTheMaximumNumberOfRequests = maximumNumberOfRequests.doubleValue() * 0.1d;
		final int i = 0;
		while (i < membersRequests.size() ? ((Long) membersRequests.get(i)[1]) >= tenPercentOfTheMaximumNumberOfRequests : false)
			result.add((Member) membersRequests.get(i)[0]);
		return result;
	}

	public List<Request> getOrderedBrotherhoodRequests(final int brotherhoodId) {
		return this.requestRepository.getOrderedBrotherhoodRequests(brotherhoodId);
	}

}
