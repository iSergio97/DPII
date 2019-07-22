
package services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import domain.CreditCard;
import domain.Provider;
import forms.RegisterProviderForm;
import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ProviderService extends AbstractService<ProviderRepository, Provider> {

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

		final CreditCard cc = new CreditCard();
		cc.setHolder(providerForm.getHolder());
		cc.setBrand(providerForm.getBrand());
		cc.setNumber(providerForm.getNumber());
		cc.setExpirationMonth(providerForm.getExpirationMonth());
		cc.setExpirationYear(providerForm.getExpirationYear());
		cc.setCVV(Integer.valueOf(providerForm.getCVV()));

		result.setCreditCard(cc);

		this.validator.validate(cc, bindingResult);
		this.validator.validate(result, bindingResult);
		this.repository.flush();

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
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.findByUserAccountId(userAccount.getId());
	}

}
