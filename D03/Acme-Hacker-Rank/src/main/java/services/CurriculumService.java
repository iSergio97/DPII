
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PositionData;
import repositories.CurriculumRepository;
import security.LoginService;

@Service
@Transactional
public class CurriculumService extends AbstractService<Curriculum> {

	@Autowired
	private CurriculumRepository	curriculumRepository;

	@Autowired
	private HackerService			hackerService;


	public CurriculumService() {
		super();
	}

	public Curriculum create() {
		final Curriculum curriculum = new Curriculum();
		curriculum.setEducationData(new ArrayList<EducationData>());
		final Hacker principal = this.hackerService.findByUserAccountId(LoginService.getPrincipal().getId());
		curriculum.setHacker(principal);
		curriculum.setMiscellaneousData(new ArrayList<MiscellaneousData>());
		curriculum.setPositionData(new ArrayList<PositionData>());

		return curriculum;
	}

	public Iterable<Curriculum> save(final Iterable<Curriculum> curriculum) {
		Assert.isTrue(curriculum != null);
		return this.curriculumRepository.save(curriculum);
	}

	public Collection<Curriculum> findCurriculumsByHacker(final Hacker hacker) {
		return this.curriculumRepository.findCurriculumsByHacker(hacker.getId());
	}

	public Curriculum findCurriculumByPDId(final int id) {
		return this.curriculumRepository.findCurriculumByPDId(id);
	}

}
