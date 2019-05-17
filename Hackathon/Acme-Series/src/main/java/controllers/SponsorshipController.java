
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import services.ProviderService;
import services.SponsorshipService;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Controller
@RequestMapping("/sponsorship")
public class SponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ProviderService		providerService;

	@Autowired
	private PositionService		positionService;


	public SponsorshipController() {

	}

	/////////////////////////////////////////////////////////////////
	//List

	@RequestMapping(value = "/provider/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Provider provider = this.providerService.findPrincipal();
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findByProviderId(provider.getId());

		result = new ModelAndView("sponsorship/provider/list");
		result.addObject("sponsorships", sponsorships);

		return result;

	}

	///////////////////////////////////////////////////////////////
	//Show

	@RequestMapping(value = "/provider/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		result = new ModelAndView("sponsorship/provider/show");
		result.addObject("sponsorship", sponsorship);

		return result;
	}

	//////////////////////////////////////////////////////////////
	//Create

	@RequestMapping(value = "/provider/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SponsorshipForm sponsorship;

		sponsorship = this.sponsorshipService.createForm();
		result = this.createAndEditModelAndView(sponsorship);

		return result;
	}

	////////////////////////////////////////////////////////////
	//Edit

	@RequestMapping(value = "/provider/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		SponsorshipForm form;
		final Collection<Position> positions = this.positionService.findAll();
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);

		form = this.sponsorshipService.deconstruct(sponsorship);

		result = new ModelAndView("sponsorship/provider/edit");
		result.addObject("sponsorship", form);
		result.addObject("positions", positions);

		return result;
	}

	////////////////////////////////////////////////////////////
	//Save

	@RequestMapping(value = "/provider/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("sponsorship") final SponsorshipForm form, final BindingResult binding) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.reconstruct(form, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(form);
		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = this.list();
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(form, "item.commit.error");
			}

		return result;
	}

	//////////////////////////////////////////////////////////
	//Delete

	@RequestMapping(value = "/provider/edit", params = "delete", method = RequestMethod.POST)
	public ModelAndView delete(@ModelAttribute("sponsorship") final SponsorshipForm form, final BindingResult binding) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.reconstruct(form, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(form);
		else
			try {
				this.sponsorshipService.delete(sponsorship);
				result = this.list();
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(form, "item.commit.error");
			}

		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final SponsorshipForm sponsorship) {
		ModelAndView result;

		result = this.createAndEditModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final SponsorshipForm sponsorship, final String message) {
		final ModelAndView result;
		final Collection<Position> positions = this.positionService.findAll();

		result = new ModelAndView("sponsorship/provider/create");
		result.addObject("sponsorship", sponsorship);
		result.addObject("positions", positions);
		result.addObject("message", message);

		return result;
	}
}
