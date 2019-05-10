
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialProfileService;
import domain.Actor;
import domain.SocialProfile;
import forms.SocialProfileForm;

@Controller
@RequestMapping("/socialprofile")
public class SocialProfileController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService			actorService;
	@Autowired
	private SocialProfileService	socialProfileService;


	// Constructors -----------------------------------------------------------

	public SocialProfileController() {
		super();
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/actor/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Actor actor;
		final Collection<SocialProfile> socialProfiles;

		actor = this.actorService.findPrincipal();
		if (actor == null)
			return new ModelAndView("redirect:/welcome/index.do");
		socialProfiles = this.socialProfileService.findByActor(actor);

		result = new ModelAndView("socialprofile/actor/list");
		result.addObject("socialProfiles", socialProfiles);

		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/actor/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final Actor actor;
		final SocialProfile socialProfile;

		actor = this.actorService.findPrincipal();
		if (actor == null)
			return new ModelAndView("redirect:/welcome/index.do");
		socialProfile = this.socialProfileService.findOne(id);
		if (socialProfile == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!socialProfile.getActor().equals(actor))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("socialprofile/actor/show");

		result.addObject("socialProfile", socialProfile);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/actor/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Actor actor;
		final SocialProfileForm socialProfileForm;

		actor = this.actorService.findPrincipal();
		if (actor == null)
			return new ModelAndView("redirect:/welcome/index.do");
		socialProfileForm = this.socialProfileService.createForm();

		result = new ModelAndView("socialprofile/actor/edit");
		result.addObject("socialProfileForm", socialProfileForm);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/actor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final Actor actor;
		final SocialProfile socialProfile;

		actor = this.actorService.findPrincipal();
		if (actor == null)
			return new ModelAndView("redirect:/welcome/index.do");
		socialProfile = this.socialProfileService.findOne(id);
		if (socialProfile == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!socialProfile.getActor().equals(actor))
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("socialprofile/actor/edit");

		result.addObject("socialProfileForm", this.socialProfileService.deconstruct(socialProfile));

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/actor/save", method = RequestMethod.POST)
	public ModelAndView edit(@Valid @ModelAttribute("socialProfileForm") final SocialProfileForm socialProfileForm, final BindingResult bindingResult) {
		final ModelAndView result;
		final Actor actor;
		SocialProfile socialProfile;

		actor = this.actorService.findPrincipal();
		if (actor == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (socialProfileForm.getId() != 0) {
			socialProfile = this.socialProfileService.findOne(socialProfileForm.getId());
			if (socialProfile == null)
				return new ModelAndView("redirect:/welcome/index.do");
			if (!socialProfile.getActor().equals(actor))
				return new ModelAndView("redirect:/welcome/index.do");
		}
		if (!bindingResult.hasErrors()) {
			socialProfile = this.socialProfileService.reconstructForm(socialProfileForm, bindingResult);
			socialProfile.setActor(actor);
			socialProfile = this.socialProfileService.save(socialProfile);
			result = this.show(socialProfile.getId());
		} else {
			result = new ModelAndView("socialprofile/actor/edit");
			result.addObject("socialProfileForm", socialProfileForm);
		}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/actor/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "id") final int id) {
		final Actor actor;
		SocialProfile socialProfile;

		actor = this.actorService.findPrincipal();
		if (actor == null)
			return new ModelAndView("redirect:/welcome/index.do");
		socialProfile = this.socialProfileService.findOne(id);
		if (socialProfile == null)
			return new ModelAndView("redirect:/welcome/index.do");
		if (!socialProfile.getActor().equals(actor))
			return new ModelAndView("redirect:/welcome/index.do");

		this.socialProfileService.delete(socialProfile);

		return this.list();
	}

}
