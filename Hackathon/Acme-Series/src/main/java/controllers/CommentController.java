
package controllers;

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

import services.CommentService;
import services.SerieService;
import services.UserService;
import domain.Comment;
import domain.Serie;
import forms.CommentForm;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CommentService	commentService;

	@Autowired
	private SerieService	serieService;

	@Autowired
	private UserService		userService;


	// Constructors -----------------------------------------------------------

	public CommentController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int serieId) {
		final ModelAndView result;
		final Comment comment;
		final Serie serie;

		serie = this.serieService.findOne(serieId);

		try {
			Assert.isTrue(!serie.getIsDraft());
			comment = this.commentService.create();
			comment.setSerie(serie);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/panic.do");
		}

		result = this.createEditModelAndView(comment, "user", "create");
		result.addObject("serieId", serie.getId());

		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;
		final int serieId;

		comment = this.commentService.findOne(commentId);

		try {
			Assert.notNull(comment);
			Assert.isTrue(comment.getUser().equals(this.userService.findPrincipal()));
			serieId = comment.getSerie().getId();
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/panic.do");
		}

		result = this.createEditModelAndView(comment, "user", "edit");
		result.addObject("serieId", serieId);

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("comment") final CommentForm commentForm, final BindingResult binding) {
		ModelAndView result;
		final Comment comment;

		if (binding.hasErrors())
			result = this.createEditModelAndView(commentForm, "user", "edit");
		else
			try {
				comment = this.commentService.reconstructForm(commentForm, binding);
				this.commentService.save(comment);
				result = new ModelAndView("redirect:list.do");
			} catch (final ValidationException oops) {
				result = this.createEditModelAndView(commentForm, "user", "edit");
				result.addObject("serieId", commentForm.getSerieId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(commentForm, "comment.commit.error", "user", "edit");
				result.addObject("serieId", commentForm.getSerieId());
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("comment") final CommentForm commentForm, final BindingResult binding) {
		ModelAndView result;
		final Comment comment;

		comment = this.commentService.findOne(commentForm.getId());
		try {
			this.commentService.delete(comment);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(comment, "comment.commit.error", "user", "edit");
		}
		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	private ModelAndView createEditModelAndView(final Comment comment, final String role, final String action) {
		return this.createEditModelAndView(comment, null, role, action);
	}

	private ModelAndView createEditModelAndView(final Comment comment, final String messageCode, final String role, final String action) {
		final ModelAndView result;

		result = this.createModelAndViewWithSystemConfiguration("comment/" + role + "/" + action);
		result.addObject("comment", comment);

		return result;
	}

	private ModelAndView createEditModelAndView(final CommentForm commentForm, final String role, final String action) {
		return this.createEditModelAndView(commentForm, null, role, action);
	}

	private ModelAndView createEditModelAndView(final CommentForm commentForm, final String messageCode, final String role, final String action) {
		final ModelAndView result;

		result = this.createModelAndViewWithSystemConfiguration("comment/" + role + "/" + action);
		result.addObject("comment", commentForm);

		return result;
	}
}
