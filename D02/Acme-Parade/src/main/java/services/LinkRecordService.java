
package services;

import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LinkRecordRepository;
import security.Authority;
import security.LoginService;
import domain.LinkRecord;
import forms.LinkRecordForm;

@Service
@Transactional
public class LinkRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private LinkRecordRepository	linkRecordRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private Validator				validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public LinkRecordService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public LinkRecord create() {

		Assert.notNull(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));

		final LinkRecord result = new LinkRecord();

		result.setTitle("");
		result.setDescription("");

		result.setBrotherhood(null);
		result.setHistory(this.brotherhoodService.findPrincipal().getHistory());

		return result;
	}

	public LinkRecordForm createForm() {
		final LinkRecordForm record = new LinkRecordForm();

		record.setTitle("");
		record.setDescription("");
		record.setBrotherhood(this.brotherhoodService.create());

		return record;
	}
	public LinkRecord save(final LinkRecord record) {

		Assert.notNull(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		Assert.isTrue(this.brotherhoodService.findPrincipal().getHistory().equals(record.getHistory()));
		Assert.isTrue(record != null);

		return this.linkRecordRepository.save(record);
	}

	public Iterable<LinkRecord> save(final Iterable<LinkRecord> records) {
		Assert.isTrue(records != null);
		return this.linkRecordRepository.save(records);
	}

	public void delete(final LinkRecord record) {

		Assert.notNull(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		Assert.isTrue(this.brotherhoodService.findPrincipal().getHistory().equals(record.getHistory()));
		Assert.isTrue(record != null);

		this.linkRecordRepository.delete(record);
	}

	public void delete(final Iterable<LinkRecord> records) {
		Assert.isTrue(records != null);
		this.linkRecordRepository.delete(records);
	}

	public LinkRecord findOne(final int id) {
		return this.linkRecordRepository.findOne(id);
	}

	public List<LinkRecord> findAll() {
		return this.linkRecordRepository.findAll();
	}

	public Collection<LinkRecord> getLinkRecordsByHistory(final int historyId) {
		return this.linkRecordRepository.getLinkRecordsByHistory(historyId);
	}

	public LinkRecord reconstruct(final LinkRecordForm record, final BindingResult binding) {
		LinkRecord result;

		if (record.getId() == 0)
			result = this.create();
		else
			result = this.linkRecordRepository.findOne(record.getId());

		result.setTitle(record.getTitle());
		result.setDescription(record.getDescription());
		result.setBrotherhood(record.getBrotherhood());
		result.setHistory(this.brotherhoodService.findPrincipal().getHistory());

		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

}
