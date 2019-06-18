
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProviderService;
import domain.Provider;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	@Autowired
	private ProviderService	providerService;


	public ProviderController() {

	}

	//////////////////////////////////////////////////////////
	// List public

	@RequestMapping(value = "/public/list", method = RequestMethod.GET)
	public ModelAndView publicList() {
		ModelAndView result;
		final Collection<Provider> providers = this.providerService.findAll();

		result = this.createModelAndViewWithSystemConfiguration("provider/public/list");
		result.addObject("providers", providers);

		return result;
	}

	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
	public ModelAndView publicShow(@RequestParam final int providerId) {
		ModelAndView result;
		final Provider provider = this.providerService.findOne(providerId);

		result = this.createModelAndViewWithSystemConfiguration("provider/public/show");
		result.addObject("provider", provider);

		return result;
	}

}
