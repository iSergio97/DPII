
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
import domain.EducationData;
import forms.EducationDataForm;
import security.LoginService;
import services.CurriculumService;
import services.EducationDataService;

@Controller
@RequestMapping("/education-data/hacker")
public class EducationDataController {

	// Services ---------------------------------------------------------------

	@Autowired
	EducationDataService	educationDataService;

	@Autowired
	CurriculumService		curriculumService;


	// Constructors -----------------------------------------------------------

	public EducationDataController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		final ModelAndView result;
		EducationData educationData;
		EducationDataForm educationDataForm;

		educationData = this.educationDataService.create();
		educationDataForm = this.educationDataService.createForm(educationData);
		educationDataForm.setCurriculumId(curriculumId);
		result = this.createEditModelAndView(educationDataForm, "create");

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationDataId) {
		ModelAndView result;
		EducationData educationData;
		EducationDataForm educationDataForm;
		final int educationDataOwnerId;
		final int curriculumId;

		educationData = this.educationDataService.findOne(educationDataId);
		educationDataForm = this.educationDataService.createForm(educationData);
		educationDataOwnerId = this.educationDataService.findOwner(educationDataId);
		curriculumId = this.educationDataService.findCurriculum(educationDataId);
		educationDataForm.setCurriculumId(curriculumId);
		Assert.notNull(educationData);
		if (LoginService.getPrincipal().getId() != educationDataOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = this.createEditModelAndView(educationDataForm, "edit");

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("educationData") final EducationDataForm educationDataForm, final BindingResult binding) {
		ModelAndView result;
		EducationData educationData;
		final int curriculumOwnerId;
		Curriculum curriculum;

		if (educationDataForm.getCurriculumId() != 0)
			curriculumOwnerId = this.curriculumService.findOwner(educationDataForm.getCurriculumId());
		else
			curriculumOwnerId = this.educationDataService.findOwner(educationDataForm.getId());
		if (educationDataForm.getId() != 0 && LoginService.getPrincipal().getId() != curriculumOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				educationData = this.educationDataService.reconstruct(educationDataForm, binding);
				curriculum = this.curriculumService.findOne(educationDataForm.getCurriculumId());
				if (LoginService.getPrincipal().getId() != curriculumOwnerId)
					result = new ModelAndView("redirect:/welcome/index.do");
				else {
					// Save Personal Data
					this.educationDataService.save(educationData);

					// Save Personal Data reference in Curriculum
					final Collection<EducationData> curriculumEducationDataList = curriculum.getEducationData();
					if (!curriculumEducationDataList.contains(educationData))
						curriculumEducationDataList.add(educationData);
					curriculum.setEducationData(curriculumEducationDataList);
					this.curriculumService.save(curriculum);

					result = new ModelAndView("redirect:/curriculum/hacker/show.do" + "?curriculumId=" + educationDataForm.getCurriculumId());
				}
			} catch (final ValidationException oops) {
				result = this.createEditModelAndView(educationDataForm, "edit");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationDataForm, "parade.commit.error", "edit");
			}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationDataForm educationDataForm, final BindingResult binding) {
		ModelAndView result;
		EducationData educationData;
		final int educationDataOwnerId;

		educationDataOwnerId = this.educationDataService.findOwner(educationDataForm.getId());
		if (educationDataForm.getId() != 0 && LoginService.getPrincipal().getId() != educationDataOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				educationData = this.educationDataService.reconstruct(educationDataForm, binding);
				if (LoginService.getPrincipal().getId() != educationDataOwnerId)
					result = new ModelAndView("redirect:/welcome/index.do");
				else {
					// Remove educationData from Curriculum
					final Curriculum c = this.curriculumService.findOne(educationDataForm.getCurriculumId());
					final Collection<EducationData> md = c.getEducationData();
					md.remove(educationData);
					c.setEducationData(md);
					this.curriculumService.save(c);
					// Delete educationData
					this.educationDataService.delete(educationData);
					result = new ModelAndView("redirect:/curriculum/hacker/show.do" + "?curriculumId=" + educationDataForm.getCurriculumId());
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationDataForm, "parade.commit.error", "edit");
			}

		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationData educationData, final String method) {
		return this.createEditModelAndView(educationData, null, method);
	}

	protected ModelAndView createEditModelAndView(final EducationData educationData, final String messageCode, final String method) {
		ModelAndView result;

		result = new ModelAndView("education-data/hacker/" + method);

		result.addObject("educationData", educationData);

		return result;

	}

	protected ModelAndView createEditModelAndView(final EducationDataForm educationDataForm, final String method) {
		return this.createEditModelAndView(educationDataForm, null, method);
	}

	protected ModelAndView createEditModelAndView(final EducationDataForm educationDataForm, final String messageCode, final String method) {
		ModelAndView result;

		result = new ModelAndView("education-data/hacker/" + method);

		result.addObject("educationData", educationDataForm);

		return result;

	}

}
