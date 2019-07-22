
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import services.PeriodRecordService;
import domain.Brotherhood;
import domain.History;
import domain.PeriodRecord;
import domain.Record;
import forms.PeriodRecordForm;

@Controller
@RequestMapping("/periodRecord")
public class PeriodRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PeriodRecordService	periodRecordService;

	@Autowired
	private HistoryService		historyService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Constructors ----------------------------------------------------------

	public PeriodRecordController() {
		super();
	}

	// Show -----------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id") final int id) {
		ModelAndView result;
		PeriodRecord record;
		History history;

		record = this.periodRecordService.findOne(id);
		history = this.brotherhoodService.findPrincipal().getHistory();
		Assert.isTrue(history.getRecords().contains(record));

		result = this.createModelAndViewWithSystemConfiguration("/periodRecord/show");
		result.addObject("periodRecord", record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "addPhoto")
	public ModelAndView show(@RequestParam(value = "id") final int id, @RequestParam(value = "photo") final String photo) {
		PeriodRecord periodRecord;

		periodRecord = this.periodRecordService.findOne(id);

		final List<String> photos = periodRecord.getPhotos();
		photos.add(photo);
		periodRecord.setPhotos(photos);

		periodRecord = this.periodRecordService.save(periodRecord);

		return this.show(periodRecord.getId());
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deletePhoto")
	public ModelAndView show(@RequestParam(value = "id") final int id, @RequestParam(value = "photoIndex") final int photoIndex) {
		PeriodRecord periodRecord;

		periodRecord = this.periodRecordService.findOne(id);

		final List<String> photos = periodRecord.getPhotos();
		photos.remove(photoIndex);
		periodRecord.setPhotos(photos);

		periodRecord = this.periodRecordService.save(periodRecord);

		return this.show(periodRecord.getId());
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<PeriodRecord> records;
		Brotherhood b;
		History h;

		b = this.brotherhoodService.findPrincipal();
		h = b.getHistory();
		records = this.periodRecordService.getPeriodRecordsByHistory(h.getId());

		result = this.createModelAndViewWithSystemConfiguration("/periodRecord/list");
		result.addObject("periodRecords", records);

		return result;
	}

	// Create ------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PeriodRecordForm record;

		record = this.periodRecordService.createForm();
		result = this.createAndEditModelAndView(record, "create");

		return result;
	}

	// Edit -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int recordId) {
		ModelAndView result;
		final PeriodRecord periodRecord;

		periodRecord = this.periodRecordService.findOne(recordId);
		Assert.notNull(periodRecord);
		result = this.createModelAndViewWithSystemConfiguration("/periodRecord/edit");
		result.addObject("periodRecord", periodRecord);

		return result;
	}

	// Save ------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("periodRecord") final PeriodRecordForm record, final BindingResult bindingResult) {
		ModelAndView result;
		PeriodRecord record2;
		Brotherhood bro;
		History h;
		Collection<Record> records;

		bro = this.brotherhoodService.findPrincipal();
		h = bro.getHistory();
		records = h.getRecords();

		if (record.getEndYear() < record.getStartYear())
			bindingResult.rejectValue("endYear", "error.endYear");

		final int actual = new Date().getYear();
		if (record.getStartYear() > actual)
			bindingResult.rejectValue("startYear", "error.startYear");

		record2 = this.periodRecordService.reconstruct(record, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createAndEditModelAndView(record);
		else
			try {

				final PeriodRecord pr = this.periodRecordService.save(record2);
				records.add(pr);
				h.setRecords(records);
				this.historyService.save(h);
				result = this.createModelAndViewWithSystemConfiguration("redirect:/periodRecord/list.do");
			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(record, "periodRecord.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final PeriodRecord periodRecord;

		periodRecord = this.periodRecordService.findOne(id);

		this.periodRecordService.delete(periodRecord);

		result = this.createModelAndViewWithSystemConfiguration("redirect:/periodRecord/list.do");

		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final PeriodRecordForm record) {
		ModelAndView result;

		result = this.createAndEditModelAndView(record, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final PeriodRecordForm record, final String message) {
		final ModelAndView result;

		result = this.createModelAndViewWithSystemConfiguration("/periodRecord/create");
		result.addObject("periodRecord", record);
		result.addObject("message", message);

		return result;
	}

}
