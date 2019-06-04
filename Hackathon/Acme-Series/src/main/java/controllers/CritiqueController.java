//
//package controllers;
//
//import java.util.List;
//
//import javax.validation.ValidationException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.Assert;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import domain.Critique;
//import domain.Serie;
//import forms.CritiqueForm;
//import security.LoginService;
//import services.CriticService;
//import services.CritiqueService;
//import services.SerieService;
//
//@Controller
//@RequestMapping("/critique")
//public class CritiqueController {
//
//	// Services ---------------------------------------------------------------
//
//	@Autowired
//	private CritiqueService	critiqueService;
//
//	@Autowired
//	private SerieService	serieService;
//
//	@Autowired
//	private CriticService	criticService;
//
//
//	// Constructors -----------------------------------------------------------
//
//	public CritiqueController() {
//		super();
//	}
//
//	// List (Publisher) -------------------------------------------------------
//
//	@RequestMapping(value = "/critic/list", method = RequestMethod.GET)
//	public ModelAndView publisherList() {
//		final ModelAndView result;
//		final List<Critique> critiques;
//
//		final int userAccountId = LoginService.getPrincipal().getId();
//		critiques = this.critiqueService.findAllByUserAccountId(userAccountId);
//
//		result = new ModelAndView("critique/critic/list");
//		result.addObject("critiques", critiques);
//		result.addObject("requestURI", "critique/critic/list.do");
//
//		return result;
//	}
//
//	//	// List (Serie) ---------------------------------------------------
//	//
//	//	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
//	//	public ModelAndView administratorList() {
//	//		final ModelAndView result;
//	//		final List<Application> applications;
//	//
//	//		applications = this.applicationService.findAll();
//	//
//	//		result = new ModelAndView("application/administrator/list");
//	//		result.addObject("applications", applications);
//	//		result.addObject("role", "administrator");
//	//		result.addObject("requestURI", "application/administrator/list.do");
//	//
//	//		return result;
//	//	}
//
//	// Show -------------------------------------------------------------------
//
//	@RequestMapping(value = "/public/show", method = RequestMethod.GET)
//	public ModelAndView show(@RequestParam final int critiqueId) {
//		ModelAndView result;
//		Critique critique;
//
//		try {
//			critique = this.critiqueService.findOne(critiqueId);
//			Assert.notNull(critique);
//			Assert.isTrue(critique.getCritic().equals(this.criticService.findPrincipal()));
//			result = this.createEditModelAndView(critique, "public", "show");
//		} catch (final Throwable oops) {
//			return new ModelAndView("redirect:/panic.do");
//		}
//
//		return result;
//	}
//
//	// Create -----------------------------------------------------------------
//
//	@RequestMapping(value = "/critic/create", method = RequestMethod.GET)
//	public ModelAndView create(@RequestParam final int serieId) {
//		final ModelAndView result;
//		final Critique critique;
//		final Serie serie;
//
//		serie = this.serieService.findOne(serieId);
//
//		try {
//			Assert.isTrue(!serie.getIsDraft());
//			critique = this.critiqueService.create();
//			critique.setSerie(serie);
//		} catch (final Throwable oops) {
//			return new ModelAndView("redirect:/panic.do");
//		}
//
//		result = this.createEditModelAndView(critique, "critic", "create");
//		result.addObject("serieId", serie.getId());
//
//		return result;
//	}
//
//	// Edit -------------------------------------------------------------------
//
//	@RequestMapping(value = "/critic/edit", method = RequestMethod.GET)
//	public ModelAndView edit(@RequestParam final int critiqueId) {
//		ModelAndView result;
//		Critique critique;
//		final int serieId;
//
//		critique = this.critiqueService.findOne(critiqueId);
//
//		try {
//			Assert.notNull(critique);
//			Assert.isTrue(critique.getCritic().equals(this.criticService.findPrincipal()));
//			serieId = critique.getSerie().getId();
//		} catch (final Throwable oops) {
//			return new ModelAndView("redirect:/panic.do");
//		}
//
//		result = this.createEditModelAndView(critique, "critic", "edit");
//		result.addObject("serieId", serieId);
//
//		return result;
//	}
//
//	// Save -------------------------------------------------------------------
//
//	@RequestMapping(value = "/critic/edit", method = RequestMethod.POST, params = "save")
//	public ModelAndView save(@ModelAttribute("critique") final CritiqueForm critiqueForm, final BindingResult binding) {
//		ModelAndView result;
//		final Critique critique;
//
//		if (binding.hasErrors())
//			result = this.createEditModelAndView(critiqueForm, "critic", "edit");
//		else
//			try {
//				critique = this.critiqueService.reconstructForm(critiqueForm, binding);
//				this.critiqueService.save(critique);
//				result = new ModelAndView("redirect:list.do");
//			} catch (final ValidationException oops) {
//				result = this.createEditModelAndView(critiqueForm, "critic", "edit");
//				result.addObject("serieId", critiqueForm.getSerieId());
//			} catch (final Throwable oops) {
//				result = this.createEditModelAndView(critiqueForm, "critique.commit.error", "critic", "edit");
//				result.addObject("serieId", critiqueForm.getSerieId());
//			}
//		return result;
//	}
//
//	// Delete -----------------------------------------------------------------
//
//	@RequestMapping(value = "/critic/edit", method = RequestMethod.POST, params = "delete")
//	public ModelAndView delete(@ModelAttribute("critique") final CritiqueForm critiqueForm, final BindingResult binding) {
//		ModelAndView result;
//		final Critique critique;
//
//		critique = this.critiqueService.findOne(critiqueForm.getId());
//		try {
//			this.critiqueService.delete(critique);
//			result = new ModelAndView("redirect:list.do");
//		} catch (final Throwable oops) {
//			result = this.createEditModelAndView(critique, "critique.commit.error", "critic", "edit");
//		}
//		return result;
//	}
//
//	// Ancillary Methods ------------------------------------------------------
//
//	private ModelAndView createEditModelAndView(final Critique critique, final String role, final String action) {
//		return this.createEditModelAndView(critique, null, role, action);
//	}
//
//	private ModelAndView createEditModelAndView(final Critique critique, final String messageCode, final String role, final String action) {
//		final ModelAndView result;
//
//		result = new ModelAndView("critique/" + role + "/" + action);
//		result.addObject("critique", critique);
//
//		return result;
//	}
//
//	private ModelAndView createEditModelAndView(final CritiqueForm critiqueForm, final String role, final String action) {
//		return this.createEditModelAndView(critiqueForm, null, role, action);
//	}
//
//	private ModelAndView createEditModelAndView(final CritiqueForm critiqueForm, final String messageCode, final String role, final String action) {
//		final ModelAndView result;
//
//		result = new ModelAndView("critique/" + role + "/" + action);
//		result.addObject("critique", critiqueForm);
//
//		return result;
//	}
//}
