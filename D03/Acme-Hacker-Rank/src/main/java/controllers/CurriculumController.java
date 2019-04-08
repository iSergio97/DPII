
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
import security.LoginService;
import services.CurriculumService;
import services.HackerService;

@Controller
@RequestMapping("/curricula/hacker")
public class CurriculumController {

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private HackerService		hackerService;


	public CurriculumController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		//TODO: Preguntar si un hacker puede tener más de una curricula
		//TODO: Si puede, corregir método y listar los curricula con un botón para ver
		//TODO: la información de cada curricula en profundidad
		final ModelAndView result;
		final Hacker hacker = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Curriculum> cr = this.curriculumService.findCurriculumsByHacker(hacker);

		result = new ModelAndView("curricula/hacker/list");
		result.addObject("curriculums", cr);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id") final int id) {
		ModelAndView result;
		final Curriculum cr = this.curriculumService.findOne(id);
		final Hacker hacker = cr.getHacker();
		final Hacker principal = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (hacker != principal)
			result = new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("/curricula/hacker/show");

		final PersonalData pData = cr.getPersonalData();
		final Collection<MiscellaneousData> mDatas = cr.getMiscellaneousData();
		final Collection<EducationData> eDatas = cr.getEducationData();
		final Collection<PositionData> pDatas = cr.getPositionData();

		result.addObject("personalData", pData);
		result.addObject("missData", mDatas);
		result.addObject("eDatas", eDatas);
		result.addObject("personalDatas", pDatas);

		return result;
	}

}
