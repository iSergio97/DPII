
package controllers;

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

import services.AcmeFloatService;
import services.BrotherhoodService;
import services.ProcessionService;
import domain.AcmeFloat;
import domain.Brotherhood;
import domain.Procession;

@Controller
@RequestMapping("/procession/administrator-brotherhood-member")
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Procession> processions;

		processions = this.processionService.findAll();

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("requestURI", "procession/administrator-brotherhood-member/list.do");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Procession procession;

		procession = this.processionService.create();
		result = this.createEditModelAndView(procession);

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId) {
		ModelAndView result;
		Procession procession;

		procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);
		result = this.createEditModelAndView(procession);

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "save")
	public ModelAndView save(@Valid final Procession procession, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(procession);
		else
			try {
				this.processionService.save(procession);
				result = new ModelAndView("redirect::list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "delete")
	public ModelAndView delete(final Procession procession, final BindingResult binding) {
		ModelAndView result;

		try {
			this.processionService.delete(procession);
			result = new ModelAndView("redirect::list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(procession, "procession.commit.error");
		}

		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	private ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView result;

		result = this.createEditModelAndView(procession, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Procession procession, final String string) {
		final ModelAndView result;
		final Brotherhood brotherhood;
		final Collection<AcmeFloat> acmeFloats;

		brotherhood = this.brotherhoodService.findPrincipal();
		acmeFloats = this.acmeFloatService.findAcmeFloats();

		result = new ModelAndView("procession/edit");
		result.addObject("brotherhood", brotherhood);
		result.addObject("acmeFloat", acmeFloats);

		result.addObject("procession", procession);

		return result;
	}

}
