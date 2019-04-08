
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		//TODO: Preguntar si un hacker puede tener más de una curricula
		//TODO: Si puede, corregir método y listar los curricula con un botón para ver
		//TODO: la información de cada curricula en profundidad
		final ModelAndView result;
		final Hacker hacker = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Curriculum cr = this.curriculumService.findCurriculumByHacker(hacker);
		final PersonalData pData = cr.getPersonalData();
		final Collection<MiscellaneousData> mDatas = cr.getMiscellaneousData();
		final Collection<EducationData> eDatas = cr.getEducationData();
		final Collection<PositionData> pDatas = cr.getPositionData();

		result = new ModelAndView("curricula/hacker/show");
		result.addObject("personalData", pData);
		result.addObject("mDatas", mDatas);
		result.addObject("eDatas", eDatas);
		result.addObject("pDatas", pDatas);

		return result;
	}

}
