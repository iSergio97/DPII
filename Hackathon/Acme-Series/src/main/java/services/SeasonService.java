/*
 * SeasonService.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Chapter;
import domain.Season;
import domain.Serie;
import forms.SeasonForm;
import repositories.SeasonRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class SeasonService extends AbstractService<SeasonRepository, Season> {

	@Override
	public Season create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.PUBLISHER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		final Season season = new Season();
		season.setChapters(new ArrayList<Chapter>());
		season.setNumber(1);
		season.setStartDate(new Date());

		return season;
	}

	@Override
	public Season save(final Season res) {
		final Authority a = new Authority();
		final Authority b = new Authority();
		a.setAuthority(Authority.PUBLISHER);
		b.setAuthority(Authority.ADMINISTRATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a) || LoginService.getPrincipal().getAuthorities().contains(b));
		return super.save(res);
	}

	@Override
	public void delete(final Season res) {
		final Authority a = new Authority();
		final Authority b = new Authority();
		a.setAuthority(Authority.PUBLISHER);
		b.setAuthority(Authority.ADMINISTRATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a) || LoginService.getPrincipal().getAuthorities().contains(b));
		super.delete(res);
	}

	////////////////////////////////////////////////////////////////////////////////
	//Form methods

	public SeasonForm createForm(final Season s) {
		final SeasonForm season = new SeasonForm();
		season.setNumber(s.getNumber());
		season.setStartDate(s.getStartDate());
		season.setEndDate(s.getEndDate());
		season.setId(s.getId());

		return season;
	}

	public Season reconstruct(final SeasonForm form, final BindingResult binding) {
		Season result;

		if (form.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(form.getId());

		result.setNumber(form.getNumber());
		result.setStartDate(form.getStartDate());
		result.setEndDate(form.getEndDate());

		this.validator.validate(result, binding);

		return result;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Double[] getChaptersPerSeasonStatistics() {
		return this.repository.getChaptersPerSeasonStatistics();
	}

	public Season findSeasonAssociated(final int chapterId) {
		return this.repository.findSeasonAssociated(chapterId);
	}

	public Collection<Season> findSeasonsBySerie(final Serie s) {
		return this.repository.findSeasonsBySerieId(s.getId());
	}

}
