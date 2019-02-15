
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private SocialProfileService	socialProfileService;


	@Test
	public void createTest() {
		Customer customer;
		super.authenticate("customer");
		customer = this.customerService.create();
		customer.setName("Sergio");
		customer.setEmail("sergiotb15@gmail.com");
		SocialProfile sp;
		sp = this.socialProfileService.create();
		sp.setActor(customer);
		sp.setLink("https://www.google.com");
		sp.setNickName("Sergio");
		sp.setSocialNetworkName("Social network");
		List<SocialProfile> ls;
		ls = new ArrayList<>();
		ls.add(sp);
		customer.setSocialProfiles(ls);
		customer.setPhoneNumber("123456789");
		customer.setPhoto("https://gyazo.com/bc9a07f806b4b8222308e38e48c6ff04");
		customer.setAddress("Calle");
		customer.setSurname("Shurname");
		Customer customerUpdated;
		customerUpdated = this.customerService.save(customer);
		Assert.isTrue(customerUpdated.getId() != 0);
		Assert.isTrue(customerUpdated.getMessageBoxes().size() == 4);
		Assert.isTrue(customerUpdated.getMessagesSent().size() == 0);

	}

}
