
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InceptionRecordRepository;
import security.Authority;
import security.LoginService;
import domain.InceptionRecord;

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
}
