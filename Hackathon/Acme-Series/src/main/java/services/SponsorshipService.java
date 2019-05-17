
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Service
@Transactional
public class SponsorshipService extends AbstractService<SponsorshipRepository, Sponsorship> {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private Validator				validator;


	public SponsorshipService() {
		super();
	}

	@Override
	public Sponsorship create() {
		final Sponsorship result = new Sponsorship();
		final Provider provider = this.providerService.findPrincipal();

		result.setCreditCard(null);
		result.setProvider(provider);
		result.setPosition(null);

		result.setBanner("");
		result.setTargetPage("");

		return result;
	}

	////////////////////////////////////////////////////////////////
	//Form methods

	public SponsorshipForm createForm() {
		final SponsorshipForm result = new SponsorshipForm();

		result.setBanner("");
		result.setTargetPage("");
		result.setPosition(null);

		//CreditCard
		result.setBrand("");
		result.setHolder("");
		result.setCVV(100);
		result.setExpirationMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
		result.setExpirationYear(Calendar.getInstance().get(Calendar.YEAR) % 100);

		return result;
	}

	public Sponsorship reconstruct(final SponsorshipForm form, final BindingResult binding) {
		Sponsorship result;

		if (form.getId() == 0)
			result = this.create();
		else
			result = this.sponsorshipRepository.findOne(form.getId());

		result.setBanner(form.getBanner());
		result.setTargetPage(form.getTargetPage());

		final CreditCard cc = new CreditCard();
		cc.setBrand(form.getBrand());
		cc.setHolder(form.getHolder());
		cc.setCVV(form.getCVV());
		cc.setNumber(form.getNumber());
		cc.setExpirationMonth(form.getExpirationMonth());
		cc.setExpirationYear(form.getExpirationYear());

		result.setCreditCard(cc);
		result.setPosition(form.getPosition());

		this.validator.validate(cc, binding);
		this.validator.validate(result, binding);

		return result;
	}

	public SponsorshipForm deconstruct(final Sponsorship sponsorship) {
		final SponsorshipForm sponsorshipForm = this.createForm();

		sponsorshipForm.setId(sponsorship.getId());
		sponsorshipForm.setBanner(sponsorship.getBanner());
		sponsorshipForm.setTargetPage(sponsorship.getTargetPage());
		sponsorshipForm.setPosition(sponsorship.getPosition());

		sponsorshipForm.setHolder(sponsorship.getCreditCard().getHolder());
		sponsorshipForm.setBrand(sponsorship.getCreditCard().getBrand());
		sponsorshipForm.setNumber(sponsorship.getCreditCard().getNumber());
		sponsorshipForm.setExpirationMonth(sponsorship.getCreditCard().getExpirationMonth());
		sponsorshipForm.setExpirationYear(sponsorship.getCreditCard().getExpirationYear());
		sponsorshipForm.setCVV(sponsorship.getCreditCard().getCVV());

		return sponsorshipForm;
	}

	public Collection<Sponsorship> findByProviderId(final int providerId) {
		return this.sponsorshipRepository.findByProviderId(providerId);
	}

	public Collection<Sponsorship> findByPositionId(final int positionId) {
		return this.sponsorshipRepository.findByPositionId(positionId);
	}

	public Double minPro() {
		return this.sponsorshipRepository.minPro();
	}

	public Double maxPro() {
		return this.sponsorshipRepository.maxPro();
	}

	public Double mediaPro() {
		return this.sponsorshipRepository.mediaPro();
	}

	public Double stdDevPro() {
		return this.sponsorshipRepository.stdDevPro();
	}

}
