
package controllers;

import java.util.ArrayList;
import java.util.Collection;

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
import security.UserAccount;
import services.AcmeFloatService;
import services.BrotherhoodService;
import services.ProcessionService;
import domain.AcmeFloat;
import domain.Brotherhood;
import domain.Procession;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private AcmeFloatService	acmeFloatService;


	// Constructors -----------------------------------------------------------

	public ProcessionController() {

	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Procession> processions;

		processions = this.processionService.findAllByBrotherhoodAccountId(LoginService.getPrincipal().getId());

		result = new ModelAndView("procession/brotherhood/list");
		result.addObject("processions", processions);
		result.addObject("requestURI", "procession/brotherhood/list.do");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Procession procession;

		procession = this.processionService.create();
		procession.setIsDraft(true);
		result = this.createEditModelAndView(procession, "create");

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId) {
		ModelAndView result;
		Procession procession;

		procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);
		procession.setAcmeFloats(new ArrayList<AcmeFloat>());
		result = this.createEditModelAndView(procession, "edit");

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Procession procession, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(procession, "edit");
		else
			try {
				procession.setIsDraft(true);
				this.processionService.save(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(procession, "procession.commit.error", "edit");
			}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Procession procession, final BindingResult binding) {
		ModelAndView result;

		try {
			this.processionService.delete(procession);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(procession, "procession.commit.error", "edit");
		}

		return result;
	}

	// Save in Final Mode -----------------------------------------------------

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "finalMode")
	public ModelAndView finalMode(@Valid final Procession procession, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(procession, "edit");
		else
			try {
				procession.setIsDraft(false);
				this.processionService.save(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(procession, "procession.commit.error", "edit");
			}

		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int processionId) {
		ModelAndView result;
		Procession procession;
		procession = this.processionService.findOne(processionId);

		Brotherhood brotherhood;
		Collection<AcmeFloat> acmeFloats;

		procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);

		brotherhood = procession.getBrotherhood();
		final UserAccount userAccount = brotherhood.getUserAccount();
		acmeFloats = this.acmeFloatService.findAcmeFloats(userAccount.getId());

		result = new ModelAndView("procession/public/" + "show");
		result.addObject("brotherhood", brotherhood);
		result.addObject("acmeFloats", acmeFloats);

		result.addObject("procession", procession);

		// result.addObject("messageCode", null);

		return result;
	}
	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Procession procession, final String method) {
		ModelAndView result;

		result = this.createEditModelAndView(procession, null, method);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Procession procession, final String messageCode, final String method) {
		final ModelAndView result;
		final Brotherhood brotherhood;
		final Collection<AcmeFloat> acmeFloats;
		final UserAccount userAccount = LoginService.getPrincipal();

		brotherhood = this.brotherhoodService.findPrincipal();
		acmeFloats = this.acmeFloatService.findAcmeFloats(userAccount.getId());

		result = new ModelAndView("procession/brotherhood/" + method);
		result.addObject("brotherhood", brotherhood);
		result.addObject("acmeFloats", acmeFloats);

		result.addObject("procession", procession);

		result.addObject("messageCode", messageCode);

		return result;
	}

}
