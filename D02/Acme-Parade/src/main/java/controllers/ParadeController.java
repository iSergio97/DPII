
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
import services.ParadeService;
import domain.AcmeFloat;
import domain.Brotherhood;
<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
import domain.Procession;
import forms.ProcessionForm;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {
=======
import domain.Parade;

@Controller
@RequestMapping("/parade/brotherhood")
public class ParadeController extends AbstractController {
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java

	// Services ---------------------------------------------------------------

	@Autowired
	private AcmeFloatService	acmeFloatService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ParadeService		paradeService;


	// Constructors -----------------------------------------------------------

	public ParadeController() {

	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Parade> parades;

		parades = this.paradeService.findAllByBrotherhoodAccountId(LoginService.getPrincipal().getId());

		result = new ModelAndView("parade/brotherhood/list");
		result.addObject("parades", parades);
		result.addObject("requestURI", "parade/brotherhood/list.do");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Parade parade;

<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
		procession = this.processionService.create();
		procession.setIsDraft(true);
		result = this.createEditModelAndView(procession, "create");
=======
		parade = this.paradeService.create();
		result = this.createEditModelAndView(parade, "create");
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java

		return result;
	}

	// Edit -------------------------------------------------------------------

<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId) {
=======
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int paradeId) {
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java
		ModelAndView result;
		Parade parade;

<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
		procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);
		procession.setAcmeFloats(new ArrayList<AcmeFloat>());
		result = this.createEditModelAndView(procession, "edit");
=======
		parade = this.paradeService.findOne(paradeId);
		Assert.notNull(parade);
		result = this.createEditModelAndView(parade, "edit");
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java

		return result;
	}

	// Save -------------------------------------------------------------------

<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ProcessionForm processionForm, final BindingResult binding) {
=======
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Parade parade, final BindingResult binding) {
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java
		ModelAndView result;
		Procession procession;

		procession = this.processionService.reconstruct(processionForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(parade, "edit");
		else
			try {
<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
				procession.setIsDraft(true);
				this.processionService.save(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(procession, "procession.commit.error", "edit");
=======
				this.paradeService.save(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(parade, "parade.commit.error");
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java
			}

		return result;
	}

	// Delete -----------------------------------------------------------------

<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Procession procession, final BindingResult binding) {
=======
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Parade parade, final BindingResult binding) {
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java
		ModelAndView result;

		try {
			this.paradeService.delete(parade);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
			result = this.createEditModelAndView(procession, "procession.commit.error", "edit");
=======
			result = this.createEditModelAndView(parade, "parade.commit.error");
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java
		}

		return result;
	}

	// Save in Final Mode -----------------------------------------------------

<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "finalMode")
	public ModelAndView finalMode(@Valid final Procession procession, final BindingResult binding) {
=======
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "finalMode")
	public ModelAndView finalMode(@Valid final Parade parade, final BindingResult binding) {
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(parade, "edit");
		else
			try {
				parade.setIsDraft(false);
				this.paradeService.save(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
				result = this.createEditModelAndView(procession, "procession.commit.error", "edit");
=======
				result = this.createEditModelAndView(parade, "parade.commit.error");
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java
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

<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
	protected ModelAndView createEditModelAndView(final Procession procession, final String method) {
=======
	private ModelAndView createEditModelAndView(final Parade parade, final String method) {
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java
		ModelAndView result;

		result = this.createEditModelAndView(parade, null, method);

		return result;
	}

<<<<<<< HEAD:D02/Acme-Parade/src/main/java/controllers/ProcessionController.java
	protected ModelAndView createEditModelAndView(final Procession procession, final String messageCode, final String method) {
=======
	private ModelAndView createEditModelAndView(final Parade parade, final String string, final String method) {
>>>>>>> domain-classes:D02/Acme-Parade/src/main/java/controllers/ParadeController.java
		final ModelAndView result;
		final Brotherhood brotherhood;
		final Collection<AcmeFloat> acmeFloats;
		final UserAccount userAccount = LoginService.getPrincipal();

		brotherhood = this.brotherhoodService.findPrincipal();
		acmeFloats = this.acmeFloatService.findAcmeFloats(userAccount.getId());

		result = new ModelAndView("parade/brotherhood/" + method);
		result.addObject("brotherhood", brotherhood);
		result.addObject("acmeFloats", acmeFloats);

		result.addObject("parade", parade);

		result.addObject("messageCode", messageCode);

		return result;
	}

}
