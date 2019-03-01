
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Message;
import domain.SocialProfile;

@Service
@Transactional
public class BrotherhoodService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private Validator				validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public BrotherhoodService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Brotherhood create() {
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		final Brotherhood brotherhood = new Brotherhood();
		brotherhood.setUserAccount(userAccount);
		brotherhood.setName("");
		brotherhood.setMiddleName("");
		brotherhood.setSurname("");
		brotherhood.setPhoto("");
		brotherhood.setEmail("");
		brotherhood.setPhoneNumber("");
		brotherhood.setAddress("");
		brotherhood.setIsSuspicious(false);
		brotherhood.setPolarityScore(null);
		brotherhood.setIsBanned(false);
		brotherhood.setSocialProfiles(new ArrayList<SocialProfile>());
		brotherhood.setMessagesSent(new ArrayList<Message>());
		brotherhood.setMessagesReceived(new ArrayList<Message>());
		brotherhood.setMessageBoxes(this.messageBoxService.createSystemBoxes());
		brotherhood.setPictures(new ArrayList<String>());

		return brotherhood;
	}
	public Brotherhood save(final Brotherhood brotherhood) {
		Assert.isTrue(brotherhood != null);
		brotherhood.setEstablishmentDate(new Date());
		return this.brotherhoodRepository.save(brotherhood);
	}

	public Iterable<Brotherhood> save(final Iterable<Brotherhood> brotherhoods) {
		Assert.isTrue(brotherhoods != null);
		return this.brotherhoodRepository.save(brotherhoods);
	}

	public void delete(final Brotherhood brotherhood) {
		Assert.isTrue(brotherhood != null);
		this.brotherhoodRepository.delete(brotherhood);
	}

	public void delete(final Iterable<Brotherhood> brotherhoods) {
		Assert.isTrue(brotherhoods != null);
		this.brotherhoodRepository.delete(brotherhoods);
	}

	public Brotherhood findOne(final int id) {
		return this.brotherhoodRepository.findOne(id);
	}

	public List<Brotherhood> findAll() {
		return this.brotherhoodRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Brotherhood findByUserAccountId(final int id) {
		return this.brotherhoodRepository.findByUserAccountId(id);
	}

	public Brotherhood findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

	public Brotherhood reconstruct(final Brotherhood brotherhood, final BindingResult bindingResult) {

		Brotherhood result;

		if (brotherhood.getId() == 0)
			result = brotherhood;
		else {
			result = this.brotherhoodRepository.findOne(brotherhood.getId());
			result.setName(brotherhood.getName());
			result.setMiddleName(brotherhood.getMiddleName());
			result.setSurname(brotherhood.getSurname());
			result.setPhoto(brotherhood.getPhoto());
			result.setEmail(brotherhood.getEmail());
			result.setPhoneNumber(brotherhood.getPhoneNumber());
			result.setAddress(brotherhood.getAddress());

			this.validator.validate(result, bindingResult);
		}
		return result;
	}

}
