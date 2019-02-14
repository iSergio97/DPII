
package domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

    // Fields -----------------------------------------------------------------

    private String					keyword;
    private String                  area;
    private Date					minimumDate;
    private Date					maximumDate;

    // Relationships ----------------------------------------------------------
    private Member member;

    // Field access methods ---------------------------------------------------

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(final String area) {
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

    // Relationship access methods --------------------------------------------


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
