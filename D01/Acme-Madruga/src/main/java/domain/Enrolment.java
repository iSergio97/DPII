package domain;

import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.List;

public class Enrolment {

    // Fields -----------------------------------------------------------------
    private Date moment;
    private String position;

    // Relationships ----------------------------------------------------------
    private Member member;
    private Brotherhood brotherhood;


    // Field access methods ---------------------------------------------------

    @NotBlank
    @NotNull
    public Date getMoment(){
        return this.moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    @NotBlank
    @NotNull
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    // Relationship access methods --------------------------------------------

    public Brotherhood getBrotherhood() {
        return brotherhood;
    }

    public void setBrotherhood(Brotherhood brotherhood) {
        this.brotherhood = brotherhood;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
