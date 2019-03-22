
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import domain.History;
import domain.Record;

@Service
@Transactional
public class HistoryService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private HistoryRepository	historyRepository;

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private BrotherhoodService	brotherhoodService;


	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public HistoryService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public History create() {
		final History result = new History();
		final List<Record> records = new ArrayList<>();

		result.setBrotherhood(this.brotherhoodService.findPrincipal());
		result.setRecords(records);

		return result;
	}

	public History save(final History history) {
		Assert.isTrue(history != null);
		return this.historyRepository.save(history);
	}

	public Iterable<History> save(final Iterable<History> histories) {
		Assert.isTrue(histories != null);
		return this.historyRepository.save(histories);
	}

	public void delete(final History history) {
		Assert.isTrue(history != null);
		this.historyRepository.delete(history);
	}

	public void delete(final Iterable<History> histories) {
		Assert.isTrue(histories != null);
		this.historyRepository.delete(histories);
	}

	public History findOne(final int id) {
		return this.historyRepository.findOne(id);
	}

	public List<History> findAll() {
		return this.historyRepository.findAll();
	}

}