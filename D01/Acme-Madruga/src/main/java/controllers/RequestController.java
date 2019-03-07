
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import services.ProcessionService;
import services.RequestService;
import domain.Member;
import domain.Procession;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController {

	@Autowired
	private RequestService		requestService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private MemberService		memberService;


	public RequestController() {
		super();
	}

	//---------MEMBER

	//----List

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

	//-----Show

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

	//-----Edit

	@RequestMapping(value = "/member/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;
		Collection<Procession> processions;

		request = this.requestService.findOne(requestId);
		processions = this.processionService.findAll();
		Assert.notNull(request);
		result = new ModelAndView("/request/member/edit");
		result.addObject("request", request);
		result.addObject("processions", processions);

		return result;
	}

	//----Save

	@RequestMapping(value = "/member/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final RequestForm request, final BindingResult bindingResult) {
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

	//----Accept Member

	@RequestMapping(value = "/brotherhood/accept", method = RequestMethod.POST, params = "accept")
	public ModelAndView acceptMember(@RequestParam(value = "id") final int id, @RequestParam(value = "hLine") final int hLine, @RequestParam(value = "vLine") final int vLine) {
		ModelAndView result;
		Request request;

		request = this.requestService.findOne(id);
		Assert.isTrue(request != null);
		Assert.isTrue(request.getStatus().equals("PENDING"));

		request.setStatus("ACCEPTED");
		request.setHLine(hLine);
		request.setVLine(vLine);

		this.requestService.save(request);

		result = new ModelAndView("redirect:..request/list.do");
		return result;
	}

	// Delete -----------------------------------------------------------------

	//	@RequestMapping(value = "/member/delete", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(@RequestParam(value = "id") final int id) {
	//		final ModelAndView result;
	//		Request request;
	//		Member member;
	//
	//		request = this.requestService.findOne(id);
	//		member = this.memberService.findPrincipal();
	//		Assert.isTrue(request.getMember().equals(member));
	//		this.requestService.delete(request);
	//
	//		result = new ModelAndView("redirect:/request/list.do");
	//
	//		return result;
	//	}

	//-------ANCILLARY METHODS

	protected ModelAndView createAndEditModelAndView(final RequestForm request) {
		ModelAndView result;

		result = this.createAndEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final RequestForm request, final String message) {
		ModelAndView result;
		final Collection<Procession> processions;

		processions = this.processionService.findAll();

		result = new ModelAndView("/request/member/create");
		result.addObject("request", request);
		result.addObject("processions", processions);
		result.addObject("message", message);

		return result;
	}

}
