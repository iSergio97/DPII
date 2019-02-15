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
import services.CustomerService;
import services.MessageBoxService;
import domain.Customer;
import domain.MessageBox;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Register ---------------------------------------------------------------
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerGet() {
		ModelAndView result;
		Customer customer;
		customer = this.customerService.create();
		result = new ModelAndView("customer/register");
		result.addObject("customer", customer);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerPost(@Valid Customer customer, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			UserAccount userAccount = customer.getUserAccount();
			customer = this.customerService.save(customer);

			final String password = new Md5PasswordEncoder().encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(password);
			userAccount = this.userAccountRepository.save(userAccount);
			customer.setUserAccount(userAccount);
			customer = this.customerService.save(customer);

			final ArrayList<MessageBox> savedMessageBoxes = new ArrayList<MessageBox>();
			for (MessageBox messageBox : this.messageBoxService.createSystemBoxes()) {
				messageBox.setActor(customer);
				messageBox = this.messageBoxService.save(messageBox);
				savedMessageBoxes.add(messageBox);
			}
			customer.setMessageBoxes(savedMessageBoxes);
			customer = this.customerService.save(customer);

			result = new ModelAndView("redirect:../welcome/index.do");
		} else {
			result = new ModelAndView("customer/register");
			result.addObject("customer", customer);
			result.addObject("showError", binding);
			result.addObject("erroresBinding", binding.getAllErrors());
			for (int i = 0; i < binding.getAllErrors().size(); i++)
				System.out.println("Error " + i + binding.getAllErrors().get(i));
		}

		return result;
	}

	@RequestMapping(value = "/handyWorker/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int customerId) {
		// Create result object
		ModelAndView result;
		Customer customer;
		result = new ModelAndView("customer/show");
		customer = this.customerService.findById(customerId);
		result.addObject("customer", customer);

		return result;
	}
}
