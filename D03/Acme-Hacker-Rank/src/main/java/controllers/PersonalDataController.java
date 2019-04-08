
package controllers;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Curriculum;
import domain.Hacker;
import domain.PersonalData;
import forms.PersonalDataForm;
import security.LoginService;
import services.CurriculumService;
import services.HackerService;
import services.PersonalDataService;

@Controller
@RequestMapping("/personal-data/hacker")
public class PersonalDataController {

	@Autowired
	private PersonalDataService	personalDataService;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private CurriculumService	curriculumService;


	public PersonalDataController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		PersonalDataForm pdForm;
		pdForm = this.personalDataService.createForm();

		result = this.createEditModelAndView(pdForm);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(final PersonalDataForm pdForm, final BindingResult bindingResult) {
		ModelAndView result;
		final PersonalData pData;
		final Hacker hacker = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		Curriculum cr = this.curriculumService.findCurriculumByHacker(hacker);
		if (cr == null)
			cr = this.curriculumService.create();
		//TODO: Comprobar más adelante si la validación de campos se produce bien
		//TODO: Revisar porqué no aparecen los errores
		try {
			pData = this.personalDataService.reconstructForm(pdForm, bindingResult);
			if (!bindingResult.hasErrors()) {
				final PersonalData data = this.personalDataService.save(pData);
				cr.setPersonalData(data);
				this.curriculumService.save(cr);
				//cr.setHacker(hacker);
				//Añadire redirect a la vista de curriculum
				result = new ModelAndView("redirect:/welcome/index.do");
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException valExp) {
			result = this.createEditModelAndView(pdForm);
		} catch (final Throwable ops) {
			result = this.createEditModelAndView(pdForm, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		final Hacker hacker = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Curriculum cr = this.curriculumService.findCurriculumByHacker(hacker);
		final PersonalData pData = cr.getPersonalData();

		result = new ModelAndView("personal-data/hacker/show");

		result.addObject("personalData", pData);

		return result;

	}

	//TODO: Añadir método para editar

	//TODO: Añadir método para eliminar

	protected ModelAndView createEditModelAndView(final PersonalDataForm pdForm) {
		return this.createEditModelAndView(pdForm, null);
	}

	protected ModelAndView createEditModelAndView(final PersonalDataForm pdForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("personal-data/hacker/create");

		result.addObject("personalData", pdForm);

		return result;

	}

}
