
package services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import domain.Position;

@Service
@Transactional
public class PositionService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private PositionRepository	positionRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public PositionService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Position create() {
		final Position position = new Position();
		position.setStrings(new HashMap<String, String>());
		return position;
	}

	public Position save(final Position position) {
		Assert.isTrue(position != null);
		return this.positionRepository.save(position);
	}

	public Iterable<Position> save(final Iterable<Position> positions) {
		Assert.isTrue(positions != null);
		return this.positionRepository.save(positions);
	}

	public void delete(final Position position) {
		Assert.isTrue(position != null);
		this.positionRepository.delete(position);
	}

	public void delete(final Iterable<Position> positions) {
		Assert.isTrue(positions != null);
		this.positionRepository.delete(positions);
	}

	public Position findOne(final int id) {
		return this.positionRepository.findOne(id);
	}

	public List<Position> findAll() {
		return this.positionRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
