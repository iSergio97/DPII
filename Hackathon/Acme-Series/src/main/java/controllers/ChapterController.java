
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChapterService;
import services.SeasonService;
import domain.Chapter;
import domain.Season;
import forms.ChapterForm;

@Controller
@RequestMapping("/chapter")
public class ChapterController extends AbstractController {

	@Autowired
	private ChapterService	chapterService;

	@Autowired
	private SeasonService	seasonService;


	public ChapterController() {

	}

	/////////////////////////////////////////////////////////////////
	//List

	@RequestMapping(value = "/publisher/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int seasonId) {
		ModelAndView result;
		final Season season = this.seasonService.findOne(seasonId);
		Collection<Chapter> chapters;

		chapters = season.getChapters();
		result = new ModelAndView("chapter/publisher/list");
		result.addObject("chapters", chapters);
		result.addObject("seasonId", seasonId);

		return result;
	}

	//////////////////////////////////////////////////////////////
	//Show

	@RequestMapping(value = "/publisher/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int chapterId) {
		ModelAndView result;
		Chapter chapter;

		chapter = this.chapterService.findOne(chapterId);
		result = new ModelAndView("chapter/publisher/show");
		result.addObject("chapter", chapter);

		return result;
	}

	/////////////////////////////////////////////////////////////
	//Create

	@RequestMapping(value = "/publisher/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int seasonId) {
		final ModelAndView result;
		final Chapter chapter;
		final ChapterForm form;

		chapter = this.chapterService.create();
		form = this.chapterService.createForm(chapter);
		form.setSeasonId(seasonId);

		result = this.createAndEditModelAndView(form);
		return result;
	}

	///////////////////////////////////////////////////////////
	//Edit

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int chapterId) {
		ModelAndView result;
		Chapter chapter;
		ChapterForm form;
		final Season s = this.seasonService.findSeasonAssociated(chapterId);

		chapter = this.chapterService.findOne(chapterId);
		s.getChapters().remove(chapter);
		form = this.chapterService.createForm(chapter);
		form.setSeasonId(s.getId());

		result = new ModelAndView("chapter/publisher/edit");
		result.addObject("chapter", form);

		return result;
	}

	////////////////////////////////////////////////////////
	//Save

	@RequestMapping(value = "/publisher/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("chapter") final ChapterForm chapter, final BindingResult binding) {
		ModelAndView result;
		Chapter chapter2;
		chapter2 = this.chapterService.reconstruct(chapter, binding);
		final Season s = this.seasonService.findOne(chapter.getSeasonId());

		if (binding.hasErrors())
			result = this.createAndEditModelAndView(chapter);
		else
			try {
				final Chapter c = this.chapterService.save(chapter2);
				final Collection<Chapter> chapters = s.getChapters();
				chapters.add(c);
				s.setChapters(chapters);
				this.seasonService.save(s);
				result = this.list(s.getId());
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(chapter, "chapter.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/publisher/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("chapter") final ChapterForm chapterForm, final BindingResult binding) {
		ModelAndView result;
		Chapter chapter;

		chapter = this.chapterService.reconstruct(chapterForm, binding);
		if (binding.hasErrors())
			result = this.createAndEditModelAndView(chapterForm);
		else
			try {
				final Season s = this.seasonService.findSeasonAssociated(chapter.getId());
				s.getChapters().remove(chapter);
				this.seasonService.save(s);
				this.chapterService.delete(chapter);
				result = this.list(s.getId());

			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(chapterForm, "season.commit.error");
			}
		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final ChapterForm chapter) {
		ModelAndView result;

		result = this.createAndEditModelAndView(chapter, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final ChapterForm chapter, final String message) {
		final ModelAndView result;

		result = new ModelAndView("chapter/publisher/create");
		result.addObject("chapter", chapter);
		result.addObject("message", message);

		return result;
	}

}
