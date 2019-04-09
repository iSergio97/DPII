
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.PositionData;
import repositories.PositionDataRepository;

@Service
@Transactional
public class PositionDataService extends AbstractService<PositionData> {

	@Autowired
	private PositionDataRepository positionDataRepository;


	public PositionDataService() {
		super();
	}

	public PositionData create() {
		final PositionData pd = new PositionData();
		pd.setDescription("");
		pd.setTitle("");

		return pd;
	}

	public Iterable<PositionData> save(final Iterable<PositionData> pd) {
		Assert.isTrue(pd != null);
		return this.positionDataRepository.save(pd);
	}
}
