/*
 * ChapterService.java
 *
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Chapter;
import domain.Season;
import forms.ChapterForm;
import repositories.ChapterRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class ChapterService extends AbstractService<ChapterRepository, Chapter> {

	@Override
	public Chapter create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.PUBLISHER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		final Chapter chapter = new Chapter();
		chapter.setTitle("New Chapter");
		chapter.setDescription("New Description");
		chapter.setDuration(1);
		chapter.setNumber(1);
		chapter.setReleaseDate(new Date());

		return chapter;
	}

	@Override
	public Chapter save(final Chapter res) {
		final Authority a = new Authority();
		final Authority b = new Authority();
		a.setAuthority(Authority.PUBLISHER);
		b.setAuthority(Authority.ADMINISTRATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a) || LoginService.getPrincipal().getAuthorities().contains(b));
		return super.save(res);
	}

	@Override
	public void delete(final Chapter res) {
		final Authority a = new Authority();
		final Authority b = new Authority();
		a.setAuthority(Authority.PUBLISHER);
		b.setAuthority(Authority.ADMINISTRATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a) || LoginService.getPrincipal().getAuthorities().contains(b));
		super.delete(res);
	}

	//////////////////////////////////////////////////////////////
	//Form methods

	public ChapterForm createForm(final Chapter c) {
		final ChapterForm form = new ChapterForm();
		form.setId(c.getId());
		form.setTitle(c.getTitle());
		form.setNumber(c.getNumber());
		form.setDescription(c.getDescription());
		form.setReleaseDate(c.getReleaseDate());
		form.setDuration(c.getDuration());

		return form;
	}

	public Chapter reconstruct(final ChapterForm form, final BindingResult binding) {
		Chapter result;
		if (form.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(form.getId());

		result.setTitle(form.getTitle());
		result.setDescription(form.getDescription());
		result.setNumber(form.getNumber());
		result.setReleaseDate(form.getReleaseDate());
		result.setDuration(form.getDuration());

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Chapter> findChaptersBySeason(final Season s) {
		return this.repository.findChaptersBySeasonId(s.getId());
	}

}