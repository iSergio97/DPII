
package services;

import java.util.ArrayList;
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
		brotherhood.setPictures(new ArrayList<String>());
		brotherhood.setPolarityScore(0);

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
		result.setTitle(brotherhood.getTitle());
		result.setEstablishmentDate(brotherhood.getEstablishmentDate());

		this.validator.validate(result, bindingResult);

		return result;
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
		//Si lo comento, falla en que no guarda userAccount y Finder, pero si no lo comento, peta aquí con las cajas de mensajes
		/*
		 * Fallo
		 * JSR-303 validated property 'messageBoxes[4].actor' does not have a corresponding accessor for Spring data binding - check your DataBinder's configuration (bean property versus direct field access)
		 */
		this.validator.validate(result, bindingResult);

		return result;
	}

	public boolean existWithArea(final Area area) {
		return this.brotherhoodRepository.countWithAreaId(area.getId()) > 0;
	}

	public Double[] getMemberStatistics() {
		return this.brotherhoodRepository.getMemberStatistics();
	}

	public List<Brotherhood> findSmallestBrotherhoods(final int number) {
		final List<Brotherhood> allOrderedByHistorySizeAscending = this.brotherhoodRepository.findAllOrderedByHistorySizeDescending();
		final int total = Math.min(number, allOrderedByHistorySizeAscending.size());
		final List<Brotherhood> smallestBrotherhoods = new ArrayList<>(total);
		smallestBrotherhoods.addAll(allOrderedByHistorySizeAscending.subList(0, total));
		return smallestBrotherhoods;
	}

	public List<Brotherhood> findLargestBrotherhoods(final int number) {
		final List<Brotherhood> allOrderedByHistorySizeDescending = this.brotherhoodRepository.findAllOrderedByHistorySizeDescending();
		final int total = Math.min(number, allOrderedByHistorySizeDescending.size());
		final List<Brotherhood> largestBrotherhoods = new ArrayList<>(total);
		largestBrotherhoods.addAll(allOrderedByHistorySizeDescending.subList(0, total));
		return largestBrotherhoods;
	}

	public BrotherhoodForm deconstruct(final Brotherhood brotherhood) {
		final BrotherhoodForm bhForm = this.createForm();

		bhForm.setId(brotherhood.getId());
		bhForm.setName(brotherhood.getName());
		bhForm.setName(brotherhood.getName());
		bhForm.setMiddleName(brotherhood.getMiddleName());
		bhForm.setSurname(brotherhood.getSurname());
		bhForm.setPhoto(brotherhood.getPhoto());
		bhForm.setEmail(brotherhood.getEmail());
		bhForm.setPhoneNumber(brotherhood.getPhoneNumber());
		bhForm.setAddress(brotherhood.getAddress());
		bhForm.setUsername(brotherhood.getUserAccount().getUsername());
		bhForm.setTitle(brotherhood.getTitle());

		return bhForm;
	}

	public Double[] getHistoryStatistics() {
		return this.brotherhoodRepository.getHistoryStatistics();
	}

	public List<Brotherhood> findBrotherhoodsWithTheLargestHistory(final int number) {
		final List<Brotherhood> allOrderedByHistorySizeDescending = this.brotherhoodRepository.findAllOrderedByHistorySizeDescending();
		final int total = Math.min(number, allOrderedByHistorySizeDescending.size());
		final List<Brotherhood> brotherhoodsWithTheLargestHistory = new ArrayList<>(total);
		brotherhoodsWithTheLargestHistory.addAll(allOrderedByHistorySizeDescending.subList(0, total));
		return brotherhoodsWithTheLargestHistory;
	}

	public List<Brotherhood> findBrotherhoodsWithHistoryLargerThanAverage() {
		return this.brotherhoodRepository.findWithHistoryLargerThanAverage();
	}

}
