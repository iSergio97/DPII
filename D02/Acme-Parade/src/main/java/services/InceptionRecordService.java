
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.InceptionRecordRepository;
import security.Authority;
import security.LoginService;
import domain.InceptionRecord;
import forms.InceptionRecordForm;

@Service
@Transactional
public class InceptionRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private BrotherhoodService			brotherhoodService;

	@Autowired
	private Validator					validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public InceptionRecordService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public InceptionRecord create() {
		Assert.notNull(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		final InceptionRecord result = new InceptionRecord();
		result.setTitle("");
		result.setDescription("");
		result.setPhotos(new ArrayList<String>());

		result.setHistory(this.brotherhoodService.findPrincipal().getHistory());

		return result;
	}

	public InceptionRecord save(final InceptionRecord record) {

		Assert.notNull(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		Assert.isTrue(this.brotherhoodService.findPrincipal().getHistory().equals(record.getHistory()));
		Assert.isTrue(record != null);

		return this.inceptionRecordRepository.save(record);
	}

	public Iterable<InceptionRecord> save(final Iterable<InceptionRecord> records) {
		Assert.isTrue(records != null);
		return this.inceptionRecordRepository.save(records);
	}

	public void delete(final InceptionRecord record) {

		Assert.notNull(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		Assert.isTrue(this.brotherhoodService.findPrincipal().getHistory().equals(record.getHistory()));
		Assert.isTrue(record != null);

		this.inceptionRecordRepository.delete(record);
	}

	public void delete(final Iterable<InceptionRecord> records) {
		Assert.isTrue(records != null);
		this.inceptionRecordRepository.delete(records);
	}

	public InceptionRecord findOne(final int id) {
		return this.inceptionRecordRepository.findOne(id);
	}

	public List<InceptionRecord> findAll() {
		return this.inceptionRecordRepository.findAll();
	}

	public InceptionRecord getInceptionRecordByHistory(final int historyId) {
		return this.inceptionRecordRepository.getInceptionRecordByHistory(historyId);
	}

	public InceptionRecordForm createForm() {
		final InceptionRecordForm rf = new InceptionRecordForm();
		rf.setDescription("");
		rf.setPhotos(new ArrayList<String>());
		rf.setTitle("");
		return rf;
	}

	public InceptionRecord reconstruct(final InceptionRecordForm record, final BindingResult bindingResult) {
		InceptionRecord result;

		if (record.getId() == 0)
			result = this.create();
		else
			result = this.inceptionRecordRepository.findOne(record.getId());

		result.setTitle(record.getTitle());
		result.setDescription(record.getDescription());
		result.setPhotos(record.getPhotos());
		result.setHistory(this.brotherhoodService.findPrincipal().getHistory());

		this.validator.validate(result, bindingResult);
		this.inceptionRecordRepository.flush();

		return result;
	}
}
