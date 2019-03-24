
package controllers;

import java.util.Collection;
import java.util.List;

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
import services.LinkRecordService;
import domain.Brotherhood;
import domain.History;
import domain.LinkRecord;
import domain.Record;
import forms.LinkRecordForm;

@Controller
@RequestMapping("/linkRecord")
public class LinkRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private LinkRecordService	linkRecordService;

	@Autowired
	private HistoryService		historyService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Constructors ----------------------------------------------------------

	public LinkRecordController() {
		super();
	}

	// Show -----------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id") final int id) {
		ModelAndView result;
		LinkRecord record;
		History history;

		record = this.linkRecordService.findOne(id);
		history = this.brotherhoodService.findPrincipal().getHistory();
		Assert.isTrue(history.getRecords().contains(record));

		result = new ModelAndView("/linkRecord/show");
		result.addObject("linkRecord", record);

		return result;
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<LinkRecord> records;
		Brotherhood b;
		History h;

		b = this.brotherhoodService.findPrincipal();
		h = b.getHistory();
		records = this.linkRecordService.getLinkRecordsByHistory(h.getId());

		result = new ModelAndView("/linkRecord/list");
		result.addObject("linkRecords", records);

		return result;
	}

	// Create ------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		LinkRecordForm record;

		record = this.linkRecordService.createForm();
		result = this.createAndEditModelAndView(record, "create");

		return result;
	}

	// Edit -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int recordId) {
		ModelAndView result;
		final LinkRecord linkRecord;
		List<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.findAll();
		linkRecord = this.linkRecordService.findOne(recordId);
		Assert.notNull(linkRecord);
		result = new ModelAndView("/linkRecord/edit");
		result.addObject("linkRecord", linkRecord);
		result.addObject("brotherhoods", brotherhoods);

		return result;
	}

	// Save ------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final LinkRecordForm record, final BindingResult bindingResult) {
		ModelAndView result;
		LinkRecord record2;
		Brotherhood bro;
		History h;
		Collection<Record> records;

		bro = this.brotherhoodService.findPrincipal();
		h = bro.getHistory();
		records = h.getRecords();

		record2 = this.linkRecordService.reconstruct(record, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createAndEditModelAndView(record);
		else
			try {
				this.linkRecordService.save(record2);
				records.add(record2);
				h.setRecords(records);
				this.historyService.save(h);
				result = new ModelAndView("redirect:/linkRecord/list.do");
			} catch (final ValidationException oops) {
				result = this.createAndEditModelAndView(record);
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(record, "linkRecord.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/linkRecord/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final LinkRecord linkRecord;

		linkRecord = this.linkRecordService.findOne(id);

		this.linkRecordService.delete(linkRecord);

		result = this.list();

		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final LinkRecordForm record) {
		ModelAndView result;

		result = this.createAndEditModelAndView(record, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final LinkRecordForm record, final String message) {
		final ModelAndView result;
		List<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("/linkRecord/create");
		result.addObject("linkRecord", record);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("message", message);

		return result;
	}

}
