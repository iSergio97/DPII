
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.MemberService;
import services.ParadeService;
import services.RequestService;
import domain.Brotherhood;
import domain.Member;
import domain.Parade;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private RequestService		requestService;
	@Autowired
	private ParadeService		paradeService;
	@Autowired
	private MemberService		memberService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Constructors ----------------------------------------------------------------

	public RequestController() {
		super();
	}

	// Member ----------------------------------------------------------------------

	// List

	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	public ModelAndView memberList() {
		ModelAndView result;
		Collection<Request> requests;
		Member member;

		member = this.memberService.findPrincipal();
		requests = member.getRequests();

		result = new ModelAndView("/request/member/list");
		result.addObject("requests", requests);
		result.addObject("member", member);
		result.addObject("requestURI", "request/member/list.do");

		return result;
	}

	// Show

	@RequestMapping(value = "/member/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "requestId") final int requestId) {
		ModelAndView result;
		Request request;

		request = this.requestService.findOne(requestId);
		result = new ModelAndView("/request/member/show");

		result.addObject("request", request);
		return result;
	}

	//----Create

	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		RequestForm request;

		request = this.requestService.createForm();
		result = this.createAndEditModelAndView(request, "create");

		return result;
	}

	// Edit

	@RequestMapping(value = "/member/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;
		Collection<Parade> parades;
		Member member;

		member = this.memberService.findPrincipal();
		request = this.requestService.findOne(requestId);
		parades = this.paradeService.findPossibleMemberParades(member.getId());
		Assert.notNull(request);
		result = new ModelAndView("/request/member/edit");
		result.addObject("request", request);
		result.addObject("parades", parades);

		return result;
	}

	// Save

	@RequestMapping(value = "/member/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("request") final RequestForm request, final BindingResult bindingResult) {
		ModelAndView result;
		Request request2;

		request2 = this.requestService.reconstructForm(request, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createAndEditModelAndView(request);
		else
			try {
				this.requestService.save(request2);
				result = new ModelAndView("redirect:/request/member/list.do");
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(request, "request.commit.error");
			}
		return result;
	}

	//--------BROTHERHOOD

	//----List

	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView brotherhoodList() {
		ModelAndView result;
		Collection<Request> requests;
		Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.findPrincipal();
		requests = this.requestService.getOrderedBrotherhoodRequests(brotherhood.getId());

		result = new ModelAndView("/request/brotherhood/list");
		result.addObject("requests", requests);
		result.addObject("brotherhood", brotherhood);
		result.addObject("requestURI", "request/brotherhood/list.do");

		return result;
	}

	//----Show

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.GET)
	public ModelAndView brotherhoodShow(@RequestParam(value = "requestId") final int requestId) {
		ModelAndView result;
		Request request;

		request = this.requestService.findOne(requestId);
		result = new ModelAndView("/request/brotherhood/show");

		result.addObject("request", request);
		return result;
	}

	//----Accept Member

	@RequestMapping(value = "/brotherhood/accept", method = RequestMethod.GET)
	public ModelAndView acceptMember(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		Request request;

		request = this.requestService.findOne(id);
		request.setStatus("APPROVED");

		result = new ModelAndView("/request/brotherhood/accept");
		result.addObject("request", request);

		return result;
	}

	@RequestMapping(value = "/brotherhood/accept", method = RequestMethod.POST, params = "accept")
	public ModelAndView acceptMember(@ModelAttribute("request") final RequestForm requestForm, final BindingResult binding) {
		Request request;
		ModelAndView result;

		request = this.requestService.reconstructFormBroAccept(requestForm, binding);

		if (binding.hasErrors())
			result = this.acceptModelAndView(requestForm);
		else
			try {
				request.setStatus("APPROVED");
				this.requestService.save(request);
				result = new ModelAndView("redirect:/request/brotherhood/list.do");
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(requestForm, "request.commit.error");
			}
		return result;
	}

	//----Reject Member

	@RequestMapping(value = "/brotherhood/reject", method = RequestMethod.GET)
	public ModelAndView rejectMember(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		Request request;

		request = this.requestService.findOne(id);
		request.setStatus("REJECTED");

		result = new ModelAndView("/request/brotherhood/reject");
		result.addObject("request", request);
		return result;
	}

	@RequestMapping(value = "/brotherhood/reject", method = RequestMethod.POST, params = "reject")
	public ModelAndView rejectMember(@ModelAttribute("request") final RequestForm requestForm, final BindingResult binding) {
		Request request;
		ModelAndView result;

		request = this.requestService.reconstructFormBroReject(requestForm, binding);

		if (binding.hasErrors())
			result = this.rejectModelAndView(requestForm);
		else
			try {
				request.setStatus("REJECTED");
				this.requestService.save(request);
				result = new ModelAndView("redirect:/request/brotherhood/list.do");
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(requestForm, "request.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/member/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		Request request;
		Member member;

		request = this.requestService.findOne(id);
		member = this.memberService.findPrincipal();
		Assert.isTrue(request.getMember().equals(member));
		this.requestService.delete(request);

		result = new ModelAndView("redirect:/request/member/list.do");

		return result;
	}

	//-------ANCILLARY METHODS

	protected ModelAndView createAndEditModelAndView(final RequestForm request) {
		ModelAndView result;

		result = this.createAndEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final RequestForm request, final String message) {
		final ModelAndView result;
		final Collection<Parade> parades;
		Member member;

		member = this.memberService.findPrincipal();
		parades = this.paradeService.findPossibleMemberParades(member.getId());

		result = new ModelAndView("/request/member/create");
		result.addObject("request", request);
		result.addObject("parades", parades);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView acceptModelAndView(final RequestForm request) {
		ModelAndView result;

		result = this.acceptModelAndView(request, null);

		return result;
	}

	protected ModelAndView acceptModelAndView(final RequestForm request, final String message) {
		ModelAndView result;

		result = new ModelAndView("/request/brotherhood/accept");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView rejectModelAndView(final RequestForm request) {
		ModelAndView result;

		result = this.rejectModelAndView(request, null);

		return result;
	}

	protected ModelAndView rejectModelAndView(final RequestForm request, final String message) {
		ModelAndView result;

		result = new ModelAndView("/request/brotherhood/reject");
		result.addObject("message", message);

		return result;
	}

}
