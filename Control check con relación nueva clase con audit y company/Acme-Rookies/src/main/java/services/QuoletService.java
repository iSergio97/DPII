
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Audit;
import domain.Company;
import domain.Quolet;
import forms.QuoletForm;
import repositories.QuoletRepository;

@Service
@Transactional
public class QuoletService extends AbstractService<QuoletRepository, Quolet> {

	@Autowired
	private AuditService		auditService;

	@Autowired
	private CompanyService		companyService;

	private static final int	TICKER_LENGTH	= 3;
	private static final String	TICKER_ALPHABET	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final Random		random			= new Random();


	//This methods has been created to be used in QuoletServiceTest.java
	//This is because our created is inherited from the AbstractService
	//We are not responsible for misuse or errors that it may cause
	public Quolet createForJunitTest() {
		final Quolet quolet = new Quolet();
		quolet.setTicker("");
		quolet.setBody("");
		quolet.setPicture("");
		quolet.setDraftMode(true);
		final Company company = this.companyService.findPrincipal();
		Assert.notNull(company);
		quolet.setCompany(company);

		return quolet;
	}

	@Override
	public Quolet save(final Quolet xxxxx) {
		Assert.notNull(xxxxx);
		Quolet res = this.findOne(xxxxx.getId());

		while (res == null || res.getId() == 0)
			try {
				res = this.repository.save(xxxxx);
			} catch (final Throwable ops) {
				this.generateTicker(xxxxx);
			}
		return xxxxx;
	}

	public QuoletForm createForm() {
		final QuoletForm form = new QuoletForm();
		form.setBody("");
		form.setPicture("");

		return form;
	}

	public Quolet reconstruct(final QuoletForm form, final BindingResult bindingResult) {
		Quolet res;

		if (form.getBody().length() > 100)
			bindingResult.rejectValue("body", "error.longBody");

		if (form.getId() == 0) {
			res = this.create();
			res.setDraftMode(true);
			this.generateTicker(res);
		} else
			res = this.findOne(form.getId());

		Assert.isTrue(res.getDraftMode());

		res.setAudit(this.auditService.findOne(form.getAuditId()));
		res.setBody(form.getBody());
		res.setPicture(form.getPicture());
		res.setCompany(this.companyService.findPrincipal());

		this.validator.validate(res, bindingResult);
		if (bindingResult.hasErrors())
			throw new ValidationException();

		return res;
	}

	public QuoletForm deconstruct(final Quolet xxxxx) {
		final QuoletForm x = this.createForm();

		x.setAuditId(xxxxx.getAudit().getId());
		x.setBody(xxxxx.getBody());
		x.setId(xxxxx.getId());
		x.setPicture(xxxxx.getPicture());

		return x;
	}

	public Collection<Quolet> findByAudit(final Audit audit) {
		return this.repository.findByAuditId(audit.getId());
	}

	public Collection<Quolet> findByCompany(final Company company) {
		return this.repository.findByCompanyId(company.getId());
	}

	public Collection<Quolet> findPublicByAudit(final int auditId) {
		return this.repository.findPublic(auditId);
	}

	//Generador de tickers con el patrón de YYMMDD-AAAAA
	private void generateTicker(final Quolet xxxxx) {
		String ticker = "";
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		final Date date = calendar.getTime();
		System.out.println(date.getDay());
		ticker += date.getYear() % 100 + "";
		final Integer month = calendar.get(Calendar.MONTH) + 1;
		if (month < 9)
			ticker += "0" + month;
		else
			ticker += month;
		final Integer day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day < 9)
			ticker += "0" + day;
		else
			ticker += day;

		ticker += "#";
		do
			for (int i = 0; i < QuoletService.TICKER_LENGTH; ++i)
				ticker += QuoletService.TICKER_ALPHABET.charAt(this.random.nextInt(QuoletService.TICKER_ALPHABET.length()));
		while (this.repository.findByTicker(ticker).size() > 0);
		xxxxx.setTicker(ticker);
	}

}
