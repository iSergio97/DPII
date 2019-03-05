
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Member;
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
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ProcessionService processionService;
	

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public RequestService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods
	
	public Request create() {
		Request request = new Request();
		
		Member member = this.memberService.findPrincipal();
		request.setMember(member);
		request.setProcession(this.processionService.create());
		request.setHLine(0);
		request.setVLine(0);
		request.setStatus("PENDING");
		request.setReason("");
		return request;
	}

	public Request save(final Request request) {
		Assert.isTrue(request != null);
		if(request.getStatus().equals("REJECTED")) {
			Assert.isTrue(request.getReason() != null);
		}
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

}
