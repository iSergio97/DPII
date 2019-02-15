/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import security.UserAccountRepository;
import services.FinderService;
import services.HandyWorkerService;
import services.MessageBoxService;
import services.WarrantyService;
import domain.Finder;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Warranty;

@Controller
@RequestMapping("/handy-worker")
public class HandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private WarrantyService			warrantyService;


	// Constructors -----------------------------------------------------------

	public HandyWorkerController() {
		super();
	}

	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerGet() {
		ModelAndView result;
		HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.create();
		result = new ModelAndView("handy-worker/register");
		result.addObject("handyWorker", handyWorker);
		return result;
	}

	//TODO Añadir Finder vacío
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerPost(@Valid HandyWorker handyWorker, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			UserAccount userAccount = handyWorker.getUserAccount();
			handyWorker = this.handyWorkerService.save(handyWorker);

			final String password = new Md5PasswordEncoder().encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(password);
			userAccount = this.userAccountRepository.save(userAccount);
			handyWorker.setUserAccount(userAccount);
			handyWorker = this.handyWorkerService.save(handyWorker);

			final ArrayList<MessageBox> savedMessageBoxes = new ArrayList<MessageBox>();
			for (MessageBox messageBox : this.messageBoxService.createSystemBoxes()) {
				messageBox.setActor(handyWorker);
				messageBox = this.messageBoxService.save(messageBox);
				savedMessageBoxes.add(messageBox);
			}
			handyWorker.setMessageBoxes(savedMessageBoxes);
			Finder finder = this.finderService.create();
			Warranty warranty = this.warrantyService.create();
			warranty = this.warrantyService.save(warranty);
			finder.setWarranty(warranty);
			finder.setHandyWorker(handyWorker);
			finder = this.finderService.save(finder);
			handyWorker.setFinder(finder);
			handyWorker = this.handyWorkerService.save(handyWorker);
			result = new ModelAndView("redirect:../welcome/index.do");
		} else {
			result = new ModelAndView("handy-worker/register");
			result.addObject("handyWorker", handyWorker);
			result.addObject("showError", binding);
		}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int handyWorkerId) {
		// Create result object
		ModelAndView result;
		HandyWorker handyWorker;
		result = new ModelAndView("handy-worker/show");
		handyWorker = this.handyWorkerService.findById(handyWorkerId);
		result.addObject("handyWorker", handyWorker);

		return result;
	}
}
