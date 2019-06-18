
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.XXXXXRepository;
import domain.Audit;
import domain.Company;
import domain.XXXXX;
import forms.XXXXXForm;

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
		XXXXX xxxxx;

		if (form.getId() == 0)
			xxxxx = this.create();
		else
			xxxxx = this.findOne(form.getId());

		Assert.isTrue(xxxxx.getDraftMode());

		xxxxx.setAudit(this.auditService.findOne(form.getAuditId()));
		xxxxx.setBody(form.getBody());
		xxxxx.setPicture(form.getPicture());
		xxxxx.setCompany(this.companyService.findPrincipal());

		return xxxxx;
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

	//Generador de tickers con el patrón de YYMMDD-AAAAA
	private void generateTicker(final XXXXX xxxxx) {
		String ticker = "";
		final Date date = new Date();
		ticker += date.getYear() % 100 + "" + date.getMonth() + "" + date.getDay();
		ticker += "-";
		do
			for (int i = 0; i < XXXXXService.TICKER_LENGTH; ++i)
				ticker += XXXXXService.TICKER_NUMBER.charAt(this.random.nextInt(XXXXXService.TICKER_NUMBER.length()));
		while (this.repository.findByTicker(ticker).size() > 0);
		xxxxx.setTicker(ticker);
	}

}
