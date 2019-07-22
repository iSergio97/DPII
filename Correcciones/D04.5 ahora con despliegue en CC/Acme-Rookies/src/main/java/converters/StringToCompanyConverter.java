/*
 * StringToCompanyConverter.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CompanyRepository;
import domain.Company;

@Component
@Transactional
public class StringToCompanyConverter implements Converter<String, Company> {

	////////////////////////////////////////////////////////////////////////////////
	// Repository

	@Autowired
	private CompanyRepository	companyRepository;


	////////////////////////////////////////////////////////////////////////////////
	// Converter methods

	@Override
	public Company convert(final String text) {
		try {
			return this.companyRepository.findOne(Integer.parseInt(text));
		} catch (final Throwable throwable) {
			throw new IllegalArgumentException(throwable);
		}
	}

}
