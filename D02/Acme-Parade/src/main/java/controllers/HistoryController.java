
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.HistoryService;
import services.InceptionRecordService;
import services.LegalRecordService;
import services.LinkRecordService;
import services.MiscellaneousRecordService;
import services.PeriodRecordService;
import domain.History;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;

@Controller
@RequestMapping("/history")
public class HistoryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HistoryService				historyService;

	@Autowired
	private InceptionRecordService		inceptionRecordService;

	@Autowired
	private LegalRecordService			legalRecordService;

	@Autowired
	private LinkRecordService			linkRecordService;

	@Autowired
	private MiscellaneousRecordService	miscRecordService;

	@Autowired
	private PeriodRecordService			periodRecordService;

	@Autowired
	private BrotherhoodService			brotherhoodService;


	// Constructors ----------------------------------------------------------

	public HistoryController() {
		super();
	}

	// List -----------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		final ModelAndView result;
		History h;
		InceptionRecord inceptionRecord;
		Collection<LegalRecord> legalRecords;
		Collection<LinkRecord> linkRecords;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		Collection<PeriodRecord> periodRecords;

		h = this.brotherhoodService.findPrincipal().getHistory();
		inceptionRecord = this.inceptionRecordService.getInceptionRecordByHistory(h.getId());
		legalRecords = this.legalRecordService.getLegalRecordsByHistory(h.getId());
		linkRecords = this.linkRecordService.getLinkRecordsByHistory(h.getId());
		miscellaneousRecords = this.miscRecordService.getMiscellaneousRecordsByHistory(h.getId());
		periodRecords = this.periodRecordService.getPeriodRecordsByHistory(h.getId());

		result = new ModelAndView("/history/show");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("legalRecords", legalRecords);
		result.addObject("linkRecords", linkRecords);
		result.addObject("miscellaneousRecords", miscellaneousRecords);
		result.addObject("periodRecords", periodRecords);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		History history;

		history = this.historyService.create();
		result = new ModelAndView("redirect:/inceptionRecord/create.do");
		return result;
	}
}
