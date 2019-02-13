
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public SystemConfigurationService() {
		super();
	}

	// Methods ----------------------------------------------------------------

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		Assert.isTrue(systemConfiguration != null);
		// Don't make new system configurations.
		Assert.isTrue(systemConfiguration.getId() == this.getSystemConfiguration().getId());
		return this.systemConfigurationRepository.save(systemConfiguration);
	}

	public SystemConfiguration getSystemConfiguration() {
		return this.systemConfigurationRepository.findAll().get(0);
	}

	public String getMessage() {
		return "master.page.message." + this.getSystemConfiguration().getMessage();
	}

}
