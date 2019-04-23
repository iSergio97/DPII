
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
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;
import security.LoginService;
import services.CurriculumService;
import services.MiscellaneousDataService;

@Controller
@RequestMapping("/miscellaneous-data/hacker")
public class MiscellaneousDataController {

	// Services ---------------------------------------------------------------

	@Autowired
	MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	CurriculumService			curriculumService;


	// Constructors -----------------------------------------------------------

	public MiscellaneousDataController() {
		super();
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int curriculumId) {
		final ModelAndView result;
		Collection<MiscellaneousData> miscellaneousDataList;
		int curriculumOwnerId;

		curriculumOwnerId = this.curriculumService.findOwner(curriculumId);
		if (LoginService.getPrincipal().getId() != curriculumOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			miscellaneousDataList = this.curriculumService.findOne(curriculumId).getMiscellaneousData();

			result = new ModelAndView("miscellaneous-data/hacker/list");
			result.addObject("miscellaneousDataList", miscellaneousDataList);
			result.addObject("requestURI", "miscellaneous-data/hacker/list.do");
		}

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		final ModelAndView result;
		MiscellaneousData miscellaneousData;
		MiscellaneousDataForm miscellaneousDataForm;

		miscellaneousData = this.miscellaneousDataService.create();
		miscellaneousDataForm = this.miscellaneousDataService.createForm(miscellaneousData);
		miscellaneousDataForm.setCurriculumId(curriculumId);
		result = this.createEditModelAndView(miscellaneousDataForm, "create");

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousDataId) {
		ModelAndView result;
		MiscellaneousData miscellaneousData;
		MiscellaneousDataForm miscellaneousDataForm;
		final int miscellaneousDataOwnerId;
		final int curriculumId;

		miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
		miscellaneousDataForm = this.miscellaneousDataService.createForm(miscellaneousData);
		miscellaneousDataOwnerId = this.miscellaneousDataService.findOwner(miscellaneousDataId);
		curriculumId = this.miscellaneousDataService.findCurriculum(miscellaneousDataId);
		miscellaneousDataForm.setCurriculumId(curriculumId);
		Assert.notNull(miscellaneousData);
		if (LoginService.getPrincipal().getId() != miscellaneousDataOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = this.createEditModelAndView(miscellaneousDataForm, "edit");

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("miscellaneousData") final MiscellaneousDataForm miscellaneousDataForm, final BindingResult binding) {
		ModelAndView result;
		MiscellaneousData miscellaneousData;
		final int curriculumOwnerId;
		Curriculum curriculum;

		if (miscellaneousDataForm.getCurriculumId() != 0)
			curriculumOwnerId = this.curriculumService.findOwner(miscellaneousDataForm.getCurriculumId());
		else
			curriculumOwnerId = this.miscellaneousDataService.findOwner(miscellaneousDataForm.getId());
		if (miscellaneousDataForm.getId() != 0 && LoginService.getPrincipal().getId() != curriculumOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				miscellaneousData = this.miscellaneousDataService.reconstruct(miscellaneousDataForm, binding);
				curriculum = this.curriculumService.findOne(miscellaneousDataForm.getCurriculumId());
				if (LoginService.getPrincipal().getId() != curriculumOwnerId)
					result = new ModelAndView("redirect:/welcome/index.do");
				else {
					// Save Misc Data
					this.miscellaneousDataService.save(miscellaneousData);

					// Save Misc Data reference in Curriculum
					final Collection<MiscellaneousData> curriculumMiscellaneousDataList = curriculum.getMiscellaneousData();
					if (!curriculumMiscellaneousDataList.contains(miscellaneousData))
						curriculumMiscellaneousDataList.add(miscellaneousData);
					curriculum.setMiscellaneousData(curriculumMiscellaneousDataList);
					this.curriculumService.save(curriculum);

					result = new ModelAndView("redirect:list.do" + "?curriculumId=" + miscellaneousDataForm.getCurriculumId());
				}
			} catch (final ValidationException oops) {
				result = this.createEditModelAndView(miscellaneousDataForm, "edit");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousDataForm, "parade.commit.error", "edit");
			}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousDataForm miscellaneousDataForm, final BindingResult binding) {
		ModelAndView result;
		MiscellaneousData miscellaneousData;
		final int miscellaneousDataOwnerId;

		miscellaneousDataOwnerId = this.miscellaneousDataService.findOwner(miscellaneousDataForm.getId());
		if (miscellaneousDataForm.getId() != 0 && LoginService.getPrincipal().getId() != miscellaneousDataOwnerId)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				miscellaneousData = this.miscellaneousDataService.reconstruct(miscellaneousDataForm, binding);
				if (LoginService.getPrincipal().getId() != miscellaneousDataOwnerId)
					result = new ModelAndView("redirect:/welcome/index.do");
				else {
					// Remove miscellaneousData from Curriculum
					final Curriculum c = this.curriculumService.findOne(miscellaneousDataForm.getCurriculumId());
					final Collection<MiscellaneousData> md = c.getMiscellaneousData();
					md.remove(miscellaneousData);
					c.setMiscellaneousData(md);
					this.curriculumService.save(c);
					// Delete miscellaneousData
					this.miscellaneousDataService.delete(miscellaneousData);
					result = new ModelAndView("redirect:/curriculum/hacker/list.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousDataForm, "parade.commit.error", "edit");
			}

		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousData miscellaneousData, final String method) {
		return this.createEditModelAndView(miscellaneousData, null, method);
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousData miscellaneousData, final String messageCode, final String method) {
		ModelAndView result;

		result = new ModelAndView("miscellaneous-data/hacker/" + method);

		result.addObject("miscellaneousData", miscellaneousData);

		return result;

	}

	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm miscellaneousDataForm, final String method) {
		return this.createEditModelAndView(miscellaneousDataForm, null, method);
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm miscellaneousDataForm, final String messageCode, final String method) {
		ModelAndView result;

		result = new ModelAndView("miscellaneous-data/hacker/" + method);

		result.addObject("miscellaneousData", miscellaneousDataForm);

		return result;

	}

}
