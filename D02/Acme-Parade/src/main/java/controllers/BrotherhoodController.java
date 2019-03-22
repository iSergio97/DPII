
package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.AcmeFloatService;
import services.BrotherhoodService;
import services.MemberService;
import services.ParadeService;
import domain.AcmeFloat;
import domain.Brotherhood;
import domain.Member;
import domain.Parade;

@Controller
@RequestMapping("/brotherhood/public")
public class BrotherhoodController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private AcmeFloatService	acmeFloatService;

	@Autowired
	private MemberService		memberService;


	// Constructors -----------------------------------------------------------

	public BrotherhoodController() {
		super();
	}

	// List -------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("brotherhood/public/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", "brotherhood/public/list.do");

		return result;
	}

	// Show -------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		Assert.notNull(brotherhood);
		brotherhood.setAcmeFloats(new ArrayList<AcmeFloat>());
		result = this.createEditModelAndView(brotherhood);

		return result;
	}

	// Ancillary Methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood) {
		final ModelAndView result;
		final Collection<Parade> parades;
		final Collection<AcmeFloat> acmeFloats;
		final Collection<Member> members;
		final UserAccount userAccount = brotherhood.getUserAccount();

		parades = this.paradeService.findAllFinalByBrotherhoodAccountId(userAccount.getId());
		acmeFloats = this.acmeFloatService.findFloatsByBrotherhoodUserAccount(userAccount.getId());
		members = this.memberService.findMemebersByBrotherhoodAccountId(userAccount.getId());

		result = new ModelAndView("brotherhood/public/" + "show");
		result.addObject("parades", parades);
		result.addObject("acmeFloats", acmeFloats);
		result.addObject("members", members);
		result.addObject("establishmentDate", new SimpleDateFormat("dd/MM/yyyy").format(brotherhood.getEstablishmentDate()));

		result.addObject("brotherhood", brotherhood);

		// result.addObject("messageCode", null);

		return result;
	}
}
