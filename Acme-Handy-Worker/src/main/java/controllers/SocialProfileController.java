
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.SocialProfileService;
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping("/social-profile")
public class SocialProfileController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SocialProfileService	socialProfileService;


	public SocialProfileController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();
		result = this.createEditModelAndView(socialProfile, "social-profile/create");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int id) {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.findById(id);
		Assert.notNull(socialProfile);

		result = this.createEditModelAndView(socialProfile, "social-profile/edit");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final SocialProfile socialProfile, final BindingResult bindings) {
		ModelAndView result;
		if (bindings.hasErrors())
			result = this.createEditModelAndView(socialProfile, "social-profile/edit");
		else
			try {
				this.socialProfileService.save(socialProfile);
				result = new ModelAndView("redirect:../profile/show.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error", "social-profile/edit");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final SocialProfile socialProfile, final BindingResult bindings) {
		ModelAndView result;
		try {
			this.socialProfileService.delete(socialProfile);
			result = new ModelAndView("redirect:../profile/show.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error", "social-profile/edit");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String viewName) {
		ModelAndView result;

		result = this.createEditModelAndView(socialProfile, null, viewName);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String messageCode, final String viewName) {
		ModelAndView result;
		String nickName;
		String socialNetworkName;
		String link;
		Actor actor;

		nickName = socialProfile.getNickName();
		socialNetworkName = socialProfile.getSocialNetworkName();
		link = socialProfile.getLink();
		final int principalId = LoginService.getPrincipal().getId();
		actor = this.actorService.findByUserAccountId(principalId);

		// Ligera modificación por motivos de tiles.xml (<h1>)
		result = new ModelAndView(viewName);

		result.addObject("socialProfile", socialProfile);

		result.addObject("nickName", nickName);
		result.addObject("socialNetworkName", socialNetworkName);
		result.addObject("link", link);
		result.addObject("actor", actor);

		result.addObject("messageCode", messageCode);

		return result;
	}
}
