/*
 * ApplicationController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.MessageBoxService;
import services.MessageService;
import services.SystemConfigurationService;
import domain.Actor;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Message;
import domain.MessageBox;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService				actorService;
	@Autowired
	private ApplicationService			applicationService;
	@Autowired
	private CustomerService				customerService;
	@Autowired
	private FixUpTaskService			fixUpTaskService;
	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private MessageService				messageService;
	@Autowired
	private MessageBoxService			messageBoxService;
	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public ApplicationController() {
		super();
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final Application application;
		final Actor actor;

		final Double vat = this.systemConfigurationService.getSystemConfiguration().getVAT();

		application = this.applicationService.findById(id);
		actor = this.actorService.findPrincipal();

		// Check principal is either the customer or the handy worker of this application
		Assert.isTrue(application.getHandyWorker().getId() == actor.getId() || application.getFixUpTask().getCustomer().getId() == actor.getId());

		result = new ModelAndView("application/display");
		result.addObject("application", application);
		result.addObject("vat", vat);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.POST, params = "addcomment")
	public ModelAndView addComment(@RequestParam(value = "id") final int id, @RequestParam(value = "text") final String text) {
		final ModelAndView result;
		Application application;
		final Actor actor;

		application = this.applicationService.findById(id);
		actor = this.actorService.findPrincipal();

		// Check principal is either the customer or the handy worker of this application
		Assert.isTrue(application.getHandyWorker().getId() == actor.getId() || application.getFixUpTask().getCustomer().getId() == actor.getId());

		final List<String> comments = application.getComments();
		comments.add(text);
		application.setComments(comments);
		application = this.applicationService.save(application);

		result = new ModelAndView("application/display");
		result.addObject("application", application);

		return result;
	}

	// List customer applications ---------------------------------------------

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ModelAndView customer() {
		final ModelAndView result;
		final Collection<Application> applications;
		final Customer customer;

		customer = this.customerService.findPrincipal();
		applications = new ArrayList<Application>();
		for (final FixUpTask f : customer.getFixUpTasks())
			applications.addAll(f.getApplications());
		result = new ModelAndView("application/customer");
		final Date expireTime = new Date();
		expireTime.setDate(expireTime.getDay() + 2);

		result.addObject("applications", applications);
		result.addObject("currentDate", new Date());
		result.addObject("expireTime", expireTime);

		return result;
	}

	@RequestMapping(value = "/customer", method = RequestMethod.POST, params = "accept")
	public ModelAndView customerAccept(@RequestParam(value = "id") final int id) {
		final ModelAndView result;

		Application application;
		Customer customer;

		application = this.applicationService.findById(id);
		customer = this.customerService.findPrincipal();

		if (customer.getCreditCard() == null) {
			result = new ModelAndView("redirect:add-credit-card.do");
			result.addObject(application);
		} else {
			Assert.isTrue(application.getFixUpTask().getCustomer().equals(customer));
			Assert.isTrue(customer.getCreditCard() != null);
			Assert.isTrue(application.getStatus().equals("PENDING"));
			Assert.isTrue(application.getFixUpTask().getTimeLimit().before(new Date()));

			application.setStatus("ACCEPTED");
			this.applicationService.save(application);

			final List<Application> ap = (List<Application>) application.getFixUpTask().getApplications();
			ap.remove(application);
			for (final Application a : ap) {
				a.setStatus("REJECTED");
				this.applicationService.save(a);

				Message message = this.messageService.create();
				message.setPriority("HIGH");
				message.setSubject("Application rejected");
				message.setBody("Application rejected");
				message.setSender(customer);
				final List<Actor> recipients = new ArrayList<>();
				recipients.add(a.getHandyWorker());
				message.setRecipients(recipients);
				final List<MessageBox> messageBoxes = new ArrayList<>();
				messageBoxes.add(this.messageBoxService.findByPrincipalAndName(customer.getId(), "OutBox"));
				messageBoxes.add(this.messageBoxService.findByPrincipalAndName(a.getHandyWorker().getId(), "InBox"));
				message.setMessageBoxes(messageBoxes);
				message = this.messageService.save(message);
				for (final MessageBox messageBox : messageBoxes) {
					final Collection<Message> messages = messageBox.getMessages();
					messages.add(message);
					messageBox.setMessages(messages);
					this.messageBoxService.save(messageBox);
				}
			}
			ap.add(application);

			Message message = this.messageService.create();
			message.setPriority("HIGH");
			message.setSubject("Application accepted");
			message.setBody("Application accepted");
			message.setSender(customer);
			final List<Actor> recipients = new ArrayList<>();
			recipients.add(application.getHandyWorker());
			message.setRecipients(recipients);
			final List<MessageBox> messageBoxes = new ArrayList<>();
			messageBoxes.add(this.messageBoxService.findByPrincipalAndName(customer.getId(), "OutBox"));
			messageBoxes.add(this.messageBoxService.findByPrincipalAndName(application.getHandyWorker().getId(), "InBox"));
			message.setMessageBoxes(messageBoxes);
			message = this.messageService.save(message);
			for (final MessageBox messageBox : messageBoxes) {
				final Collection<Message> messages = messageBox.getMessages();
				messages.add(message);
				messageBox.setMessages(messages);
				this.messageBoxService.save(messageBox);
			}

			result = this.customer();

		}

		return result;
	}

	@RequestMapping(value = "/customer", method = RequestMethod.POST, params = "reject")
	public ModelAndView customerReject(@RequestParam(value = "id") final int id) {
		final Application application;
		final Customer customer;

		application = this.applicationService.findById(id);
		customer = this.customerService.findPrincipal();

		Assert.isTrue(application.getFixUpTask().getCustomer().equals(customer));
		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isTrue(application.getFixUpTask().getTimeLimit().before(new Date()));

		application.setStatus("REJECTED");
		this.applicationService.save(application);

		Message message = this.messageService.create();
		message.setPriority("HIGH");
		message.setSubject("Application rejected");
		message.setBody("Application rejected");
		message.setSender(customer);
		final List<Actor> recipients = new ArrayList<>();
		recipients.add(application.getHandyWorker());
		message.setRecipients(recipients);
		final List<MessageBox> messageBoxes = new ArrayList<>();
		messageBoxes.add(this.messageBoxService.findByPrincipalAndName(customer.getId(), "OutBox"));
		messageBoxes.add(this.messageBoxService.findByPrincipalAndName(application.getHandyWorker().getId(), "InBox"));
		message.setMessageBoxes(messageBoxes);
		message = this.messageService.save(message);
		for (final MessageBox messageBox : messageBoxes) {
			final Collection<Message> messages = messageBox.getMessages();
			messages.add(message);
			messageBox.setMessages(messages);
			this.messageBoxService.save(messageBox);
		}

		return this.customer();
	}

	// List handy worker applications -----------------------------------------

	@RequestMapping(value = "/handyworker", method = RequestMethod.GET)
	public ModelAndView handyWorker() {
		final ModelAndView result;
		final Collection<Application> applications;
		final HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findById(this.actorService.findPrincipal().getId());
		applications = handyWorker.getApplications();

		result = new ModelAndView("application/handyworker");
		result.addObject("applications", applications);
		result.addObject("currentDate", new Date());

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "fixuptaskid") final int fixUpTaskId) {
		final ModelAndView result;

		result = new ModelAndView("application/create");
		result.addObject("fixuptaskid", fixUpTaskId);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@RequestParam(value = "fixuptaskid") final int fixUpTaskId, @RequestParam(value = "offeredprice") final int offeredPrice, @RequestParam(value = "comment") final String comment) {
		final ModelAndView result;
		final Application application;
		final HandyWorker handyWorker;
		final FixUpTask fixUpTask;

		handyWorker = this.handyWorkerService.findById(this.actorService.findPrincipal().getId());
		fixUpTask = this.fixUpTaskService.findById(fixUpTaskId);
		application = this.applicationService.create();
		// Set fix-up task
		application.setFixUpTask(fixUpTask);
		final Collection<Application> fixUpTaskApplications = fixUpTask.getApplications();
		fixUpTaskApplications.add(application);
		fixUpTask.setApplications(fixUpTaskApplications);
		this.fixUpTaskService.save(fixUpTask);
		// Set handy worker
		application.setHandyWorker(handyWorker);
		final Collection<Application> handyWorkerApplications = handyWorker.getApplications();
		handyWorkerApplications.add(application);
		handyWorker.setApplications(handyWorkerApplications);
		this.handyWorkerService.save(handyWorker);
		//
		application.setStatus("PENDING");
		final ArrayList<String> comments = new ArrayList<>();
		comments.add(comment);
		application.setComments(comments);
		this.applicationService.save(application);

		result = new ModelAndView("application/create");
		result.addObject("fixuptaskid", fixUpTaskId);

		return result;
	}

	// Add CreditCard -----------------------------------------------------------------

	@RequestMapping(value = "/add-credit-card", method = RequestMethod.GET)
	public ModelAndView createCreditCard(final Application application, final BindingResult binding) {
		ModelAndView result;
		CreditCard creditCard;
		List<String> brands = new ArrayList<String>();

		creditCard = new CreditCard();
		Assert.notNull(creditCard);
		brands = this.systemConfigurationService.getSystemConfiguration().getCreditCardMakers();

		result = new ModelAndView("application/add-credit-card");
		result.addObject("creditCard", creditCard);
		result.addObject("brands", brands);

		result.addObject("messageCode", null);

		return result;
	}
	@RequestMapping(value = "/add-credit-card", method = RequestMethod.POST, params = "submit")
	public ModelAndView saveCreditCard(final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;
		Customer customer;

		customer = this.customerService.findPrincipal();

		if (binding.hasErrors()) {
			result = new ModelAndView("application/add-credit-card");
			result.addObject("customer", customer);
			result.addObject("creditCard", creditCard);
			result.addObject("messageCode", null);
		} else
			try {
				customer.setCreditCard(creditCard);
				this.customerService.save(customer);

				result = new ModelAndView("redirect:customer.do");

			} catch (final Throwable oops) {
				result = new ModelAndView("application/add-credit-card");
				result.addObject("customer", customer);
				result.addObject("creditCard", creditCard);
				result.addObject("messageCode", "creditCard.commit.error");
			}
		return result;
	}

}
