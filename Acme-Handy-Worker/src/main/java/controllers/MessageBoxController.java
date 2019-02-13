
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.MessageBoxService;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Controller
@RequestMapping("/message-box")
public class MessageBoxController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public MessageBoxController() {
		super();
	}

	// List ------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final int id = LoginService.getPrincipal().getId();
			Actor actor;
			actor = this.actorService.findByUserAccountId(id);
			if (actor.getMessageBoxes().size() == 0) {
				final List<MessageBox> messageBoxes = new ArrayList<MessageBox>();
				for (final MessageBox messageBox : this.messageBoxService.createSystemBoxes()) {
					messageBox.setActor(actor);
					messageBoxes.add(this.messageBoxService.save(messageBox));
				}
				actor.setMessageBoxes(messageBoxes);
				actor = this.actorService.save(actor);
			}

			final Collection<MessageBox> messageBoxes = actor.getMessageBoxes();
			final Collection<MessageBox> systemBoxes = this.messageBoxService.getSystemBoxes(actor);
			messageBoxes.removeAll(systemBoxes);

			result = new ModelAndView("message-box/list");
			result.addObject("messageBoxes", messageBoxes);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../j_spring_security_logout");
		}

		return result;
	}

	// Create ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final MessageBox messageBox;

		messageBox = this.messageBoxService.create();
		result = this.createEditModelAndView(messageBox, "message-box/create");

		return result;
	}

	// Edit ------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int id) {
		ModelAndView result;
		MessageBox messageBox;

		messageBox = this.messageBoxService.findById(id);
		Assert.notNull(messageBox);

		result = this.createEditModelAndView(messageBox, "message-box/edit");

		return result;
	}

	// Save ------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid @ModelAttribute("messageBox") final MessageBox messageBox, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageBox, "message-box/edit");
		else
			try {
				this.messageBoxService.save(messageBox);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageBox, "messageBox.commit.error", "message-box/edit");
			}

		return result;
	}

	// Delete ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MessageBox messageBox, final BindingResult binding) {
		ModelAndView result;

		try {
			this.messageBoxService.delete(messageBox);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(messageBox, "messageBox.commit.error", "message-box/edit");
		}

		return result;
	}

	// Show ------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "name") final String name) {
		ModelAndView result;
		Actor actor;
		MessageBox messageBox;

		try {
			actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
			messageBox = this.messageBoxService.findByPrincipalAndName(actor.getId(), name);
			Assert.notNull(messageBox);

			result = this.createEditModelAndView(messageBox, "message-box/show");

			result.addObject("messageCode", null);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../j_spring_security_logout");
		}

		return result;
	}
	// CreateEditModelAndView ------------------------------------------------

	protected ModelAndView createEditModelAndView(final MessageBox messageBox, final String viewName) {
		ModelAndView result;

		result = this.createEditModelAndView(messageBox, null, viewName);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageBox messageBox, final String messageCode, final String viewName) {
		final ModelAndView result;
		final String name;
		final Actor actor;
		final Collection<Message> messages;

		name = messageBox.getName();
		final int principalId = LoginService.getPrincipal().getId();
		actor = this.actorService.findByUserAccountId(principalId);
		messages = messageBox.getMessages();

		// Ligera modificación por motivos de tiles.xml (<h1>)
		result = new ModelAndView(viewName);
		result.addObject("messageBox", messageBox);
		result.addObject("name", name);
		result.addObject("actor", actor);
		result.addObject("messages", messages);

		result.addObject("messageCode", messageCode);

		return result;
	}
}
