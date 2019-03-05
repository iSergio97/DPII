
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
import domain.MessageBox;
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
	private MessageBoxService	messageBoxService;

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
		final Collection<MessageBox> mbs = this.messageBoxService.createSystemBoxes();
		member.setMessageBoxes(mbs);
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

		/*
		 * Note:
		 * Fallo
		 * JSR-303 validated property 'messageBoxes[4].actor' does not have a corresponding accessor for Spring data binding - check your DataBinder's configuration (bean property versus direct field access)
		 */
		this.validator.validate(result, bindingResult);

		return result;
	}
}
