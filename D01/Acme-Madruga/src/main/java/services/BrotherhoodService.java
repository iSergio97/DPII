
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Area;
import domain.Brotherhood;
import domain.Message;
import domain.SocialProfile;
import forms.BrotherhoodForm;

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

	public BrotherhoodForm createForm() {

		final BrotherhoodForm brotherhood = new BrotherhoodForm();
		brotherhood.setName("");
		brotherhood.setMiddleName("");
		brotherhood.setSurname("");
		brotherhood.setPhoto("");
		brotherhood.setEmail("");
		brotherhood.setPhoneNumber("");
		brotherhood.setAddress("");
		brotherhood.setUsername("");
		brotherhood.setPassword("");
		brotherhood.setConfirmPassword("");

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

	public Brotherhood reconstruct(final BrotherhoodForm brotherhood, final BindingResult bindingResult) {

		Brotherhood result;

		if (brotherhood.getId() == 0)
			result = this.create();
		else
			result = this.brotherhoodRepository.findOne(brotherhood.getId());

		result.setName(brotherhood.getName());
		result.setMiddleName(brotherhood.getMiddleName());
		result.setSurname(brotherhood.getSurname());
		result.setPhoto(brotherhood.getPhoto());
		result.setEmail(brotherhood.getEmail());
		result.setPhoneNumber(brotherhood.getPhoneNumber());
		result.setAddress(brotherhood.getAddress());

		this.validator.validate(result, bindingResult);

		return result;
	}

	public boolean existWithArea(final Area area) {
		return this.brotherhoodRepository.countWithAreaId(area.getId()) > 0;
	}

	public Brotherhood reconstructForm(final BrotherhoodForm member, final BindingResult bindingResult) {

		Brotherhood result;

		if (member.getId() == 0)
			result = this.create();
		else
			result = this.brotherhoodRepository.findOne(member.getId());

		result.setName(member.getName());
		result.setMiddleName(member.getMiddleName());
		result.setSurname(member.getSurname());
		result.setPhoto(member.getPhoto());
		result.setEmail(member.getEmail());
		result.setPhoneNumber(member.getPhoneNumber());
		result.setAddress(member.getAddress());

		//Note:
		//Si lo comento, falla en que no guarda userAccount y Finder, pero si no lo comento, peta aqu� con las cajas de mensajes
		/*
		 * Fallo
		 * JSR-303 validated property 'messageBoxes[4].actor' does not have a corresponding accessor for Spring data binding - check your DataBinder's configuration (bean property versus direct field access)
		 */
		this.validator.validate(result, bindingResult);

		return result;
	}

}
