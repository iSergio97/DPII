
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.EducationData;
import forms.EducationDataForm;
import repositories.EducationDataRepository;

@Service
@Transactional
public class EducationDataService extends AbstractService<EducationData>{

	@Autowired
	private EducationDataRepository educationDataRepository;


	public EducationDataService() {
		super();
	}

	public EducationData create() {
		final EducationData eData = new EducationData();

		eData.setDegree("");
		eData.setInstitution("");
		eData.setMark(0.);

		return eData;
	}

	public Iterable<EducationData> save(final Iterable<EducationData> pDatas) {
		Assert.isTrue(pDatas != null);
		return this.educationDataRepository.save(pDatas);
	}

	public EducationDataForm createForm() {
		final EducationDataForm pdForm = new EducationDataForm();

		pdForm.setDegree("");
		pdForm.setInstitution("");
		pdForm.setMark(0.);
		return pdForm;
	}

	public EducationData reconstructForm(final EducationDataForm pdForm, final BindingResult bindingResult) {
		EducationData result;

		if (pdForm.getId() == 0)
			result = this.create();
		else
			result = this.educationDataRepository.findOne(pdForm.getId());

		result.setDegree(pdForm.getDegree());
		result.setEndDate(pdForm.getEndDate());
		result.setStartDate(pdForm.getStartDate());
		result.setInstitution(pdForm.getInstitution());
		result.setMark(pdForm.getMark());

		return result;
	}

	public EducationDataForm deconstruct(final EducationData pData) {
		final EducationDataForm pdForm = this.createForm();

		return pdForm;
	}
}