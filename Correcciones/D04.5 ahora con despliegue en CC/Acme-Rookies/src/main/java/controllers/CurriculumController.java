
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculumService;
import services.PersonalDataService;
import services.RookieService;
import domain.Curriculum;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;
import domain.Rookie;
import forms.PersonalDataForm;

@Controller
@RequestMapping("/curriculum/rookie")
public class CurriculumController extends AbstractController {

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private PersonalDataService	personalDataService;


	public CurriculumController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		PersonalDataForm personalDataForm;
		personalDataForm = this.personalDataService.createForm();

		result = this.createEditModelAndView(personalDataForm);

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Rookie rookie = this.rookieService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Curriculum> curricula = this.curriculumService.findCurriculaByRookie(rookie);

		result = this.createModelAndViewWithSystemConfiguration("curriculum/rookie/list");
		result.addObject("curricula", curricula);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculumId) {
		ModelAndView result;
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		final Rookie rookie = curriculum.getRookie();
		final Rookie principal = this.rookieService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (rookie != principal)
			result = this.createModelAndViewWithSystemConfiguration("redirect:/welcome/index.do");
		else {
			result = this.createModelAndViewWithSystemConfiguration("curriculum/rookie/show");

			final PersonalData personalData = curriculum.getPersonalData();
			final Collection<MiscellaneousData> miscellaneousDataList = curriculum.getMiscellaneousData();
			final Collection<EducationData> educationDataList = curriculum.getEducationData();
			final Collection<PositionData> positionDataList = curriculum.getPositionData();

			result.addObject("curriculumName", curriculum.getName());
			result.addObject("curriculumId", curriculumId);
			result.addObject("personalData", personalData);
			result.addObject("positionDataList", positionDataList);
			result.addObject("educationDataList", educationDataList);
			result.addObject("miscellaneousDataList", miscellaneousDataList);
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalDataForm personalDataForm) {
		return this.createEditModelAndView(personalDataForm, null);
	}

	protected ModelAndView createEditModelAndView(final PersonalDataForm personalDataForm, final String message) {
		ModelAndView result;

		result = this.createModelAndViewWithSystemConfiguration("curriculum/rookie/create");

		result.addObject("personalData", personalDataForm);

		return result;

	}

}
