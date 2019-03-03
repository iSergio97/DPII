
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import domain.Area;

@Service
@Transactional
public class AreaService {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	private AreaRepository	areaRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	////////////////////////////////////////////////////////////////////////////////
	// Constructors

	public AreaService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public Area create() {
		final Area area = new Area();
		area.setName("");
		area.setPictures(new ArrayList<String>());
		return area;
	}

	public Area save(final Area area) {
		Assert.isTrue(area != null);
		return this.areaRepository.save(area);
	}

	public Iterable<Area> save(final Iterable<Area> areas) {
		Assert.isTrue(areas != null);
		return this.areaRepository.save(areas);
	}

	public void delete(final Area area) {
		Assert.isTrue(area != null);
		this.areaRepository.delete(area);
	}

	public void delete(final Iterable<Area> areas) {
		Assert.isTrue(areas != null);
		this.areaRepository.delete(areas);
	}

	public Area findOne(final int id) {
		return this.areaRepository.findOne(id);
	}

	public List<Area> findAll() {
		return this.areaRepository.findAll();
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

}
