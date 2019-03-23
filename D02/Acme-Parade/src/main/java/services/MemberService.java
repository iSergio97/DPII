
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Enrolment;
import domain.Member;
import domain.Message;
import domain.SocialProfile;
import forms.MemberForm;

@Service
@Transactional
public class MemberService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private MemberRepository	memberRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private Validator			validator;

	@Autowired
	private FinderService		finderService;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public MemberService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Member create() {
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.MEMBER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		final Member member = new Member();
		member.setUserAccount(userAccount);
		member.setName("");
		member.setMiddleName("");
		member.setSurname("");
		member.setPhoto("");
		member.setEmail("");
		member.setPhoneNumber("");
		member.setAddress("");
		member.setIsSuspicious(false);
		member.setPolarityScore(0);
		member.setIsBanned(false);
		member.setSocialProfiles(new ArrayList<SocialProfile>());
		member.setMessagesSent(new ArrayList<Message>());
		member.setMessagesReceived(new ArrayList<Message>());
		member.setEnrolments(new ArrayList<Enrolment>());
		member.setFinder(this.finderService.create());

		return member;
	}

	public MemberForm createForm() {

		final MemberForm member = new MemberForm();
		member.setName("");
		member.setMiddleName("");
		member.setSurname("");
		member.setPhoto("");
		member.setEmail("");
		member.setPhoneNumber("");
		member.setAddress("");
		member.setUsername("");
		member.setPassword("");
		member.setConfirmPassword("");

		return member;
	}

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
		return this.findByUserAccountId(userAccount.getId());
	}

	public Member findByUserAccountId(final int id) {
		return this.memberRepository.findByUserAccountId(id);
	}

	//Reconstruct method

	public Member reconstruct(final Member member, final BindingResult bindingResult) {

		Member result;

		if (member.getId() == 0)
			result = member;
		else
			result = this.memberRepository.findOne(member.getId());
		result.setName(member.getName());
		result.setMiddleName(member.getMiddleName());
		result.setSurname(member.getSurname());
		result.setPhoto(member.getPhoto());
		result.setEmail(member.getEmail());
		result.setPhoneNumber(member.getPhoneNumber());
		result.setAddress(member.getAddress());

		this.validator.validate(result, bindingResult);
		return result;
	}

	public Member reconstructForm(final MemberForm member, final BindingResult bindingResult) {

		Member result;

		if (member.getId() == 0)
			result = this.create();
		else
			result = this.memberRepository.findOne(member.getId());

		result.setName(member.getName());
		result.setMiddleName(member.getMiddleName());
		result.setSurname(member.getSurname());
		result.setPhoto(member.getPhoto());
		result.setEmail(member.getEmail());
		result.setPhoneNumber(member.getPhoneNumber());
		result.setAddress(member.getAddress());

		result.getUserAccount().setUsername(member.getUsername());
		result.getUserAccount().setPassword(member.getPassword());

		this.validator.validate(result, bindingResult);
		this.memberRepository.flush();

		//TODO: Revisar errores form
		//if (bindingResult.hasErrors())
		//throw new ValidationException();

		return result;
	}

	public MemberForm deconstruct(final Member member) {
		final MemberForm memberf = this.createForm();

		memberf.setId(member.getId());
		memberf.setName(member.getName());
		memberf.setName(member.getName());
		memberf.setMiddleName(member.getMiddleName());
		memberf.setSurname(member.getSurname());
		memberf.setPhoto(member.getPhoto());
		memberf.setEmail(member.getEmail());
		memberf.setPhoneNumber(member.getPhoneNumber());
		memberf.setAddress(member.getAddress());
		memberf.setUsername(member.getUserAccount().getUsername());

		return memberf;

	}

	public Collection<Member> findMemebersByBrotherhoodAccountId(final int id) {
		return this.memberRepository.findMemebersByBrotherhoodAccountId(id);
	}
}
