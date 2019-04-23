
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Curriculum;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;
import forms.PersonalDataForm;
import security.LoginService;
import services.CurriculumService;
import services.HackerService;
import services.PersonalDataService;

@Controller
@RequestMapping("/curriculum/hacker")
public class CurriculumController {

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private PersonalDataService	personalDataService;


	public CurriculumController() {
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		//TODO: Preguntar si un hacker puede tener m�s de un curr�culum
		//TODO: Si puede, corregir m�todo y listar los curr�culums con un bot�n para ver
		//TODO: la informaci�n de cada curr�culum en profundidad
		final ModelAndView result;
		final Hacker hacker = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Curriculum> cr = this.curriculumService.findCurriculumsByHacker(hacker);

		result = new ModelAndView("curriculum/hacker/list");
		result.addObject("curriculums", cr);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculumId) {
		ModelAndView result;
		final Curriculum cr = this.curriculumService.findOne(curriculumId);
		final Hacker hacker = cr.getHacker();
		final Hacker principal = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (hacker != principal)
			result = new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("curriculum/hacker/show");

		final PersonalData pData = cr.getPersonalData();
		final Collection<MiscellaneousData> mDatas = cr.getMiscellaneousData();
		final Collection<EducationData> eDatas = cr.getEducationData();
		final Collection<PositionData> pDatas = cr.getPositionData();

		result.addObject("crName", cr.getName());
		result.addObject("personalData", pData);
		result.addObject("misData", mDatas);
		result.addObject("eDatas", eDatas);
		result.addObject("personalDatas", pDatas);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalDataForm pdForm) {
		return this.createEditModelAndView(pdForm, null);
	}

	protected ModelAndView createEditModelAndView(final PersonalDataForm pdForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("curriculum/hacker/create");

		result.addObject("personalData", pdForm);

		return result;

	}

}