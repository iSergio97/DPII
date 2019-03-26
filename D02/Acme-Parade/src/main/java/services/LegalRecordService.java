
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LegalRecordRepository;
import domain.LegalRecord;
import forms.LegalRecordForm;

@Service
@Transactional
public class LegalRecordService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private LegalRecordRepository	legalRecordRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private Validator				validator;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public LegalRecordService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public LegalRecord create() {
		final LegalRecord result = new LegalRecord();

		result.setTitle("");
		result.setDescription("");
		result.setLegalName("");
		result.setApplicableLaws("");
		result.setVAT(0.0);
		result.setHistory(null);

		return result;
	}

	public LegalRecordForm createForm() {
		final LegalRecordForm record = new LegalRecordForm();

		record.setTitle("");
		record.setDescription("");
		record.setLegalName("");
		record.setVAT(0.0);
		record.setApplicableLaws("");
		record.setId(0);

		return record;
	}

	public LegalRecord save(final LegalRecord record) {
		Assert.isTrue(record != null);
		return this.legalRecordRepository.save(record);
	}

	public Iterable<LegalRecord> save(final Iterable<LegalRecord> records) {
		Assert.isTrue(records != null);
		return this.legalRecordRepository.save(records);
	}

	public void delete(final LegalRecord record) {
		Assert.isTrue(record != null);
		this.legalRecordRepository.delete(record);
	}

	public void delete(final Iterable<LegalRecord> records) {
		Assert.isTrue(records != null);
		this.legalRecordRepository.delete(records);
	}

	public LegalRecord findOne(final int id) {
		return this.legalRecordRepository.findOne(id);
	}

	public List<LegalRecord> findAll() {
		return this.legalRecordRepository.findAll();
	}

	public Collection<LegalRecord> getLegalRecordsByHistory(final int historyId) {
		return this.legalRecordRepository.getLegalRecordsByHistory(historyId);
	}

	public LegalRecord reconstruct(final LegalRecordForm record, final BindingResult binding) {
		LegalRecord result;

		if (record.getId() == 0)
			result = this.create();
		else
			result = this.legalRecordRepository.findOne(record.getId());

		result.setTitle(record.getTitle());
		result.setDescription(record.getDescription());
		result.setLegalName(record.getLegalName());
		result.setVAT(record.getVAT());
		result.setApplicableLaws(record.getApplicableLaws());
		result.setHistory(this.brotherhoodService.findPrincipal().getHistory());

		this.validator.validate(result, binding);
		this.legalRecordRepository.flush();

		return result;
	}

}
