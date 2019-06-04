/*
 * SerieService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.SerieRepository;
import security.Authority;
import security.LoginService;
import domain.Serie;
import forms.SerieForm;

@Service
@Transactional
public class SerieService extends AbstractService<SerieRepository, Serie> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private PublisherService	publisherService;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Serie create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.PUBLISHER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		final Serie serie = super.create();

		serie.setStatus("");
		serie.setIsDraft(true);
		serie.setPublisher(this.publisherService.findPrincipal());

		return serie;
	}

	@Override
	public Serie save(final Serie res) {
		final Authority a = new Authority();
		final Authority b = new Authority();
		a.setAuthority(Authority.PUBLISHER);
		b.setAuthority(Authority.ADMINISTRATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a) || LoginService.getPrincipal().getAuthorities().contains(b));
		return super.save(res);
	}

	@Override
	public void delete(final Serie res) {
		final Authority a = new Authority();
		final Authority b = new Authority();
		a.setAuthority(Authority.PUBLISHER);
		b.setAuthority(Authority.ADMINISTRATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a) || LoginService.getPrincipal().getAuthorities().contains(b));
		super.delete(res);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public SerieForm createForm() {
		final SerieForm form = new SerieForm();
		form.setTitle("");
		form.setDescription("");
		form.setBanner("");
		form.setStartDate(new Date());
		form.setStatus("");
		form.setIsDraft(true);
		form.setDirector("");
		form.setCast("");

		return form;
	}

	public Serie reconstruct(final SerieForm serieForm, final BindingResult binding) {
		Serie result;

		if (serieForm.getId() == 0)
			result = this.create();
		else
			result = this.repository.findOne(serieForm.getId());

		result.setTitle(serieForm.getTitle());
		result.setDescription(serieForm.getDescription());
		result.setBanner(serieForm.getBanner());
		result.setStartDate(serieForm.getStartDate());
		result.setEndDate(serieForm.getEndDate());
		result.setStatus(serieForm.getStatus());
		result.setDirector(serieForm.getDirector());
		result.setCast(serieForm.getCast());
		result.setIsDraft(serieForm.getIsDraft());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public SerieForm deconstruct(final Serie serie) {
		final SerieForm serieForm = this.createForm();

		serieForm.setId(serie.getId());
		serieForm.setTitle(serie.getTitle());
		serieForm.setDescription(serie.getDescription());
		serieForm.setBanner(serie.getBanner());
		serieForm.setStartDate(serie.getStartDate());
		serieForm.setEndDate(serie.getEndDate());
		serieForm.setStatus(serie.getStatus());
		serieForm.setDirector(serie.getDirector());
		serieForm.setCast(serie.getCast());
		serieForm.setIsDraft(serie.getIsDraft());

		return serieForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Double[] getSeasonsPerSerieStatistics() {
		return this.repository.getSeasonsPerSerieStatistics();
	}

	public Double[] getSerieCritiqueScoreStatistics(final Serie serie) {
		return this.repository.getSerieCritiqueScoreStatistics(serie.getId());
	}

	public List<Serie> getSeriesSortedByNumberOfFavorites() {
		return this.repository.getSeriesSortedByNumberOfFavorites();
	}

	public List<Object[]> getTop5SeriesWithMostComments() {
		final List<Object[]> topSeriesWithMostComments = this.repository.getSeriesSortedByNumberOfCommentsDescending();
		if (topSeriesWithMostComments.size() <= 5)
			return topSeriesWithMostComments;
		else
			return topSeriesWithMostComments.subList(0, 5);
	}

	public List<Object[]> getTop5SeriesWithBestAverageCritiqueScore() {
		final List<Object[]> topSeriesWithBestAverageCritiqueScore = this.repository.getSeriesSortedByCritiqueScoreDescending();
		if (topSeriesWithBestAverageCritiqueScore.size() <= 5)
			return topSeriesWithBestAverageCritiqueScore;
		else
			return topSeriesWithBestAverageCritiqueScore.subList(0, 5);
	}

	public Collection<Serie> findFavouriteByUserId(final int userId) {
		return this.repository.findFavouriteByUserId(userId);
	}

	public Collection<Serie> findPendingByUserId(final int userId) {
		return this.repository.findPendingByUserId(userId);
	}

	public Collection<Serie> findWatchingByUserId(final int userId) {
		return this.repository.findWatchingByUserId(userId);
	}

	public Collection<Serie> findWatchedByUserId(final int userId) {
		return this.repository.findWatchedByUserId(userId);
	}

	public Collection<Serie> findByPublisherId(final int publisherId) {
		return this.repository.findByPublisherId(publisherId);
	}

	public Collection<Serie> findPublicSeries() {
		return this.repository.findPublicSeries();
	}

	public Serie findSerieAssociated(final int seasonId) {
		return this.repository.findSerieAssociated(seasonId);
	}

	public Collection<Serie> searchQuery(final String text) {
		final String query = "%" + text + "%";
		return this.repository.searchQuery(query);
	}

}
