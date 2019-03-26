
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
import services.HistoryService;
import services.LegalRecordService;
import domain.Brotherhood;
import domain.History;
import domain.LegalRecord;
import domain.Record;
import forms.LegalRecordForm;

@Controller
@RequestMapping("/legalRecord")
public class LegalRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private LegalRecordService	legalRecordService;

	@Autowired
	private HistoryService		historyService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Constructors ----------------------------------------------------------

	public LegalRecordController() {
		super();
	}

	// Show -----------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id") final int id) {
		ModelAndView result;
		LegalRecord record;
		History history;

		record = this.legalRecordService.findOne(id);
		history = this.brotherhoodService.findPrincipal().getHistory();
		Assert.isTrue(history.getRecords().contains(record));

		result = new ModelAndView("/legalRecord/show");
		result.addObject("legalRecord", record);

		return result;
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<LegalRecord> records;
		Brotherhood b;
		History h;

		b = this.brotherhoodService.findPrincipal();
		h = b.getHistory();
		records = this.legalRecordService.getLegalRecordsByHistory(h.getId());

		result = new ModelAndView("/legalRecord/list");
		result.addObject("legalRecords", records);

		return result;
	}

	// Create ------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		LegalRecordForm record;

		record = this.legalRecordService.createForm();
		result = this.createAndEditModelAndView(record, "create");

		return result;
	}

	// Edit -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int recordId) {
		ModelAndView result;
		final LegalRecord legalRecord;
		Collection<Brotherhood> brotherhoods;
		brotherhoods = this.brotherhoodService.findAll();

		legalRecord = this.legalRecordService.findOne(recordId);
		Assert.notNull(legalRecord);
		result = new ModelAndView("/legalRecord/edit");
		result.addObject("legalRecord", legalRecord);
		result.addObject("brotherhoods", brotherhoods);

		return result;
	}
	// Save ------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("legalRecord") final LegalRecordForm record, final BindingResult bindingResult) {
		ModelAndView result;
		LegalRecord record2;
		Brotherhood bro;
		History h;
		Collection<Record> records;

		bro = this.brotherhoodService.findPrincipal();
		h = bro.getHistory();
		records = h.getRecords();

		record2 = this.legalRecordService.reconstruct(record, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createAndEditModelAndView(record);
		else
			try {
				final LegalRecord lr = this.legalRecordService.save(record2);
				records.add(lr);
				h.setRecords(records);
				this.historyService.save(h);
				result = new ModelAndView("redirect:/legalRecord/list.do");
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(record, "legalRecord.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/legalRecord/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final LegalRecord legalRecord;

		legalRecord = this.legalRecordService.findOne(id);

		this.legalRecordService.delete(legalRecord);

		result = this.list();

		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final LegalRecordForm record) {
		ModelAndView result;

		result = this.createAndEditModelAndView(record, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final LegalRecordForm record, final String message) {
		final ModelAndView result;

		result = new ModelAndView("/legalRecord/create");
		result.addObject("legalRecord", record);
		result.addObject("message", message);

		return result;
	}

}
