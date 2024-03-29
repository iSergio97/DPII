
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.CreditCard;
import domain.Provider;
import forms.RegisterProviderForm;

@Service
@Transactional
public class ProviderService extends AbstractService<ProviderRepository, Provider> {

	@Autowired
	private UserAccountRepository	userAccountRepository;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Provider create() {
		final Provider provider = super.create();

		// create user account
		final UserAccount userAccount = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		userAccount.setUsername("");
		userAccount.setPassword("");
		provider.setUserAccount(userAccount);
		// set fields
		provider.setMake("");
		provider.setName("");
		provider.setSurnames("");
		provider.setVat("");
		provider.setEmail("");
		provider.setCreditCard(null);
		provider.setPhoto("");
		provider.setPhoneNumber("");
		provider.setAddress("");
		provider.setIsFlagged(false);
		provider.setIsBanned(false);

		// set relationships
		return provider;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public RegisterProviderForm createForm() {
		final RegisterProviderForm providerForm = new RegisterProviderForm();

		providerForm.setMake("");
		providerForm.setName("");
		providerForm.setSurnames("");
		providerForm.setVat("");
		providerForm.setPhoto("");
		providerForm.setEmail("");
		providerForm.setPhoneNumber("");
		providerForm.setAddress("");
		providerForm.setUsername("");
		providerForm.setPassword("");
		providerForm.setConfirmPassword("");
		providerForm.setCVV("");
		providerForm.setBrand("");
		providerForm.setHolder("");

		return providerForm;
	}

	public Provider reconstructForm(final RegisterProviderForm providerForm, final BindingResult bindingResult) {
		final Provider result;
		final List<String> usernames = this.userAccountRepository.getUserNames();

		final Date date = new Date();

		if (providerForm.getExpirationMonth() == null || providerForm.getExpirationYear() == null) {
			if (providerForm.getExpirationMonth() == null) {
				final ObjectError error = new ObjectError("expirationMonthNull", "The month of the credit card is null");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationMonth", "error.monthNull");
			}

			if (providerForm.getExpirationYear() == null) {
				final ObjectError error = new ObjectError("expirationYearNull", "The year of the credit card is nu��");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationYear", "error.yearNull");
			}
		} else if (providerForm.getExpirationYear() < (date.getYear() % 100) || providerForm.getExpirationYear() == (date.getYear() % 100) && providerForm.getExpirationMonth() < (date.getMonth() + 1)) {
			if (providerForm.getExpirationYear() < (date.getYear() % 100)) {
				final ObjectError error = new ObjectError("expirationYear", "The year of the credit card is older than the current year");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationYear", "error.oldYear");
			}
			if (providerForm.getExpirationYear() == (date.getYear() % 100) && providerForm.getExpirationMonth() < (date.getMonth() + 1)) {
				final ObjectError error = new ObjectError("expirationMonth", "The month of the credit card is older than the current month");
				bindingResult.addError(error);
				bindingResult.rejectValue("expirationMonth", "error.oldMonth");
			}
		}

		if (providerForm.getId() == 0) {
			if (usernames.contains(providerForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUserName");
			}
		} else {
			final Provider provider3 = this.findPrincipal();
			usernames.remove(provider3.getUserAccount().getUsername());
			if (usernames.contains(providerForm.getUsername())) {
				final ObjectError error = new ObjectError("userName", "An account already exists for this username.");
				bindingResult.addError(error);
				bindingResult.rejectValue("username", "error.existedUsername");
			}
		}

		if (providerForm.getUsername().length() < 5 || providerForm.getUsername().length() > 32) {
			final ObjectError error = new ObjectError("username", "This username is too short or too long. Please, use another.");
			bindingResult.addError(error);
			bindingResult.rejectValue("username", "error.shortUserName");
		}

		if (!providerForm.getPassword().equals(providerForm.getConfirmPassword())) {
			final ObjectError error = new ObjectError("pass", "Both password do not match. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.wrongPass");
		}
		if (providerForm.getPassword().length() == 0) {
			final ObjectError error = new ObjectError("pass", "Password must not be empty!. Try again.");
			bindingResult.addError(error);
			bindingResult.rejectValue("password", "error.nullPass");
		}

		if (providerForm.getPhoneNumber().length() < 3) {
			final ObjectError error = new ObjectError("phoneNumber", "Short phone number");
			bindingResult.addError(error);
			bindingResult.rejectValue("phoneNumber", "error.shortNumber");
		}

		if (providerForm.getCVV() == "") {
			final ObjectError error = new ObjectError("CVV", "nullCvv");
			bindingResult.addError(error);
			bindingResult.rejectValue("CVV", "error.nullCvv");
		} else if (Integer.valueOf(providerForm.getCVV()) < 100) {
			final ObjectError error = new ObjectError("CVV", "shortCvv");
			bindingResult.addError(error);
			bindingResult.rejectValue("CVV", "error.shortCvv");
		}

		if (providerForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(providerForm.getId());

		result.setMake(providerForm.getMake());
		result.setName(providerForm.getName());
		result.setSurnames(providerForm.getSurnames());
		result.setVat(providerForm.getVat());
		result.setSurnames(providerForm.getSurnames());
		result.setPhoto(providerForm.getPhoto());
		result.setEmail(providerForm.getEmail());
		result.setPhoneNumber(providerForm.getPhoneNumber());
		result.setAddress(providerForm.getAddress());

		result.getUserAccount().setUsername(providerForm.getUsername());
		result.getUserAccount().setPassword(providerForm.getPassword());

		this.validator.validate(result, bindingResult);

		final CreditCard cc = new CreditCard();
		cc.setHolder(providerForm.getHolder());
		cc.setBrand(providerForm.getBrand());
		cc.setNumber(providerForm.getNumber());
		cc.setExpirationMonth(providerForm.getExpirationMonth());
		cc.setExpirationYear(providerForm.getExpirationYear());
		cc.setCVV(Integer.valueOf(providerForm.getCVV()));

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return result;
	}
	public RegisterProviderForm deconstruct(final Provider provider) {
		final RegisterProviderForm providerForm = this.createForm();

		providerForm.setId(provider.getId());
		providerForm.setMake(provider.getMake());
		providerForm.setName(provider.getName());
		providerForm.setSurnames(provider.getSurnames());
		providerForm.setVat(provider.getVat());
		providerForm.setPhoto(provider.getPhoto());
		providerForm.setEmail(provider.getEmail());
		providerForm.setPhoneNumber(provider.getPhoneNumber());
		providerForm.setAddress(provider.getAddress());

		providerForm.setUsername(provider.getUserAccount().getUsername());
		providerForm.setPassword(provider.getUserAccount().getPassword());

		providerForm.setHolder(provider.getCreditCard().getHolder());
		providerForm.setBrand(provider.getCreditCard().getBrand());
		providerForm.setNumber(provider.getCreditCard().getNumber());
		providerForm.setExpirationMonth(provider.getCreditCard().getExpirationMonth());
		providerForm.setExpirationYear(provider.getCreditCard().getExpirationYear());
		providerForm.setCVV(String.valueOf(provider.getCreditCard().getCVV()));

		return providerForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Provider findByUserAccountId(final int id) {
		return this.repository.findByUserAccountId(id);
	}

	public Provider findPrincipal() {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
			return null;
		else {
			final UserAccount userAccount = LoginService.getPrincipal();
			for (final Authority authority : userAccount.getAuthorities())
				if (authority.getAuthority().equals(Authority.PROVIDER))
					return this.findByUserAccountId(userAccount.getId());
			return null;
		}
	}

}
