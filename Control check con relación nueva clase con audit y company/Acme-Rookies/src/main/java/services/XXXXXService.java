
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
import domain.XXXXX;
import forms.XXXXXForm;
import repositories.XXXXXRepository;

@Service
@Transactional
public class XXXXXService extends AbstractService<XXXXXRepository, XXXXX> {

	@Autowired
	private AuditService		auditService;

	@Autowired
	private CompanyService		companyService;

	private static final int	TICKER_LENGTH	= 5;
	private static final String	TICKER_NUMBER	= "0123456789";
	//Por si las moscas, el ticker lleva en vez de números, letras
	//En este caso, cambiar en el generador de tickers, TICKER_NUMBER por TICKER_LENGTH
	private static final String	TICKER_ALPHABET	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	//Esto no me gusta nada, se aconsejó eliminar toda referencia a objetos fuera de los métodos
	//para mejorar la eficiencia y otros problemas que puede conllevar
	private final Random		random			= new Random();


	@Override
	public XXXXX save(final XXXXX xxxxx) {
		Assert.notNull(xxxxx);
		XXXXX res = this.findOne(xxxxx.getId());

		while (res == null || res.getId() == 0)
			try {
				res = this.repository.save(xxxxx);
			} catch (final Throwable ops) {
				this.generateTicker(xxxxx);
			}
		return xxxxx;
	}

	public XXXXXForm createForm() {
		final XXXXXForm form = new XXXXXForm();
		form.setBody("");
		form.setPicture("");

		return form;
	}

	public XXXXX reconstruct(final XXXXXForm form, final BindingResult bindingResult) {
		XXXXX res;

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

	public XXXXXForm deconstruct(final XXXXX xxxxx) {
		final XXXXXForm x = this.createForm();

		x.setAuditId(xxxxx.getAudit().getId());
		x.setBody(xxxxx.getBody());
		x.setId(xxxxx.getId());
		x.setPicture(xxxxx.getPicture());

		return x;
	}

	public Collection<XXXXX> findByAudit(final Audit audit) {
		return this.repository.findByAuditId(audit.getId());
	}

	public Collection<XXXXX> findByCompany(final Company company) {
		return this.repository.findByCompanyId(company.getId());
	}

	public Collection<XXXXX> findPublicByAudit(final int auditId) {
		return this.repository.findPublic(auditId);
	}

	//Generador de tickers con el patrón de YYMMDD-AAAAA
	private void generateTicker(final XXXXX xxxxx) {
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
		ticker += "-";
		do
			for (int i = 0; i < XXXXXService.TICKER_LENGTH; ++i)
				ticker += XXXXXService.TICKER_ALPHABET.charAt(this.random.nextInt(XXXXXService.TICKER_ALPHABET.length()));
		while (this.repository.findByTicker(ticker).size() > 0);
		xxxxx.setTicker(ticker);
	}

}
