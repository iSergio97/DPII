
package controllers;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.HistoryService;
import services.MiscellaneousRecordService;
import domain.Brotherhood;
import domain.History;
import domain.MiscellaneousRecord;
import domain.Record;
import forms.MiscellaneousRecordForm;

@Controller
@RequestMapping("/miscellaneousRecord")
public class MiscellaneousRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscRecordService;

	@Autowired
	private HistoryService				historyService;

	@Autowired
	private BrotherhoodService			brotherhoodService;


	// Constructors ----------------------------------------------------------

	public MiscellaneousRecordController() {
		super();
	}

	// Show -----------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id") final int id) {
		ModelAndView result;
		MiscellaneousRecord record;
		History history;

		record = this.miscRecordService.findOne(id);
		history = this.brotherhoodService.findPrincipal().getHistory();
		Assert.isTrue(history.getRecords().contains(record));

		result = new ModelAndView("/miscellaneousRecord/show");
		result.addObject("miscellaneousRecord", record);

		return result;
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<MiscellaneousRecord> records;
		Brotherhood b;
		History h;

		b = this.brotherhoodService.findPrincipal();
		h = b.getHistory();
		records = this.miscRecordService.getMiscellaneousRecordsByHistory(h.getId());

		result = new ModelAndView("/miscellaneousRecord/list");
		result.addObject("miscellaneousRecords", records);

		return result;
	}

	// Create ------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MiscellaneousRecordForm record;

		record = this.miscRecordService.createForm();
		result = this.createAndEditModelAndView(record, "create");

		return result;
	}

	// Edit -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int recordId) {
		ModelAndView result;
		final MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscRecordService.findOne(recordId);
		Assert.notNull(miscellaneousRecord);
		result = new ModelAndView("/miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);

		return result;
	}

	// Save ------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final MiscellaneousRecordForm record, final BindingResult bindingResult) {
		ModelAndView result;
		MiscellaneousRecord record2;
		Brotherhood bro;
		History h;
		Collection<Record> records;

		bro = this.brotherhoodService.findPrincipal();
		h = bro.getHistory();
		records = h.getRecords();

		record2 = this.miscRecordService.reconstruct(record, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createAndEditModelAndView(record);
		else
			try {
				this.miscRecordService.save(record2);
				records.add(record2);
				h.setRecords(records);
				this.historyService.save(h);
				result = new ModelAndView("redirect:/miscellaneousRecord/list.do");
			} catch (final ValidationException oops) {
				result = this.createAndEditModelAndView(record);
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(record, "miscellaneousRecord.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/miscellaneousRecord/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final MiscellaneousRecord periodRecord;

		periodRecord = this.miscRecordService.findOne(id);

		this.miscRecordService.delete(periodRecord);

		result = this.list();

		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final MiscellaneousRecordForm record) {
		ModelAndView result;

		result = this.createAndEditModelAndView(record, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final MiscellaneousRecordForm record, final String message) {
		final ModelAndView result;

		result = new ModelAndView("/miscellaneousRecord/create");
		result.addObject("miscellaneousRecord", record);
		result.addObject("message", message);

		return result;
	}

}
