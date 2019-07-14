
package controllers;

import java.util.Collection;
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
import services.InceptionRecordService;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;
import domain.Record;
import forms.InceptionRecordForm;

@Controller
@RequestMapping("/inceptionRecord")
public class InceptionRecordController extends AbstractController {

	@Autowired
	private InceptionRecordService	inceptionRecordService;

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	public InceptionRecordController() {
		super();
	}

	// Show -----------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id") final int id) {
		ModelAndView result;
		InceptionRecord record;
		History history;

		record = this.inceptionRecordService.findOne(id);
		history = this.brotherhoodService.findPrincipal().getHistory();
		Assert.isTrue(history.getRecords().contains(record));

		result = this.createModelAndViewWithSystemConfiguration("/inceptionRecord/show");
		result.addObject("inceptionRecord", record);

		return result;
	}

	@RequestMapping(value = "/inceptionRecord/show", method = RequestMethod.POST, params = "addPhoto")
	public ModelAndView show(@RequestParam(value = "id") final int id, @RequestParam(value = "photo") final String photo) {
		InceptionRecord inceptionRecord;

		inceptionRecord = this.inceptionRecordService.findOne(id);

		final List<String> photos = (List<String>) inceptionRecord.getPhotos();
		photos.add(photo);
		inceptionRecord.setPhotos(photos);

		inceptionRecord = this.inceptionRecordService.save(inceptionRecord);

		return this.show(inceptionRecord.getId());
	}

	@RequestMapping(value = "/inceptionRecord/show", method = RequestMethod.POST, params = "deletePhoto")
	public ModelAndView show(@RequestParam(value = "id") final int id, @RequestParam(value = "photoIndex") final int photoIndex) {
		InceptionRecord inceptionRecord;

		inceptionRecord = this.inceptionRecordService.findOne(id);

		final List<String> photos = (List<String>) inceptionRecord.getPhotos();
		photos.remove(photoIndex);
		inceptionRecord.setPhotos(photos);

		inceptionRecord = this.inceptionRecordService.save(inceptionRecord);

		return this.show(inceptionRecord.getId());
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final InceptionRecord record;
		Brotherhood b;
		History h;

		b = this.brotherhoodService.findPrincipal();
		h = b.getHistory();
		record = this.inceptionRecordService.getInceptionRecordByHistory(h.getId());

		result = this.createModelAndViewWithSystemConfiguration("/inceptionRecord/list");
		result.addObject("inceptionRecords", record);

		return result;
	}

	// Create ------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		InceptionRecordForm record;

		record = this.inceptionRecordService.createForm();
		result = this.createAndEditModelAndView(record, "create");

		return result;
	}

	// Edit -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id") final int recordId) {
		ModelAndView result;
		final InceptionRecord periodRecord;

		periodRecord = this.inceptionRecordService.findOne(recordId);
		Assert.notNull(periodRecord);
		result = this.createModelAndViewWithSystemConfiguration("/periodRecord/edit");
		result.addObject("periodRecord", periodRecord);

		return result;
	}

	// Save ------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("inceptionRecord") final InceptionRecordForm record, final BindingResult bindingResult) {
		ModelAndView result;
		InceptionRecord record2;
		Brotherhood bro;
		History h;
		Collection<Record> records;

		bro = this.brotherhoodService.findPrincipal();
		h = bro.getHistory();
		records = h.getRecords();

		record2 = this.inceptionRecordService.reconstruct(record, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createAndEditModelAndView(record);
		else
			try {

				final InceptionRecord pr = this.inceptionRecordService.save(record2);
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

	@RequestMapping(value = "/periodRecord/delete", params = "delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value = "id") final int id) {
		final ModelAndView result;
		final InceptionRecord periodRecord;

		periodRecord = this.inceptionRecordService.findOne(id);

		this.inceptionRecordService.delete(periodRecord);

		result = this.createModelAndViewWithSystemConfiguration("redirect:/periodRecord/list.do");

		return result;
	}

	// Ancillary Methods ------------------------------------------------

	protected ModelAndView createAndEditModelAndView(final InceptionRecordForm record) {
		ModelAndView result;

		result = this.createAndEditModelAndView(record, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(final InceptionRecordForm record, final String message) {
		final ModelAndView result;

		result = this.createModelAndViewWithSystemConfiguration("/periodRecord/create");
		result.addObject("periodRecord", record);
		result.addObject("message", message);

		return result;
	}
}
