/*
 * AuditService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.Position;
import forms.AuditForm;

@Service
@Transactional
public class AuditService extends AbstractService<AuditRepository, Audit> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private CompanyService	companyService;
	@Autowired
	private PositionService	positionService;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Audit create() {
		final Audit audit = super.create();

		audit.setIsDraft(true);

		return audit;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Form methods

	public AuditForm createForm() {
		final AuditForm auditForm = new AuditForm();

		auditForm.setText("");
		auditForm.setScore(0.0);
		auditForm.setPositionId(0);

		return auditForm;
	}

	public Audit reconstructForm(final AuditForm auditForm, final BindingResult bindingResult) {
		final Audit audit;

		if (auditForm.getId() == 0)
			audit = this.create();
		else
			audit = this.findOne(auditForm.getId());

		Assert.isTrue(audit.getIsDraft() == true);

		audit.setMoment(new Date());
		audit.setText(auditForm.getText());
		audit.setScore(auditForm.getScore());
		audit.setPosition(this.positionService.findOne(auditForm.getPositionId()));

		this.validator.validate(audit, bindingResult);

		return audit;
	}

	public AuditForm deconstruct(final Audit audit) {
		final AuditForm auditForm = this.createForm();

		Assert.isTrue(audit.getIsDraft() == true);

		auditForm.setId(audit.getId());
		auditForm.setText(audit.getText());
		auditForm.setScore(audit.getScore());
		auditForm.setPositionId(audit.getPosition().getId());

		return auditForm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Collection<Audit> getAuditsOfAuditor(final Auditor auditor) {
		return this.repository.findByAuditorId(auditor.getId());
	}

	public Collection<Audit> getAuditsOfPosition(final Position position) {
		return this.repository.findByPositionId(position.getId());
	}

	public Double getScoreOfPosition(final Position position) {
		if (this.repository.countByPositionId(position.getId()) == 0)
			return null;
		else
			return this.repository.averageScoreByPositionId(position.getId()) / 10.0d;
	}

	public Map<Position, Double> getScoresByPosition() {
		final Map<Position, Double> scores = new HashMap<>();
		for (final Position position : this.positionService.findAll())
			scores.put(position, this.getScoreOfPosition(position));
		return scores;
	}

	public Double getScoreOfCompany(final Company company) {
		if (this.repository.countByCompanyId(company.getId()) == 0)
			return null;
		else
			return this.repository.averageScoreByCompanyId(company.getId()) / 10.0d;
	}

	public Map<Company, Double> getScoresByCompany() {
		final Map<Company, Double> scores = new HashMap<>();
		for (final Company company : this.companyService.findAll())
			scores.put(company, this.getScoreOfCompany(company));
		return scores;
	}

	public double getMinimumScorePosition() {
		return this.repository.getMinimumScorePosition();
	}

	public double getMaximumScorePosition() {
		return this.repository.getMaximumScorePosition();
	}

	public double getAverageScorePosition() {
		return this.repository.getAverageScorePosition();
	}

	public double getStandardDeviationScorePosition() {
		return this.repository.getStandardDeviationScorePosition();
	}

	public double getMinimumScoreCompany() {
		return this.repository.getMinimumScoreCompany();
	}

	public double getMaximumScoreCompany() {
		return this.repository.getMaximumScoreCompany();
	}

	public double getAverageScoreCompany() {
		return this.repository.getAverageScoreCompany();
	}

	public double getStandardDeviationScoreCompany() {
		return this.repository.getStandardDeviationScoreCompany();
	}

	@SuppressWarnings("rawtypes")
	public Map<Company, Double> getCompaniesWithTheHighestAuditScoreAndTheirAverageSalary(final int numberOfCompanies) {
		final Map<Company, Double> scoresByCompany = this.getScoresByCompany();
		final Map<Company, Double> result = new HashMap<>();
		final List<Entry> entries = Arrays.asList(scoresByCompany.entrySet().toArray(new Map.Entry[scoresByCompany.size()]));
		final Comparator<Entry> c = new Comparator<Entry>() {

			@Override
			public int compare(final Entry o1, final Entry o2) {
				final Double l1 = (Double) o1.getValue();
				final Double l2 = (Double) o2.getValue();
				if (l1 == null)
					return -1;
				if (l2 == null)
					return 1;
				return l1 < l2 ? 1 : -1;
			}

		};
		Collections.sort(entries, c);
		for (int i = 0; i < numberOfCompanies; ++i) {
			final Company company = (Company) entries.get(i).getKey();
			final List<Position> companyPositions = this.positionService.findPositionsByCompany(company);
			if (companyPositions.size() == 0)
				result.put(company, null);
			else {
				Double salarySum = 0.0d;
				Double positionCount = 0.0d;
				for (final Position position : companyPositions) {
					salarySum += position.getSalary();
					positionCount += 1.0d;
				}
				result.put(company, salarySum / positionCount);
			}
		}
		return result;
	}

}
