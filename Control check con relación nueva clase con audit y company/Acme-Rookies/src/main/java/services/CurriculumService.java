
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Curriculum;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PositionData;
import domain.Rookie;
import repositories.CurriculumRepository;
import security.LoginService;

@Service
@Transactional
public class CurriculumService extends AbstractService<CurriculumRepository, Curriculum> {

	////////////////////////////////////////////////////////////////////////////////
	// Supporting services

	@Autowired
	private RookieService				rookieService;

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private PositionDataService			positionDataService;


	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@Override
	public Curriculum create() {
		final Curriculum curriculum = new Curriculum();
		curriculum.setName("");
		curriculum.setEducationData(new ArrayList<EducationData>());
		final Rookie principal = this.rookieService.findByUserAccountId(LoginService.getPrincipal().getId());
		curriculum.setRookie(principal);
		curriculum.setMiscellaneousData(new ArrayList<MiscellaneousData>());
		curriculum.setPositionData(new ArrayList<PositionData>());

		return curriculum;
	}

	public void saveAllCr(final Curriculum cr) {
		final Collection<EducationData> lsEdu = cr.getEducationData();
		final Collection<MiscellaneousData> lsMis = cr.getMiscellaneousData();
		final Collection<PositionData> lsPos = cr.getPositionData();

		this.educationDataService.save(lsEdu);
		this.miscellaneousDataService.save(lsMis);
		this.positionDataService.save(lsPos);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Ancillary methods

	public Curriculum copy(final Curriculum curriculum) {
		final Curriculum copy = this.create();
		copy.setName(curriculum.getName());
		copy.setRookie(curriculum.getRookie());
		// Copy personal data
		copy.setPersonalData(this.personalDataService.copy(curriculum.getPersonalData()));
		// Copy position data
		final ArrayList<PositionData> copiedPositionData = new ArrayList<>();
		for (final PositionData positionData : curriculum.getPositionData())
			copiedPositionData.add(this.positionDataService.copy(positionData));
		copy.setPositionData(copiedPositionData);
		// Copy education data
		final ArrayList<EducationData> copiedEducationData = new ArrayList<>();
		for (final EducationData educationData : curriculum.getEducationData())
			copiedEducationData.add(this.educationDataService.copy(educationData));
		copy.setEducationData(copiedEducationData);
		// Copy miscellaneous data
		final ArrayList<MiscellaneousData> copiedMiscellaneousData = new ArrayList<>();
		for (final MiscellaneousData miscellaneousData : curriculum.getMiscellaneousData())
			copiedMiscellaneousData.add(this.miscellaneousDataService.copy(miscellaneousData));
		copy.setMiscellaneousData(copiedMiscellaneousData);

		return this.save(copy);
	}

	public Collection<Curriculum> findCurriculaByRookie(final Rookie rookie) {
		return this.repository.findCurriculaByRookie(rookie.getId());
	}

	public Curriculum findCurriculumByPDId(final int id) {
		return this.repository.findCurriculumByPDId(id);
	}

	public int findOwner(final int curriculumId) {
		return this.repository.findOwner(curriculumId);
	}

}
