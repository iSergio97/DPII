
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Curriculum;
import domain.PositionData;
import forms.PositionDataForm;
import security.LoginService;
import services.CurriculumService;
import services.PositionDataService;

@Controller
@RequestMapping("/position-data/rookie")
public class PositionDataController {
	// Services ---------------------------------------------------------------

	@Autowired
	PositionDataService	positionDataService;

	@Autowired
	CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public PositionDataController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		final ModelAndView result;
		PositionData positionData;
		PositionDataForm positionDataForm;

		positionData = this.positionDataService.create();
		positionDataForm = this.positionDataService.createForm(positionData);
		positionDataForm.setCurriculumId(curriculumId);
		result = this.createEditModelAndView(positionDataForm, "create");

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionDataId) {
		ModelAndView result;
		PositionData positionData;
		PositionDataForm positionDataForm;
		final int positionDataOwnerId;
		final int curriculumId;

		positionData = this.positionDataService.findOne(positionDataId);
		positionDataForm = this.positionDataService.createForm(positionData);
		positionDataOwnerId = this.positionDataService.findOwner(positionDataId);
		curriculumId = this.positionDataService.findCurriculum(positionDataId);
		positionDataForm.setCurriculumId(curriculumId);
		Assert.notNull(positionData);
		if (LoginService.getPrincipal().getId() != positionDataOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = this.createEditModelAndView(positionDataForm, "edit");

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("positionData") final PositionDataForm positionDataForm, final BindingResult binding) {
		ModelAndView result;
		PositionData positionData;
		final int curriculumOwnerId;
		Curriculum curriculum;

		if (positionDataForm.getCurriculumId() != 0)
			curriculumOwnerId = this.curriculumService.findOwner(positionDataForm.getCurriculumId());
		else
			curriculumOwnerId = this.positionDataService.findOwner(positionDataForm.getId());
		if (positionDataForm.getId() != 0 && LoginService.getPrincipal().getId() != curriculumOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				positionData = this.positionDataService.reconstruct(positionDataForm, binding);
				curriculum = this.curriculumService.findOne(positionDataForm.getCurriculumId());
				if (LoginService.getPrincipal().getId() != curriculumOwnerId)
					result = new ModelAndView("redirect:/welcome/index.do");
				else {
					// Save Position Data
					this.positionDataService.save(positionData);

					// Save Position Data reference in Curriculum
					final Collection<PositionData> curriculumPositionDataList = curriculum.getPositionData();
					if (!curriculumPositionDataList.contains(positionData))
						curriculumPositionDataList.add(positionData);
					curriculum.setPositionData(curriculumPositionDataList);
					this.curriculumService.save(curriculum);

					result = new ModelAndView("redirect:/curriculum/rookie/show.do" + "?curriculumId=" + positionDataForm.getCurriculumId());
				}
			} catch (final ValidationException oops) {
				result = this.createEditModelAndView(positionDataForm, "edit");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(positionDataForm, "parade.commit.error", "edit");
			}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PositionDataForm positionDataForm, final BindingResult binding) {
		ModelAndView result;
		PositionData positionData;
		final int positionDataOwnerId;

		positionDataOwnerId = this.positionDataService.findOwner(positionDataForm.getId());
		if (positionDataForm.getId() != 0 && LoginService.getPrincipal().getId() != positionDataOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				positionData = this.positionDataService.reconstruct(positionDataForm, binding);
				if (LoginService.getPrincipal().getId() != positionDataOwnerId)
					result = new ModelAndView("redirect:/welcome/index.do");
				else {
					// Remove positionData from Curriculum
					final Curriculum c = this.curriculumService.findOne(positionDataForm.getCurriculumId());
					final Collection<PositionData> pd = c.getPositionData();
					pd.remove(positionData);
					c.setPositionData(pd);
					this.curriculumService.save(c);
					// Delete positionData
					this.positionDataService.delete(positionData);
					result = new ModelAndView("redirect:/curriculum/rookie/show.do" + "?curriculumId=" + positionDataForm.getCurriculumId());
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(positionDataForm, "parade.commit.error", "edit");
			}

		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PositionData positionData, final String method) {
		return this.createEditModelAndView(positionData, null, method);
	}

	protected ModelAndView createEditModelAndView(final PositionData positionData, final String messageCode, final String method) {
		ModelAndView result;

		result = new ModelAndView("position-data/rookie/" + method);

		result.addObject("positionData", positionData);

		return result;

	}

	protected ModelAndView createEditModelAndView(final PositionDataForm positionDataForm, final String method) {
		return this.createEditModelAndView(positionDataForm, null, method);
	}

	protected ModelAndView createEditModelAndView(final PositionDataForm positionDataForm, final String messageCode, final String method) {
		ModelAndView result;

		result = new ModelAndView("position-data/rookie/" + method);

		result.addObject("positionData", positionDataForm);

		return result;

	}
}
