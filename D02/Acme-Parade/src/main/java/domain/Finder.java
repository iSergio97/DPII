
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	////////////////////////////////////////////////////////////////////////////////
	// Fields

	private String	keyword;
	private Area	area;
	private Date	minimumDate;
	private Date	maximumDate;
	private float	cache;


	////////////////////////////////////////////////////////////////////////////////
	// Field access methods

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMinimumDate() {
		return this.minimumDate;
	}

	public void setMinimumDate(final Date minimumDate) {
		this.minimumDate = minimumDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMaximumDate() {
		return this.maximumDate;
	}

	public void setMaximumDate(final Date maximumDate) {
		this.maximumDate = maximumDate;
	}

	public float getCache() {
		return this.cache;
	}

	public void setCache(final float cache) {
		this.cache = cache;
	}

}
