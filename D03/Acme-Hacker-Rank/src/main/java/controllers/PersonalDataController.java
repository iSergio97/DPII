
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView save(@ModelAttribute("personalData") final PersonalDataForm personalData, final BindingResult bindingResult) {
		ModelAndView result;
		final PersonalData pData;
		final Hacker hacker = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		Curriculum cr;
		if (personalData.getId() == 0)
			cr = this.curriculumService.create();
		else
			cr = this.curriculumService.findCurriculumByPDId(personalData.getId());
		if (personalData.getCurriculumName().isEmpty()) {
			final ObjectError error = new ObjectError("curriculumName", "This curriculum name must not be empty!");
			bindingResult.addError(error);
			bindingResult.rejectValue("curriculumName", "error.curriculum.name");
		}
		try {
			pData = this.personalDataService.reconstructForm(personalData, bindingResult);
			if (!bindingResult.hasErrors()) {
				final PersonalData data = this.personalDataService.save(pData);
				cr.setPersonalData(data);
				cr.setHacker(hacker);
				cr.setPersonalData(data);
				this.curriculumService.saveAllCr(cr);
				cr.setName(personalData.getCurriculumName());
				this.curriculumService.save(cr);
				result = new ModelAndView("redirect:/welcome/index.do");
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException valExp) {
			result = this.createEditModelAndView(personalData);
		} catch (final Throwable ops) {
			result = this.createEditModelAndView(personalData, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataID) {
		ModelAndView result;
		final Hacker hacker = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Curriculum> cl = this.curriculumService.findCurriculumsByHacker(hacker);
		final List<Integer> ls = new ArrayList<>();
		for (final Curriculum c : cl)
			ls.add(c.getPersonalData().getId());

		if (!ls.contains(personalDataID))
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			final PersonalData pd = this.personalDataService.findOne(personalDataID);
			final PersonalDataForm pdForm = this.personalDataService.deconstruct(pd);
			result = new ModelAndView("personal-data/hacker/edit");
			result.addObject("personalData", pdForm);
		}
		return result;
	}

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