package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.LoginService;
import security.UserAccount;
import domain.Member;

@Service
@Transactional
public class MemberService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private MemberRepository		memberRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public MemberService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Member save(final Member member) {
		Assert.isTrue(member != null);
		return this.memberRepository.save(member);
	}

	public Iterable<Member> save(final Iterable<Member> members) {
		Assert.isTrue(members != null);
		return this.memberRepository.save(members);
	}

	public void delete(final Member member) {
		Assert.isTrue(member != null);
		this.memberRepository.delete(member);
	}

	public void delete(final Iterable<Member> members) {
		Assert.isTrue(members != null);
		this.memberRepository.delete(members);
	}

	public Member findOne(final int id) {
		return this.memberRepository.findOne(id);
	}

	public List<Member> findAll() {
		return this.memberRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Member findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findOne(userAccount.getId());
	}

	public Member findByUserAccountId(final int id) {
		return this.memberRepository.findByUserAccountId(id);
	}

}
